package pl.poznan.put.michalxpz.budgettracker.controllers

import com.jfoenix.controls.JFXDrawer
import com.jfoenix.controls.JFXHamburger
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.chart.LineChart
import javafx.scene.chart.PieChart
import javafx.scene.chart.XYChart
import pl.poznan.put.michalxpz.budgettracker.data.Expense
import pl.poznan.put.michalxpz.budgettracker.data.ExpenseCategory
import pl.poznan.put.michalxpz.budgettracker.data.Income
import pl.poznan.put.michalxpz.budgettracker.data.IncomeCategory
import pl.poznan.put.michalxpz.budgettracker.initializers.DrawerMenuInitializer
import pl.poznan.put.michalxpz.budgettracker.repository.ExpensesRepository
import pl.poznan.put.michalxpz.budgettracker.repository.IncomesRepository
import java.net.URL
import java.time.LocalDate
import java.util.*

class AnalysisScreenController : Initializable {

    private val expenses = ArrayList<Expense>()
    private  val incomes = ArrayList<Income>()

    @FXML
    private lateinit var drawerMenu: JFXDrawer

    @FXML
    private lateinit var burgerButton: JFXHamburger

    @FXML
    private lateinit var incomesPieChart: PieChart

    @FXML
    private lateinit var expensesPieChart: PieChart

    @FXML
    private lateinit var expensesGraph: LineChart<String, Double>

    @FXML
    private lateinit var incomesGraph: LineChart<String, Double>

    override fun initialize(url: URL?, resourceBundle: ResourceBundle?) {
        setupDrawerMenu()
        loadExpensesAndIncomes()
        setupPieCharts()
        setupLineCharts()
    }

    private fun setupDrawerMenu() {
        val drawerMenuInitializer = DrawerMenuInitializer(drawerMenu = drawerMenu, burgerButton = burgerButton)
        drawerMenuInitializer.setupDrawerMenu()
    }

    private fun setupLineCharts() {
        val incomesMap = HashMap<LocalDate, Double>()
        val expensesMap = HashMap<LocalDate, Double>()
        val incomeDates = arrayListOf<LocalDate>()
        val expenseDates = arrayListOf<LocalDate>()

        incomes.forEach { income ->
            incomesMap.put(income.date, incomesMap.get(income.date)?.plus(income.amount) ?: income.amount)
            incomeDates.add(income.date)
        }
        expenses.forEach { expense ->
            expensesMap.put(expense.date, expensesMap.get(expense.date)?.plus(expense.amount) ?: expense.amount)
            expenseDates.add(expense.date)
        }
        val sortedIncomeDates = incomeDates.sorted()
        val sortedExpenseDates = expenseDates.sorted()

        val incomeSeries = createSeries(sortedIncomeDates, incomesMap)
        val expenseSeries = createSeries(sortedExpenseDates, expensesMap)
        incomesGraph.data.add(incomeSeries)
        expensesGraph.data.add(expenseSeries)
    }

    private fun setupPieCharts() {
        val incomesMap = HashMap<IncomeCategory, Double>()
        val expensesMap = HashMap<ExpenseCategory, Double>()
        incomes.forEach { income ->
            incomesMap.put(income.category, incomesMap.get(income.category)?.plus(income.amount) ?: income.amount)
        }
        incomesMap.forEach { key, value ->
            if (value != 0.0) incomesPieChart.data.add(PieChart.Data(key.name, value))
        }
        expenses.forEach { expense ->
            expensesMap.put(expense.category, expensesMap.get(expense.category)?.plus(expense.amount) ?: expense.amount)
        }
        expensesMap.forEach { key, value ->
            if (value != 0.0) expensesPieChart.data.add(PieChart.Data(key.name, value))
        }
    }

    private fun loadExpensesAndIncomes() {
        val expensesRepository = ExpensesRepository()
        val incomesRepository = IncomesRepository()
        expensesRepository.readFromFile()?.forEach { expense ->
            expenses.add(expense)
        }
        incomesRepository.readFromFile()?.forEach { income ->
            incomes.add(income)
        }
    }

    private fun createSeries(
        sortedDates: List<LocalDate>,
        map: HashMap<LocalDate, Double>,
    ): XYChart.Series<String, Double> {
        val series = XYChart.Series<String, Double>()
        sortedDates.forEach { date ->
            map.forEach { key, value ->
                if (date == key) {
                    val dateString = "${key.monthValue}-${key.dayOfMonth}"
                    series.data.add(XYChart.Data(dateString, value))
                }
            }
        }
        return series
    }
}
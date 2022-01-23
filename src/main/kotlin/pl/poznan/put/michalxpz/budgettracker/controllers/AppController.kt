package pl.poznan.put.michalxpz.budgettracker.controllers

import com.jfoenix.controls.JFXDrawer
import com.jfoenix.controls.JFXHamburger
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.geometry.Insets
import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.layout.GridPane
import javafx.scene.paint.Color
import javafx.scene.shape.ArcType
import pl.poznan.put.michalxpz.budgettracker.data.Expense
import pl.poznan.put.michalxpz.budgettracker.data.Income
import pl.poznan.put.michalxpz.budgettracker.initializers.DrawerMenuInitializer
import pl.poznan.put.michalxpz.budgettracker.initializers.SetupDialogInitializer
import pl.poznan.put.michalxpz.budgettracker.repository.ExpensesRepository
import pl.poznan.put.michalxpz.budgettracker.repository.IncomesRepository
import pl.poznan.put.michalxpz.budgettracker.sharedPrefs.SharedPrefs
import java.net.URL
import java.time.LocalDate
import java.util.*
import kotlin.math.roundToInt

class AppController : Initializable {

    private val expenses = ArrayList<Expense>()
    private val incomes = ArrayList<Income>()
    private var targetPercentage: Double = 0.0
    private lateinit var graphicsContext: GraphicsContext

    @FXML
    private lateinit var gridPane: GridPane

    @FXML
    private lateinit var last5ExpensesTable: TableView<Expense>

    @FXML
    private lateinit var incomeSenderCol: TableColumn<Income, String>

    @FXML
    private lateinit var incomeAmountCol: TableColumn<Income, Double>

    @FXML
    private lateinit var incomeDateCol: TableColumn<Income, LocalDate>

    @FXML
    private lateinit var expenseReceiverCol: TableColumn<Expense, String>

    @FXML
    private lateinit var expenseAmountCol: TableColumn<Expense, Double>

    @FXML
    private lateinit var expenseDateCol: TableColumn<Expense, LocalDate>

    @FXML
    private lateinit var last5IncomesTable: TableView<Income>

    @FXML
    private lateinit var nameLabel: Label

    @FXML
    private lateinit var drawerMenu: JFXDrawer

    @FXML
    private lateinit var burgerButton : JFXHamburger

    @FXML
    private lateinit var settingsButton: Button

    @FXML
    private lateinit var progressIndicatorCanvas: Canvas

    @FXML
    private lateinit var budgetLabel: Label

    @FXML
    private lateinit var budgetPercentLabel: Label


    override fun initialize(url: URL?, resourceBundle: ResourceBundle?) {
        setupDrawerMenu()
        loadExpensesAndIncomes()
        calculateBalance()
        setupTables()
        setUpCanvas()
        setNameLabel(SharedPrefs.applicationData.name)
        setupGridPaneMargins()
        setupSettingButtonAction()
    }

    private fun setupDrawerMenu() {
        val drawerMenuInitializer = DrawerMenuInitializer(drawerMenu = drawerMenu, burgerButton = burgerButton)
        drawerMenuInitializer.setupDrawerMenu()
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

    private fun setUpCanvas() {
        targetPercentage = SharedPrefs.balance * 360 / SharedPrefs.applicationData.targetAmount
        if (targetPercentage < 0) targetPercentage = 0.0
        budgetLabel.text = "Your balance is: ${SharedPrefs.balance}"
        graphicsContext = progressIndicatorCanvas.graphicsContext2D
        graphicsContext.lineWidth = 2.0
        graphicsContext.stroke = if (!SharedPrefs.applicationData.isDarkMode) {
            Color.BLACK
        } else {
            Color.WHITE
        }
        graphicsContext.strokeArc(0.0, 0.0, 180.0, 160.0, 270.0, targetPercentage, ArcType.OPEN)
        budgetPercentLabel.text = "${(SharedPrefs.balance/SharedPrefs.applicationData.targetAmount*100).roundToInt()}%"
    }

    private fun setupTables() {
        incomeAmountCol.cellValueFactory = PropertyValueFactory("amount")
        incomeSenderCol.cellValueFactory = PropertyValueFactory("sender")
        incomeDateCol.cellValueFactory = PropertyValueFactory("date")
        expenseAmountCol.cellValueFactory = PropertyValueFactory("amount")
        expenseReceiverCol.cellValueFactory = PropertyValueFactory("receiver")
        expenseDateCol.cellValueFactory = PropertyValueFactory("date")

        expenses.take(5).forEach {
            last5ExpensesTable.items.add(it)
        }

        incomes.take(5).forEach {
            last5IncomesTable.items.add(it)
        }
    }

    private fun calculateBalance() {
        if (SharedPrefs.balance == 0.0) {
            val expensesRepository = ExpensesRepository()
            val incomesRepository = IncomesRepository()
            expensesRepository.readFromFile()?.forEach { expense ->
                SharedPrefs.balance -= expense.amount
            }
            incomesRepository.readFromFile()?.forEach { income ->
                SharedPrefs.balance += income.amount
            }
        }
    }

    private fun setupSettingButtonAction() {
        settingsButton.addEventHandler(ActionEvent.ACTION) {
            progressIndicatorCanvas.graphicsContext2D.clearRect(
                0.0,
                0.0,
                progressIndicatorCanvas.width,
                progressIndicatorCanvas.height
            )
            openSetupDialog()
            setNameLabel(SharedPrefs.applicationData.name)
            calculateBalance()
            setUpCanvas()
        }
    }

    private fun openSetupDialog() {
        val setupDialogInitializer = SetupDialogInitializer()
        setupDialogInitializer.openSetupDialog()
    }

    private fun setNameLabel(name: String?) {
        if (!name.isNullOrEmpty()) {
            nameLabel.text = "Welcome, $name!\nWe're glad you manage your finances with us :)"
        } else {
            nameLabel.text = "..."
        }
    }

    private fun setupGridPaneMargins() {
        gridPane.hgap = 10.0
        gridPane.vgap = 10.0
        gridPane.padding = Insets(10.0, 10.0, 10.0, 10.0)
    }

}
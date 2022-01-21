package pl.poznan.put.michalxpz.budgettracker.controllers

import com.jfoenix.controls.JFXDrawer
import com.jfoenix.controls.JFXHamburger
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext
import javafx.scene.control.Label
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.input.MouseEvent
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.VBox
import javafx.scene.shape.ArcType
import pl.poznan.put.michalxpz.budgettracker.App
import pl.poznan.put.michalxpz.budgettracker.data.Expense
import pl.poznan.put.michalxpz.budgettracker.data.Income
import pl.poznan.put.michalxpz.budgettracker.repository.ExpensesRepository
import pl.poznan.put.michalxpz.budgettracker.repository.IncomesRepository
import pl.poznan.put.michalxpz.budgettracker.sharedPrefs.SharedPrefs
import java.io.IOException
import java.net.URL
import java.time.LocalDate
import java.util.*
import java.util.logging.Level
import java.util.logging.Logger
import kotlin.math.roundToInt

class AppController : Initializable {

    private val expenses = ArrayList<Expense>()
    private  val incomes = ArrayList<Income>()
    private lateinit var graphicsContext: GraphicsContext
    private var targetPercentage: Double = 0.0

    @FXML
    private lateinit var rootPane: AnchorPane
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
    private lateinit var anchorPane : AnchorPane

    @FXML
    private lateinit var drawerMenu: JFXDrawer

    @FXML
    private lateinit var burgerButton : JFXHamburger

    @FXML
    private lateinit var progressIndicatorCanvas: Canvas

    @FXML
    private lateinit var budgetLabel: Label

    @FXML
    private lateinit var budgetPercentLabel: Label


    override fun initialize(url: URL?, resourceBundle: ResourceBundle?) {
        setupDrawerMenu()
        loadExpensesAndIncomes()
        setupTables()
        setUpCanvas()
    }

    private fun setUpCanvas() {
        targetPercentage = SharedPrefs.balance * 360 / SharedPrefs.applicationData.targetAmount
        budgetLabel.text = "Your balance is: ${SharedPrefs.balance}"
        graphicsContext = progressIndicatorCanvas.graphicsContext2D
        graphicsContext.strokeArc(360.0, 0.0, 180.0, 160.0, 270.0, targetPercentage, ArcType.OPEN)
        budgetPercentLabel.text = "${(SharedPrefs.balance/SharedPrefs.applicationData.targetAmount*100).roundToInt()}%"

    }


    fun setNameLabel(name: String?) {
        if (!name.isNullOrEmpty()) {
            nameLabel.text = "Wlecome, $name!\nWe're glad you manage your finances with us :)"
        } else {
            nameLabel.text = "..."
        }
    }

    private fun setupDrawerMenu() {
        try {
            val fxmlLoader = FXMLLoader(App::class.java.getResource("sideDrawer.fxml"))
            val box: VBox = fxmlLoader.load()
            drawerMenu.setSidePane(box)

            val hamburgerTransition = HamburgerBackArrowBasicTransition(burgerButton)
            hamburgerTransition.rate = -1.0
            burgerButton.addEventHandler(
                MouseEvent.MOUSE_PRESSED
            ) {
                hamburgerTransition.rate *= -1
                hamburgerTransition.play()
                if (drawerMenu.isOpened) drawerMenu.close() else drawerMenu.open()

            }

        } catch (exception: IOException) {
            Logger.getLogger(AppController::class.java.name).log(Level.SEVERE, null, exception)
        }
    }

    private fun loadExpensesAndIncomes() {
        val expensesRepository = ExpensesRepository()
        val incomesRepository = IncomesRepository()
        expensesRepository.readFromFile()?.forEach { expense ->
            expenses.add(expense)
            SharedPrefs.balance -= expense.amount
        }
        incomesRepository.readFromFile()?.forEach { income ->
            incomes.add(income)
            SharedPrefs.balance += income.amount
        }
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

}
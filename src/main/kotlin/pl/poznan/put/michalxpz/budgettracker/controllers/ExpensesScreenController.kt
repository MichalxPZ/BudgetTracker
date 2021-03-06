package pl.poznan.put.michalxpz.budgettracker.controllers

import com.jfoenix.controls.JFXButton
import com.jfoenix.controls.JFXDrawer
import com.jfoenix.controls.JFXHamburger
import javafx.collections.FXCollections
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.control.*
import javafx.scene.control.cell.PropertyValueFactory
import javafx.util.Callback
import pl.poznan.put.michalxpz.budgettracker.App
import pl.poznan.put.michalxpz.budgettracker.data.Expense
import pl.poznan.put.michalxpz.budgettracker.data.ExpenseCategory
import pl.poznan.put.michalxpz.budgettracker.initializers.DrawerMenuInitializer
import pl.poznan.put.michalxpz.budgettracker.repository.ExpensesRepository
import pl.poznan.put.michalxpz.budgettracker.sharedPrefs.SharedPrefs
import java.net.URL
import java.time.LocalDate
import java.util.*

class ExpensesScreenController : Initializable {

    private var expenses = ArrayList<Expense>()

    @FXML
    private lateinit var drawerMenu: JFXDrawer

    @FXML
    private lateinit var burgerButton: JFXHamburger

    @FXML
    private lateinit var expensesTable: TableView<Expense>

    @FXML
    private lateinit var expenseReceiverCol: TableColumn<Expense, String>

    @FXML
    private lateinit var expenseCategoryCol: TableColumn<Expense, ExpenseCategory>

    @FXML
    private lateinit var expenseAmountCol: TableColumn<Expense, Double>

    @FXML
    private lateinit var expenseDateCol: TableColumn<Expense, LocalDate>

    @FXML
    private lateinit var addExpenseButton: JFXButton

    @FXML
    private var delCol: TableColumn<Expense, Void> = TableColumn<Expense, Void>()

    override fun initialize(url: URL?, resourceBundle: ResourceBundle?) {
        setupDrawerMenu()
        loadExpenses()
        setupTables()
        setupDeleteButton()
        setupAddButtonAction()
    }

    private fun setupDrawerMenu() {
        val drawerMenuInitializer = DrawerMenuInitializer(drawerMenu = drawerMenu, burgerButton = burgerButton)
        drawerMenuInitializer.setupDrawerMenu()
    }

    private fun loadExpenses() {
        val expensesRepository = ExpensesRepository()

        expensesRepository.readFromFile()?.forEach { expense ->
            expenses.add(expense)
        }
    }

    private fun setupTables() {
        expensesTable.items = FXCollections.observableList(ArrayList())
        expenses = ArrayList()
        loadExpenses()
        expenseAmountCol.cellValueFactory = PropertyValueFactory("amount")
        expenseReceiverCol.cellValueFactory = PropertyValueFactory("receiver")
        expenseDateCol.cellValueFactory = PropertyValueFactory("date")
        expenseCategoryCol.cellValueFactory = PropertyValueFactory("category")

        expenses.forEach {
            expensesTable.items.add(it)
        }
    }

    private fun setupDeleteButton() {
        val cellFactory: Callback<TableColumn<Expense?, Void?>?, TableCell<Expense?, Void?>?> =
            Callback<TableColumn<Expense?, Void?>?, TableCell<Expense?, Void?>?> {
                object : TableCell<Expense?, Void?>() {
                    private val btn = Button("Remove")

                    init {
                        btn.onAction = EventHandler {
                            val data: Expense = expensesTable.items[index]
                            deleteRow(data)
                        }
                    }

                    override fun updateItem(p0: Void?, empty: Boolean) {
                        super.updateItem(item, empty)
                        graphic = if (empty) {
                            null
                        } else {
                            btn
                        }
                    }
                }
            }
        delCol.cellFactory = cellFactory
        expensesTable.columns.add(delCol)
    }

    private fun setupAddButtonAction() {
        addExpenseButton.addEventHandler(ActionEvent.ACTION) {
            openAddExpenseDialog()
        }
    }

    private fun openAddExpenseDialog() {
        val fxmlLoader = FXMLLoader(App::class.java.getResource("addExpenseDialog.fxml"))
        val dialogPane = fxmlLoader.load<DialogPane>()
        val dialogController = fxmlLoader.getController<AddExpenseController>()
        val dialog = Dialog<ButtonType>()
        dialog.dialogPane = dialogPane
        dialog.title = dialogPane.headerText
        val clickedButton: Optional<ButtonType> = dialog.showAndWait()
        if (clickedButton.get() == ButtonType.OK) {
            var status = dialogController.validateExpenseData()
            while (status != "OK") {
                dialog.headerText = status
                dialog.showAndWait()
                status = dialogController.validateExpenseData()
            }
            dialogController.addExpense()
            setupTables()
        }
    }

    private fun deleteRow(data: Expense) {
        expensesTable.items.remove(data)
        expenses.remove(data)
        val expensesRepository = ExpensesRepository()
        SharedPrefs.balance += data.amount
        expensesRepository.saveToFile(expenses)
    }
}
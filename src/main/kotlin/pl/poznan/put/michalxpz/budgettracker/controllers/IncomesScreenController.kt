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
import pl.poznan.put.michalxpz.budgettracker.data.Income
import pl.poznan.put.michalxpz.budgettracker.data.IncomeCategory
import pl.poznan.put.michalxpz.budgettracker.initializers.DrawerMenuInitializer
import pl.poznan.put.michalxpz.budgettracker.repository.IncomesRepository
import pl.poznan.put.michalxpz.budgettracker.sharedPrefs.SharedPrefs
import java.net.URL
import java.time.LocalDate
import java.util.*

class IncomeScreenContoller : Initializable {

    private var incomes = ArrayList<Income>()

    @FXML
    private lateinit var drawerMenu: JFXDrawer
    @FXML
    private lateinit var burgerButton: JFXHamburger

    @FXML
    private lateinit var incomesTable: TableView<Income>

    @FXML
    private lateinit var incomeSenderCol: TableColumn<Income, String>

    @FXML
    private lateinit var incomeCategoryCol: TableColumn<Income, IncomeCategory>

    @FXML
    private lateinit var incomeAmountCol: TableColumn<Income, Double>

    @FXML
    private lateinit var incomeDateCol: TableColumn<Income, LocalDate>

    @FXML
    private lateinit var addIncomeButton: JFXButton

    @FXML
    private var delCol: TableColumn<Income, Void> = TableColumn<Income, Void>()

    override fun initialize(url: URL?, resourceBundle: ResourceBundle?) {
        setupDrawerMenu()
        loadIncome()
        setupTables()
        setupDeleteButton()
        setupAddButtonAction()
    }

    private fun setupDrawerMenu() {
        val drawerMenuInitializer = DrawerMenuInitializer(drawerMenu = drawerMenu, burgerButton = burgerButton)
        drawerMenuInitializer.setupDrawerMenu()
    }

    private fun loadIncome() {
        val incomesRepository = IncomesRepository()

        incomesRepository.readFromFile()?.forEach { Income ->
            incomes.add(Income)
        }
    }


    private fun setupTables() {
        incomesTable.items = FXCollections.observableList(ArrayList())
        incomes = ArrayList()
        loadIncome()
        incomeAmountCol.cellValueFactory = PropertyValueFactory("amount")
        incomeSenderCol.cellValueFactory = PropertyValueFactory("sender")
        incomeDateCol.cellValueFactory = PropertyValueFactory("date")
        incomeCategoryCol.cellValueFactory = PropertyValueFactory("category")

        incomes.forEach {
            incomesTable.items.add(it)
        }
    }

    private fun setupDeleteButton() {
        val cellFactory: Callback<TableColumn<Income?, Void?>?, TableCell<Income?, Void?>?> =
            Callback<TableColumn<Income?, Void?>?, TableCell<Income?, Void?>?> {
                object : TableCell<Income?, Void?>() {
                    private val btn = Button("Remove")

                    init {
                        btn.onAction = EventHandler {
                            val data: Income = incomesTable.items[index]
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
        incomesTable.columns.add(delCol)
    }

    private fun setupAddButtonAction() {
        addIncomeButton.addEventHandler(ActionEvent.ACTION) {
            openAddIncomeDialog()
        }
    }

    private fun openAddIncomeDialog() {
        val fxmlLoader = FXMLLoader(App::class.java.getResource("addIncomeDialog.fxml"))
        val dialogPane = fxmlLoader.load<DialogPane>()
        val dialogController = fxmlLoader.getController<AddIncomeController>()
        val dialog = Dialog<ButtonType>()
        dialog.dialogPane = dialogPane
        dialog.title = dialogPane.headerText
        val clickedButton: Optional<ButtonType> = dialog.showAndWait()
        if (clickedButton.get() == ButtonType.OK) {
            var status = dialogController.validateIncomeData()
            while (status != "OK") {
                dialog.headerText = status
                dialog.showAndWait()
                status = dialogController.validateIncomeData()
            }
            dialogController.addIncome()
            setupTables()
        }
    }

    private fun deleteRow(data: Income) {
        incomesTable.items.remove(data)
        incomes.remove(data)
        val incomesRepository = IncomesRepository()
        SharedPrefs.balance -= data.amount
        incomesRepository.saveToFile(incomes)
    }
}

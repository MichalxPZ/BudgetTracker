package pl.poznan.put.michalxpz.budgettracker

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.control.ButtonType
import javafx.scene.control.Dialog
import javafx.scene.control.DialogPane
import javafx.stage.Stage
import pl.poznan.put.michalxpz.budgettracker.controllers.AppController
import pl.poznan.put.michalxpz.budgettracker.controllers.StartupDialogController
import pl.poznan.put.michalxpz.budgettracker.data.*
import pl.poznan.put.michalxpz.budgettracker.repository.ExpensesRepository
import pl.poznan.put.michalxpz.budgettracker.repository.IncomesRepository
import pl.poznan.put.michalxpz.budgettracker.sharedPrefs.SharedPrefs
import pl.poznan.put.michalxpz.budgettracker.sharedPrefs.SharedPrefsRepository
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class App : Application() {
    override fun start(stage: Stage) {
        loadSharedPrefs()
        setupMockData()
        val fxmlLoader = FXMLLoader(App::class.java.getResource("home.fxml"))
        val scene = Scene(fxmlLoader.load(), 800.0, 600.0)
        val controller = fxmlLoader.getController<AppController>()
        stage.title = "BudgetTracker"
        stage.scene = scene
        stage.minHeight = 600.0
        stage.minWidth = 800.0
        stage.show()
        controller.setNameLabel(SharedPrefs.applicationData.name)
    }

    private fun loadSharedPrefs() {
        val sharedPrefsRepository = SharedPrefsRepository()
        val appDataList = sharedPrefsRepository.readFromFile()
        var appData: ApplicationData? = null
        if (appDataList?.size!! > 0) {
            appData = appDataList!!.get(0)
        }
        if (appData != null) {
            SharedPrefs.applicationData = appData
        } else {
            initSharedPrefs()
        }
    }

    private fun initSharedPrefs() {
        val fxmlLoader = FXMLLoader(App::class.java.getResource("startupDialog.fxml"))
        val dialogPane = fxmlLoader.load<DialogPane>()
        val dialogController = fxmlLoader.getController<StartupDialogController>()
        val dialog = Dialog<ButtonType>()
        dialog.dialogPane = dialogPane
        dialog.title = dialogPane.headerText
        var clickedButton: Optional<ButtonType> = dialog.showAndWait()
        if (clickedButton.get() == ButtonType.OK) {
            var status = dialogController.validateApplicationData()
            while (status != "OK") {
                dialog.headerText = status
                clickedButton = dialog.showAndWait()
                status = dialogController.validateApplicationData()
            }
            dialogController.setApplicationData()
        }
    }

    fun setupMockData() {
        val incomes = arrayListOf<Income>(
            Income(amount = 1000.0, category = IncomeCategory.SALARY, sender = "My mom", LocalDate.parse("2022-01-20", DateTimeFormatter.ISO_DATE)),
            Income(amount = 2000.0, category = IncomeCategory.RECHARGE, sender = "My dad", LocalDate.parse("2022-01-19", DateTimeFormatter.ISO_DATE)),
            Income(amount = 3000.0, category = IncomeCategory.UNDEFINED, sender = "My mom", LocalDate.parse("2022-01-19", DateTimeFormatter.ISO_DATE)),
            Income(amount = 3000.0, category = IncomeCategory.SALARY, sender = "My boss", LocalDate.parse("2022-01-19", DateTimeFormatter.ISO_DATE)),
            Income(amount = 4000.0, category = IncomeCategory.SALARY, sender = "My mom", LocalDate.parse("2022-01-18", DateTimeFormatter.ISO_DATE)),
            Income(amount = 5000.0, category = IncomeCategory.SALARY, sender = "My mom", LocalDate.parse("2022-01-18", DateTimeFormatter.ISO_DATE)),
            Income(amount = 6100.0, category = IncomeCategory.RECHARGE, sender = "My mom", LocalDate.parse("2022-01-18", DateTimeFormatter.ISO_DATE))
        )

        val expenses = arrayListOf<Expense>(
            Expense(amount = 1000.0, category = ExpenseCategory.BILLS, receiver = "biedronka", LocalDate.parse("2022-01-20", DateTimeFormatter.ISO_DATE)),
            Expense(amount = 2000.0, category = ExpenseCategory.CLOTHES, receiver = "zabka", LocalDate.parse("2022-01-20", DateTimeFormatter.ISO_DATE)),
            Expense(amount = 3000.0, category = ExpenseCategory.ENTERTAINMENT, receiver = "piekus", LocalDate.parse("2022-01-20", DateTimeFormatter.ISO_DATE)),
            Expense(amount = 3000.0, category = ExpenseCategory.HEALTH, receiver = "twoja mama", LocalDate.parse("2022-01-19", DateTimeFormatter.ISO_DATE)),
            Expense(amount = 4000.0, category = ExpenseCategory.TRANSPORT, receiver = "przelw", LocalDate.parse("2022-01-19", DateTimeFormatter.ISO_DATE)),
            Expense(amount = 5000.0, category = ExpenseCategory.UNDEFINED, receiver = "biedronka", LocalDate.parse("2022-01-18", DateTimeFormatter.ISO_DATE)),
            Expense(amount = 6000.0, category = ExpenseCategory.BILLS, receiver = "biedronka", LocalDate.parse("2022-01-18", DateTimeFormatter.ISO_DATE)),
        )

        val incomesRepository = IncomesRepository()
        incomesRepository.saveToFile(incomes)
        val expensesRepository = ExpensesRepository()
        expensesRepository.saveToFile(expenses)
    }
}

fun main() {
    Application.launch(App::class.java)

}
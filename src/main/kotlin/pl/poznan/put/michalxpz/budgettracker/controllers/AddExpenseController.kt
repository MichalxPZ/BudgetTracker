package pl.poznan.put.michalxpz.budgettracker.controllers

import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.ChoiceBox
import javafx.scene.control.TextField
import pl.poznan.put.michalxpz.budgettracker.data.Expense
import pl.poznan.put.michalxpz.budgettracker.data.ExpenseCategory
import pl.poznan.put.michalxpz.budgettracker.exceptions.BudgetTrackerExceptions
import pl.poznan.put.michalxpz.budgettracker.repository.ExpensesRepository
import pl.poznan.put.michalxpz.budgettracker.sharedPrefs.SharedPrefs
import pl.poznan.put.michalxpz.budgettracker.validators.DateFieldValidator
import pl.poznan.put.michalxpz.budgettracker.validators.MoneyValueValidator
import pl.poznan.put.michalxpz.budgettracker.validators.NameFieldValidator
import java.net.URL
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class AddExpenseController : Initializable {

    private val expenses = ArrayList<Expense>()

    @FXML
    private lateinit var categoryChoiceBox: ChoiceBox<ExpenseCategory>

    @FXML
    private lateinit var receiverTextField: TextField

    @FXML
    private lateinit var amountTextField: TextField

    @FXML
    private lateinit var dateTextField: TextField

    override fun initialize(p0: URL?, p1: ResourceBundle?) {
        setupChoiceBoxOptions()
    }

    private fun setupChoiceBoxOptions() {
        val choices = ArrayList<ExpenseCategory>()
        ExpenseCategory.values().forEach {
            choices.add(it)
        }
        categoryChoiceBox.items = FXCollections.observableList(choices)
        categoryChoiceBox.value = ExpenseCategory.UNDEFINED
    }

    fun validateExpenseData(): String {
        var validationStatus = "OK"
        try {
            val nameValidator = NameFieldValidator()
            nameValidator.parseName(receiverTextField.text)

            val dateFieldValidator = DateFieldValidator()
            dateFieldValidator.parseDate(dateTextField.text)

            val moneyValueValidator = MoneyValueValidator()
            moneyValueValidator.parseValue(amountTextField.text)

            return validationStatus
        } catch (exception: BudgetTrackerExceptions) {
            println(exception)
            validationStatus = exception.toString()
            return validationStatus
        }
    }

    private fun loadExpenses() {
        val expensesRepository = ExpensesRepository()

        expensesRepository.readFromFile()?.forEach { expense ->
            expenses.add(expense)
        }
    }

    fun addExpense() {
        loadExpenses()
        val expense = Expense(
            amount = amountTextField.text.toDouble(),
            category = categoryChoiceBox.value,
            receiver = receiverTextField.text,
            date = LocalDate.parse(dateTextField.text, DateTimeFormatter.ISO_DATE)
        )
        expenses.add(0, expense)
        SharedPrefs.balance -= expense.amount
        val expensesRepository = ExpensesRepository()
        expensesRepository.saveToFile(expenses)
    }
}

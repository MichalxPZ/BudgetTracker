package pl.poznan.put.michalxpz.budgettracker.controllers

import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.ChoiceBox
import javafx.scene.control.TextField
import pl.poznan.put.michalxpz.budgettracker.data.Income
import pl.poznan.put.michalxpz.budgettracker.data.IncomeCategory
import pl.poznan.put.michalxpz.budgettracker.exceptions.BudgetTrackerExceptions
import pl.poznan.put.michalxpz.budgettracker.repository.IncomesRepository
import pl.poznan.put.michalxpz.budgettracker.sharedPrefs.SharedPrefs
import pl.poznan.put.michalxpz.budgettracker.validators.DateFieldValidator
import pl.poznan.put.michalxpz.budgettracker.validators.MoneyValueValidator
import pl.poznan.put.michalxpz.budgettracker.validators.NameFieldValidator
import java.net.URL
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class AddIncomeController : Initializable{

    private val incomes = ArrayList<Income>()

    @FXML
    private lateinit var categoryChoiceBox: ChoiceBox<IncomeCategory>

    @FXML
    private lateinit var senderTextField: TextField

    @FXML
    private lateinit var amountTextField: TextField

    @FXML
    private lateinit var dateTextField: TextField

    override fun initialize(p0: URL?, p1: ResourceBundle?) {
        setupChoiceBoxOptions()
    }

    private fun setupChoiceBoxOptions() {
        val choices = ArrayList<IncomeCategory>()
        IncomeCategory.values().forEach {
            choices.add(it)
        }
        categoryChoiceBox.items = FXCollections.observableList(choices)
        categoryChoiceBox.value = IncomeCategory.UNDEFINED
    }

    fun validateIncomeData(): String {
        var validationStatus = "OK"
        return try {
            val nameValidator = NameFieldValidator()
            nameValidator.parseName(senderTextField.text)

            val dateFieldValidator = DateFieldValidator()
            dateFieldValidator.parseDate(dateTextField.text)

            val moneyValueValidator = MoneyValueValidator()
            moneyValueValidator.parseValue(amountTextField.text)

            validationStatus
        } catch (exception: BudgetTrackerExceptions) {
            println(exception)
            validationStatus = exception.toString()
            validationStatus
        }
    }

    private fun loadExpenses() {
        val incomesRepository = IncomesRepository()

        incomesRepository.readFromFile()?.forEach { expense ->
            incomes.add(expense)
        }
    }

    fun addIncome() {
        loadExpenses()
        val income = Income(
            amount = amountTextField.text.toDouble(),
            category = categoryChoiceBox.value,
            sender = senderTextField.text,
            date = LocalDate.parse(dateTextField.text, DateTimeFormatter.ISO_DATE)
        )
        incomes.add(0, income)
        SharedPrefs.balance += income.amount
        val incomesRepository = IncomesRepository()
        incomesRepository.saveToFile(incomes)
    }
}
package pl.poznan.put.michalxpz.budgettracker.controllers

import com.jfoenix.controls.JFXToggleButton
import javafx.fxml.FXML
import javafx.scene.control.TextField
import pl.poznan.put.michalxpz.budgettracker.data.ApplicationData
import pl.poznan.put.michalxpz.budgettracker.exceptions.BudgetTrackerExceptions
import pl.poznan.put.michalxpz.budgettracker.sharedPrefs.SharedPrefs
import pl.poznan.put.michalxpz.budgettracker.sharedPrefs.SharedPrefsRepository
import pl.poznan.put.michalxpz.budgettracker.validators.NameFieldValidator
import pl.poznan.put.michalxpz.budgettracker.validators.SurnameValidator
import pl.poznan.put.michalxpz.budgettracker.validators.TargetAmountFieldValidator

class StartupDialogController {

    @FXML
    lateinit var darkModeToggle: JFXToggleButton

    @FXML
    lateinit var targetAmountTextField: TextField

    @FXML
    lateinit var surnameTextField: TextField

    @FXML
    lateinit var nameTextField: TextField

    fun validateApplicationData(): String {
        var validationStatus = "OK"
        return try {
            val nameValidator = NameFieldValidator()
            nameValidator.parseName(nameTextField.text)
            val surnameValidator = SurnameValidator()
            surnameValidator.parseSurname(surnameTextField.text)
            val targetAmountFieldValidator = TargetAmountFieldValidator()
            targetAmountFieldValidator.parseValue(targetAmountTextField.text)

            validationStatus
        } catch (exception: BudgetTrackerExceptions) {
            println(exception)
            validationStatus = exception.toString()
            validationStatus
        }
    }

    fun setApplicationData() {
        val applicationData = ApplicationData(
            name = nameTextField.text,
            surname = surnameTextField.text,
            targetAmount = targetAmountTextField.text.toDouble(),
            lastSearch = ArrayList(),
            isDarkMode = darkModeToggle.isSelected
        )
        SharedPrefs.applicationData = applicationData
        println(SharedPrefs.applicationData)

        saveToFile(applicationData)
    }

    private fun saveToFile(applicationData: ApplicationData) {
        val array: ArrayList<ApplicationData> = ArrayList()
        array.add(applicationData)
        val sharedPrefsRepository = SharedPrefsRepository()
        sharedPrefsRepository.saveToFile(array)
    }
}
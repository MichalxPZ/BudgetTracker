package pl.poznan.put.michalxpz.budgettracker.controllers

import com.jfoenix.controls.JFXToggleButton
import javafx.fxml.FXML
import javafx.scene.control.DialogPane
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
    private lateinit var darkModeToggle: JFXToggleButton

    @FXML
    private lateinit var targetAmountTextField: TextField

    @FXML
    private lateinit var surnameTextField: TextField

    @FXML
    private lateinit var nameTextField: TextField

    @FXML
    private lateinit var dialogPane: DialogPane

    fun validateApplicationData(): String {
        var validationStatus = "OK"
        try {
            val nameValidator = NameFieldValidator()
            val name = nameValidator.parseName(nameTextField.text)

            val surnameValidator = SurnameValidator()
            val surname = surnameValidator.parseSurname(surnameTextField.text)

            val targetAmountFieldValidator = TargetAmountFieldValidator()
            val targetAmount = targetAmountFieldValidator.parseValue(targetAmountTextField.text)
            val isDarkMode = darkModeToggle.isSelected

            return validationStatus
        } catch (exception: BudgetTrackerExceptions) {
            println(exception)
            validationStatus = exception.toString()
            return validationStatus
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
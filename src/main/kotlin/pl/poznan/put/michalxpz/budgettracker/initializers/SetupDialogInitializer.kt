package pl.poznan.put.michalxpz.budgettracker.initializers

import javafx.fxml.FXMLLoader
import javafx.scene.control.ButtonType
import javafx.scene.control.Dialog
import javafx.scene.control.DialogPane
import pl.poznan.put.michalxpz.budgettracker.App
import pl.poznan.put.michalxpz.budgettracker.controllers.StartupDialogController
import pl.poznan.put.michalxpz.budgettracker.sharedPrefs.SharedPrefs
import java.util.*

open class SetupDialogInitializer {
    fun openSetupDialog() {
        val fxmlLoader = FXMLLoader(App::class.java.getResource("startupDialog.fxml"))
        val dialogPane = fxmlLoader.load<DialogPane>()
        val dialogController = fxmlLoader.getController<StartupDialogController>()
        val dialog = Dialog<ButtonType>()
        dialog.dialogPane = dialogPane
        dialog.title = dialogPane.headerText
        dialogController.nameTextField.text = if (SharedPrefs.applicationData.name != "name") SharedPrefs.applicationData.name else ""
        dialogController.surnameTextField.text = if (SharedPrefs.applicationData.surname != "surname") SharedPrefs.applicationData.surname else ""
        dialogController.targetAmountTextField.text =if (SharedPrefs.applicationData.targetAmount != 0.0) SharedPrefs.applicationData.targetAmount.toString() else ""
        dialogController.darkModeToggle.isSelected = SharedPrefs.applicationData.isDarkMode
        val clickedButton: Optional<ButtonType> = dialog.showAndWait()
        handleButtonStatus(clickedButton, dialogController, dialog)
    }

    fun handleButtonStatus(
        clickedButton: Optional<ButtonType>,
        dialogController: StartupDialogController,
        dialog: Dialog<ButtonType>
    ) {
        if (clickedButton.get() == ButtonType.OK) {
            var status = dialogController.validateApplicationData()
            while (status != "OK") {
                dialog.headerText = status
                dialog.showAndWait()
                status = dialogController.validateApplicationData()
            }
            dialogController.setApplicationData()
        }
    }
}
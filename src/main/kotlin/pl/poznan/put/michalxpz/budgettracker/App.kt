package pl.poznan.put.michalxpz.budgettracker

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.control.ButtonType
import javafx.scene.control.Dialog
import javafx.scene.control.DialogPane
import javafx.stage.Stage
import pl.poznan.put.michalxpz.budgettracker.controllers.StartupDialogController
import pl.poznan.put.michalxpz.budgettracker.data.ApplicationData
import pl.poznan.put.michalxpz.budgettracker.sharedPrefs.SharedPrefs
import pl.poznan.put.michalxpz.budgettracker.sharedPrefs.SharedPrefsRepository
import java.util.*

class App : Application() {
    override fun start(stage: Stage) {
        val fxmlLoader = FXMLLoader(App::class.java.getResource("home.fxml"))
        val scene = Scene(fxmlLoader.load(), 600.0, 600.0)
        stage.title = "BudgetTracker"
        stage.scene = scene
        stage.show()
        loadSharedPrefs()
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
}

fun main() {
    Application.launch(App::class.java)

}
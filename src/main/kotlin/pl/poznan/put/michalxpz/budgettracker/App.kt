package pl.poznan.put.michalxpz.budgettracker

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage
import pl.poznan.put.michalxpz.budgettracker.data.ApplicationData
import pl.poznan.put.michalxpz.budgettracker.initializers.SetupDialogInitializer
import pl.poznan.put.michalxpz.budgettracker.sharedPrefs.SharedPrefs
import pl.poznan.put.michalxpz.budgettracker.sharedPrefs.SharedPrefsRepository

class App : Application() {
    override fun start(stage: Stage) {
        loadSharedPrefs()
        val fxmlLoader = FXMLLoader(App::class.java.getResource("home.fxml"))
        val scene = Scene(fxmlLoader.load(), 800.0, 600.0)
        val css = if (!SharedPrefs.applicationData.isDarkMode) {
            App::class.java.getResource("applicationLightColors.css")!!.toExternalForm()
        } else {
            App::class.java.getResource("applicationDarkColors.css")!!.toExternalForm()
        }
        scene.stylesheets.add(css)
        setupStage(stage, scene)
        stage.show()
    }

    private fun loadSharedPrefs() {
        val sharedPrefsRepository = SharedPrefsRepository()
        val appDataList = sharedPrefsRepository.readFromFile()
        var appData: ApplicationData? = null
        if (appDataList?.size!! > 0) {
            appData = appDataList.get(0)
        }
        if (appData != null) {
            SharedPrefs.applicationData = appData
        } else {
            openSetupDialog()
        }
    }

    private fun openSetupDialog() {
        val setupDialogInitializer = SetupDialogInitializer()
        setupDialogInitializer.openSetupDialog()
    }

    private fun setupStage(stage: Stage, scene: Scene) {
        stage.title = "BudgetTracker"
        stage.scene = scene
        stage.minHeight = 600.0
        stage.minWidth = 800.0
        stage.maxHeight = 600.0
        stage.maxWidth = 800.0
    }
}

fun main() {
    Application.launch(App::class.java)

}
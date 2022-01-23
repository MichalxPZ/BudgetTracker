package pl.poznan.put.michalxpz.budgettracker.controllers

import com.jfoenix.controls.JFXButton
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.Node
import javafx.scene.Scene
import javafx.stage.Stage
import pl.poznan.put.michalxpz.budgettracker.App
import pl.poznan.put.michalxpz.budgettracker.sharedPrefs.SharedPrefs
import java.net.URL
import java.util.*

class DrawerController : Initializable{

    @FXML
    private lateinit var expensesButton: JFXButton

    @FXML
    private lateinit var incomesButton: JFXButton

    @FXML
    private lateinit var analysisButton: JFXButton

    @FXML
    private lateinit var homeButton: JFXButton

    override fun initialize(p0: URL?, p1: ResourceBundle?) {
        setDrawerNavigationEvents()
    }


    private fun setDrawerNavigationEvents() {
        analysisButton.addEventHandler(ActionEvent.ACTION) { actionEvent ->
            val stage = (actionEvent.source as Node).scene.window as Stage
            setLoaderPath(stage, "analysis.fxml")
        }

        expensesButton.addEventHandler(ActionEvent.ACTION) { actionEvent ->
            val stage = (actionEvent.source as Node).scene.window as Stage
            setLoaderPath(stage, "expenses.fxml")
        }

        incomesButton.addEventHandler(ActionEvent.ACTION) { actionEvent ->
            val stage = (actionEvent.source as Node).scene.window as Stage
            setLoaderPath(stage, "incomes.fxml")
        }

        homeButton.addEventHandler(ActionEvent.ACTION) { actionEvent ->
            val stage = (actionEvent.source as Node).scene.window as Stage
            setLoaderPath(stage, "home.fxml")

        }
    }

    private fun setLoaderPath(stage: Stage, path: String) {
        val fxmlLoader = FXMLLoader(App::class.java.getResource(path))
        val scene = Scene(fxmlLoader.load(), 800.0, 600.0)
        stage.title = path.substringBefore(".")
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
        stage.scene = scene
        setStageSize(stage, 800.0, 600.0)
        setStageCss(stage)
        stage.show()
    }

    private fun setStageCss(stage: Stage,) {
        val css = if (!SharedPrefs.applicationData.isDarkMode) {
            App::class.java.getResource("applicationLightColors.css")!!.toExternalForm()
        } else {
            App::class.java.getResource("applicationDarkColors.css")!!.toExternalForm()
        }
        stage.scene.stylesheets.add(css)
    }

    private fun setStageSize(stage: Stage, x: Double = 800.0, y: Double = 600.0) {
        stage.minHeight =y
        stage.minWidth = x
        stage.maxHeight = y
        stage.maxWidth = x
    }
}
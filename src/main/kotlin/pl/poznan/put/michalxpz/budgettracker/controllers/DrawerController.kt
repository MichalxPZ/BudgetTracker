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
import java.net.URL
import java.util.*

class DrawerController : Initializable{

    @FXML
    lateinit var expensesButton: JFXButton

    @FXML
    lateinit var incomesButton: JFXButton

    @FXML
    lateinit var analysisButton: JFXButton

    @FXML
    lateinit var homeButton: JFXButton

    override fun initialize(p0: URL?, p1: ResourceBundle?) {
        setDrawerNavigationEvents()
    }


    fun setDrawerNavigationEvents() {
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

    fun setLoaderPath(stage: Stage, path: String) {
        val fxmlLoader = FXMLLoader(App::class.java.getResource(path))
        val scene = Scene(fxmlLoader.load(), 600.0, 600.0)
        stage.title = path.substringBefore(".")
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
        stage.scene = scene
        stage.show()
    }
}
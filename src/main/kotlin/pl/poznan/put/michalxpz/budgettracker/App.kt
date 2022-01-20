package pl.poznan.put.michalxpz.budgettracker

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage

class App : Application() {
    override fun start(stage: Stage) {
        val fxmlLoader = FXMLLoader(App::class.java.getResource("home.fxml"))
        val scene = Scene(fxmlLoader.load(), 600.0, 600.0)
        stage.title = "BudgetTracker"
        stage.scene = scene
        stage.show()
    }
}

fun main() {
    Application.launch(App::class.java)

}
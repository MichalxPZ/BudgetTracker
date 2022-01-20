package pl.poznan.put.michalxpz.budgettracker.controllers

import com.jfoenix.controls.JFXDrawer
import com.jfoenix.controls.JFXHamburger
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.input.MouseEvent
import javafx.scene.layout.VBox
import pl.poznan.put.michalxpz.budgettracker.App
import java.io.IOException
import java.net.URL
import java.util.*
import java.util.logging.Level
import java.util.logging.Logger

class AnalysisScreenController : Initializable {
    @FXML
    lateinit var drawerMenu: JFXDrawer

    @FXML
    lateinit var burgerButton: JFXHamburger

    override fun initialize(url: URL?, resourceBundle: ResourceBundle?) {
        setupDrawerMenu()

    }

    private fun setupDrawerMenu() {
        try {
            val fxmlLoader = FXMLLoader(App::class.java.getResource("sideDrawer.fxml"))
            val box: VBox = fxmlLoader.load()
            drawerMenu.setSidePane(box)

            val hamburgerTransition = HamburgerBackArrowBasicTransition(burgerButton)
            hamburgerTransition.rate = -1.0
            burgerButton.addEventHandler(
                MouseEvent.MOUSE_PRESSED
            ) {
                hamburgerTransition.rate *= -1
                hamburgerTransition.play()
                if (drawerMenu.isOpened) drawerMenu.close() else drawerMenu.open()

            }

        } catch (exception: IOException) {
            Logger.getLogger(AppController::class.java.name).log(Level.SEVERE, null, exception)
        }
    }

}
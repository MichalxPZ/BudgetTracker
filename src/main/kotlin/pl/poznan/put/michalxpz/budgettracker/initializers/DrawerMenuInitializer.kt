package pl.poznan.put.michalxpz.budgettracker.initializers

import com.jfoenix.controls.JFXDrawer
import com.jfoenix.controls.JFXHamburger
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition
import javafx.fxml.FXMLLoader
import javafx.scene.input.MouseEvent
import javafx.scene.layout.VBox
import pl.poznan.put.michalxpz.budgettracker.App
import pl.poznan.put.michalxpz.budgettracker.controllers.AppController
import java.io.IOException
import java.util.logging.Level
import java.util.logging.Logger

class DrawerMenuInitializer(
    private val drawerMenu: JFXDrawer,
    private val burgerButton: JFXHamburger
) {
    fun setupDrawerMenu() {
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
                if (drawerMenu.isOpened) {
                    drawerMenu.close()
                    drawerMenu.toBack()
                } else {
                    drawerMenu.open()
                    drawerMenu.toFront()
                }
            }
        } catch (exception: IOException) {
            Logger.getLogger(AppController::class.java.name).log(Level.SEVERE, null, exception)
        }
    }

}
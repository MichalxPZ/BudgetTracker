module pl.poznan.put.michalxpz.budgettracker {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires kotlin.stdlib;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires com.jfoenix;
    requires java.logging;

    opens pl.poznan.put.michalxpz.budgettracker to javafx.fxml;
    opens pl.poznan.put.michalxpz.budgettracker.controllers to javafx.fxml;
    opens pl.poznan.put.michalxpz.budgettracker.data to javafx.base;

    exports pl.poznan.put.michalxpz.budgettracker;

}
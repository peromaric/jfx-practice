module hr.tvz.jfxpractice {
    requires javafx.controls;
    requires javafx.fxml;


    opens hr.tvz.dev to javafx.fxml;
    exports hr.tvz.dev;
    exports hr.tvz.dev.controllers;
    opens hr.tvz.dev.controllers to javafx.fxml;
}
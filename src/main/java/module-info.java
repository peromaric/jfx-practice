module hr.tvz.jfxpractice {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires ch.qos.logback.classic;
    requires org.slf4j;


    opens hr.tvz.dev to javafx.fxml;
    exports hr.tvz.dev;
    exports hr.tvz.dev.controllers;
    opens hr.tvz.dev.controllers to javafx.fxml;
}
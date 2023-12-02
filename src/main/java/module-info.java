module hr.tvz.jfxpractice {
    requires javafx.controls;
    requires javafx.fxml;


    opens hr.tvz.jfxpractice to javafx.fxml;
    exports hr.tvz.jfxpractice;
}
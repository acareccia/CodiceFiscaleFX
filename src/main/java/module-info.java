module codiceFiscaleFx {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;

    opens com.ilfalsodemetrio.utils.ui.fx to javafx.fxml;
    exports com.ilfalsodemetrio.utils.ui.fx;
}

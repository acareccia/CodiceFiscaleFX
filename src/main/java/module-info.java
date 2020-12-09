module codiceFiscaleFx {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires lombok;

    opens com.ilfalsodemetrio.utils.ui.fx to javafx.fxml;
    exports com.ilfalsodemetrio.utils.ui.fx;
}

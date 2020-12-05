package com.ilfalsodemetrio.utils.ui.fx;

import com.ilfalsodemetrio.utils.ui.fx.AlertHelper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Window;

public class Controller {

    @FXML
    private TextField nameField;

    @FXML
    public TextField surnameField;

    @FXML
    private ComboBox<String> sexBox;

    @FXML
    private Button submitButton;

    @FXML
    private ListView<String> codesList;


    public void initialize() {
        System.out.println("init");
        sexBox.setItems(FXCollections.observableArrayList("M",  "F"));
        codesList.setItems(FXCollections.observableArrayList("ABCD",  "EFGH"));

    }

    public void handleSubmitButtonAction(ActionEvent actionEvent) {
        Window owner = submitButton.getScene().getWindow();
        if(nameField.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Error", "Please enter your name");
            return;
        }
    }
}

package com.ilfalsodemetrio.utils.ui.fx;

import com.ilfalsodemetrio.utils.entity.Localita;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Window;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private TextField nameField;

    @FXML
    public TextField surnameField;

    @FXML
    private ComboBox<String> sexBox;

    @FXML
    private ComboBox<String> birhDateLocations;

    @FXML
    private Button submitButton;

    @FXML
    private ListView<String> codesList;

    @FXML
    private VBox codePane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("init");

        sexBox.setItems(FXCollections.observableArrayList(
                "M",  "F"
        ));
        birhDateLocations.setItems(FXCollections.observableArrayList(
                "MILANO", "ROMA","SVIZZERA"
        ));

        codesList.setItems(FXCollections.observableArrayList(""));
    }

    public void handleSubmitButtonAction(ActionEvent actionEvent) {
        Window owner = submitButton.getScene().getWindow();
        if(nameField.getText().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Error", "Please enter your name");
            return;
        }

        //sample
        codesList.setItems(FXCollections.observableArrayList("ABCD",  "EFGH"));
        codesList.getItems().add(nameField.getText());
        codesList.getItems().add(surnameField.getText());

        //AlertHelper.showAlert(Alert.AlertType.CONFIRMATION, owner, "Info", nameField.getText());

    }
}

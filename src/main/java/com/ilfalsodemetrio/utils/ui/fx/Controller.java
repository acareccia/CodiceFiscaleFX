package com.ilfalsodemetrio.utils.ui.fx;

import com.ilfalsodemetrio.utils.CodiceFiscaleChecker;
import com.ilfalsodemetrio.utils.entity.Localita;
import com.ilfalsodemetrio.utils.entity.Persona;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Window;

import javax.swing.event.ChangeListener;
import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    public TextField surnameField;

    @FXML
    private TextField nameField;
    @FXML
    private ChoiceBox<String> sexBox;

    @FXML
    private DatePicker birhDatePicker;

    @FXML
    private ComboBox<String> birhDateLocation;

    @FXML
    private Button submitButton;

    @FXML
    private ListView<String> codesList;

    private static TextFormatter.Change formatUpper(TextFormatter.Change change) {
        change.setText(change.getText().toUpperCase());
        return change;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("init");

        sexBox.setItems(FXCollections.observableArrayList(
                "M", "F"
        ));

        birhDateLocation.setItems(FXCollections.observableArrayList(
                "F205", "ROMA", "SVIZZERA"
        ));

        nameField.setTextFormatter(new TextFormatter<>(Controller::formatUpper));
        surnameField.setTextFormatter(new TextFormatter<>(Controller::formatUpper));

        codesList.getItems().add("");

    }


    public void handleSubmitButtonAction(ActionEvent actionEvent) {
        Window owner = submitButton.getScene().getWindow();
        if (nameField.getText().isEmpty() ||
                surnameField.getText().isEmpty() ||
                sexBox.getValue().isEmpty() ||
                birhDatePicker.getValue() == null ||
                birhDateLocation.getValue().isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Error", "Dati incompleti");
            return;
        }

        //todo: binding?
        Persona persona = new Persona();
        persona.setNome(nameField.getText());
        persona.setCognome(surnameField.getText());
        persona.setSesso(sexBox.getValue());
        persona.setDataNascita(Date.valueOf(birhDatePicker.getValue()));
        persona.setLocalita(new Localita(birhDateLocation.getValue()));

        List<String> codes = CodiceFiscaleChecker.getAllValidCodiciFiscali(persona);

        codesList.getItems().clear();
        codesList.getItems().addAll(codes);

        //AlertHelper.showAlert(Alert.AlertType.INFORMATION, owner, "Info","Codice Fiscale : "+ codes.get(0));

    }
}

package com.ilfalsodemetrio.utils.ui.fx;

import com.ilfalsodemetrio.utils.CodiceFiscaleChecker;
import com.ilfalsodemetrio.utils.entity.Localita;
import com.ilfalsodemetrio.utils.entity.Persona;
import com.ilfalsodemetrio.utils.loaders.LocalitaLoader;
import com.ilfalsodemetrio.utils.loaders.PersonaLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Window;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private TextField nameField;
    @FXML
    public TextField surnameField;
    @FXML
    private ChoiceBox<String> sexBox;
    @FXML
    private DatePicker birhDatePicker;
    @FXML
    private ComboBox<Localita> birhDateLocation;

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
        // formatters
        nameField.setTextFormatter(new TextFormatter<>(Controller::formatUpper));
        surnameField.setTextFormatter(new TextFormatter<>(Controller::formatUpper));

        // sex
        sexBox.setItems(FXCollections.observableArrayList(
                "M", "F"
        ));
        sexBox.getSelectionModel().selectFirst();

        // birthLocations
        ObservableList<Localita> list = FXCollections.observableList(LocalitaLoader.loader());

        birhDateLocation.setItems(list);
        birhDateLocation.getSelectionModel().selectFirst();

        // list
        codesList.getItems().add("");
    }


    public void handleSubmitButtonAction(ActionEvent actionEvent) {
        Window owner = submitButton.getScene().getWindow();
        if (nameField.getText().isEmpty() ||
                surnameField.getText().isEmpty() ||
                sexBox.getValue().isEmpty() ||
                birhDatePicker.getValue() == null ||
                birhDateLocation.getValue() == null) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Error", "Dati incompleti");
            return;
        }

        //todo: binding?
        Persona persona = new Persona();
        persona.setNome(nameField.getText());
        persona.setCognome(surnameField.getText());
        persona.setSesso(sexBox.getValue());
        persona.setDataNascita(Date.valueOf(birhDatePicker.getValue()));
        persona.setLocalita(birhDateLocation.getValue());

        List<String> codes = CodiceFiscaleChecker.getAllValidCodiciFiscali(persona);

        codesList.getItems().clear();
        codesList.getItems().addAll(codes);

        //AlertHelper.showAlert(Alert.AlertType.INFORMATION, owner, "Info","Codice Fiscale : "+ codes.get(0));

    }

    public void handleRandomButtonAction(ActionEvent actionEvent) {
        Persona persona = PersonaLoader.rand();
        System.out.println(persona);

        nameField.setText(persona.getNome());
        surnameField.setText(persona.getCognome());
        sexBox.setValue(persona.getSesso());
        birhDatePicker.setValue(LocalDate.from(
                persona.getDataNascita().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()));
        birhDateLocation.setValue(persona.getLocalita());

        List<String> codes = CodiceFiscaleChecker.getAllValidCodiciFiscali(persona);
        codesList.getItems().clear();
        codesList.getItems().addAll(codes);
    }

    public void handleResetButtonAction(ActionEvent actionEvent) {
        nameField.setText(null);
        surnameField.setText(null);
        sexBox.getSelectionModel().selectFirst();;
        birhDatePicker.setValue(null);
        birhDateLocation.getSelectionModel().selectFirst();;

        codesList.getItems().clear();
    }
}

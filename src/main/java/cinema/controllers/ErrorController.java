package cinema.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ErrorController implements Initializable {
    @FXML
    private Button ButtonOk;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void ButtonOkOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) ButtonOk.getScene().getWindow();
        stage.close();

    }
}

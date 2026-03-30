package cinema.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class AccueilController extends MenuController implements Initializable {

    @FXML
    private Label bienvenue;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setBienvenue() {
        bienvenue.setText("BONJOUR " + nameUti.toUpperCase());
    }

}

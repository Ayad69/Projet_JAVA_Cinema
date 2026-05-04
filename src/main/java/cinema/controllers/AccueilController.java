package cinema.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

// Page d'accueil après la connexion : menu + message "Bonjour ...".
public class AccueilController extends MenuController implements Initializable {
    @FXML
    public Label labelError;
    @FXML
    private Label bienvenue;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    // Met à jour le texte du label du haut avec le prénom/nom de l'utilisateur.
    public void setBienvenue() {
        if (nameUti == null || nameUti.trim().isEmpty()) {
            bienvenue.setText("BONJOUR");
            return;
        }
        bienvenue.setText("BONJOUR " + nameUti.toUpperCase());
    }

}

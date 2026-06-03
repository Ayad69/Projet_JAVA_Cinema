package cinema.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import cinema.BO.Utilisateur;
import cinema.DAO.UtilisateurDAO;
import cinema.Session;
import cinema.util.WindowIcons;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

// Page où l'utilisateur tape login + mot de passe avant d'entrer dans l'appli.
public class ConnexionController implements Initializable {

    // Message rouge si le login est faux (par exemple).
    public Label messageError;

    @Override
    // Au chargement de la page : pour l'instant on ne fait rien de spécial.
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private TextField tfLogin;
    @FXML
    private PasswordField tfMDP;
    @FXML
    private Button bConnexion;

    @FXML
    // Quand on clique sur "Connexion" : on teste login/mdp puis on va à l'accueil si c'est bon.
    public void bConnexionClick(ActionEvent event) {
       String login = tfLogin.getText();
        String mdp = tfMDP.getText();

        UtilisateurDAO userDAO = new UtilisateurDAO();
        Utilisateur user = userDAO.authenticate(login, mdp);
        if (user != null) {
            // On garde tout l'objet utilisateur pour les logs (id + login).
            Session.setUtilisateur(user);
            showAccueil(user.getNom());
        }else{
            messageError.setText("Login ou MDP incorrecte");
        }
    }

    // Ouvre la fenêtre d'accueil et donne le nom à afficher ("Bonjour ...").
    private void showAccueil(String name) {
        Stage stageP = (Stage) bConnexion.getScene().getWindow();
        // on ferme l'écran
        stageP.close();
        try {

            // Charger le fichier FXML pour la pop-up
            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getResource("/cinema/views/page_accueil.fxml"));
            Parent root = fxmlLoader.load();

            // Obtenir le contrôleur de la nouvelle fenetre
            AccueilController accueilController = fxmlLoader.getController();
            accueilController.setName(name);
            accueilController.setBienvenue();

            // Créer une nouvelle fenêtre (Stage)
            Stage stage = new Stage();
            WindowIcons.apply(stage);
            stage.setTitle("Accueil Gestion de franchises");
            stage.setScene(new Scene(root));
            // Configurer la fenêtre en tant que modal
            stage.initModality(Modality.APPLICATION_MODAL);
            // ne pas pouvoir agrandir la taille de la page
            stage.setResizable(false);

            // Afficher la fenêtre et attendre qu'elle se ferme
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    // Petite fenêtre d'erreur (si tu branches un bouton dessus dans le FXML).
    private void showError() {

        try {
            // Charger le fichier FXML pour la pop-up
            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getResource("/cinema/views/ErreurConnexion.fxml"));
            Parent root = fxmlLoader.load();

            // Obtenir le contrôleur de la pop-up
            ErrorController errorController = fxmlLoader.getController();

            // Passer la variable au contrôleur de la pop-up
            // errorController.setMajLabel(Integer.toString(compteur));

            // Créer une nouvelle fenêtre (Stage)
            Stage stage = new Stage();
            WindowIcons.apply(stage);
            stage.setTitle("Error Window");
            stage.setScene(new Scene(root));
            // ne pas pouvoir agrandir la taille de la page
            stage.setResizable(false);

            // Configurer la fenêtre en tant que modal
            stage.initModality(Modality.APPLICATION_MODAL);

            // Afficher la fenêtre et attendre qu'elle se ferme
            stage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

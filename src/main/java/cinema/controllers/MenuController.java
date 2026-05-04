package cinema.controllers;

import cinema.Session;
import cinema.util.WindowIcons;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.stage.Modality;
import javafx.stage.Stage;

// Classe "parent" pour les pages qui ont le menu du haut (franchises, cinémas, salles...).
// Toutes les actions du menu sont ici : ouvrir une autre page, fermer la fenêtre actuelle, etc.
public class MenuController {

    @FXML
    // Liens du menu (définis dans le fichier FXML).
    protected MenuItem bListeFranchise, bAjouterFranchise, bListeCinema, bAjouterCinema, bQuitter, bAccueil,
            bListeSalle,
            bAjouterSalle;

    // Le login de la personne connectée (on le repasse d'écran en écran avec setName).
    protected String nameUti;
    @FXML
    private Label labelError;

    @FXML
    // Quitte complètement l'application.
    public void bQuitterClick(ActionEvent event) {
        Session.clear();
        Platform.exit();
    }

    @FXML
    // Retour à l'écran d'accueil après connexion.
    public void bAccueilClick(ActionEvent event) {
        Stage stageP = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();
        stageP.close();
        try {

            // Charger le fichier FXML
            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getResource("/cinema/views/page_accueil.fxml"));
            Parent root = fxmlLoader.load();

            // Obtenir le contrôleur de la nouvelle fenetre
            AccueilController accueilController = fxmlLoader.getController();
            accueilController.setName(nameUti);
            accueilController.setBienvenue();

            // Créer une nouvelle fenêtre (Stage)
            Stage stage = new Stage();
            WindowIcons.apply(stage);
            stage.setTitle("Accueil Gestion de franchises");
            stage.setScene(new Scene(root));

            // Configurer la fenêtre en tant que modal
            stage.initModality(Modality.APPLICATION_MODAL);

            // Afficher la fenêtre et attendre qu'elle se ferme
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            stageP.show();
        }
    }

    @FXML
    // Ouvre la liste des franchises.
    public void bListFranchiseClick(ActionEvent event) {
        Stage stageP = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();
        stageP.close();
        try {

            // Charger le fichier FXML
            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getResource("/cinema/views/page_liste_franchise.fxml"));
            Parent root = fxmlLoader.load();

            // Obtenir le contrôleur de la nouvelle fenetre
            ListeFranchiseController listeFranchiseController = fxmlLoader.getController();
            listeFranchiseController.setName(nameUti);

            // Créer une nouvelle fenêtre (Stage)
            Stage stage = new Stage();
            WindowIcons.apply(stage);
            stage.setTitle("Liste franchises");
            stage.setScene(new Scene(root));

            // Configurer la fenêtre en tant que modal
            stage.initModality(Modality.APPLICATION_MODAL);

            // Afficher la fenêtre et attendre qu'elle se ferme
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            stageP.show();
            labelError.setText("Page en maintenance..");
        }
    }

    @FXML
    // Ouvre le formulaire pour ajouter une franchise.
    public void bAjouterFranchiseClick(ActionEvent event) {
        Stage stageP = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();
        stageP.close();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getResource("/cinema/views/page_ajout_franchise.fxml"));
            Parent root = fxmlLoader.load();

            // Obtenir le contrôleur de la nouvelle fenetre
            AjouterFranchiseController ajouterFranchiseController = fxmlLoader.getController();
            ajouterFranchiseController.setName(nameUti);

            // Créer une nouvelle fenêtre (Stage)
            Stage stage = new Stage();
            WindowIcons.apply(stage);
            stage.setTitle("Ajouter une franchise");
            stage.setScene(new Scene(root));

            // Configurer la fenêtre en tant que modal
            stage.initModality(Modality.APPLICATION_MODAL);

            // Afficher la fenêtre et attendre qu'elle se ferme
            stage.show();
        } catch (Exception e) {
        e.printStackTrace();
        }

    }

    @FXML
    // Ouvre la liste des cinémas.
    public void bListeCinemaClick(ActionEvent event) {
        Stage stageP = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();
        stageP.close();
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getResource("/cinema/views/page_liste_cinema.fxml"));
            Parent root = fxmlLoader.load();

            ListeCinemaController listeCinemaController = fxmlLoader.getController();
            listeCinemaController.setName(nameUti);

            Stage stage = new Stage();
            WindowIcons.apply(stage);
            stage.setTitle("Liste cinéma");
            stage.setScene(new Scene(root));

            stage.initModality(Modality.APPLICATION_MODAL);

            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            stageP.show();
            labelError.setText("La page est en maintenance..");
        }
    }

    @FXML
    // Ouvre le formulaire d'ajout d'un cinéma.
    public void bAjouterCinemaClick(ActionEvent event) {
        Stage stageP = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();
        stageP.close();
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getResource("/cinema/views/page_ajout_cinema.fxml"));
            Parent root = fxmlLoader.load();

            AjoutCinemaController ajoutCinemaController = fxmlLoader.getController();
            ajoutCinemaController.setName(nameUti);

            Stage stage = new Stage();
            WindowIcons.apply(stage);
            stage.setTitle("Ajout d'un cinéma");
            stage.setScene(new Scene(root));

            stage.initModality(Modality.APPLICATION_MODAL);

            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            stageP.show();
            labelError.setText("La page est en maintenance..");
        }
    }

    @FXML
    // Ouvre la liste des salles.
    public void bListeSalleClick(ActionEvent event) {
        Stage stageP = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();
        stageP.close();
        try {

            // Charger le fichier FXML
            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getResource("/cinema/views/page_liste_salles.fxml"));
            Parent root = fxmlLoader.load();

            ListeSalleController listeSalleController = fxmlLoader.getController();
            listeSalleController.setName(nameUti);

            // Créer une nouvelle fenêtre (Stage)
            Stage stage = new Stage();
            WindowIcons.apply(stage);
            stage.setTitle("Liste salles");
            stage.setScene(new Scene(root));

            // Configurer la fenêtre en tant que modal
            stage.initModality(Modality.APPLICATION_MODAL);

            // Afficher la fenêtre et attendre qu'elle se ferme
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            stageP.show();
            labelError.setText("La page est en maintenance..");
        }
    }

    // Pour que les sous-contrôleurs sachent quel utilisateur est connecté.
    public void setName(String nameUti) {
        this.nameUti = nameUti;
    }

    @FXML
    // Ouvre le formulaire d'ajout d'une salle.
    public void bAjouterSalleClick(ActionEvent event) {
        Stage stageP = (Stage) ((MenuItem) event.getSource()).getParentPopup().getOwnerWindow();
        stageP.close();
        try {

            // Charger le fichier FXML
            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getResource("/cinema/views/page_ajout_salles.fxml"));
            Parent root = fxmlLoader.load();

            AjoutSalleController ajoutSalleController = fxmlLoader.getController();
            ajoutSalleController.setName(nameUti);

            // Créer une nouvelle fenêtre (Stage)
            Stage stage = new Stage();
            WindowIcons.apply(stage);
            stage.setTitle("Ajout d'une salle");
            stage.setScene(new Scene(root));

            // Configurer la fenêtre en tant que modal
            stage.initModality(Modality.APPLICATION_MODAL);

            // Afficher la fenêtre et attendre qu'elle se ferme
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            stageP.show();
            labelError.setText("La page est en maintenance..");
        }
    }
}

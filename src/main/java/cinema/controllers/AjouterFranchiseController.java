package cinema.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import cinema.BO.Franchise;
import cinema.BO.Utilisateur;
import cinema.DAO.FranchiseDAO;
import cinema.DAO.UtilisateurDAO;
import cinema.util.WindowIcons;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

// Formulaire "nouvelle franchise" : nom, siège social, choix du gérant (utilisateur).
public class AjouterFranchiseController extends MenuController implements Initializable {

    @FXML
    private Label message;
    @FXML
    private TextField tfNomFranchise, tfSiegeSocial;
    @FXML
    private Button bRetour;
    @FXML
    private ListView<Utilisateur> lvGerantFranchise;

    @Override
    // Liste des utilisateurs possibles comme "gérant" de la nouvelle franchise.
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<Utilisateur> utilisateurs = getUtilisateurList();

        lvGerantFranchise.setItems(utilisateurs);
        message.setText("");
    }

    private ObservableList<Utilisateur> getUtilisateurList() {

        UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
        List<Utilisateur> utilisateurs = utilisateurDAO.findAll();

        ObservableList<Utilisateur> list = FXCollections.observableArrayList(utilisateurs);
        return list;
    }

    @FXML
    public void bRetourClick(ActionEvent event) {
        // On fait le lien avec l'ecran actuel
        Stage stageP = (Stage) bRetour.getScene().getWindow();
        // on ferme l'écran
        stageP.close();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getResource("/cinema/views/page_accueil.fxml"));
            Parent root = fxmlLoader.load();

            AccueilController accueilController = fxmlLoader.getController();
            accueilController.setName(nameUti);
            accueilController.setBienvenue();

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
        }

    }

    @FXML
    public void bEnregistrerClick(ActionEvent event) {

        String nomFranchise = tfNomFranchise.getText();
        String siegeSocial = tfSiegeSocial.getText();
        Utilisateur utilisateur = lvGerantFranchise.getSelectionModel().getSelectedItem();

        if (nomFranchise == null || nomFranchise.trim().isEmpty()
                || siegeSocial == null || siegeSocial.trim().isEmpty()
                || utilisateur == null) {
            message.setText("Veuillez remplir toutes les cases et choisir un gérant.");
            message.setTextFill(Color.RED);
            return;
        }

        Franchise franchise = new Franchise(0, nomFranchise.trim(), siegeSocial.trim(),
                utilisateur.getIdUtilisateur());

        FranchiseDAO franchiseDAO = new FranchiseDAO();
        boolean controle = franchiseDAO.create(franchise);
        if (controle) {
            tfNomFranchise.clear();
            tfSiegeSocial.clear();
            lvGerantFranchise.getSelectionModel().clearSelection();
            message.setText("Franchise enregistrée avec succès.");
            message.setTextFill(Color.GREEN);
        } else {
            message.setText("L'enregistrement a échoué.");
            message.setTextFill(Color.RED);
        }

    }

    @FXML
    public void bEffacerClick(ActionEvent event) {
        if (tfNomFranchise != null)
            tfNomFranchise.clear();
        if (tfSiegeSocial != null)
            tfSiegeSocial.clear();
        lvGerantFranchise.getSelectionModel().clearSelection();
        message.setText("");
    }

}

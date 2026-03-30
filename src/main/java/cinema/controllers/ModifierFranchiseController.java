package cinema.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import cinema.BO.Franchise;
import cinema.BO.Utilisateur;
import cinema.DAO.FranchiseDAO;
import cinema.DAO.UtilisateurDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ModifierFranchiseController extends MenuController implements Initializable {

    @FXML
    private TextField tfNomFranchise, tfSiegeSocial;
    @FXML
    private Button bRetour;
    @FXML
    private ListView<Utilisateur> lvGerantFranchise;

    private int idFranchise;
    private int idGerant;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Utilisateur> utilisateurs = getUtilisateurList();
        lvGerantFranchise.setItems(utilisateurs);
    }

    private ObservableList<Utilisateur> getUtilisateurList() {

        UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
        List<Utilisateur> utilisateurs = utilisateurDAO.findAll();

        ObservableList<Utilisateur> list = FXCollections.observableArrayList(utilisateurs);
        return list;
    }

    public void setAttributes(Franchise franchise) {
        tfNomFranchise.setText(franchise.getNomFranchise());
        tfSiegeSocial.setText(franchise.getSiegeSocial());
        lvGerantFranchise.getSelectionModel().select(franchise.getIdGerant() - 1);
        this.idGerant = franchise.getIdGerant();
        this.idFranchise = franchise.getIdFranchise();
    }

    @FXML
    private void bEnregistrerClick(ActionEvent event) {
        String nom = tfNomFranchise.getText();
        String siegeSocial = tfSiegeSocial.getText();
        Utilisateur selected = lvGerantFranchise.getSelectionModel().getSelectedItem();

        if (nom != null && siegeSocial != null && selected != null && !nom.trim().isEmpty()
                && !siegeSocial.trim().isEmpty()) {
            int idGerant = selected.getIdUtilisateur();
            Franchise newFranchise = new Franchise(this.idFranchise, nom, siegeSocial, idGerant);

            FranchiseDAO franchiseDAO = new FranchiseDAO();
            boolean controle = franchiseDAO.update(newFranchise);
            if (controle) {
                Stage stageP = (Stage) bRetour.getScene().getWindow();
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
        } else {
            try {
                // Charger le fichier FXML
                FXMLLoader fxmlLoader = new FXMLLoader(
                        getClass().getResource("/cinema/views/popup_ajout_etu.fxml"));
                Parent root = fxmlLoader.load();

                // Créer une nouvelle fenêtre (Stage)
                Stage stage = new Stage();
                stage.setTitle("Pop-up");
                stage.setScene(new Scene(root));

                // Configurer la fenêtre en tant que modal
                stage.initModality(Modality.APPLICATION_MODAL);

                // Afficher la fenêtre et attendre qu'elle se ferme
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    @FXML
    private void bRetourClick(ActionEvent event) {
        Stage stageP = (Stage) bRetour.getScene().getWindow();
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
}

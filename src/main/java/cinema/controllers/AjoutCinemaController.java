package cinema.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import cinema.BO.Cinema;
import cinema.BO.Franchise;
import cinema.DAO.CinemaDAO;
import cinema.DAO.FranchiseDAO;
import cinema.util.WindowIcons;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

// Formulaire pour créer un nouveau cinéma (nom, adresse, franchise).
public class AjoutCinemaController extends MenuController implements Initializable {
    public Label message;
    @FXML
    private TextField tfNomFranchise, tfSiegeSocial;
    @FXML
    private Button bRetour;
    @FXML
    private ListView<Franchise> lvGerantFranchise;

    @Override
    // Au démarrage de la page : on charge la liste des franchises pour le ListView.
    public void initialize(URL location, ResourceBundle resources) {
        lvGerantFranchise.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Franchise item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getNomFranchise());
            }
        });

        FranchiseDAO franchiseDAO = new FranchiseDAO();
        List<Franchise> franchises = franchiseDAO.findAll();
        if (franchises != null) {
            lvGerantFranchise.setItems(FXCollections.observableArrayList(franchises));
        }
    }

    @FXML
    public void bRetourClick(ActionEvent event) {
        Stage stageP = (Stage) bRetour.getScene().getWindow();
        stageP.close();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getResource("/cinema/views/page_accueil.fxml"));
            Parent root = fxmlLoader.load();

            AccueilController accueilController = fxmlLoader.getController();
            accueilController.setName(nameUti);
            accueilController.setBienvenue();

            Stage stage = new Stage();
            WindowIcons.apply(stage);
            stage.setTitle("Accueil");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            stageP.show();
        }
    }

    @FXML
    public void bEnregistrerClick(ActionEvent event) {
        String denomination = tfNomFranchise.getText();
        String adresse = tfSiegeSocial.getText();
        Franchise franchise = lvGerantFranchise.getSelectionModel().getSelectedItem();

        if (denomination == null || denomination.trim().isEmpty()
                || adresse == null || adresse.trim().isEmpty()
                || franchise == null) {
            message.setText("Veuillez remplir toutes les cases");
            return;
        }

        Cinema cinema = new Cinema( 0,denomination, adresse, adresse, franchise.getIdFranchise());
        CinemaDAO cinemaDAO = new CinemaDAO();
        if (cinemaDAO.create(cinema)) {
            bEffacerClick(event);
            message.setText("Cinema enregsitré avec succés !");

        }

    }

    @FXML
    public void bEffacerClick(ActionEvent event) {
        if (tfNomFranchise != null) {
            tfNomFranchise.clear();
        }
        if (tfSiegeSocial != null) {
            tfSiegeSocial.clear();
        }
        if (lvGerantFranchise != null) {
            lvGerantFranchise.getSelectionModel().clearSelection();
        }
    }
}

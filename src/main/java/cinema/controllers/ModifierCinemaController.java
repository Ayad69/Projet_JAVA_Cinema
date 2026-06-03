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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

// Écran pour changer les infos d'un cinéma déjà existant.
public class ModifierCinemaController extends MenuController implements Initializable {

    @FXML
    private TextField tfNomFranchise, tfSiegeSocial, tfVille,tfFranchise;

    @FXML
    private ListView<Franchise> lvGerantFranchise;

    private Cinema cinemaToEdit;

    @FXML
    private Button bRetour, bEnregistrer;
    FranchiseDAO franchiseDAO = new FranchiseDAO();


    @Override
    // Prépare la liste des franchises (affichage = nom seulement).
    public void initialize(URL location, ResourceBundle resources) {
        lvGerantFranchise.setCellFactory(param -> new javafx.scene.control.ListCell<>() {
            @Override
            protected void updateItem(Franchise item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getNomFranchise());
            }
        });

        List<Franchise> franchises = franchiseDAO.findAll();
        if (franchises != null) {
            lvGerantFranchise.setItems(FXCollections.observableArrayList(franchises));
        }
    }

    @FXML
    private void bRetourClick(ActionEvent event) {
        Stage stageP = (Stage) ((Node) event.getSource()).getScene().getWindow();
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
            // ne pas pouvoir agrandir la taille de la page
            stage.setResizable(false);

            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            stageP.show();
        }
    }

    @FXML
    private void bEnregistrerClick(ActionEvent event) {
        String denomination = tfNomFranchise.getText();
        String adresse = tfSiegeSocial.getText();
        String ville = tfVille.getText();
        Franchise selectedFranchise = lvGerantFranchise.getSelectionModel().getSelectedItem();

        if (cinemaToEdit != null
                && denomination != null && !denomination.trim().isEmpty()
                && adresse != null && !adresse.trim().isEmpty()
                && ville != null && !ville.trim().isEmpty()
                && selectedFranchise != null) {
            Cinema cinemaUpdated = new Cinema(
                    cinemaToEdit.getIdCinema(),
                    denomination,
                    adresse,
                    ville,
                    selectedFranchise.getIdFranchise());
            CinemaDAO cinemaDAO = new CinemaDAO();
            boolean controle = cinemaDAO.update(cinemaUpdated);
            if (controle) {
                Stage stageP = (Stage) bRetour.getScene().getWindow();
                stageP.close();
                try {

                    FXMLLoader fxmlLoader = new FXMLLoader(
                            getClass().getResource("/cinema/views/page_liste_cinema.fxml"));
                    Parent root = fxmlLoader.load();

                    ListeCinemaController listeCinemaController = fxmlLoader.getController();
                    listeCinemaController.setName(nameUti);

                    Stage stage = new Stage();
                    WindowIcons.apply(stage);
                    stage.setTitle("Liste franchises");
                    stage.setScene(new Scene(root));
                    // ne pas pouvoir agrandir la taille de la page
                    stage.setResizable(false);
                    stage.initModality(Modality.APPLICATION_MODAL);

                    stage.show();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void setCinemaToEdit(Cinema cinema) {
        this.cinemaToEdit = cinema;
        if (cinema == null) {
            return;
        }
        tfNomFranchise.setText(cinema.getDenomination());
        tfSiegeSocial.setText(cinema.getAdresse());
        tfVille.setText(cinema.getVille());
        for (Franchise franchise : lvGerantFranchise.getItems()) {
            if (franchise != null && franchise.getIdFranchise() == cinema.getIdFranchise()) {
                lvGerantFranchise.getSelectionModel().select(franchise);
                break;
            }
        }

    }

    public void setName(String nameUti) {
        this.nameUti = nameUti;
    }
}

package cinema.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import cinema.BO.Cinema;
import cinema.BO.Salle;
import cinema.DAO.CinemaDAO;
import cinema.DAO.SalleDAO;
import cinema.util.WindowIcons;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

// Formulaire pour ajouter une salle dans un cinéma (numéro, places, etc.).
public class AjoutSalleController extends MenuController implements Initializable {

    @FXML
    private Label message;
    @FXML
    private TextField tfNumero, tfDescription, tfNbPlaces;
    @FXML
    private Button bRetour;
    @FXML
    private ListView<Cinema> lvCinema;

    @Override
    // On affiche le nom du cinéma dans la liste (pas l'objet bizarre Java).
    public void initialize(URL location, ResourceBundle resources) {
        lvCinema.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Cinema item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getDenomination());
            }
        });

        CinemaDAO cinemaDAO = new CinemaDAO();
        List<Cinema> cinemas = cinemaDAO.findAll();
        lvCinema.setItems(FXCollections.observableArrayList(cinemas));
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
        Cinema cinema = lvCinema.getSelectionModel().getSelectedItem();
        String desc = tfDescription.getText();
        if (cinema == null || desc == null || desc.trim().isEmpty()) {
            if (message != null) {
                message.setText("Veuillez remplir tous les champs et choisir un cinéma.");
            }
            return;
        }
        try {
            int numero = Integer.parseInt(tfNumero.getText().trim());
            int nbPlaces = Integer.parseInt(tfNbPlaces.getText().trim());
            Salle salle = new Salle(0, numero, desc.trim(), nbPlaces, cinema.getIdCinema());
            SalleDAO salleDAO = new SalleDAO();
            if (salleDAO.create(salle)) {
                bEffacerClick(event);
                if (message != null) {
                    message.setText("Salle enregistrée avec succès !");
                }
            } else {
                if (message != null) {
                    message.setText("Enregistrement impossible (vérifiez la base ou les doublons).");
                }
            }
        } catch (NumberFormatException e) {
            if (message != null) {
                message.setText("Numéro et nombre de places doivent être des nombres entiers.");
            }
        }
    }

    @FXML
    public void bEffacerClick(ActionEvent event) {
        if (tfNumero != null) {
            tfNumero.clear();
        }
        if (tfDescription != null) {
            tfDescription.clear();
        }
        if (tfNbPlaces != null) {
            tfNbPlaces.clear();
        }
        if (lvCinema != null) {
            lvCinema.getSelectionModel().clearSelection();
        }
        if (message != null) {
            message.setText("");
        }
    }
}

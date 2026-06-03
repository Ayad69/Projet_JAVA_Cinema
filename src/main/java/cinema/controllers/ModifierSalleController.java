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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

// Écran pour modifier une salle (numéro, description, cinéma...).
public class ModifierSalleController extends MenuController implements Initializable {

    @FXML
    private TextField tfNumero, tfDescription, tfNbPlaces;
    @FXML
    private ListView<Cinema> lvCinema;
    @FXML
    private Button bRetour, bEnregistrer;

    private Salle salleToEdit;

    @Override
    // Charge tous les cinémas pour choisir à quel cinéma rattacher la salle.
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
    private void bRetourClick(ActionEvent event) {
        Stage stageP = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stageP.close();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    getClass().getResource("/cinema/views/page_liste_salles.fxml"));
            Parent root = fxmlLoader.load();
            ListeSalleController listeSalleController = fxmlLoader.getController();
            listeSalleController.setName(nameUti);

            Stage stage = new Stage();
            WindowIcons.apply(stage);
            stage.setTitle("Liste salles");
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
        if (salleToEdit == null) {
            return;
        }
        try {
            int numero = Integer.parseInt(tfNumero.getText().trim());
            int nbPlaces = Integer.parseInt(tfNbPlaces.getText().trim());
            String description = tfDescription.getText();
            Cinema cinema = lvCinema.getSelectionModel().getSelectedItem();
            if (description == null || description.trim().isEmpty() || cinema == null) {
                return;
            }
            Salle updated = new Salle(salleToEdit.getIdSalle(), numero, description.trim(), nbPlaces, cinema.getIdCinema());
            SalleDAO salleDAO = new SalleDAO();
            if (salleDAO.update(updated)) {
                Stage stageP = (Stage) bRetour.getScene().getWindow();
                stageP.close();
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(
                            getClass().getResource("/cinema/views/page_liste_salles.fxml"));
                    Parent root = fxmlLoader.load();
                    ListeSalleController listeSalleController = fxmlLoader.getController();
                    listeSalleController.setName(nameUti);

                    Stage stage = new Stage();
                    WindowIcons.apply(stage);
                    stage.setTitle("Liste salles");
                    stage.setScene(new Scene(root));
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (NumberFormatException ignored) {
            // champs numériques invalides
        }
    }

    public void setSalleToEdit(Salle salle) {
        this.salleToEdit = salle;
        if (salle == null) {
            return;
        }
        tfNumero.setText(String.valueOf(salle.getNumero()));
        tfDescription.setText(salle.getDescription() != null ? salle.getDescription() : "");
        tfNbPlaces.setText(String.valueOf(salle.getNbPlaces()));
        for (Cinema c : lvCinema.getItems()) {
            if (c.getIdCinema() == salle.getIdCinema()) {
                lvCinema.getSelectionModel().select(c);
                break;
            }
        }
    }
}

package cinema.controllers;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import cinema.BO.Cinema;
import cinema.BO.Salle;
import cinema.DAO.CinemaDAO;
import cinema.DAO.SalleDAO;
import cinema.util.WindowIcons;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

// Liste toutes les salles comme la liste des cinémas (tableau + boutons d'action).
public class ListeSalleController extends MenuController implements Initializable {

    @FXML
    private TableView<Salle> tvSalle;
    @FXML
    private TableColumn<Salle, Integer> tcNumero;
    @FXML
    private TableColumn<Salle, String> tcDescription;
    @FXML
    private TableColumn<Salle, Integer> tcNbPlaces;
    @FXML
    private TableColumn<Salle, String> tcCinema;
    @FXML
    private TableColumn<Salle, Void> tcVp, tcModif, tcSupp;
    @FXML
    private Button bRetour;

    @Override
    // Au chargement : on remplit le tableau des salles et on crée les boutons dans les colonnes.
    public void initialize(URL location, ResourceBundle resources) {
        CinemaDAO cinemaDAO = new CinemaDAO();
        List<Cinema> cinemaList = cinemaDAO.findAll();
        Map<Integer, Cinema> cinemas = cinemaList.stream()
                .collect(Collectors.toMap(Cinema::getIdCinema, c -> c));

        tcNumero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        tcDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        tcNbPlaces.setCellValueFactory(new PropertyValueFactory<>("nbPlaces"));
        tcCinema.setCellValueFactory(cellData -> {
            Cinema c = cinemas.get(cellData.getValue().getIdCinema());
            return new SimpleStringProperty(c != null ? c.getDenomination() : "—");
        });

        ObservableList<Salle> data = FXCollections.observableArrayList(new SalleDAO().findAll());
        tvSalle.setItems(data);
        btnVPS(cinemas);
        btnModif();
        btnSupp();
    }

    // Retour vers l'accueil.
    public void bRetourClick(ActionEvent actionEvent) {
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

    // Bouton "Voir plus" : petite fenêtre avec les infos de la salle.
    private void btnVPS(Map<Integer, Cinema> cinemas) {
        tcVp.setCellFactory(column -> new TableCell<Salle, Void>() {
            private final Button btn = new Button("Voir plus");

            {
                btn.setOnAction(event -> {
                    Salle salle = getTableView().getItems().get(getIndex());
                    Cinema c = cinemas.get(salle.getIdCinema());
                    String cinemaNom = c != null ? c.getDenomination() : "—";
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Détails salle");
                    alert.setHeaderText("Salle n°" + salle.getNumero());
                    alert.setContentText(
                            "Description : " + (salle.getDescription() != null ? salle.getDescription() : "") + "\n"
                                    + "Places : " + salle.getNbPlaces() + "\n"
                                    + "Cinéma : " + cinemaNom);
                    alert.showAndWait();
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });
    }

    // Ouvre l'écran de modification pour la salle de la ligne.
    private void btnModif() {
        tcModif.setCellFactory(column -> new TableCell<Salle, Void>() {
            private final Button btn = new Button("Modifier");

            {
                btn.setOnAction(event -> {
                    Salle salle = getTableView().getItems().get(getIndex());
                    Stage stageP = (Stage) bRetour.getScene().getWindow();
                    stageP.close();
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(
                                getClass().getResource("/cinema/views/page_modif_salle.fxml"));
                        Parent root = fxmlLoader.load();
                        ModifierSalleController ctrl = fxmlLoader.getController();
                        ctrl.setName(nameUti);
                        ctrl.setSalleToEdit(salle);
                        Stage stage = new Stage();
                        WindowIcons.apply(stage);
                        stage.setTitle("Modification salle");
                        stage.setScene(new Scene(root));
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.show();
                    } catch (Exception e) {
                        e.printStackTrace();
                        stageP.show();
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });
    }

    // Demande confirmation puis supprime la salle en base.
    private void btnSupp() {
        tcSupp.setCellFactory(col -> new TableCell<Salle, Void>() {
            private final Button btn = new Button("Supprimer");

            {
                btn.setOnAction(event -> {
                    Salle salle = getTableView().getItems().get(getIndex());
                    Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                    confirm.setTitle("Suppression");
                    confirm.setHeaderText(null);
                    confirm.setContentText("Supprimer la salle n°" + salle.getNumero() + " ?");
                    confirm.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.OK) {
                            SalleDAO dao = new SalleDAO();
                            if (dao.delete(salle)) {
                                tvSalle.getItems().remove(salle);
                            }
                        }
                    });
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });
    }
}

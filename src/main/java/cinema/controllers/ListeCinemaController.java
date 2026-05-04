package cinema.controllers;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import cinema.BO.Cinema;
import cinema.BO.Franchise;
import cinema.DAO.CinemaDAO;
import cinema.DAO.FranchiseDAO;
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
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

// Contrôleur de la page "liste des cinémas" : tableau + boutons voir plus / modifier / supprimer.
public class ListeCinemaController extends MenuController implements Initializable {

    @FXML
    // Le tableau qui affiche tous les cinémas.
    private TableView<Cinema> tvCinema;

    @FXML
    private TableColumn<Cinema, String> tcDenomination, tcFranchise;

    @FXML
    private TableColumn<Cinema, Void> tcModif, tcSupp,tcVp;


    @FXML
    private Button bRetour;

    @Override
    // Appelé automatiquement quand la page FXML est chargée : on remplit le tableau.
    public void initialize(URL location, ResourceBundle resources) {

        FranchiseDAO fDAO = new FranchiseDAO();

        // On met les franchises dans une Map pour retrouver vite le nom à partir de l'id.
        Map<Integer, Franchise> franchises = fDAO.findAll()
                .stream()
                .collect(Collectors.toMap(Franchise::getIdFranchise, u -> u));

        tcFranchise.setCellValueFactory(cellData -> {
            Franchise franchise = franchises.get(cellData.getValue().getIdFranchise());
            return new SimpleStringProperty(
                    franchise != null ? franchise.getNomFranchise() : "Aucun gérant");
        });

        tcDenomination.setCellValueFactory(new PropertyValueFactory<>("denomination"));
        ObservableList<Cinema> data = getCinema();
        tvCinema.setItems(data);
        btnModif();
        btnSupp();
        btnVPS();
    }

    // Va chercher tous les cinémas en base et les met dans une liste pour le TableView.
    private ObservableList<Cinema> getCinema() {

        CinemaDAO cinemaDAO = new CinemaDAO();
        List<Cinema> mesCinemas = cinemaDAO.findAll();
        ObservableList<Cinema> list = FXCollections.observableArrayList(mesCinemas);
        return list;
    }

    // Bouton retour : on ferme cette fenêtre et on rouvre l'accueil.
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
        }
    }

    // Colonne "Voir plus" : bouton qui ouvre la page détail du cinéma.
    private void btnVPS() {
        tcVp.setCellFactory(column -> new TableCell<Cinema, Void>() {
            private Button btn = new Button("Voir plus");
            {
                btn.setOnAction(event -> {
                    Cinema cinema = getTableView().getItems().get(getIndex());
                    Stage stageP = (Stage) bRetour.getScene().getWindow();
                    stageP.close();

                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(
                                getClass().getResource("/cinema/views/page_voir_plus_cinema.fxml"));
                        Parent root = fxmlLoader.load();
                        VoirPlusCinemaController voirPlusCinemaController = fxmlLoader.getController();
                        voirPlusCinemaController.setName(nameUti);
                        voirPlusCinemaController.setCinema(cinema);

                        Stage stage = new Stage();
                        WindowIcons.apply(stage);
                        stage.setTitle("Voir plus");
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
    // Colonne "Modifier" : ouvre l'écran de modification pour la ligne choisie.
    private void btnModif() {
        tcModif.setCellFactory(column -> new TableCell<Cinema, Void>() {
            private Button btn = new Button("Modifier");
            {
                btn.setOnAction(event -> {
                    Cinema cinema = getTableView().getItems().get(getIndex());
                    Stage stageP = (Stage) bRetour.getScene().getWindow();
                    stageP.close();

                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(
                                getClass().getResource("/cinema/views/page_modif_cinema.fxml"));
                        Parent root = fxmlLoader.load();
                        ModifierCinemaController modifierCinemaController = fxmlLoader.getController();
                        modifierCinemaController.setName(nameUti);
                        modifierCinemaController.setCinemaToEdit(cinema);

                        Stage stage = new Stage();
                        WindowIcons.apply(stage);
                        stage.setTitle("Modification cinema");
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

    // Colonne "Supprimer" : toujours une popup de confirmation (comme la liste franchises).
    private void btnSupp() {
        tcSupp.setCellFactory(col -> new TableCell<Cinema, Void>() {
            private Button btn = new Button("Supprimer");
            {
                btn.setOnAction(event -> {
                    Cinema cinema = getTableView().getItems().get(getIndex());
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(
                                getClass().getResource("/cinema/views/popup_cinema.fxml"));
                        Parent root = fxmlLoader.load();
                        PopupCinema popupCinemaController = fxmlLoader.getController();
                        popupCinemaController.setCinemaToDelete(cinema, tvCinema);

                        Stage stage = new Stage();
                        WindowIcons.apply(stage);
                        stage.setTitle("Pop-up");
                        stage.setScene(new Scene(root));
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.show();
                    } catch (Exception e) {
                        e.printStackTrace();
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

}

package cinema.controllers;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import cinema.BO.Franchise;
import cinema.BO.Cinema;
import cinema.BO.Utilisateur;
import cinema.DAO.FranchiseDAO;
import cinema.DAO.CinemaDAO;
import cinema.DAO.UtilisateurDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

public class ListeFranchiseController extends MenuController implements Initializable {
    @FXML
    private TableView<Franchise> tvFranchises;

    @FXML
    private TableColumn<Franchise, String> tcNomFranchise;

    @FXML
    private TableColumn<Franchise, String> tcSiegeSocial;

    @FXML
    private TableColumn<Franchise, String> tcGerant;

    @FXML
    private TableColumn<Franchise, Void> tcModifier;

    @FXML
    private TableColumn<Franchise, Void> tcSupprimer;

    @FXML
    private Button bRetour;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        UtilisateurDAO gerantDAO = new UtilisateurDAO();

        // Programmation fonctionnelle
        // Collecteur de flux :
        // https://www.ionos.fr/digitalguide/sites-internet/developpement-web/les-collectors-de-streams-en-java/
        // toMap :
        // https://www.geeksforgeeks.org/java/collectors-tomap-method-in-java-with-examples/
        //
        Map<Integer, Utilisateur> gerants = gerantDAO.findAll()
                .stream()
                .collect(Collectors.toMap(Utilisateur::getIdUtilisateur, u -> u));

        tcGerant.setCellValueFactory(cellData -> {
            Utilisateur gerant = gerants.get(cellData.getValue().getIdGerant());
            return new SimpleStringProperty(
                    gerant != null ? gerant.getNom() + " " + gerant.getPrenom() : "Aucun gérant");
        });
        tcNomFranchise.setCellValueFactory(new PropertyValueFactory<>("nomFranchise"));
        tcSiegeSocial.setCellValueFactory(new PropertyValueFactory<>("siegeSocial"));
        ObservableList<Franchise> data = getFranchiseList();
        tvFranchises.setItems(data);

        addButtonModifierToTable();
        addButtonSupprimerToTable();
    }

    private ObservableList<Franchise> getFranchiseList() {

        FranchiseDAO var1 = new FranchiseDAO();
        List<Franchise> var2 = var1.findAll();

        ObservableList<Franchise> list = FXCollections.observableArrayList();
        if (var2 != null) {
            list.addAll(var2);
        }
        return list;
    }

    @FXML
    private void bRetourClick() {
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

    private void addButtonModifierToTable() {
        tcModifier.setCellFactory(column -> new TableCell<>() {
            private final Button btn = new Button("Modifier");
            {
                btn.setOnAction(event -> {
                    Franchise franchise = getTableView().getItems().get(getIndex());
                    Stage stageP = (Stage) bRetour.getScene().getWindow();
                    stageP.close();

                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(
                                getClass().getResource("/cinema/views/page_modif_franchise.fxml"));
                        Parent root = fxmlLoader.load();

                        ModifierFranchiseController modifierFranchiseCtrl = fxmlLoader.getController();
                        modifierFranchiseCtrl.setAttributes(franchise);
                        modifierFranchiseCtrl.setName(nameUti);

                        Stage stage = new Stage();
                        stage.setTitle("Modification franchise");
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

    private void addButtonSupprimerToTable() {
        tcSupprimer.setCellFactory(column -> new TableCell<>() {
            private final Button btn = new Button("Supprimer");

            {
                btn.setOnAction(event -> {
                    Franchise franchise = getTableView().getItems().get(getIndex());
                    tvFranchises.getItems().remove(franchise);
                    FranchiseDAO franchiseDAO = new FranchiseDAO();
                    franchiseDAO.delete(franchise);
                });
                // btn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });
    }

}

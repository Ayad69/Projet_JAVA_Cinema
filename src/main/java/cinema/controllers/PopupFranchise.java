package cinema.controllers;

import cinema.BO.Franchise;
import cinema.DAO.FranchiseDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

// Confirmation avant suppression d'une franchise (même principe que PopupCinema).
public class PopupFranchise {
    @FXML
    private Label ff;
    protected String nameUti;
    private Franchise franchise;
    private TableView<Franchise> tvFranchises;
    @FXML
    private Button btnOk, btnRe;

    @FXML
    private void btnRe(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
        // ne pas pouvoir agrandir la taille de la page
        stage.setResizable(false);

    }

    @FXML
    private void btnOk(ActionEvent event) {
        if (franchise != null) {
            FranchiseDAO franchiseDAO = new FranchiseDAO();
            franchiseDAO.delete(franchise);
            if (tvFranchises != null) {
                tvFranchises.getItems().remove(franchise);
            }
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void setName(String nameUti) {
        this.nameUti = nameUti;
    }

    public void setFranchiseToDelete(Franchise franchise, TableView<Franchise> tvFranchises) {
        this.franchise = franchise;
        this.tvFranchises = tvFranchises;
    }
}

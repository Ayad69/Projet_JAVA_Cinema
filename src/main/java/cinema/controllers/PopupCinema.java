package cinema.controllers;

import cinema.BO.Cinema;
import cinema.DAO.CinemaDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

// Petite fenêtre "tu es sûr ?" avant de supprimer un cinéma (quand il y a un cas particulier).
public class PopupCinema {
    @FXML
    private Label ff;
    protected String nameUti;
    private Cinema cinema;
    private TableView<Cinema> tvCinema;
    @FXML
    private Button btnOk, btnRe;

    @FXML
    // "Retour" = on annule, on ferme la popup sans supprimer.
    private void btnRe(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    // "Oui" = on supprime vraiment le cinéma en base et on l'enlève du tableau.
    private void btnOk(ActionEvent event) {
        if (cinema != null) {
            CinemaDAO cinemaDAO = new CinemaDAO();
            cinemaDAO.delete(cinema);
            if (tvCinema != null) {
                tvCinema.getItems().remove(cinema);
            }
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void setName(String nameUti) {
        this.nameUti = nameUti;
    }

    // Appelé depuis la liste cinéma : on sait quel cinéma supprimer et quel tableau mettre à jour.
    public void setCinemaToDelete(Cinema cinema, TableView<Cinema> tvCinema) {
        this.cinema = cinema;
        this.tvCinema = tvCinema;
    }
}

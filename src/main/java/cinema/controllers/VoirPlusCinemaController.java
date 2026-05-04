package cinema.controllers;

import java.util.List;
import java.util.stream.Collectors;

import cinema.BO.Cinema;
import cinema.BO.Franchise;
import cinema.BO.Salle;
import cinema.DAO.FranchiseDAO;
import cinema.DAO.SalleDAO;
import cinema.util.WindowIcons;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Modality;
import javafx.stage.Stage;

// Écran "détail" d'un cinéma : adresse, franchise, liste des salles, etc.
public class VoirPlusCinemaController extends MenuController {

    @FXML
    private Label lDenomination, lAdresse, lVille, lFranchise;
    @FXML
    private TextArea taSalles;
    @FXML
    private Button bRetour;

    // Remplit les labels et la zone de texte à partir du cinéma choisi dans la liste.
    public void setCinema(Cinema cinema) {
        if (cinema == null) {
            return;
        }
        lDenomination.setText(cinema.getDenomination());
        lAdresse.setText(cinema.getAdresse() != null ? cinema.getAdresse() : "—");
        lVille.setText(cinema.getVille() != null ? cinema.getVille() : "—");

        FranchiseDAO franchiseDAO = new FranchiseDAO();
        Franchise franchise = franchiseDAO.find(cinema.getIdFranchise());
        lFranchise.setText(franchise != null ? franchise.getNomFranchise() : "—");

        SalleDAO salleDAO = new SalleDAO();
        List<Salle> salles = salleDAO.findAll().stream()
                .filter(s -> s.getIdCinema() == cinema.getIdCinema())
                .collect(Collectors.toList());
        if (salles.isEmpty()) {
            taSalles.setText("Aucune salle enregistrée pour ce cinéma.");
        } else {
            StringBuilder sb = new StringBuilder();
            for (Salle s : salles) {
                sb.append("• N°").append(s.getNumero())
                        .append(" — ").append(s.getDescription() != null ? s.getDescription() : "")
                        .append(" (").append(s.getNbPlaces()).append(" places)\n");
            }
            taSalles.setText(sb.toString().trim());
        }
    }

    @FXML
    // Retour à la liste des cinémas.
    private void bRetourClick(ActionEvent event) {
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
            stage.setTitle("Liste des cinémas");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            stageP.show();
        }
    }
}

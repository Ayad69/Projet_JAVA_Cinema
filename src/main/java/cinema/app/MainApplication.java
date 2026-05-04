package cinema.app;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import cinema.util.WindowIcons;

// C'est le "vrai" démarrage de l'appli graphique (JavaFX).
// Ici on ouvre la première fenêtre : la page de connexion.
public class MainApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        try {
            // On charge le fichier FXML de la page connexion (le design de l'écran).
            Parent parent = FXMLLoader.load(getClass().getResource("/cinema/views/page_connexion.fxml"));

            // La scène = ce qu'on met dans la fenêtre (ici tout le contenu de la connexion).
            Scene scene = new Scene(parent);

            // Le stage = la fenêtre Windows (titre, taille, icône, etc.).
            primaryStage.setTitle("Application de gestion de franchise - Authentification");
            primaryStage.setResizable(false);
            primaryStage.centerOnScreen();
            WindowIcons.apply(primaryStage);
            primaryStage.setScene(scene);

            // Petite astuce : au début la fenêtre passe devant les autres, puis on enlève ça.
            primaryStage.setAlwaysOnTop(true);
            primaryStage.show();
            primaryStage.setAlwaysOnTop(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

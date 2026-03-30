package cinema.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        try {
            // chargement de la vue de connexion
            Parent parent = FXMLLoader.load(getClass().getResource("/cinema/views/page_connexion.fxml"));

            // configuration de la scène
            Scene scene = new Scene(parent);

            // paramétrage du stage (fenêtre principale)
            primaryStage.setTitle("Application de gestion de franchise - Authentification");
            primaryStage.setResizable(false);
            primaryStage.centerOnScreen();
            primaryStage.getIcons().add(new Image("/cinema/images/cinema_32x32.png"));
            primaryStage.setScene(scene);

            primaryStage.setAlwaysOnTop(true);   // Toujours au-dessus des autres fenêtres
            // affichage
            primaryStage.show();
            primaryStage.setAlwaysOnTop(false);
        } // end try
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}

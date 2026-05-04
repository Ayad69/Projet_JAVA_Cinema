package cinema.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;

import cinema.util.WindowIcons;

// Utilitaire pour changer de page FXML sans tout recopier (historique + paramètres).
// Pas obligatoire si tu ouvres tout avec FXMLLoader à la main comme dans MenuController.
public class Navigation {

    private static Stage primaryStage;
    private static final Stack<String> historique = new Stack<>();
    private static final Map<String, Object> params = new HashMap<>();

    // Garde la fenêtre principale pour pouvoir changer sa scène plus tard.
    public static void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }

    // Charge un fichier FXML et l'affiche dans la fenêtre principale.
    public static void goTo(String fxmlPath) {
        try {
            if (primaryStage != null) {
                if (!historique.isEmpty() && !historique.peek().equals(fxmlPath)) {
                    historique.push(fxmlPath);
                } else if (historique.isEmpty()) {
                    historique.push(fxmlPath);
                }

                Parent root = FXMLLoader.load(Navigation.class.getResource(fxmlPath));
                primaryStage.setScene(new Scene(root));

                WindowIcons.apply(primaryStage);

                primaryStage.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void goTo(String fxmlPath, String key, Object value) {
        setParam(key, value);
        goTo(fxmlPath);
    }

    public static void goTo(String fxmlPath, Window currentWindow) {
        try {
            Parent root = FXMLLoader.load(Navigation.class.getResource(fxmlPath));
            Stage newStage = new Stage();
            WindowIcons.apply(newStage);
            newStage.setScene(new Scene(root));
            newStage.setResizable(false);

            newStage.show();

            if (currentWindow != null) {
                currentWindow.hide();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void goTo(String fxmlPath, String key, Object value, Window currentWindow) {
        setParam(key, value);
        goTo(fxmlPath, currentWindow);
    }

    // Revient à la page précédente (grâce à la pile "historique").
    public static void goBack() {
        if (historique.size() >= 2) {
            historique.pop();
            String previous = historique.peek();
            goTo(previous);
        }
    }

    public static void clearHistory() {
        historique.clear();
    }

    public static void setParam(String key, Object value) {
        params.put(key, value);
    }

    public static <T> T getParam(String key) {
        return (T) params.get(key);
    }

    public static void clearParams() {
        params.clear();
    }
}

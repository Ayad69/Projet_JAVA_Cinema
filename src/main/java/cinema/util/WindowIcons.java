package cinema.util;

import java.net.URL;

import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Met le même logo sur la barre de titre / la barre des tâches pour toutes les fenêtres.
 */
public final class WindowIcons {

    private static final String LOGO = "/cinema/images/cinema_32x32.png";

    private WindowIcons() {
    }

    public static void apply(Stage stage) {
        if (stage == null) {
            return;
        }
        stage.getIcons().clear();
        URL url = WindowIcons.class.getResource(LOGO);
        if (url != null) {
            stage.getIcons().add(new Image(url.toExternalForm()));
        } else {
            stage.getIcons().add(new Image(LOGO));
        }
    }
}

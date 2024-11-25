package application;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import java.net.URL;
import java.util.ResourceBundle;

public class PerdisteController implements Initializable {
    @FXML
    private ImageView derrota;
    private static MediaPlayer mediaPlayer;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Configura el efecto de transición y escala
        iniciarAnimacionDerrota();
    }

    private void iniciarAnimacionDerrota() {
        // Comienza con la imagen invisible y en tamaño reducido
        derrota.setOpacity(0);
        derrota.setScaleX(0.5);
        derrota.setScaleY(0.5);

        // Animación de aumento de escala
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(2), derrota);
        scaleTransition.setFromX(0.5);
        scaleTransition.setFromY(0.5);
        scaleTransition.setToX(1.0);
        scaleTransition.setToY(1.0);

        // Animación de aparición (fade in)
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2), derrota);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);

        // Inicia ambas transiciones juntas
        scaleTransition.play();
        fadeTransition.play();
        String rutaIntroAudio = getClass().getResource("/Resources/derrota.mp3").toExternalForm(); // Cambié la ruta a "Intro.mp3"
        Media introMedia = new Media(rutaIntroAudio);
        mediaPlayer = new MediaPlayer(introMedia);
        mediaPlayer.play();  // Reproducir la música

    }
    
}

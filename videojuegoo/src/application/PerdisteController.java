package application;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ResourceBundle;

public class PerdisteController implements Initializable {
    @FXML
    private ImageView derrota;
    private static MediaPlayer mediaPlayer;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        iniciarAnimacionDerrota();
    }

    private void iniciarAnimacionDerrota() {
        // Estado inicial de la imagen
        derrota.setOpacity(0);
        derrota.setScaleX(0.5);
        derrota.setScaleY(0.5);

        // Transición de escala
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(2), derrota);
        scaleTransition.setFromX(0.5);
        scaleTransition.setFromY(0.5);
        scaleTransition.setToX(1.0);
        scaleTransition.setToY(1.0);

        // Transición de aparición
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2), derrota);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);

        // Ejecutar ambas transiciones
        scaleTransition.play();
        fadeTransition.play();

        // Reproducir música desde archivo temporal
        if (mediaPlayer == null) {
            try {
                String resourcePath = "/Resources/derrota.mp3";
                InputStream audioStream = getClass().getResourceAsStream(resourcePath);

                if (audioStream == null) {
                	  throw new java.io.FileNotFoundException("ERROR: No se encontró el recurso como stream: " + resourcePath);
                }

                // Crear archivo temporal con extensión .mp3
                Path tempFile = Files.createTempFile("derrota_temp", ".mp3");
                tempFile.toFile().deleteOnExit(); // Se elimina al cerrar la app

                // Copiar el audio al archivo temporal
                try (OutputStream out = Files.newOutputStream(tempFile)) {
                    audioStream.transferTo(out);
                }

                // Crear y reproducir el MediaPlayer desde el archivo temporal
                Media media = new Media(tempFile.toUri().toString());
                mediaPlayer = new MediaPlayer(media);
                mediaPlayer.play();

            } catch (Exception e) {
                System.err.println("Error al reproducir audio de derrota:");
                e.printStackTrace();
            }
        }
    }

}

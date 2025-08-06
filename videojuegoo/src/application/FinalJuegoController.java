package application;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ResourceBundle;

public class FinalJuegoController implements Initializable {
    @FXML
    private ImageView finalimg1;  // Imagen que contiene el texto dentro
    @FXML
    private ImageView finalimg2;
    
    private MediaPlayer mediaPlayer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Obtener la altura de la imagen para calcular la distancia total
        double alturaImagen = finalimg1.getFitHeight(); // Altura de la imagen en píxeles

        // Crear la transición de movimiento
        TranslateTransition transition = new TranslateTransition();
        transition.setNode(finalimg1); // Imagen a mover
        transition.setDuration(Duration.minutes(1)); // Duración de 1 minuto
        transition.setByY(-alturaImagen - finalimg1.getLayoutY()); // Subir fuera de pantalla
        transition.setCycleCount(1); // Solo una vez
        transition.setAutoReverse(false); // No regresa

        // Iniciar la animación
        transition.play();

        // Reproducir música con MediaPlayer solo si aún no fue creada
        if (mediaPlayer == null) {
            try {
                String resourcePath = "/Resources/final.mp3";
                InputStream audioStream = getClass().getResourceAsStream(resourcePath);

                if (audioStream == null) {
                    throw new FileNotFoundException("ERROR: No se encontró el recurso: " + resourcePath);
                }

                // Crear archivo temporal para reproducir desde file:
                Path tempFile = Files.createTempFile("final_temp", ".mp3");
                tempFile.toFile().deleteOnExit();

                try (OutputStream out = Files.newOutputStream(tempFile)) {
                    audioStream.transferTo(out);
                }

                Media media = new Media(tempFile.toUri().toString());
                mediaPlayer = new MediaPlayer(media);
                mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Música en bucle
                mediaPlayer.play();

            } catch (Exception e) {
                System.err.println("Error al cargar o reproducir el audio de final:");
                e.printStackTrace();
            }
        }

        // Acción al terminar la animación
        transition.setOnFinished(event -> {
            try {
                EscenaPostCreditos();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void EscenaPostCreditos() throws IOException {
    	  FXMLLoader loader = new FXMLLoader(getClass().getResource("PostCreditos.fxml"));
          Parent root = loader.load();
          Stage stage = (Stage) finalimg1.getScene().getWindow();
          Scene scene = new Scene(root);
          root.requestFocus();
          stage.setScene(scene);
          stage.show();
    }
    
}

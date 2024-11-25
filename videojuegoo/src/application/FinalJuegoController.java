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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FinalJuegoController implements Initializable {
    @FXML
    private ImageView finalimg1;  // Imagen que contiene el texto dentro
    @FXML
    private ImageView finalimg2;
    
    private MediaPlayer mediaPlayer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Configurar la música

        // Obtener la altura de la imagen para calcular la distancia total
        double alturaImagen = finalimg1.getFitHeight(); // Altura de la imagen en píxeles

        // Crear la transición de movimiento
        TranslateTransition transition = new TranslateTransition();
        transition.setNode(finalimg1); // Establecer la imagen que queremos mover
        transition.setDuration(Duration.minutes(1)); // Duración total de 1 minuto
        transition.setByY(-alturaImagen - finalimg1.getLayoutY()); // Subir la imagen completamente fuera de la vista
        transition.setCycleCount(1); // Solo una vez
        transition.setAutoReverse(false); // No regresa

        // Iniciar la animación
        transition.play();
        if (mediaPlayer == null) {
            String rutaNivel1Audio = getClass().getResource("/Resources/final.mp3").toExternalForm();
            Media nivel1Media = new Media(rutaNivel1Audio);
            mediaPlayer = new MediaPlayer(nivel1Media);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Música en bucle
            mediaPlayer.play();
        }
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

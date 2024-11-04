package application;

import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.PerspectiveTransform;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EntradaController implements Initializable {

    @FXML
    private ImageView intro;  // Imagen que contiene el texto dentro
    private Stage stage;
    private Scene scene;
    private MediaPlayer mediaPlayer;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // Efecto de perspectiva
        PerspectiveTransform perspective = new PerspectiveTransform();
        perspective.setUlx(200.0);
        perspective.setUly(0.0);
        perspective.setUrx(400.0);
        perspective.setUry(0.0);
        perspective.setLlx(0.0);
        perspective.setLly(800.0);
        perspective.setLrx(600.0);
        perspective.setLry(800.0);
        intro.setEffect(perspective);

        // Transiciones de movimiento y escala
        TranslateTransition translate = new TranslateTransition();
        translate.setNode(intro);
        translate.setDuration(Duration.seconds(30));
        translate.setFromY(400);
        translate.setToY(-1100);

        ScaleTransition scale = new ScaleTransition();
        scale.setNode(intro);
        scale.setDuration(Duration.seconds(30));
        scale.setFromX(1.5);
        scale.setFromY(1.5);
        scale.setToX(0.5);
        scale.setToY(0.5);

        // Reproducir las animaciones
        translate.play();
        scale.play();

        // Reproducir la música de intro
        String rutaIntroAudio = getClass().getResource("/Resources/Intro.mp3").toExternalForm(); // Cambié la ruta a "Intro.mp3"
        Media introMedia = new Media(rutaIntroAudio);
        mediaPlayer = new MediaPlayer(introMedia);
        mediaPlayer.play();  // Reproducir la música

        // Después de que termine la animación, cargar MovimientoController
        translate.setOnFinished(event -> {
            try {
                cambiarAMovimiento();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void cambiarAMovimiento() throws IOException {
        // Detener y liberar la música antes de cambiar de escena
        if (mediaPlayer != null) {
            mediaPlayer.stop();  // Detener la música de intro
            mediaPlayer.dispose();  // Liberar los recursos
        }

        // Cargar la nueva escena
        FXMLLoader loader = new FXMLLoader(getClass().getResource("primeraisla.fxml"));
        Parent root = loader.load();

        // Configurar la nueva escena
        stage = (Stage) intro.getScene().getWindow();
        scene = new Scene(root);
        root.requestFocus();
        stage.setScene(scene);
        stage.show();
    }
}

package application;

import java.awt.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class PelearController {
    private static MediaPlayer mediaPlayer;

    @FXML
    public void initialize() {
        // Verifica si el mediaPlayer ya ha sido creado
        if (mediaPlayer == null) {
            String rutaNivel1Audio = getClass().getResource("/Resources/pelea.mp3").toExternalForm();
            Media nivel1Media = new Media(rutaNivel1Audio);
            mediaPlayer = new MediaPlayer(nivel1Media);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.play();
        } else {
            // Si el mediaPlayer ya existe, solo reanuda la música si está pausada
            if (mediaPlayer.getStatus() == MediaPlayer.Status.PAUSED) {
                mediaPlayer.play();
            }
        }
    }
    
}


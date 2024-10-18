package application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.net.URL;

public class InterfazController {
     private Stage stage;  
     private Scene scene;
     private Parent root;
     private MediaPlayer mediaPlayer;
     public void CambiarAcontroles(ActionEvent event) throws IOException {
    	  Parent root = FXMLLoader.load(getClass().getResource("Controles.fxml"));
    	  stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	  scene = new Scene(root);
    	  stage.setScene(scene);
    	 stage.show();
     }
     
     public void CambiarAMenu(ActionEvent event) throws IOException {
    	 Parent root = FXMLLoader.load(getClass().getResource("Interfaz.fxml"));
   	  stage = (Stage)((Node)event.getSource()).getScene().getWindow();
   	  scene = new Scene(root);
   	  stage.setScene(scene);
   	 stage.show();
     }
     public void InicioDelJuego(ActionEvent event) throws IOException {
   	  Parent root = FXMLLoader.load(getClass().getResource("EntradDeJuego.fxml"));
   	  stage = (Stage)((Node)event.getSource()).getScene().getWindow();
   	  scene = new Scene(root);
   	  stage.setScene(scene);
   	 stage.show();
   	 
   	String rutaAudio = getClass().getResource("/Resources/Intro.mp3").toExternalForm();
    Media media = new Media(rutaAudio);
    mediaPlayer = new MediaPlayer(media);
    mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Repetir indefinidamente
    mediaPlayer.play(); // Reproduce la música
    }

     
}

    

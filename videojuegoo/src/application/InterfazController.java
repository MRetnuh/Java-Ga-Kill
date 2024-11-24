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
     private static MediaPlayer mediaPlayer;
 @FXML
    
     public void initialize() {
         if (mediaPlayer == null) {
             String rutaNivel1Audio = getClass().getResource("/Resources/menu.mp3").toExternalForm();
             Media nivel1Media = new Media(rutaNivel1Audio);
             mediaPlayer = new MediaPlayer(nivel1Media);
             mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
             mediaPlayer.play();
         }
     }

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
    	  if (mediaPlayer != null) {
              mediaPlayer.stop();  // Detener la música de intro
              mediaPlayer.dispose();  // Liberar los recursos
          }
   	  Parent root = FXMLLoader.load(getClass().getResource("EntradDeJuego.fxml"));
   	  stage = (Stage)((Node)event.getSource()).getScene().getWindow();
   	  scene = new Scene(root);
   	  stage.setScene(scene);
   	 stage.show();
    }
     public void CambiarAPersonajes(ActionEvent event) throws IOException {
   	  Parent root = FXMLLoader.load(getClass().getResource("characters.fxml"));
   	  stage = (Stage)((Node)event.getSource()).getScene().getWindow();
   	  scene = new Scene(root);
   	  stage.setScene(scene);
   	 stage.show();
    }
     public void CambiarAEnemigos(ActionEvent event) throws IOException {
      	  Parent root = FXMLLoader.load(getClass().getResource("enemigos.fxml"));
      	  stage = (Stage)((Node)event.getSource()).getScene().getWindow();
      	  scene = new Scene(root);
      	  stage.setScene(scene);
      	 stage.show();
       }
}

    

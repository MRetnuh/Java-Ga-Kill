package application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ResourceBundle;

public class InterfazController implements Initializable{
     private Stage stage;  
     private Scene scene;
     private Parent root;
     private static MediaPlayer mediaPlayer;


     @Override
 public void initialize(URL location, ResourceBundle resources) {
     if (mediaPlayer == null) {
         try {
             String resourcePath = "/Resources/menu.mp3";
             InputStream audioStream = getClass().getResourceAsStream(resourcePath);

             if (audioStream == null) {
                 throw new java.io.FileNotFoundException("ERROR DEFINITIVO: No se pudo encontrar el recurso como stream: " + resourcePath);
             }

             // Crear un archivo temporal con la extensión .mp3
             Path tempFile = Files.createTempFile("menu_temp", ".mp3");

             // Asegurarse de que el archivo temporal se borre cuando se cierre el juego
             tempFile.toFile().deleteOnExit();

             // Copiar el stream de audio desde el JAR hacia el archivo temporal
             try (OutputStream out = Files.newOutputStream(tempFile)) {
                 audioStream.transferTo(out);
             }

             // Ahora creamos el Media object usando la URI del archivo temporal,
             // que tendrá un protocolo 'file:' válido que JavaFX sí entiende.
             Media nivel1Media = new Media(tempFile.toUri().toString());
             mediaPlayer = new MediaPlayer(nivel1Media);
             mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
             mediaPlayer.play();

         } catch (Exception e) {
             System.err.println("Ocurrió un error grave al inicializar la música (método de archivo temporal):");
             e.printStackTrace();
         }
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

    

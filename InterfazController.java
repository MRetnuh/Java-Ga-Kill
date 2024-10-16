package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class InterfazController {
     private Stage stage;  
     private Scene scene;
     private Parent root;

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
    }
}


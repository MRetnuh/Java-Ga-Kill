package application;
import java.io.IOException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
public class Mochila {
private Stage stage;  
private Scene scene;
private Parent root;
private Personaje prota;
private Personaje prota2;
private Personaje prota3;
@FXML
private Label Item1, Item2;
private int protaVida;
@FXML
  private int protaAtaque;
@FXML
  private int protaExperiencia;
@FXML
  private int protaExpLimite;
@FXML
  private int protavidamax;
@FXML
  private int protanivel;
@FXML
  private double posX;
  private double posY;
private int[][] layout;
public int pociones = 0;
public int totems = 0;
public void initialize(Personaje prota, int[][] layout, Personaje prota2, Personaje prota3, int curaciones, int totems) {
	this.pociones = curaciones;
	this.totems = totems;
	 this.prota = prota;
	   this.layout = layout;
	   this.protaAtaque = prota.daño;
	   this.protaVida = prota.salud;
	   this.protavidamax = prota.vidaMaxima;
	   this.protaExperiencia = prota.experienciaActual;
	   this.protaExpLimite = prota.experienciaLimite;
	   this.protanivel = prota.nivel;
	   this.posX = prota.posX;
      this.posY = prota.posY;
      this.prota2 = prota2;
      this.prota3 = prota3;
      labels();
}
public void labels() {
	 Item1.setText("X " + pociones);
     Item2.setText("X " + totems);
}
public void VolverAselector (ActionEvent event) throws IOException{
	 FXMLLoader loader = new FXMLLoader(getClass().getResource("Menu.fxml"));
     Parent root = loader.load();
     MenuController MenuController = loader.getController();
     MenuController.setStats(prota, layout, prota2, prota3, pociones, totems);
     stage = (Stage)((Node)event.getSource()).getScene().getWindow();
	  scene = new Scene(root);
	  root.requestFocus();
	  stage.setScene(scene);
	 stage.show();
}
}

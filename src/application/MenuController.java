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

public class MenuController {
	  @FXML
	 private Personaje prota;
	  @FXML
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
	    public Button seleccionar1, seleccionar2;
	    private int protanivel;
	  @FXML
	    private double posX;
	    private double posY;
	 private Stage stage;  
     private Scene scene;
     private Parent root;
     private int[][] layout;
     public int akame = 1;
     public int leone = 0;
     @FXML
     private Label ataque, vida, experiencia, nivel;
     @FXML
     private static MediaPlayer mediaPlayer;
	   public void volveralmapa(ActionEvent event) throws IOException {
		   FXMLLoader loader = new FXMLLoader(getClass().getResource("primeraisla.fxml"));
           Parent root = loader.load();
           MovimientoController movimientoController = loader.getController();
           movimientoController.layout = this.layout;
           movimientoController.akame = 0;
           movimientoController.leone = 1;
           movimientoController.Akame.vidaMaxima = prota.vidaMaxima;
           movimientoController.Akame.salud = prota.salud;
           movimientoController.Akame.daño = prota.daño;
           movimientoController.Akame.experienciaActual = prota.experienciaActual;
           movimientoController.Akame.experienciaLimite = prota.experienciaLimite;
           movimientoController.Akame.nivel = prota.nivel;
           movimientoController.Akame.posX = prota.posX;
           movimientoController.Akame.posY = prota.posY;
           movimientoController.initialize();
	    	  stage = (Stage)((Node)event.getSource()).getScene().getWindow();
	    	  scene = new Scene(root);
	    	  root.requestFocus();
	    	  stage.setScene(scene);
	    	 stage.show();
	    	 
	     }
	   public void setStats(Personaje prota, int[][] layout) {
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
	        this.akame = prota.akame;
	        this.leone = prota.leone;
	        actualizarLabels();
	        
	   }
	   public void actualizarLabels(){
		   vida.setText("Vida: " + protaVida + "/" + protavidamax);
		   ataque.setText("Ataque: " + protaAtaque);
		   experiencia.setText("Experiencia: " + protaExperiencia + "/" + protaExpLimite);
		   nivel.setText("Nivel: " + protanivel);
	   }
	   public void volveralmapa1(ActionEvent event) throws IOException {
		   FXMLLoader loader = new FXMLLoader(getClass().getResource("primeraisla.fxml"));
           Parent root = loader.load();
           MovimientoController movimientoController = loader.getController();
           movimientoController.layout = this.layout;
           movimientoController.akame = 1;
           movimientoController.leone = 0;
           movimientoController.Akame.vidaMaxima = prota.vidaMaxima;
           movimientoController.Akame.salud = prota.salud;
           movimientoController.Akame.daño = prota.daño;
           movimientoController.Akame.experienciaActual = prota.experienciaActual;
           movimientoController.Akame.experienciaLimite = prota.experienciaLimite;
           movimientoController.Akame.nivel = prota.nivel;
           movimientoController.Akame.posX = prota.posX;
           movimientoController.Akame.posY = prota.posY;
           movimientoController.initialize();
	    	  stage = (Stage)((Node)event.getSource()).getScene().getWindow();
	    	  scene = new Scene(root);
	    	  root.requestFocus();
	    	  stage.setScene(scene);
	    	 stage.show();
	    	 
	     }
}

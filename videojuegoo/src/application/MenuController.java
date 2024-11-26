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
	  private Personaje prota2;
	  private Personaje prota3;
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
	    public Button seleccionar1, seleccionar2, seleccionar3;
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
     public int curaciones = 0;
     public int totems = 0;
     @FXML
     private Label ataque, vida, experiencia, nivel, ataque1, vida1, experiencia1, nivel1, ataque2, vida2, experiencia2, nivel2;
     @FXML
     private static MediaPlayer mediaPlayer;
	   public void volveralmapa(ActionEvent event) throws IOException {
		   FXMLLoader loader = new FXMLLoader(getClass().getResource("primeraisla.fxml"));
           Parent root = loader.load();
           MovimientoController movimientoController = loader.getController();
           movimientoController.layout = this.layout;
           movimientoController.akame = 0;
           movimientoController.leone = 1;
           movimientoController.java = 0;
           movimientoController.Akame.vidaMaxima = prota.vidaMaxima;
           movimientoController.Akame.salud = prota.salud;
           movimientoController.Akame.daño = prota.daño;
           movimientoController.Akame.experienciaActual = prota.experienciaActual;
           movimientoController.Akame.experienciaLimite = prota.experienciaLimite;
           movimientoController.Akame.nivel = prota.nivel;
           movimientoController.Akame.posX = prota.posX;
           movimientoController.Akame.posY = prota.posY;
           movimientoController.Leone.salud = prota2.salud;
           movimientoController.Leone.vidaMaxima = prota2.vidaMaxima;
           movimientoController.Leone.experienciaActual = prota2.experienciaActual;
           movimientoController.Leone.daño = prota2.daño;
           movimientoController.Leone.experienciaLimite = prota2.experienciaLimite;
           movimientoController.Leone.nivel = prota2.nivel;
           movimientoController.Java.salud = prota3.salud;
           movimientoController.Java.vidaMaxima = prota3.vidaMaxima;
           movimientoController.Java.experienciaActual = prota3.experienciaActual;
           movimientoController.Java.daño = prota3.daño;
           movimientoController.Java.experienciaLimite = prota3.experienciaLimite;
           movimientoController.Java.nivel = prota3.nivel;
           movimientoController.curaciones = curaciones;
           movimientoController.totems = totems;
           movimientoController.initialize();
	    	  stage = (Stage)((Node)event.getSource()).getScene().getWindow();
	    	  scene = new Scene(root);
	    	  root.requestFocus();
	    	  stage.setScene(scene);
	    	 stage.show();
	    	 
	     }
	   public void setStats(Personaje prota, int[][] layout, Personaje prota2, Personaje prota3, int curaciones, int totems) {
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
	        this.prota2 = prota2;
	        this.prota3 = prota3;
	        this.curaciones = curaciones;
	        this.totems = totems;
	        actualizarLabels();
	        
	   }
	   public void actualizarLabels(){
		   vida.setText("Vida: " + protaVida + "/" + protavidamax);
		   ataque.setText("Ataque: " + protaAtaque);
		   experiencia.setText("Experiencia: " + protaExperiencia + "/" + protaExpLimite);
		   nivel.setText("Nivel: " + protanivel);
		   vida1.setText("Vida: " + prota2.salud + "/" + prota2.vidaMaxima);
		   ataque1.setText("Ataque: " + prota2.daño);
		   experiencia1.setText("Experiencia: " + prota2.experienciaActual + "/" + prota.experienciaLimite);
		   nivel1.setText("Nivel: " + prota2.nivel);
		   vida2.setText("Vida: " + prota3.salud + "/" + prota3.vidaMaxima);
		   ataque2.setText("Ataque: " + prota3.daño);
		   experiencia2.setText("Experiencia: " + prota3.experienciaActual + "/" + prota.experienciaLimite);
		   nivel2.setText("Nivel: " + prota3.nivel);
	   }
	   public void volveralmapa1(ActionEvent event) throws IOException {
		   FXMLLoader loader = new FXMLLoader(getClass().getResource("primeraisla.fxml"));
           Parent root = loader.load();
           MovimientoController movimientoController = loader.getController();
           movimientoController.layout = this.layout;
           movimientoController.akame = 1;
           movimientoController.leone = 0;
           movimientoController.java = 0;
           movimientoController.Akame.vidaMaxima = prota.vidaMaxima;
           movimientoController.Akame.salud = prota.salud;
           movimientoController.Akame.daño = prota.daño;
           movimientoController.Akame.experienciaActual = prota.experienciaActual;
           movimientoController.Akame.experienciaLimite = prota.experienciaLimite;
           movimientoController.Akame.nivel = prota.nivel;
           movimientoController.Akame.posX = prota.posX;
           movimientoController.Akame.posY = prota.posY;
           movimientoController.Leone.salud = prota2.salud;
           movimientoController.Leone.vidaMaxima = prota2.vidaMaxima;
           movimientoController.Leone.experienciaActual = prota2.experienciaActual;
           movimientoController.Leone.daño = prota2.daño;
           movimientoController.Leone.experienciaLimite = prota2.experienciaLimite;
           movimientoController.Leone.nivel = prota2.nivel;
           movimientoController.Java.salud = prota3.salud;
           movimientoController.Java.vidaMaxima = prota3.vidaMaxima;
           movimientoController.Java.experienciaActual = prota3.experienciaActual;
           movimientoController.Java.daño = prota3.daño;
           movimientoController.Java.experienciaLimite = prota3.experienciaLimite;
           movimientoController.Java.nivel = prota3.nivel;
           movimientoController.curaciones = curaciones;
           movimientoController.totems = totems;
           movimientoController.initialize();
	    	  stage = (Stage)((Node)event.getSource()).getScene().getWindow();
	    	  scene = new Scene(root);
	    	  root.requestFocus();
	    	  stage.setScene(scene);
	    	 stage.show();
	    	 
	     }
	   public void volveralmapa2(ActionEvent event) throws IOException {
		   FXMLLoader loader = new FXMLLoader(getClass().getResource("primeraisla.fxml"));
           Parent root = loader.load();
           MovimientoController movimientoController = loader.getController();
           movimientoController.layout = this.layout;
           movimientoController.akame = 0;
           movimientoController.leone = 0;
           movimientoController.java = 1;
           movimientoController.Akame.vidaMaxima = prota.vidaMaxima;
           movimientoController.Akame.salud = prota.salud;
           movimientoController.Akame.daño = prota.daño;
           movimientoController.Akame.experienciaActual = prota.experienciaActual;
           movimientoController.Akame.experienciaLimite = prota.experienciaLimite;
           movimientoController.Akame.nivel = prota.nivel;
           movimientoController.Akame.posX = prota.posX;
           movimientoController.Akame.posY = prota.posY;
           movimientoController.Leone.salud = prota2.salud;
           movimientoController.Leone.vidaMaxima = prota2.vidaMaxima;
           movimientoController.Leone.experienciaActual = prota2.experienciaActual;
           movimientoController.Leone.daño = prota2.daño;
           movimientoController.Leone.experienciaLimite = prota2.experienciaLimite;
           movimientoController.Leone.nivel = prota2.nivel;
           movimientoController.Java.salud = prota3.salud;
           movimientoController.Java.vidaMaxima = prota3.vidaMaxima;
           movimientoController.Java.experienciaActual = prota3.experienciaActual;
           movimientoController.Java.daño = prota3.daño;
           movimientoController.Java.experienciaLimite = prota3.experienciaLimite;
           movimientoController.Java.nivel = prota3.nivel;
           movimientoController.curaciones = curaciones;
           movimientoController.totems = totems;
           movimientoController.initialize();
	    	  stage = (Stage)((Node)event.getSource()).getScene().getWindow();
	    	  scene = new Scene(root);
	    	  root.requestFocus();
	    	  stage.setScene(scene);
	    	 stage.show();
	    	 
	     }
	   public void VerMochila(ActionEvent event) throws IOException {
		   FXMLLoader loader = new FXMLLoader(getClass().getResource("Mochila.fxml"));
           Parent root = loader.load();
           Mochila Mochila = loader.getController();
           Mochila.initialize(prota, layout, prota2, prota3, curaciones, totems);
           stage = (Stage)((Node)event.getSource()).getScene().getWindow();
	    	  scene = new Scene(root);
	    	  root.requestFocus();
	    	  stage.setScene(scene);
	    	 stage.show();
}
}

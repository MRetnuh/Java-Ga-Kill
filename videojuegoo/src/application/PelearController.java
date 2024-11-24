package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class PelearController {

    @FXML
    private ProgressBar vidaProta, vidaEnemigo, Habilidad;
    @FXML
    private Label nivelAtaque, VidaCantidad, AtaqueEnemigo, VidaEnemigo;
    @FXML
    private Button atacar, vida, especial, personajes;
    @FXML
    public ImageView personaje, enemigo;
    @FXML
    private Line efecto;
    @FXML
    private static MediaPlayer mediaPlayer;
    private Stage stage;
    private boolean playerTurn = true;
    private Personaje prota;
    private Personaje Enemigo;
    private Scene scene;
    private int protaVida;
    private int protavidamax;
    private int enemigoVida;
    private int enemigoVidamax;
    private int protaAtaque;
    private int protanivel;
    private int enemigoAtaque;
    private int protaExperiencia;
	private int protaExpLimite;
	private int curaciones;
    private double posX;
    private double posY;
    private int[][] layout;
    private int enemigoRow;
    private int enemigoCol;
    public int enemy;
    public int habilidad =0;
    // Método para recibir los personajes y configurar los datos iniciales
    public void setPersonajes(Personaje prota, Personaje enemigo, int[][] layout, int enemigoRow, int enemigoCol, int enemy, int curaciones) {
        this.prota = prota;
        this.Enemigo = enemigo;
        // Configura las barras de progreso de vida usando la salud actual y la vida máxima del personaje
        this.protaVida = prota.salud;
        this.protaAtaque = prota.daño;
        this.enemigoVida = enemigo.salud;
        this.enemigoAtaque = enemigo.daño;
        this.enemigoVidamax = enemigo.vidaMaxima;
        this.layout = layout;
        this.enemigoRow = enemigoRow;
        this.enemigoCol = enemigoCol;
        this.posX = prota.posX;
        this.posY = prota.posY;
        this.curaciones = curaciones;
        this.protanivel = prota.nivel;
        this.protavidamax = prota.vidaMaxima;
        this.protaExperiencia = prota.experienciaActual;
        this.protaExpLimite = prota.experienciaLimite;
        vidaProta.setProgress((double) protaVida / prota.vidaMaxima);
        vidaEnemigo.setProgress((double) enemigoVida / enemigo.vidaMaxima);
        Habilidad.setProgress((double) habilidad / 2);
        actualizarLabels();
        
    }
    @FXML
    public void initialize() {
          // Configurar el audio de fondo
        if (mediaPlayer == null) {
            
            String rutaNivel1Audio = getClass().getResource("/Resources/pelea.mp3").toExternalForm();
            Media nivel1Media = new Media(rutaNivel1Audio);
            mediaPlayer = new MediaPlayer(nivel1Media);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.play();
        }
        else if (mediaPlayer != null) {
            
            String rutaNivel1Audio = getClass().getResource("/Resources/pelea.mp3").toExternalForm();
            Media nivel1Media = new Media(rutaNivel1Audio);
            mediaPlayer = new MediaPlayer(nivel1Media);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.play();
        }

        // Configurar los eventos de los botones
        atacar.setOnAction(e -> realizarAtaque());
        vida.setOnAction(e -> recuperarVida());
        especial.setOnAction(e -> usarHabilidadEspecial());
    }
    private void mostrarEfecto(boolean esJugador) {
        double startX;
        double startY;
        double endX;
        double endY;

        // Ajustar las posiciones según quién ataca
        if (esJugador) {
            // Coordenadas del jugador atacando al enemigo
            startX = personaje.getLayoutX() - 80; // Centro del personaje
            startY = personaje.getLayoutY() - 320; // Parte superior del personaje
            endX = enemigo.getLayoutX() - 320; // Centro del enemigo
            endY = enemigo.getLayoutY() + 30; // Centro del enemigo
        } else {
            // Coordenadas del enemigo atacando al jugador
            startX = enemigo.getLayoutX() - 680; // Centro del enemigo
            startY = enemigo.getLayoutY() + 200; // Parte inferior del enemigo
            endX = personaje.getLayoutX() - 480;
            endY = personaje.getLayoutY() +30;
        }

        // Configurar la línea
        efecto.setStartX(startX);
        efecto.setStartY(startY);
        efecto.setEndX(endX);
        efecto.setEndY(endY);
        efecto.setVisible(true);

        // Usar un Timeline para ocultar la línea después de un breve tiempo
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.seconds(0.5), e -> efecto.setVisible(false))
        );
        timeline.play();
    }


    // Acción de ataque del jugador
    private void realizarAtaque() {
         habilidad++;
         Habilidad.setProgress((double) habilidad / 2);
        if (playerTurn) {
            // Animar el efecto desde el jugador hacia el enemigo
            mostrarEfecto(true);

            enemigoVida -= protaAtaque;
            vidaEnemigo.setProgress((double) enemigoVida / Enemigo.vidaMaxima);
            actualizarLabels();
            checkEnemyLife();
            switchTurn();
        }
    }

    // Acción de recuperación de vida del jugador
    private void recuperarVida() {
        if (playerTurn) {
        	 if (curaciones > 0) {
            prota.curarse(30);  // Curar al personaje
            protaVida = prota.salud;  // Actualizar la salud actual del jugador
            vidaProta.setProgress((double) protaVida / protavidamax);
            curaciones--;
            actualizarLabels();
        }
        	 else if(curaciones <= 0) {
        		  vida.setDisable(true); // Deshabilitar el botón
        	        vida.setStyle("-fx-background-color: #ff0000;"); 
        	 }
    }
    }
    // Acción de habilidad especial del jugador
    private void usarHabilidadEspecial() {
        if (playerTurn) {
        	if(habilidad == 2){ // Usar ataque especial
        	 mostrarEfecto(true);
            enemigoVida -= protaAtaque * 2; // Actualizar la salud actual del enemigo
            vidaEnemigo.setProgress((double) enemigoVida / Enemigo.vidaMaxima);
            actualizarLabels();
            checkEnemyLife();
            switchTurn();
            habilidad = 0;
            Habilidad.setProgress((double) habilidad / 2);
        }
    }
    }
    // Cambiar de turno y realizar la acción del enemigo
    private void switchTurn() {
        playerTurn = false;
        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.setOnFinished(event -> {
            realizarAccionEnemigo();
            playerTurn = true;
        });
        pause.play();
    }

    // Acción del enemigo en su turno
    public void realizarAccionEnemigo() {
        if (enemigoVida > 0) {
            // Animar el efecto desde el enemigo hacia el jugador
            mostrarEfecto(false);

            int action = (int) (Math.random() * 3);
            int damage = switch (action) {
                case 0 -> enemigoAtaque;
                case 1 -> enemigoAtaque + 5;
                case 2 -> enemigoAtaque - 5;
                default -> enemigoAtaque;
            };
            prota.recibirdaño(damage);
            protaVida = prota.salud;
            vidaProta.setProgress((double) protaVida / prota.vidaMaxima);
            actualizarLabels();
            checkPlayerLife();
        }
    }

    // Verificar si la vida del enemigo llega a 0 o menos
    private void checkEnemyLife() {
        if (enemigoVida <= 0) {
            enemigoVida = 0;
            prota.experienciaActual += 5;
            if(prota.experienciaActual >= prota.experienciaLimite) {
            	prota.nivel++;
            	prota.daño += 10;
            	prota.salud = prota.vidaMaxima + 20;
            	prota.vidaMaxima = prota.salud;
            	prota.experienciaActual = 0;
            	prota.experienciaLimite += 10;
            	
            }
            vidaEnemigo.setProgress(0);
            actualizarLabels();
            finDeLaPelea();
        }
    }

    // Verificar si la vida del jugador llega a 0 o menos
    private void checkPlayerLife() {
    	try {
        if (protaVida <= 0) {
            protaVida = 0;
            vidaProta.setProgress(0);
            actualizarLabels();
            if (mediaPlayer != null) {
                mediaPlayer.stop();  // Detener la música de intro
                mediaPlayer.dispose();  // Liberar los recursos
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Derrota.fxml"));
            Parent root = loader.load();
            stage = (Stage) personaje.getScene().getWindow();
            scene = new Scene(root);
            root.requestFocus();
            stage.setScene(scene);
            stage.show();
        }
    	}catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Actualizar los valores de los labels con la información actual
    private void actualizarLabels() {
        VidaCantidad.setText("Vida: " + protaVida);
        nivelAtaque.setText("Ataque: " + protaAtaque);
        VidaEnemigo.setText("Vida: " + enemigoVida);
        AtaqueEnemigo.setText("Ataque: " + enemigoAtaque);
    }

    // Método que se ejecuta al finalizar la pelea
    public void finDeLaPelea() {
        try {
            mediaPlayer.stop();
            layout[enemigoRow][enemigoCol] = 1;
            if(enemigoVidamax == 180) {
           	 layout[enemigoRow][enemigoCol] = 2;
           }
            FXMLLoader loader = new FXMLLoader(getClass().getResource("primeraisla.fxml"));
            Parent root = loader.load();
            MovimientoController movimientoController = loader.getController();
            movimientoController.layout = this.layout;
            movimientoController.Akame.salud = prota.salud;
            movimientoController.Akame.vidaMaxima = prota.vidaMaxima;
            movimientoController.Akame.posX = prota.posX;
            movimientoController.Akame.posY = prota.posY;
            movimientoController.Akame.experienciaActual = prota.experienciaActual;
            movimientoController.Akame.daño = prota.daño;
            movimientoController.Akame.nivel = prota.nivel;
            movimientoController.Akame.experienciaLimite = prota.experienciaLimite;
            movimientoController.initialize();
            stage = (Stage) personaje.getScene().getWindow();
            scene = new Scene(root);
            root.requestFocus();
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

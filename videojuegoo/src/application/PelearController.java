package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class PelearController {

    @FXML
    private ProgressBar vidaProta, vidaEnemigo;
    @FXML
    private Label nivelAtaque, VidaCantidad, AtaqueEnemigo, VidaEnemigo;
    @FXML
    private Button atacar, vida, especial, personajes;
    @FXML
    private ImageView personaje, enemigo;
    @FXML
    private static MediaPlayer mediaPlayer;
    private Stage stage;
    private boolean playerTurn = true;
    private Personaje prota;
    private Personaje Enemigo;
    private Scene scene;
    private int protaVida;
    private int enemigoVida;
    private int protaAtaque;
    private int enemigoAtaque;
    private double posX;
    private double posY;
    private int[][] layout;
    private int enemigoRow;
    private int enemigoCol;

    // Método para recibir los personajes y configurar los datos iniciales
    public void setPersonajes(Personaje prota, Personaje enemigo, int[][] layout, int enemigoRow, int enemigoCol) {
        this.prota = prota;
        this.Enemigo = enemigo;

        // Configura las barras de progreso de vida usando la salud actual y la vida máxima del personaje
        this.protaVida = prota.salud;
        this.protaAtaque = prota.daño;
        this.enemigoVida = enemigo.salud;
        this.enemigoAtaque = enemigo.daño;
        this.layout = layout;
        this.enemigoRow = enemigoRow;
        this.enemigoCol = enemigoCol;
        this.posX = prota.posX;
        this.posY = prota.posY;
        System.out.println("caca " + prota.posX);
        System.out.println("caca " + prota.posY);
        vidaProta.setProgress((double) protaVida / prota.vidaMaxima);
        vidaEnemigo.setProgress((double) enemigoVida / enemigo.vidaMaxima);
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
        } else if (mediaPlayer.getStatus() == MediaPlayer.Status.PAUSED) {
            mediaPlayer.play();
        }

        // Configurar los eventos de los botones
        atacar.setOnAction(e -> realizarAtaque());
        vida.setOnAction(e -> recuperarVida());
        especial.setOnAction(e -> usarHabilidadEspecial());
    }

    // Acción de ataque del jugador
    private void realizarAtaque() {
        if (playerTurn) {
            enemigoVida -= protaAtaque;  // Actualizar la salud actual del enemigo
            vidaEnemigo.setProgress((double) enemigoVida / Enemigo.vidaMaxima);
            actualizarLabels();
            checkEnemyLife();
            switchTurn();
        }
    }

    // Acción de recuperación de vida del jugador
    private void recuperarVida() {
        if (playerTurn) {
            prota.curarse(20);  // Curar al personaje
            protaVida = prota.salud;  // Actualizar la salud actual del jugador
            vidaProta.setProgress((double) protaVida / prota.vidaMaxima);
            actualizarLabels();
            switchTurn();
        }
    }

    // Acción de habilidad especial del jugador
    private void usarHabilidadEspecial() {
        if (playerTurn) {// Usar ataque especial
            enemigoVida -= protaAtaque * 2; // Actualizar la salud actual del enemigo
            vidaEnemigo.setProgress((double) enemigoVida / Enemigo.vidaMaxima);
            actualizarLabels();
            checkEnemyLife();
            switchTurn();
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
    private void realizarAccionEnemigo() {
        if (enemigoVida > 0) {
            int action = (int) (Math.random() * 3);
            int damage = switch (action) {
                case 0 -> enemigoAtaque;
                case 1 -> enemigoAtaque + 5;
                case 2 -> enemigoAtaque - 5;
                default -> enemigoAtaque;
            };
            prota.recibirdaño(damage);  // El enemigo ataca al jugador
            protaVida = prota.salud;  // Actualizar la salud actual del jugador
            vidaProta.setProgress((double) protaVida / prota.vidaMaxima);
            actualizarLabels();
            checkPlayerLife();
        }
    }

    // Verificar si la vida del enemigo llega a 0 o menos
    private void checkEnemyLife() {
        if (enemigoVida <= 0) {
            enemigoVida = 0;
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("primeraisla.fxml"));
            Parent root = loader.load();
            MovimientoController movimientoController = loader.getController();
            movimientoController.layout = this.layout;
            movimientoController.Akame.salud = prota.salud;
            movimientoController.Akame.posX = prota.posX;
            movimientoController.Akame.posY = prota.posY;
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

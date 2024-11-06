package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
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

    private boolean playerTurn = true;
    private Personaje prota;
    private Personaje Enemigo;

    private int protaVida;
    private int enemigoVida;
    private int protaAtaque;
    private int enemigoAtaque;

    // Método para recibir los personajes y configurar los datos iniciales
    public void setPersonajes(Personaje prota, Personaje enemigo) {
        this.prota = prota;
        this.Enemigo = enemigo;

        // Inicializa los valores de vida y ataque
        this.protaVida = prota.salud;
        this.protaAtaque = prota.daño;
        this.enemigoVida = enemigo.salud;  // Corrige de "enemigo.salud" a "enemigoVida"
        this.enemigoAtaque = enemigo.daño;

        // Configura las barras de progreso de vida usando vida máxima
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
            enemigoVida -= protaAtaque;
            vidaEnemigo.setProgress(enemigoVida / 100.0);
            actualizarLabels();
            checkEnemyLife();
            switchTurn();
        }
    }

    // Acción de recuperación de vida del jugador
    private void recuperarVida() {
        if (playerTurn) {
            protaVida = Math.min(protaVida + 20, 100);
            vidaProta.setProgress(protaVida / 100.0);
            actualizarLabels();
            switchTurn();
        }
    }

    // Acción de habilidad especial del jugador
    private void usarHabilidadEspecial() {
        if (playerTurn) {
            enemigoVida -= 25;
            vidaEnemigo.setProgress(enemigoVida / 100.0);
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
            protaVida -= damage;
            protaVida = Math.max(protaVida, 0);
            vidaProta.setProgress(protaVida / 100.0);
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
        }
    }

    // Verificar si la vida del jugador llega a 0 o menos
    private void checkPlayerLife() {
        if (protaVida <= 0) {
            protaVida = 0;
            vidaProta.setProgress(0);
            actualizarLabels();
        }
    }

    // Actualizar los valores de los labels con la información actual
    private void actualizarLabels() {
        VidaCantidad.setText("Vida: " + protaVida);
        nivelAtaque.setText("Ataque: " + protaAtaque);
        VidaEnemigo.setText("Vida: " + enemigoVida);
        AtaqueEnemigo.setText("Ataque: " + enemigoAtaque);
    }
}

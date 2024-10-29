package application;

import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.animation.AnimationTimer;

import javafx.scene.media.MediaPlayer;
public class MovimientoController {
	 private MediaPlayer mediaPlayer;
    @FXML
    private AnchorPane rootPane;

    private Personaje personaje;
    private boolean moveUp, moveDown, moveLeft, moveRight;
    private long lastTime = 0;
    private int frames = 0;
    private long fpsLastTime = 0;

    @FXML
    public void initialize() {
    	 String rutaNivel1Audio = getClass().getResource("/Resources/primeraisla.mp3").toExternalForm();
         Media nivel1Media = new Media(rutaNivel1Audio);
         mediaPlayer = new MediaPlayer(nivel1Media);
         mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Repetir indefinidamente
         mediaPlayer.play();
        // Inicializa la instancia de Personaje
        personaje = new Personaje();
        
        // Coloca el ImageView del personaje en la posición inicial (x=50, y=50)
        personaje.getImageView().setX(50);
        personaje.getImageView().setY(50);
        rootPane.getChildren().add(personaje.getImageView());

        // Inicia el AnimationTimer para actualizar la posición y animación del personaje
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                double elapsedTime = (now - lastTime) / 1_000_000_000.0;
                lastTime = now;

                if (elapsedTime > 0.1) {
                    elapsedTime = 0.016;
                }

                updatePosition(elapsedTime);
                personaje.update();  // Actualiza la animación del personaje

                frames++;
                if (fpsLastTime == 0) {
                    fpsLastTime = now;
                }
                if (now - fpsLastTime >= 1_000_000_000) {
                    System.out.println("FPS: " + frames);
                    frames = 0;
                    fpsLastTime = now;
                }
            }
        };
        timer.start();

        rootPane.setOnKeyPressed(this::handleKeyPress);
        rootPane.setOnKeyReleased(this::handleKeyRelease);
        rootPane.setFocusTraversable(true);
        rootPane.requestFocus();
    }

    private void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
            case W -> { 
                moveUp = true;
                personaje.startMoving("espalda");
            }
            case S -> { 
                moveDown = true;
                personaje.startMoving("frente");
            }
            case A -> { 
                moveLeft = true;
                personaje.startMoving("izquierda");
            }
            case D -> { 
                moveRight = true;
                personaje.startMoving("derecha");
            }
        }
    }

    private void handleKeyRelease(KeyEvent event) {
        switch (event.getCode()) {
            case W -> moveUp = false;
            case S -> moveDown = false;
            case A -> moveLeft = false;
            case D -> moveRight = false;
        }
        
        // Detenemos el movimiento si no hay teclas presionadas
        if (!moveUp && !moveDown && !moveLeft && !moveRight) {
            personaje.stopMoving();
        }
    }

    private void updatePosition(double elapsedTime) {
        double speed = 200;

        // Actualiza la posición de ImageView del personaje
        if (moveUp) personaje.getImageView().setY(personaje.getImageView().getY() - speed * elapsedTime);
        if (moveDown) personaje.getImageView().setY(personaje.getImageView().getY() + speed * elapsedTime);
        if (moveLeft) personaje.getImageView().setX(personaje.getImageView().getX() - speed * elapsedTime);
        if (moveRight) personaje.getImageView().setX(personaje.getImageView().getX() + speed * elapsedTime);
    }
}

package application;

import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.animation.AnimationTimer;
import javafx.scene.shape.Rectangle;
import javafx.geometry.Bounds;
import java.util.ArrayList;
import java.util.List;

public class MovimientoController {
    private MediaPlayer mediaPlayer;
    @FXML
    private AnchorPane rootPane;

    private Personaje personaje;
    private boolean moveUp, moveDown, moveLeft, moveRight;
    private long lastTime = 0;
    private int frames = 0;
    private long fpsLastTime = 0;

    private List<Rectangle> obstacles = new ArrayList<>(); // Lista de áreas de colisión

    @FXML
    public void initialize() {
        String rutaNivel1Audio = getClass().getResource("/Resources/primeraisla.mp3").toExternalForm();
        Media nivel1Media = new Media(rutaNivel1Audio);
        mediaPlayer = new MediaPlayer(nivel1Media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();

        personaje = new Personaje();
        personaje.getImageView().setX(50);
        personaje.getImageView().setY(50);
        rootPane.getChildren().add(personaje.getImageView());

        // Define áreas de colisión (obstáculos) en el mapa
        obstacles.add(new Rectangle(200, 150, 100, 100)); // Ejemplo de obstáculo
        obstacles.add(new Rectangle(400, 300, 150, 150)); // Otro ejemplo

        // Añadir obstáculos al rootPane para visualizarlos (opcional)
        obstacles.forEach(rootPane.getChildren()::add);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                double elapsedTime = (now - lastTime) / 1_000_000_000.0;
                lastTime = now;

                if (elapsedTime > 0.1) {
                    elapsedTime = 0.016;
                }

                updatePosition(elapsedTime);
                personaje.update();

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

        if (!moveUp && !moveDown && !moveLeft && !moveRight) {
            personaje.stopMoving();
        }
    }

    private void updatePosition(double elapsedTime) {
        double speed = 200;
        double mapWidth = 1200;
        double mapHeight = 1200;

        double newX = personaje.getImageView().getX();
        double newY = personaje.getImageView().getY();

        if (moveUp) newY -= speed * elapsedTime;
        if (moveDown) newY += speed * elapsedTime;
        if (moveLeft) newX -= speed * elapsedTime;
        if (moveRight) newX += speed * elapsedTime;

        newX = Math.max(0, Math.min(newX, mapWidth - personaje.getImageView().getBoundsInParent().getWidth()));
        newY = Math.max(0, Math.min(newY, mapHeight - personaje.getImageView().getBoundsInParent().getHeight()));

        if (!isCollision(newX, newY)) {
            personaje.getImageView().setX(newX);
            personaje.getImageView().setY(newY);

            double centerX = 300;
            double centerY = 300;
            double offsetX = Math.min(Math.max(centerX - newX, 300 - mapWidth), 0);
            double offsetY = Math.min(Math.max(centerY - newY, 300 - mapHeight), 0);

            rootPane.setLayoutX(offsetX);
            rootPane.setLayoutY(offsetY);
        }
    }

    private boolean isCollision(double x, double y) {
        Bounds characterBounds = personaje.getImageView().getBoundsInParent();

        for (Rectangle obstacle : obstacles) {
            Bounds obstacleBounds = obstacle.getBoundsInParent();
            if (obstacleBounds.intersects(x, y, characterBounds.getWidth(), characterBounds.getHeight())) {
                return true; // Colisión detectada
            }
        }
        return false; // No hay colisión
    }
}

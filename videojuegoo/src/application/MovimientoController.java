package application;

import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.animation.AnimationTimer;
import javafx.scene.shape.Rectangle;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;

public class MovimientoController {
    private MediaPlayer mediaPlayer;
    @FXML
    private AnchorPane rootPane;

    private final int TILE_SIZE = 40; // Tamaño de cada tile (imagen) en píxeles
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

        // Cargar el mapa en el AnchorPane
        cargarMapa();

        rootPane.getChildren().add(personaje.getImageView()); // Añadir personaje al final para estar encima

        // Define áreas de colisión (obstáculos) en el mapa
        Rectangle obstacle1 = new Rectangle(200, 150, 100, 100); // Ejemplo de obstáculo
        obstacle1.setFill(Color.TRANSPARENT); // Hacerlo invisible
        obstacles.add(obstacle1);

        Rectangle obstacle2 = new Rectangle(400, 300, 150, 150); // Otro ejemplo
        obstacle2.setFill(Color.TRANSPARENT); // Hacerlo invisible
        obstacles.add(obstacle2);

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

    private void cargarMapa() {
        String basePath = "/Mapa/";

        Image pastoImg = new Image(getClass().getResourceAsStream(basePath + "pasto.png"));
        Image arenaImg = new Image(getClass().getResourceAsStream(basePath + "arena.png"));
        Image casaImg = new Image(getClass().getResourceAsStream(basePath + "casa.png"));
        Image arbolImg = new Image(getClass().getResourceAsStream(basePath + "arbol1.png"));
        Image piso1Img = new Image(getClass().getResourceAsStream(basePath + "piso1.png"));
        Image piso2Img = new Image(getClass().getResourceAsStream(basePath + "piso2.png"));

        int[][] layout = {
            {1, 1, 2, 2, 1, 1, 4, 4, 1, 1},
            {1, 1, 2, 2, 1, 1, 4, 4, 1, 1},
            {1, 1, 0, 0, 0, 0, 4, 4, 1, 1},
            {1, 1, 0, 3, 3, 0, 0, 0, 1, 1},
            {1, 1, 0, 3, 3, 0, 0, 0, 1, 1},
            {1, 1, 0, 0, 0, 0, 5, 5, 1, 1},
            {1, 1, 0, 0, 0, 0, 5, 5, 1, 1},
            {1, 1, 0, 0, 0, 0, 5, 5, 1, 1},
            {1, 1, 2, 2, 0, 0, 5, 5, 1, 1},
            {1, 1, 2, 2, 1, 1, 5, 5, 1, 1},
        };

        // Dibujar primero las capas inferiores (pasto, arena)
        for (int row = 0; row < layout.length; row++) {
            for (int col = 0; col < layout[row].length; col++) {
                ImageView tile = new ImageView();
                switch (layout[row][col]) {
                    case 0:
                        tile.setImage(pastoImg);
                        break;
                    case 1:
                        tile.setImage(arenaImg);
                        break;
                    case 4:
                        tile.setImage(piso1Img);
                        break;
                    case 5:
                        tile.setImage(piso2Img);
                        break;
                }

                tile.setFitWidth(TILE_SIZE);
                tile.setFitHeight(TILE_SIZE);
                tile.setLayoutX(col * TILE_SIZE);
                tile.setLayoutY(row * TILE_SIZE);

                rootPane.getChildren().add(tile); // Añadir el tile de fondo al rootPane
            }
        }

        // Dibujar después los objetos que deben estar sobre el terreno (árboles, casas)
        for (int row = 0; row < layout.length; row++) {
            for (int col = 0; col < layout[row].length; col++) {
                ImageView tile = new ImageView();
                switch (layout[row][col]) {
                    case 2:
                        tile.setImage(casaImg);
                        break;
                    case 3:
                        tile.setImage(arbolImg);
                        break;
                }

                tile.setFitWidth(TILE_SIZE);
                tile.setFitHeight(TILE_SIZE);
                tile.setLayoutX(col * TILE_SIZE);
                tile.setLayoutY(row * TILE_SIZE);

                rootPane.getChildren().add(tile); // Añadir los árboles y casas encima del terreno
            }
        }
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

        Bounds personajeBounds = personaje.getImageView().getBoundsInParent();

        boolean collision = false;
        for (Rectangle obstacle : obstacles) {
            if (obstacle.getBoundsInParent().intersects(personajeBounds)) {
                collision = true;
                break;
            }
        }

        if (!collision) {
            personaje.getImageView().setX(newX);
            personaje.getImageView().setY(newY);
        }
    }
}

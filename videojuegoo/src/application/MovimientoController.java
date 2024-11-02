package application;

import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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

    private int[][] layout = {
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        {1, 1, 1, 1, 1, 3, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        {1, 1, 1, 1, 1, 3, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        {1, 1, 1, 1, 1, 3, 3, 1, 1, 1, 1, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 4, 4, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
    };

    private final int VIEWPORT_WIDTH = 800;
    private final int VIEWPORT_HEIGHT = 600;

    @FXML
    public void initialize() {
        String rutaNivel1Audio = getClass().getResource("/Resources/primeraisla.mp3").toExternalForm();
        Media nivel1Media = new Media(rutaNivel1Audio);
        mediaPlayer = new MediaPlayer(nivel1Media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();

        personaje = new Personaje();
        personaje.getImageView().setX(150);
        personaje.getImageView().setY(150);

        cargarMapa();

        rootPane.getChildren().add(personaje.getImageView()); // Añadir personaje al final para estar encima

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                double elapsedTime = (now - lastTime) / 1_000_000_000.0;
                lastTime = now;

                if (elapsedTime > 0.1) {
                    elapsedTime = 0.016;
                }

                updatePosition(elapsedTime);
                updateCamera();
                personaje.update();

                frames++;
                if (fpsLastTime == 0) {
                    fpsLastTime = now;
                }
                if (now - fpsLastTime >= 1_000_000_000) {
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

        Image arenaImg = new Image(getClass().getResourceAsStream(basePath + "arena.png"));
        Image casaImg = new Image(getClass().getResourceAsStream(basePath + "casa.png"));
        Image arbolImg = new Image(getClass().getResourceAsStream(basePath + "arbol.png"));
        Image piso1Img = new Image(getClass().getResourceAsStream(basePath + "piso1.png"));

        for (int row = 0; row < layout.length; row++) {
            for (int col = 0; col < layout[row].length; col++) {
                ImageView tile = new ImageView();
                switch (layout[row][col]) {
                    case 1 -> tile.setImage(arenaImg);
                    case 2 -> tile.setImage(piso1Img);
                    case 3 -> tile.setImage(arbolImg);
                    case 4 -> tile.setImage(casaImg);
                }

                tile.setFitWidth(TILE_SIZE);
                tile.setFitHeight(TILE_SIZE);
                tile.setLayoutX(col * TILE_SIZE);
                tile.setLayoutY(row * TILE_SIZE);

                rootPane.getChildren().add(tile); // Añadir el tile de fondo al rootPane
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
        double newX = personaje.getImageView().getX();
        double newY = personaje.getImageView().getY();
        
        double margenColision = 5;

        if (moveUp) newY -= speed * elapsedTime;
        if (moveDown) newY += speed * elapsedTime;
        if (moveLeft) newX -= speed * elapsedTime;
        if (moveRight) newX += speed * elapsedTime;

        int futureRowTop = (int) ((newY + margenColision) / TILE_SIZE);
        int futureRowBottom = (int) ((newY + TILE_SIZE - margenColision) / TILE_SIZE);
        int futureColLeft = (int) ((newX + margenColision) / TILE_SIZE);
        int futureColRight = (int) ((newX + TILE_SIZE - margenColision) / TILE_SIZE);

        if (futureRowTop >= 0 && futureRowBottom < layout.length && futureColLeft >= 0 && futureColRight < layout[0].length) {
            if (layout[futureRowTop][futureColLeft] == 1 && layout[futureRowTop][futureColRight] == 1 &&
                layout[futureRowBottom][futureColLeft] == 1 && layout[futureRowBottom][futureColRight] == 1) {
                personaje.getImageView().setX(newX);
                personaje.getImageView().setY(newY);
            }
        }
    }

    private void updateCamera() {
        double characterX = personaje.getImageView().getX();
        double characterY = personaje.getImageView().getY();

        double offsetX = characterX - VIEWPORT_WIDTH / 2.0;
        double offsetY = characterY - VIEWPORT_HEIGHT / 2.0;

        offsetX = Math.max(0, Math.min(offsetX, TILE_SIZE * layout[0].length - VIEWPORT_WIDTH));
        offsetY = Math.max(0, Math.min(offsetY, TILE_SIZE * layout.length - VIEWPORT_HEIGHT));

        rootPane.setLayoutX(-offsetX);
        rootPane.setLayoutY(-offsetY);
    }
}

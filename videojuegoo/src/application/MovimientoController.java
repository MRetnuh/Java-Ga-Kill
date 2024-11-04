package application;

import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
public class MovimientoController {
	public class PersonajeStruct {
	    public int salud;
	    public int daño;
	    public int nivel;

	    public PersonajeStruct(int salud, int daño, int nivel) {
	        this.salud = salud;
	        this.daño = daño;
	        this.nivel = nivel;
	    }
	}
	PersonajeStruct Akame = new PersonajeStruct(80, 25, 1);
	PersonajeStruct Leone = new PersonajeStruct(160, 5, 1);
	PersonajeStruct Java = new PersonajeStruct(100, 5, 1);
	PersonajeStruct Enemigo1 = new PersonajeStruct(100, 5, 1);
	PersonajeStruct Enemigo2 = new PersonajeStruct(100, 5, 1);
	PersonajeStruct Esdeath = new PersonajeStruct(100, 5, 1);

    private MediaPlayer mediaPlayer;
    @FXML
    private AnchorPane rootPane;

    private final int TILE_SIZE = 60; // Tamaño de cada tile (imagen) en píxeles
    private Personaje personaje1;
    private boolean moveUp, moveDown, moveLeft, moveRight;
    private long lastTime = 0;
    private long fpsLastTime = 0;

    private int[][] layout = {
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        {1, 1, 1, 1, 1, 3, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
        {1, 1, 1, 1, 1, 3, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 5, 1, 1, 1, 1, 6, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
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

        personaje1 = new Personaje();
        personaje1.getImageView().setX(150);
        personaje1.getImageView().setY(150);

        cargarMapa();

        rootPane.getChildren().add(personaje1.getImageView()); // Añadir personaje al final para estar encima

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
                personaje1.update();

                if (fpsLastTime == 0) {
                    fpsLastTime = now;
                }
                if (now - fpsLastTime >= 1_000_000_000) {
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
        Image enemy1Img = new Image(getClass().getResourceAsStream(basePath + "enemy11.png"));
        Image enemy2Img = new Image(getClass().getResourceAsStream(basePath + "enemy22.png"));
        for (int row = 0; row < layout.length; row++) {
            for (int col = 0; col < layout[row].length; col++) {
                ImageView tile = new ImageView();
                switch (layout[row][col]) {
                    case 1:
                    	tile.setImage(arenaImg);
                    	break;
                    case 2:
                    tile.setImage(piso1Img);
                        break;
                    case 3:
                    	tile.setImage(arbolImg);
                    	break;
                    case 4:
                    	tile.setImage(casaImg);
                    	break;
                    case 5:
                    	tile.setImage(enemy1Img);
                    	 tile.setUserData("enemigo");
                    	break;
                    case 6:
                    	tile.setImage(enemy2Img);
                    	break;
                    case 7:
                    	tile.setImage(casaImg);
                    	break;
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
                personaje1.startMoving("espalda");
            }
            case S -> { 
                moveDown = true;
                personaje1.startMoving("frente");
            }
            case A -> { 
                moveLeft = true;
                personaje1.startMoving("izquierda");
            }
            case D -> { 
                moveRight = true;
                personaje1.startMoving("derecha");
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
            personaje1.stopMoving();
        }
    }
    private void cambiarEscena(String primerArchivo, String segundoArchivo) {
        try {
        	Stage stage = (Stage) rootPane.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource(primerArchivo));
            stage.setScene(new Scene(root));
            stage.show();

            // Cambiar al segundo archivo después de un retraso de 2 segundos
            new Thread(() -> {
                try {
                    Thread.sleep(2000);
                    Platform.runLater(() -> {
                        try {
                            Parent root2 = FXMLLoader.load(getClass().getResource(segundoArchivo));
                            stage.setScene(new Scene(root2));
                            stage.show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void updatePosition(double elapsedTime) {
        double speed = 200;
        double newX = personaje1.getImageView().getX();
        double newY = personaje1.getImageView().getY();
        
        double margenColision = 5;

        if (moveUp) newY -= speed * elapsedTime;
        if (moveDown) newY += speed * elapsedTime;
        if (moveLeft) newX -= speed * elapsedTime;
        if (moveRight) newX += speed * elapsedTime;

        int futureRowTop = (int) ((newY + 30) / TILE_SIZE);
        int futureRowBottom = (int) ((newY + TILE_SIZE - margenColision) / TILE_SIZE);
        int futureColLeft = (int) ((newX + margenColision) / TILE_SIZE);
        int futureColRight = (int) ((newX + TILE_SIZE - 30) / TILE_SIZE);

        if (futureRowTop >= 0 && futureRowBottom < layout.length && futureColLeft >= 0 && futureColRight < layout[0].length) {
            if (layout[futureRowTop][futureColLeft] == 1 && layout[futureRowTop][futureColRight] == 1 &&
                layout[futureRowBottom][futureColLeft] == 1 && layout[futureRowBottom][futureColRight] == 1) {
                personaje1.getImageView().setX(newX);
                personaje1.getImageView().setY(newY);
            }
        }
        int topLeftTile = layout[futureRowTop][futureColLeft];
        int topRightTile = layout[futureRowTop][futureColRight];
        int bottomLeftTile = layout[futureRowBottom][futureColLeft];
        int bottomRightTile = layout[futureRowBottom][futureColRight];

        // Verificar colisión con paredes
        boolean isWalkable = topLeftTile == 1 && topRightTile == 1 && bottomLeftTile == 1 && bottomRightTile == 1;

        if (isWalkable) {
            personaje1.getImageView().setX(newX);
            personaje1.getImageView().setY(newY);
        }
        
        // Verificar colisión con enemigo
        if (topLeftTile == 5 || topRightTile == 5 || bottomLeftTile == 5 || bottomRightTile == 5 ||
            topLeftTile == 6 || topRightTile == 6 || bottomLeftTile == 6 || bottomRightTile == 6) {
        	
        	  mediaPlayer.stop();
            // Detenemos la música de movimiento
       
            
            // Cambiar a la escena de pelea, si aún no ha sido cambiada
            cambiarEscena("introPelea.fxml", "Pelea.fxml");
        }
    }


    private void updateCamera() {
        double characterX = personaje1.getImageView().getX();
        double characterY = personaje1.getImageView().getY();

        double offsetX = characterX - VIEWPORT_WIDTH / 2.0;
        double offsetY = characterY - VIEWPORT_HEIGHT / 2.0;

        offsetX = Math.max(0, Math.min(offsetX, TILE_SIZE * layout[0].length - VIEWPORT_WIDTH));
        offsetY = Math.max(0, Math.min(offsetY, TILE_SIZE * layout.length - VIEWPORT_HEIGHT));

        rootPane.setLayoutX(-offsetX);
        rootPane.setLayoutY(-offsetY);
    }
}

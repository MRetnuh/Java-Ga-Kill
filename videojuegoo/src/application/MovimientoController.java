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
	  Personaje Akame = new Personaje(110, 30, 1, 1, 20, 100,150, 150);
	    Personaje Leone = new Personaje(150, 15, 1, 1, 20, 140,150, 150);
	    Personaje Java = new Personaje(120, 20, 1, 1, 20, 110,150, 150);
	    Personaje Enemigo1 = new Personaje(95, 20, 1, 1, 20, 90,150, 150);
	    Personaje Enemigo2 = new Personaje(180, 30, 1, 1, 20, 180,150, 150);
	    Personaje Esdeath = new Personaje(350, 40, 1, 1, 20, 350,150, 150);
    private MediaPlayer mediaPlayer;
    @FXML
    private AnchorPane rootPane;
    
    private final int TILE_SIZE = 60; // Tamaño de cada tile (imagen) en píxeles
    private Personaje personaje1;
    private boolean moveUp, moveDown, moveLeft, moveRight;
    private long lastTime = 0;
    private long fpsLastTime = 0;
    public int[][] layout = {
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
    	 if (mediaPlayer != null) {
             mediaPlayer.stop();  // Detener la música de intro
             mediaPlayer.dispose();  // Liberar los recursos
         }

        String rutaNivel1Audio = getClass().getResource("/Resources/primeraisla.mp3").toExternalForm();
        Media nivel1Media = new Media(rutaNivel1Audio);
        mediaPlayer = new MediaPlayer(nivel1Media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();

        personaje1 = new Personaje();
        personaje1.getImageView().setX(Akame.posX);
        personaje1.getImageView().setY(Akame.posY);
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

    public void cargarMapa() {
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
    private boolean peleando = false;
    private void cambiarEscena(String primerArchivo, String segundoArchivo, int enemigoRow, int enemigoCol) {
        if (!peleando) {
            peleando = true;
            mediaPlayer.stop();
        }

        try {
            Stage stage = (Stage) rootPane.getScene().getWindow();

            // Cargar la primera escena (puede ser una escena de introducción)
            Parent root1 = FXMLLoader.load(getClass().getResource(primerArchivo));
            stage.setScene(new Scene(root1));
            stage.show();

            new Thread(() -> {
                try {
                    Thread.sleep(2000); // Espera de 2 segundos

                    Platform.runLater(() -> {
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource(segundoArchivo));
                            Parent root2 = loader.load();

                            // Obtener el controlador de PelearController
                            PelearController pelearController = loader.getController();

                            // Configurar el personaje y enemigo para la pelea
                            Personaje prota = Akame;  
                            Personaje enemigo = Enemigo1;  

                            // Pasar los personajes, layout y las coordenadas del enemigo al controlador de pelea
                            pelearController.setPersonajes(prota, enemigo, layout, enemigoRow, enemigoCol);

                            // Cambia la escena a la pelea
                            stage.setScene(new Scene(root2));
                            stage.show();

                            // Añadir un listener para actualizar el mapa al terminar la pelea
                            stage.setOnHidden(event -> {
                                peleando = false;
                                pelearController.finDeLaPelea(); // Actualiza el layout en la posición del enemigo
                                cargarMapa(); // Recarga el mapa para reflejar cambios
                            });

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


    public void updatePosition(double elapsedTime) {
        double speed = 200;
        double newX = personaje1.getImageView().getX();
        double newY = personaje1.getImageView().getY();
        double margenColision = 5;
        if (moveUp) newY -= speed * elapsedTime;
        if (moveDown) newY += speed * elapsedTime;
        if (moveLeft) newX -= speed * elapsedTime;
        if (moveRight) newX += speed * elapsedTime;
        if (!peleando) {
            if (moveUp) Akame.posY -= speed * elapsedTime;
            if (moveDown) Akame.posY += speed * elapsedTime;
            if (moveLeft) Akame.posX -= speed * elapsedTime;
            if (moveRight) Akame.posX += speed * elapsedTime;
        }

        System.out.println(" " + Akame.posX);
        System.out.println(" " + Akame.posY);
        int futureRowTop = Math.max(0, Math.min((int) ((Akame.posY + 30) / TILE_SIZE), layout.length - 1));
        int futureRowBottom = Math.max(0, Math.min((int) ((Akame.posY + TILE_SIZE - margenColision) / TILE_SIZE), layout.length - 1));
        int futureColLeft = Math.max(0, Math.min((int) ((Akame.posX + margenColision) / TILE_SIZE), layout[0].length - 1));
        int futureColRight = Math.max(0, Math.min((int) ((Akame.posX + TILE_SIZE - 30) / TILE_SIZE), layout[0].length - 1));

        int topLeftTile = layout[futureRowTop][futureColLeft];
        int topRightTile = layout[futureRowTop][futureColRight];
        int bottomLeftTile = layout[futureRowBottom][futureColLeft];
        int bottomRightTile = layout[futureRowBottom][futureColRight];

        boolean isWalkable = topLeftTile == 1 && topRightTile == 1 && bottomLeftTile == 1 && bottomRightTile == 1;

        if (isWalkable) {
            personaje1.getImageView().setX(Akame.posX);
            personaje1.getImageView().setY(Akame.posY);
         
        }

        if (!peleando && (topLeftTile == 5 || topRightTile == 5 || bottomLeftTile == 5 || bottomRightTile == 5 ||
                topLeftTile == 6 || topRightTile == 6 || bottomLeftTile == 6 || bottomRightTile == 6)) {
  // Almacena la posición del enemigo en el layout antes de cambiar de escena
  if (topLeftTile == 5 || topLeftTile == 6) {
      layout[futureRowTop][futureColLeft] = 1;
      cambiarEscena("introPelea.fxml", "Pelea.fxml", futureRowTop, futureColLeft);
  } else if (topRightTile == 5 || topRightTile == 6) {
      layout[futureRowTop][futureColRight] = 1;
      cambiarEscena("introPelea.fxml", "Pelea.fxml", futureRowTop, futureColRight);
  } else if (bottomLeftTile == 5 || bottomLeftTile == 6) {
      layout[futureRowBottom][futureColLeft] = 1;
      cambiarEscena("introPelea.fxml", "Pelea.fxml", futureRowBottom, futureColLeft);
  } else if (bottomRightTile == 5 || bottomRightTile == 6) {
      layout[futureRowBottom][futureColRight] = 1;
      cambiarEscena("introPelea.fxml", "Pelea.fxml", futureRowBottom, futureColRight);
  }
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

package application;

import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.animation.AnimationTimer;

public class MovimientoController {
    
    @FXML
    private AnchorPane rootPane;

    private Rectangle square;
    private boolean moveUp, moveDown, moveLeft, moveRight;
    private long lastTime = 0;
    private int frames = 0;
    private long fpsLastTime = 0;

    @FXML
    public void initialize() {
        // Crear un cuadrado negro en x: 100, y: 100
        square = new Rectangle(50, 50, Color.BLACK);
        square.setX(100);
        square.setY(100);
        
        rootPane.getChildren().add(square);

        // Iniciar AnimationTimer para actualizar la posición del cuadrado
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                // Calcular el tiempo transcurrido desde el último frame en segundos
                double elapsedTime = (now - lastTime) / 1_000_000_000.0;
                lastTime = now;
                
                // Limitar la primera vez que se ejecuta para evitar saltos
                if (elapsedTime > 0.1) {
                    elapsedTime = 0.016; // Aproximadamente 1/60
                }
                
                updatePosition(elapsedTime);

                // Contador de FPS
                frames++;
                if (fpsLastTime == 0) {
                    fpsLastTime = now;
                }
                if (now - fpsLastTime >= 1_000_000_000) { // 1 segundo en nanosegundos
                    System.out.println("FPS: " + frames);
                    frames = 0;
                    fpsLastTime = now;
                }
            }
        };
        timer.start();

        // Agregar EventHandlers para teclas presionadas y soltadas
        rootPane.setOnKeyPressed(this::handleKeyPress);
        rootPane.setOnKeyReleased(this::handleKeyRelease);

        // Solicitar foco para rootPane
        rootPane.setFocusTraversable(true);
        rootPane.requestFocus();
    }

    private void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
            case W -> moveUp = true;
            case S -> moveDown = true;
            case A -> moveLeft = true;
            case D -> moveRight = true;
        }
    }

    private void handleKeyRelease(KeyEvent event) {
        switch (event.getCode()) {
            case W -> moveUp = false;
            case S -> moveDown = false;
            case A -> moveLeft = false;
            case D -> moveRight = false;
        }
    }

    private void updatePosition(double elapsedTime) {
        double speed = 200; // Velocidad en píxeles por segundo

        if (moveUp) square.setY(square.getY() - speed * elapsedTime);
        if (moveDown) square.setY(square.getY() + speed * elapsedTime);
        if (moveLeft) square.setX(square.getX() - speed * elapsedTime);
        if (moveRight) square.setX(square.getX() + speed * elapsedTime);
    }
}

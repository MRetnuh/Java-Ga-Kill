package application;

import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.effect.PerspectiveTransform;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class EntradaController implements Initializable {
	
    @FXML
    private ImageView intro;  // Imagen que contiene el texto dentro

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // Aplicar perspectiva para que el texto parezca cercano desde abajo
        PerspectiveTransform perspective = new PerspectiveTransform();
        perspective.setUlx(200.0);  // Coordenada superior izquierda (x)
        perspective.setUly(0.0);    // Coordenada superior izquierda (y)
        perspective.setUrx(400.0);  // Coordenada superior derecha (x)
        perspective.setUry(0.0);    // Coordenada superior derecha (y)
        perspective.setLlx(0.0);    // Coordenada inferior izquierda (x)
        perspective.setLly(800.0);  // Coordenada inferior izquierda (y)
        perspective.setLrx(600.0);  // Coordenada inferior derecha (x)
        perspective.setLry(800.0);  // Coordenada inferior derecha (y)
        intro.setEffect(perspective);

        // Crear una transición de movimiento para la imagen
        TranslateTransition translate = new TranslateTransition();
        translate.setNode(intro);
        translate.setDuration(Duration.seconds(30));  // Duración del desplazamiento
        translate.setFromY(400);  // Empieza desde abajo
        translate.setToY(-1100);  // Mover hacia arriba (ajustar a -1000 para que salga de la vista)

        // Crear una transición de escala para que el texto se haga más pequeño
        ScaleTransition scale = new ScaleTransition();
        scale.setNode(intro);
        scale.setDuration(Duration.seconds(30));  // Duración de la escala
        scale.setFromX(1.5);  // Comienza con un tamaño grande (150%)
        scale.setFromY(1.5);  // Comienza con un tamaño grande (150%)
        scale.setToX(0.5);    // Finaliza en tamaño pequeño (50%)
        scale.setToY(0.5);    // Finaliza en tamaño pequeño (50%)

        // Iniciar ambas animaciones
        translate.play();
        scale.play();
    }
    
}

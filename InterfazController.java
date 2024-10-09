package application;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;

public class InterfazController {

    @FXML
    private Label labelMensaje;

    @FXML
    private AnchorPane anchorPane;  // El AnchorPane para poder manipular sus hijos (componentes)

    @FXML
    public void mostrarTexto1() {
        labelMensaje.setText("Texto del Botón 1");
    }

    @FXML
    public void mostrarTexto2() {
        labelMensaje.setText("Texto del Botón 2");
    }

    @FXML
    public void mostrarTexto3() {
        limpiarYMostrarCirculo();  // Limpia la pantalla y muestra el círculo cuando se presiona "Jugar"
    }

    private void limpiarYMostrarCirculo() {
        anchorPane.getChildren().clear();  // Elimina todos los elementos del AnchorPane

        // Crear un círculo con radio de 50 y posicionado en el centro
        Circle circulo = new Circle(300, 200, 50); 
        anchorPane.getChildren().add(circulo);  // Añade el círculo al AnchorPane
    }
}

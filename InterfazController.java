package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;

public class InterfazController {

    @FXML
    private Label labelMensaje;

    @FXML
    private AnchorPane anchorPane;  // El AnchorPane para poder manipular sus hijos (componentes)

    @FXML
    public void mostrarTexto1() {
        anchorPane.getChildren().clear();  // Limpiar todos los elementos actuales

        // Crear el Label con el texto "Texto del Botón 1"
        Label texto1 = new Label("Texto del Botón 1");
        texto1.setLayoutX(200);  // Posicionar el Label en el AnchorPane
        texto1.setLayoutY(150);

        // Crear el botón para regresar al inicio
        Button btnVolver = crearBoton("Volver al inicio", 250, 250, 21);
        btnVolver.setOnAction(e -> volverAlInicio());

        // Añadir el Label y el botón al AnchorPane
        anchorPane.getChildren().addAll(texto1, btnVolver);  // Asegúrate de añadir el botón al AnchorPane
    }

    @FXML
    public void mostrarTexto2() {
        anchorPane.getChildren().clear();  // Limpiar los elementos existentes
        Label texto2 = new Label("Texto del Botón 2");  // Crear un nuevo Label con el texto deseado
        texto2.setLayoutX(200);  // Establecer la posición del Label en el AnchorPane
        texto2.setLayoutY(150);

        // Crear el botón para regresar al inicio
        Button btnVolver = crearBoton("Volver al inicio", 250, 250, 21);
        btnVolver.setOnAction(e -> volverAlInicio());

        // Añadir el Label y el botón al AnchorPane
        anchorPane.getChildren().addAll(texto2, btnVolver);
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

        // Crear el botón para regresar al inicio
        Button btnVolver = crearBoton("Volver al inicio", 250, 350, 21);
        btnVolver.setOnAction(e -> volverAlInicio());

        // Añadir el botón de regreso al AnchorPane
        anchorPane.getChildren().add(btnVolver);
    }

    private void volverAlInicio() {
        anchorPane.getChildren().clear();  // Limpiar todos los elementos actuales

        // Crear el primer botón
        Button btn1 = crearBoton("Enemigos", 241, 242, 21);
        btn1.setOnAction(e -> mostrarTexto1());

        // Crear el segundo botón
        Button btn2 = crearBoton("Controles", 242, 178, 21);
        btn2.setOnAction(e -> mostrarTexto2());

        // Crear el tercer botón
        Button btn3 = crearBoton("Jugar", 241, 120, 20);
        btn3.setPrefHeight(36);  // Establecer altura del botón
        btn3.setPrefWidth(119);  // Establecer ancho del botón
        btn3.setOnAction(e -> mostrarTexto3());

        // Añadir todos los botones de vuelta al AnchorPane
        anchorPane.getChildren().addAll(btn1, btn2, btn3);
    }

    // Método para crear botones con propiedades específicas
    private Button crearBoton(String texto, double layoutX, double layoutY, int fontSize) {
        Button boton = new Button(texto);
        boton.setLayoutX(layoutX);  // Posicionar el botón
        boton.setLayoutY(layoutY);
        boton.setFont(new Font(fontSize));  // Asignar tamaño de fuente
        return boton;
    }
}

module videojuegoo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
	requires java.desktop;
	requires javafx.graphics;
    
    // Exporta el paquete application para que pueda ser usado por otros módulos, incluido javafx.graphics
    opens application to javafx.fxml, javafx.graphics;
}
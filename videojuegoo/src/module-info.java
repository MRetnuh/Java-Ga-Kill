module videojuegoo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
	requires java.desktop;
    
    // Exporta el paquete application para que pueda ser usado por otros módulos, incluido javafx.graphics
    opens application to javafx.fxml, javafx.graphics;
}
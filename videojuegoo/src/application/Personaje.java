package application;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Personaje {
	  public int salud;
	    public int daño;
	    public int nivel;
	    public int experienciaActual;
	    public int experienciaLimite;
	    public int vidaMaxima;
	    public double posX;
	    public double posY;
	    public int akame = 1;
	    public int leone = 0;
	    public int java = 0;
	    public Personaje(int salud, int daño, int nivel, int experienciaActual, int experienciaLimite, int vidaMaxima, double posX, double posY) {
	        this.salud = salud;
	        this.daño = daño;
	        this.nivel = nivel;
	        this.experienciaActual = experienciaActual;
	        this.experienciaLimite = experienciaLimite;
	        this.vidaMaxima = vidaMaxima;
	        this.posX = posX;
	        this.posY = posY;
	    }
	    public void recibirdaño(int daño) {
	        salud = Math.max(0, salud - daño); // Evitar que la salud sea menor que 0
	    }

	    public void curarse(int cantidad) {
	        salud = Math.min(vidaMaxima, salud + cantidad); // Asegura que no exceda vidaMaxima
	    }
	    public void setPosicion(double x, double y) {
	        this.posX = x;
	        this.posY = y;
	    }
	// Cambiar a mayúscula inicial
    // Declaración de variables BufferedImage en una sola línea
    public Image imagen1, imagen2, imagen3, imagen4, imagen5, imagen6, imagen7, imagen8;
    public Image imagen9, imagen10, imagen11, imagen12, imagen13, imagen14, imagen15, imagen16;

    // Variables para imágenes idle
    public Image frameDerechaIdle;
    public Image frameIzquierdaIdle;
    public Image frameFrenteIdle;
    public Image frameEspaldaIdle;

    private ImageView imageView;
    private int currentFrame = 0;
    private boolean moving = false;
    private String direction = "frente";
    private long lastFrameTime = 0;

    public Personaje(int akame, int leone, int java) {
        try {
        	if(akame == 1) {
            // Cargar imágenes de movimiento
    		imagen1 = cargarImagen(getClass().getResource("/akame_derecha_moviendose_1.png"));
    		imagen2 = cargarImagen(getClass().getResource("/akame_derecha_moviendose_2.png"));
            imagen3 = cargarImagen(getClass().getResource("/akame_derecha_moviendose_3.png"));
            imagen4 = cargarImagen(getClass().getResource("/akame_derecha_moviendose_4.png"));
            imagen5 = cargarImagen(getClass().getResource("/akame_izquierda_moviendose_1.png"));
            imagen6 = cargarImagen(getClass().getResource("/akame_izquierda_moviendose_2.png"));
            imagen7 = cargarImagen(getClass().getResource("/akame_izquierda_moviendose_3.png"));
            imagen8 = cargarImagen(getClass().getResource("/akame_izquierda_moviendose_4.png"));
            imagen9 = cargarImagen(getClass().getResource("/akame_de_frente_moviendose_1.png"));
            imagen10 = cargarImagen(getClass().getResource("/akame_de_frente_moviendose_2.png"));
            imagen11 = cargarImagen(getClass().getResource("/akame_de_frente_moviendose_3.png"));
            imagen12 = cargarImagen(getClass().getResource("/akame_de_frente_moviendose_4.png"));
            imagen13 = cargarImagen(getClass().getResource("/akame_de_espalda_moviendose_1.png"));
            imagen14 = cargarImagen(getClass().getResource("/akame_de_espalda_moviendose_2.png"));
            imagen15 = cargarImagen(getClass().getResource("/akame_de_espalda_moviendose_3.png"));
            imagen16 = cargarImagen(getClass().getResource("/akame_de_espalda_moviendose_4.png"));

            frameDerechaIdle = new Image(getClass().getResource("/akame_derecha_(detenida).png").toExternalForm());
            frameIzquierdaIdle = new Image(getClass().getResource("/akame_izquierda_(detenida).png").toExternalForm());
            frameFrenteIdle = new Image(getClass().getResource("/akame_de_frente_(detenida).png").toExternalForm());
            frameEspaldaIdle = new Image(getClass().getResource("/akame_de_espaldas_(detenida).png").toExternalForm());


            // Crear el ImageView con la imagen inicial
            imageView = new ImageView(frameFrenteIdle);
        	}
        	if (leone == 1){
        		   imagen1 = cargarImagen(getClass().getResource("/leone_derecha_moviendose1.png"));
                   imagen2 = cargarImagen(getClass().getResource("/leone_derecha_moviendose2.png"));
                   imagen3 = cargarImagen(getClass().getResource("/leone_derecha_moviendose3.png"));
                   imagen4 = cargarImagen(getClass().getResource("/leone_derecha_moviendose4.png"));
                   imagen5 = cargarImagen(getClass().getResource("/leone_izquierda_moviendose1.png"));
                   imagen6 = cargarImagen(getClass().getResource("/leone_izquierda_moviendose2.png"));
                   imagen7 = cargarImagen(getClass().getResource("/leone_izquierda_moviendose3.png"));
                   imagen8 = cargarImagen(getClass().getResource("/leone_izquierda_moviendose4.png"));
                   imagen9 = cargarImagen(getClass().getResource("/leone_de_frente_moviendose1.png"));
                   imagen10 = cargarImagen(getClass().getResource("/leone_de_frente_moviendose2.png"));
                   imagen11 = cargarImagen(getClass().getResource("/leone_de_frente_moviendose3.png"));
                   imagen12 = cargarImagen(getClass().getResource("/leone_de_frente_moviendose4.png"));
                   imagen13 = cargarImagen(getClass().getResource("/leone_de_espalda_moviendose1.png"));
                   imagen14 = cargarImagen(getClass().getResource("/leone_de_espalda_moviendose2.png"));
                   imagen15 = cargarImagen(getClass().getResource("/leone_de_espalda_moviendose3.png"));
                   imagen16 = cargarImagen(getClass().getResource("/leone_de_espalda_moviendose4.png"));

                   // Inicializar imágenes idle
                   frameDerechaIdle = new Image(getClass().getResource("/leone_derecha(detenida).png").toExternalForm());
                   frameIzquierdaIdle = new Image(getClass().getResource("/leone_izquierda(detenida).png").toExternalForm());
                   frameFrenteIdle = new Image(getClass().getResource("/leone_de_frente(detenida).png").toExternalForm());
                   frameEspaldaIdle = new Image(getClass().getResource("/leone_de_espalda(detenida).png").toExternalForm());

                   // Crear el ImageView con la imagen inicial
                   imageView = new ImageView(frameFrenteIdle); 
        	}
        	if(java == 1) {
                // Cargar imágenes de movimiento
                imagen1 = cargarImagen(getClass().getResource("/java_derecha_moviendose1.png"));
                imagen2 = cargarImagen(getClass().getResource("/java_derecha_moviendose2.png"));
                imagen3 = cargarImagen(getClass().getResource("/java_derecha_moviendose3.png"));
                imagen4 = cargarImagen(getClass().getResource("/java_derecha_moviendose4.png"));
                imagen5 = cargarImagen(getClass().getResource("/java_izquierda_moviendose1.png"));
                imagen6 = cargarImagen(getClass().getResource("/java_izquierda_moviendose2.png"));
                imagen7 = cargarImagen(getClass().getResource("/java_izquierda_moviendose3.png"));
                imagen8 = cargarImagen(getClass().getResource("/java_izquierda_moviendose4.png"));
                imagen9 = cargarImagen(getClass().getResource("/java_de_frente_moviendose1.png"));
                imagen10 = cargarImagen(getClass().getResource("/java_de_frente_moviendose2.png"));
                imagen11 = cargarImagen(getClass().getResource("/java_de_frente_moviendose3.png"));
                imagen12 = cargarImagen(getClass().getResource("/java_de_frente_moviendose4.png"));
                imagen13 = cargarImagen(getClass().getResource("/java_de_espalda_moviendose1.png"));
                imagen14 = cargarImagen(getClass().getResource("/java_de_espalda_moviendose2.png"));
                imagen15 = cargarImagen(getClass().getResource("/java_de_espalda_moviendose3.png"));
                imagen16 = cargarImagen(getClass().getResource("/java_de_espalda_moviendose4.png"));

                // Inicializar imágenes idle
                frameDerechaIdle = new Image(getClass().getResource("/java_derecha(detenido).png").toExternalForm());
                frameIzquierdaIdle = new Image(getClass().getResource("/java_izquierda(detenido).png").toExternalForm());
                frameFrenteIdle = new Image(getClass().getResource("/java_de_frente(detenido).png").toExternalForm());
                frameEspaldaIdle = new Image(getClass().getResource("/java_de_espalda(detenido).png").toExternalForm());

                // Crear el ImageView con la imagen inicial
                imageView = new ImageView(frameFrenteIdle);
            	}
        } catch (IOException e) {
            System.err.println("Error al cargar las imágenes: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private Image cargarImagen(URL resourceUrl) throws IOException {
        if (resourceUrl == null) {
            throw new IOException("No se encontró el archivo de imagen");
        }
        return new Image(resourceUrl.toExternalForm());
    }


    public ImageView getImageView() {
        return imageView;
    }

    public void startMoving(String direction) {
        this.direction = direction;
        moving = true;
    }

    public void stopMoving() {
        moving = false;
    }

    public void update(int akame, int leone, int java) {
        long now = System.currentTimeMillis();
        if (moving && now - lastFrameTime >= 150) { // Cambiar frame cada 150 ms
            currentFrame = (currentFrame + 1) % 4; // 4 frames por dirección
           if(akame == 1) {
            switch (direction) {
                case "derecha" -> imageView.setImage(new Image(getClass().getResource("/akame_derecha_moviendose_" + (currentFrame + 1) + ".png").toExternalForm()));
                case "izquierda" -> imageView.setImage(new Image(getClass().getResource("/akame_izquierda_moviendose_" + (currentFrame + 1) + ".png").toExternalForm()));
                case "frente" -> imageView.setImage(new Image(getClass().getResource("/akame_de_frente_moviendose_" + (currentFrame + 1) + ".png").toExternalForm()));
                case "espalda" -> imageView.setImage(new Image(getClass().getResource("/akame_de_espalda_moviendose_" + (currentFrame + 1) + ".png").toExternalForm()));
            }
            lastFrameTime = now;
           }
           if(leone == 1) {
        	   switch (direction) {
               case "derecha" -> imageView.setImage(new Image(getClass().getResource("/leone_derecha_moviendose" + (currentFrame + 1) + ".png").toExternalForm()));
               case "izquierda" -> imageView.setImage(new Image(getClass().getResource("/leone_izquierda_moviendose" + (currentFrame + 1) + ".png").toExternalForm()));
               case "frente" -> imageView.setImage(new Image(getClass().getResource("/leone_de_frente_moviendose" + (currentFrame + 1) + ".png").toExternalForm()));
               case "espalda" -> imageView.setImage(new Image(getClass().getResource("/leone_de_espalda_moviendose" + (currentFrame + 1) + ".png").toExternalForm()));
           }
           lastFrameTime = now;
           }
           if(java == 1) {
        	   switch (direction) {
               case "derecha" -> imageView.setImage(new Image(getClass().getResource("/java_derecha_moviendose" + (currentFrame + 1) + ".png").toExternalForm()));
               case "izquierda" -> imageView.setImage(new Image(getClass().getResource("/java_izquierda_moviendose" + (currentFrame + 1) + ".png").toExternalForm()));
               case "frente" -> imageView.setImage(new Image(getClass().getResource("/java_de_frente_moviendose" + (currentFrame + 1) + ".png").toExternalForm()));
               case "espalda" -> imageView.setImage(new Image(getClass().getResource("/java_de_espalda_moviendose" + (currentFrame + 1) + ".png").toExternalForm()));
           }
           lastFrameTime = now;
           }
        }  else if (!moving) {
            // Mostrar la imagen idle según la dirección
            switch (direction) {
                case "derecha" -> imageView.setImage(frameDerechaIdle);
                case "izquierda" -> imageView.setImage(frameIzquierdaIdle);
                case "frente" -> imageView.setImage(frameFrenteIdle);
                case "espalda" -> imageView.setImage(frameEspaldaIdle);
            }
        }
    }
   
}

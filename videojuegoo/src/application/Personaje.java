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

public class Personaje {
	private AnchorPane fondo;
	private MenuController menu;
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
    public BufferedImage imagen1, imagen2, imagen3, imagen4, imagen5, imagen6, imagen7, imagen8;
    public BufferedImage imagen9, imagen10, imagen11, imagen12, imagen13, imagen14, imagen15, imagen16;

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

    public Personaje(int akame, int leone) {
        try {
        	if(akame == 1) {
            // Cargar imágenes de movimiento
            imagen1 = cargarImagen(new File("src/Personajes/akame_derecha_moviendose_1.png"));
            imagen2 = cargarImagen(new File("src/Personajes/akame_derecha_moviendose_2.png"));
            imagen3 = cargarImagen(new File("src/Personajes/akame_derecha_moviendose_3.png"));
            imagen4 = cargarImagen(new File("src/Personajes/akame_derecha_moviendose_4.png"));
            imagen5 = cargarImagen(new File("src/Personajes/akame_izquierda_moviendose_1.png"));
            imagen6 = cargarImagen(new File("src/Personajes/akame_izquierda_moviendose_2.png"));
            imagen7 = cargarImagen(new File("src/Personajes/akame_izquierda_moviendose_3.png"));
            imagen8 = cargarImagen(new File("src/Personajes/akame_izquierda_moviendose_4.png"));
            imagen9 = cargarImagen(new File("src/Personajes/akame_de_frente_moviendose_1.png"));
            imagen10 = cargarImagen(new File("src/Personajes/akame_de_frente_moviendose_2.png"));
            imagen11 = cargarImagen(new File("src/Personajes/akame_de_frente_moviendose_3.png"));
            imagen12 = cargarImagen(new File("src/Personajes/akame_de_frente_moviendose_4.png"));
            imagen13 = cargarImagen(new File("src/Personajes/akame_de_espalda_moviendose_1.png"));
            imagen14 = cargarImagen(new File("src/Personajes/akame_de_espalda_moviendose_2.png"));
            imagen15 = cargarImagen(new File("src/Personajes/akame_de_espalda_moviendose_3.png"));
            imagen16 = cargarImagen(new File("src/Personajes/akame_de_espalda_moviendose_4.png"));

            // Inicializar imágenes idle
            frameDerechaIdle = new Image(new File("src/Personajes/akame_derecha_(detenida).png").toURI().toString());
            frameIzquierdaIdle = new Image(new File("src/Personajes/akame_izquierda_(detenida).png").toURI().toString());
            frameFrenteIdle = new Image(new File("src/Personajes/akame_de_frente_(detenida).png").toURI().toString());
            frameEspaldaIdle = new Image(new File("src/Personajes/akame_de_espaldas_(detenida).png").toURI().toString());

            // Crear el ImageView con la imagen inicial
            imageView = new ImageView(frameFrenteIdle);
        	}
        	if (leone == 1){
        		   imagen1 = cargarImagen(new File("src/Personajes2/leone_derecha_moviendose1.png"));
                   imagen2 = cargarImagen(new File("src/Personajes2/leone_derecha_moviendose2.png"));
                   imagen3 = cargarImagen(new File("src/Personajes2/leone_derecha_moviendose3.png"));
                   imagen4 = cargarImagen(new File("src/Personajes2/leone_derecha_moviendose4.png"));
                   imagen5 = cargarImagen(new File("src/Personajes2/leone_izquierda_moviendose1.png"));
                   imagen6 = cargarImagen(new File("src/Personajes2/leone_izquierda_moviendose2.png"));
                   imagen7 = cargarImagen(new File("src/Personajes2/leone_izquierda_moviendose3.png"));
                   imagen8 = cargarImagen(new File("src/Personajes2/leone_izquierda_moviendose4.png"));
                   imagen9 = cargarImagen(new File("src/Personajes2/leone_de_frente_moviendose1.png"));
                   imagen10 = cargarImagen(new File("src/Personajes2/leone_de_frente_moviendose2.png"));
                   imagen11 = cargarImagen(new File("src/Personajes2/leone_de_frente_moviendose3.png"));
                   imagen12 = cargarImagen(new File("src/Personajes2/leone_de_frente_moviendose4.png"));
                   imagen13 = cargarImagen(new File("src/Personajes2/leone_de_espalda_moviendose1.png"));
                   imagen14 = cargarImagen(new File("src/Personajes2/leone_de_espalda_moviendose2.png"));
                   imagen15 = cargarImagen(new File("src/Personajes2/leone_de_espalda_moviendose3.png"));
                   imagen16 = cargarImagen(new File("src/Personajes2/leone_de_espalda_moviendose4.png"));

                   // Inicializar imágenes idle
                   frameDerechaIdle = new Image(new File("src/Personajes2/leone_derecha(detenida).png").toURI().toString());
                   frameIzquierdaIdle = new Image(new File("src/Personajes2/leone_izquierda(detenida).png").toURI().toString());
                   frameFrenteIdle = new Image(new File("src/Personajes2/leone_de_frente(detenida).png").toURI().toString());
                   frameEspaldaIdle = new Image(new File("src/Personajes2/leone_de_espalda(detenida).png").toURI().toString());

                   // Crear el ImageView con la imagen inicial
                   imageView = new ImageView(frameFrenteIdle); 
        	}
        } catch (IOException e) {
            System.err.println("Error al cargar las imágenes: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private BufferedImage cargarImagen(File file) throws IOException {
        if (!file.exists()) {
            throw new IOException("No se encontró el archivo de imagen: " + file.getAbsolutePath());
        }
        return ImageIO.read(file);
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

    public void update(int akame, int leone) {
        long now = System.currentTimeMillis();
        if (moving && now - lastFrameTime >= 150) { // Cambiar frame cada 150 ms
            currentFrame = (currentFrame + 1) % 4; // 4 frames por dirección
           if(akame == 1) {
            switch (direction) {
                case "derecha" -> imageView.setImage(new Image(new File("src/Personajes/akame_derecha_moviendose_" + (currentFrame + 1) + ".png").toURI().toString()));
                case "izquierda" -> imageView.setImage(new Image(new File("src/Personajes/akame_izquierda_moviendose_" + (currentFrame + 1) + ".png").toURI().toString()));
                case "frente" -> imageView.setImage(new Image(new File("src/Personajes/akame_de_frente_moviendose_" + (currentFrame + 1) + ".png").toURI().toString()));
                case "espalda" -> imageView.setImage(new Image(new File("src/Personajes/akame_de_espalda_moviendose_" + (currentFrame + 1) + ".png").toURI().toString()));
            }
            lastFrameTime = now;
           }
           if(leone == 1) {
        	   switch (direction) {
               case "derecha" -> imageView.setImage(new Image(new File("src/Personajes2/leone_derecha_moviendose" + (currentFrame + 1) + ".png").toURI().toString()));
               case "izquierda" -> imageView.setImage(new Image(new File("src/Personajes2/leone_izquierda_moviendose" + (currentFrame + 1) + ".png").toURI().toString()));
               case "frente" -> imageView.setImage(new Image(new File("src/Personajes2/leone_de_frente_moviendose" + (currentFrame + 1) + ".png").toURI().toString()));
               case "espalda" -> imageView.setImage(new Image(new File("src/Personajes2/leone_de_espalda_moviendose" + (currentFrame + 1) + ".png").toURI().toString()));
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

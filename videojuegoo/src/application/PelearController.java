package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.io.IOException;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class PelearController {

    @FXML
    private ProgressBar vidaProta, vidaEnemigo, Habilidad;
    @FXML
    private Label nivelAtaque, VidaCantidad, AtaqueEnemigo, VidaEnemigo;
    @FXML
    private Button atacar, vida, especial, personajes, Revivir;
    @FXML
    public ImageView personaje, enemigo;
    @FXML
    private Line efecto;
    @FXML
    private static MediaPlayer mediaPlayer;
    private Stage stage;
    private boolean playerTurn = true;
    private Personaje prota;
    private Personaje prota2;
	private Personaje prota3;
    private Personaje Enemigo;
    public int personajesimg = 1;
    public int akame = 1;
    public int leone = 1;
    public int java = 1;
    private Scene scene;
    private int protaVida;
    private int protavidamax;
    private int enemigoVida;
    private int enemigoVidamax;
    private int protaAtaque;
    private int protanivel;
    private int enemigoAtaque;
    private int enemyattack;
    private int protaExperiencia;
	private int protaExpLimite;
	private int curaciones;
	private int totems;
    private double posX;
    private double posY;
    private int[][] layout;
    private int enemigoRow;
    private int enemigoCol;
    public int enemy;
    public int habilidad =0;
    public int habilidad1 = 0;
    public int habilidad2 = 0;
    // Método para recibir los personajes y configurar los datos iniciales
    public void setPersonajes(Personaje prota, Personaje enemigo, int[][] layout, int enemigoRow, int enemigoCol, int enemy, int curaciones, Personaje prota2, Personaje prota3, int totems) {
        this.prota = prota;
        this.Enemigo = enemigo;
        // Configura las barras de progreso de vida usando la salud actual y la vida máxima del personaje
        this.protaVida = prota.salud;
        this.protaAtaque = prota.daño;
        this.enemigoVida = enemigo.salud;
        this.enemigoAtaque = enemigo.daño;
        this.enemigoVidamax = enemigo.vidaMaxima;
        this.layout = layout;
        this.enemigoRow = enemigoRow;
        this.enemigoCol = enemigoCol;
        this.posX = prota.posX;
        this.posY = prota.posY;
        this.curaciones = curaciones;
        this.totems = totems;
        this.protanivel = prota.nivel;
        this.protavidamax = prota.vidaMaxima;
        this.protaExperiencia = prota.experienciaActual;
        this.protaExpLimite = prota.experienciaLimite;
        this.prota2 = prota2;
        this.prota3 = prota3;
        vidaProta.setProgress((double) protaVida / prota.vidaMaxima);
        vidaEnemigo.setProgress((double) enemigoVida / enemigo.vidaMaxima);
        Habilidad.setProgress((double) habilidad / 2);
        actualizarLabels();
        enemyattack = enemigoAtaque;
    }
    @FXML
    public void initialize() {
          // Configurar el audio de fondo
        if (mediaPlayer == null) {
            
            String rutaNivel1Audio = getClass().getResource("/Resources/pelea.mp3").toExternalForm();
            Media nivel1Media = new Media(rutaNivel1Audio);
            mediaPlayer = new MediaPlayer(nivel1Media);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.play();
        }
        else if (mediaPlayer != null) {
            
            String rutaNivel1Audio = getClass().getResource("/Resources/pelea.mp3").toExternalForm();
            Media nivel1Media = new Media(rutaNivel1Audio);
            mediaPlayer = new MediaPlayer(nivel1Media);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.play();
        }

        // Configurar los eventos de los botones
        atacar.setOnAction(e -> {
			try {
				realizarAtaque();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
        vida.setOnAction(e -> recuperarVida());
        Revivir.setOnAction(e -> RevivirPersonaje());
        personajes.setOnAction(e -> CambiarPersonaje());
        especial.setOnAction(e -> {
			try {
				usarHabilidadEspecial();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
    }
    private void mostrarEfecto(boolean esJugador) {
        double startX;
        double startY;
        double endX;
        double endY;

        // Ajustar las posiciones según quién ataca
        if (esJugador) {
            // Coordenadas del jugador atacando al enemigo
            startX = personaje.getLayoutX() - 80; // Centro del personaje
            startY = personaje.getLayoutY() - 320; // Parte superior del personaje
            endX = enemigo.getLayoutX() - 320; // Centro del enemigo
            endY = enemigo.getLayoutY() + 30; // Centro del enemigo
        } else {
            // Coordenadas del enemigo atacando al jugador
            startX = enemigo.getLayoutX() - 680; // Centro del enemigo
            startY = enemigo.getLayoutY() + 200; // Parte inferior del enemigo
            endX = personaje.getLayoutX() - 480;
            endY = personaje.getLayoutY() +30;
        }

        // Configurar la línea
        efecto.setStartX(startX);
        efecto.setStartY(startY);
        efecto.setEndX(endX);
        efecto.setEndY(endY);
        efecto.setVisible(true);

        // Usar un Timeline para ocultar la línea después de un breve tiempo
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.seconds(0.5), e -> efecto.setVisible(false))
        );
        timeline.play();
    }


    // Acción de ataque del jugador
    private void realizarAtaque() throws IOException {
        if (playerTurn) {
            // Animar el efecto desde el jugador hacia el enemigo
           
            if(personajesimg == 1 && akame == 1) {
            	 mostrarEfecto(true);
            	 habilidad++;
            	   Habilidad.setProgress((double) habilidad / 2);
            enemigoVida -= protaAtaque;
            vidaEnemigo.setProgress((double) enemigoVida / Enemigo.vidaMaxima);
            actualizarLabels();
            checkEnemyLife();
            switchTurn();
            }
            if(personajesimg == 2 && leone == 1) {
            	 mostrarEfecto(true);
            	habilidad1++;
            	   Habilidad.setProgress((double) habilidad1 / 2);
                enemigoVida -= prota2.daño;
                vidaEnemigo.setProgress((double) enemigoVida / Enemigo.vidaMaxima);
                actualizarLabels();
                checkEnemyLife();
                switchTurn();
                }
            if(personajesimg == 3 && java == 1) {
            	 mostrarEfecto(true);
            	habilidad2++;
            	   Habilidad.setProgress((double) habilidad2 / 2);
                enemigoVida -= prota3.daño;
                vidaEnemigo.setProgress((double) enemigoVida / Enemigo.vidaMaxima);
                actualizarLabels();
                checkEnemyLife();
                switchTurn();
                }
        }
    }

    // Acción de recuperación de vida del jugador
    private void recuperarVida() {
        if (playerTurn) {
        	 if (curaciones > 0) {
        		 if(personajesimg == 1 && protaVida < protavidamax && akame == 1) {
            prota.curarse(30);  // Curar al personaje
            protaVida = prota.salud;  // Actualizar la salud actual del jugador
            vidaProta.setProgress((double) protaVida / protavidamax);
            curaciones--;
            actualizarLabels();
        }
        		 else if(personajesimg == 2 && prota2.salud < prota2.vidaMaxima && leone == 1) {
        	            prota2.curarse(30);  // Actualizar la salud actual del jugador
        	            vidaProta.setProgress((double) prota2.salud / prota2.vidaMaxima);
        	            curaciones--;
        	            actualizarLabels();
        	        }
        		 else if(personajesimg == 3 && prota3.salud < prota3.vidaMaxima && java == 1) {
        	            prota3.curarse(30); // Actualizar la salud actual del jugador
        	            vidaProta.setProgress((double) prota3.salud / prota3.vidaMaxima);
        	            curaciones--;
        	            actualizarLabels();
        	        }
    }
        	 else if(curaciones <= 0) {
       		  vida.setDisable(true); // Deshabilitar el botón
       	        vida.setStyle("-fx-background-color: #ff0000;"); 
       	 }
    }
    }
    // Acción de habilidad especial del jugador
    private void usarHabilidadEspecial() throws IOException {
        if (playerTurn) {
        	if(habilidad >= 2 && akame == 1){ // Usar ataque especial
        	 mostrarEfecto(true);
            enemigoVida -= protaAtaque * 2; // Actualizar la salud actual del enemigo
            vidaEnemigo.setProgress((double) enemigoVida / Enemigo.vidaMaxima);
            actualizarLabels();
            checkEnemyLife();
            switchTurn();
            habilidad = 0;
            Habilidad.setProgress((double) habilidad / 2);
            
        }
        	if(habilidad1 >= 2 && leone == 1){ 
               prota2.salud = prota2.vidaMaxima;
               vidaProta.setProgress((double) prota2.salud / prota2.vidaMaxima);
               actualizarLabels();
               switchTurn();
               habilidad1 = 0;
               Habilidad.setProgress((double) habilidad1 / 2);
               
    }
        	if(habilidad2 >= 2 && java == 1){ // Usar ataque especial
           	 mostrarEfecto(true);
           	 enemyattack = enemigoAtaque - 10;
           	 enemigoAtaque = enemyattack;
               actualizarLabels();
               checkEnemyLife();
               switchTurn();
               habilidad2 = 0;
               Habilidad.setProgress((double) habilidad2 / 2);
    }
        }
    }
    private void CambiarPersonaje() {
        if (playerTurn) {
        personajesimg++;
        if (personajesimg > 3) {
            personajesimg = 1; // Reiniciar a la imagen inicial
        }

        // Cambiar la imagen según el valor de `personajesimg`
        String rutaAbsoluta = null;

        switch (personajesimg) {
            case 1:
                rutaAbsoluta = "file:///C:/Users/Acer/Desktop/Edu/LATZINA/akame_derecha_(detenida).png";
                actualizarLabels();
                vidaProta.setProgress((double) protaVida / protavidamax);
                Habilidad.setStyle("-fx-accent: red;"); 
                Habilidad.setProgress((double) habilidad / 2);
                break;
            case 2:
                rutaAbsoluta = "file:///C:/Users/Acer/Desktop/Edu/LATZINA/leone_derecha(detenida).png";
                actualizarLabels();
                vidaProta.setProgress((double) prota2.salud / prota2.vidaMaxima);
                Habilidad.setStyle("-fx-accent: yellow;"); 
                Habilidad.setProgress((double) habilidad1 / 2);
                break;
            case 3:
                rutaAbsoluta = "file:///C:/Users/Acer/Desktop/Edu/LATZINA/java_derecha(detenido).png";
                actualizarLabels();
                vidaProta.setProgress((double) prota3.salud / prota3.vidaMaxima);
                Habilidad.setStyle("-fx-accent: blue;"); 
                Habilidad.setProgress((double) habilidad2 / 2);
                break;
        }

        // Asegurarse de que la ruta no sea nula antes de cambiar la imagen
        if (rutaAbsoluta != null) {
            Image nuevaImagen = new Image(rutaAbsoluta);
            personaje.setImage(nuevaImagen);
        }
    }
    }
    
    private void RevivirPersonaje() {
    	if(totems > 0) {
    	if(personajesimg == 1 && akame == 0) {
    		prota.salud = prota.vidaMaxima;
    		 actualizarLabels();
             vidaProta.setProgress((double) protaVida / protavidamax);
    		akame = 1;
    		totems--;
    	}
    	if(personajesimg == 2 && leone == 0) {
    		leone = 1;
    		  actualizarLabels();
              vidaProta.setProgress((double) prota2.salud / prota2.vidaMaxima);
    		prota2.salud = prota2.vidaMaxima;
    		totems--;
    	}
    	if(personajesimg == 3 && java == 0) {
    		prota3.salud = prota3.vidaMaxima;
    		  actualizarLabels();
              vidaProta.setProgress((double) prota3.salud / prota3.vidaMaxima);
    		java = 1;
    		totems--;
    	}
    	}
    	else if (totems <= 0){
    		 Revivir.setDisable(true); // Deshabilitar el botón
    	        Revivir.setStyle("-fx-background-color: #ff0000;"); 
    	
    }
    }
    // Cambiar de turno y realizar la acción del enemigo
    private void switchTurn() {
        playerTurn = false;
        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.setOnFinished(event -> {
            realizarAccionEnemigo();
            playerTurn = true;
        });
        pause.play();
    }

    // Acción del enemigo en su turno
    public void realizarAccionEnemigo() {
        if (enemigoVida > 0) {
            // Animar el efecto desde el enemigo hacia el jugador
            mostrarEfecto(false);

            int action = (int) (Math.random() * 3);
            int damage = switch (action) {
                case 0 -> enemigoAtaque;
                case 1 -> enemigoAtaque + 5;
                case 2 -> enemigoAtaque - 5;
                default -> enemigoAtaque;
            };
            if(personajesimg == 1) {
            prota.recibirdaño(damage);
            protaVida = prota.salud;
            vidaProta.setProgress((double) protaVida / prota.vidaMaxima);
            actualizarLabels();
            checkPlayerLife();
            }
            if(personajesimg == 2) {
                prota2.recibirdaño(damage);
                vidaProta.setProgress((double) prota2.salud / prota2.vidaMaxima);
                actualizarLabels();
                checkPlayerLife();
                }
            if(personajesimg == 3) {
                prota3.recibirdaño(damage);
                vidaProta.setProgress((double) prota3.salud / prota3.vidaMaxima);
                actualizarLabels();
                checkPlayerLife();
                }
        }
    }

    // Verificar si la vida del enemigo llega a 0 o menos
    private void checkEnemyLife() throws IOException {
        if (enemigoVida <= 0) {
            enemigoVida = 0;
            if(enemigoVidamax == 500) {
            	  if (mediaPlayer != null) {
                      mediaPlayer.stop();  // Detener la música de intro
                      mediaPlayer.dispose();  // Liberar los recursos
                  }
        		findeljuego();
        		
        	}
            else {
            	if(personajesimg == 1) {
            		if(enemigoVidamax == 180) {
            			prota.experienciaActual += 10;
            		}
            		else {
            prota.experienciaActual += 5;
            		}
            if(prota.experienciaActual >= prota.experienciaLimite) {
            	prota.nivel++;
            	prota.daño += 10;
            	prota.salud = prota.vidaMaxima + 20;
            	prota.vidaMaxima = prota.salud;
            	prota.experienciaActual = 0;
            	prota.experienciaLimite += 10;
            	
            
            	}
            	}
            	
            	if(personajesimg == 2) {
            		if(enemigoVidamax == 180) {
            			prota2.experienciaActual += 10;
            		}
            		else {
                    prota2.experienciaActual += 5;
            		}
                    if(prota2.experienciaActual >= prota2.experienciaLimite) {
                    	prota2.nivel++;
                    	prota2.daño += 10;
                    	prota2.salud = prota2.vidaMaxima + 20;
                    	prota2.vidaMaxima = prota2.salud;
                    	prota2.experienciaActual = 0;
                    	prota2.experienciaLimite += 10;
                    	
                    }
            		
                    	}
            	if(personajesimg == 3) {
            		if(enemigoVidamax == 180) {
            			prota3.experienciaActual += 10;
            		}
            		else {
                    prota3.experienciaActual += 5;
            		}
                    if(prota3.experienciaActual >= prota3.experienciaLimite) {
                    	prota3.nivel++;
                    	prota3.daño += 10;
                    	prota3.salud = prota3.vidaMaxima + 20;
                    	prota3.vidaMaxima = prota3.salud;
                    	prota3.experienciaActual = 0;
                    	prota3.experienciaLimite += 10;
                    	
                    }
                    	
            	}
            vidaEnemigo.setProgress(0);
            actualizarLabels();
            finDeLaPelea();
        }
        }
    }

    // Verificar si la vida del jugador llega a 0 o menos
    private void checkPlayerLife() {
    	try {
        if (protaVida <= 0) {
        	akame = 0;
            protaVida = 0;
            actualizarLabels();
        }
        if (prota2.salud <= 0) {
        	leone = 0;
            prota2.salud = 0;
            actualizarLabels();
        }
        if (prota3.salud <= 0) {
        	java = 0;
            prota3.salud = 0;
            actualizarLabels();
        }
            if(akame == 0 && leone == 0 && java == 0) {
            if (mediaPlayer != null) {
                mediaPlayer.stop();  // Detener la música de intro
                mediaPlayer.dispose();  // Liberar los recursos
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Derrota.fxml"));
            Parent root = loader.load();
            stage = (Stage) personaje.getScene().getWindow();
            scene = new Scene(root);
            root.requestFocus();
            stage.setScene(scene);
            stage.show();
        }
        
    	}catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Actualizar los valores de los labels con la información actual
    private void actualizarLabels() {
    	if(personajesimg == 1) {
        VidaCantidad.setText("Vida: " + protaVida);
        nivelAtaque.setText("Ataque: " + protaAtaque);
        VidaEnemigo.setText("Vida: " + enemigoVida);
        AtaqueEnemigo.setText("Ataque: " + enemigoAtaque);
    	}
    	if(personajesimg == 2) {
            VidaCantidad.setText("Vida: " + prota2.salud);
            nivelAtaque.setText("Ataque: " + prota2.daño);
            VidaEnemigo.setText("Vida: " + enemigoVida);
            AtaqueEnemigo.setText("Ataque: " + enemigoAtaque);
        	}
    	if(personajesimg == 3) {
            VidaCantidad.setText("Vida: " + prota3.salud);
            nivelAtaque.setText("Ataque: " + prota3.daño);
            VidaEnemigo.setText("Vida: " + enemigoVida);
            AtaqueEnemigo.setText("Ataque: " + enemigoAtaque);
        	}
    }
    
    public void findeljuego() throws IOException {
    	 FXMLLoader loader = new FXMLLoader(getClass().getResource("FinalJuego.fxml"));
         Parent root = loader.load();
         stage = (Stage) personaje.getScene().getWindow();
         scene = new Scene(root);
         stage.setScene(scene);
         stage.show();
    
    }
    // Método que se ejecuta al finalizar la pelea
    public void finDeLaPelea() {
        try {
            mediaPlayer.stop();
            layout[enemigoRow][enemigoCol] = 1;
            if(enemigoVidamax == 180) {
           	 layout[enemigoRow][enemigoCol] = 2;
           }
            FXMLLoader loader = new FXMLLoader(getClass().getResource("primeraisla.fxml"));
            Parent root = loader.load();
            MovimientoController movimientoController = loader.getController();
            movimientoController.layout = this.layout;
            movimientoController.Akame.salud = prota.salud;
            movimientoController.Akame.vidaMaxima = prota.vidaMaxima;
            movimientoController.Akame.posX = prota.posX;
            movimientoController.Akame.posY = prota.posY;
            movimientoController.Akame.experienciaActual = prota.experienciaActual;
            movimientoController.Akame.daño = prota.daño;
            movimientoController.Akame.nivel = prota.nivel;
            movimientoController.Akame.experienciaLimite = prota.experienciaLimite;
            movimientoController.Leone.salud = prota2.salud;
            movimientoController.Leone.vidaMaxima = prota2.vidaMaxima;
            movimientoController.Leone.experienciaActual = prota2.experienciaActual;
            movimientoController.Leone.daño = prota2.daño;
            movimientoController.Leone.experienciaLimite = prota2.experienciaLimite;
            movimientoController.Leone.nivel = prota2.nivel;
            movimientoController.Java.salud = prota3.salud;
            movimientoController.Java.vidaMaxima = prota3.vidaMaxima;
            movimientoController.Java.experienciaActual = prota3.experienciaActual;
            movimientoController.Java.daño = prota3.daño;
            movimientoController.Java.experienciaLimite = prota3.experienciaLimite;
            movimientoController.Java.nivel = prota3.nivel;
            movimientoController.curaciones = curaciones;
            movimientoController.totems = totems;
            movimientoController.initialize();
            stage = (Stage) personaje.getScene().getWindow();
            scene = new Scene(root);
            root.requestFocus();
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

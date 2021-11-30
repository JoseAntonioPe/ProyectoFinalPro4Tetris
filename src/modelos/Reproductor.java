package modelos;

import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class Reproductor {

    public static String FONDO_INTRO = new File("sonido_tetris.mp3").toURI().toString();
    public static String FONDO_SINGLE_PLAYER = new File("audio_singleplayer.mp3").toURI().toString();
    public static String FONDO_MULTIPLE_PLAYER = new File("audio_multiplayer.mp3").toURI().toString();
    public static String SONIDO_BOTON_CLICK = new File("boton_singleplayer.mp3").toURI().toString();
    public static String SONIDO_BOTON_CLICK2 = new File("boton_multiplayer.mp3").toURI().toString();
    public static String SONIDO_BOTON_HOVER = new File("boton_puntero.wav").toURI().toString();

    private MediaPlayer reproductorFondos;
    private MediaPlayer reproductorSonidos;
    private Media media;
    private static Reproductor reproductor;

    private Reproductor() {
        media = new Media(FONDO_INTRO);
        reproductorSonidos = new MediaPlayer(media);
    }

    public static Reproductor obtenerReproductor() {
        if (reproductor == null) {
            reproductor = new Reproductor();
            return reproductor;
        } else {
            return reproductor;
        }

    }

    public void reproducirFondo(String url, boolean autoplay) {
        media = new Media(url);
        reproductorFondos = new MediaPlayer(media);
        reproductorFondos.setAutoPlay(autoplay);

        reproductorFondos.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                reproductorFondos.seek(Duration.ZERO);
            }
        });

    }

    public void reproducirSonido(String url) {
        media = new Media(url);
        reproductorSonidos = new MediaPlayer(media);
        reproductorSonidos.play();
    }

    public void detenerFondo() {
        reproductorFondos.stop();
    }

}

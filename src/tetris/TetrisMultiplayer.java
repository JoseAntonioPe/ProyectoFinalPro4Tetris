package tetris;

import conexion.Cliente;
import conexion.Jugador;
import modelos.Reproductor;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import visualizacion.VentanaPrincipal;

public class TetrisMultiplayer extends JFrame {

    JLabel barra_de_estado;
    JFrame superVentana;
    JMenuBar menuBar;
    JMenu opciones;
    JMenuItem abrir;
    TableroMultiplayer tablero_juego;
    JPanel panel;

    Jugador jugador;

    public TetrisMultiplayer() {
        Reproductor.obtenerReproductor().detenerFondo();
        Reproductor.obtenerReproductor().reproducirFondo(Reproductor.FONDO_MULTIPLE_PLAYER, true);
        panel = new JPanel();

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                Reproductor.obtenerReproductor().detenerFondo();
                Reproductor.obtenerReproductor().reproducirFondo(Reproductor.FONDO_INTRO, true);
            }
        });
        panel.setPreferredSize(new Dimension(400, 500));
        panel.setBackground(Color.WHITE);
        menuBar = new JMenuBar();
        opciones = new JMenu("Opciones");
        abrir = new JMenuItem("Abrir");
        menuBar.add(opciones);
        opciones.add(abrir);
        setJMenuBar(menuBar);
        barra_de_estado = new JLabel("PUNTAJE:  0");
        add(barra_de_estado, BorderLayout.SOUTH);
        barra_de_estado.setFont(new Font("Serif", Font.BOLD, 14));
        tablero_juego = new TableroMultiplayer(this);
        add(tablero_juego, BorderLayout.CENTER);
        tablero_juego.inicio_juego();
        setTitle("TETRIS 99");
        setResizable(true);

        add(panel, BorderLayout.EAST);
        pack();

    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
        jugador.setTablero(tablero_juego.getTablero());
        tablero_juego.setJugador(jugador);
    }

    public void obtenerTableros() {
        try {
            Cliente cliente = new Cliente();

            for (Jugador item : cliente.obtenerData()) {
                System.out.println("jugadores: " + item.obtenerNombre());
                if (!item.getIp().equals(jugador.getIp())) {
                    TableroMultiplayer tablero = new TableroMultiplayer(this);
                    tablero.setJugador(item);
                    tablero.obtenerMatriz();
                    panel.add(tablero);
                }
            }

        } catch (Exception e) {
        }
        this.update(getGraphics());
    }

    //icono del juego
    @Override
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().
                getImage(ClassLoader.getSystemResource("imagenes/icono.jpeg"));

        return retValue;
    }

    public JLabel getBarra_estado() {
        return barra_de_estado;
    }

    public static void main(String[] args) {
        VentanaPrincipal principal = new VentanaPrincipal();
        //sonido del tetris
        TetrisMultiplayer juego = new TetrisMultiplayer();
        juego.setLocationRelativeTo(null);
        juego.setVisible(true);
    }

    public void abrirVentana(JFrame ventana) {
        superVentana = ventana;
    }

    public TableroMultiplayer getTablero_juego() {
        return tablero_juego;
    }

}

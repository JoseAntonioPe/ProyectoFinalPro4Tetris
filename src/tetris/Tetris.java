package tetris;

import modelos.Reproductor;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import visualizacion.VentanaPrincipal;

public class Tetris extends JFrame {
    
    JLabel barra_de_estado;
    JFrame superVentana;
    JMenuBar menuBar;
    JMenu opciones;
    JMenuItem abrir;
    Tablero tablero_juego;
    
    public Tetris() {
        Reproductor.obtenerReproductor().detenerFondo();
        Reproductor.obtenerReproductor().reproducirFondo(Reproductor.FONDO_SINGLE_PLAYER, true);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                Reproductor.obtenerReproductor().detenerFondo();
                Reproductor.obtenerReproductor().reproducirFondo(Reproductor.FONDO_INTRO, true);
            }
        });
        menuBar = new JMenuBar();
        opciones = new JMenu("Opciones");
        abrir = new JMenuItem("Abrir");
        abrir.addActionListener((ActionEvent e) -> {
           Tetris nuevaVentana = new Tetris();
           nuevaVentana.getTablero_juego().obtenerMatriz(this);
           nuevaVentana.setVisible(true);
        });
        menuBar.add(opciones);
        opciones.add(abrir);
        setJMenuBar(menuBar);
        barra_de_estado = new JLabel("PUNTAJE:  0");
        add(barra_de_estado, BorderLayout.SOUTH);
        barra_de_estado.setFont(new Font("Serif", Font.BOLD, 14));
        Tablero tablero_juego = new Tablero(this);
        add(tablero_juego);
        tablero_juego.inicio_juego();
        setSize(530, 740);
        setTitle("TETRIS 99");
        setResizable(false);
        
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
        Tetris juego = new Tetris();
        juego.setLocationRelativeTo(null);
        juego.setVisible(true);
    }
    
    public void abrirVentana(JFrame ventana) {
        superVentana = ventana;
    }

    public Tablero getTablero_juego() {
        return tablero_juego;
    }

    
}

package tetris;

import conexion.Cliente;
import conexion.Jugador;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import conexion.Forma.Tetrominoes;
import conexion.Forma;
import java.awt.Image;
import javax.swing.ImageIcon;
import modelos.Reproductor;
import visualizacion.Perdiste;

public class TableroMultiplayer extends JPanel implements ActionListener {

    final int ancho_tablero = 10;
    final int alto_tablero = 22;
    Timer tiempo;
    boolean finalizar = false;
    boolean iniciar = false;
    boolean pausar = false;
    int num_lineas_eliminadas = 0;
    int cursor_X = 0;
    int cursor_Y = 0;
    JLabel barra_de_estado;
    Forma pieza;
    Tetrominoes[] tablero;
    Image uno = new ImageIcon(this.getClass().getResource("/imagenes/neon.jpg")).getImage();

    Jugador jugador;

    public TableroMultiplayer(TetrisMultiplayer parent) {

        setFocusable(true);
        setPreferredSize(new Dimension(400, 500));
        pieza = new Forma();
        tiempo = new Timer(400, this);
        tiempo.start();

        barra_de_estado = parent.getBarra_estado();
        tablero = new Tetrominoes[ancho_tablero * alto_tablero];
        addKeyListener(new TAdapter());
        clearBoard();
    }

    public void figuraAgregada() {
        try {
            Cliente cliente = new Cliente();
            cliente.obtenerData();
            cliente.figuraAgregada(jugador);
        } catch (Exception e) {
        }
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
        tablero = jugador.getTablero();

    }

    public void actionPerformed(ActionEvent e) {
        if (finalizar) {
            finalizar = false;
            nueva_pieza();
        } else {
            una_linea_debajo();
        }
    }

    int ancho_cuadrado() {
        return (int) getSize().getWidth() / ancho_tablero;
    }

    int alto_cuadrado() {
        return (int) getSize().getHeight() / alto_tablero;
    }

    Tetrominoes forma(int x, int y) {
        return tablero[(y * ancho_tablero) + x];
    }

    public void inicio_juego() {
        if (pausar) {
            return;
        }

        iniciar = true;
        finalizar = false;
        num_lineas_eliminadas = 0;
        clearBoard();

//        nueva_pieza();
        tiempo.start();
    }

    private void pause() {
        if (!iniciar) {
            return;
        }

        pausar = !pausar;
        if (pausar) {
            Reproductor.obtenerReproductor().detenerFondo();
            tiempo.stop();
            barra_de_estado.setText("PAUSA");
        } else {
            Reproductor.obtenerReproductor().reproducirFondo(Reproductor.FONDO_MULTIPLE_PLAYER, true);
            tiempo.start();
            barra_de_estado.setText(String.valueOf("PUNTAJE:  " + num_lineas_eliminadas));
        }
        repaint();
    }

    public void paint(Graphics g) {
        super.paint(g);
        g.drawImage(uno, 0, 0, this);
        Dimension size = getSize();
        int tablero_superior = (int) size.getHeight() - alto_tablero * alto_cuadrado();

        for (int i = 0; i < alto_tablero; ++i) {
            for (int j = 0; j < ancho_tablero; ++j) {
                Tetrominoes formacion = forma(j, alto_tablero - i - 1);
                if (formacion != Tetrominoes.sin_forma) {
                    dibujar_cuadrado(g, 0 + j * ancho_cuadrado(),
                            tablero_superior + i * alto_cuadrado(), formacion);
                }
            }
        }

        if (pieza.getForma() != Tetrominoes.sin_forma) {
            for (int i = 0; i < 4; ++i) {
                int x = cursor_X + pieza.x(i);
                int y = cursor_Y - pieza.y(i);
                dibujar_cuadrado(g, 0 + x * ancho_cuadrado(),
                        tablero_superior + (alto_tablero - y - 1) * alto_cuadrado(),
                        pieza.getForma());
            }
        }
    }

    private void despegable() {
        int nuevo_Y = cursor_Y;
        while (nuevo_Y > 0) {
            if (!prueba_movimiento(pieza, cursor_X, nuevo_Y - 1)) {
                break;
            }
            --nuevo_Y;
        }
        pieza_caida();
    }

    private void una_linea_debajo() {
        if (!prueba_movimiento(pieza, cursor_X, cursor_Y - 1)) {
            pieza_caida();
        }
    }

    private void clearBoard() {
        for (int i = 0; i < alto_tablero * ancho_tablero; ++i) {
            tablero[i] = Tetrominoes.sin_forma;
        }
    }

    private void pieza_caida() {
        for (int i = 0; i < 4; ++i) {
            int x = cursor_X + pieza.x(i);
            int y = cursor_Y - pieza.y(i);
            tablero[(y * ancho_tablero) + x] = pieza.getForma();
        }
        
        eliminar_lineas_completas();

        if (!finalizar) {
            figuraAgregada();
            nueva_pieza();
        }
    }

    private void nueva_pieza() {
        pieza.setFormaAleatoria();
        cursor_X = ancho_tablero / 2 + 1;
        cursor_Y = alto_tablero - 1 + pieza.minY();

        if (!prueba_movimiento(pieza, cursor_X, cursor_Y)) {
            pieza.setForma(Tetrominoes.sin_forma);
            tiempo.stop();
            iniciar = false;
            
            Reproductor.obtenerReproductor().detenerFondo();
            Perdiste ventana = new Perdiste(null, true);
            ventana.setVisible(true);
            
            barra_de_estado.setText("JUEGO TERMINADO,       [ENTER] NUEVO JUEGO       -     [ESCAPE] SALIR");
        }
    }

    private boolean prueba_movimiento(Forma nueva_pieza, int nuevo_X, int nuevo_Y) {
        for (int i = 0; i < 4; ++i) {
            int x = nuevo_X + nueva_pieza.x(i);
            int y = nuevo_Y - nueva_pieza.y(i);
            if (x < 0 || x >= ancho_tablero || y < 0 || y >= alto_tablero) {
                return false;
            }
            if (forma(x, y) != Tetrominoes.sin_forma) {
                return false;
            }
        }

        pieza = nueva_pieza;
        cursor_X = nuevo_X;
        cursor_Y = nuevo_Y;
        repaint();
        return true;
    }

    private void eliminar_lineas_completas() {
        int num_lineas_completas = 0;

        for (int i = alto_tablero - 1; i >= 0; --i) {
            boolean lineas_completas = true;

            for (int j = 0; j < ancho_tablero; ++j) {
                if (forma(j, i) == Tetrominoes.sin_forma) {
                    lineas_completas = false;
                    break;
                }
            }

            if (lineas_completas) {
                ++num_lineas_completas;
                for (int k = i; k < alto_tablero - 1; ++k) {
                    for (int j = 0; j < ancho_tablero; ++j) {
                        tablero[(k * ancho_tablero) + j] = forma(j, k + 1);
                    }
                }
            }
        }

        if (num_lineas_completas > 0) {
            num_lineas_eliminadas += num_lineas_completas;
            barra_de_estado.setText(String.valueOf("PUNTAJE:  " + num_lineas_eliminadas));
            finalizar = true;
            pieza.setForma(Tetrominoes.sin_forma);
            repaint();
        }
    }

    private void dibujar_cuadrado(Graphics g, int x, int y, Tetrominoes forma) {
        Color colores[] = {new Color(0, 0, 0), new Color(204, 102, 102),
            new Color(102, 204, 102), new Color(102, 102, 204),
            new Color(204, 204, 102), new Color(204, 102, 204),
            new Color(102, 204, 204), new Color(218, 170, 0)
        };

        Color color = colores[forma.ordinal()];

        g.setColor(color);
        g.fillRect(x + 1, y + 1, ancho_cuadrado() - 2, alto_cuadrado() - 2);

        g.setColor(color.brighter());
        g.drawLine(x, y + alto_cuadrado() - 1, x, y);
        g.drawLine(x, y, x + ancho_cuadrado() - 1, y);

        g.setColor(color.darker());
        g.drawLine(x + 1, y + alto_cuadrado() - 1, x + ancho_cuadrado() - 1, y + alto_cuadrado() - 1);
        g.drawLine(x + ancho_cuadrado() - 1, y + alto_cuadrado() - 1, x + ancho_cuadrado() - 1, y + 1);
    }

    class TAdapter extends KeyAdapter {

        public void keyPressed(KeyEvent e) {
            int keycode = e.getKeyCode();
            if (keycode == KeyEvent.VK_ENTER) {
                Reproductor.obtenerReproductor().detenerFondo();
                String[] args = {"hi"};
                Tetris.main(args);
            }
            if (keycode == KeyEvent.VK_ESCAPE) {
                System.exit(0);
            }
            if (!iniciar || pieza.getForma() == Tetrominoes.sin_forma) {
                return;
            }

            if (keycode == 'p' || keycode == 'P') {
                pause();
                return;
            }

            if (pausar) {
                return;
            }

            switch (keycode) {
                case KeyEvent.VK_LEFT:
                    prueba_movimiento(pieza, cursor_X - 1, cursor_Y);
                    break;
                case KeyEvent.VK_RIGHT:
                    prueba_movimiento(pieza, cursor_X + 1, cursor_Y);
                    break;
                case KeyEvent.VK_DOWN:
                    prueba_movimiento(pieza.giroDerecha(), cursor_X, cursor_Y);

                    break;
                case KeyEvent.VK_UP:
                    prueba_movimiento(pieza.giroIzquierda(), cursor_X, cursor_Y);

                    break;
                case KeyEvent.VK_SPACE:
                    despegable();
                    break;
                case 'd':
                    una_linea_debajo();
                    break;
                case 'D':
                    una_linea_debajo();
                    break;

            }

        }
    }

    public Tetrominoes[] getTablero() {
        return tablero;
    }
//Este vamos a usar para traer la matriz de los demas

    public void obtenerMatriz() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    tiempo.stop();
                    while (true) {
                        Thread.sleep(500);
                        //tablero = tetris.getTablero_juego().getTablero();
                        //Cliente cliente = new Cliente();
                        for (Jugador item : new Cliente().obtenerData()) {
                            if (item.getIp().equals(jugador.getIp())) {
                                tablero = item.getTablero();
                                break;
                            }
                        }

                        updateUI();
                    }
                } catch (Exception e) {
                }

            }
        };
        Thread hilo = new Thread(runnable);
        hilo.start();
    }
}

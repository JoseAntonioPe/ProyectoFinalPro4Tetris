package tetris;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JLabel;
import javax.swing.Timer;
import modelos.Reproductor;
import tetris.Forma.Tetrominoes;
import visualizacion.Perdiste;

public class Panel extends javax.swing.JPanel implements ActionListener {

    final int ancho_tablero = 10;
    final int alto_tablero = 22;
    Timer tiempo;
    boolean fin = false;
    boolean inicio = false;
    boolean pauso = false;
    int num_linas_removidas = 0;
    int cursor_X = 0;
    int cursor_Y = 0;
    JLabel barra_estado;
    Forma pieza;
    Tetrominoes[] tablero;

    public Panel() {
        initComponents();
        setFocusable(true);
        pieza = new Forma();
        tiempo = new Timer(400, this);
        tiempo.start();

        tablero = new Tetrominoes[ancho_tablero * alto_tablero];
        limpiar_tablero();
    }

    public void actionPerformed(ActionEvent e) {
        if (fin) {
            fin = false;
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

    Tetrominoes foma_en(int x, int y) {
        return tablero[(y * ancho_tablero) + x];
    }

    public void iniciar() {
        if (pauso) {
            return;
        }

        inicio = true;
        fin = false;
        num_linas_removidas = 0;
        limpiar_tablero();

        nueva_pieza();
        tiempo.start();
    }

    private void pausar() {
        if (!inicio) {
            return;
        }

        pauso = !pauso;
        if (pauso) {
            Reproductor.obtenerReproductor().detenerFondo();
            tiempo.stop();
            barra_estado.setText("PAUSA");
        } else {
            Reproductor.obtenerReproductor().reproducirFondo(Reproductor.FONDO_SINGLE_PLAYER, true);
            tiempo.start();
            barra_estado.setText(String.valueOf("PUNTAJE:  " + num_linas_removidas));
        }
        repaint();
    }

    public void paint(Graphics g) {
        super.paint(g);

        Dimension tamaño = getSize();
        int tablero_superior = (int) tamaño.getHeight() - alto_tablero * alto_cuadrado();

        for (int i = 0; i < alto_tablero; ++i) {
            for (int j = 0; j < ancho_tablero; ++j) {
                Tetrominoes forma = foma_en(j, alto_tablero - i - 1);
                if (forma != Tetrominoes.sin_forma) {
                    drawSquare(g, 0 + j * ancho_cuadrado(),
                            tablero_superior + i * alto_cuadrado(), forma);
                }
            }
        }

        if (pieza.getForma() != Tetrominoes.sin_forma) {
            for (int i = 0; i < 4; ++i) {
                int x = cursor_X + pieza.x(i);
                int y = cursor_Y - pieza.y(i);
                drawSquare(g, 0 + x * ancho_cuadrado(),
                        tablero_superior + (alto_tablero - y - 1) * alto_cuadrado(),
                        pieza.getForma());
            }
        }
    }

    private void despegable() {
        int newY = cursor_Y;
        while (newY > 0) {
            if (!prueba_movimiento(pieza, cursor_X, newY - 1)) {
                break;
            }
            --newY;
        }
        pieza_Caida();
    }

    private void una_linea_debajo() {
        if (!prueba_movimiento(pieza, cursor_X, cursor_Y - 1)) {
            pieza_Caida();
        }
    }

    private void limpiar_tablero() {
        for (int i = 0; i < alto_tablero * ancho_tablero; ++i) {
            tablero[i] = Tetrominoes.sin_forma;
        }
    }

    private void pieza_Caida() {
        for (int i = 0; i < 4; ++i) {
            int x = cursor_X + pieza.x(i);
            int y = cursor_Y - pieza.y(i);
            tablero[(y * ancho_tablero) + x] = pieza.getForma();
        }

        eliminar_lineas_completas();

        if (!fin) {
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
            inicio = false;
            
            Reproductor.obtenerReproductor().detenerFondo();
            Perdiste ventana = new Perdiste(null, true);
            ventana.setVisible(true);

            barra_estado.setText("JUEGO TERMINADO,       [ENTER] NUEVO JUEGO       -     [ESCAPE] SALIR");
        }
    }

    private boolean prueba_movimiento(Forma newPiece, int newX, int newY) {
        for (int i = 0; i < 4; ++i) {
            int x = newX + newPiece.x(i);
            int y = newY - newPiece.y(i);
            if (x < 0 || x >= ancho_tablero || y < 0 || y >= alto_tablero) {
                return false;
            }
            if (foma_en(x, y) != Tetrominoes.sin_forma) {
                return false;
            }
        }

        pieza = newPiece;
        cursor_X = newX;
        cursor_Y = newY;
        repaint();
        return true;
    }

    private void eliminar_lineas_completas() {
        int num_lineas_llenas = 0;

        for (int i = alto_tablero - 1; i >= 0; --i) {
            boolean linea_llena = true;

            for (int j = 0; j < ancho_tablero; ++j) {
                if (foma_en(j, i) == Tetrominoes.sin_forma) {
                    linea_llena = false;
                    break;
                }
            }

            if (linea_llena) {
                ++num_lineas_llenas;
                for (int k = i; k < alto_tablero - 1; ++k) {
                    for (int j = 0; j < ancho_tablero; ++j) {
                        tablero[(k * ancho_tablero) + j] = foma_en(j, k + 1);
                    }
                }
            }
        }

        if (num_lineas_llenas > 0) {
            num_linas_removidas += num_lineas_llenas;
            barra_estado.setText(String.valueOf("PUNTAJE:  " + num_linas_removidas));
            fin = true;
            pieza.setForma(Tetrominoes.sin_forma);
            repaint();
        }
    }

    private void drawSquare(Graphics g, int x, int y, Tetrominoes shape) {
        Color colors[] = {new Color(0, 0, 0), new Color(204, 102, 102),
            new Color(102, 204, 102), new Color(102, 102, 204),
            new Color(204, 204, 102), new Color(204, 102, 204),
            new Color(102, 204, 204), new Color(218, 170, 0)
        };

        Color color = colors[shape.ordinal()];

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
            if (!inicio || pieza.getForma() == Tetrominoes.sin_forma) {
                return;
            }

            if (keycode == 'p' || keycode == 'P') {
                pausar();
                return;
            }

            if (pauso) {
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}

package tetris;

import java.io.Serializable;
import java.util.Random;

public class Forma {

    public enum Tetrominoes implements Serializable {
        sin_forma, forma_Z, forma_S, forma_Linea,
        forma_T, forma_cuadrada, forma_L, forma_L_reflejada
    };

    private Tetrominoes Forma_de_pieza;
    private int coordenadas[][];
    private int[][][] tabla_de_coordenadas;

    public Forma() {

        coordenadas = new int[4][2];
        setForma(Tetrominoes.sin_forma);

    }

    public void setForma(Tetrominoes shape) {

        tabla_de_coordenadas = new int[][][]{
            {{0, 0}, {0, 0}, {0, 0}, {0, 0}},
            {{0, -1}, {0, 0}, {-1, 0}, {-1, 1}},
            {{0, -1}, {0, 0}, {1, 0}, {1, 1}},
            {{0, -1}, {0, 0}, {0, 1}, {0, 2}},
            {{-1, 0}, {0, 0}, {1, 0}, {0, 1}},
            {{0, 0}, {1, 0}, {0, 1}, {1, 1}},
            {{-1, -1}, {0, -1}, {0, 0}, {0, 1}},
            {{1, -1}, {0, -1}, {0, 0}, {0, 1}}
        };

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 2; ++j) {
                coordenadas[i][j] = tabla_de_coordenadas[shape.ordinal()][i][j];
            }
        }
        Forma_de_pieza = shape;

    }

    private void setX(int index, int x) {
        coordenadas[index][0] = x;
    }

    private void setY(int index, int y) {
        coordenadas[index][1] = y;
    }

    public int x(int index) {
        return coordenadas[index][0];
    }

    public int y(int index) {
        return coordenadas[index][1];
    }

    public Tetrominoes getForma() {
        return Forma_de_pieza;
    }

    public void setFormaAleatoria() {
        Random r = new Random();
        int x = Math.abs(r.nextInt()) % 7 + 1;
        Tetrominoes[] values = Tetrominoes.values();
        setForma(values[x]);
    }

    public int minX() {
        int m = coordenadas[0][0];
        for (int i = 0; i < 4; i++) {
            m = Math.min(m, coordenadas[i][0]);
        }
        return m;
    }

    public int minY() {
        int m = coordenadas[0][1];
        for (int i = 0; i < 4; i++) {
            m = Math.min(m, coordenadas[i][1]);
        }
        return m;
    }

    public Forma giroIzquierda() {
        if (Forma_de_pieza == Tetrominoes.forma_cuadrada) {
            return this;
        }

        Forma resultado = new Forma();
        resultado.Forma_de_pieza = Forma_de_pieza;

        for (int i = 0; i < 4; ++i) {
            resultado.setX(i, y(i));
            resultado.setY(i, -x(i));
        }
        return resultado;
    }

    public Forma giroDerecha() {
        if (Forma_de_pieza == Tetrominoes.forma_cuadrada) {
            return this;
        }

        Forma resultado = new Forma();
        resultado.Forma_de_pieza = Forma_de_pieza;

        for (int i = 0; i < 4; ++i) {
            resultado.setX(i, -y(i));
            resultado.setY(i, x(i));
        }
        return resultado;
    }
}

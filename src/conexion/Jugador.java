package conexion;

import java.io.Serializable;

public class Jugador implements Serializable{
    private String ip;
    private String nombre;
    private boolean jugando;
    private boolean conectado;
    private Forma.Tetrominoes[] tablero;

    public Jugador(String ip, String nombre, boolean jugando, boolean conectado, Forma.Tetrominoes[] tablero) {
        this.ip = ip;
        this.nombre = nombre;
        this.jugando = jugando;
        this.conectado = conectado;
        this.tablero = tablero;
    }

    public Forma.Tetrominoes[] getTablero() {
        return tablero;
    }

    public void setTablero(Forma.Tetrominoes[] tablero) {
        this.tablero = tablero;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String obtenerNombre() {
        return nombre;
    }

    public void seleccionarNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean estaJugando() {
        return jugando;
    }

    public void setJugando(boolean jugando) {
        this.jugando = jugando;
    }

    public boolean estaConectado() {
        return conectado;
    }

    public void setConectado(boolean conectado) {
        this.conectado = conectado;
    }

    @Override
    public String toString() {
        return "Jugador{" + "ip=" + ip + ", nombre=" + nombre + ", jugando=" + jugando + ", conectado=" + conectado + ", tablero=" + tablero + '}';
    }

   
}

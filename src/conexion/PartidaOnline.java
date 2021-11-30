package conexion;

import java.util.LinkedList;
import java.util.List;

public class PartidaOnline {

    private List<Jugador> jugadores = new LinkedList<>();
    private boolean jugando;
    private int cantidadJugadores;
    private int count = 0;
    
    public PartidaOnline(boolean jugando, int cantidadJugadores) {
        this.jugando = jugando;
        this.cantidadJugadores = cantidadJugadores;
    }

    public void registrarJugador(Jugador jugador) {
        count++;
        if (cantidadJugadores == count) {
            jugando = true;
        } else {
            
            jugadores.add(jugador);
        }
    }

    public List<Jugador> obtenerJugadores() {
        return jugadores;
    }
    
    public Jugador obtenerJugador(Jugador jugador){
        Jugador objeto = null;
         for (Jugador item : jugadores) {
            if (item.getIp().equals(jugador.getIp())) {
                objeto = item;
                verificarFinDePartida();
                break;
            }
        }
         return objeto;
    }
    
    public void verificarFinDePartida() {
        int count = 0;
        for (Jugador item : jugadores) {
            if(item.estaJugando()){
                count++;
            }
        }
        if(count <= 1){
            jugando = false;
        }
    }
    
//este metodo me falto    
    public void filaCompletada(Jugador jugador) {
        
    }
    
    public void figuraAgregada(Jugador jugador) {
        for (Jugador item : jugadores) {
            if (item.getIp().equals(jugador.getIp())) {
                item.setTablero(jugador.getTablero());
                verificarFinDePartida();
                break;
            }
        }
    }

    public void jugadorHaPerdido(Jugador jugador) {
        for (Jugador item : jugadores) {
            if (item.getIp().equals(jugador.getIp())) {
                item.setJugando(false);
                verificarFinDePartida();
                break;
            }
        }
    }

    public void jugadorDesconectado(Jugador jugador) {
        for (Jugador item : jugadores) {
            if (item.getIp().equals(jugador.getIp())) {
                item.setConectado(false);
                item.setJugando(false);
                verificarFinDePartida();
                break;
            }
        }
    }

    public boolean isJugando() {
        return jugando;
    }
    
    
}

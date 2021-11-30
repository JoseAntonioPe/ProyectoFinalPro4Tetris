
package conexion;

import java.io.Serializable;
import java.util.List;

public class ListaSocket implements Serializable {
    
    private String peticion;
    private List<Jugador> jugadores;

    public ListaSocket(String peticion, List<Jugador> jugador) {
        this.peticion = peticion;
        this.jugadores = jugador;
    }

    public String getPeticion() {
        return peticion;
    }

    public void setPeticion(String peticion) {
        this.peticion = peticion;
    }

    public List<Jugador> getJugadores() {
        return jugadores;
    }

    public void setJugadores(List<Jugador> jugador) {
        this.jugadores = jugador;
    }
 
}

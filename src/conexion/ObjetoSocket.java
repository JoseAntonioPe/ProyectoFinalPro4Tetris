
package conexion;

import java.io.Serializable;

public class ObjetoSocket implements Serializable {
    
    private String peticion;
    private Jugador jugador;

    public ObjetoSocket(String peticion, Jugador jugador) {
        this.peticion = peticion;
        this.jugador = jugador;
    }

  
    public String getPeticion() {
        return peticion;
    }

    public void setPeticion(String peticion) {
        this.peticion = peticion;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }
    
    

 
}

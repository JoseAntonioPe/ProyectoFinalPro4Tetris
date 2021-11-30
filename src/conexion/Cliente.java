package conexion;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class Cliente extends Conn {

    public Cliente() throws IOException {
        super("cliente");
    } //Se usa el constructor para cliente de Conexion

    public void registrarse(Jugador jugador) {
        try {
            salidaObjetoServidor = new ObjectOutputStream(cs.getOutputStream());
            ObjetoSocket objeto = new ObjetoSocket("registrarJugador", jugador);
            salidaObjetoServidor.writeObject(objeto);
            cs.close();
        } catch (Exception e) {
        }
    }

    public void figuraAgregada(Jugador jugador) {
        try {
            salidaObjetoServidor = new ObjectOutputStream(cs.getOutputStream());
            ObjetoSocket objeto = new ObjetoSocket("figuraAgregada", jugador);
            salidaObjetoServidor.writeObject(objeto);
            cs.close();
        } catch (Exception e) {
        }
    }

    public String obtenerEstado() {
        String data = "";
        try {
            salidaObjetoServidor = new ObjectOutputStream(cs.getOutputStream());
            ObjetoSocket objeto = new ObjetoSocket("estado", null);
            salidaObjetoServidor.writeObject(objeto);
            ObjectInputStream entradaObjeto = new ObjectInputStream(cs.getInputStream());
            ObjetoSocket objetoEntrada = (ObjetoSocket) entradaObjeto.readObject();
            data = objetoEntrada.getPeticion();
            cs.close();//Fin de la conexión

        } catch (Exception e) {

        }

        if (data != null) {

            return data;
        }
        return "no responde";

    }

    public List<Jugador> obtenerData() {
        List<Jugador> data = null;
        try {
            salidaObjetoServidor = new ObjectOutputStream(cs.getOutputStream());
            ObjetoSocket peticion = new ObjetoSocket("obtenerData", null);
            salidaObjetoServidor.writeObject(peticion);
            ObjectInputStream entradaObjeto = new ObjectInputStream(cs.getInputStream());
            ListaSocket lista = (ListaSocket) entradaObjeto.readObject();
            data = lista.getJugadores();
            cs.close();//Fin de la conexión

        } catch (Exception e) {

        }
        return data;
    }
}

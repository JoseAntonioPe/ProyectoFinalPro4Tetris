package conexion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Servidor extends Conn {

    private PartidaOnline partida = new PartidaOnline(false, 2);

    public Servidor() throws IOException {
        super("servidor");
    } //Se usa el constructor para servidor de Conexion

    public void startServer()//Método para iniciar el servidor
    {
        try {

            while (true) {
                System.out.println("Esperando..."); //Esperando conexión
                cs = ss.accept(); //Accept comienza el socket y espera una conexión desde un cliente

                System.out.println("Cliente en línea");

                BufferedReader entrada = new BufferedReader(new InputStreamReader(cs.getInputStream()));
                ObjectInputStream entradaObjeto = new ObjectInputStream(cs.getInputStream());
                ObjetoSocket objetoSocket = (ObjetoSocket) entradaObjeto.readObject();
                String peticion = objetoSocket.getPeticion();

                if (peticion.equals("registrarJugador")) {
                    System.out.println("peticion: " + peticion);
                    partida.registrarJugador(objetoSocket.getJugador());
                    System.out.println("nombre: " + objetoSocket.getJugador().obtenerNombre());
                    System.out.println(partida.obtenerJugadores().size());
                }

                if (peticion.equals("estado")) {
                    salidaObjetoServidor = new ObjectOutputStream(cs.getOutputStream());
                    ObjetoSocket respuesta = new ObjetoSocket("" + partida.isJugando(), null);
                    salidaObjetoServidor.writeObject(respuesta);
                }

                if (peticion.equals("obtenerData")) {
                    salidaObjetoServidor = new ObjectOutputStream(cs.getOutputStream());
                    ListaSocket respuesta = new ListaSocket("" + partida.isJugando(), partida.obtenerJugadores());
                    salidaObjetoServidor.writeObject(respuesta);
                    System.out.println("peticion: " + objetoSocket.getPeticion());
                }

                if (peticion.equals("figuraAgregada")) {
                    System.out.println("peticion: " + objetoSocket.getPeticion());
                    partida.figuraAgregada(objetoSocket.getJugador());

                }
                if (peticion.equals("jugador ha perdido")) {
                    System.out.println("peticion: " + objetoSocket.getPeticion());
                    partida.jugadorHaPerdido(objetoSocket.getJugador());

                }
                if (peticion.equals("fila completada")) {
                    System.out.println("peticion: " + objetoSocket.getPeticion());
                }

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}

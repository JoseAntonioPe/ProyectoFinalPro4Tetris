package conexion;

import java.io.IOException;

public class Second {

    public static void main(String[] args) throws IOException, InterruptedException {
            System.out.println("Iniciando cliente\n");
            Cliente cli = new Cliente(); //Se crea el cliente
            cli.registrarse(new Jugador("1515", "aaklsdk", true, true, null));
    }
}

package Vista;

import java.util.Scanner;

public class VistaTorneo {
    private Scanner sc = new Scanner(System.in);

    public void mostrarMenu() {
        System.out.println("MENÚ DEL TORNEO");
        System.out.println("1 - Registrar equipo");
        System.out.println("2 - Registrar jugador");
        System.out.println("3 - Generar fixture automático");
        System.out.println("4- Mostrar Equipos por Zona");
        System.out.println("5- Mostrar Jugadores por Equipo");
        System.out.println("6- Mostrar Equipo de un Jugador");
        System.out.println("7 - Cargar resultado de un partido");
        System.out.println("8 - Ver tabla de posiciones");
        System.out.println("9- Mostrar Resultados de todos los Partidos");
        System.out.println("10- Mostrar Equipo campeon");
        System.out.println("0 - Salir");
    }

    public int getOpcion() {
        System.out.print("Elegí una opción: ");
        return sc.nextInt();
    }

    public String pedirNombreEquipo() {
        System.out.print("Nombre del equipo: ");
        return sc.next();
    }

    public String pedirZona() {
        System.out.print("Zona (A o B): ");
        return sc.next();
    }

    public String pedirNombreJugador() {
        System.out.print("Nombre del jugador: ");
        return sc.next();
    }

    public String pedirNombreLocal() {
        System.out.print("Nombre del equipo local: ");
        return sc.next();
    }

    public String pedirNombreVisitante() {
        System.out.print("Nombre del equipo visitante: ");
        return sc.next();
    }

    public int pedirGoles(String equipo) {
        System.out.print("Goles de " + equipo + ": ");
        return sc.nextInt();
    }

    public String pedirFecha() {
        System.out.print("Fecha del partido (ej: Fecha 1): ");
        return sc.next();
    }
    public int seleccionarPartido(int cantidad) {
    System.out.print("Elegí el número del partido (1 a " + cantidad + "): ");
    while (!sc.hasNextInt()) {
        System.out.print("Debés ingresar un número válido: ");
        sc.next(); 
    }
    return sc.nextInt();
}
    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }
}

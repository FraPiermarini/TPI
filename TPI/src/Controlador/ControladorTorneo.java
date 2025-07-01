package Controlador;

import Modelo.*;
import Persistencia.EquipoDAO;
import Persistencia.JugadorDAO;
import Persistencia.PartidoDAO;
import Vista.VistaTorneo;

import java.util.ArrayList;
import java.util.List;


public class ControladorTorneo {
    private ArrayList<Equipo> equipos = new ArrayList<>();
    private ArrayList<Partido> partidos = new ArrayList<>();
    private ArrayList<Equipo> zonaA = new ArrayList<>();
    private ArrayList<Equipo> zonaB = new ArrayList<>();
    private VistaTorneo vista = new VistaTorneo();
    private JugadorDAO jugadorDAO = new JugadorDAO();
    private EquipoDAO equipoDAO = new EquipoDAO();
    private PartidoDAO partidoDAO = new PartidoDAO();



    public void cargarEquiposDesdeBD() { //Con este metodo cargamos los equipos en la BD y los separamos por zonas.
    List<Equipo> desdeBD = equipoDAO.obtenerTodosLosEquipos();
    for (Equipo e : desdeBD) {
        equipos.add(e);
        if (e.getZona().equalsIgnoreCase("A")) {
            zonaA.add(e);
        } else if (e.getZona().equalsIgnoreCase("B")) {
            zonaB.add(e);
        }
    }
    vista.mostrarMensaje("Equipos cargados desde la base de datos.");
}
    public void cargarJugadoresDesdeBD() {  //Aca cargamos los jugadores en la base de datos por equipo
    jugadorDAO.obtenerJugadores(equipos);
    vista.mostrarMensaje("Jugadores cargados desde la base de datos.");
    }



    public void iniciar() {  //Iniciamos el menu
        cargarEquiposDesdeBD();
        cargarJugadoresDesdeBD();
        int opcion;
        do {
            vista.mostrarMenu();
            opcion = vista.getOpcion();

            switch (opcion) {
                case 1:
                    registrarEquipo();
                    break;
                case 2:
                    registrarJugador();
                    break;
                case 3:
                    generarFixture();
                    break;
                case 4:
                    mostrarEquiposPorZona();
                    break;
                case 5:
                    mostrarJugadoresEquipo();
                    break;
                case 6:
                    buscarEquipoJugador();
                    break;
                case 7:
                    registrarResultado();
                    break;
                case 8:
                    mostrarTablaPosiciones();
                    break;
                case 9:
                    mostrarResultados();
                    break;
                case 10:
                    mostrarCampeon();
                    break;
                case 0:
                    vista.mostrarMensaje("Saliendo del sistema.");
                    break;
                default:
                    vista.mostrarMensaje("Opción inválida.");
                    break;
            }

        } while (opcion != 0);
    }

    public void registrarEquipo() {  //Registramos el equipo y lo guardamos en la BD
    String nombre = vista.pedirNombreEquipo();
    String zona = vista.pedirZona();

    Equipo equipo = new Equipo(nombre, zona);
    equipos.add(equipo);
    equipoDAO.guardarEquipo(equipo); 

    vista.mostrarMensaje("Equipo registrado exitosamente.");
}

    public void registrarJugador() { //Registramos un jugador de un equipo y lo guardamos en la bd
    String nombreJugador = vista.pedirNombreJugador();
    String nombreEquipo = vista.pedirNombreEquipo();

    Equipo equipo = buscarEquipo(nombreEquipo);
    if (equipo == null) {
        vista.mostrarMensaje("El equipo no existe.");
        return;
    }

    Jugador jugador = new Jugador(nombreJugador, equipo);
    equipo.agregarJugador(jugador);
    jugadorDAO.guardarJugador(jugador); // 

    vista.mostrarMensaje("Jugador registrado en " + equipo.getNombre());
}
    public void generarFixture() {  //Generamos el fixture de partidos y lo guardamos en la BD


        for (Equipo e : equipos) {
            if (e.getZona().equalsIgnoreCase("A")) {
                zonaA.add(e);
            } else if (e.getZona().equalsIgnoreCase("B")) {
                zonaB.add(e);
            }
        }

        generarPartidosPorZona(zonaA, "Zona A");
        generarPartidosPorZona(zonaB, "Zona B");
        partidoDAO.guardarPartidos(partidos);


        vista.mostrarMensaje("Fixture generado correctamente por zonas.");
    }

    private void generarPartidosPorZona(ArrayList<Equipo> zona, String nombreZona) {  //Genera todos los posibles partidos de cada zona
        int fecha = 1;

        for (int i = 0; i < zona.size(); i++) {
            for (int j = i + 1; j < zona.size(); j++) {
                Equipo local = zona.get(i);
                Equipo visitante = zona.get(j);
                String Numerofecha = "Fecha " + fecha;

                partidos.add(new Partido(local, visitante, Numerofecha));
                fecha = (fecha % 14) + 1;
            }
        }
    }

    private Equipo buscarEquipo(String nombre) {  //Busca un equipo 
        for (Equipo e : equipos) {
            if (e.getNombre().equalsIgnoreCase(nombre)) {
                return e;
            }
        }
        return null;
    }
    public void mostrarEquiposPorZona() {  //Muestra los equipos por cada zona
        vista.mostrarMensaje("Equipos en Zona A:");
        for (Equipo e : zonaA) {
            vista.mostrarMensaje("- " + e.getNombre());
        }

        vista.mostrarMensaje("Equipos en Zona B:");
        for (Equipo e : zonaB) {
            vista.mostrarMensaje("- " + e.getNombre());
        }
    }
    public void mostrarJugadoresEquipo() {  //Muestra los jugadores de cada equipo
        String nombreEquipo = vista.pedirNombreEquipo();
        Equipo equipo = buscarEquipo(nombreEquipo);

        if (equipo == null) {
            vista.mostrarMensaje("El equipo no existe.");
            return;
        }

        vista.mostrarMensaje("Jugadores de " + equipo.getNombre() + ":");
        for (Jugador j : equipo.getJugadores()) {
            vista.mostrarMensaje("- " + j.getNombre());
        }
    }
    public void buscarEquipoJugador() { //Muestra el equipo de un jugador determinado
        String nombreJugador = vista.pedirNombreJugador();

        for (Equipo e : equipos) {
            for (Jugador j : e.getJugadores()) {
                if (j.getNombre().equalsIgnoreCase(nombreJugador)) {
                    vista.mostrarMensaje(nombreJugador + " pertenece al equipo " + e.getNombre());
                    return;
                }
            }
        }

        vista.mostrarMensaje("No se encontró al jugador.");
    }



    public void registrarResultado() { //Recorre los partidos generados  y cargamos los resultados
        ArrayList<Partido> disponibles = new ArrayList<>();

        for (Partido p : partidos) {
            if (!p.fueJugado()) {
                disponibles.add(p);
            }
        }

        if (disponibles.isEmpty()) {
            vista.mostrarMensaje("No hay partidos pendientes.");
            return;
        }

        for (int i = 0; i < disponibles.size(); i++) {
            vista.mostrarMensaje((i + 1) + " - " + disponibles.get(i));
        }

        int opcion = vista.seleccionarPartido(disponibles.size());

        if (opcion < 1 || opcion > disponibles.size()) {
            vista.mostrarMensaje("Opción inválida.");
            return;
        }

        Partido partidoElegido = disponibles.get(opcion - 1);

        int golesLocal = vista.pedirGoles(partidoElegido.getEquipoLocal().getNombre());
        int golesVisitante = vista.pedirGoles(partidoElegido.getEquipoVisitante().getNombre());

        partidoElegido.registrarResultado(golesLocal, golesVisitante);
        partidoDAO.actualizarResultado(partidoElegido);
        equipoDAO.actualizarEquipo(partidoElegido.getEquipoLocal());
        equipoDAO.actualizarEquipo(partidoElegido.getEquipoVisitante());

        vista.mostrarMensaje("Resultado registrado correctamente.");
    }

    public void mostrarTablaPosiciones() {  //Muestra una tabla de posicion en general
        vista.mostrarMensaje("Tabla de Posiciones");

        for (int i = 0; i < equipos.size() - 1; i++) {
            for (int j = 0; j < equipos.size() - i - 1; j++) {
                if (equipos.get(j).getPuntos() < equipos.get(j + 1).getPuntos()) {
                    equipos.add(j, equipos.remove(j + 1));
                }
            }
        }

        for (int i = 0; i < equipos.size(); i++) {
            vista.mostrarMensaje((i + 1) + "° " + equipos.get(i).getNombre() + ": " + equipos.get(i).getPuntos() + " pts");
        }
    }
    public void mostrarResultados() {  //Muestra todos los resultados de los partidos
        boolean hayResultados = false;

        for (Partido p : partidos) {
            if (p.fueJugado()) {
                String resultado = " " + p.getEquipoLocal().getNombre() + " "
                    + p.getGolesLocal() + " - " + p.getGolesVisitante() + " "
                    + p.getEquipoVisitante().getNombre() + " (" + p.getFecha() + ")";
                vista.mostrarMensaje(resultado);
                hayResultados = true;
            }
        }

        if (!hayResultados) {
            vista.mostrarMensaje("Todavía no hay resultados registrados.");
        }
    }
    public void mostrarCampeon() {  //Muestra al campeon del torneo
        if (equipos.isEmpty()) {
            vista.mostrarMensaje("No hay equipos registrados.");
            return;
        }

        Equipo campeon = equipos.get(0);

        for (Equipo e : equipos) {
            if (e.getPuntos() > campeon.getPuntos()) {
                campeon = e;
            }
        }

        vista.mostrarMensaje("Campeón del Torneo: " + campeon.getNombre() + " con " + campeon.getPuntos() + " puntos.");
    }

}
package Controlador;

import Modelo.*;
import Vista.VistaTorneo;

import java.util.ArrayList;


public class ControladorTorneo {
    private ArrayList<Equipo> equipos = new ArrayList<>();
    private ArrayList<Partido> partidos = new ArrayList<>();
    private ArrayList<Equipo> zonaA = new ArrayList<>();
    private ArrayList<Equipo> zonaB = new ArrayList<>();
    private VistaTorneo vista = new VistaTorneo();

    public void iniciar() {
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
                    mostrarEstadisticas();
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

    public void registrarEquipo() {
        String nombre = vista.pedirNombreEquipo();
        String zona = vista.pedirZona();
        equipos.add(new Equipo(nombre, zona));
        vista.mostrarMensaje("Equipo registrado exitosamente.");
    }

    public void registrarJugador() {
        String nombreJugador = vista.pedirNombreJugador();
        String nombreEquipo = vista.pedirNombreEquipo();

        Equipo equipo = buscarEquipo(nombreEquipo);
        if (equipo == null) {
            vista.mostrarMensaje("El equipo no existe.");
            return;
        }

        equipo.agregarJugador(new Jugador(nombreJugador, equipo));
        vista.mostrarMensaje("Jugador registrado en " + equipo.getNombre());
    }
    public void generarFixture() {


        for (Equipo e : equipos) {
            if (e.getZona().equalsIgnoreCase("A")) {
                zonaA.add(e);
            } else if (e.getZona().equalsIgnoreCase("B")) {
                zonaB.add(e);
            }
        }

        generarPartidosPorZona(zonaA, "Zona A");
        generarPartidosPorZona(zonaB, "Zona B");

        vista.mostrarMensaje("Fixture generado correctamente por zonas.");
    }

    private void generarPartidosPorZona(ArrayList<Equipo> zona, String nombreZona) {
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

    private Equipo buscarEquipo(String nombre) {
        for (Equipo e : equipos) {
            if (e.getNombre().equalsIgnoreCase(nombre)) {
                return e;
            }
        }
        return null;
    }
    public void mostrarEquiposPorZona() {
        vista.mostrarMensaje("Equipos en Zona A:");
        for (Equipo e : zonaA) {
            vista.mostrarMensaje("- " + e.getNombre());
        }

        vista.mostrarMensaje("Equipos en Zona B:");
        for (Equipo e : zonaB) {
            vista.mostrarMensaje("- " + e.getNombre());
        }
    }
    public void mostrarJugadoresEquipo() {
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
    public void buscarEquipoJugador() {
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

    

    public void registrarResultado() {
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
        vista.mostrarMensaje("Resultado registrado correctamente.");
    }

    public void mostrarTablaPosiciones() {
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

    public void mostrarEstadisticas() {
        for (Equipo e : equipos) {
            vista.mostrarMensaje("Jugadores de " + e.getNombre() + ":");
            for (Jugador j : e.getJugadores()) {
                String info = "- " + j.getNombre() +
                              "Goles: " + j.getGoles() +
                              "Amarillas: " + j.getAmarillas() +
                              "Rojas: " + j.getRojas();
                vista.mostrarMensaje(info);
            }
        }
    }
}
package Modelo;
import java.util.ArrayList;
public class Equipo {
    private String nombre;
    private String zona;
    private int puntos;
    private int golesAFavor;
    private int golesEnContra;
    private ArrayList<Jugador> jugadores;

    public Equipo(String nombre, String zona){
        this.nombre = nombre;
        this.zona = zona;
        this.puntos = 0;
        this.golesAFavor = 0;
        this.golesEnContra = 0;
        this.jugadores = new ArrayList<>();
    }
    public String getNombre(){
        return nombre;
    }
    public String getZona(){
        return zona;
    }
    public int getPuntos(){
        return puntos;
    }
    public int getGolesAFavor(){
        return golesAFavor;
    }
    public int getGolesEnContra(){
        return golesEnContra;
    }
    public ArrayList<Jugador> getJugadores(){
        return jugadores;
    }
        public void agregarJugador(Jugador jugador) {
        jugadores.add(jugador);
    }

    public void sumarPuntos(int puntos) {
        this.puntos += puntos;
    }

    public void actualizarGoles(int aFavor, int enContra) {
        this.golesAFavor += aFavor;
        this.golesEnContra += enContra;
    }

    @Override
    public String toString() {
        return nombre + " (Zona " + zona + ")";
    }
}




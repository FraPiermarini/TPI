package Modelo;

public class Jugador {
    private String nombre;
    private Equipo equipo;


    public Jugador(String nombre, Equipo equipo){
        this.nombre = nombre;
        this.equipo = equipo;

    }
    public String getNombre(){
        return nombre;
    }
    public Equipo getEquipo(){
        return equipo;
    }

    @Override
    public String toString(){
        return "Nombre Jugador: " + nombre + "Equipo del jugador: " + equipo;
    }
}

package Modelo;

public class Jugador {
    private String nombre;
    private Equipo equipo;
    private int goles;
    private int amarillas;
    private int rojas;

    public Jugador(String nombre, Equipo equipo){
        this.nombre = nombre;
        this.equipo = equipo;
        this.goles = 0;
        this.amarillas = 0;
        this.rojas = 0;
    }
    public String getNombre(){
        return nombre;
    }
    public Equipo getEquipo(){
        return equipo;
    }
    public int getGoles(){
        return goles;
    }
    public int getAmarillas(){
        return amarillas;
    }
    public int getRojas(){
        return rojas;
    }
    public void sumarGoles(){
        goles++;
    }
    public void sumarAmarillas(){
        amarillas++;
    }
    public void sumarRojas(){
        rojas++;
    }
    @Override
    public String toString(){
        return "Nombre Jugador: " + nombre + "Equipo del jugador: " + equipo + "Goles: " + goles + "Amarillas: " + amarillas + "Rojas: " + rojas;
    }
}

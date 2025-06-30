package Modelo;

public class Partido {
    private Equipo equipoLocal;
    private Equipo equipoVisitante;
    private String fecha;
    private int golesLocal;
    private int golesVisitante;
    private boolean jugado;

    public Partido(Equipo equipoLocal, Equipo equipoVisitante, String fecha) {
        this.equipoLocal = equipoLocal;
        this.equipoVisitante = equipoVisitante;
        this.fecha = fecha;
        this.golesLocal = -1;
        this.golesVisitante = -1;
        this.jugado = false;
    }

    public Equipo getEquipoLocal() {
        return equipoLocal;
    }

    public Equipo getEquipoVisitante() {
        return equipoVisitante;
    }

    public String getFecha() {
        return fecha;
    }

    public boolean fueJugado() {
        return jugado;
    }

    public int getGolesLocal() {
        return golesLocal;
    }

    public int getGolesVisitante() {
        return golesVisitante;
    }
    public void setGolesLocal(int golesLocal) {
    this.golesLocal = golesLocal;
}

public void setGolesVisitante(int golesVisitante) {
    this.golesVisitante = golesVisitante;
}


    public void registrarResultado(int golesLocal, int golesVisitante) {
        this.golesLocal = golesLocal;
        this.golesVisitante = golesVisitante;
        this.jugado = true;

        equipoLocal.actualizarGoles(golesLocal, golesVisitante);
        equipoVisitante.actualizarGoles(golesVisitante, golesLocal);

        if (golesLocal > golesVisitante) {
            equipoLocal.sumarPuntos(3);
        } else if (golesVisitante > golesLocal) {
            equipoVisitante.sumarPuntos(3);
        } else {
            equipoLocal.sumarPuntos(1);
            equipoVisitante.sumarPuntos(1);
        }
    }

    @Override
    public String toString() {
        return equipoLocal.getNombre() + " vs " + equipoVisitante.getNombre() + " (" + fecha + ")";
    }
}

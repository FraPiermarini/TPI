package Persistencia;

import Modelo.Equipo;
import ConexionBD.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EquipoDAO {  //Clase creada para insertar los equipos a la base de datos

    public void guardarEquipo(Equipo equipo) {
        String sql = "INSERT INTO equipo (nombre, zona, puntos, golesAFavor, golesEnContra) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, equipo.getNombre());
            stmt.setString(2, equipo.getZona());
            stmt.setInt(3, equipo.getPuntos());
            stmt.setInt(4, equipo.getGolesAFavor());
            stmt.setInt(5, equipo.getGolesEnContra());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al guardar equipo: " + e.getMessage());
        }
    }

    public List<Equipo> obtenerTodosLosEquipos() {
        List<Equipo> lista = new ArrayList<>();
        String sql = "SELECT nombre, zona, puntos, golesAFavor, golesEnContra FROM equipo";

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String nombre = rs.getString("nombre");
                String zona = rs.getString("zona");
                int puntos = rs.getInt("puntos");
                int golesAFavor = rs.getInt("golesAFavor");
                int golesEnContra = rs.getInt("golesEnContra");

                Equipo equipo = new Equipo(nombre, zona);
                equipo.sumarPuntos(puntos);
                equipo.actualizarGoles(golesAFavor, golesEnContra);

                lista.add(equipo);
            }

        } catch (SQLException e) {
            System.out.println("Error al recuperar equipos: " + e.getMessage());
        }

        return lista;
    }
    public void actualizarEquipo(Equipo equipo) {
    String sql = "UPDATE equipo SET puntos = ?, golesAFavor = ?, golesEnContra = ? WHERE nombre = ?";

    try (Connection conn = ConexionBD.conectar();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, equipo.getPuntos());
        stmt.setInt(2, equipo.getGolesAFavor());
        stmt.setInt(3, equipo.getGolesEnContra());
        stmt.setString(4, equipo.getNombre());

        stmt.executeUpdate();

    } catch (SQLException e) {
        System.out.println("Error al actualizar el equipo: " + e.getMessage());
    }
}
}
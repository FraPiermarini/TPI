package Persistencia;

import Modelo.Equipo;
import Modelo.Partido;
import ConexionBD.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PartidoDAO {

    public void guardarPartidos(List<Partido> partidos) { //Clase creada para insertar los partidos a la BD
        String sql = "INSERT INTO partido (equipo_local_id, equipo_visitante_id, fecha, goles_local, goles_visitante, jugado) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionBD.conectar()) {
            for (Partido p : partidos) {
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setInt(1, obtenerIdEquipo(conn, p.getEquipoLocal().getNombre()));
                    stmt.setInt(2, obtenerIdEquipo(conn, p.getEquipoVisitante().getNombre()));
                    stmt.setString(3, p.getFecha());
                    stmt.setInt(4, p.getGolesLocal());
                    stmt.setInt(5, p.getGolesVisitante());
                    stmt.setBoolean(6, p.fueJugado());
                    stmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al guardar partidos: " + e.getMessage());
        }
    }

    public List<Partido> obtenerPartidos(List<Equipo> equipos) {
        List<Partido> lista = new ArrayList<>();
        String sql = "SELECT equipo_local_id, equipo_visitante_id, fecha, goles_local, goles_visitante, jugado FROM partido";

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int idLocal = rs.getInt("equipo_local_id");
                int idVisitante = rs.getInt("equipo_visitante_id");
                String fecha = rs.getString("fecha");
                int golesLocal = rs.getInt("goles_local");
                int golesVisitante = rs.getInt("goles_visitante");
                boolean jugado = rs.getBoolean("jugado");

                Equipo local = buscarEquipoPorId(conn, equipos, idLocal);
                Equipo visitante = buscarEquipoPorId(conn, equipos, idVisitante);

                if (local != null && visitante != null) {
                    Partido partido = new Partido(local, visitante, fecha);
                    if (jugado) {
                        partido.registrarResultado(golesLocal, golesVisitante);
                    }
                    lista.add(partido);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al recuperar partidos: " + e.getMessage());
        }

        return lista;
    }

    public void actualizarResultado(Partido partido) {
        String sql = "UPDATE partido SET goles_local = ?, goles_visitante = ?, jugado = ? " +
                     "WHERE equipo_local_id = ? AND equipo_visitante_id = ? AND fecha = ?";

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, partido.getGolesLocal());
            stmt.setInt(2, partido.getGolesVisitante());
            stmt.setBoolean(3, true);
            stmt.setInt(4, obtenerIdEquipo(conn, partido.getEquipoLocal().getNombre()));
            stmt.setInt(5, obtenerIdEquipo(conn, partido.getEquipoVisitante().getNombre()));
            stmt.setString(6, partido.getFecha());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error al actualizar el resultado del partido: " + e.getMessage());
        }
    }

    private int obtenerIdEquipo(Connection conn, String nombreEquipo) throws SQLException {
        String sql = "SELECT id FROM equipo WHERE nombre = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombreEquipo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            } else {
                throw new SQLException("No se encontró el equipo '" + nombreEquipo + "' en la base.");
            }
        }
    }

    private Equipo buscarEquipoPorId(Connection conn, List<Equipo> equipos, int idBuscado) throws SQLException {
        String sql = "SELECT nombre FROM equipo WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idBuscado);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String nombre = rs.getString("nombre");
                for (Equipo e : equipos) {
                    if (e.getNombre().equalsIgnoreCase(nombre)) {
                        return e;
                    }
                }
            }
        }
        return null;
    }
}
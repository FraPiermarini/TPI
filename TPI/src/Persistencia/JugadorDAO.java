package Persistencia;

import Modelo.Jugador;
import Modelo.Equipo;
import ConexionBD.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class JugadorDAO {  //Clase creada para insertar los jugadores a lla base de datos

    public void guardarJugador(Jugador jugador) {
        String sql = "INSERT INTO jugador (nombre, equipo_id) VALUES (?, ?)";

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, jugador.getNombre());
            stmt.setInt(2, obtenerIdEquipo(conn, jugador.getEquipo().getNombre()));
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error al guardar jugador: " + e.getMessage());
        }
    }

    public void obtenerJugadores(List<Equipo> equipos) {
        String sql = "SELECT nombre, equipo_id FROM jugador";

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String nombreJugador = rs.getString("nombre");
                int equipoId = rs.getInt("equipo_id");

                for (Equipo equipo : equipos) {
                    int idEncontrado = obtenerIdEquipo(conn, equipo.getNombre());
                    if (idEncontrado == equipoId) {
                        Jugador jugador = new Jugador(nombreJugador, equipo);
                        equipo.agregarJugador(jugador);
                        break;
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al recuperar jugadores: " + e.getMessage());
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
                throw new SQLException("No se encontro el equipo '" + nombreEquipo + "' en la base.");
            }
        }
    }
}
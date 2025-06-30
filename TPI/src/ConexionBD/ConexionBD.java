package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conexionBD {
   private static final String URL = "jdbc:mysql://localhost:3306/vete";
   private static final String USER = "root";
   private static final String PASSWORD = "tpi123";

   public conexionBD() {
   }

   public static Connection getConnection() throws SQLException {
      try {
         Class.forName("com.mysql.cj.jdbc.Driver");
         return DriverManager.getConnection("jdbc:mysql://localhost:3306/vete", "root", "Marcio33");
      } catch (ClassNotFoundException var1) {
         throw new SQLException("MySQL JDBC Driver no encontrado", var1);
      }
   }
}

package basedatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AdminBD {

	public static Connection obtenerConexion() throws ClassNotFoundException, SQLException {
		Connection con = null;

		Class.forName("com.mysql.jdbc.Driver");

		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/schemaprueba", "root", "");

		return con;
	}

}
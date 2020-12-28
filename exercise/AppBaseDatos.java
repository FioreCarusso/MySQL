package exercise;

import abm.AdminBD;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AppBaseDatos {

	public static void main(String[] args) {




		/*objetos statement*/

		Statement stat;

		try {
			Connection conexion = AdminBD.obtenerConexion(); // creo objeto conexion

			stat = conexion.createStatement();
			            // HACER INSERT DE UN NUEVO CLIENTE
						// TOMAR LOS DATOS POR TECLADO
						// USAR stmt.executeUpdate en lugar de stmt.executeQuery
            int id = 11;
			String nom = "Ludmila";
			String ape = "Velazco";
			String gen = "FEM";
			String dni = "336";
			
			String insert = "INSERT INTO clientes VALUES (" + id + ", ' " + nom + "', '" + ape + " ', '" + gen + " ', '" + dni + "')";
			stat.executeUpdate(insert);
			
			ResultSet resultado = stat.executeQuery("SELECT * FROM clientes");// creo objeto result
			while (resultado.next()) { // recorro el objeto result
				System.out.println( // imprimo
						resultado.getInt(1) + "  " + resultado.getString(2) + "  " + resultado.getString(3) + "  "
								+ resultado.getString(4) + " " + resultado.getString(5));
			}
			conexion.close();
			
		} catch (SQLException | ClassNotFoundException e) {
	
			e.printStackTrace();

		}
	}
}

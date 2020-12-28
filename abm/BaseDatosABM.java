package basedatos;

import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BaseDatosABM {

	public static void main(String[] args) {

		Connection conexion;
		try {
			conexion = AdminBD.obtenerConexion();

			Scanner scan = new Scanner(System.in);

			int operacion = mostrarMenu(scan);

			while (operacion != 0) {
				switch (operacion) {
				case 1:
					alta(conexion, scan);
					break;
				case 2:
					modificacion(scan, conexion);
					break;
				case 3:
					baja(conexion, scan);
					break;
				case 4:
					mostrarListado(conexion);
					break;

				case 5:
					buscarListado(conexion, scan);
					break;
				case 0:
					break;
				default:
					System.out.println("Ingrese una opcion correcta.");
				}

				operacion = mostrarMenu(scan);
			}

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();
		}

	}

//MOSTRAR MENU

	private static int mostrarMenu(Scanner scan) {
		System.out.println(" SISTEMA ABM de PERSONAS  ");
		System.out.println("_____________________________________");

		System.out.println("OPCIONES");
		System.out.println("1 - Alta");
		System.out.println("2 - Modificacion");
		System.out.println("3 - Baja");
		System.out.println("4 - Mostrar Listado");
		System.out.println("5 - Buscar en el Listado");
		System.out.println("0 - Salir");
		int operacion = 0;
		operacion = scan.nextInt();
		return operacion;

	}

//ALTA DE DATOS/Insert

	private static void alta(Connection conexion, Scanner scan) {
		System.out.println("ALTA DE PERSONA. INGRESE SUS DATOS");
		System.out.println("__________________________________");
		System.out.println("");

		System.out.println("Ingrese Nombre");
		String nombre = scan.next();

		System.out.println("Ingrese fecha de nacimiento (formato YYYY-MM-DD)");
		String fechaNacimiento = scan.next();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Date fechaNa;
		try {
			fechaNa = sdf.parse(fechaNacimiento);
			int edad = calculoEdad(fechaNa); // ACÁ

			Statement stat = conexion.createStatement();

			stat.executeUpdate("INSERT INTO PERSONA (NombreCompleto, Edad, FechaNacimiento) VALUES ('" + nombre + "',"
					+ edad + ",'" + fechaNacimiento + "')");

			System.out.println("");
			System.out.println("Alta de persona exitosa");
			System.out.println(" ");

		} catch (ParseException | SQLException e) {
			e.printStackTrace();
		}

	}

//BAJA DE DATOS/Delet

	private static void baja(Connection conexion, Scanner scan) {

		try {
			Statement stat = conexion.createStatement();

			System.out.println("BAJA DE DATOS");
			System.out.println("_____________________________________");
			System.out.println("");
			System.out.println("Ingrese el ID del usuario al que dar de baja");
			int id = scan.nextInt();

			ResultSet resultado = stat.executeQuery("SELECT * FROM persona WHERE IDPersona=" + id + ";");

			System.out.println("¿Está seguro de querer eliminar el siguiente registro? 1-SI | 2-NO");

			while (resultado.next()) {
				Date fechaNa = resultado.getDate(4);
				System.out.println(resultado.getInt(1) + "   " + resultado.getString(2) + "    " + resultado.getInt(3)
						+ "       " + fechaNa);

			}

			int opcion = scan.nextInt();

			switch (opcion) {
			case 1:
				ResultSet rs = stat.executeQuery("SELECT * FROM persona WHERE IDPersona=" + id + ";");

				if (rs.next()) {
					stat.executeUpdate("DELETE FROM persona WHERE IDPersona = " + id + ";");
					System.out.println("Se dio de baja el registro:" + id);
					conexion.close();

				} else {
					System.out.println("El ID: " + id + " ingresado no existe. ");
				}

			case 2:
				System.out.println("No se registraron bajas");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

//MOSTRAR LISTADO/Select (chequeado)
	private static void mostrarListado(Connection conexion) {

		System.out.println();
		System.out.println("LISTADO DE DATOS");
		System.out.println("_____________________________________");
		System.out.println("ID____NOMBRE____EDAD_____F.NACIM_____");
		Statement stat;
		try {
			stat = conexion.createStatement();
			ResultSet resultado = stat.executeQuery("SELECT * FROM persona");
			while (resultado.next()) {
				Date fechaNa = resultado.getDate(4);
				System.out.println(resultado.getInt(1) + "   " + resultado.getString(2) + "    " + resultado.getInt(3)
						+ "       " + fechaNa);

			}
			System.out.println("_____________________________________");
			System.out.println("__________FIN DEL LISTADO____________");
			System.out.println();
			conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();

		}

	}

//MODIFICACION DE DATOS/Update

	private static void modificacion(Scanner scan, Connection conexion) {
		/*
		 * UPDATE persona SET NombreCompleto = 'nombre' WHERE ID = 1
		 */
		try {
			Statement stat = conexion.createStatement();
			System.out.println("MODIFICACIÓN DE DATOS");
			System.out.println("___________________________________________________");
			System.out.println("");

			System.out.println("");
			System.out.println("¿Qué registro desea modificar?");
			System.out.println("Ingrese: 1- NOMBRE.  2-FECHA DE NACIMIENTO.");
			int modificacion = scan.nextInt();

			System.out.println("Ingrese el ID");
			int id = scan.nextInt();

			System.out.println("Está seguro de querer modificar el siguiente registro? 1-SI | 2-NO");

			ResultSet resultado = stat.executeQuery("SELECT * FROM persona WHERE IDPersona= " + id + ";");

			while (resultado.next()) {
				Date fechaNa = resultado.getDate(4);
				System.out.println(resultado.getInt(1) + "   " + resultado.getString(2) + "    " + resultado.getInt(3)
						+ "       " + fechaNa);

			}

			int opcionCambio = scan.nextInt();

			switch (opcionCambio) {

			case 1:
				ResultSet rs = stat.executeQuery("SELECT * FROM persona WHERE IDPersona= " + id + ";");

				if (rs.next()) {

					switch (modificacion) {

					case 1:
						System.out.println("Ingrese el nuevo Nombre");
						String nuevoNombre = scan.next();
						stat.executeUpdate("UPDATE persona SET NombreCompleto =  ' " + nuevoNombre
								+ " ' WHERE IDPersona = " + id + ";");
						System.out.println("Modificacion exitosa");
						conexion.close();
						break;

					case 2:
						System.out.println("Ingrese la nueva Fecha de Nacimiento (Respete el formato: yyyy-mm-dd)");
						String fecha = scan.next();
						SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");

						try {
							Date fechaNa = sdf.parse(fecha);
							int edad = calculoEdad(fechaNa);

							stat.executeUpdate("UPDATE persona SET Edad =  " + edad + ", FechaNacimiento = ' " + fecha
									+ " ' WHERE IDPersona = " + id + ";");
							System.out.println("Modificacion exitosa");

							conexion.close();
							break;

						} catch (ParseException e1) {

							e1.printStackTrace();
						}

					}

				} else {
					System.out.println("El ID: " + id + " no existe");
					System.out.println("  ");
				}

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// CALCULO DE EDAD

	private static int calculoEdad(Date fechaNa) {
		GregorianCalendar hoy = new GregorianCalendar();
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(fechaNa);

		int añoActual = hoy.get(Calendar.YEAR);

		int añoNacimiento = gc.get(Calendar.YEAR);

		int mesActual = hoy.get(Calendar.MONTH);
		int mesNacimiento = gc.get(Calendar.MONTH);

		int diaActual = hoy.get(Calendar.DATE);
		int diaNacimiento = gc.get(Calendar.DATE);
		int difEdad = 0;
		difEdad = añoActual - añoNacimiento;

		if (mesActual < mesNacimiento) {
			difEdad = difEdad - 1;
		} else {
			if (mesActual == mesNacimiento && diaActual < diaNacimiento) {
				difEdad = difEdad - 1;
			}
		}

		return difEdad;

	}

	// BUSQUEDA DE DATOS/Select-Like (chequeado)

	private static void buscarListado(Connection conexion, Scanner scan) {

		try {
			Statement stat = conexion.createStatement();

			System.out.println("BUSQUEDA DE DATOS");
			System.out.println("_____________________________________");
			System.out.println("Ingrese Nombre ");
			String nombre = scan.next();

			ResultSet resultado = stat
					.executeQuery("SELECT * FROM Persona WHERE NombreCompleto LIKE '" + nombre + "%';");

			System.out.println("ID____NOMBRE____EDAD_____F.NACIM_____");

			while (resultado.next()) {
				System.out.println(resultado.getInt(1) + "   " + resultado.getString(2) + "    " + resultado.getInt(3)
						+ "   " + resultado.getDate(4));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}

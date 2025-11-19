package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionBD {

		public static Connection getConnexion() {
			Connection cnx = null;
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				System.out.println("Drive correcto");
				cnx = DriverManager.getConnection("jdbc:mysql://localhost:3306/BD_Bodega_2025","root","1234");
				System.out.println("Conexión correcta");
			}catch (Exception e) {
				System.out.println("Error" + e);	
			}
			
			return cnx;
		}
		
		public static void main (String[]arg) {
			getConnexion();
		}

}

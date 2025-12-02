package Métodos;

import java.sql.CallableStatement;
import java.sql.ResultSet;

import Conexion.ConexionBD;
import Modelo.Usuario;

public class DAO_Usuario {
	public Usuario validarLogin(String nombreUsuario, String contraseña) {
        Usuario user = null;
        try {
            CallableStatement cs = ConexionBD.getConnexion().prepareCall("{call sp_validarUsuario(?,?)}");
            cs.setString(1, nombreUsuario);
            cs.setString(2, contraseña);
            ResultSet rs = cs.executeQuery();
            if (rs.next()) {
                user = new Usuario(
                        rs.getInt("idUsuario"), rs.getString("nombreUsuario"), rs.getString("Contraseña"),rs.getString("nombreCompleto"), rs.getString("rol")
                );
            }
        } catch (Exception e) {
            System.out.println("Error al validar usuario: " + e.getMessage());
        }
        return user;
    }
}

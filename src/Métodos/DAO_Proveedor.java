package Métodos;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import Conexion.ConexionBD;
import Modelo.Proveedor;

public class DAO_Proveedor {
	public void insertar(Proveedor p) {
        try {
            CallableStatement cs = ConexionBD.getConnexion().prepareCall("{call sp_insertarProveedor(?,?,?,?,?)}");
            cs.setString(1, p.getCodProveedor());
            cs.setString(2, p.getRuc());
            cs.setString(3, p.getRazonSocial());
            cs.setString(4, p.getTelefono());
            cs.setString(5, p.getCorreo());
            cs.executeUpdate();
            System.out.println("Proveedor insertado correctamente.");

        } catch (Exception e) {
            System.out.println("Error al insertar proveedor: " + e.getMessage());
        }
    }
	
	public ArrayList<Proveedor> listar() {
        ArrayList<Proveedor> lista = new ArrayList<>();

        try {
            CallableStatement cs = ConexionBD.getConnexion().prepareCall("{call sp_listarProveedores()}");

            ResultSet rs = cs.executeQuery();

            while (rs.next()) {
                Proveedor p = new Proveedor(
                        rs.getString("codProveedor"), rs.getString("ruc"),
                        rs.getString("razonSocial"),
                        rs.getString("telefono"),
                        rs.getString("correo")
                );
                lista.add(p);
            }

        } catch (Exception e) {
            System.out.println("Error al listar proveedores: " + e.getMessage());
        }
        return lista;
    }
	
	public Proveedor buscar(String codProveedor) {
	        Proveedor p = null;

	        try {
	            CallableStatement cs = ConexionBD.getConnexion().prepareCall("{call sp_buscarProveedor(?)}");

	            cs.setString(1, codProveedor);

	            ResultSet rs = cs.executeQuery();

	            if (rs.next()) {
	                p = new Proveedor(
	                        rs.getString("codProveedor"), rs.getString("ruc"), rs.getString("razonSocial"), rs.getString("telefono"), 	rs.getString("correo")
	                );
	            }
	        } catch (Exception e) {
	            System.out.println("Error al buscar proveedor: " + e.getMessage());
	        }
	        return p;
	}
	
	public void modificar(Proveedor p) {
        try {
            CallableStatement cs = ConexionBD.getConnexion().prepareCall("{call sp_modificarProveedor(?,?,?,?,?)}");

            cs.setString(1, p.getCodProveedor());
            cs.setString(2, p.getRuc());
            cs.setString(3, p.getRazonSocial());
            cs.setString(4, p.getTelefono());
            cs.setString(5, p.getCorreo());
            cs.executeUpdate();
            System.out.println("Proveedor modificado correctamente.");

        } catch (Exception e) {
            System.out.println("Error al modificar proveedor: " + e.getMessage());
        }
    }
	public void eliminar(String codProveedor) {
        try {
            CallableStatement cs = ConexionBD.getConnexion().prepareCall("{call sp_eliminarProveedor(?)}");
            cs.setString(1, codProveedor);	
            cs.executeUpdate();
            System.out.println("Proveedor eliminado correctamente.");

        } catch (Exception e) {
            System.out.println("Error al eliminar proveedor: " + e.getMessage());
        }
    }

	

}

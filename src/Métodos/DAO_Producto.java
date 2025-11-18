package Métodos;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import Conexion.ConexionBD;
import Modelo.Producto;

public class DAO_Producto {
public ArrayList<Producto>listarProductos(){
	ArrayList<Producto> lista = new ArrayList<Producto>();
	try {
		 CallableStatement csta = ConexionBD.getConnexion().prepareCall("{call sp_Listar()}");
         ResultSet rs = csta.executeQuery();
         Producto p;
         while (rs.next()) {
			p = new Producto(rs.getInt("codigo"), rs.getString("nombre"), rs.getDouble("precio"), rs.getInt("stock"), rs.getString("categoria"));
		    lista.add(p);
		}
		
	} catch (Exception e) {
        System.out.println("Error al listar productos: " + e);
	}
	return lista;
}

public Producto buscarProducto(int codigo) {
    Producto p = null;
    try {
        CallableStatement csta = ConexionBD.getConnexion().prepareCall("{call sp_buscarProducto(?)}");
        csta.setInt(1, codigo);
        ResultSet rs = csta.executeQuery();

        if (rs.next()) { 
			p = new Producto(rs.getInt("codigo"), rs.getString("nombre"), rs.getDouble("precio"), rs.getInt("stock"), rs.getString("categoria"));
        }

    } catch (Exception e) {
        System.out.println("Error al buscar producto: " + e);
    }
    return p;
}

public void insertarProducto(Producto p) {
    try {
        CallableStatement csta = ConexionBD.getConnexion().prepareCall("{call sp_insertarProducto(?,?,?,?,?)}");
        csta.setInt(1, p.getCodigo());
        csta.setString(2, p.getNombre());
        csta.setDouble(3, p.getPrecio());
        csta.setInt(4, p.getStock());
        csta.setString(5, p.getCategoria());
        csta.executeUpdate();

    } catch (Exception e) {
        System.out.println("Error al insertar producto: " + e);
    }
}

public void modificarProducto(Producto p) {
    try {
        CallableStatement csta = ConexionBD.getConnexion().prepareCall("{call sp_modificarProducto(?,?,?,?,?)}");
        csta.setInt(1, p.getCodigo());
        csta.setString(2, p.getNombre());
        csta.setDouble(3, p.getPrecio());
        csta.setInt(4, p.getStock());
        csta.setString(5, p.getCategoria());
        csta.executeUpdate();

    } catch (Exception e) {
        System.out.println("Error al modificar producto: " + e);
    }
}

public void eliminarProducto(int codigo) {
    try {
        CallableStatement csta = ConexionBD.getConnexion().prepareCall("{call sp_eliminarProducto(?)}");
        csta.setInt(1, codigo);
        csta.executeUpdate();

    } catch (Exception e) {
        System.out.println("Error al eliminar producto: " + e);
    }
}

}

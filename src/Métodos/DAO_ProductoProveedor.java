package Métodos;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import Conexion.ConexionBD;
import Modelo.Producto;
import Modelo.ProductoProveedor;
import Modelo.Proveedor;

public class DAO_ProductoProveedor {
	
	public void insertar(ProductoProveedor pp) {
        try {
            CallableStatement cs = ConexionBD.getConnexion()
                    .prepareCall("{call sp_insertarProductoProveedor(?,?,?)}");

            cs.setInt(1, pp.getProducto().getCodigo());
            cs.setString(2, pp.getProveedor().getCodProveedor());
            cs.setInt(3, pp.getCantidad());
            cs.executeUpdate();
            System.out.println("Relación producto-proveedor registrada correctamente.");

        } catch (Exception e) {
            System.out.println("Error al insertar relación producto-proveedor: " + e.getMessage());
        }
    }
	
	public ArrayList<ProductoProveedor> listarTodo() {
        ArrayList<ProductoProveedor> lista = new ArrayList<>();

        try {
            CallableStatement cs = ConexionBD.getConnexion()
                    .prepareCall("{call sp_listarProductoProveedor()}");

            ResultSet rs = cs.executeQuery();

            while (rs.next()) {

                Producto prod = new Producto(
                        rs.getInt("codigo"), rs.getString("nombre"), rs.getDouble("precio"), rs.getInt("stock"), rs.getString("categoria")
                );

               Proveedor prov = new Proveedor(
                        rs.getString("codProveedor"), rs.getString("ruc"), rs.getString("razonSocial"), rs.getString("telefono"), rs.getString("correo")
                );
                int cantidad = rs.getInt("cantidad");
                lista.add(new ProductoProveedor(prod, prov, cantidad));
            }

        } catch (Exception e) {
            System.out.println("Error al listar producto-proveedor: " + e.getMessage());
        }
        return lista;
    }
	 public ArrayList<ProductoProveedor> listarProveedoresDeProducto(int codigoProducto) {
	        ArrayList<ProductoProveedor> lista = new ArrayList<>();

	        try {
	            CallableStatement cs = ConexionBD.getConnexion()
	                    .prepareCall("{call sp_listarProveedoresDeProducto(?)}");

	            cs.setInt(1, codigoProducto);
	            ResultSet rs = cs.executeQuery();

	            while (rs.next()) {
	                Producto prod = new Producto(
	                        rs.getInt("codigo"), rs.getString("nombre"), rs.getDouble("precio"), rs.getInt("stock"), rs.getString("categoria")
	                );

	                Proveedor prov = new Proveedor(
	                        rs.getString("codProveedor"), rs.getString("ruc"), rs.getString("razonSocial"), rs.getString("telefono"), rs.getString("correo")

	                );

	                int cantidad = rs.getInt("cantidad");
	                lista.add(new ProductoProveedor(prod, prov, cantidad));
	            }

	        } catch (Exception e) {
	            System.out.println("Error al listar proveedores del producto: " + e.getMessage());
	        }	
	        return lista;
	    }

}

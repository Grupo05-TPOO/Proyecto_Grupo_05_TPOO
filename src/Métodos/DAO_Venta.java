package Métodos;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import Conexion.ConexionBD;
import Modelo.Producto;
import Modelo.Venta;

public class DAO_Venta {
    public void insertarVenta(Venta v) {
        try {
            CallableStatement csta = ConexionBD.getConnexion().prepareCall("{call sp_insertarVenta(?,?,?,?,?)}");
            csta.setInt(1, v.getProducto().getCodigo());
            csta.setInt(2, v.getCantidad());
            csta.setDouble(3, v.getProducto().getPrecio());
            csta.setDouble(4, v.getDescuento());
            csta.setDouble(5, v.calcularSubtotal());
            csta.executeUpdate();
            System.out.println("Venta insertada correctamente");

        } catch (Exception e) {
            System.out.println("Error al insertar venta: " + e);
        }
    }

 public void actualizarStock(int codigoProducto, int nuevoStock) {
    try {
          CallableStatement csta = ConexionBD.getConnexion().prepareCall("{call sp_actualizarStock(?,?)}");
            csta.setInt(1, codigoProducto);
            csta.setInt(2, nuevoStock);
            csta.executeUpdate();
            System.out.println("Stock actualizado correctamente");

        } catch (Exception e) {
            System.out.println("Error al actualizar stock: " + e);
        }
    }

}

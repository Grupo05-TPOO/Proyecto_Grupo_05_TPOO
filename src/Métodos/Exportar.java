package Métodos;

import java.io.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import Modelo.Producto;
import Modelo.Venta;

public class Exportar {

    public static void exportarProductos(ArrayList<Producto> listaProductos) {
        try {
            File carpeta = new File("exportaciones");
            if (!carpeta.exists()) {
                carpeta.mkdirs();
            }

            File archivo = new File("exportaciones/productos.csv");

            try (PrintWriter pw = new PrintWriter(new FileWriter(archivo))) {

                pw.println("Código\tNombre\tPrecio\tStock\tCategoría");

                for (Producto p : listaProductos) {
                    pw.println(p.getCodigo() + "\t" + p.getNombre() + "\t" + p.getPrecio() + "\t" + p.getStock() + "\t" + p.getCategoria());
                }

                JOptionPane.showMessageDialog(null, "Productos exportados en:\n" + archivo.getAbsolutePath());
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al exportar productos: " + e.getMessage());
        }
    }

    public static void exportarVentas(ArrayList<Venta> listaVentas) {
        try {
            File carpeta = new File("exportaciones");
            if (!carpeta.exists()) {
                carpeta.mkdirs();
            }

            File archivo = new File("exportaciones/ventas.csv");

            try (PrintWriter pw = new PrintWriter(new FileWriter(archivo))) {

                pw.println("Producto\tCantidad\tPrecio\tDescuento\tSubtotal");

                for (Venta v : listaVentas) {
                    Producto p = v.getProducto();
                    pw.println(p.getNombre() + "\t" + v.getCantidad() + "\t" + p.getPrecio() + "\t" + v.getDescuento() + "\t" + v.calcularSubtotal());
                }

                JOptionPane.showMessageDialog(null, "Ventas exportadas en:\n" + archivo.getAbsolutePath());
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al exportar ventas: " + e.getMessage());
        }
    }
}

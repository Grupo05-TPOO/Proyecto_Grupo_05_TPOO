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

            File archivoExcel = new File("exportaciones/productos.csv");

            try (PrintWriter pw = new PrintWriter(new FileWriter(archivoExcel))) {
               
                pw.println("Código\tNombre\tPrecio\tStock\tCategoría");

                
                for (Producto p : listaProductos) {
                    pw.println(
                        p.getCodigo() + "\t" +
                        p.getNombre() + "\t" +
                        p.getPrecio() + "\t" +
                        p.getStock() + "\t" +
                        p.getCategoria()
                    );
                }

                System.out.println("✅ Archivo de productos generado en: " + archivoExcel.getAbsolutePath());
                JOptionPane.showMessageDialog(null,
                        "✅ Productos exportados correctamente:\n" + archivoExcel.getAbsolutePath(),
                        "Exportación Exitosa", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (IOException e) {
            System.out.println("⚠️ Error al exportar productos: " + e.getMessage());
            JOptionPane.showMessageDialog(null,
                    "⚠️ Error al exportar productos: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

  
    public static void exportarVentas(ArrayList<Venta> listaVentas) {
        try {
            File carpeta = new File("exportaciones");
            if (!carpeta.exists()) {
                carpeta.mkdirs(); 
            }

            File archivoExcel = new File("exportaciones/ventas.csv");

            try (PrintWriter pw = new PrintWriter(new FileWriter(archivoExcel))) {
               
                pw.println("Producto\tCantidad\tPrecio\tDescuento\tSubtotal");

               
                for (Venta v : listaVentas) {
                    Producto p = v.getProducto();
                    pw.println(
                        p.getNombre() + "\t" +
                        v.getCantidad() + "\t" +
                        p.getPrecio() + "\t" +
                        v.getDescuento() + "\t" +
                        v.calcularSubtotal()
                    );
                }

                System.out.println("✅ Archivo de ventas generado en: " + archivoExcel.getAbsolutePath());
                JOptionPane.showMessageDialog(null,
                        "✅ Ventas exportadas correctamente:\n" + archivoExcel.getAbsolutePath(),
                        "Exportación Exitosa", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (IOException e) {
            System.out.println("⚠️ Error al exportar ventas: " + e.getMessage());
            JOptionPane.showMessageDialog(null,
                    "⚠️ Error al exportar ventas: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
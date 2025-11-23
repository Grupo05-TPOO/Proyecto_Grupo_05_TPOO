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
    
    public static void generarBoletaTXT(javax.swing.table.DefaultTableModel modeloTabla, double total) {

        try {
            File carpeta = new File("boletas");
            if (!carpeta.exists()) {
                carpeta.mkdirs();
            }

            int numero = carpeta.list().length + 1;
            String nombreArchivo = "boletas/Boleta_" + String.format("%05d", numero) + ".txt";

            PrintWriter pw = new PrintWriter(new FileWriter(nombreArchivo));

            java.time.LocalDateTime now = java.time.LocalDateTime.now();
            String fecha = now.toLocalDate().toString();
            String hora = now.toLocalTime().withNano(0).toString();

            pw.println("======================================================");
            pw.println("|                    BOLETA DE VENTA                 |");
            pw.println("======================================================");
            pw.println("| Fecha: " + fecha + "   Hora: " + hora + "               |");
            pw.println("| N° Boleta: BV-" + String.format("%05d", numero) + "                                  |");
            pw.println("------------------------------------------------------");
            pw.println("| Producto        | Cant. | Precio   | Subtotal     |");
            pw.println("------------------------------------------------------");

            for (int i = 0; i < modeloTabla.getRowCount(); i++) {

                String nom = modeloTabla.getValueAt(i, 1).toString();
                String cant = modeloTabla.getValueAt(i, 3).toString();
                String precio = modeloTabla.getValueAt(i, 2).toString();
                String sub = modeloTabla.getValueAt(i, 5).toString();

                pw.printf("| %-15s | %-5s | %-8s | %-12s |\n",
                        nom, cant, precio, sub);
            }

            pw.println("------------------------------------------------------");
            pw.println("| TOTAL A PAGAR: S/ " + total + "                                 |");
            pw.println("======================================================");
            pw.println("| GRACIAS POR SU COMPRA                              |");
            pw.println("| LOS SALUDA LA TIA FELICITA                         |");
            pw.println("======================================================");

            pw.close();

            JOptionPane.showMessageDialog(null, "Boleta generada:\n" + nombreArchivo);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al generar boleta: " + e.getMessage());
        }
    }
    
    public static void exportarReporteSemanalTXT(ArrayList<DAO_Venta.VentaSemanal> lista) {

        try {

            if (lista == null || lista.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No hay ventas esta semana.");
                return;
            }

            File carpeta = new File("reportes");
            if (!carpeta.exists()) {
                carpeta.mkdirs();
            }

            String nombreArchivo = "reportes/Reporte_Semanal.txt";
            PrintWriter pw = new PrintWriter(new FileWriter(nombreArchivo));

            pw.println("===============================================================");
            pw.println("|                  REPORTE SEMANAL DE VENTAS                  |");
            pw.println("===============================================================");

            String fecha = java.time.LocalDate.now().toString();
            String hora = java.time.LocalTime.now().withNano(0).toString();

            pw.println("Fecha: " + fecha + "   Hora: " + hora);
            pw.println("---------------------------------------------------------------");

            pw.println("Producto               Cant   Precio     Subtotal     Fecha");
            pw.println("---------------------------------------------------------------");

            double totalSemanal = 0;

            for (DAO_Venta.VentaSemanal v : lista) {

                String prod = v.producto;
                if (prod.length() > 20) prod = prod.substring(0, 20); 

                String cantidad = String.valueOf(v.cantidad);
                String precio = String.format("%.2f", v.precio);
                String subtotal = String.format("%.2f", v.subtotal);
                String fechaVenta = v.fecha.toLocalDateTime().toLocalDate().toString();

                totalSemanal += v.subtotal;

                pw.printf("%-22s %-6s %-10s %-12s %s\n",
                        prod, cantidad, precio, subtotal, fechaVenta);
            }

            pw.println("---------------------------------------------------------------");
            pw.println("TOTAL SEMANAL: S/ " + String.format("%.2f", totalSemanal));
            pw.println("===============================================================");
            pw.println("|                FIN DEL REPORTE SEMANAL                      |");
            pw.println("===============================================================");

            pw.close();

            JOptionPane.showMessageDialog(null, "Reporte semanal generado:\n" + nombreArchivo);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al generar reporte semanal: " + e.getMessage());
        }
    }



}

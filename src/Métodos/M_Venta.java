package Métodos;

import java.io.*;
import java.util.ArrayList;
import Modelo.Producto;
import Modelo.Venta;

public class M_Venta {
    private ArrayList<Venta> ListaV;

    public M_Venta() {
        ListaV = new ArrayList<>();
        cargarVentasDesdeArchivo();
    }

    public void guardarVentasEnArchivo() {
        if (ListaV == null || ListaV.isEmpty()) {
            System.out.println("⚠️ No hay ventas para guardar.");
            return;
        }

        try (PrintWriter pw = new PrintWriter(new FileWriter("ventas.txt"))) {
            for (Venta v : ListaV) {
                Producto p = v.getProducto();
                pw.println(p.getNombre() + "," +
                           v.getCantidad() + "," +
                           p.getPrecio() + "," +
                           v.getDescuento() + "," +
                           v.calcularSubtotal());
            }
            System.out.println("✅ Ventas guardadas correctamente en ventas.txt");
        } catch (IOException e) {
            System.out.println("⚠️ Error al guardar ventas: " + e.getMessage());
        }
    }

    public void cargarVentasDesdeArchivo() {
        ListaV.clear();
        File archivo = new File("ventas.txt");

        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
                System.out.println("📁 Archivo ventas.txt creado correctamente.");
            } catch (IOException e) {
                System.out.println("⚠️ Error al crear archivo: " + e.getMessage());
            }
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 5) {
                    String nombre = datos[0];
                    int cantidad = Integer.parseInt(datos[1]);
                    double precio = Double.parseDouble(datos[2]);
                    double descuento = Double.parseDouble(datos[3]);

                    Producto p = new Producto(0, nombre, precio, 0, "");
                    Venta v = new Venta(cantidad, p, descuento);
                    ListaV.add(v);
                }
            }
            System.out.println("✅ Ventas cargadas correctamente desde ventas.txt");
        } catch (IOException e) {
            System.out.println("⚠️ Error al cargar ventas: " + e.getMessage());
        }
    }

    public boolean eliminarVenta(String nombreProducto) {
        for (int i = 0; i < ListaV.size(); i++) {
            if (ListaV.get(i).getProducto().getNombre().equalsIgnoreCase(nombreProducto)) {
                ListaV.remove(i);
                guardarVentasEnArchivo();
                return true;
            }
        }
        return false;
    }

    public void agregarVenta(Venta v) {
        ListaV.add(v);
        guardarVentasEnArchivo();
    }

    public void limpiarVenta() {
        ListaV.clear();
    }

    public ArrayList<Venta> getVentas() {
        return ListaV;
    }

    public double calculadoraSubtotal() {
        double total = 0;
        for (Venta v : ListaV) {
            total += v.calcularSubtotal();
        }
        return total;
    }
}

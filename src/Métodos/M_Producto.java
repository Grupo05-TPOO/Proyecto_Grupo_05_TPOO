package Métodos;
import java.io.*;
import java.util.ArrayList;
import Modelo.Producto;

public class M_Producto {

    private static ArrayList<Producto> lista = new ArrayList<>();

    public M_Producto() {
        cargarProductosDesdeArchivo();
    }

    
    public void guardarProductosEnArchivo() {
        try (PrintWriter pw = new PrintWriter(new FileWriter("productos.txt"))) {
            for (Producto p : lista) {
                pw.println(p.getCodigo() + "," +
                           p.getNombre() + "," +
                           p.getPrecio() + "," +
                           p.getStock() + "," +
                           p.getCategoria());
            }
            System.out.println("✅ Productos guardados correctamente en productos.txt");
        } catch (IOException e) {
            System.out.println("⚠️ Error al guardar productos: " + e.getMessage());
        }
    }

    
    public void cargarProductosDesdeArchivo() {
        lista.clear();
        File archivo = new File("productos.txt");

        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
                System.out.println("📁 Archivo productos.txt creado correctamente.");
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
                    int codigo = Integer.parseInt(datos[0]);
                    String nombre = datos[1];
                    double precio = Double.parseDouble(datos[2]);
                    int stock = Integer.parseInt(datos[3]);
                    String categoria = datos[4];
                    lista.add(new Producto(codigo, nombre, precio, stock, categoria));
                }
            }
            System.out.println("✅ Productos cargados correctamente desde productos.txt");
        } catch (IOException e) {
            System.out.println("⚠️ Error al cargar productos: " + e.getMessage());
        }
    }

   
    public void agregarProducto(Producto p) {
        lista.add(p);
        guardarProductosEnArchivo();
    }


    public boolean eliminarProducto(int codigo) {
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getCodigo() == codigo) {
                lista.remove(i);
                guardarProductosEnArchivo();
                return true;
            }
        }
        return false;
    }

    
    public Producto buscarProducto(int cod) {
        for (Producto p : lista) {
            if (p.getCodigo() == cod) return p;
        }
        return null;
    }

    
    public ArrayList<Producto> getProductos() {
        return lista;
    }

    public int tamañoP() {
        return lista.size();
    }

    public Producto obtener(int x) {
        return lista.get(x);
    }
}

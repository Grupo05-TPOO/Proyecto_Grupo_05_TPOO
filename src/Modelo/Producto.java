package Modelo;

public class Producto {
	private int codigo;
    private String nombre;
    private double precio;
    private int stock;
    private String categoria;

    public Producto(int codigo, String nombre, double precio, int stock, String categoria) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.categoria = categoria;
    }

public String getNombre() {
	return nombre;
}
public void setNombre(String nombre) {
	this.nombre = nombre;
}
public String getCategoria() {
	return categoria;
}
public void setCategoria(String categoria) {
	this.categoria = categoria;
}
public double getPrecio() {
	return precio;
}
public void setPrecio(double precio) {
	this.precio = precio;
}
public int getCodigo() {
	return codigo;
}
public void setCodigo(int codigo) {
	this.codigo = codigo;
}
public int getStock() {
	return stock;
}
public void setStock(int stock) {
	this.stock = stock;
}
public String datosProducto() {
    return "Código: " + codigo +
           " | Nombre: " + nombre +
           " | Precio: S/ " + precio +
           " | Stock: " + stock +
           " | Categoría: " + categoria;
}



public String toString() {
    return codigo + " - " + nombre + " (" + categoria + ")";
}
}
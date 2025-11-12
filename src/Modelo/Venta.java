package Modelo;

public class Venta {
private int cantidad;
private Producto producto;
private double descuento;

public Venta(int cantidad, Producto producto, double descuento) {
	super();
	this.cantidad = cantidad;
	this.producto = producto;
	this.descuento = descuento;
}


public int getCantidad() {
	return cantidad;
}


public void setCantidad(int cantidad) {
	this.cantidad = cantidad;
}


public Producto getProducto() {
	return producto;
}


public void setProducto(Producto producto) {
	this.producto = producto;
}


public double getDescuento() {
	return descuento;
}


public void setDescuento(double descuento) {
	this.descuento = descuento;
}


public double calcularSubtotal() {
    double subtotal = producto.getPrecio() * cantidad;
    return subtotal - (subtotal * descuento / 100);
}
}

package Modelo;

public class ProductoProveedor {
	private Producto producto;
	private Proveedor proveedor;
	private int cantidad;
	public ProductoProveedor(Producto producto, Proveedor proveedor, int cantidad) {
		super();
		this.producto = producto;
		this.proveedor = proveedor;
		this.cantidad = cantidad;
	}
	public Producto getProducto() {
		return producto;
	}
	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	public Proveedor getProveedor() {
		return proveedor;
	}
	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	
}

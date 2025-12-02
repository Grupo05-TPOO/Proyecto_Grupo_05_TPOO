package Modelo;

public class Proveedor {
	private String codProveedor;
	private String ruc;
	private String razonSocial;
	private String telefono;
	private String correo;
	public Proveedor(String codProveedor, String ruc, String razonSocial, String telefono, String correo) {
		super();
		this.codProveedor = codProveedor;
		this.ruc = ruc;
		this.razonSocial = razonSocial;
		this.telefono = telefono;
		this.correo = correo;
	}
	public String getCodProveedor() {
		return codProveedor;
	}
	public void setCodProveedor(String codProveedor) {
		this.codProveedor = codProveedor;
	}
	public String getRuc() {
		return ruc;
	}
	public void setRuc(String ruc) {
		this.ruc = ruc;
	}
	public String getRazonSocial() {
		return razonSocial;
	}
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public String toString() {
		return codProveedor  + " - " + razonSocial;
	}

}

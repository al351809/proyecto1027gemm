package es.uji.ei1027.model;

public class Instructor {
	
	private String dni;
	private String alias;
	private String nombre;
	private String email;
	private String numeroCuenta;
	private String estado;
	
	
	
	public String getDni() {
		return dni;
	}
	
	public void setDni(String dni) {
		this.dni = dni;
	}
	
	
	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getNumeroCuenta() {
		return numeroCuenta;
	}
	
	public void setNumeroCuenta(String numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}
	
	
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	@Override
	public String toString() {
		return "Instructor [dni=" + dni + ", nombre=" + nombre + ", email=" + email
				+ ", numeroCuenta=" + numeroCuenta + ", estado=" + estado + "]";
	}
	
	

}

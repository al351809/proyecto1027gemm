package es.uji.ei1027.model;

public class ClienteTipo {
	
	private String dni;
	private String nombreActividad;
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public String getNombreActividad() {
		return nombreActividad;
	}
	public void setNombreActividad(String nombreActividad) {
		this.nombreActividad = nombreActividad;
	}
	@Override
	public String toString() {
		return "ClineteTipo [dni=" + dni + ", nombre=" + nombreActividad + "]";
	}
	
	
}

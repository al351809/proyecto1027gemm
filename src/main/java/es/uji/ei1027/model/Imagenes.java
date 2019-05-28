package es.uji.ei1027.model;

public class Imagenes {

	private String nombre;
	private String imagen;
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getImagen() {
		return imagen;
	}
	public void setImagen(String imagen) {
		this.imagen = imagen;
	}
	@Override
	public String toString() {
		return "Imagenes [nombre=" + nombre + ", imagen=" + imagen + "]";
	}
	
	
	
}

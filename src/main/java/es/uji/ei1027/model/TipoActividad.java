package es.uji.ei1027.model;

public class TipoActividad {
	
	private String nombre;
	private String nivel;
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getNivel() {
		return nivel;
	}
	public void setNivel(String nivel) {
		this.nivel = nivel;
	}
	@Override
	public String toString() {
		return "TipoActividad [nombre=" + nombre + ", nivel=" + nivel + "]";
	}
	
	
}

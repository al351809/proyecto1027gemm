package es.uji.ei1027.model;

import java.util.Date;

public class Actividad {

	private String nombre;
	private String descripcion;
	private String duracion;
	private String fecha;
	private String precio;
	private int minPersonas;
	private int maxPersonas;
	private String lugar;
	private String puntoEncuentro;
	private String imagenes;
	private String textoCliente;
	private String tipo;
	private String estado;
	
	
    public String getNombre() {
		return nombre;
	}
    
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDuracion() {
		return duracion;
	}

	public void setDuracion(String duracion) {
		this.duracion = duracion;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getPrecio() {
		return precio;
	}

	public void setPrecio(String precio) {
		this.precio = precio;
	}

	public int getMinPersonas() {
		return minPersonas;
	}

	public void setMinPersonas(int minPersonas) {
		this.minPersonas = minPersonas;
	}

	public int getMaxPersonas() {
		return maxPersonas;
	}

	public void setMaxPersonas(int maxPersonas) {
		this.maxPersonas = maxPersonas;
	}

	public String getLugar() {
		return lugar;
	}

	public void setLugar(String lugar) {
		this.lugar = lugar;
	}

	public String getPuntoEncuentro() {
		return puntoEncuentro;
	}

	public void setPuntoEncuentro(String puntoEncuentro) {
		this.puntoEncuentro = puntoEncuentro;
	}

	public String getImagenes() {
		return imagenes;
	}

	public void setImagenes(String imagenes) {
		this.imagenes = imagenes;
	}

	public String getTextoCliente() {
		return textoCliente;
	}

	public void setTextoCliente(String textoCliente) {
		this.textoCliente = textoCliente;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	@Override
	public String toString() {
		return "Actividad [nombre=" + nombre + ", descripcion=" + descripcion + ", duracion=" + duracion + ", fecha="
				+ fecha + ", precio=" + precio + ", minPersonas=" + minPersonas + ", maxPersonas=" + maxPersonas
				+ ", lugar=" + lugar + ", puntoEncuentro=" + puntoEncuentro + ", imagenes=" + imagenes
				+ ", textoCliente=" + textoCliente + ", tipo=" + tipo + ", estado=" + estado + "]";
	}

	
}

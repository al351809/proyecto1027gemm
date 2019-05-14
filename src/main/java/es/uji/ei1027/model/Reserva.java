package es.uji.ei1027.model;

public class Reserva {

	private int idReserva;
	private String estadoPago;
	private int numTransaccion;
	private int numAsistentes;
	private int precioPersona;
	private String fecha;
	private String dniCliente;
	private String nombreActividad;
	
	public int getIdReserva() {
		return idReserva;
	}
	public void setIdReserva(int idReserva) {
		this.idReserva = idReserva;
	}
	public String getEstadoPago() {
		return estadoPago;
	}
	public void setEstadoPago(String estadoPago) {
		this.estadoPago = estadoPago;
	}
	public int getNumTransaccion() {
		return numTransaccion;
	}
	public void setNumTransaccion(int numTransaccion) {
		this.numTransaccion = numTransaccion;
	}
	public int getNumAsistentes() {
		return numAsistentes;
	}
	public void setNumAsistentes(int numAsistentes) {
		this.numAsistentes = numAsistentes;
	}
	public int getPrecioPersona() {
		return precioPersona;
	}
	public void setPrecioPersona(int precioPersona) {
		this.precioPersona = precioPersona;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getDniCliente() {
		return dniCliente;
	}
	public void setDniCliente(String dniCliente) {
		this.dniCliente = dniCliente;
	}
	public String getNombreActividad() {
		return nombreActividad;
	}
	public void setNombreActividad(String nombreActividad) {
		this.nombreActividad = nombreActividad;
	}
	@Override
	public String toString() {
		return "Reserva [idReserva=" + idReserva + ", estadoPago=" + estadoPago + ", numTransaccion=" + numTransaccion
				+ ", numAsistentes=" + numAsistentes + ", precioPersona=" + precioPersona + ", fecha=" + fecha
				+ ", dniCliente=" + dniCliente + ", nombreActividad=" + nombreActividad + "]";
	}
	
	
	
	
}

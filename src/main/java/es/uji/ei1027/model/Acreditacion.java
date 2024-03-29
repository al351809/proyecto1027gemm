package es.uji.ei1027.model;

public class Acreditacion {
    private int idcertificado;
    private String certificado;
    private String dni;
    private String estado;
    private String tipo;
    
	public int getIdcertificado() {
		return idcertificado;
	}
	public void setIdcertificado(int idcertificado) {
		this.idcertificado = idcertificado;
	}
	public String getCertificado() {
		return certificado;
	}
	public void setCertificado(String certificado) {
		this.certificado = certificado;
	}
	
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String i) {
		this.estado = i;
	}
	
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	@Override
	public String toString() {
		return "Acreditacion [idcertificado=" + idcertificado + ", certificado=" + certificado + ", dni=" + dni
				+ ", estado=" + estado + ", tipo=" + tipo + "]";
	}

	
	
    
}

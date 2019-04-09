package es.uji.ei1027.model;

public class Acreditacion {
    private int idcertificado;
    private String certificado;
    private String estado;
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
	public String getEstado() {
		return estado;
	}
	public void setEstado(String i) {
		this.estado = i;
	}
	@Override
	public String toString() {
		return "Nadador [idcertificado=" + idcertificado + ", certificado=" + certificado + ", estado=" + estado + "]";
	}
    
}

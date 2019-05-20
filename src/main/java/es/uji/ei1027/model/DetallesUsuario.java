package es.uji.ei1027.model;

public class DetallesUsuario {
	
	String usuario;
	String contrasenya;
	String rol;
	
	public String getUsuario() {
		
		return usuario;
	}
	public void setUsuario(String usuario) {
	    this.usuario = usuario; 
	}

	public String getContraseña() {
	   return contrasenya; 
	}

	public void setContraseña(String contraseña) {
	   this.contrasenya = contraseña;
	}
	
	public String getRol() {
		return rol;
	}
	
	public void setRol(String rol) {
		this.rol = rol;
	}
	
	@Override
	public String toString() {
		return "DetallesUsuario [usuario=" + usuario + ", contrasenya=" + contrasenya + ", rol=" + rol + "]";
	}

	
}

package es.uji.ei1027.model;

public class DetallesUsuario {
	
	String usuario;
	String password;
	String rol;
	
	public String getUsuario() {
		
		return usuario;
	}
	public void setUsuario(String usuario) {
	    this.usuario = usuario; 
	}

	public String getPassword() {
	   return password; 
	}

	public void setPassword(String password) {
	   this.password = password;
	}
	
	public String getRol() {
		return rol;
	}
	
	public void setRol(String rol) {
		this.rol = rol;
	}
	
	@Override
	public String toString() {
		return "DetallesUsuario [usuario=" + usuario + ", password=" + password + ", rol=" + rol + "]";
	}

	
}

package DataBase;

public class Usuarios {
	private long id;
	private String usuario;
	private String senha;
	private String email;
	
	public long getId(){
		return id;
	}
	public void setId(long id){
		this.id = id;
	}
	
	public String getUsuario(){
		return usuario;
	}
	public void setUsuario(String usuario){
		this.usuario = usuario;
	}
	
	public String getSenha(){
		return senha;
	}
	public void setSenha(String senha){
		this.senha = senha;
	}
	
	public String getEmail(){
		return email;
	}
	public void setEmail(String email){
		this.email = email;
	}
	
}

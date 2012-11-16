package DataBase;

public class Eventos {
	private long id;
	private String estado;
	private String evento;
	
	public long getId(){
		return id;
	}
	public void setId(long id){
		this.id = id;
	}
	
	public String getEstado(){
		return estado;
	}
	public void setEstado(String estado){
		this.estado = estado;
	}
	
	public String getEvento(){
		return evento;
	}
	public void setEvento(String evento){
		this.evento = evento;
	}
}

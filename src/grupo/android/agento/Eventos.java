package grupo.android.agento;

public class Eventos {

	private long id;
	private String estado;
	private String evento;

	public Eventos() {
		super();
		this.id = -1;
		this.estado = "";
		this.evento = "";
	}

	public Eventos(long id, String estado, String evento) {
		super();
		this.id = id;
		this.estado = estado;
		this.evento = evento;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getEvento() {
		return evento;
	}

	public void setEvento(String evento) {
		this.evento = evento;
	}
}

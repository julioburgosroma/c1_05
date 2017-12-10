
public class Sucesor {
	private Accion accion;
	private Estado estado;
	private int coste;
	public Sucesor (Accion nueva_accion, Estado nuevo_estado, int nuevo_coste){
		set_accion(nueva_accion);
		set_estado(nuevo_estado);
		set_coste(nuevo_coste);
	}
	public Accion get_accion() {
		return accion;
	}
	public void set_accion(Accion accion) {
		this.accion = accion;
	}
	public Estado get_estado() {
		return estado;
	}
	public void set_estado(Estado estado) {
		this.estado = estado;
	}
	public int get_coste() {
		return coste;
	}
	public void set_coste(int coste) {
		this.coste = coste;
	}
	public String toString(){
		return "Acción: "+accion+"\nCoste: "+coste +"\nEstado:"+estado;
		
	}
}

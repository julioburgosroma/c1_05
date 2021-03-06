public class Nodo implements Comparable<Nodo>{
	private Estado estado;
	private int costo;
	private Accion accion;
	private Nodo padre;
	private int profundidad;
	private int valor=0;
	
	public Nodo(){}
	public Nodo(Estado nuevo_estado, int nuevo_costo, Accion nueva_accion, Nodo nuevo_padre, int nueva_profundidad, int nuevo_valor){
		estado = nuevo_estado;
		costo = nuevo_costo;
		accion = nueva_accion;
		padre = nuevo_padre;
		profundidad = nueva_profundidad;
		valor = nuevo_valor;
	}
	public Estado get_estado(){
		return estado;
	}
	public void set_estado(Estado nuevo_estado){
		estado = nuevo_estado;
	}
	public int get_costo(){
		return costo;
	}
	public void set_costo(int nuevo_costo){
		costo = nuevo_costo;
	}
	public Accion get_accion(){
		return accion;
	}
	public void set_accion(Accion nueva_accion){
		accion = nueva_accion;
	}
	public Nodo get_padre() {
		return padre;
	}
	public void set_padre(Nodo padre) {
		this.padre = padre;
	}
	
	public int get_profundidad() {
		return profundidad;
	}
	public void set_profundidad(int profundidad) {
		this.profundidad = profundidad;
	}
	
	@Override
	public int compareTo(Nodo o) {
        if (this.get_valor() < o.get_valor()) {
            return -1;
        } else if (this.get_valor() > o.get_valor()) {
            return 1;
        } else {
            return 0;
        }
	}
	@Override
	public String toString() {
		return "Nodo [estado=" + estado + ", costo=" + costo + ", accion=" + accion + "]";
	}
	public int get_valor() {
		return valor;
	}
	public void set_valor(int valor) {
		this.valor = valor;
	}
}
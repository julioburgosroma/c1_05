
public class Reparto {
	private int cantidad;
	private Posicion lugar;
	public Reparto(){}
	public Reparto (int c, Posicion l){
		cantidad = c;
		lugar = l;
	}
	public int get_cantidad(){
		return cantidad;
	}
	public void set_cantidad(int nueva_cantidad){
		cantidad = nueva_cantidad;
	}
	public Posicion get_lugar(){
		return lugar;
	}
	public void set_lugar(Posicion nuevo_lugar){
		lugar = nuevo_lugar;
	}
	public int get_lugar_x(){
		return lugar.get_x();
	}
	public void set_lugar_x(int nuevo_x){
		lugar.set_x(nuevo_x);
	}
	public int get_lugar_y(){
		return lugar.get_y();
	}
	public void set_lugar_y(int nuevo_y){
		lugar.set_x(nuevo_y);
	}
	@Override
	public String toString() {
		return "(" + cantidad + ", " + lugar+")";
	}
}

import java.util.List;

public class Accion {
	private Posicion movimiento;
	private List<Reparto> reparto;
	private int costo;
	public Accion(){}
	public Accion (Posicion m, List<Reparto> r){
		movimiento = m;
		reparto = r;
	}
	public Posicion get_movimiento(){
		return movimiento;
	}
	public void set_movimiento(Posicion nuevo_movimiento){
		movimiento = nuevo_movimiento;
	}
	public int get_movimiento_x(){
		return movimiento.get_x();
	}
	public void set_movimiento_x(int nuevo_x){
		movimiento.set_x(nuevo_x);
	}
	public int get_movimiento_y(){
		return movimiento.get_y();
	}
	public void set_movimiento_y(int nuevo_y){
		movimiento.set_y(nuevo_y);
	}
	public List<Reparto> get_reparto(){
		return reparto;
	}
	public void set_reparto(List<Reparto> nuevo_reparto){
		reparto = nuevo_reparto;
	}
	public int get_reparto_cantidad(int i){
		return reparto.get(i).get_cantidad();
	}
	public void set_reparto_cantidad(int i, int nueva_cantidad){
		reparto.get(i).set_cantidad(nueva_cantidad);
	}
	public Posicion get_reparto_posicion(int i){
		return reparto.get(i).get_lugar();
	}
	public void set_reparto_posicion(int i, Posicion nueva_posicion){
		reparto.get(i).set_lugar(nueva_posicion);
	}
	public int get_reparto_posicion_x(int i){
		return reparto.get(i).get_lugar_x();
	}
	public void set_reparto_posicion_x(int i, int nueva_x){
		reparto.get(i).get_lugar().set_x(nueva_x);
	}
	public int get_reparto_posicion_y(int i){
		return reparto.get(i).get_lugar_y();
	}
	public void set_reparto_posicion_y(int i, int nueva_y){
		reparto.get(i).get_lugar().set_y(nueva_y);
	}
	@Override
	public String toString() {
		return movimiento + ", " + reparto;
	}
	public int get_costo() {
		return costo;
	}
	public void set_costo(int nuevo_costo){
		costo = nuevo_costo;
	}
}

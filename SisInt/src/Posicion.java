
public class Posicion {
	private int x;
	private int y;
	public Posicion(){}
	public Posicion (int x, int y){
		this.x = x;
		this.y = y;
	}
	public int get_x(){
		return x;
	}
	public void set_x(int nuevo_x){
		x = nuevo_x;
	}
	public int get_y(){
		return y;
	}
	public void set_y(int nuevo_y){
		y = nuevo_y;
	}
	@Override
	public String toString() {
		return "(" + x + ", " + y+")";
	}
}

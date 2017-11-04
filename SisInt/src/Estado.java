
public class Estado {
	private int [][] terreno;
	private Posicion tractor;
	public Estado(int [][] nuevo_terreno, Posicion nuevo_tractor){
		terreno = nuevo_terreno;
		tractor = nuevo_tractor;
	}
	public int [][] get_terreno(){
		return terreno;
	}
	public void set_terreno(int [][] nuevo_terreno){
		terreno = nuevo_terreno;
	}
	public Posicion get_tractor(){
		return tractor;
	}
	public void set_tractor (Posicion nuevo_tractor){
		tractor = nuevo_tractor;
	}
	public int get_tractor_x(){
		return tractor.get_x();
	}
	public void set_tractor_x(int nuevo_x){
		tractor.set_x(nuevo_x);
	}
	public int get_tractor_y(){
		return tractor.get_y();
	}
	public void set_tractor_y(int nuevo_y){
		tractor.set_y(nuevo_y);
	}
	public int costo(Accion accion){
		int costo = 1;
		for (int i = 0; i < accion.get_reparto().size(); i++){
			costo += accion.get_reparto_cantidad(i);
		}
		return costo;
	}
	public void representacion(){
		System.out.println("Estado:");
		System.out.println("Posición: ("+get_tractor_x()+", "+get_tractor_y()+")");
		for (int i = 0; i < terreno[0].length; i++){
			for (int j = 0; j < terreno.length; j++)
				System.out.print(terreno[j][i]+" ");
			System.out.println();
		}
	}
}

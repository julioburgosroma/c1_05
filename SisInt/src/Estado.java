
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class Estado {
	private int [][] terreno;
	private Posicion tractor;
	private int valoracion;
	private BigInteger md5;
	public Estado(int [][] nuevo_terreno, Posicion nuevo_tractor) throws NoSuchAlgorithmException{
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
	public int heuristica(){
		int heuristica = 0;
		for (int i = 0; i < this.get_terreno().length; i++)
			for(int j = 0; j < this.get_terreno()[0].length; j++)
				if(this.get_terreno()[i][j] != SisInt.K)
					heuristica++;
		return heuristica;
	}
	public int costo(Accion accion){
		int costo = 1;
		for (int i = 0; i < accion.get_reparto().size(); i++){
			costo += accion.get_reparto_cantidad(i);
		}
		return costo;
	}
	public BigInteger get_md5() throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.reset();
		md.update(this.toString().getBytes());
		this.md5 = new BigInteger(1, md.digest());
		return md5;
	}
	public int get_valoracion() {
		return valoracion;
	}
	public void set_valoracion(int valoracion) {
		this.valoracion = valoracion;
	}
	public static void imprimir_terreno(int [][] terreno){
		System.out.println("Terreno: ");
		for (int i = 0; i < terreno[0].length; i++){
			for (int j = 0; j < terreno.length; j++)
				System.out.print(terreno[j][i]+" ");
			System.out.println();
		}
	}
	public String toString(){
		String terreno = "";
		for (int i = 0; i < this.get_terreno()[0].length; i++){
			for (int j = 0; j < this.get_terreno().length; j++)
				terreno += this.get_terreno()[j][i]+" ";
			terreno += "\n";
		}
		return "Posicion Inicial:"+"\n"+this.get_tractor().toString()+"\n"+ "Terreno:"+"\n"+terreno;
	}
}

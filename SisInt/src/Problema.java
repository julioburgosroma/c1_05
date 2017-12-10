import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Problema {
	private Estado estado_inicial;
	
	public Problema(Estado estado){
		estado_inicial = estado;
	}
	
	public Estado get_estado_inicial(){
		return estado_inicial;
	}
	
	public boolean test_objetivo(Estado estado){
    	boolean objetivo = true;
    	for(int i = 0; i < estado.get_terreno().length && objetivo; i++){
    		for(int j = 0; j < estado.get_terreno()[0].length && objetivo; j++){
    			if(estado.get_terreno()[i][j] != SisInt.K)
    				objetivo = false;
    		}
    	}
    	return objetivo;
    }
	
	public static int mejor (List<Accion> acciones_totales, List <Reparto> copia2){
		int total_lista=0;
		int parcial_lista=0;
		int total_copia2=0;
		int mejor=0;
		for(int j=0;j<copia2.size();j++){
			total_copia2=total_copia2+copia2.get(j).get_cantidad();
		}
		
		for(int i = 0; i < acciones_totales.size(); i++){
			for(int j=0;j<acciones_totales.get(i).get_reparto().size();j++){
				parcial_lista=parcial_lista+acciones_totales.get(i).get_reparto().get(j).get_cantidad();
			}
			if(parcial_lista>=total_lista){
				total_lista=parcial_lista;
			}
			parcial_lista=0;
		}
		
		
		if(total_copia2>total_lista){
			mejor=1;
		}
		if(total_copia2==total_lista){
			mejor=2;
		}
		return mejor;
	}
	
	public int calcular_sobrante(Estado estado_inicial){
		int sobrante=estado_inicial.get_terreno()[estado_inicial.get_tractor_x()][estado_inicial.get_tractor_y()]-SisInt.K;
		if(sobrante<0){
			sobrante=0;
		}
		return sobrante;
	}
	
	public ArrayList<Posicion> calcular_vecinos (int [][] terreno, int pos_x, int pos_y){
		ArrayList<Posicion> vecinos = new ArrayList<Posicion>();
		for(int i = 0; i < 4; i++)
			switch(i){
				case 0: 
					if(pos_x+1 < terreno.length)
						vecinos.add(new Posicion(pos_x+1, pos_y));
					break;	
				case 1: 
					if(pos_y+1 < terreno[0].length)
						vecinos.add(new Posicion(pos_x, pos_y+1));
					
					break;
				case 2: 
					if(pos_x-1 >= 0)
						vecinos.add(new Posicion(pos_x-1, pos_y));
					break;
				case 3:
					if(pos_y-1 >= 0)
						vecinos.add(new Posicion(pos_x, pos_y-1));
					break;
			}
		return vecinos;
	}
	
	public List<Accion> crear_acciones(Estado estado){
		int sobrante = calcular_sobrante(estado);
		List<Accion> acciones = new ArrayList<Accion>();
		ArrayList<Posicion> vecinos = calcular_vecinos(estado.get_terreno(), estado.get_tractor_x(), estado.get_tractor_y());
		crear_acciones_recursivo (sobrante, acciones, 0,  new Reparto[vecinos.size()],  vecinos, estado);
		return acciones;
	}
	
	private static void crear_acciones_recursivo(int sobrante, List<Accion> acciones_totales, int indice, Reparto[]aux, List<Posicion> vecinos, Estado estado){
		Reparto[] copia = new Reparto[aux.length];
		if(indice == vecinos.size() ){
			System.arraycopy(aux,0,copia,0,aux.length);
			List<Reparto> copia2=new ArrayList<Reparto>();
			copia2=Arrays.asList(copia);
			
		     
			int op = mejor (acciones_totales, copia2);
			if (op == 1){
				acciones_totales.clear();
				for(int i = 0; i < vecinos.size(); i++){
				
					Accion accion = new Accion(vecinos.get(i), copia2);
					accion.set_costo(estado.costo(accion));
					acciones_totales.add(accion);
				}
			}
			if (op == 2){
				for(int i = 0; i < vecinos.size(); i++){
					Accion accion = new Accion(vecinos.get(i), copia2);
					accion.set_costo(estado.costo(accion));
					acciones_totales.add(accion);
				}
			}
		}
		else{
			for(int j = 0;(sobrante-j) >= 0; j++){
				if ((estado.get_terreno()[vecinos.get(indice).get_x()][vecinos.get(indice).get_y()] + j) <= SisInt.MAX){
					Reparto reparto=new Reparto(j,vecinos.get(indice));
					aux[indice]=reparto;
					crear_acciones_recursivo(sobrante-j,acciones_totales,indice+1,aux,vecinos, estado);
				}
			}
		}
	}
	
	public ArrayList<Sucesor> sucesores (Estado estado_padre) throws NoSuchAlgorithmException{
		int aux1 = 0, aux2 = 0,cant=0,resta=0,suma=0;
		ArrayList<Sucesor> sucesores = new ArrayList<Sucesor>();
		List<Accion> acciones = new ArrayList<Accion>();
		acciones = crear_acciones(estado_padre);
		for (int i = 0; i < acciones.size(); i++){
			int [][] copia = new int[estado_padre.get_terreno().length][estado_padre.get_terreno()[0].length];
			for(int a=0;a<copia.length;a++){
				for(int b=0;b<copia.length;b++){
					copia[a][b] = estado_padre.get_terreno()[a][b];
				}
			}
			Estado estado = new Estado(copia, estado_padre.get_tractor());
			estado.set_tractor(acciones.get(i).get_movimiento());
			for (int j = 0; j < acciones.get(i).get_reparto().size(); j++){
				cant =acciones.get(i).get_reparto_cantidad(j);
				aux1=estado.get_terreno()[estado_padre.get_tractor_x()][estado_padre.get_tractor_y()];
				aux2 = estado.get_terreno()[acciones.get(i).get_reparto_posicion_x(j)][acciones.get(i).get_reparto_posicion_y(j)];
				suma=aux2+cant;
				resta=aux1-cant;
				estado.get_terreno()[acciones.get(i).get_reparto_posicion_x(j)][acciones.get(i).get_reparto_posicion_y(j)] = suma;
				estado.get_terreno()[estado_padre.get_tractor_x()][estado_padre.get_tractor_y()] = resta;
			}
			Sucesor sucesor = new Sucesor(acciones.get(i), estado, estado.costo(acciones.get(i)));
			sucesores.add(sucesor);
			cant=0;
		}
		return sucesores;
	}
}

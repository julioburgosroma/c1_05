import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.security.NoSuchAlgorithmException;

public class SisInt {
	static Scanner entradaEscaner;
	static int INT_MAX = 99;
	static int PROF_MAX = 30;
	static int INC_PROF = 10;
	static int COTA = 20;
	static int C = 2;
	static int F = C;
	static int K = 5;
	static int MAX = 8;
	static int POS_X = 1;
	static int POS_Y = 1;
	
	
	public static void main(String args[]) throws IOException, NoSuchAlgorithmException{
		introducir_datos();
		int [][] terreno = new int[C][F];
		
		
		
		
		
		generar_terreno(terreno);
		escribir_fichero(terreno);
		Problema problema = new Problema (new Estado(terreno, new Posicion(POS_X, POS_Y)));
		
		System.out.println(problema.get_estado_inicial().toString());
		
		
		ArrayList<Nodo> solucion = new ArrayList<Nodo>();
		solucion = busqueda_estrategia (problema);
		escribir_ficherosol(solucion);
		
		
	
		
		
		if(!solucion.isEmpty()){
			System.out.println("Solucion encontrada (tamaño "+solucion.size()+"):");
			
		}
		else
			System.out.println("No hay solucion.");
		
		
		
		
		
		
	}
	
	public static void introducir_datos(){
		entradaEscaner = new Scanner (System.in);
		System.out.println("Introduce la dimension x del terreno: ");
		C = entradaEscaner.nextInt();
		System.out.println("Introduce la dimension y del terreno: ");
		F = entradaEscaner.nextInt();
		System.out.println("Introduce la cantidad de arena deseada en cada casilla: ");
		K = entradaEscaner.nextInt();
		System.out.println("Introduce la cantidad de arena mÃ¡xima en cada casilla (Maximo "+K+"): ");
		MAX = entradaEscaner.nextInt();
		System.out.println("Introduce la posicion inicial en el eje x (entre 0 y "+(C-1)+"): ");
		POS_X = entradaEscaner.nextInt();
		System.out.println("Introduce la posicion inicial en el eje x (entre 0 y "+(F-1)+"): ");
		POS_Y = entradaEscaner.nextInt();
	}
	
	public static void escribir_fichero(int [][] terreno) throws IOException{
		BufferedWriter bw = new BufferedWriter(new FileWriter ("terreno.txt"));
		bw.write(POS_X+" ");
		bw.write(POS_Y+" ");
		bw.write(K+" ");
		bw.write(MAX+" ");
		bw.write(C+" ");
		bw.write(F+"");
		bw.newLine();
		for (int i = 0; i < terreno[0].length; i++){
			bw.write(" ");
			for(int j = 0; j < terreno.length; j++){
				bw.write(terreno[j][i]+ " ");
			}
			bw.newLine();
		}
		bw.close();
	}
	
	public static void generar_terreno(int terreno[][]){
		int aux[][] = new int[C][F];
		int total=0;
		int v=K*C*F;
		for (int i = 0; i < terreno.length; i++){
			for(int j = 0; j < terreno[0].length; j++){
				terreno[i][j] = K;
				int alea = (int) (Math.random() * MAX) + 1;
				if(total<=v){
					aux[i][j]=alea;
					total=total+alea;
				}
			}
		}
		
		if(total==v){
			for (int i = 0; i < terreno.length; i++){
				for(int j = 0; j < terreno[0].length; j++){
					terreno[i][j]=aux[i][j];
				}
			}
		}
		else{
			generar_terreno(terreno);
		}
		
	}
	
	public static ArrayList<Nodo> lista_nodos (ArrayList<Estado> visitados, ArrayList<Sucesor> sucesores, Nodo nodo_padre, int prof_max, int estrategia) throws NoSuchAlgorithmException{
		ArrayList<Nodo> nodos_sucesores = new ArrayList<Nodo>();
		Estado aux;
		boolean control;
		int costo;
		
		for (int i = 0; i < visitados.size(); i++){
			control = false;
			for (int j = 0; j < sucesores.size() && !control; j++){
				if(visitados.get(i).get_md5().compareTo(sucesores.get(j).get_estado().get_md5()) == 0){
					control = true;
					if (visitados.get(i).get_valoracion() < sucesores.get(j).get_estado().get_valoracion())
						nodos_sucesores.remove(j);
				}
			}
		}
		for (int i = 0; i < sucesores.size(); i++){
			aux = sucesores.get(i).get_estado();
			costo = sucesores.get(i).get_coste() + nodo_padre.get_estado().get_valoracion();
			switch(estrategia){
				case 0: //Anchura
					aux.set_valoracion(costo);
					nodos_sucesores.add(new Nodo(aux, sucesores.get(i).get_coste(), sucesores.get(i).get_accion(), nodo_padre, nodo_padre.get_profundidad()+1, nodo_padre.get_profundidad()+1));
					break;
				case 1: //Profundidad
					if(nodo_padre.get_profundidad()+1 <= prof_max){
						aux.set_valoracion(costo);
						nodos_sucesores.add(new Nodo(aux, sucesores.get(i).get_coste(), sucesores.get(i).get_accion(), nodo_padre, nodo_padre.get_profundidad()+1, INT_MAX - nodo_padre.get_profundidad()+1));
					}
					break;
				case 2: //Costo uniforme
					aux.set_valoracion(costo);
					nodos_sucesores.add(new Nodo(aux, sucesores.get(i).get_coste(), sucesores.get(i).get_accion(), nodo_padre, nodo_padre.get_profundidad()+1, costo));
					break;
				case 3: //A*
					costo += sucesores.get(i).get_estado().heuristica();
					aux.set_valoracion(nodo_padre.get_estado().heuristica()+sucesores.get(i).get_coste());
					nodos_sucesores.add(new Nodo(aux, sucesores.get(i).get_coste(), sucesores.get(i).get_accion(), nodo_padre, nodo_padre.get_profundidad()+1, costo));
					break;
			}
		}
		return nodos_sucesores;
	}
	
	public static ArrayList<Nodo> crea_solucion (Nodo objetivo){
		ArrayList<Nodo> solucion = new ArrayList<Nodo>();
		solucion.add(objetivo);
		Nodo aux = new Nodo (objetivo.get_estado(), objetivo.get_costo(), objetivo.get_accion(), objetivo.get_padre(), objetivo.get_profundidad(), objetivo.get_valor());
		while (aux.get_padre() != null){
			solucion.add(aux.get_padre());
			aux = aux.get_padre();
		}
		return solucion;
	}
	public static void escribir_ficherosol(ArrayList<Nodo> solucion) throws IOException{
		BufferedWriter bw = new BufferedWriter(new FileWriter ("solucion.txt"));
		int costototal=0;
		for (int i = solucion.size()-1; i >= 0; i--){
			costototal=costototal+solucion.get(i).get_costo();
			bw.write(" ");
			bw.write("Movimiento ");
			if(solucion.get(i).get_accion().get_movimiento()!=null){
				bw.write(solucion.get(i).get_accion().toString());
			}
			else{
				bw.write("("+solucion.get(i).get_estado().get_tractor().get_x());
				bw.write(","+solucion.get(i).get_estado().get_tractor().get_y()+")");
			}
			bw.newLine();
			for (int k = 0; k < solucion.get(i).get_estado().get_terreno()[0].length; k++){
				bw.write(" ");
				for(int j = 0; j < solucion.get(i).get_estado().get_terreno().length; j++){
					bw.write(solucion.get(i).get_estado().get_terreno()[j][k]+ " ");
				}
				bw.newLine();
			}
			bw.newLine();
		}
		bw.newLine();
		bw.write("coste total= "+costototal);
		
		bw.close();
	}
	
	public static ArrayList<Nodo> busqueda_acotada (Problema problema, int estrategia, int prof_max) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		boolean solucion = false, control, control2;
		ArrayList<Nodo> camino_solucion = new ArrayList<Nodo>();
		Frontera frontera = new Frontera(new PriorityQueue<Nodo>());
		ArrayList<Estado> visitados = new ArrayList<Estado>();
		ArrayList<Sucesor> sucesores = new ArrayList<Sucesor>();
		List<Nodo> nodos_sucesores = new ArrayList<Nodo>();
		
		Nodo actual = new Nodo();
		Nodo inicial = new Nodo(problema.get_estado_inicial(), 0, new Accion(), null, 0, 0);
		frontera.insertar(inicial);
		while(!solucion && !frontera.es_vacia()){
			control = false;
			control2 = false;
			actual = frontera.elimina();
			


			if (problema.test_objetivo(actual.get_estado())){
				solucion = true;
			}
			else{
				for(int i = 0; i < visitados.size() && !control; i++){
					if(actual.get_estado().get_md5().compareTo(visitados.get(i).get_md5()) == 0){
						control = true;
						if (actual.get_estado().get_valoracion() < visitados.get(i).get_valoracion()){
							
							visitados.set(i, actual.get_estado());
						}
						else
							control2 = true;
					}
				}
				if(!control){
					visitados.add(actual.get_estado());
					
				}
				if(!control2){
					sucesores = problema.sucesores(actual.get_estado());
					nodos_sucesores = lista_nodos (visitados, sucesores, actual, prof_max, estrategia);
					
					frontera.insertar_conjunto(nodos_sucesores);
				}
			}
		}
		if (solucion){
			camino_solucion = crea_solucion(actual);
			
		}
		return camino_solucion;
	}
	
	public static ArrayList<Nodo> busqueda(Problema problema, int estrategia, int prof_max, int inc_prof) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		int prof_actual = inc_prof;
		ArrayList<Nodo> solucion = new ArrayList<Nodo>();
		while(solucion.isEmpty() && prof_actual <= prof_max){
			solucion = busqueda_acotada(problema, estrategia, prof_actual);
			prof_actual += inc_prof;
		}
		return solucion;
	}
	public static ArrayList<Nodo> busqueda_estrategia(Problema problema) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		entradaEscaner = new Scanner (System.in);
		ArrayList<Nodo> solucion = new ArrayList<Nodo>();
		System.out.println("Introduce la estrategia elegida: ");
		System.out.println("0: Anchura.\n1: Profundidad.\n2: Profundidad acotada.\n3: Profundidad iterativa.\n4: Costo uniforme.\n5: A*.\n");
		int estrategia = entradaEscaner.nextInt();
	      
	    switch (estrategia) {
	    	case 0:
	    		solucion = busqueda (problema, 0, INT_MAX, INT_MAX); //Anchura
	    		break;
	    	case 1:
	    		solucion = busqueda (problema, 1, PROF_MAX, PROF_MAX); //Profundidad
	    		break;
	    	case 2:
	    		solucion = busqueda (problema, 1, COTA, COTA); //Profundidad acotada
	    		break;
	    	case 3:
	    		solucion = busqueda (problema, 1, PROF_MAX, INC_PROF); //Profundidad iterativa
	    		break;
	    	case 4:
	    		solucion = busqueda (problema, 2, 9, 9); //Costo uniforme
	    		break;
	    	case 5:
	    		solucion = busqueda (problema, 3, 9, 9); // A*
	    		break;
	    }
	    return solucion;
	}
}
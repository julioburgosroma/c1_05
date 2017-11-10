import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import java.util.Scanner;

public class SisInt {
	static Scanner entradaEscaner;
	static int c = 3;
	static int f = 3;
	static int k = 5;
	static int max = 8;
	static int pos_x = 1;
	static int pos_y = 1;
	
	public static void main(String args[]) throws IOException{
		//introducir_datos();
		int [][] terreno = new int[c][f];
		generar_terreno(terreno);
		imprimir_terreno(terreno);
		escribir_fichero(terreno);
		Problema problema = new Problema (new Estado(terreno, new Posicion(pos_x, pos_y)));
		ArrayList<Nodo> solucion = new ArrayList<Nodo>();
		solucion = Reolver_busqueda (problema, 0, 9, 4);
		if(!solucion.isEmpty()){
			System.out.println("Solución encontrada (tamaño "+solucion.size()+"):");
			for(int i = solucion.size()-1; i >= 0; i--)
				solucion.get(i).get_estado().representacion();
		}
		else
			System.out.println("No hay solución.");
		
		escribir_ficherosol(solucion);
	}
	
	public static void introducir_datos(){
		entradaEscaner = new Scanner (System.in);
		System.out.println("Introduce la dimension x del terreno: ");
		c = entradaEscaner.nextInt();
		System.out.println("Introduce la dimension y del terreno: ");
		f = entradaEscaner.nextInt();
		System.out.println("Introduce la cantidad de arena deseada en cada casilla: ");
		k = entradaEscaner.nextInt();
		System.out.println("Introduce la cantidad de arena máxima en cada casilla (mínimo "+k+"): ");
		max = entradaEscaner.nextInt();
		System.out.println("Introduce la posicion inicial en el eje x (entre 0 y "+(c-1)+"): ");
		pos_x = entradaEscaner.nextInt();
		System.out.println("Introduce la posicion inicial en el eje x (entre 0 y "+(f-1)+"): ");
		pos_y = entradaEscaner.nextInt();
	}
	
	public static void escribir_fichero(int [][] terreno) throws IOException{
		BufferedWriter bw = new BufferedWriter(new FileWriter ("terreno.txt"));
		bw.write(pos_x+" ");
		bw.write(pos_y+" ");
		bw.write(k+" ");
		bw.write(max+" ");
		bw.write(c+" ");
		bw.write(f+"");
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
	
	public static void escribir_ficherosol(ArrayList<Nodo> solucion) throws IOException{
		BufferedWriter bw = new BufferedWriter(new FileWriter ("solucion.txt"));
		
		for (int i = solucion.size()-1; i >= 0; i--){
			bw.write(" ");
			bw.write("Movimiento ");
			bw.write(solucion.get(i).get_accion().toString());
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
		bw.close();
	}
	
	
	
	public static void imprimir_terreno(int [][] terreno){
		System.out.println("Terreno: ");
		for (int i = 0; i < terreno[0].length; i++){
			for (int j = 0; j < terreno.length; j++)
				System.out.print(terreno[j][i]+" ");
			System.out.println();
		}
	}
	
	public static void imprimir_acciones(List<Accion> acciones_posibles){
		for(int i = 0; i < acciones_posibles.size(); i++)
			System.out.println("Accion "+i+": "+acciones_posibles.get(i).toString());
	}
	
	public static void generar_terreno(int terreno[][]){
		int aux[][] = new int[c][f];
		int total=0;
		int v=k*c*f;
		for (int i = 0; i < terreno.length; i++){
			for(int j = 0; j < terreno[0].length; j++){
				terreno[i][j] = k;
				int alea = (int) (Math.random() * max) + 1;
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
	
	public static ArrayList<Nodo> lista_nodos (ArrayList<Sucesor> sucesores, Nodo nodo_actual, int prof_max, int estrategia){
		ArrayList<Nodo> nodos_sucesores = new ArrayList<Nodo>();
		
		
			for (int i = 0; i < sucesores.size(); i++){
				if(nodo_actual.get_profundidad()+1 <= prof_max){
					nodos_sucesores.add(new Nodo(sucesores.get(i).get_estado(), sucesores.get(i).get_coste(), sucesores.get(i).get_accion(), nodo_actual, nodo_actual.get_profundidad()+1));
				}
			}
		
		
		
		return nodos_sucesores;
	}
	
	public static ArrayList<Nodo> crea_solucion (Nodo objetivo){
		ArrayList<Nodo> solucion = new ArrayList<Nodo>();
		solucion.add(objetivo);
		Nodo aux = new Nodo (objetivo.get_estado(), objetivo.get_costo(), objetivo.get_accion(), objetivo.get_padre(), objetivo.get_profundidad());
		while (aux.get_padre() != null){
			solucion.add(aux.get_padre());
			aux = aux.get_padre();
		}
		return solucion;
	}
	
	public static ArrayList<Nodo> busqueda_acotada (Problema problema, int estrategia, int prof_max){
		boolean solucion = false;
		ArrayList<Nodo> camino_solucion = new ArrayList<Nodo>();
		Frontera frontera = new Frontera(new LinkedList<Nodo>());
		
		ArrayList<Sucesor> sucesores = new ArrayList<Sucesor>();
		List<Nodo> nodos_sucesores = new ArrayList<Nodo>();
		
		Nodo actual = new Nodo();
		Nodo inicial = new Nodo(problema.get_estado_inicial(), 0, new Accion(), null, 0);
		frontera.insertar(inicial);
		while(!solucion && !frontera.es_vacia()){
			actual = frontera.elimina();
			if (problema.test_objetivo(actual.get_estado())){
				solucion = true;
				problema.test_objetivo(actual.get_estado());
			}
			else{
				if(actual.get_profundidad()+1 <= prof_max){
					sucesores = problema.sucesores(actual.get_estado());
					nodos_sucesores = lista_nodos (sucesores, actual, prof_max, estrategia);
					
					frontera.insertar_conjunto(nodos_sucesores);
					
					if(estrategia!=0){         // si la estrategia es !0 "profundidid" coloca los sucerores al principio de la fontera
						int cont=frontera.getFrontera().size();
						for(int i=0;i<cont;i++){
			        	frontera.insertar(frontera.elimina());
						}
					}
					
				}
			}
		}
		if (solucion)
			camino_solucion = crea_solucion(actual);
		return camino_solucion;
	}
	
	public static ArrayList<Nodo> busqueda(Problema problema, int estrategia, int prof_max, int inc_prof){
		int prof_actual = inc_prof;
		ArrayList<Nodo> solucion = new ArrayList<Nodo>();
		while(solucion.isEmpty() && prof_actual <= prof_max){
			solucion = busqueda_acotada(problema, estrategia, prof_actual);
			prof_actual += inc_prof;
		}
		return solucion;
	}
	
	public static ArrayList<Nodo> Reolver_busqueda(Problema problema, int estrategia, int prof_max, int inc_prof){
		ArrayList<Nodo> solucion = new ArrayList<Nodo>();
		 int op = estrategia;
	      
	      switch ( op ) {
	      case 0:
	    	  solucion = busqueda (problema, 0, 9, 4); //anchura
	           break;
	      case 1:
	    	  solucion = busqueda (problema, 1, 9, 9); // profundidad
	           break;
	      case 2:
	    	  solucion = busqueda (problema, 0, 9, 4);//profundidad acotada
	           break;
	      case 3:
	    	  solucion = busqueda (problema, 0, 90, 4);//profundidad iterativa
	           break;
	     
	      default:
	           System.out.println("error" );
	           break;
	      }
		
		
		
		
		return solucion;
	}
	
	
	
	
}

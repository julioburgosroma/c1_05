public class Arbol {
 
    /* Atributos */
    private Nodo raiz;
    private List<Nodo> frontera;
 
    /* Contructories */    
    public Arbol( Estado nuevo_estado, int nuevo_costo, Accion nueva_accion ) {
        this.raiz = new Nodo(nuevo_estado, nuevo_costo, nueva_accion);
        frontera = crear_frontera();
        // añadir a frontera sucesores de la raíz
    }
 
    public Arbol( Nodo raiz ) {
        this.raiz = raiz;
    }
 
    /* Setters y Getters */
    public Nodo getRaiz() {
        return raiz;
    }
 
    public void setRaiz(Nodo raiz) {
        this.raiz = raiz;
    }
    
    public List<Nodo> crear_frontera(){
    	List<Nodo> frontera = new ArrayList<Nodo>;
    	
    	
    	return frontera;
    }
    
public static ArrayList<Estado> Sucesores (Estado estado_inicial,List<Accion> acciones){
		int aux1 = 0, aux2 = 0,cant=0,resta=0,suma=0;
		ArrayList<Estado> sucesores = new ArrayList<Estado>();
		for (int i = 0; i < acciones.size(); i++){
			int [][] copia = new int[estado_inicial.get_terreno().length][estado_inicial.get_terreno()[0].length];//creamos un terreno para cada nodo
			for(int a=0;a<copia.length;a++){                                     
				for(int b=0;b<copia.length;b++){					// para copiar el contenido de terreno a copia.
					copia[a][b]=estado_inicial.get_terreno()[a][b];
				}
			}
			Estado estado =new Estado(copia, estado_inicial.get_tractor()); //creamos un estado por cada accion
			estado.set_tractor(acciones.get(i).get_movimiento());
			for (int j = 0; j < acciones.get(i).get_reparto().size(); j++){
				cant =acciones.get(i).get_reparto_cantidad(j);
				aux1=estado.get_terreno()[estado_inicial.get_tractor_x()][estado_inicial.get_tractor_y()];
				aux2 = estado.get_terreno()[acciones.get(i).get_reparto_posicion_x(j)][acciones.get(i).get_reparto_posicion_y(j)];
				suma=aux2+cant;
				resta=aux1-cant;
				estado.get_terreno()[acciones.get(i).get_reparto_posicion_x(j)][acciones.get(i).get_reparto_posicion_y(j)] = suma;
				estado.get_terreno()[estado_inicial.get_tractor_x()][estado_inicial.get_tractor_y()] = resta;
			}
			sucesores.add(estado);     // a�adimos el nodo a la lista de sucesores
			cant=0;					// volvemo a poner a cero la cantidad 
		}
		return sucesores;
	}
    
    private void addNodo( Nodo nodo, Nodo raiz ) {
        //nodo es el resultado de frontera.elimina() en el main
    	
    }
    
    public Nodo eliminar(List<Nodo> frontera){
    	//devolver y eliminar el primer nodo
    }
    
    public void addNodo( Nodo nodo ) {
        this.addNodo( nodo , this.raiz );
    }
    public static boolean test_objetivo(Estado estado, int k){
		boolean objetivo = true;
		for(int i = 0; i < estado.get_terreno().length && objetivo == false; i++){
			for (int j = 0; j < estado.get_terreno()[0].length && objetivo == false; j++){
				if(estado.get_terreno()[i][j] != k)
					objetivo = false;
			}
		}
		return objetivo;
	}
}
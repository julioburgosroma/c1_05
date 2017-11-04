import java.util.Collection;
import java.util.PriorityQueue;

public class Frontera {
	private PriorityQueue<Nodo> frontera;
	
	public Frontera(PriorityQueue<Nodo> f){
		frontera = f;
	}
	public PriorityQueue<Nodo> getFrontera() {
		return frontera;
	}
	
	public void insertar(Nodo nodo){
		frontera.add(nodo);
	}
	
	public void insertar_conjunto (Collection<? extends Nodo> coleccion){
		frontera.addAll(coleccion);
	}
	
	public Nodo elimina(){
	    	return frontera.poll();
	    }
	 
	public boolean es_vacia(){
	    	boolean vacia = false;
	    	if(frontera.isEmpty())
	    		vacia = true;
	    	return vacia;
	    }
}

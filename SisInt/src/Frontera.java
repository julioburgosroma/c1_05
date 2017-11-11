import java.util.Collection;
import java.util.List;
import java.util.Queue;

public class Frontera {
	private List<Nodo> frontera;
	
	public Frontera(List<Nodo> f){
		frontera = f;
	}
	public List<Nodo> getFrontera() {
		return frontera;
	}
	
	public void insertar(Nodo nodo){
		frontera.add(nodo);
	}
	
	public void insertar2(Nodo nodo){
		frontera.add(nodo);
		
	}
	
	public void insertar_conjunto (Collection<? extends Nodo> coleccion){
		frontera.addAll(coleccion);
	}
	public void insertar_conjunto2 (Collection<? extends Nodo> coleccion){
		frontera.addAll(0,coleccion);
	}
	public Nodo elimina(){
	    	return frontera.remove(0);
	    }
	 
	public boolean es_vacia(){
	    	boolean vacia = false;
	    	if(frontera.isEmpty())
	    		vacia = true;
	    	return vacia;
	    }
}

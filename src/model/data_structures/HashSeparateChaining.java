package model.data_structures;

import java.util.Iterator;

public class HashSeparateChaining <K extends Comparable<K>, V extends Comparable<V>> {

	private int tamActual;
	private int tamTotal;
    private NodoHash<K,V>[] nodos;
    private int factorCargaMaximo;
    private int contrehash;
    private NodoHash<K,ListaDoblementeEncadenada<V>>[] nodosSet;
	
    
    public HashSeparateChaining() 
    {
    	this(4);
    }
    
    
	public HashSeparateChaining( int max ){
	tamTotal=max;
	factorCargaMaximo=5;
	tamActual=0;
	contrehash=0;
	nodos=  new NodoHash[max];
	nodosSet=  new NodoHash[max];
	}
	
	public void put(K key, V value){
	
		double factorActual = tamActual/tamTotal;
		if(factorActual>= factorCargaMaximo)
		{
			rehash();
		}
		
		int indice=hash(key);
		if(nodos[indice]==null)
		{
			nodos[indice]= new NodoHash<K,V>(key, value);
		}
		else
		{
			NodoHash<K, V> actual = nodos[indice];
			boolean encontrado=false;
			while (actual.darSiguiente()!=null) {
				if(actual.darE().equals(key)) {
					NodoHash <K,V> temp=actual.darSiguiente();
					encontrado=true;
					actual=new NodoHash<K, V>(key, value);
					actual.cambiarSiguiente(temp);
					
				}
				actual= actual.darSiguiente();	
				}
			if(encontrado==false)
			actual.cambiarSiguiente(new NodoHash<K,V>(key, value));
		}
		tamActual++;
	}

	private void rehash() {

		HashSeparateChaining<K, V> nuevatabla= new HashSeparateChaining<>(tamTotal*5);
		Iterator<K> itr = keys();
		while (itr.hasNext()) {
			K keyActuak = (K) itr.next();
			V valorActual = get(keyActuak);
			nuevatabla.put(keyActuak, valorActual);
		}
		
		tamTotal = 5* tamTotal;
		nodos  = nuevatabla.getNodos();
		++contrehash;
		
		
		
	}

	public NodoHash<K, V>[] getNodos() {
		return	nodos;
	}

	public V delete(K keyActual) {
		// TODO Auto-generated method stub
		NodoHash<K,V> variable= nodos[hash(keyActual)];
		V resp=null;
		if(variable==null)
			return null;
		if(variable.darE().equals(keyActual))
		{
			nodos[hash(keyActual)]=variable.darSiguiente();
			tamActual--;
			resp=variable.darv();
		}
		
		while (variable.darSiguiente()!=null)
		{
			if(variable.darSiguiente().darE().equals(keyActual)) {
			resp=variable.darSiguiente().darv();
			variable.cambiarSiguiente(variable.darSiguiente().darSiguiente());
			tamActual--;}
		variable=variable.darSiguiente();
		
		
		}
		return resp;
	}

	public V get(K keyActual) {
		// TODO Auto-generated method stub
		NodoHash<K,V> variable= nodos[hash(keyActual)];
		V resp=null;
		while (variable!=null)
		{
			if(variable.darE().equals(keyActual))
			resp=variable.darv();
		variable=variable.darSiguiente();
		
		}
		return resp;
	}
	public Iterator<K> keys() {
		// TODO Auto-generated method stub
		ListaDoblementeEncadenada<K> lista=new ListaDoblementeEncadenada<K>();
		
		for (int i = 0; i < nodos.length; i++) {
			
			NodoHash<K, V> nodoHash = nodos[i];
			while(nodoHash!=null) {
				lista.insertarComienzo(nodoHash.darE());
				nodoHash=nodoHash.darSiguiente();
			}
			
		}
		Iterator<K> resp=lista.iterator();
		return resp;
	}
    

 public Iterator<K> keysSet() {
	// TODO Auto-generated method stub
	ListaDoblementeEncadenada<K> lista=new ListaDoblementeEncadenada<K>();
	
	for (int i = 0; i < nodosSet.length; i++) {
		
		NodoHash<K, ListaDoblementeEncadenada<V>> nodoHash = nodosSet[i];
		while(nodoHash!=null) {
			lista.insertarComienzo(nodoHash.darE());
			nodoHash=nodoHash.darSiguiente();
		}
		
	}
	Iterator<K> resp=lista.iterator();
	return resp;
}
	private int hash(K key) {
		
		// TODO Auto-generated method stub
		return (key.hashCode() & 0x7fffffff)%tamTotal;
	}

	public int getTamActual() {
		return tamActual;
	}

	public int getTamTotal() {
		return tamTotal;
	}

	public int getContrehash() {
		return contrehash;
	}
	public void putInSet(K key, V val) {
		
		double factorActual = tamActual/tamTotal;
		if(factorActual>= factorCargaMaximo)
		{
			rehashSet();
		}
		
		int i=hash(key);
		
		
		
		if(nodosSet[i]==null)
		{
			nodosSet[i]= new NodoHash<K,ListaDoblementeEncadenada <V>>(key, new ListaDoblementeEncadenada<>());
			nodosSet[i].darv().insertarComienzo(val);
			
		}
		else
		{
			NodoHash<K, ListaDoblementeEncadenada <V>> actual = nodosSet[i];
			boolean encontrado=false;
			while(actual.darSiguiente()!=null&&encontrado==false) {
				if(actual.darE().equals(key)) {
                    actual.darv().insertarComienzo(val);
                    encontrado=true;
				}
				
					actual= actual.darSiguiente();
				
			}
			if(actual.darE().equals(key)) {
                actual.darv().insertarComienzo(val);
                encontrado=true;
			}
			if(encontrado==false) {
				actual.cambiarSiguiente(new NodoHash<K,ListaDoblementeEncadenada<V>>(key, new ListaDoblementeEncadenada<>()));
				actual.darSiguiente().darv().insertarComienzo(val);
			}
			
		}
		tamActual++;
	}
	public ListaDoblementeEncadenada<V> getSet(K key){
		NodoHash<K,ListaDoblementeEncadenada<V>> variable= nodosSet[hash(key)];
		ListaDoblementeEncadenada<V> resp=null;
		while (variable!=null)
		{
			if(variable.darE().equals(key))
			resp=variable.darv();
		variable=variable.darSiguiente();
		
		}
		return resp;
	}
	public void rehashSet() {

		HashSeparateChaining<K, V> nuevatabla= new HashSeparateChaining<>(tamTotal*5);
		Iterator<K> itr = keysSet();
		while (itr.hasNext()) {
			K keyActuak = (K) itr.next();
			ListaDoblementeEncadenada<V> listaActual = getSet(keyActuak);
			for (V valorActual : listaActual) {
				nuevatabla.putInSet(keyActuak, valorActual);
			}
			
		}
		
		tamTotal = 5* tamTotal;
		nodosSet  = nuevatabla.getNodosSet();
		++contrehash;
		
		
	}


	public NodoHash<K, ListaDoblementeEncadenada<V>>[] getNodosSet() {
		// TODO Auto-generated method stub
		return nodosSet;
	}
	public Iterator <V> deleteSet(K keyActual) {
		// TODO Auto-generated method stub
		NodoHash<K,ListaDoblementeEncadenada<V>> variable= nodosSet[hash(keyActual)];
		ListaDoblementeEncadenada<V> resp=null;
		if(variable==null)
			return null;
		if(variable.darE().equals(keyActual))
		{
			nodosSet[hash(keyActual)]=variable.darSiguiente();
			tamActual--;
			resp=variable.darv();
		}
		
		while (variable.darSiguiente()!=null)
		{
			if(variable.darSiguiente().darE().equals(keyActual)) {
			resp=variable.darSiguiente().darv();
			variable.cambiarSiguiente(variable.darSiguiente().darSiguiente());
			tamActual--;}
		variable=variable.darSiguiente();
		
		
		}
		return resp.iterator();
	}
}

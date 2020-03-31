package model.data_structures;

import java.util.Iterator;

public class HashSeparateChaining <K extends Comparable<K>, V extends Comparable<V>> {

	private int tamActual;
	private int tamTotal;
    private int factorCargaMaximo;
    private int contrehash;
    private int numduplas;
    private NodoHash22<K,ListaDoblementeEncadenada<V>>[] nodosSet;
	
    
    public HashSeparateChaining() 
    {
    	this(4);
    }
    
    
	public HashSeparateChaining( int max ){
	tamTotal=max;
	factorCargaMaximo=5;
	tamActual=0;
	contrehash=0;
	nodosSet=  new NodoHash22[max];
	}
	
    
 public Iterator<K> keysSet() {
	// TODO Auto-generated method stub
	ListaDoblementeEncadenada<K> lista=new ListaDoblementeEncadenada<K>();
	
	for (int i = 0; i < nodosSet.length; i++) {
		
		NodoHash22<K, ListaDoblementeEncadenada<V>> nodoHash = nodosSet[i];
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
			nodosSet[i]= new NodoHash22<K,ListaDoblementeEncadenada <V>>(key, new ListaDoblementeEncadenada<>());
			nodosSet[i].darv().insertarComienzo(val);
			
		}
		else
		{
			NodoHash22<K, ListaDoblementeEncadenada <V>> actual = nodosSet[i];
			boolean encontrado=false;
			while(actual.darSiguiente()!=null && encontrado==false) {
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
			if(!encontrado) {
				actual.cambiarSiguiente(new NodoHash22<K,ListaDoblementeEncadenada<V>>(key, new ListaDoblementeEncadenada<>()));
				actual.darSiguiente().darv().insertarComienzo(val);
				numduplas++;
			}
			
		}
		tamActual++;
	}
	public ListaDoblementeEncadenada<V> getSet(K key){
		NodoHash22<K,ListaDoblementeEncadenada<V>> variable= nodosSet[hash(key)];
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
		
		int temp=tamTotal*2;
		if(temp%2==0) 
		{
			++temp;
		}
		
		HashSeparateChaining<K, V> nuevatabla= new HashSeparateChaining<>(temp);
		Iterator<K> itr = keysSet();
		while (itr.hasNext()) {
			K keyActuak = (K) itr.next();
			ListaDoblementeEncadenada<V> listaActual = getSet(keyActuak);
			for (V valorActual : listaActual) {
				nuevatabla.putInSet(keyActuak, valorActual);
			}
			
		}
		
		tamTotal = temp;
		nodosSet  = nuevatabla.getNodosSet();
		++contrehash;
		
		
	}


	public NodoHash22<K, ListaDoblementeEncadenada<V>>[] getNodosSet() {
		// TODO Auto-generated method stub
		return nodosSet;
	}
	public Iterator <V> deleteSet(K keyActual) {
		// TODO Auto-generated method stub
		NodoHash22<K,ListaDoblementeEncadenada<V>> variable= nodosSet[hash(keyActual)];
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


	public int getNumduplas() {
		return numduplas;
	}
}

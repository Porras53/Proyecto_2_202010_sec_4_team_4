package model.data_structures;

import java.util.Iterator;

public class HashSeparateChaining <K extends Comparable<K>, V> {

	private int tamActual;
	private int tamTotal;
    private NodoHash<K,V>[] nodos;
    private int factorCargaMaximo;
	
	public HashSeparateChaining( int max ){
	max=tamTotal;
	factorCargaMaximo=5;
	tamActual=0;
	nodos=  new NodoHash[max];
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
			while (actual.darSiguiente()!=null) {
				actual= actual.darSiguiente();
				
			}
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
		
		
		
	}

	public NodoHash<K, V>[] getNodos() {
		return	nodos;
	}

	public V get(K keyActuak) {
		// TODO Auto-generated method stub
		return null;
	}

	public Iterator<K> keys() {
		// TODO Auto-generated method stub
		return null;
	}

	private int hash(K key) {
		
		// TODO Auto-generated method stub
		return (key.hashCode() & 0x7fffffff)%tamTotal;
	}
	
}

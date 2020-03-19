package model.data_structures;

import model.logic.KeyComparendo;
import sun.misc.Queue;

public class HashLinearProbing<K extends Comparable<K>, V> {
	
	private int n;           // number of key-value pairs in the symbol table
    private int m;           // size of linear probing table
    private NodoHash<K,V>[] nodos;
    
    public HashLinearProbing (int capacidad) 
    {
    	m = capacidad;
        n = 0;
        nodos= new NodoHash[m];
    }
    
 
    public int size() {
        return n;
    }
    
    public boolean isEmpty() {
        return size() == 0;
    }
    
    public boolean contains(K key) {
        if (key == null) throw new IllegalArgumentException("argument to contains() is null");
        return get(key) != null;
    }
    
    private int hash(K key) {
        return (key.hashCode() & 0x7fffffff) % m;
    }

    
    private void resize(int capacidad) {
        HashLinearProbing temp = new HashLinearProbing(capacidad);
        for (int i = 0; i < m; i++) {
            if (nodos[i] != null) {
                temp.put(nodos[i].darE(),nodos[i].darv());
            }
        }
        nodos = temp.nodos;
        m    = temp.m;
    }
    
    public void put(K key, V val) {
        if (key == null) throw new IllegalArgumentException("first argument to put() is null");

        if (val == null) {
            delete(key);
            return;
        }

        // double table size if 50% full
        if (n >= m/2) resize(2*m);
        
        int i= hash(key);
        
        if(nodos[i]==null) 
        {
        	nodos[i]=new NodoHash(key,val);
        }
        else {
        	
        for (; nodos[i]!= null; i = (i + 1) % m) {
        	if(nodos[i].darE()!=null){
        		
            if (nodos[i].darE().equals(key)) {
                nodos[i].cambiarV(val);
                return;
            }
            
        	}
        }
        
        nodos[i]=new NodoHash(key,val);
        
        }
        n++;
    }
    
    public V get(K key) {
        if (key == null) throw new IllegalArgumentException("argument to get() is null");
        for (int i = hash(key); nodos[i].darE() != null; i = (i + 1) % m)
            if (nodos[i].darE().equals(key))
                return nodos[i].darv();
        return null;
    }
    
    public void delete(K key) {
        if (key == null) throw new IllegalArgumentException("argument to delete() is null");
        if (!contains(key)) return;

        // find position i of key
        int i = hash(key);
        while (!key.equals(nodos[i].darE())) {
            i = (i + 1) % m;
        }

        // delete key and associated value
        nodos[i].cambiarE(null);
        nodos[i].cambiarV(null);
        
        // rehash all keys in same cluster
        i = (i + 1) % m;
        while (nodos[i].darE() != null) {
            // delete keys[i] an vals[i] and reinsert
            K   keyToRehash = nodos[i].darE();
            V valToRehash = nodos[i].darv();
            nodos[i].cambiarE(null);
            nodos[i].cambiarV(null);
            n--;
            put(keyToRehash, valToRehash);
            i = (i + 1) % m;
        }

        n--;
    }
    
    public Iterable<K> keys() {
        ListaEncadenadaCola<K> queue = new ListaEncadenadaCola<K>();
        for (int i = 0; i < m; i++)
            if (nodos[i].darE() != null) queue.insertarFinal(nodos[i].darE());
        return queue;
    }
    
    
    
    
    
}

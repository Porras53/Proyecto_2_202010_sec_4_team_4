package model.data_structures;

import model.logic.KeyComparendo;
import sun.misc.Queue;

public class HashLinearProbing<K extends Comparable<K>, V extends Comparable<V>> {
	
	private int n;           // number of key-value pairs in the symbol table
    private int m;           // size of linear probing table
    private NodoHash22<K,V>[] nodos;
    private int contrehash;
    
    public HashLinearProbing() {
        this(4);
    }
    
    public HashLinearProbing (int capacidad) 
    {
    	m = capacidad;
        n = 0;
        nodos= new NodoHash22[m];
        contrehash=0;
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
    	if(capacidad % 2 == 0) 
    	{
    		capacidad++;
    	}
    	
        HashLinearProbing temp = new HashLinearProbing(capacidad);
        for (int i = 0; i < m; i++) {
            if (nodos[i] != null) {
                temp.put(nodos[i].darE(),nodos[i].darv());
            }
        }
        nodos = temp.nodos;
        m    = temp.m;
        ++contrehash;
    }
    
    public void put(K key, V val) {
        if (key == null) throw new IllegalArgumentException("first argument to put() is null");

        if (val == null) {
            delete(key);
            return;
        }

        // double table size if 50% full
        if (n >= m/2) resize(2*m);
        
        int i=hash(key);
        
     if(nodos[i]==null) {
    	 nodos[i]=new NodoHash22(key,val);
     }
     else if(nodos[i]!=null){
    	 boolean agregado=false;
    	 boolean posencontrada=false;
        for (;!agregado; i = (i + 1) % m) {
        	
            	   if(nodos[i]==null) 
            	   {
            		   nodos[i]=new NodoHash22(key,val);
            		   agregado=true;
            	   }
        }
     }
     
        n++;
    }
    
    public V get(K key) {
        if (key == null) throw new IllegalArgumentException("argument to get() is null");
        
        for (int i = hash(key); nodos[i]!= null; i = (i + 1) % m)
        
        if(nodos[i].darE()!=null)
            if (nodos[i].darE().equals(key))
                return nodos[i].darv();
        
        
        return null;
    }
    
    public V delete(K key) {
    	V retorno=null;
        if (key == null) throw new IllegalArgumentException("argument to delete() is null");
        if (!contains(key)) return null;

        // find position i of key
        int i = hash(key);
        while (!key.equals(nodos[i].darE())) {
            i = (i + 1) % m;
        }
        
        retorno=nodos[i].darv();

        // delete key and associated value
        nodos[i]=null;
        
        // rehash all keys in same cluster
        i = (i + 1) % m;
        while (nodos[i] != null) {
            // delete keys[i] an vals[i] and reinsert
            K   keyToRehash = nodos[i].darE();
            V valToRehash = nodos[i].darv();
            nodos[i]= null;
            n--;
            put(keyToRehash, valToRehash);
            i = (i + 1) % m;
        }
        
        n--;
        
        if (n > 0 && n <= m/8) resize(m/2);

        assert check();
        
        return retorno;

        
    }
    
    public Iterable<K> keys() {
        ListaDoblementeEncadenada<K> queue = new ListaDoblementeEncadenada<K>();
        for (int i = 0; i < m; i++)
            if (nodos[i].darE() != null) queue.insertarFinal(nodos[i].darE());
        return queue;
    }


	public NodoHash22<K, V>[] getNodos() {
		return nodos;
	}
	
	private boolean check() {

        // check that hash table is at most 50% full
        if (m < 2*n) {
            System.err.println("Hash table size m = " + m + "; array size n = " + n);
            return false;
        }

        // check that each key in table can be found by get()
        for (int i = 0; i < m; i++) {
        	
            if (nodos[i] == null) continue;
            else if (get(nodos[i].darE()) != nodos[i].darv()) {
                System.err.println("get[" + nodos[i].darE() + "] = " + get(nodos[i].darE()) + "; vals[i] = " + nodos[i].darv());
                return false;
            	}
        	
        }
        return true;
    }

	public int getContrehash() {
		return contrehash;
	}
    
    
    
    
    
}

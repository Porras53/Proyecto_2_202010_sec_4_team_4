package model.data_structures;

public class NodoHash<K extends Comparable<K>,V extends Comparable<V>> {
	/**
	 * Elemento Génerico que se almacena en el nodo.
	 */
	private K e;
	private V v;
	/**
	 * Referencia para el siguiente nodo de la lista.
	 */
	private NodoHash siguiente;
	
	/**
	 * 	Método Constructor
	 * @param t2. Elemento que se almacenera en nodo.
	 */
	public NodoHash(K t2,V t3)
	{
		e=t2;
		v=t3;
		siguiente=null;
	}
	
	/**
	 * Se cambia la referencia del siguiente nodo del actual nodo.
	 * @param newnode. Nueva referencia de nodo.
	 */
	
	public void cambiarSiguiente(NodoHash newnode)
	{
		siguiente=newnode;
	}
	
	/**
	 * Retorna la referencia que se tiene como nodo siguiente.
	 * @return Nodo siguiente del actual.
	 */
	
	public NodoHash darSiguiente()
	{
		return siguiente;
	}
	
	/**
	 * Retorna el elemento genérico almacenado en el nodo.
	 * @return Elemento Genérico.
	 */
	public K darE()
	{
		return e;
	}
	
	/**
	 * Permite modificar el elemento contenido en el nodo.
	 * @param e
	 */
	
	public void cambiarE(K e)
	{
		this.e=e;
	}
    

	/**
	 * Retorna el elemento genérico almacenado en el nodo.
	 * @return Elemento Genérico.
	 */
	public V darv()
	{
		return v;
	}
	
	/**
	 * Permite modificar el elemento contenido en el nodo.
	 * @param e
	 */
	
	public void cambiarV(V v)
	{
		this.v=v;
	}

}

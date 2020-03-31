package model.data_structures;

public class NodoHash22<K extends Comparable<K>,V > {
	/**
	 * Elemento G�nerico que se almacena en el nodo.
	 */
	private K e;
	private V v;
	/**
	 * Referencia para el siguiente nodo de la lista.
	 */
	private NodoHash22 siguiente;
	
	/**
	 * 	M�todo Constructor
	 * @param t2. Elemento que se almacenera en nodo.
	 */
	public NodoHash22(K t2,V t3)
	{
		e=t2;
		v=t3;
		siguiente=null;
	}
	
	/**
	 * Se cambia la referencia del siguiente nodo del actual nodo.
	 * @param newnode. Nueva referencia de nodo.
	 */
	
	public void cambiarSiguiente(NodoHash22 newnode)
	{
		siguiente=newnode;
	}
	
	/**
	 * Retorna la referencia que se tiene como nodo siguiente.
	 * @return Nodo siguiente del actual.
	 */
	
	public NodoHash22<K,V> darSiguiente()
	{
		return siguiente;
	}
	
	/**
	 * Retorna el elemento gen�rico almacenado en el nodo.
	 * @return Elemento Gen�rico.
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
	 * Retorna el elemento gen�rico almacenado en el nodo.
	 * @return Elemento Gen�rico.
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

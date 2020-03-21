package model.data_structures;

public class Node <E extends Comparable<E>>
{
	/**
	 * Elemento Génerico que se almacena en el nodo.
	 */
	private E e;
	
	/**
	 * Referencia para el siguiente nodo de la lista.
	 */
	private Node siguiente;
	
	private Node anterior;
	
	/**
	 * 	Método Constructor
	 * @param t2. Elemento que se almacenera en nodo.
	 */
	public Node(E t2)
	{
		e=t2;
		siguiente=null;
		anterior=null;
	}
	
	/**
	 * Se cambia la referencia del siguiente nodo del actual nodo.
	 * @param newnode. Nueva referencia de nodo.
	 */
	
	public void cambiarSiguiente(Node newnode)
	{
		siguiente=newnode;
	}
	
	/**
	 * Retorna la referencia que se tiene como nodo siguiente.
	 * @return Nodo siguiente del actual.
	 */
	
	public Node darSiguiente()
	{
		return siguiente;
	}
	
	/**
	 * Retorna el elemento genérico almacenado en el nodo.
	 * @return Elemento Genérico.
	 */
	public E darE()
	{
		return e;
	}
	
	/**
	 * Permite modificar el elemento contenido en el nodo.
	 * @param e
	 */
	
	public void cambiarE(E e)
	{
		this.e=e;
	}

	public Node darAnterior() {
		return anterior;
	}

	public void cambiarAnterior(Node anterior) {
		this.anterior = anterior;
	}
	
	
}

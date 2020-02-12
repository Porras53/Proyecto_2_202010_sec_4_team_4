package model.data_structures;

import java.util.Iterator;

public class ListaEncadenadaPila <T>
{
	
	/**
	 * Cantidad de elementos de la lista
	 */
	private int longitud;
	
	/**
	 * Referencia del primer elemento de la lista
	 */
	private Node<T> topStack = null;
	
	
	/**
	 * Metodo Constructro Lista Encadenada
	 */
	public ListaEncadenadaPila()
	{
		longitud=0;
		topStack=null;
	}
	
	/**
	 * Da el elemento de la clase generica almacenada en la lista.
	 * @return Elemento que esta primero en la Lista
	 */
	
	public T darCabeza()
	{
		return topStack.darE();
	}
	
	/**
	 * Retorna el tamaño de la longitud.
	 * @return longitud lista
	 */
	
	public int darLongitud()
	{
		return longitud;
	}
	
	/**
	 * Dice si la lista esta vacia o no.
	 * @return true si esta vacia, false de lo contrario
	 */
	
	public boolean esListaVacia()
	{
		return topStack==null;
	}
	
	/**
	 * Inserta un nuvo elemento genérico al principio de la lista.
	 * @param t2. Elemento nuevo a agregar.
	 */
	
	public void insertarComienzo(T t2)
	{
		Node<T> nodo = new Node<T>(t2);
		if(esListaVacia())
		{
			topStack = nodo;
		}
		else{
		nodo.cambiarSiguiente(topStack);
		topStack= nodo;
		}
		longitud++;
	}
	
	/**
	 * Elimina el primer elemento de la lista.
	 */
	
	public T eliminarComienzo()
	{
		if(topStack!=null)
		{
			T elem=topStack.darE();
			Node<T> primer= topStack;
			topStack= topStack.darSiguiente();
			primer.cambiarSiguiente(null);
			longitud--;
			return elem;
		}
		else
		{
			return null;
		}
		
	}
	
	/**
	 * Retorna un objeto de la lista , dado su posición.
	 * @param n. Posición en la lista.
	 * @return Elemento de clase génerica.
	 */
	
	public T darObjeto(int pos)
	{
		if(topStack==null)
		{
			return null;
		}
		else
		{
			Node<T> puntero= topStack;
			int cont=0;
			while(cont < pos && puntero.darSiguiente()!=null)
			{
				puntero = puntero.darSiguiente();
				cont++;
			}
			if(cont != pos)
			{
				return null;
			}
			else
			{
				return (T) puntero.darE();
				
			}
		
		}
		
	}
	
	
	
	
	
	
}

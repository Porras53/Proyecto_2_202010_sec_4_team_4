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
	
	
	public T darCabeza()
	{
		return topStack.darE();
	}
	
	public int darLongitud()
	{
		return longitud;
	}
	
	public boolean esListaVacia()
	{
		return topStack==null;
	}
	
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

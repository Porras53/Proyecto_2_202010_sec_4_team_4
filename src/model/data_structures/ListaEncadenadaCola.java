package model.data_structures;

import java.util.Iterator;

public class ListaEncadenadaCola <T>
{
	
	/**
	 * Cantidad de elementos de la lista
	 */
	private int longitud;
	
	/**
	 * Referencia del primer elemento de la lista
	 */
	private Node<T> cabeza = null;
	
	
	/**
	 * Referencia del ultimo elemento de la lista
	 */
	private Node<T> ultimo=null;
	
	
	/**
	 * Metodo Constructro Lista Encadenada
	 */
	public ListaEncadenadaCola()
	{
		longitud=0;
		cabeza=null;
		ultimo=null;
	}
	
	public T darCabeza()
	{
		return cabeza.darE();
	}
	
	public int darLongitud()
	{
		return longitud;
	}
	
	public boolean esListaVacia()
	{
		return cabeza==null;
	}
	
	public void insertarFinal(T t2)
	{
		Node<T> node= new Node<T>(t2);
		if(cabeza==null)
		{
			cabeza = node;
			ultimo=cabeza;
		}
		
		else
		{	
			ultimo.cambiarSiguiente(node);
			ultimo=node;
		}
		longitud++;
	}
	
	
	
	public T eliminarComienzo()
	{
		if(cabeza!=null)
		{
			Node<T> primer= cabeza;
			T elem= cabeza.darE();
			cabeza= cabeza.darSiguiente();
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
		if(cabeza==null)
		{
			return null;
		}
		else
		{
			Node<T> puntero= cabeza;
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
	
	public T darUltimo()
	{
		return ultimo.darE();
	}
	
	
	
	
}

package model.data_structures;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ListaDoblementeEncadenada <T extends Comparable<T>> implements Iterable<T>
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
	public ListaDoblementeEncadenada()
	{
		longitud=0;
		cabeza=null;
		ultimo=null;
	}
	
	/**
	 * Da el elemento de la clase generica almacenada en la lista.
	 * @return Elemento que esta primero en la Lista
	 */
	
	public T darCabeza()
	{
		return cabeza.darE();
	}
	
	
	public Node<T> darCabeza2()
	{
		return cabeza;
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
		return cabeza==null;
	}
	
	/**
	 * Inserta un nuevo elemento genérico al final de la lista.
	 * @param t2. Elemento nuevo a agregar.
	 */
	
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
			node.cambiarAnterior(ultimo);
			ultimo=node;
		}
		longitud++;
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
			cabeza = nodo;
		}
		else{
		nodo.cambiarSiguiente(cabeza);
		cabeza.cambiarAnterior(nodo);
		cabeza= nodo;
		}
		longitud++;
	}
	
	/**
	 * Elimina el primer elemento de la lista.
	 */
	
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
	
	
	public T eliminarFinal()
	{
		if(cabeza!= null)
		{
			T retorno=null;
			if(cabeza.darSiguiente()==null)
			{
				cabeza = null;
			}
			else
			{
				Node<T> penultimo= ultimo.darAnterior();
				retorno= ultimo.darE();
				penultimo.cambiarSiguiente(null);
				ultimo= penultimo;
			}
			
			longitud--;
			return retorno;
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
	
	public Node darObjeto(int pos)
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
				return puntero;
				
			}
		
		}
		
	}
	
	
	/**
	 * Retorna el ultimo elemento génerico de la lista
	 * @return Elemento de clase génerica.
	 */
	
	public T darUltimo()
	{
		return ultimo.darE();
	}
	
	
	public Node<T> darUltimo2()
	{
		return ultimo;
	}

	public Iterator<T> iterator()  {
        return new LinkedIterator(cabeza);  
    }

    
    private class LinkedIterator implements Iterator<T> {
        private Node<T> current;

        public LinkedIterator(Node<T> first) {
            current = first;
        }

        public boolean hasNext()  { return current != null;                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public T next() {
            if (!hasNext()) throw new NoSuchElementException();
            T item = current.darE();
            current = current.darSiguiente(); 
            return item;
        }
	
    }
	
}

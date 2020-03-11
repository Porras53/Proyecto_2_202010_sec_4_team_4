package model.data_structures;

public class MaxColaCP <T extends Comparable<T>>{

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
	public MaxColaCP()
	{
		longitud=0;
		cabeza=null;
		ultimo=null;
	}
	
	/**
	 * Da el elemento de la clase generica almacenada en la lista.
	 * @return Elemento que esta primero en la Lista
	 */
	
	public T darMax()
	{
		return cabeza.darE();
	}
	
	/**
	 * Retorna el tamaño de la longitud.
	 * @return longitud lista
	 */
	
	public int darNumElementos()
	{
		return longitud;
	}
	
	/**
	 * Dice si la lista esta vacia o no.
	 * @return true si esta vacia, false de lo contrario
	 */
	
	public boolean esVacia()
	{
		return cabeza==null;
	}
	
	/**
	 * Inserta un nuevo elemento genérico al final de la lista.
	 * @param t2. Elemento nuevo a agregar.
	 */
	
	public void agregar(T t2)
	{
		Node<T> node= new Node<T>(t2);
		if(esVacia())
		{
			cabeza = node;
			ultimo=cabeza;
		}
		
		else
		{	
			Node puntero= cabeza;
			while(puntero!=null)
			{
				Node siguiente= puntero.darSiguiente();
				if(siguiente!=null)
				{
					if(puntero.darE().compareTo(t2) >= 0 && siguiente.darE().compareTo(t2) <= 0)
					{
						puntero.cambiarSiguiente(node);
						node.cambiarSiguiente(siguiente);
					}
					
				}
				else if(siguiente==null)
				{
					ultimo.cambiarSiguiente(node);
					ultimo=node;
				}
				
				puntero=puntero.darSiguiente();
			}
			
		}
		
		longitud++;
	}
	
	/**
	 * Elimina el primer elemento de la lista.
	 */
	
	public T sacarMax()
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
	
	/**
	 * Retorna un objeto de la lista , dado su posición.
	 * @param n. Posición en la lista.
	 * @return Elemento de clase génerica.
	 */
	
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
	
	
	/**
	 * Retorna el ultimo elemento génerico de la lista
	 * @return Elemento de clase génerica.
	 */
	
	public T darUltimo()
	{
		return ultimo.darE();
	}
	
	
}

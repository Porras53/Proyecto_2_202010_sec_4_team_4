package model.data_structures;

/**
 * 2019-01-23
 * Estructura de Datos Arreglo Dinamico de Strings.
 * El arreglo al llenarse (llegar a su maxima capacidad) debe aumentar su capacidad.
 * @author Fernando De la Rosa
 *
 */
public class ArregloDinamico<T extends Comparable<T>> {
		/**
		 * Capacidad maxima del arreglo
		 */
        private int tamanoMax;
		/**
		 * Numero de elementos presentes en el arreglo (de forma compacta desde la posicion 0)
		 */
        private int tamanoAct;
        /**
         * Arreglo de elementos de tamaNo maximo
         */
        private T elementos[ ];

        /**
         * Construir un arreglo con la capacidad maxima inicial.
         * @param max Capacidad maxima inicial
         */
		public ArregloDinamico( int max )
        {
               elementos = (T[]) new Comparable[max];
               tamanoMax = max;
               tamanoAct = 0;
        }
        
		public void agregar( T dato )
        {
               if ( tamanoAct == tamanoMax )
               {  // caso de arreglo lleno (aumentar tamaNo)
                    tamanoMax = 2 * tamanoMax;
                    T [ ] copia = elementos;
                    elementos = (T[]) new Comparable[tamanoMax];
                    for ( int i = 0; i < tamanoAct; i++)
                    {
                     	 elementos[i] = copia[i];
                    } 
            	    System.out.println("Arreglo lleno: " + tamanoAct + " - Arreglo duplicado: " + tamanoMax);
               }	
               elementos[tamanoAct] = dato;
               tamanoAct++;
       }

		public int darCapacidad() {
			return tamanoMax;
		}

		public int darTamano() {
			return tamanoAct;
		}

		public T darElemento(int i) {
				// TODO implementar
		
			return elementos[i];
		}
		public void swap( int i, int j)
		{
			T temp =elementos[i];
			elementos[i]=elementos[j];
			elementos[j]=temp;
			
		}

		public T buscar(T dato) {
			// TODO implementar
			T buscado=null;
			boolean encontre=false;
			for(int i=0;i<elementos.length&&!encontre;i++)
			{
				
				if(elementos[i].compareTo(dato)==0)
					buscado=elementos[i];
				    encontre=true;
			  		
			}
			
			
			// Recomendacion: Usar el criterio de comparacion natural (metodo compareTo()) definido en Strings.
			return buscado;
		}

		public T eliminar(T dato) {
			// TODO implementar
			T eliminado=null;
			for(int i=0;i<tamanoAct;i++)
			{
			  if(elementos[i].compareTo(dato)==0)
			  {
			 	eliminado=elementos[i];
				for(int j=i; j<tamanoAct;j++)
				{
					elementos[j+1]=elementos[j];
				}
			  }
			}
			
			// Recomendacion: Usar el criterio de comparacion natural (metodo compareTo()) definido en Strings.
			return eliminado;
		}
		
		public T eliminar() {
			T respuesta = elementos[tamanoAct - 1];
			elementos[tamanoAct - 1] = null;
			tamanoAct--;
			return respuesta;
		}

}
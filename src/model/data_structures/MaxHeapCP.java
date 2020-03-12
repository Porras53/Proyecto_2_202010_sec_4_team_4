package model.data_structures;

public class MaxHeapCP <T extends Comparable<T>> 
{
	private ArregloDinamico<T> arreglo;
	
	public MaxHeapCP() {
		arreglo = new ArregloDinamico<T>(100);
	}
	
	public int darTamano() {
		return arreglo.darTamano();
	}
	
	public boolean estaVacio() {
		return arreglo.darTamano() == 0;
	}
	
	public boolean agregar(T dato) {
		arreglo.agregar(dato);
		int posicion = darTamano() - 1;
		while(posicion > 0 && dato.compareTo(arreglo.darElemento((posicion-1)/2)) > 0) {
			arreglo.swap(posicion, (posicion-1)/2);
			posicion = (posicion-1)/2;
		}
		return true;
	}
	
	public T darMayor() {
		return arreglo.darElemento(0);
	}
	
	public T eliminarMayor() {
		arreglo.swap(0, arreglo.darTamano() - 1);
		T elemento = arreglo.eliminar();
		int posicion = 0;
		while(!esHoja(posicion) && (arreglo.darElemento(posicion).compareTo(arreglo.darElemento(2*posicion+1)) < 0 ||
				(!nuloDerecha(posicion) && arreglo.darElemento(posicion).compareTo(arreglo.darElemento(2*posicion+2)) < 0))) {
			if(arreglo.darElemento(posicion).compareTo(arreglo.darElemento(2*posicion+1)) < 0 && (nuloDerecha(posicion) ||
					arreglo.darElemento(2*posicion+1).compareTo(arreglo.darElemento(2*posicion+2)) > 0)) {
				arreglo.swap(posicion, 2*posicion+1);
				posicion = 2*posicion+1;
			} else {
				arreglo.swap(posicion, 2*posicion+2);
				posicion = 2*posicion+2;
			}
		}
		
		return elemento;
	}
	
	public void imprimirDatos() {
		for(int i = 0; i < darTamano(); i++)
			System.out.println(arreglo.darElemento(i).toString());
	}
	
	public boolean esHoja(int pos) {
		return 2*pos+1 >= darTamano();
	}
	
	public boolean nuloDerecha(int pos) {
		return 2*pos+2 >= darTamano();
	}

	public ArregloDinamico<T> getArreglo() {
		return arreglo;
	}
	
}

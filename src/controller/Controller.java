package controller;

import java.io.FileNotFoundException;
import java.util.Scanner;

import model.data_structures.ArregloDinamico;
import model.data_structures.ListaEncadenadaCola;
import model.data_structures.MaxColaCP;
import model.data_structures.MaxHeapCP;
import model.data_structures.Node;
import model.logic.Comparendo;
import model.logic.Modelo;
import view.View;

public class Controller {

	/* Instancia del Modelo*/
	private Modelo modelo;
	
	/* Instancia de la Vista*/
	private View view;
	
	/**
	 * Crear la vista y el modelo del proyecto
	 * @param capacidad tamaNo inicial del arreglo
	 */
	public Controller ()
	{
		view = new View();
		modelo = new Modelo();
	}
	
	
		
	public void run() 
	{
		Scanner lector = new Scanner(System.in);
		boolean fin = false;
		String respuesta = "";

		while( !fin ){
			view.printMenu();

			int option = lector.nextInt();
			switch(option){
				case 1:
				    modelo = new Modelo();
				    view.printMessage("Inserte la cantidad de datos para la muestra: ");
				    int n= lector.nextInt();
				    view.printMessage("Cargando los comparendos...");
				try {
					
					
					modelo.cargarCola(n);
					

				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					ListaEncadenadaCola datosCola= (ListaEncadenadaCola) modelo.getDatosCola();
				    
				    view.printMessage("Numero de comparendos = " + datosCola.darLongitud() + "\n---------");
					break;

					
				case 2:
					view.printMessage("Inserte la cantidad de datos que quiere evaluar: ");
				    n= lector.nextInt();
				    view.printMessage("Inserte las clases de vehiculos , separadas por comas y sin espacios (Ej: Motocicleta,Automovil ...): ");
				    String clases= lector.next();
				    
				    try{
				    String[] parametroclases= clases.trim().split(",");
				    MaxHeapCP heap = modelo.requerimientoMaxHeap(n, parametroclases);
				    heap.imprimirDatos();
				    }
				    catch(Exception e)
				    {
				    	view.printMessage("Ingreso de forma incorrecta el formato de las clases de vehiculo...");
				    }
				    break;
				
				case 3:
					view.printMessage("Inserte la cantidad de datos que quiere evaluar: ");
				    n= lector.nextInt();
				    view.printMessage("Inserte las clases de vehiculos , separadas por comas (Ej: Motocicleta,Automovil ...): ");
				    clases= lector.next();
				    
				    try{
				    String[] parametroclases= clases.trim().split(",");
				    MaxColaCP cola = modelo.requerimientoMaxCola(n, parametroclases);
				    for(Node puntero=cola.darMax2();puntero!=null;puntero=puntero.darSiguiente())
				    {
				    	view.printMessage(puntero.darE().toString());
				    }
				    }
				    catch(Exception e)
				    {
				    	view.printMessage("Ingreso de forma incorrecta el formato de las clases de vehiculo...");
				    }
				    
				    break;
					
				case 5: 
					view.printMessage("--------- \n Hasta pronto !! \n---------"); 
					lector.close();
					fin = true;
					break;	

				default: 
					view.printMessage("--------- \n Opcion Invalida !! \n---------");
					break;
			}
		}
		
	}	
}

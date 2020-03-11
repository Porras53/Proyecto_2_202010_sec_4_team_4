package controller;

import java.io.FileNotFoundException;
import java.util.Scanner;


import model.data_structures.ListaEncadenadaCola;
import model.data_structures.MaxColaCP;
import model.data_structures.MaxHeapCP;
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
				    view.printMessage("Cargando los comparendos...");
				try {
					long inicial = System.currentTimeMillis();
					modelo.cargarHeap();
					long finalT = System.currentTimeMillis();
					System.out.println("Tiempo Heap: " + ((finalT - inicial)) + "ms");
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					//MaxColaCP datosCola= (MaxColaCP) modelo.getDatosCola();
				    
				    //view.printMessage("Lista de Comparendos cargado");
				    //view.printMessage("Primer Comparendo = " + datosCola.darMax().toString() + "\n---------");
				    //view.printMessage("Ultimo Comparendo = " + datosCola.darUltimo().toString() + "\n---------");
				    //view.printMessage("Numero de comparendos = " + datosCola.darNumElementos() + "\n---------");
					break;

					
				case 3:
					MaxHeapCP<Comparendo> heap = modelo.getHeap();
					view.printMessage("Ingrese el numero de comparendos\n");
					int numero = lector.nextInt();
					for(int i = 0; i < numero; i++) {
						System.out.println(heap.eliminarMayor());
					}
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

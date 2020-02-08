package controller;

import java.io.FileNotFoundException;
import java.util.Scanner;


import model.data_structures.ListaEncadenadaCola;
import model.data_structures.ListaEncadenadaPila;
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
				try {
					modelo.cargar();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				    ListaEncadenadaCola datosCola= (ListaEncadenadaCola) modelo.getDatosCola();
				    ListaEncadenadaPila datosPila=(ListaEncadenadaPila) modelo.getDatosPila();
				    
				    view.printMessage("Lista de Comparendos cargado");
				    view.printMessage("Primer Comparendo = " + datosCola.darCabeza().toString() + "\n---------");
				    view.printMessage("Ultimo Comparendo = " + datosPila.darCabeza().toString() + "\n---------");
				    view.printMessage("Numero de comparendos = " + datosCola.darLongitud() + "\n---------");
					break;

				case 2:
					view.printMessage("--------- \nDar Object ID del comparendo a consultar: \n---------");
					int dato = lector.nextInt();
					view.printMessage("--------- \n"+modelo.darInfoPorID(dato)+"\n---------");						
					break;
					
				case 3:
					
					break;
					
				case 4: 
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

package controller;

import java.io.FileNotFoundException;
import java.util.Scanner;


import model.data_structures.ListaEncadenadaCola;
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
					modelo.cargar();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				    ListaEncadenadaCola datosCola= (ListaEncadenadaCola) modelo.getDatosCola();
				    
				    view.printMessage("Lista de Comparendos cargado");
				    view.printMessage("Primer Comparendo = " + datosCola.darCabeza().toString() + "\n---------");
				    view.printMessage("Ultimo Comparendo = " + datosCola.darUltimo().toString() + "\n---------");
				    view.printMessage("Numero de comparendos = " + datosCola.darLongitud() + "\n---------");
					break;

				case 2:
					view.printMessage("--------- \nProcesando... \n---------");
					Comparable<Comparendo>[] datos= modelo.copiarComparendos();
					modelo.shellSort(datos);
					
					view.printMessage("Los primeros diez comparendos: \n---------");
					int i=0;
					while(i<10)
					{
						Comparendo c=(Comparendo) datos[i];
						view.printMessage("Comparendo= Codigo Infraccion:"+c.toString() +"\n---------");
						i++;
					}
					view.printMessage("Los ultimos diez comparendos: \n---------");
					i=10;
					while(i>0)
					{
						Comparendo c=(Comparendo) datos[(datos.length-1)-i];
						view.printMessage("Comparendo= Codigo Infraccion:"+c.toString() +"\n---------");
						i--;
					}
							
					
					break;
					
				case 3:
					view.printMessage("--------- \nProcesando... \n---------");
					Comparable<Comparendo>[] datos1= modelo.copiarComparendos();
					long inicio = System.currentTimeMillis();
					long inicio2 = System.nanoTime();
					modelo.sort(datos1);
					modelo.mergeSort(datos1,0, datos1.length-1);
					long fin2 = System.nanoTime();
					long fin3 = System.currentTimeMillis();

					double tiempo = (double) ((fin3 - inicio)/1000);
					System.out.println((fin2-inicio2)/1.0e9 +" segundos, duró mergeSort.");
					System.out.println(tiempo +" segundos, duró mergeSort.");
					
					
					view.printMessage("Los primeros diez comparendos: \n---------");
					i=0;
					while(i<10)
					{
						Comparendo c=(Comparendo) modelo.getAux()[i];
						view.printMessage("Comparendo= Codigo Infraccion:"+c.toString() +"\n---------");
						i++;
					}
					view.printMessage("Los ultimos diez comparendos: \n---------");
					i=10;
					while(i>0)
					{
						Comparendo c=(Comparendo) modelo.getAux()[(modelo.getAux().length-1)-i];
						view.printMessage("Comparendo= Codigo Infraccion:"+c.toString() +"\n---------");
						i--;
					}
					
					break;
					
				case 4: 
					view.printMessage("--------- \n Procesando... \n---------"); 
					Comparable<Comparendo>[] datos2= modelo.copiarComparendos();
					
					inicio = System.currentTimeMillis();
					inicio2 = System.nanoTime();
					
					modelo.quickSort(datos2);
					
					fin2 = System.nanoTime();
					fin3 = System.currentTimeMillis();

					tiempo = (double) ((fin3-inicio)/1000);
					System.out.println((fin2-inicio2)/1.0e9 +" segundos, duró QuickSort.");
					System.out.println(tiempo +" segundos, duró QuickSort.");
					
					view.printMessage("Los primeros diez comparendos: \n---------");
					i=0;
					while(i<10)
					{
						Comparendo c=(Comparendo) datos2[i];
						view.printMessage("Comparendo= Codigo Infraccion:"+c.toString() +"\n---------");
						i++;
					}
					view.printMessage("Los ultimos diez comparendos: \n---------");
					i=10;
					while(i>0)
					{
						Comparendo c=(Comparendo) datos2[(datos2.length-1)-i];
						view.printMessage("Comparendo= Codigo Infraccion:"+c.toString() +"\n---------");
						i--;
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

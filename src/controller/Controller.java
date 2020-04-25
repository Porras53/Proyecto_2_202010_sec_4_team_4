package controller;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import model.data_structures.HashLinearProbing;
import model.data_structures.HashSeparateChaining;
import model.data_structures.ListaDoblementeEncadenada;
import model.data_structures.Node;
import model.data_structures.NodoHash;
import model.data_structures.NodoHash22;
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
					
					
					modelo.cargarCola();
					

				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				    
					break;
				case 2: 
					view.printMessage("Inserta un número n: \n---------"); 
					int n1 = lector.nextInt();
					modelo.parteApunto1(n1);
					
					break;
				case 3: 
					view.printMessage("Inserta el número del mes(1-12): \n---------"); 
					int nmes = lector.nextInt();
					view.printMessage("Inserte el día de la semana (L,M,I,J,V,S,D): \n---------"); 
					String diasem = lector.next();
					modelo.parteApunto2(nmes, diasem);
					
					break;
				case 4: 
					view.printMessage("Inserta una fecha inicial en el formato: (yyyy/MM/dd-HH:mm:ss): \n---------"); 
					String fecha1 = lector.next();
					view.printMessage("Inserta una fecha inicial en el formato: (yyyy/MM/dd-HH:mm:ss): \n---------"); 
					String fecha2 = lector.next();
					view.printMessage("Inserta una localidad: \n---------"); 
					String localidad1 = lector.next();
					
					SimpleDateFormat objSDF= new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss");
					Date fecha11=null;
					Date fecha22=null;
					try {

						fecha11=objSDF.parse(fecha1);
						fecha22=objSDF.parse(fecha2);
						

					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					modelo.parteApunto3(fecha11, fecha22, localidad1);
					
					break;
				case 5: 
					view.printMessage("Inserta un número n \n---------"); 
					int n = lector.nextInt();
					modelo.parteBpunto1(n);
					break;
					
				case 6: 
					view.printMessage("Inserte el medio de deteccion: \n---------"); 
					String mediodeteccion = lector.next();
					view.printMessage("Inserte la clase del vehiculo: \n---------"); 
					String clasevehi = lector.next();
					view.printMessage("Inserte el tipo de servicio: \n---------"); 
					String tiposervi = lector.next();
					view.printMessage("Inserte la localidad: \n---------"); 
					String localidad = lector.next();
					modelo.parteBpunto2(mediodeteccion, clasevehi, tiposervi, localidad);
					break;
				case 7: 
					view.printMessage("Inserta la latitud inicial: \n---------"); 
					double la1 = lector.nextDouble();
					view.printMessage("Inserta la latitud final: \n---------"); 
					double la2 = lector.nextDouble();
					
					modelo.parteBpunto3(la1, la2);
					break;
				case 8: 
					view.printMessage("Inserta un número n que indica el rango de fechas: \n---------"); 
					int n2 = lector.nextInt();
					modelo.parteCpunto1(n2);
					break;
				case 9: 
					modelo.parteCpunto2();
					break;
				case 10: 
					modelo.parteCpunto3();
					break;
				
				case 11: 
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

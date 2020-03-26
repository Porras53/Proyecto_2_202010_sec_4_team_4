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
					HashLinearProbing datosCola= modelo.getDatosCola2();
					Double nm= (Double.valueOf(datosCola.size())/Double.valueOf(datosCola.getNodos().length));
				    view.printMessage("Factor final de carga linear hash (N/M) = "+ nm);
				    view.printMessage("Número de duplas(K,V) en la tabla linear hash= " + datosCola.size() + "\n");
				    view.printMessage("Número de rehash hechos en linear hash= "+datosCola.getContrehash());
				    
				    
				    HashSeparateChaining datosCola2=modelo.getDatosCola3();
				    Double nm2= (Double.valueOf(datosCola2.getTamActual())/Double.valueOf(datosCola2.getNodosSet().length));
				    view.printMessage("Factor final de carga separate hash (N/M) = "+ nm2);
				    view.printMessage("Número de comparendos o número de duplas(K,V) en la tabla linear hash= " + datosCola2.getNumduplas()+ "\n");
				    view.printMessage("Número de rehash hechos en separate hash= "+datosCola2.getContrehash());
				    
				    
				    NodoHash[] nodos=datosCola.getNodos();
				    boolean encontrado=false;
				    for(int i=0;i<nodos.length && !encontrado;i++) 
				    {
				    	
				    	if(nodos[i]!=null) 
				    	{
				    		view.printMessage("Primer Comparendo = "+ datosCola.getNodos()[i].darv().toString());
				    		encontrado=true;
				    	}
				    }
				    
				    Object retorno=null;
				    for(int i=0;i<nodos.length;i++) 
				    {
				    	if(nodos[i]!=null) 
				    	{
				    		retorno=datosCola.getNodos()[i].darv();
				    	}
				    }
				    view.printMessage("Último Comparendo = "+ retorno.toString());
				    
					break;
					
				case 2: 
					
					view.printMessage("Ingresa la fecha(año/mes/dia): \n---------"); 
					String fe=lector.next();
					
					SimpleDateFormat objSDF= new SimpleDateFormat("yyyy/MM/dd");
					
				Date nuevafecha=null;
				
				try {
					nuevafecha = objSDF.parse(fe);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					
					view.printMessage("Ingresa la clase de vehiculo: \n---------"); 
					String clasevehicu=lector.next();
					
					if(clasevehicu.equalsIgnoreCase("Automóvil"))
					{
						clasevehicu="AutomÃ“vil";
					}
					else if(clasevehicu.equalsIgnoreCase("Camión")) 
					{
						clasevehicu="CamiÃ“n";
					}
					
					view.printMessage("Ingresa el codigo de infraccion: \n---------"); 
					String infra=lector.next();
					
					
					ListaDoblementeEncadenada nuevo=modelo.requerimiento1Linear(nuevafecha, clasevehicu, infra);
					int i=0;
					Node puntero= nuevo.darCabeza2();
					while(i<nuevo.darLongitud()) 
					{
						view.printMessage("Comparendo "+i+ " = "+puntero.darE().toString());
						
						puntero=puntero.darSiguiente();
						i++;
					}
					break;		
				
				case 3:
					view.printMessage("Ingresa la fecha(año/mes/dia): \n---------"); 
					String fe1=lector.next();
					
					SimpleDateFormat objSDF1= new SimpleDateFormat("yyyy/MM/dd");
					
				Date nuevafecha1=null;
				
				try {
					nuevafecha1 = objSDF1.parse(fe1);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					
					view.printMessage("Ingresa la clase de vehiculo: \n---------"); 
					String clasevehicu1=lector.next();
					
					view.printMessage("Ingresa el codigo de infraccion: \n---------"); 
					String infra1=lector.next();
					
					
					ListaDoblementeEncadenada nuevo1=modelo.requerimiento1Separate(nuevafecha1, clasevehicu1, infra1);
					
					int i1=0;
					Node puntero1= nuevo1.darCabeza2();
					while(i1<nuevo1.darLongitud()) 
					{
						view.printMessage("Comparendo "+i1+ " = "+puntero1.darE().toString());
						
						puntero1=puntero1.darSiguiente();
						i1++;
					}
					break;
					
				case 4:
					modelo.requerimiento3();
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

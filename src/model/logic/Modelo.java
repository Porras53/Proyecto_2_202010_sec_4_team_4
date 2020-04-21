package model.logic;

import java.awt.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;

import model.data_structures.*;

/**
 * Definicion del modelo del mundo
 *
 */
public class Modelo {
	/**
	 * Atributos del modelo del mundo
	 */
	private final static double LATITUD_ESTA_POLI=4.647586;

	private final static double LONGITUD_ESTA_POLI=74.078122;

	private static final int EARTH_RADIUS = 6371; // Approx Earth radius in KM
	/**
	 * Cola de lista encadenada.
	 */

	private HashLinearProbing datosCola2;

	private HashSeparateChaining datosCola3;

	private ArbolRojoNegroBTS datosArbol;
	
	private ArbolRojoNegroBTS datosArbol2;

	private MaxHeapCP  maxcola;
	
	private ListaDoblementeEncadenada<Comparendo> listaDatos;
	
	private static Comparable[] aux;

	/**
	 * Constructor del modelo del mundo con capacidad predefinida
	 */
	public Modelo()
	{

		datosCola2 = new HashLinearProbing();
		datosCola3=new HashSeparateChaining();
		datosArbol= new ArbolRojoNegroBTS();
		listaDatos=new ListaDoblementeEncadenada();
		maxcola= new MaxHeapCP();
	}

	/**
	 * Carga el archivo .JSON en una lista enlazada.
	 * @throws FileNotFoundException. Si no encuentra el archivo.
	 */

	public void cargarCola() throws FileNotFoundException
	{
		//Definir mejor la entrada para el lector de json

		long inicio = System.currentTimeMillis();
		long inicio2 = System.nanoTime();
		String dir= "./data/Comparendos_DEI_2018_Bogotá_D.C.geojson";
		File archivo= new File(dir);
		JsonReader reader= new JsonReader( new InputStreamReader(new FileInputStream(archivo)));
		JsonObject gsonObj0= JsonParser.parseReader(reader).getAsJsonObject();

		JsonArray comparendos=gsonObj0.get("features").getAsJsonArray();
		int i=0;
		while(i<comparendos.size())
		{
			JsonElement obj= comparendos.get(i);
			JsonObject gsonObj= obj.getAsJsonObject();

			JsonObject gsonObjpropiedades=gsonObj.get("properties").getAsJsonObject();
			int objid= gsonObjpropiedades.get("OBJECTID").getAsInt();
			String fecha= gsonObjpropiedades.get("FECHA_HORA").getAsString();
			String mediodeteccion = gsonObjpropiedades.get("MEDIO_DETECCION").getAsString();
			String clasevehiculo=gsonObjpropiedades.get("CLASE_VEHICULO").getAsString();
			String tiposervi=gsonObjpropiedades.get("TIPO_SERVICIO").getAsString();
			String infraccion=gsonObjpropiedades.get("INFRACCION").getAsString();
			String desinfraccion=gsonObjpropiedades.get("DES_INFRACCION").getAsString();
			String localidad=gsonObjpropiedades.get("LOCALIDAD").getAsString();
			String municipio = "";

			JsonObject gsonObjgeometria=gsonObj.get("geometry").getAsJsonObject();

			JsonArray gsonArrcoordenadas= gsonObjgeometria.get("coordinates").getAsJsonArray();
			double longitud= gsonArrcoordenadas.get(0).getAsDouble();
			double latitud= gsonArrcoordenadas.get(1).getAsDouble();
			double distanciconestacion=distance(latitud, longitud, LATITUD_ESTA_POLI, LONGITUD_ESTA_POLI);
			
			
			Comparendo agregar=new Comparendo(objid, fecha,mediodeteccion,clasevehiculo, tiposervi, infraccion, desinfraccion, localidad, municipio ,longitud,latitud,distanciconestacion);
			datosArbol.put(agregar.getLlave(), agregar);
			listaDatos.insertarComienzo(agregar);
			i++;
		}
		long fin2 = System.nanoTime();
		long fin = System.currentTimeMillis();

		System.out.println((fin2-inicio2)/1.0e9 +" segundos, de la carga de datos normal.");

		System.out.println("Numero de Comparendos: "+datosArbol.size());
		System.out.println("El comparendo con mayor ObejctID es: "+datosArbol.max(datosArbol.getRoot()).darv().toString());

	}



	private static void shuffle(Comparable[] a)
	{
		Random r= new Random();
		for(int i= a.length-1;i>0;i--)
		{
			int index= r.nextInt(i+1);
			Comparable a2= a[index];
			a[index]=a[i];
			a[i]=a2;
		}
	}

	public static Comparable[] getAux() {
		return aux;
	}



	public HashLinearProbing getDatosCola2() {
		return datosCola2;
	}

	public HashSeparateChaining getDatosCola3() {
		return datosCola3;
	}

	public Comparable[] copiar(ListaDoblementeEncadenada datos)
	{
		int i=0;
		Node puntero=datos.darCabeza2();
		Comparable[] arreglo= new Comparable[datos.darLongitud()];
		while(i<datos.darLongitud())
		{
			arreglo[i]= puntero.darE();
			puntero=puntero.darSiguiente();
			i++;
		}
		return arreglo;

	}

	public void pegar(Comparable[] copia, ListaDoblementeEncadenada nuevo)
	{
		int i=0;
		while(i<copia.length)
		{
			nuevo.insertarFinal(copia[i]);
			i++;
		}
	}

	public void shellSortMenoraMayor(Comparable datos[])
	{

		int N=datos.length;
		int h=1;
		while(h<N/3)
			h=3*h+1;
		while(h>=1){
			for(int i=h;i<N;i++)
			{
				for(int j=i;j>=h && less(datos[j], datos[j-h]);j-=h)
				{
					exch(datos,j,j-h);
				}
			}
			h=h/3;
		}

	}

	private boolean less(Comparable v,Comparable w)
	{
		return v.compareTo(w) < 0;
	}

	private void exch(Comparable[] datos,int i, int j)
	{
		Comparable t=datos[i];
		datos[i]=datos[j];
		datos[j]=t;
	}

	public static double distance(double startLat, double startLong,
			double endLat, double endLong) {

		double dLat  = Math.toRadians((endLat - startLat));
		double dLong = Math.toRadians((endLong - startLong));

		startLat = Math.toRadians(startLat);
		endLat   = Math.toRadians(endLat);

		double a = haversin(dLat) + Math.cos(startLat) * Math.cos(endLat) * haversin(dLong);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

		return EARTH_RADIUS * c; // <-- d
	}

	public static double haversin(double val) {
		return Math.pow(Math.sin(val / 2), 2);
	}


	public void parteApunto1(int m) {
	    ListaDoblementeEncadenada<Comparendo> list;
	    list = new ListaDoblementeEncadenada<Comparendo>();
		MaxHeapCP <Comparendo>heap= new MaxHeapCP<Comparendo>();
		for (Comparendo comparendo : listaDatos) {

		   comparendo.setIndicador(1);
		   heap.agregar(comparendo);
		}

		for (int i = 0; i < m; i++) {
			list.insertarComienzo(heap.eliminarMayor());

		}
		Node actual=list.darCabeza2();
		for(int i=0;i<list.darLongitud() && actual != null;i++) 
		{
			System.out.println(actual.darE().toString());
			actual=actual.darSiguiente();
		}





	}
	      public void parteApunto2(int mes, String pDia)
	{
		HashSeparateChaining<String, Comparendo> tabla;
		tabla=new HashSeparateChaining<String,Comparendo>(100);
		for (Comparendo comparendo  : listaDatos) {
			String key ="";
			key+=comparendo.getDate().getMonth();
			int dia=comparendo.getDate().getDay();
			if(dia==0)
				key+=",D";
			else if(dia==1)
				key+=",L";
			else if(dia==2)
				key+=",M";
			else if(dia==3)
				key+=",I";

			else if(dia==4)
				key+=",J";
			else if(dia==5)
				key+=",V";
			else
				key+=",S";
			tabla.putInSet(key, comparendo);
		}
		
		ListaDoblementeEncadenada resp=tabla.getSet(mes+","+pDia);
		Node actual=resp.darCabeza2();
		for(int i=0;i<resp.darLongitud() && actual != null;i++) 
		{
			System.out.println(actual.darE().toString());
			actual=actual.darSiguiente();
		}

	}
	      public void parteApunto3(Date fechaInicial, Date fechaFinal, String localidad)
	      {
	    	  ListaDoblementeEncadenada<Comparendo> resp;
	    	  resp= new ListaDoblementeEncadenada<Comparendo>();
	    	  Iterable<KeyComparendo> it =datosArbol.keys(datosArbol.min(),datosArbol.max());
	    	  
	    	for (KeyComparendo key : it) {
	    		  
			Comparendo c = (Comparendo) datosArbol.get(key);
			
			SimpleDateFormat objSDF2= new SimpleDateFormat("YYYY/MM/DD-HH:MM:ss");

			String nuevo=objSDF2.format(c.getDate());

			Date nuevofinal=null;
			try {
				nuevofinal=objSDF2.parse(nuevo);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
	    	  if(c.getLocalidad().equalsIgnoreCase(localidad.trim()) && nuevofinal.compareTo(fechaInicial)<=0 && nuevofinal.compareTo(fechaFinal)>=0)
	    		  resp.insertarComienzo(c);

			}
	    		
	    	Node actual=resp.darCabeza2();
	  		for(int i=0;i<resp.darLongitud() && actual != null;i++) 
	  		{
	  			System.out.println(actual.darE().toString());
	  			actual=actual.darSiguiente();
	  		}
	    	   

	      }

	public void parteBpunto1(int n) 
	{
		MaxHeapCP maxcola2= new MaxHeapCP();
		Iterable<KeyComparendo> resultado= datosArbol.keys(datosArbol.min(),datosArbol.max());
		
		Iterator<KeyComparendo> iterator= resultado.iterator();
		while(iterator.hasNext()) 
		{
			KeyComparendo llave= (KeyComparendo) iterator.next();
			Comparendo comparendo=(Comparendo)datosArbol.get(llave);
			comparendo.setIndicador(2);
			maxcola2.agregar(comparendo);
		}
		
		
		for(int i=0; i<n;i++) 
		{
			System.out.println(maxcola2.getArreglo().darElemento(i).toString());
		}
	}
	
	public void parteBpunto2(String mediodeteccion, String clasevehi,String tiposervi,String localidad) 
	{
		MaxColaCP maxcola2= new MaxColaCP();
		Iterable<KeyComparendo> resultado= datosArbol.keys(datosArbol.min(),datosArbol.max());
		Iterator<KeyComparendo> iterator= resultado.iterator();
		while(iterator.hasNext()) 
		{
			KeyComparendo llave= (KeyComparendo) iterator.next();
			Comparendo comparendo=(Comparendo)datosArbol.get(llave);
			comparendo.setIndicador(3);
			if(comparendo.getMediodeteccion().equalsIgnoreCase(mediodeteccion.trim()) && comparendo.getClasevehi().equalsIgnoreCase(clasevehi.trim()) && comparendo.getTiposervi().equalsIgnoreCase(tiposervi.trim()) && comparendo.getLocalidad().equalsIgnoreCase(localidad.trim())) 
			{
				maxcola2.agregar(comparendo);
			}
		}
		
		Node actual=maxcola2.darMax2();
		for(int i=0;i<maxcola2.darNumElementos() && actual != null;i++) 
		{
			System.out.println(actual.darE().toString());
			actual=actual.darSiguiente();
		}
		
	}
	public void parteBpunto3(double latitudin, double latitudfi) 
	{
		ListaDoblementeEncadenada maxcola2= new ListaDoblementeEncadenada();
		Iterable<KeyComparendo> resultado= datosArbol.keys(datosArbol.min(),datosArbol.max());
		
		Iterator<KeyComparendo> iterator= resultado.iterator();
		while(iterator.hasNext()) 
		{
			KeyComparendo llave= (KeyComparendo) iterator.next();
			Comparendo comparendo=(Comparendo)datosArbol.get(llave);
			
			if( comparendo.getLatitud()<=latitudfi && comparendo.getLatitud()>=latitudin) {
			System.out.println(comparendo.toString());
			}
		}
		
		
		
	}
	
	public void parteCpunto1() 
	{
		
	}
	
}


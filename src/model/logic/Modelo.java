package model.logic;

import java.awt.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
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
	
	/**
	 * Cola de lista encadenada.
	 */
	
	private MaxHeapCP datosCola2;
	
	private MaxColaCP datosCola3;
	
	private ListaEncadenadaCola datosCola;

	private static Comparable[] aux;

	/**
	 * Constructor del modelo del mundo con capacidad predefinida
	 */
	public Modelo()
	{
		datosCola3=new MaxColaCP();

		datosCola2 = new MaxHeapCP();

		datosCola = new ListaEncadenadaCola();
	}

	/**
	 * Carga el archivo .JSON en una lista enlazada.
	 * @throws FileNotFoundException. Si no encuentra el archivo.
	 */
	
	public void cargarCola(int n) throws FileNotFoundException
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
			//String mediodeteccion=gsonObjpropiedades.get("MEDIO_DETECCION").getAsString();
			String mediodeteccion = "";
			String clasevehiculo=gsonObjpropiedades.get("CLASE_VEHI").getAsString();
			String tiposervi=gsonObjpropiedades.get("TIPO_SERVI").getAsString();
			String infraccion=gsonObjpropiedades.get("INFRACCION").getAsString();
			String desinfraccion=gsonObjpropiedades.get("DES_INFRAC").getAsString();
			String localidad=gsonObjpropiedades.get("LOCALIDAD").getAsString();
			//String municipio= gsonObjpropiedades.get("MUNICIPIO").getAsString();
			String municipio = "";

			JsonObject gsonObjgeometria=gsonObj.get("geometry").getAsJsonObject();

			JsonArray gsonArrcoordenadas= gsonObjgeometria.get("coordinates").getAsJsonArray();
			double longitud= gsonArrcoordenadas.get(0).getAsDouble();
			double latitud= gsonArrcoordenadas.get(1).getAsDouble();

			Comparendo agregar=new Comparendo(objid, fecha,mediodeteccion,clasevehiculo, tiposervi, infraccion, desinfraccion, localidad, municipio ,longitud,latitud);
			datosCola.insertarFinal(agregar);
			i++;
		}
		long fin2 = System.nanoTime();
		long fin = System.currentTimeMillis();

		
		System.out.println((fin2-inicio2)/1.0e9 +" segundos, de la carga de datos normal.");

		Comparable[] copia=copiarComparendos();
		shuffle(copia);
		
		inicio = System.nanoTime();
		for(i=0;i<n && i<datosCola.darLongitud();i++)
		{
			datosCola2.agregar(copia[i]);
		}
		fin = System.nanoTime();
		System.out.println((fin-inicio)/1.0e9 +" segundos, de la carga de datos en heap.");
		
		inicio = System.nanoTime();
		for(i=0;i<n && i<datosCola.darLongitud();i++)
		{
			datosCola3.agregar(copia[i]);
		}
		fin = System.nanoTime();
		System.out.println((fin-inicio)/1.0e9 +" segundos, de la carga de datos en cola.");
		
	}
	
	
	public MaxHeapCP requerimientoMaxHeap(int n,String[] clasesvehiculo)
	{
		long inicio2 = System.nanoTime();
		MaxHeapCP retorno=new MaxHeapCP<>();
		ArregloDinamico heap=datosCola2.getArreglo();
		int i=0;
		while(i<clasesvehiculo.length)
		{
			String clase= clasesvehiculo[i];
			int j=0;
			while(j<n && j<datosCola2.darTamano())
			{
				if(((Comparendo)heap.darElemento(j)).getClasevehi().equalsIgnoreCase(clase.trim()))
				{
					retorno.agregar(heap.darElemento(j));
				}
				j++;
			}
			
			i++;
		}
		long fin2 = System.nanoTime();
		System.out.println((fin2-inicio2)/1.0e9 +" segundos, del requerimiento en MaxHeap.");
		return retorno;
	}
	
	
	public MaxColaCP requerimientoMaxCola(int n,String[] clasesvehiculo)
	{
		long inicio2 = System.nanoTime();
		MaxColaCP retorno=new MaxColaCP<>();
		
		int i=0;
		while(i<clasesvehiculo.length)
		{
			String clase= clasesvehiculo[i];
			Node puntero=datosCola3.darMax2();
			int j=0;
			while(j<n && j<datosCola3.darNumElementos() && puntero!=null)
			{
				if(((Comparendo)puntero.darE()).getClasevehi().equalsIgnoreCase(clase.trim()))
				{
					retorno.agregar(puntero.darE());
				}
				puntero=puntero.darSiguiente();
				j++;
			}
			
			i++;
		}
		long fin2 = System.nanoTime();
		System.out.println((fin2-inicio2)/1.0e9 +" segundos, del requerimiento en MaxCola.");
		return retorno;
	}
	
	
	public Comparable[] copiarComparendos()
	{
		int i=0;
		Node puntero=datosCola.darCabeza2();
		Comparable[] arreglo= new Comparable[datosCola.darLongitud()];
		while(i<datosCola.darLongitud())
		{
			arreglo[i]= puntero.darE();
			puntero=puntero.darSiguiente();
			i++;
		}
		
		return arreglo;
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

	public ListaEncadenadaCola getDatosCola() {
		return datosCola;
	}

	public MaxHeapCP getHeap() {
		return datosCola2;
	}






}


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
	private ListaEncadenadaCola datosCola;
	
	private static Comparable[] aux;

	/**
	 * Constructor del modelo del mundo con capacidad predefinida
	 */
	public Modelo()
	{
		datosCola = new ListaEncadenadaCola();
	}

	/**
	 * Carga el archivo .JSON en una lista enlazada.
	 * @throws FileNotFoundException. Si no encuentra el archivo.
	 */

	public void cargar() throws FileNotFoundException
	{
		//Definir mejor la entrada para el lector de json
		long inicio = System.currentTimeMillis();
		long inicio2 = System.nanoTime();
		String dir= "./data/comparendos_dei_2018.geojson";
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
			String clasevehiculo=gsonObjpropiedades.get("CLASE_VEHI").getAsString();
			String tiposervi=gsonObjpropiedades.get("TIPO_SERVI").getAsString();
			String infraccion=gsonObjpropiedades.get("INFRACCION").getAsString();
			String desinfraccion=gsonObjpropiedades.get("DES_INFRAC").getAsString();
			String localidad=gsonObjpropiedades.get("LOCALIDAD").getAsString();

			JsonObject gsonObjgeometria=gsonObj.get("geometry").getAsJsonObject();

			JsonArray gsonArrcoordenadas= gsonObjgeometria.get("coordinates").getAsJsonArray();
			double longitud= gsonArrcoordenadas.get(0).getAsDouble();
			double latitud= gsonArrcoordenadas.get(1).getAsDouble();

			Comparendo agregar=new Comparendo(objid, fecha, clasevehiculo, tiposervi, infraccion, desinfraccion, localidad, longitud,latitud);
			datosCola.insertarFinal(agregar);
			i++;
		}
		long fin2 = System.nanoTime();
		long fin = System.currentTimeMillis();

		double tiempo = (double) ((fin - inicio)/1000);
		System.out.println((fin2-inicio2)/1.0e9 +" segundos, de la carga de datos.");
		System.out.println(tiempo +" segundos, de la carga de datos.");


	}

	public static Comparable[] getAux() {
		return aux;
	}

	/**
	 * Requerimiento 1
	 * @return
	 */
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
	
	/**
	 * Requerimiento 2
	 */
	
	private static boolean less(Comparable v,Comparable w)
	{
		return v.compareTo(w) < 0;
	}
	
	private static void exch(Comparable[] datos,int i, int j)
	{
		Comparable t=datos[i];
		datos[i]=datos[j];
		datos[j]=t;
	}
	
	public void shellSort(Comparable datos[])
	{
		long inicio = System.currentTimeMillis();
		long inicio2 = System.nanoTime();
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
		
		long fin2 = System.nanoTime();
		long fin = System.currentTimeMillis();

		double tiempo = (double) ((fin - inicio)/1000);
		System.out.println((fin2-inicio2)/1.0e9 +" segundos, dur� shell");
		System.out.println(tiempo +" segundos, dur� shell");
	}
	
	
	/**
	 * Requerimiento 3
	 * @param a
	 * @param lo
	 * @param mid
	 * @param hi
	 */

	
	private static void merge(Comparable[] a,int lo, int mid, int hi)
	{
		int i=lo, j= mid+1;
		for(int k=lo;k<= hi;k++)
		{
			aux[k]=a[k];
		}
		for(int k=lo;k<=hi;k++)
		{
			if(i >mid)
			{
				a[k]=aux[j++];
			}
			else if(j>hi)
			{
				a[k]=aux[i++];
			}
			else if(less(aux[j],aux[i]))
			{
				a[k]=aux[j++];
			}
			else
			{
				a[k]=aux[i++];
			}
		}
	}
	
	public static void sort(Comparable[] a)
	{
		aux= new Comparable[a.length];
		mergeSort(a,0,a.length-1);
	}
	
	public static void mergeSort(Comparable[] a,int lo, int hi) 
	{
		if(hi<=lo) return;
		int mid=lo+(hi-lo)/2;
		mergeSort(a,lo,mid);
		mergeSort(a,mid+1,hi);
		merge(a,lo,mid,hi);
	}
	
	
	/**
	 * Requerimiento 4
	 */
	
	private static int partition(Comparable[] a,int lo, int hi)
	{
		int i=lo, j=hi+1;
		Comparable v= a[lo];
		while(true)
		{
			while(less(a[++i],v)) if (i==hi) break;
			while (less (v, a[--j])) if(j== lo) break;
			if(i >= j) break;
			exch(a,i,j);
		}
		exch(a,lo,j);
		return j;
	}
	public static void quickSort(Comparable[] a)
	{
		shuffle(a);
		quickSort(a,0,a.length-1);
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
	
	public static void quickSort(Comparable[] a,int lo,int hi)
	{
		if(hi<=lo) return;
		int j= partition(a,lo,hi);
		quickSort(a,lo,j-1);
		quickSort(a,j+1,hi);
	}
	
	

	/**
	 * Servicio de consulta de numero de elementos presentes en el modelo 
	 * @return numero de elementos presentes en el modelo
	 */

	public ListaEncadenadaCola getDatosCola() {
		return datosCola;
	}







}


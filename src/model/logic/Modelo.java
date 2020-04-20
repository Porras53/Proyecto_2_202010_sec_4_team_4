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
	
	private HashLinearProbing datosCola2;
	
	private HashSeparateChaining datosCola3;
	
	private ArbolRojoNegroBTS datosArbol;

	private static Comparable[] aux;

	/**
	 * Constructor del modelo del mundo con capacidad predefinida
	 */
	public Modelo()
	{
		
		datosCola2 = new HashLinearProbing();
		datosCola3=new HashSeparateChaining();
		datosArbol= new ArbolRojoNegroBTS();
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
		String dir= "./data/Comparendos_DEI_2018_Bogot�_D.C.geojson";
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
			String mediodeteccion = "";
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

			Comparendo agregar=new Comparendo(objid, fecha,mediodeteccion,clasevehiculo, tiposervi, infraccion, desinfraccion, localidad, municipio ,longitud,latitud);
			datosArbol.put(agregar.getLlave(), agregar);
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
public void parteApunto1() {

	






}
}


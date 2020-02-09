package model.logic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;

import model.data_structures.*;
import sun.misc.IOUtils;

/**
 * Definicion del modelo del mundo
 *
 */
public class Modelo {
	/**
	 * Atributos del modelo del mundo
	 */
	private ListaEncadenadaCola datosCola;


	private ListaEncadenadaPila datosPila;

	/**
	 * Constructor del modelo del mundo con capacidad predefinida
	 */
	public Modelo()
	{
		datosCola = new ListaEncadenadaCola();
		datosPila = new ListaEncadenadaPila();
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
		String dir= "./data/comparendos_dei_2018_small.geojson";
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
			datosPila.insertarComienzo(agregar);
			i++;
		}
		long fin2 = System.nanoTime();
		long fin = System.currentTimeMillis();

		double tiempo = (double) ((fin - inicio)/1000);
		System.out.println((fin2-inicio2)/1.0e9 +" segundos");
		System.out.println(tiempo +" segundos");


	}


	public ListaEncadenadaCola buscarMayorCluster()
	{
		ListaEncadenadaCola mayor= new ListaEncadenadaCola();
		ListaEncadenadaCola actual= new ListaEncadenadaCola();
		int i=0;
		int a=datosCola.darLongitud();
		while(i<a)
		{
			Comparendo c=(Comparendo) datosCola.eliminarComienzo();
			
			if(actual.esListaVacia())
			{
				actual.insertarFinal(c);
			}
			else{


				if(((Comparendo)actual.darUltimo()).getInfraccion().equals(c.getInfraccion()))
				{
					actual.insertarFinal(c);
				}
				else if(!(((Comparendo)actual.darUltimo()).getInfraccion().equals(c.getInfraccion())))
				{
					if(actual.darLongitud()>mayor.darLongitud())
					{
						mayor= actual;
					}
					actual= new ListaEncadenadaCola();
				}
			}
			i++;
		}
		return mayor;

	}

	public ListaEncadenadaCola buscarNcomparendosporInfraccion(int n,String infraccion)
	{
		ListaEncadenadaCola colanueva= new ListaEncadenadaCola();
		int i=0;
		if(datosPila.darLongitud()<n)
		{
			n=datosPila.darLongitud();
		}
		while(i<n)
		{
			Comparendo actual=(Comparendo) datosPila.eliminarComienzo();
			if(actual.getInfraccion().equalsIgnoreCase(infraccion)){
				colanueva.insertarFinal(actual);
			}
			i++;
		}

		return colanueva;
	}


	/**
	 * Busca y retorna un comparendo en la lista con el ID dado.
	 * @param idobject. ID del comparendo.
	 * @return Información básica del comparendo.
	 */

	public String darInfoPorID(int idobject)
	{
		String retorno=null;
		int cont=0;
		boolean encontrado= false;
		while(cont < datosCola.darLongitud() && !encontrado)
		{
			Comparendo c= (Comparendo) datosCola.darObjeto(cont);
			if(c.getId()==idobject)
			{
				retorno="ID ="+c.getId()+" ,Fecha = "+c.getFecha()+" ,Infraccion ="+c.getClasevehi()+" ,Tipo de servicio="+c.getTiposervi() +" ,Localidad="+c.getLocalidad();
				encontrado= true;
			}
			cont++;
		}

		if(retorno==null)
		{
			retorno="No existe información acerca del comparendo con ID = "+idobject;
		}

		return retorno;

	}


	/**
	 * Servicio de consulta de numero de elementos presentes en el modelo 
	 * @return numero de elementos presentes en el modelo
	 */

	public ListaEncadenadaCola getDatosCola() {
		return datosCola;
	}


	public ListaEncadenadaPila getDatosPila() {
		return datosPila;
	}






}


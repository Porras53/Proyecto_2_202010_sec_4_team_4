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

	private final static double LONGITUD_ESTA_POLI=-74.078122;

	private final static int CONS_ASTERISCO=300;

	private final static int CONS_NUMERAL=300;

	private final static int N=20;

	private final static int CONS_PROCESADOS=1500;

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

	public double distance(double startLat, double startLong,
			double endLat, double endLong) {

		double dLat  = Math.toRadians((endLat - startLat));
		double dLong = Math.toRadians((endLong - startLong));

		startLat = Math.toRadians(startLat);
		endLat   = Math.toRadians(endLat);

		double a = haversin(dLat) + Math.cos(startLat) * Math.cos(endLat) * haversin(dLong);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

		return EARTH_RADIUS * c; // <-- d
	}

	public double haversin(double val) {
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
		for(int i=0;i<N && actual != null;i++) 
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

			SimpleDateFormat objSDF2= new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss");

			String nuevo=objSDF2.format(c.getDate());

			Date nuevofinal=null;
			try {
				nuevofinal=objSDF2.parse(nuevo);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 

			if(c.getLocalidad().equalsIgnoreCase(localidad.trim()) && nuevofinal.compareTo(fechaInicial)>=0 && nuevofinal.compareTo(fechaFinal)<=0)
				resp.insertarFinal(c);

		}

		Node actual=resp.darCabeza2();
		for(int i=0;i<N && actual != null;i++) 
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
		ListaDoblementeEncadenada list = new ListaDoblementeEncadenada<Comparendo>();
		
		for (int i = 0; i < n; i++) {
			list.insertarComienzo(maxcola2.eliminarMayor());

		}

		Node actual=list.darCabeza2();
		for(int i=0; i<list.darLongitud() && actual != null;i++) 
		{
			System.out.println(actual.darE().toString());
			actual=actual.darSiguiente();
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
			comparendo.setIndicador(5);
			if(comparendo.getMediodeteccion().equalsIgnoreCase(mediodeteccion.trim()) && comparendo.getClasevehi().equalsIgnoreCase(clasevehi.trim()) && comparendo.getTiposervi().equalsIgnoreCase(tiposervi.trim()) && comparendo.getLocalidad().equalsIgnoreCase(localidad.trim())) 
			{
				maxcola2.agregar(comparendo);
			}
		}

		Node actual=maxcola2.darMax2();
		for(int i=0;i<N && actual != null;i++) 
		{
			System.out.println(actual.darE().toString());
			actual=actual.darSiguiente();
		}

	}
	public void parteBpunto3(double latitudin, double latitudfi) 
	{
		ListaDoblementeEncadenada maxcola2= new ListaDoblementeEncadenada();
		Iterable<KeyComparendo> resultado= datosArbol.keys(datosArbol.min(),datosArbol.max());
		int n=0;
		Iterator<KeyComparendo> iterator= resultado.iterator();
		while(iterator.hasNext() && n<N) 
		{
			KeyComparendo llave= (KeyComparendo) iterator.next();
			Comparendo comparendo=(Comparendo)datosArbol.get(llave);

			if( comparendo.getLatitud()<=latitudfi && comparendo.getLatitud()>=latitudin) {
				System.out.println(comparendo.toString());
				n++;
			}
			
		}



	}

	public void parteCpunto1(int d) 
	{
		HashSeparateChaining tablahash= new HashSeparateChaining();

		Iterable<KeyComparendo> resultado= datosArbol.keys(datosArbol.min(),datosArbol.max());

		Iterator<KeyComparendo> iterator= resultado.iterator();

		while(iterator.hasNext()) 
		{
			KeyComparendo llave= (KeyComparendo) iterator.next();
			Comparendo comparendo=(Comparendo)datosArbol.get(llave);
			tablahash.putInSet(comparendo.getLlave().getFecha(), comparendo);
		}

		int i=0;
		boolean terminado=false;

		String fecha="2018/01/01";
		SimpleDateFormat objSDF2= new SimpleDateFormat("yyyy/MM/dd");

		Date fechai=null;
		try {
			fechai=objSDF2.parse(fecha);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}        

		Date fechaf=null;

		System.out.println("Rango de fechas         | Comparendos durante el año  \n ---------------------------------------");
		while(!terminado) {

			String nuevo=objSDF2.format(fechai);

			String linea=nuevo+"-";

			String[] dma= nuevo.split("/");
			int nuevodia=(Integer.parseInt(dma[2])+(d-1));
			int mes=(Integer.parseInt(dma[1]));

			if(nuevodia>31 && (mes==1 || mes==3 || mes==5 || mes==7 || mes==8 || mes==10)) 
			{
				mes++;
				nuevodia=nuevodia%31;
			}
			else if(nuevodia>30 && (mes==4 || mes==6 || mes==9 || mes==11)) 
			{
				mes++;
				nuevodia=nuevodia%30;
			}
			else if(nuevodia>28 && mes==2)
			{
				mes++;
				nuevodia=nuevodia%28;
			}
			else if(mes==12 && (nuevodia>31)) 
			{
				mes=12;
				nuevodia=31;
			}

			String nuevodiastring=nuevodia+"";
			String nuevomesstring=mes+"";

			if(mes<10) 
			{
				nuevomesstring="0"+nuevomesstring;
			}


			if(nuevodia<10) 
			{
				nuevodiastring="0"+nuevodiastring;
			}

			nuevo=dma[0]+"/"+nuevomesstring+"/"+nuevodiastring;

			linea=linea+nuevo+" | ";
			try {
				fechaf=objSDF2.parse(nuevo);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}                          	

			int cont=0;
			int k=0;
			NodoHash22[] comparendos=tablahash.getNodosSet();
			while(k<tablahash.getTamTotal()) 
			{
				if(comparendos[k]!=null) {
					if((comparendos[k].darE()).compareTo(fechai)>=0 && (comparendos[k].darE()).compareTo(fechaf)<=0) {

						cont+=((ListaDoblementeEncadenada) comparendos[k].darv()).darLongitud();

					}
				}
				k++;
			}

			int canasterisco=cont/CONS_ASTERISCO;

			if(cont % CONS_ASTERISCO !=0) 
			{
				canasterisco++;
			}

			int j=0;
			while(j<canasterisco) 
			{
				linea+="*";
				j++;
			}

			linea+="\n";

			System.out.println(linea);

			/**
			 *  Antes de esto generar los asteriscos.
			 */






			String nuevo1=objSDF2.format(fechaf);

			String[] dma1= nuevo1.split("/");


			if(Integer.parseInt(dma[1])==12 && Integer.parseInt(dma1[2])==31) 

			{
				terminado=true;
			}

			else

			{

				int nuevodia1=(Integer.parseInt(dma1[2])+(1));

				nuevo1=dma1[0]+"/"+dma1[1]+"/"+nuevodia1;
				mes=Integer.parseInt(dma1[1]);

				if(nuevodia1>31 && (mes==1 || mes==3 || mes==5 || mes==7 || mes==8 || mes==10)) 
				{
					mes++;
					nuevodia1=nuevodia1%31;
				}
				else if(nuevodia1>30 && (mes==4 || mes==6 || mes==9 || mes==11)) 
				{
					mes++;
					nuevodia1=nuevodia1%30;
				}
				else if(nuevodia1>28 && mes==2)
				{
					mes++;
					nuevodia1=nuevodia1%28;
				}
				else if(mes==12 && (nuevodia1>31)) 
				{
					mes=12;
					nuevodia1=31;
				}

				nuevodiastring=nuevodia1+"";
				nuevomesstring=mes+"";

				if(mes<10) 
				{
					nuevomesstring="0"+nuevomesstring;
				}


				if(nuevodia<10) 
				{
					nuevodiastring="0"+nuevodiastring;
				}

				nuevo1=dma[0]+"/"+nuevomesstring+"/"+nuevodiastring;

				try {
					fechai=objSDF2.parse(nuevo1);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}



		}


	}

	public void parteCpunto2() 
	{
		/**
		 * Carga en una tabla de hash
		 */

		HashSeparateChaining tablahash= new HashSeparateChaining();

		Iterable<KeyComparendo> resultado= datosArbol.keys(datosArbol.min(),datosArbol.max());

		Iterator<KeyComparendo> iterator= resultado.iterator();

		while(iterator.hasNext()) 
		{
			KeyComparendo llave= (KeyComparendo) iterator.next();
			Comparendo comparendo=(Comparendo)datosArbol.get(llave);
			tablahash.putInSet(comparendo.getLlave().getFecha(), comparendo);
		}

		/**
		 * Recorrer la tabla para cada día.
		 */
		System.out.println("Fecha    | Comparendos procesado *** \n         | Comparendos que estan en espera  ### \n ---------------------------------------------- \n");



		String fecha="2018/01/01";

		SimpleDateFormat objSDF2= new SimpleDateFormat("yyyy/MM/dd");

		Date fechai=null;
		try {
			fechai=objSDF2.parse(fecha);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}        
		boolean terminado=false;

		int diapromde4=0;
		int diaminde4=1000;
		int diamaxde4=0;
		int numprocesados4=0;

		int diapromde40=0;
		int diaminde40=1000;
		int diamaxde40=0;
		int numprocesados40=0;

		int diapromde400=0;
		int diaminde400=1000;
		int diamaxde400=0;
		int numprocesados400=0;

		int numpromdediasespera=0;
		int penalizacionestotal=0;

		int numprocesadostotal=0;


		ListaDoblementeEncadenada coladeespera= new ListaDoblementeEncadenada();

		while(!terminado) {

			String[] dma2= objSDF2.format(fechai).split("/");
			int diahoy=(Integer.parseInt(dma2[2]));
			int meshoy=(Integer.parseInt(dma2[1]));

			String retorno=objSDF2.format(fechai)+" | ";


			/**
			 * Procesar los 1500 comparendos del dia.
			 */
			int cantprocehoy=0;
			boolean finish=false;
			int u=0;

			while(u<CONS_PROCESADOS && !finish) 
			{

				Comparendo c=(Comparendo)coladeespera.eliminarComienzo();
				if(c!=null) 
				{
					/**
					 * falta hacer el calculo luego del posible vencimiento
					 */
					String[] dmy= objSDF2.format(c.getLlave().getFecha()).split("/");
					int diacompa=(Integer.parseInt(dmy[2]));
					int mescompa=(Integer.parseInt(dmy[1]));
					
					int diasretrasado=(int) ((fechai.getTime()-c.getLlave().getFecha().getTime())/86400000);;

					

					/**
					 * Encontrar el tipo de penalizacion.
					 */
					int multipena=0; 
					if(diasretrasado!=0) 
					{
						String descompa=c.getDesinfraccion();
						if(descompa.contains("SERA INMOVILIZADO") || (descompa.contains("SER") && (descompa.contains(" INMOVILIZADO")))  ) 

						{
							multipena=400;
							if(diasretrasado>diamaxde400) 
							{
								diamaxde400=diasretrasado;
							}
							if(diasretrasado<diaminde400) 
							{
								diaminde400=diasretrasado;
							}

							diapromde400+=diasretrasado;
							numprocesados400++;
						}

						else if(descompa.contains("LICENCIA DE CONDUCCIÃ“N")) 

						{
							multipena=40;
							if(diasretrasado>diamaxde40) 
							{
								diamaxde40=diasretrasado;
							}
							if(diasretrasado<diaminde40) 
							{
								diaminde40=diasretrasado;
							}

							diapromde40+=diasretrasado;
							numprocesados40++;
						}

						else

						{
							multipena=4;
							if(diasretrasado>diamaxde4) 
							{
								diamaxde4=diasretrasado;
							}
							if(diasretrasado<diaminde4) 
							{
								diaminde4=diasretrasado;
							}
							diapromde4+=diasretrasado;
							numprocesados4++;
						}

					}


					penalizacionestotal+=multipena*diasretrasado;
					numprocesadostotal++;
					cantprocehoy++;
				}
				else 
				{
					finish=true;
				}


				u++;
			}

			/**
			 * Contabilizacion de comparendos procesados y en cola 
			 */
			int cont=0;
			int k=tablahash.hash(fechai);
			NodoHash22[] comparendos=tablahash.getNodosSet();


			cont+=((ListaDoblementeEncadenada) comparendos[k].darv()).darLongitud();
			Node actual1= ((ListaDoblementeEncadenada)comparendos[k].darv()).darCabeza2();
			while(actual1!=null) 
			{
				/**
				 * Poner en espera los nmuevos comparendos del día.
				 */
				coladeespera.insertarFinal(actual1.darE());
				actual1=actual1.darSiguiente();
			}

			if(cantprocehoy<CONS_PROCESADOS) 
			{
				/*
				 * PROCESARLOS FALTANTES DEL DIA...
				 */
				boolean finish1=false;
				int restante=CONS_PROCESADOS-cantprocehoy;
				int p=0;

				while(p<restante && !finish1) 
				{
					Comparendo c1=(Comparendo)coladeespera.eliminarComienzo();
					if(c1!=null) 
					{
						/**
						 * falta hacer el calculo luego del posible vencimiento
						 */
						String[] dmy= objSDF2.format(c1.getLlave().getFecha()).split("/");
						int diacompa=(Integer.parseInt(dmy[2]));
						int mescompa=(Integer.parseInt(dmy[1]));

						int diasretrasado=(int) ((fechai.getTime()-c1.getLlave().getFecha().getTime())/86400000);;


						/**
						 * Encontrar el tipo de penalizacion.
						 */
						int multipena=0; 
						if(diasretrasado!=0) 
						{

							String descompa=c1.getDesinfraccion();
							if(descompa.contains("SERA INMOVILIZADO") || (descompa.contains("SER") && (descompa.contains(" INMOVILIZADO")))  ) 

							{
								multipena=400;
								if(diasretrasado>diamaxde400) 
								{
									diamaxde400=diasretrasado;
								}
								if(diasretrasado<diaminde400) 
								{
									diaminde400=diasretrasado;
								}

								diapromde400+=diasretrasado;
								numprocesados400++;
							}

							else if(descompa.contains("LICENCIA DE CONDUCCIÃ“N")) 

							{
								multipena=40;
								if(diasretrasado>diamaxde40) 
								{
									diamaxde40=diasretrasado;
								}
								if(diasretrasado<diaminde40) 
								{
									diaminde40=diasretrasado;
								}

								diapromde40+=diasretrasado;
								numprocesados40++;
							}

							else

							{
								multipena=4;
								if(diasretrasado>diamaxde4) 
								{
									diamaxde4=diasretrasado;
								}
								if(diasretrasado<diaminde4) 
								{
									diaminde4=diasretrasado;
								}
								diapromde4+=diasretrasado;
								numprocesados4++;
							}
						}



						penalizacionestotal+=multipena*diasretrasado;
						numprocesadostotal++;
						cantprocehoy++;
					}
					else 
					{
						finish1=true;
					}

					p++;
				}

			}



			int numerodeasteriscos=cantprocehoy/CONS_ASTERISCO;
			if(cantprocehoy%CONS_ASTERISCO !=0) 
			{
				numerodeasteriscos++;
			}

			for(int y=0;y<numerodeasteriscos;y++) 
			{
				retorno+="*";
			}

			retorno+="\n           | ";

			int numerodenumerales=coladeespera.darLongitud()/CONS_NUMERAL;
			if(coladeespera.darLongitud()%CONS_NUMERAL !=0) 
			{
				numerodenumerales++;
			}


			for(int y=0;y<numerodenumerales;y++) 
			{
				retorno+="#";
			}

			retorno+="\n";

			System.out.println(retorno);

			String nuevo=objSDF2.format(fechai);


			String[] dma= nuevo.split("/");
			int nuevodia=(Integer.parseInt(dma[2])+(1));
			int mes=(Integer.parseInt(dma[1]));

			if(nuevodia>31 && (mes==1 || mes==3 || mes==5 || mes==7 || mes==8 || mes==10)) 
			{
				mes++;
				nuevodia=nuevodia%31;
			}
			else if(nuevodia>30 && (mes==4 || mes==6 || mes==9 || mes==11)) 
			{
				mes++;
				nuevodia=nuevodia%30;
			}
			else if(nuevodia>28 && mes==2)
			{
				mes++;
				nuevodia=nuevodia%28;
			}
			else if(mes==12 && (nuevodia>31)) 
			{
				mes=12;
				nuevodia=31;
				terminado=true;
			}

			String nuevodiastring=nuevodia+"";
			String nuevomesstring=mes+"";

			if(mes<10) 
			{
				nuevomesstring="0"+nuevomesstring;
			}


			if(nuevodia<10) 
			{
				nuevodiastring="0"+nuevodiastring;
			}

			nuevo=dma[0]+"/"+nuevomesstring+"/"+nuevodiastring;

			try {
				fechai=objSDF2.parse(nuevo);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}


		System.out.println("El costo total que generan las penalizaciones en 2018 es= "+penalizacionestotal+" (COP)");
		System.out.println("El numero de 4: "+numprocesados4);
		System.out.println("El numero de 40: "+numprocesados40);
		System.out.println("El numero de 400: "+numprocesados400);
		System.out.println("El numero de dias en promedio que debe esperar un comparendo es= "+numpromdediasespera/numprocesadostotal);

		System.out.println("Costo diario del comparendo | Tiempo mínimo de espera(días) | Tiempo promedio de espera(días) | Tiempo máximo de espera(días)");
		System.out.println("$400                        | "+diaminde400+"                                           | "+diapromde400/numprocesados400+"                                           | "+diamaxde400);
		System.out.println("$40                         | "+diaminde40+"                                           | "+diapromde40/numprocesados40+"                                           | "+diamaxde40);
		System.out.println("$4                          | "+diaminde4+"                                           | "+diapromde4/numprocesados4+"                                           | "+diamaxde4);


	}
	
	
	
	
	
	
	
	
	

	public void parteCpunto3() 
	{
		/**
		 * Carga en una tabla de hash
		 */

		HashSeparateChaining tablahash= new HashSeparateChaining();

		Iterable<KeyComparendo> resultado= datosArbol.keys(datosArbol.min(),datosArbol.max());

		Iterator<KeyComparendo> iterator= resultado.iterator();

		while(iterator.hasNext()) 
		{
			KeyComparendo llave= (KeyComparendo) iterator.next();
			Comparendo comparendo=(Comparendo)datosArbol.get(llave);
			tablahash.putInSet(comparendo.getLlave().getFecha(), comparendo);
		}

		/**
		 * Recorrer la tabla para cada día.
		 */
		System.out.println("Fecha    | Comparendos procesado *** \n         | Comparendos que estan en espera  ### \n ---------------------------------------------- \n");



		String fecha="2018/01/01";

		SimpleDateFormat objSDF2= new SimpleDateFormat("yyyy/MM/dd");

		Date fechai=null;
		try {
			fechai=objSDF2.parse(fecha);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}        
		boolean terminado=false;

		int diapromde4=0;
		int diaminde4=1000;
		int diamaxde4=0;
		int numprocesados4=0;

		int diapromde40=0;
		int diaminde40=1000;
		int diamaxde40=0;
		int numprocesados40=0;

		int diapromde400=0;
		int diaminde400=1000;
		int diamaxde400=0;
		int numprocesados400=0;

		int numpromdediasespera=0;
		int penalizacionestotal=0;

		int numprocesadostotal=0;


		MaxColaCP coladeespera= new MaxColaCP();

		while(!terminado) {

			String[] dma2= objSDF2.format(fechai).split("/");
			int diahoy=(Integer.parseInt(dma2[2]));
			int meshoy=(Integer.parseInt(dma2[1]));

			String retorno=objSDF2.format(fechai)+" | ";


			/**
			 * Procesar los 1500 comparendos del dia.
			 */
			int cantprocehoy=0;
			boolean finish=false;
			int u=0;

			while(u<CONS_PROCESADOS && !coladeespera.esVacia()) 
			{
				Comparendo c=(Comparendo) coladeespera.sacarMax();
				/**
				 * falta hacer el calculo luego del posible vencimiento
				 */
				String[] dmy= objSDF2.format(c.getLlave().getFecha()).split("/");
				int diacompa=(Integer.parseInt(dmy[2]));
				int mescompa=(Integer.parseInt(dmy[1]));

				int diasretrasado=(int) ((fechai.getTime()-c.getLlave().getFecha().getTime())/86400000);

				numpromdediasespera+=diasretrasado;

				

				/**
				 * Encontrar el tipo de penalizacion.
				 */
				int multipena=0; 
				if(diasretrasado!=0) 
				{

					String descompa=c.getDesinfraccion();
					if(descompa.contains("SERA INMOVILIZADO") || (descompa.contains("SER") && (descompa.contains(" INMOVILIZADO")))  ) 

					{
						multipena=400;
						if(diasretrasado>diamaxde400) 
						{
							diamaxde400=diasretrasado;
						}
						if(diasretrasado<diaminde400) 
						{
							diaminde400=diasretrasado;
						}

						diapromde400+=diasretrasado;
						numprocesados400++;
					}

					else if(descompa.contains("LICENCIA DE CONDUCCIÃ“N")) 

					{
						multipena=40;
						if(diasretrasado>diamaxde40) 
						{
							diamaxde40=diasretrasado;
						}
						if(diasretrasado<diaminde40) 
						{
							diaminde40=diasretrasado;
						}

						diapromde40+=diasretrasado;
						numprocesados40++;
					}

					else

					{
						multipena=4;
						if(diasretrasado>diamaxde4) 
						{
							diamaxde4=diasretrasado;
						}
						if(diasretrasado<diaminde4) 
						{
							diaminde4=diasretrasado;
						}
						diapromde4+=diasretrasado;
						numprocesados4++;
					}
				}



				penalizacionestotal+=multipena*diasretrasado;
				numprocesadostotal++;
				cantprocehoy++;


				u++;
			}

			/**
			 * Contabilizacion de comparendos procesados y en cola 
			 */
			int cont=0;
			int k=tablahash.hash(fechai);
			NodoHash22[] comparendos=tablahash.getNodosSet();


			cont+=((ListaDoblementeEncadenada) comparendos[k].darv()).darLongitud();
			Node actual1= ((ListaDoblementeEncadenada)comparendos[k].darv()).darCabeza2();
			while(actual1!=null) 
			{
				/**
				 * Poner en espera los nmuevos comparendos del día.
				 */
				Integer llave=4;
				String descompaagregar=((Comparendo)actual1.darE()).getDesinfraccion();
				if(descompaagregar.contains("SERA INMOVILIZADO") || (descompaagregar.contains("SER") && (descompaagregar.contains(" INMOVILIZADO")))  ) 

				{
					llave=400;
				}

				else if(descompaagregar.contains("LICENCIA DE CONDUCCIÃ“N")) 

				{
					llave=40;
				}
				
				((Comparendo)actual1.darE()).setIndicador(4);
				((Comparendo)actual1.darE()).setPenacompa(llave);
				coladeespera.agregar(actual1.darE());
				actual1=actual1.darSiguiente();
			}

			if(cantprocehoy<CONS_PROCESADOS) 
			{
				/*
				 * PROCESARLOS FALTANTES DEL DIA...
				 */
				boolean finish1=false;
				int restante=CONS_PROCESADOS-cantprocehoy;
				int p=0;

				while(p<restante && !coladeespera.esVacia()) 
				{
						
						Comparendo c1=(Comparendo) coladeespera.sacarMax();
					
						/**
						 * falta hacer el calculo luego del posible vencimiento
						 */
						String[] dmy= objSDF2.format(c1.getLlave().getFecha()).split("/");
						int diacompa=(Integer.parseInt(dmy[2]));
						int mescompa=(Integer.parseInt(dmy[1]));

						int diasretrasado=(int) ((fechai.getTime()-c1.getLlave().getFecha().getTime())/86400000);

						numpromdediasespera+=diasretrasado;

						

						/**
						 * Encontrar el tipo de penalizacion.
						 */
						int multipena=0; 
						if(diasretrasado!=0) 
						{

							String descompa=c1.getDesinfraccion();
							if(descompa.contains("SERA INMOVILIZADO") || (descompa.contains("SER") && (descompa.contains(" INMOVILIZADO")))  ) 

							{
								multipena=400;
								if(diasretrasado>diamaxde400) 
								{
									diamaxde400=diasretrasado;
								}
								if(diasretrasado<diaminde400) 
								{
									diaminde400=diasretrasado;
								}

								diapromde400+=diasretrasado;
								numprocesados400++;
							}

							else if(descompa.contains("LICENCIA DE CONDUCCIÃ“N")) 

							{
								multipena=40;
								if(diasretrasado>diamaxde40) 
								{
									diamaxde40=diasretrasado;
								}
								if(diasretrasado<diaminde40) 
								{
									diaminde40=diasretrasado;
								}

								diapromde40+=diasretrasado;
								numprocesados40++;
							}

							else

							{
								multipena=4;
								if(diasretrasado>diamaxde4) 
								{
									diamaxde4=diasretrasado;
								}
								if(diasretrasado<diaminde4) 
								{
									diaminde4=diasretrasado;
								}
								diapromde4+=diasretrasado;
								numprocesados4++;
							}
						}



						penalizacionestotal+=multipena*diasretrasado;
						numprocesadostotal++;
						cantprocehoy++;
						p++;
				}

			}



			int numerodeasteriscos=cantprocehoy/CONS_ASTERISCO;
			if(cantprocehoy%CONS_ASTERISCO !=0) 
			{
				numerodeasteriscos++;
			}

			for(int y=0;y<numerodeasteriscos;y++) 
			{
				retorno+="*";
			}

			retorno+="\n           | ";

			int numerodenumerales=coladeespera.darNumElementos()/CONS_NUMERAL;
			if(coladeespera.darNumElementos()%CONS_NUMERAL !=0) 
			{
				numerodenumerales++;
			}


			for(int y=0;y<numerodenumerales;y++) 
			{
				retorno+="#";
			}

			retorno+="\n";

			System.out.println(retorno);

			String nuevo=objSDF2.format(fechai);


			String[] dma= nuevo.split("/");
			int nuevodia=(Integer.parseInt(dma[2])+(1));
			int mes=(Integer.parseInt(dma[1]));

			if(nuevodia>31 && (mes==1 || mes==3 || mes==5 || mes==7 || mes==8 || mes==10)) 
			{
				mes++;
				nuevodia=nuevodia%31;
			}
			else if(nuevodia>30 && (mes==4 || mes==6 || mes==9 || mes==11)) 
			{
				mes++;
				nuevodia=nuevodia%30;
			}
			else if(nuevodia>28 && mes==2)
			{
				mes++;
				nuevodia=nuevodia%28;
			}
			else if(mes==12 && (nuevodia>31)) 
			{
				mes=12;
				nuevodia=31;
				terminado=true;
			}

			String nuevodiastring=nuevodia+"";
			String nuevomesstring=mes+"";

			if(mes<10) 
			{
				nuevomesstring="0"+nuevomesstring;
			}


			if(nuevodia<10) 
			{
				nuevodiastring="0"+nuevodiastring;
			}

			nuevo=dma[0]+"/"+nuevomesstring+"/"+nuevodiastring;

			try {
				fechai=objSDF2.parse(nuevo);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}


		System.out.println("El costo total que generan las penalizaciones en 2018 es= "+penalizacionestotal+" (COP)");
		System.out.println("El numero de 4: "+numprocesados4);
		System.out.println("El numero de 40: "+numprocesados40);
		System.out.println("El numero de 400: "+numprocesados400);
		System.out.println("El numero de dias en promedio que debe esperar un comparendo es= "+numpromdediasespera/numprocesadostotal);

		System.out.println("Costo diario del comparendo | Tiempo mínimo de espera(días) | Tiempo promedio de espera(días) | Tiempo máximo de espera(días)");
		System.out.println("$400                        | "+diaminde400+"                                           | "+diapromde400/numprocesados400+"                                           | "+diamaxde400);
		System.out.println("$40                         | "+diaminde40+"                                           | "+diapromde40/numprocesados40+"                                           | "+diamaxde40);
		System.out.println("$4                          | "+diaminde4+"                                           | "+diapromde4/numprocesados4+"                                           | "+diamaxde4);




	}



}


package model.logic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Comparendo implements Comparable<Comparendo>{

	private int id;
	private Date fecha;
	private String mediodeteccion;
	private String clasevehi;
	private String tiposervi;
	private String infraccion;
	private String desinfraccion;
	private String localidad;
	private String municipio;
	private double longitud;
	private double latitud;
	private double distanciaconestacion;
	private KeyComparendo llave;
	private int indicador;
	private int penacompa;

	public Comparendo(int id,String fecha,String mediodeteccion ,String clasevehiculo,String tiposervicio,String infraccion,String desinfraccion,String localidad,String municipio ,double longitud,double latitud,double distanciaconestacion)
	{
		this.id= id;
		SimpleDateFormat objSDF= new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		try {

			this.fecha=objSDF.parse(fecha);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.mediodeteccion=mediodeteccion;
		this.clasevehi=clasevehiculo;
		this.tiposervi=tiposervicio;
		this.infraccion=infraccion;
		this.desinfraccion=desinfraccion;
		this.localidad=localidad;
		this.municipio=municipio;
		this.longitud=longitud;
		this.latitud=latitud;
		this.distanciaconestacion=distanciaconestacion;
		this.indicador=0;
		this.penacompa=0;

		SimpleDateFormat objSDF2= new SimpleDateFormat("yyyy/MM/dd");

		String nuevo=objSDF2.format(this.fecha);

		Date nuevofinal=null;
		try {
			nuevofinal=objSDF2.parse(nuevo);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}                                                                                                                          
		this.llave= new KeyComparendo(id,nuevofinal,this.clasevehi,this.infraccion);
	}


	public String toString3() {
		return "Comparendo [id=" + id + ", fecha=" + fecha.toString() + ", clase de vehiculo=" + clasevehi + ", tipo de servicio=" + tiposervi
				+ ", infraccion=" + infraccion + ", descripcion de infraccion=" + desinfraccion + ", localidad=" + localidad
				+" Municipio="+ municipio +", coordenadas=" +"Longitud= "+longitud +", Latitud= "+latitud + "]";
	}
	public String toString2()
	{
		return "Comparendo [ infraccion=" + infraccion+" , id=" + id + ", fecha=" + fecha.toString() + ", clase de vehiculo=" + clasevehi + ", tipo de servicio=" + tiposervi
				+ ", localidad=" + localidad +"]";
	}
	@Override
	public String toString()
	{
		return "Comparendo "+": id= "+id+ " ,fecha y hora= "+fecha.toString()+" ,localidad=" +localidad+" , infraccion= "+infraccion+ " Tipo Vehiculo="+clasevehi + ", tipo de servicio=" + tiposervi+ ", latitud="+latitud+" , longitud="+longitud +", distancia con esta="+distanciaconestacion;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public int getIndicador()
	{
		return indicador;
	}
	public void setIndicador(int indicador) {
		this.indicador=indicador;
	}
	public String getFecha() {
		return fecha.toString();
	}

	public void setFecha(String fecha) {
		SimpleDateFormat objSDF= new SimpleDateFormat("aaaa-MM-dd T HH: mm: ss.000Z");
		try {
			this.fecha=objSDF.parse(fecha);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    public Date getDate()
    {
    	return fecha;
    }
	public String getClasevehi() {
		return clasevehi;
	}

	public void setClasevehi(String clasevehi) {
		this.clasevehi = clasevehi;
	}

	public String getTiposervi() {
		return tiposervi;
	}

	public void setTiposervi(String tiposervi) {
		this.tiposervi = tiposervi;
	}

	public String getInfraccion() {
		return infraccion;
	}

	public void setInfraccion(String infraccion) {
		this.infraccion = infraccion;
	}

	public String getDesinfraccion() {
		return desinfraccion;
	}

	public void setDesinfraccion(String desinfraccion) {
		this.desinfraccion = desinfraccion;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	@Override
	public int compareTo(Comparendo compa) {
		// TODO Auto-generated method stub
		if(indicador==1)
		{
            if(tiposervi.equals(compa.getTiposervi()))
            {
            	return infraccion.compareTo(compa.getInfraccion());
            }
            else if(tiposervi.equals("Publico"))
            {
            	return 1;
            }
            else if(tiposervi.equals("Particular"))
            {
            	return -1;
            }
            else if(tiposervi.equals("Oficial")&& compa.getTiposervi().equals("Publico"))	
            {
	             return -1; 
            }
            else
            {
            	return 1;
            }
		}
		else if(indicador==2) 
		{	
			int retorno=0;
			if(distanciaconestacion<compa.getDistanciaconestacion()) 
			{
				retorno=-1;
			}
			else if(distanciaconestacion>compa.getDistanciaconestacion())
			{
				retorno=1;
			}
				
			return retorno;
		}
		else if(indicador==3) 
		{
			int retorno=0;
			if(fecha.compareTo(compa.getDate())>0) 
			{
				retorno=1;
			}
			else if(fecha.compareTo(compa.getDate())<0 ) 
			{
				retorno=-1;
			}
			
			return retorno;
		}
		else if(indicador==4) 
		{
			int retorno=0;
			if(penacompa>compa.getPenacompa()) 
			{
				retorno=1;
			}
			else if(penacompa<compa.getPenacompa() ) 
			{
				retorno=-1;
			}
			return retorno;
		}
		else if(indicador==5)
		{
			int retorno=0;
			if(fecha.compareTo(compa.getDate())>0) 
			{
				retorno=-1;
			}
			else if(fecha.compareTo(compa.getDate())<0 ) 
			{
				retorno=1;
			}
			
			return retorno;
		}
		else
		{
			if(id > compa.id)
				return 1;
			else if(id == compa.id ) {
				return 0;
			} else {
				return -1;
			}
		}
		
	}

	public String getMediodeteccion() {
		return mediodeteccion;
	}

	public void setMediodeteccion(String mediodeteccion) {
		this.mediodeteccion = mediodeteccion;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}


	public KeyComparendo getLlave() {
		return llave;
	}


	public void setLlave(KeyComparendo llave) {
		this.llave = llave;
	}


	public double getDistanciaconestacion() {
		return distanciaconestacion;
	}


	public void setDistanciaconestacion(double distanciaconestacion) {
		this.distanciaconestacion = distanciaconestacion;
	}


	public double getLatitud() {
		return latitud;
	}


	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}


	public int getPenacompa() {
		return penacompa;
	}


	public void setPenacompa(int penacompa) {
		this.penacompa = penacompa;
	}


}

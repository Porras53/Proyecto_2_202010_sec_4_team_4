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
	
	public Comparendo(int id,String fecha,String mediodeteccion ,String clasevehiculo,String tiposervicio,String infraccion,String desinfraccion,String localidad,String municipio ,double longitud,double latitud)
	{
		this.id= id;
		SimpleDateFormat objSDF= new SimpleDateFormat("yyyy/MM/dd");
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
	}

	@Override
	public String toString() {
		return "Comparendo [id=" + id + ", fecha=" + fecha.toString() + ", clase de vehiculo=" + clasevehi + ", tipo de servicio=" + tiposervi
				+ ", infraccion=" + infraccion + ", descripcion de infraccion=" + desinfraccion + ", localidad=" + localidad
				+" Municipio="+ municipio +", coordenadas=" +"Longitud= "+longitud +", Latitud= "+latitud + "]";
	}
	public String toString2()
	{
		return "Comparendo [ infraccion=" + infraccion+" , id=" + id + ", fecha=" + fecha.toString() + ", clase de vehiculo=" + clasevehi + ", tipo de servicio=" + tiposervi
				 + ", localidad=" + localidad +"]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
		
		
		return (int) (latitud - compa.latitud);
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



	
	
}

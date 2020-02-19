package model.logic;

public class Comparendo implements Comparable<Comparendo>{

	private int id;
	private String fecha;
	private String clasevehi;
	private String tiposervi;
	private String infraccion;
	private String desinfraccion;
	private String localidad;
	private double longitud;
	private double latitud;
	
	public Comparendo(int id,String fecha, String clasevehiculo,String tiposervicio,String infraccion,String desinfraccion,String localidad, double longitud,double latitud)
	{
		this.id= id;
		this.fecha=fecha;
		this.clasevehi=clasevehiculo;
		this.tiposervi=tiposervicio;
		this.infraccion=infraccion;
		this.desinfraccion=desinfraccion;
		this.localidad=localidad;
		this.longitud=longitud;
		this.latitud=latitud;
	}

	@Override
	public String toString() {
		return "Comparendo [id=" + id + ", fecha=" + fecha + ", clase de vehiculo=" + clasevehi + ", tipo de servicio=" + tiposervi
				+ ", infraccion=" + infraccion + ", descripcion de infraccion=" + desinfraccion + ", localidad=" + localidad
				+ ", coordenadas=" +"Longitud= "+longitud +", Latitud= "+latitud + "]";
	}
	public String toString2()
	{
		return "Comparendo [ infraccion=" + infraccion+" , id=" + id + ", fecha=" + fecha + ", clase de vehiculo=" + clasevehi + ", tipo de servicio=" + tiposervi
				 + ", localidad=" + localidad +"]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
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
		String[] datosreal=fecha.split("/");
		String[] datos= compa.getFecha().split("/");

		int mes1=Integer.parseInt(datosreal[1]);
		int mes=Integer.parseInt(datos[1]);
		int dia1= Integer.parseInt(datosreal[2]);
		int dia= Integer.parseInt(datos[2]);
		
		int retorno=0;
		
		if(mes1>mes){ retorno=1;}
		else if(mes1<mes) {retorno=-1;}
		else if(dia1>dia) {retorno=1;}
		else if(dia1<dia) {retorno=-1;}
		
		if(retorno==0)
		{
			if(id>compa.getId()) {retorno=1;}
			else if(id<compa.getId()) {retorno=-1;}
		}
		
		return retorno;
	}



	
	
}

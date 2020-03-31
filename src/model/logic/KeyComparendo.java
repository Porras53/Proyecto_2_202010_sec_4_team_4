package model.logic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class KeyComparendo implements Comparable<KeyComparendo>{

	private int id;
	private Date fecha;
	private String clasevehiculo;
	private String infraccion;

	
	public KeyComparendo(int i,Date f,String cv,String in) 
	{
		id=i;
		fecha=f;
		clasevehiculo=cv;
		infraccion=in;
	}
	
	@Override
	public boolean equals(Object k) 
	{
		KeyComparendo k2=(KeyComparendo) k;
		return fecha.equals(k2.getFecha()) && clasevehiculo.equalsIgnoreCase(k2.getClasevehiculo()) && infraccion.equalsIgnoreCase(k2.getInfraccion());
	}
	

	
	public int hashCode() 
	{
		int hash=17;
		hash=(31*hash)+fecha.hashCode();
		hash=(31*hash)+clasevehiculo.hashCode();
		hash=(31*hash)+infraccion.hashCode();
		return hash;
		
	}
	
	
	@Override
	public int compareTo(KeyComparendo o) {
		int retorno=0;
		
		if(id>o.getId()) 
		{
			retorno=1;
		}
		else if(id<o.getId()) 
		{
			retorno=-1;
		}
		return retorno;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getClasevehiculo() {
		return clasevehiculo;
	}

	public void setClasevehiculo(String clasevehiculo) {
		this.clasevehiculo = clasevehiculo;
	}

	public String getInfraccion() {
		return infraccion;
	}

	public void setInfraccion(String infraccion) {
		this.infraccion = infraccion;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}

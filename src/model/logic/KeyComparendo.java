package model.logic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class KeyComparendo implements Comparable<KeyComparendo>{

	private Date fecha;
	private String clasevehiculo;
	private String infraccion;

	
	public KeyComparendo(Date f,String cv,String in) 
	{
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
		// TODO Auto-generated method stub
		return 0;
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

}

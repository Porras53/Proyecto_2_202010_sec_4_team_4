package model.logic;

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
	
	public boolean equals(KeyComparendo k) 
	{
		return fecha.equals(fecha) && clasevehiculo.equalsIgnoreCase(clasevehiculo) && infraccion.equalsIgnoreCase(infraccion);
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

package test.data_structures;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import model.data_structures.MaxColaCP;

public class TestMaxCola {

public MaxColaCP<Integer> cola;
	
	@Before
	public void setupScenario() {
		cola = new MaxColaCP<Integer>();
		cola.agregar(2);
		cola.agregar(1);
		cola.agregar(5);
		cola.agregar(8);
	}
	
	@Test
	public void testAgregarCola() {
		assertNotNull("La cola no deberia ser nula", cola);
		assertEquals("El tamaño de la cola no es el esperado", 4, cola.darNumElementos());
	}
	
	@Test
	public void testdarMax() {
		assertEquals("El valor no es el esperado",new Integer(8), cola.darMax());
	}
	
	@Test
	public void testSacarMax() {
		cola.agregar(11);
		
		assertEquals("El valor no es el esperado",new Integer(11), cola.sacarMax());
		
		
		assertEquals("El valor no es el esperado",4, cola.darNumElementos());
		
		cola.sacarMax();
		
		
	}
	
	
}

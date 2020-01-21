package test.logic;

import static org.junit.Assert.*;
import model.logic.MVCModelo;

import org.junit.Before;
import org.junit.Test;

public class TestMVCModelo {
	
	private MVCModelo modelo;
	private static int CAPACIDAD=100;
	
	@Before
	public void setUp1() {
		modelo= new MVCModelo(CAPACIDAD);
	}

	public void setUp2() {
		for(int i =0; i< CAPACIDAD;i++){
			modelo.agregar(""+i);
		}
	}

	@Test
	public void testMVCModelo() {
		assertTrue(modelo!=null);
		assertEquals(0, modelo.darTamano());  // Modelo con 0 elementos presentes.
	}

	@Test
	public void testDarTamano() {
		assertEquals(0, modelo.darTamano());  // Modelo con 0 elementos presentes.
	}

	@Test
	public void testAgregar() {
		// TODO Completar la prueba
		
	}

	@Test
	public void testBuscar() {
		setUp2();
		// TODO Completar la prueba
	}

	@Test
	public void testEliminar() {
		setUp2();
		// TODO Completar la prueba
		
	}

}

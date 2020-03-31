package test.data_structures;

import static org.junit.Assert.*;

import model.data_structures.HashLinearProbing;
import model.data_structures.HashSeparateChaining;
import model.logic.Modelo;

import org.junit.Before;
import org.junit.Test;

public class TestHashSeparateChaining {

	
	private Modelo modelo;
	private HashSeparateChaining separate;
	private static int CAPACIDAD=100;
	
	@Before
	public void setUp1() {
		separate= new HashSeparateChaining();
		
		for(Integer i=0;i<CAPACIDAD;i++) 
		{
			separate.putInSet(i, i);
		}
	}
	
	
	@Test
	public void testSize() {
		// TODO
		assertEquals(100,separate.getTamActual());
	}
	@Test
	public void testEmpty() {
		// TODO
		assertFalse(separate.getNodosSet()==null);
	}
	@Test
	public void testGet() {
		// TODO
		assertNotNull(separate.getSet(20));
	}
	
	@Test
	public void testPut() {
		// TODO
		Integer i=20;
		Integer f=500;
		assertEquals(20,separate.getSet(20).darCabeza());
		separate.putInSet(i, f);
		assertEquals(101,separate.getTamActual());
		assertNotNull(separate.getSet(20));
		assertEquals(500,separate.getSet(20).darCabeza());
		assertEquals(20,separate.getSet(20).darCabeza2().darSiguiente().darE());
		
	}
	
	@Test
	public void testDelete() {
		// TODO
		Integer nuevo=50;
		separate.deleteSet(nuevo);
		assertEquals(99,separate.getTamActual());
	}
	
	
	
	
}

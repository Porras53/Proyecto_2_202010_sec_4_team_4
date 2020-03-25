package test.data_structures;

import static org.junit.Assert.*;

import model.data_structures.HashLinearProbing;
import model.logic.Modelo;

import org.junit.Before;
import org.junit.Test;

public class TestHashLinearProbing {

	
	private Modelo modelo;
	private HashLinearProbing linear;
	private static int CAPACIDAD=100;
	
	@Before
	public void setUp1() {
		linear= new HashLinearProbing();
		
		for(Integer i=0;i<CAPACIDAD;i++) 
		{
			linear.put(i, i);
		}
	}
	
	
	@Test
	public void testSize() {
		// TODO
		assertEquals(100,linear.size());
	}
	@Test
	public void testEmpty() {
		// TODO
		assertFalse(linear.isEmpty());
	}
	@Test
	public void testContains() {
		// TODO
		assertTrue(linear.contains(20));
	}
	
	@Test
	public void testPut() {
		// TODO
		Integer nuevo= 101;
		linear.put(nuevo, nuevo);
		assertEquals(101,linear.size());
		assertTrue(linear.contains(101));
	}
	
	@Test
	public void testGet() {
		// TODO
		Integer nuevo= 50;
		assertEquals(50,linear.get(nuevo));
	}
	
	@Test
	public void testDelete() {
		// TODO
		Integer nuevo= 50;
		assertEquals(50,linear.delete(nuevo));
		assertEquals(99,linear.size());
	}
	
	
	
	
}

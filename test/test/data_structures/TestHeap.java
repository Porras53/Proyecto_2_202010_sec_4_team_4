package test.data_structures;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import model.data_structures.MaxHeapCP;

public class TestHeap {

	public MaxHeapCP<Integer> heap;
	
	@Before
	public void setupScenario() {
		heap = new MaxHeapCP<Integer>();
		heap.agregar(2);
		heap.agregar(1);
		heap.agregar(5);
		heap.agregar(8);
	}
	
	@Test
	public void testAgregarHeap() {
		assertNotNull("El heap no deberia ser nulo", heap);
		assertEquals("El tamaño del heap no es el esperado", 4, heap.darTamano());
	}
	
	@Test
	public void testSacarMayor() {
		assertEquals("El valor no es el esperado",new Integer(8), heap.eliminarMayor());
		assertEquals("El valor no es el esperado",new Integer(5), heap.eliminarMayor());
	}
	
	@Test
	public void testAgregar() {
		heap.agregar(11);
		assertEquals("El valor no es el esperado",new Integer(11), heap.eliminarMayor());
		heap.agregar(0);
		assertNotEquals("El valor no es el esperado",new Integer(0), heap.eliminarMayor());
	}
}

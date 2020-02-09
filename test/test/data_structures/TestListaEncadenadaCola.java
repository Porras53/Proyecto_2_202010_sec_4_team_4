package test.data_structures;

import static org.junit.Assert.*;

import org.junit.*;

import model.data_structures.*;

public class TestListaEncadenadaCola 
{

	private ListaEncadenadaCola<String> cola;
	private static int TAMANO= 100;
	
	@Before
	public void setUp1()
	{
		cola= new ListaEncadenadaCola<String>();
	}
	
	public void setUp2()
	{
		int cont=0;
		while(cont<TAMANO)
		{
			cola.insertarFinal(" "+cont);
			cont++;
		}
	}
	
	
	@Test
	public void testListaEncadenada() 
	{
		// TODO
		assertTrue(cola!=null);
		assertEquals(0,cola.darLongitud());
	}
	
	@Test
	public void darLongitud() {
		setUp2();
		// TODO
		assertEquals(100,cola.darLongitud());
		
	}
	
	@Test
	public void darCabeza() {
		setUp2();
		// TODO
		assertEquals(" "+0,cola.darCabeza());
		
	}
	
	@Test
	public void esListaVacia() {
		
		assertTrue(cola.esListaVacia());
		setUp2();
		assertFalse(cola.esListaVacia());
		
	}
	

	@Test
	public void testInsertarFinal() {
		setUp2();
		// TODO
		assertEquals(100,cola.darLongitud());
		assertEquals(" "+99,cola.darUltimo());
	}
	
	
	@Test
	public void testEliminarComienzo() {
		setUp2();
		// TODO
		cola.eliminarComienzo();
		assertEquals(99,cola.darLongitud());
		assertEquals(" "+1,cola.darCabeza());
		
	}

	
	
	
	
	
}

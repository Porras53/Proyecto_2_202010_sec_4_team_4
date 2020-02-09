package test.data_structures;

import static org.junit.Assert.*;

import org.junit.*;

import model.data_structures.*;

public class TestListaEncadenadaPila 
{

	private ListaEncadenadaPila<String> pila;
	private static int TAMANO= 100;
	
	@Before
	public void setUp1()
	{
		pila= new ListaEncadenadaPila<String>();
	}
	
	public void setUp2()
	{
		int cont=0;
		while(cont<TAMANO)
		{
			pila.insertarComienzo(" "+cont);
			cont++;
		}
	}
	
	
	@Test
	public void testListaEncadenada() 
	{
		// TODO
		assertTrue(pila!=null);
		assertEquals(0,pila.darLongitud());
	}
	
	@Test
	public void darLongitud() {
		setUp2();
		// TODO
		assertEquals(100,pila.darLongitud());
		
	}
	
	@Test
	public void darCabeza() {
		setUp2();
		// TODO
		assertEquals(" "+99,pila.darCabeza());
		
	}
	
	@Test
	public void esListaVacia() {
		
		assertTrue(pila.esListaVacia());
		setUp2();
		assertFalse(pila.esListaVacia());
		
	}
	

	
	@Test
	public void testInsertarComienzo() {
		setUp2();
		// TODO
		pila.insertarComienzo(" "+ 200);
		assertEquals(" "+200,pila.darCabeza());
		
	}
	
	
	@Test
	public void testEliminarComienzo() {
		setUp2();
		// TODO
		pila.eliminarComienzo();
		assertEquals(99,pila.darLongitud());
		assertEquals(" "+98,pila.darCabeza());
		
	}
	
	
	
	
}

package test.data_structures;
import static org.junit.Assert.assertEquals;

import org.junit.*;

import model.logic.Modelo;

public class TestQuickSort {

	private Double[] aleatorio;
	private Double[] ascendente;
	private Double[] descendente;
	private Modelo modelo = new Modelo();
	
	private static int TAMANO=12;
	
	@Before
	public void setUp1()
	{
		aleatorio= new Double[TAMANO];
		ascendente= new Double[TAMANO];
		descendente= new Double[TAMANO];
		
		Double j=0.0;
		for(int i=0;i<TAMANO;i++)
		{
			ascendente[i]=j;
			j+=1.0;
		}
		
		j=0.0;
		for(int i=TAMANO-1;i>0;i--)
		{
			descendente[i]=j;
			j+=1.0;
		}
		
		for(int i=0;i<TAMANO;i++)
		{
			aleatorio[i]=Math.random();
		}
	}
	
	
	@Test
	public void testShellSort()
	{
		Double w=11.0;
		modelo.quickSort(aleatorio);
		Double mayor=0.0;
		for(int i=0;i<TAMANO;i++)
		{
			if(mayor<aleatorio[i])
			{
				mayor= aleatorio[i];
			}
		}
		assertEquals(mayor,aleatorio[TAMANO-1]);
		modelo.quickSort(ascendente);
		assertEquals(w,ascendente[TAMANO-1]);
		
		/**modelo.shellSort(descendente);
		assertEquals(w,descendente[TAMANO-1]);**/
		
		
		
	}
	
}

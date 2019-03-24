package model.data_structures;

import junit.framework.TestCase;
import model.vo.LocationVO;

public class PruebasBST extends TestCase{
	
	private ITablaSimOrd<String, Integer> tabla;
	private ITablaSimOrd<Integer, Integer> tabla2;
	private void setUpEscenario0() {
		tabla= new BlancoRojoBST<>();
	}
	
	private void setUpEscenario1() {
		tabla= new BlancoRojoBST<>();
		tabla.put("Daniel", 10);
		tabla.put("Sebastián", 20);
		tabla.put("Camila", 40);
		
	}
	
	private void setUpEscenario2() {
		tabla2= new BlancoRojoBST<>();
		
		for (int i = 0; i < 100; i++) {
			tabla2.put(i, i);
		}
		
	}
	

	public void testColaVacia(){
		setUpEscenario0();
		//Las colas de prioridad deberían estar vacías
		assertEquals("No hay elementos debería retornar true",true, tabla.isEmpty());
		//Eliminar
		assertEquals("No hay elementos debería retornar null",null, tabla.get("Hola"));
	}
	
	
	
	
	public void testPocosElementos(){
		setUpEscenario1();
		assertEquals(false, tabla.isEmpty());
		assertEquals(true, tabla.contains("Daniel"));
		assertEquals(true, tabla.contains("Sebastián"));
		assertEquals(true, tabla.contains("Camila"));
		assertEquals(true, tabla.darTamano() == 3);
		assertEquals(true, tabla.get("Daniel") == 10);
		assertEquals(true, tabla.get("Sebastián") == 20);
		assertEquals(true, tabla.get("Camila") == 40);

		
		//Pruebas Básicas de Eliminar
		assertEquals(true, tabla.delete("Camila") == 40);
		assertEquals(true, tabla.delete("Camila") == null);
		assertEquals(false, tabla.contains("Camila"));

		tabla.deleteMin();
		
		
		assertEquals(false, tabla.contains("Daniel"));
		assertEquals(true, tabla.delete("Sebastián") == 20);
		assertEquals(false, tabla.contains("Sebastián"));
		assertEquals(true, tabla.isEmpty());
		
		tabla.put("Camilo", 50);
		tabla.put("Fernando", 70);
		tabla.put("Daniela", 70);
		assertEquals(true, tabla.darTamano() == 3);
		tabla.deleteMin();
		assertEquals(true, tabla.darTamano() == 2);
		assertEquals(false, tabla.contains("Camilo"));

	}
	
	public void testMuchosElementos(){
		setUpEscenario2();
		
		assertEquals(true, tabla2.darTamano() == 100);
		assertEquals(false, tabla2.isEmpty());
		
		for (int i = 100; i < 200; i++) {
			tabla2.put(i, i);
		}
		
		assertEquals(true, tabla2.darTamano() == 200);
		
		for (int i = 100; i < 200; i++) {
			tabla2.put(i, i*4);
		}
		
		assertEquals(true, tabla2.darTamano() == 200);
		
		for (int i = 100; i < 200; i++) {
			tabla2.put(i, i);
		}
		
		for (int i = 0; i < 200; i++) {
			assertEquals(true, tabla2.delete(i)== i);
		}
		
	}

	

}

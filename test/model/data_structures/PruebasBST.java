package model.data_structures;

import java.util.Iterator;

//import com.sun.org.apache.bcel.internal.generic.Select;

import junit.framework.TestCase;
import model.vo.LocationVO;

public class PruebasBST extends TestCase{
	
	
	/**
	 * Atributos para guardar las tablas de prueba
	 */
	private ITablaSimOrd<String, Integer> tabla;
	private ITablaSimOrd<Integer, Integer> tabla2;
	
	/**
	 * Escenario 0, tabla vac�a
	 */
	private void setUpEscenario0() {
		tabla= new BlancoRojoBST<>();
	}
	
	
	/**
	 * Escenario 1 tabla con pocos elementos
	 */
	private void setUpEscenario1() {
		tabla= new BlancoRojoBST<>();
		tabla.put("Daniel", 10);
		tabla.put("Sebasti�n", 20);
		tabla.put("Camila", 40);
		
	}
	
	
	/**
	 * Escenario 2 tabla con 100 elementos
	 */
	private void setUpEscenario2() {
		tabla2= new BlancoRojoBST<>();
		
		for (int i = 0; i < 100; i++) {
			tabla2.put(i, i);
		}
		
	}
	


	/**
	 * Pruebas sobre la tabla vac�a
	 */
	public void testColaVacia(){
		setUpEscenario0();
		//Las colas de prioridad deber�an estar vac�as
		assertEquals("No hay elementos deber�a retornar true",true, tabla.isEmpty());
		//Eliminar
		assertEquals("No hay elementos deber�a retornar null",null, tabla.get("Hola"));
	}
	
	
	
	/**
	 * Se pruebas sobre los pocos elementos
	 */
	public void testPocosElementos(){
		
		/**
		 *Se verifica que la tabla tenga los elementos ingresados
		 */
		setUpEscenario1();
		assertEquals(false, tabla.isEmpty());
		assertEquals(true, tabla.contains("Daniel"));
		assertEquals(true, tabla.contains("Sebasti�n"));
		assertEquals(true, tabla.contains("Camila"));
		assertEquals(true, tabla.darTamano() == 3);
		assertEquals(true, tabla.get("Daniel") == 10);
		assertEquals(true, tabla.get("Sebasti�n") == 20);
		assertEquals(true, tabla.get("Camila") == 40);

		
		/**
		 * Pruebas de eliminaci�n
		 */
		assertEquals(true, tabla.delete("Camila") == 40);
		assertEquals(true, tabla.delete("Camila") == null);
		assertEquals(false, tabla.contains("Camila"));

		tabla.deleteMin();
		
		
		assertEquals(false, tabla.contains("Daniel"));
		assertEquals(true, tabla.delete("Sebasti�n") == 20);
		assertEquals(false, tabla.contains("Sebasti�n"));
		assertEquals(true, tabla.isEmpty());
		
		
		/**
		 * Se reincertan elementos para verificar que sucede
		 */
		tabla.put("Camilo", 50);
		tabla.put("Fernando", 70);
		tabla.put("Daniela", 70);
		assertEquals(true, tabla.darTamano() == 3);
		tabla.deleteMin();
		assertEquals(true, tabla.darTamano() == 2);
		assertEquals(false, tabla.contains("Camilo"));

	}
	
	
	/**
	 * Pruebas sobre 100 elementos
	 */
	public void testMuchosElementos(){
		setUpEscenario2();
		
		/**
		 * Se verifica que la tabla cuente con los elementos iniciales
		 */
		assertEquals(true, tabla2.darTamano() == 100);
		assertEquals(false, tabla2.isEmpty());
		
		assertEquals(true, tabla2.max() == 99);
		assertEquals(true, tabla2.min() == 0);
		
		
		
		/**
		 * Se a�aden 100 elementos m�s
		 */
		for (int i = 100; i < 200; i++) {
			tabla2.put(i, i);
		}
		
		assertEquals(true, tabla2.darTamano() == 200);
		
		
		/**
		 * Se sobreescriben 100 llaves 
		 */
		for (int i = 100; i < 200; i++) {
			tabla2.put(i, i*4);
		}
		
		assertEquals(true, tabla2.darTamano() == 200);
		
		for (int i = 100; i < 200; i++) {
			tabla2.put(i, i);
		}
		
		/**
		 * Se verifica la eliminaci�n
		 */
		for (int i = 0; i < 200; i++) {
			assertEquals(true, tabla2.delete(i)== i);
		}
		
	}
	

	/**
	 * Se verifican: Rank, Select, Floor, Ceiling
	 */
	public void testOperacionesNoFundamentales(){
		setUpEscenario2();
		
		// RANK
		for (int i = 0; i < 100; i++) {
			assertEquals(true, tabla2.rank(i) == i);
		}
		
		
		//Select
		for (int i = 0; i < 100; i++) {
			assertEquals(true, tabla2.select(i) == i);
		}
		
		
		//Floor
		for (int i = 0; i < 100; i++) {
			assertEquals(true, tabla2.floor(i) == i);
		}
		
		//Ceiling
		for (int i = 0; i < 100; i++) {
			assertEquals(true, tabla2.ceiling(i) == i);
		}
	}
	
	

	/**
	 * Prueba sobre el iterador
	 */
	public void testIterador(){
		
		setUpEscenario2();
		int contador = 0;
		for(Integer s: tabla2){
			assertEquals(true, s == contador);
			contador ++;
		}
	}

	

}

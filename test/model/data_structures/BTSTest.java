package model.data_structures;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import model.util.Sort;

public class BTSTest {
	/*
	 * Atributos 
	 */
	private ITablaSimOrd<String, Integer> tabla;
	private final int numeroEscenarios = 100;
	
	/*
	 * Escenarios
	 */
	// Arreglo con n elementos
	private void setUpEscenario(int n, boolean aleatorio) {
		tabla = new BlancoRojoBST<String, Integer>();
		if (!aleatorio) {
			for (int i = 0; i < n; i++) {
				tabla.put("Elemento " + i, i);
			}
		} else {
			IArregloDinamico<Integer> ordenPos = new ArregloDinamico<>(n);
			Sort.shuffle(ordenPos);
			
			for (Integer i: ordenPos) {
				tabla.put("Elemento " + i, i);
			}
		}
	}

	/*
	 * Metodos para Pruebas
	 */
	/**
	 * Prueba el constructor con diferentes tamanios iniciales para cada escenario. Asume que darTamano() funciona correctamente
	 */

	@Test
	public void testBTS() {
		for (int n = 1; n < numeroEscenarios; n++) {
			setUpEscenario(n, true);
			assertTrue("Escenario: " + n + " creado en desorden. El arbol deberia tener tamano " + n, tabla.darTamano() == n);
			
			setUpEscenario(n, false);
			assertTrue("Escenario: " + n + " creado en orden. El arbol deberia tener tamano " + n, tabla.darTamano() == n);
		}
		System.out.println("El constructor funciona!\n");
	}
	
	/**
	 * Prueba el metodo darTamano()
	 */
	@Test
	public void testDarTamano() {
		for (int n = 1; n <= numeroEscenarios; n++) {
			setUpEscenario(n, true);
			assertTrue("Escenario: " + n + " creado en desorden. El arbol deberia tener " + n + " elementos."
						+ " Pero tiene " + tabla.darTamano(), tabla.darTamano() == n);
			
			setUpEscenario(n, false);
			assertTrue("Escenario: " + n + " creado en orden. El arbol deberia tener " + n + " elementos."
						+ " Pero tiene " + tabla.darTamano(), tabla.darTamano() == n);
			
			System.out.println("darTamano() funciona para el escenario " + n);
		}
		System.out.println("darTamano() funciona!\n");
	}
	
	/**
	 * Prueba el metodo get(). Asume que darTamano() funciona correctamente
	 */
	@Test
	public void testGet() {
		for (int n = 1; n <= numeroEscenarios; n++) {	
			for (boolean desordenado: new boolean[]{true, false}) {
				System.out.println("\n\nEntrando a probar get()  para el escenario N = " + n);
				setUpEscenario(n, desordenado);
				
				// Obtener los elementos
				Integer valor;
				for (int i = 0; i < n; i++) {
					valor = tabla.get("Elemento " + i);
					// Verificar que el objeto es el esperado
					assertTrue("Escenario: " + n + " creado en desorden?: " + desordenado + ". El dato esperado era: " + i
							+ ", pero se obtuvo " + (valor != null? "nulo": valor), valor != null && valor.equals(i));
					// Verificar que no ha cambiado el tamano del arbol
					assertTrue("Escenario: " + n + " creado en desorden?: " + desordenado + ". El arbol deberia tener " + n + " elementos."
							+ " Pero tiene " + tabla.darTamano(), tabla.darTamano() == n);
				}
				
				System.out.println("get() funciona para el escenario " + n);
			}
		}
		System.out.println("get() funciona!\n");
	}
	
	/**
	 * Prueba el metodo put. Asume que el metodo get() funciona correctamente
	 */
	@Test
	public void testPut() {
		int nAgregar;
		Integer valor;
		IArregloDinamico<Integer> ordenPos; // Arreglo con el orden en que se agregan los nuevos elementos
		
		for (int n = 1; n <= numeroEscenarios; n++) {
			setUpEscenario(n, true);
			nAgregar = 2*n;
			
			// Agrega nAgregar elementos nuevos
			ordenPos = new ArregloDinamico<>(nAgregar);
			Sort.shuffle(ordenPos);
			for (int i: ordenPos) {
				tabla.put("Nuevo elemento " + i, i);
				
				// Comprobar que el elemento fue agregado
				valor = tabla.get("Nuevo elemento " + i);
				assertTrue("Escenario: " + n + ". Se espera que al conseguir el elemento recien colocado se obtenga " + i + 
						", pero se obtiene " + (valor != null? "nulo": valor), valor != null && valor.equals(i));
				System.out.println("put() funciona para el escenario " + n + ", agregando nuevos elementos");
			}
			// Comprobar que el tamanio de la tabla es el esperado
			assertTrue("Escenario: " + n + ". El arbol deberia tener " + (n + nAgregar) + " elementos."
					+ " Pero tiene " + tabla.darTamano(), tabla.darTamano() == (n + nAgregar));
			System.out.println("put() funciona para el escenario " + "(" + n + ", " + (n/2) + "), agregando nuevos elementos, Y identifica el tamanio adecuado");
			
			
			// Modifica nAgregar/2 elementos existentes
			for (int i = 0; i < nAgregar/2; i++) {
				tabla.put("Nuevo elemento " + i, -i);
				
				// Comprobar que el elemento fue modificado correctamente
				valor = tabla.get("Nuevo elemento " + i);
				assertTrue("Escenario: " + n + ". Se espera que al conseguir el elemento recien colocado se obtenga " + (-i) + 
						", pero se obtiene " + (valor != null? "nulo": valor), valor == -i);
				System.out.println("put() funciona para el escenario " + n + ", reemplazando elementos");
			}
			// Comprobar que no se cambio el tamanio del arbol
			assertTrue("Escenario: " + n + ". No debio cambiar de tamanio. El arbol deberia tener " + (n + nAgregar) + " elementos."
					+ " Pero tiene " + tabla.darTamano(), tabla.darTamano() == (n + nAgregar));
			System.out.println("put() funciona para el escenario " + n + ", reemplazando elementos, Y identifica el tamanio adecuado");
		}
		System.out.println("put() funciona!\n");
	}
	
	/**
	 * Prueba el metodo delete()
	 */
	@Test
	public void testDelete() {
		int nEliminar;
		Integer valor;
		int nEliminados;
		
		for (int n = 1; n <= numeroEscenarios; n++) {
			setUpEscenario(n, true);
			nEliminar = n;
			nEliminados = 0;
			
			// Eliminar nEliminar elementos
			for (int i = 0; i < nEliminar; i++) {
				valor = tabla.delete("Elemento " + i);
						
				// Verificar que se elimino el elemento correcto
				assertTrue("Escenario: " + n + ". El dato esperado era: " + i
						+ ", pero se obtuvo " + (valor != null? "nulo": valor), valor != null && valor.equals(i));
				nEliminados += 1;
				
				// Comprobar que el total de elementos disminuye en 1
				assertTrue("Escenario: " + n + ". El arbol deberia tener " + (n - nEliminados) + " elementos."
						+ " Pero tiene " + tabla.darTamano(), tabla.darTamano() == (n - nEliminados));
			}
			System.out.println("delete() funciona para el escenario " + n + ", eliminando todos los elementos.");			
		}
		System.out.println("delete() funciona!");
	}
	
	/**
	 * Prueba el metodo iterator().
	 */
	@Test
	public void testIterator() {
		int n = numeroEscenarios; // n grande para probar el iterador
		int nProbar = 20; // Probar varias veces el iterador para el arbol creado de diferentes maneras pero con los mismos elementos
		boolean[] constrActual = new boolean[] {true, false};
		for (int j = 0; j < nProbar; j++) {  
			setUpEscenario(n, constrActual[j%2]); // Probar intercaladamente para cada constructor
			
			int i = 0;
			for(String dato: tabla) {
				assertTrue("Escenario: " + n + ". El " + i + "-esimo elemento deberia ser: Elemento " + i
						+ ", pero se obtuvo " + dato, dato.equals("Elemento " + i));
				i += 1;
			}
			// Verificar que solo se identifican n elementos
			assertTrue("Escenario: " + n + ". El iterador deberia identificar y devolver " + n + " elementos", i == n);
			System.out.println("El iterador paso la prueba #" + j);
		}
		System.out.println("El iterador funciona para un tamanio " + n);
	}
}
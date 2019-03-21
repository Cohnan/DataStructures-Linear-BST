package model.data_structures;

import static org.junit.Assert.*;

import java.util.Comparator;

import javax.swing.text.html.HTMLDocument.Iterator;

import org.junit.Test;

import controller.LoadMovingViolations;
import model.util.Sort;
import model.vo.VOMovingViolation;

public class TestTablaHashConInfracciones {
	/**
	 * Atributos
	 */
	
	int totalConsultas = 10000;
	
	int[] tamaniosIniciales = new int[] {1, 10, 100, 1000, 10000};
	
	private static LinProbTH<Integer, IArregloDinamico<VOMovingViolation>> thLinProb;
	
	private static SepChainTH<Integer, IArregloDinamico<VOMovingViolation>> thSepChain;
	
	IArregloDinamico<Integer> nCargas;

	/**
	 * Establecimiento de escenarios
	 * @param tabla
	 * @param capInicial
	 */
	private void setUpEscenario(int nTabla, int capInicial ) {
		if (nTabla == 1) {
			thSepChain = null;
			thLinProb = new LinProbTH<Integer, IArregloDinamico<VOMovingViolation>>(capInicial);
			
			this.nCargas = LoadMovingViolations.loadMovingViolations(1, new ITablaHash[] {thLinProb});
		} else if (nTabla == 2) {
			thLinProb = null;
			thSepChain = new SepChainTH<Integer, IArregloDinamico<VOMovingViolation>>(capInicial);
			
			this.nCargas = LoadMovingViolations.loadMovingViolations(1, new ITablaHash[] {thSepChain});
		}
	}

	/**
	 * 
	 */
	@Test
	public void testLinProb() {
		for (int tamanioInicial : tamaniosIniciales) {
			setUpEscenario(1, tamanioInicial);
			System.out.println("\nProbando linProb con un tamaño inicial de " + tamanioInicial);
			
			int total = 0;
			//Integer[] arreglo = new Integer[totalConsultas];
	
			for(Integer s:nCargas){
				total+=s;
			}
			
			System.out.println("Total de datos " + total);
	
			//Inicialmente
			System.out.println("N�mero de Llaves " + thLinProb.n);
			System.out.println("Capacidad del arreglo " + thLinProb.m);
			
			// Consultas Get
			IArregloDinamico<Integer> listaPosiciones= generarListaOrdenada(thLinProb.n, totalConsultas);
			long startTime = System.nanoTime();
			for (int i = 0; i < listaPosiciones.darTamano(); i++) {
				thLinProb.get(listaPosiciones.darObjeto(i));
			}
			long endTime = System.nanoTime();
			long totalTime = endTime-startTime;
			
			//Resultados
			System.out.println("N�mero de Rehash's " + thLinProb.numRehash);
			System.out.println("Tiempo Total de Get's " + totalTime + " nanosegundos");
			System.out.println("N�mero de Llaves " + thLinProb.n);
			System.out.println("Capacidad Final del Arreglo " + thLinProb.m);
		}
	}

	
	/**
	 * 
	 */
	@Test
	public void testSepChain() {
		for (int tamanioInicial : tamaniosIniciales) {
			setUpEscenario(2, tamanioInicial);
			System.out.println("\nProbando setpChain con un tamaño inicial de " + tamanioInicial);
			
			int total = 0;
			Integer[] arreglo = new Integer[totalConsultas];
	
			for(Integer s:nCargas){
				total+=s;
			}
			
			System.out.println("Total de datos" + total);
	
			//Inicialmente
			System.out.println("N�mero de Llaves " + thSepChain.n);
			System.out.println("Capacidad del arreglo " + thSepChain.m);
	
			// Consultas Get
			IArregloDinamico<Integer> listaPosiciones= generarListaOrdenada(thSepChain.n, totalConsultas);
			long startTime = System.nanoTime();
			for (int i = 0; i < listaPosiciones.darTamano(); i++) {
				thSepChain.get(listaPosiciones.darObjeto(i));
			}
			long endTime = System.nanoTime();
			long totalTime = endTime-startTime;
			
			//Resultados
			System.out.println("N�mero de Rehash's " + thSepChain.numRehash);
			System.out.println("Tiempo Total de Get's " + totalTime + " nanosegundos");
			System.out.println("N�mero de Llaves " + thSepChain.n);
			System.out.println("Capacidad Final del Arreglo " + thSepChain.m);
		}
	}

	/**
	 * Genera un arreglo de numeros entre 0 y max (sin incluirlo) de tamaño tamano
	 * @para max Numero maximo deseado 
	 * @param tamano
	 * @return El arreglo deseado
	 */
	private IArregloDinamico<Integer> generarListaOrdenada(int max, int tamano) {
		IArregloDinamico<Integer> posiciones  =  new ArregloDinamico<>(tamano);
		// Generar posiciones
		for (int i = 0; i < tamano; i++){
			posiciones.agregar((int)(Math.random() * max));
		}
		
		Sort.ordenarShellSort(posiciones); //Rapido para listas parcialmente ordenadas

		return posiciones;
	}
}
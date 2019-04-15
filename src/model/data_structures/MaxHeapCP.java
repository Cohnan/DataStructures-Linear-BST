package model.data_structures;

import java.util.Iterator;

import model.util.Sort;

public class MaxHeapCP <T extends Comparable<T>> implements IColaPrioridad<T>{

	
private ArregloDinamico<T> cp;
	
	
	public MaxHeapCP(){
		cp = new ArregloDinamico<T>();
		cp.agregar(null);
	}
	
	
	
	@Override
	public Iterator<T> iterator() {
		return new Iterator<T>() {
			int iActual = 1;
			@Override
			public boolean hasNext() {
				return iActual < cp.darTamano();
			}
			@Override
			public T next() {
				return cp.darObjeto(iActual++);
			}
		};
	}

	@Override
	public boolean esVacia() {
		if(cp.darTamano() == 1) return true;
		return false;
	}

	@Override
	public int darNumElementos() {
		return cp.darTamano()-1;
	}

	public void agregar(T t){
		cp.agregar(t);
		swim(darNumElementos());
	}

	public T delMax() {
	

		if(darNumElementos() ==0) return null;
		T max = cp.darObjeto(1);
		exch(1,darNumElementos());
		cp.eliminarEnPos(darNumElementos());
		sink(1);
		
		return max;
	}

	@Override
	public T max() {
		// TODO Auto-generated method stub
		return cp.darObjeto(1);
	}
	
	private void swim (int k){
		
		while(k>1 && less(k/2,k)){
			exch(k,k/2);
			k = k/2;
		}
		
	}
	
	private void sink(int k){
		
		int N = darNumElementos();
		while(2*k<=N){
			int j = 2*k;
			if(j<N && less(j,j+1)) j++;
			if(!less(k,j)) break;
			exch(k,j);
			k = j;
		}
	}
	
	private boolean less (int i, int j){
		return cp.darObjeto(i).compareTo(cp.darObjeto(j))<0;
	}
	
	private void exch(int i, int j){
	T auxiliar = cp.darObjeto(i);
	cp.cambiarEnPos(i, cp.darObjeto(j));	
	cp.cambiarEnPos(j, auxiliar);
	}

	public Iterable<T> iterableEnOrden(){
		return new Iterable<T>() {
			
			@Override
			public Iterator<T> iterator() {
				return new Iterator<T>() {
					
					ArregloDinamico<T> ordenado = copiaOrdenada();
					int iSiguiente = ordenado.darTamano() - 1;
					
					@Override
					public boolean hasNext() {
						return iSiguiente >= 0;
					}

					@Override
					public T next() {
						return ordenado.darObjeto(iSiguiente--);
					}
					
					private ArregloDinamico<T> copiaOrdenada(){
						ArregloDinamico<T> copiaOrdenada = new ArregloDinamico<T>(cp.darTamano());
						
						// Crea una copia del arreglo en el sentido que contiene los mismos objetos, pero si utilizo cambiarEnPos o agregar en cp, no me afecta en nada esta copia 
						for (T dato : cp){
							if (dato != null) copiaOrdenada.agregar(dato);
						}
						int n = copiaOrdenada.darTamano();
						
						// Ordena de menor a mayor
						Sort.ordenarHeapSorted(copiaOrdenada);
						
						// Reversa el arreglo
						//T temp;
						//for (int i = 0; i < copiaOrdenada.darTamano()/2; i++) {
						//	temp = copiaOrdenada.darObjeto(i);
						//	copiaOrdenada.cambiarEnPos(i, copiaOrdenada.darObjeto(n-1 - i));
						//	copiaOrdenada.cambiarEnPos(n-1 -i, temp);
						//}
						
						return copiaOrdenada;
					}
					
				};
			}
		};
	}
	
	
}

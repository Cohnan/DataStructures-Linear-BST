package model.data_structures;

public class NodoBST<K,V> {
	
	private K key;
	private V value;
	private NodoBST<K, V> izquierda;
	private NodoBST<K, V> derecha;
	private int tamano;
	private boolean color;
	
	
	public NodoBST(K pKey, V pValue,boolean pColor, int pTamano){
		
		key = pKey;
		value = pValue;
		tamano = pTamano;
		color = pColor;
	}
	

	public int darTamano(){
		return tamano;
	}
		
	public K darKey(){
		return key;
	}
	
	public NodoBST<K, V> darIzquierda(){
		return izquierda;
	}
	
	public NodoBST<K, V> darDerecha(){
		return derecha;
	}
	public V darValor(){
		return value;
	}
	
	public void asignarColor(boolean pColor){
		color = pColor;
	}
	
	public void asignarIzquierda(NodoBST<K, V> pIzquierda){
		izquierda = pIzquierda;
	}
	public void asignarDerecha(NodoBST<K, V> pDerecha){
		derecha = pDerecha;
	}
	
	public void asignarValor(V pValor){
		value = pValor;
	}
	
	public boolean darColor(){
		return color;
	}
	
	public void asignarTamano(int pTamano){
		tamano = pTamano;
	}
	
	public void asignarKey(K pKey){
		key = pKey;
	}
	
	
	
	
}

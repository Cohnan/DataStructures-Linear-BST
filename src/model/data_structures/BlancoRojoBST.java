package model.data_structures;

import java.util.Iterator;

public class BlancoRojoBST<K extends Comparable<K>, V> implements ITablaSimOrd<K, V> {

	private NodoBST<K, V> root;
	private static final boolean RED = true;
	private static final boolean BLACK = false;


	public BlancoRojoBST() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public Iterator<K> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void put(K key, V value) {
		// TODO Auto-generated method stub
		if(key==null) return;
		root = put(root,key,value);
		root.asignarColor(BLACK);
	}

	private NodoBST<K, V> put(NodoBST<K, V> pNodo, K key, V value){
		if (pNodo == null) return new NodoBST<K,V>(key,value,RED,1);

		int cmp = key.compareTo(pNodo.darKey());
		if (cmp < 0) pNodo.asignarIzquierda(put(pNodo.darIzquierda(),  key, value)); 
		else if (cmp > 0) pNodo.asignarDerecha(put(pNodo.darDerecha(), key, value)); 
		else pNodo.asignarValor(value);

		if (isRed(pNodo.darDerecha()) && !isRed(pNodo.darIzquierda())) pNodo = rotarIzquierda(pNodo);
		if (isRed(pNodo.darIzquierda())  &&  isRed(pNodo.darIzquierda().darIzquierda())) pNodo = rotarDerecha(pNodo);
		if (isRed(pNodo.darIzquierda())  &&  isRed(pNodo.darDerecha())) cambiarColores(pNodo);
		pNodo.asignarTamano(size(pNodo.darIzquierda())+size(pNodo.darDerecha())+1);
		return pNodo;
	}


	private NodoBST<K, V> rotarIzquierda(NodoBST<K, V> pNodo) {
		NodoBST<K, V> x = pNodo.darDerecha();
		pNodo.asignarDerecha(x.darIzquierda());
		x.asignarIzquierda(pNodo);
		x.asignarColor(x.darIzquierda().darColor());
		x.darIzquierda().asignarColor(RED);
		x.asignarTamano(pNodo.darTamano());
		pNodo.asignarTamano(size(pNodo.darIzquierda())+size(pNodo.darDerecha())+1);
		return x;
	}

	private NodoBST<K, V> rotarDerecha(NodoBST<K, V> pNodo) {
		NodoBST<K, V> x = pNodo.darIzquierda();
		pNodo.asignarIzquierda(x.darDerecha());
		x.asignarDerecha(pNodo);
		x.asignarColor(x.darDerecha().darColor());
		x.darDerecha().asignarColor(RED);
		x.asignarTamano(pNodo.darTamano());
		pNodo.asignarTamano(size(pNodo.darIzquierda())+size(pNodo.darDerecha())+1);
		return x;
	}

	private void cambiarColores(NodoBST<K, V> pNodo) {
		pNodo.asignarColor(!pNodo.darColor());
		pNodo.darIzquierda().asignarColor(!pNodo.darIzquierda().darColor());
		pNodo.darDerecha().asignarColor(!pNodo.darDerecha().darColor());
	}



	private boolean isRed(NodoBST<K, V> pNodo){
		if (pNodo == null) return false;
		return pNodo.darColor() == RED;
	}
	@Override
	public V get(K key) {
		// TODO Auto-generated method stub

		if(key == null) return null;
		return get(root,key);
	}

	private V get(NodoBST<K, V> pNodo, K key){
		while (pNodo!=null) {
			int comparacion = key.compareTo(pNodo.darKey());
			if(comparacion<0) pNodo = pNodo.darIzquierda();
			else if(comparacion>0) pNodo =  pNodo.darDerecha();
			else return pNodo.darValor();
		}	

		return null;
	}

	@Override
	public V delete(K key) {
		// TODO Auto-generated method stub
		if (key == null) throw new IllegalArgumentException("argument to delete() is null");
		if (!contains(key)) return null;
		V respuesta = get(key);

		// if both children of root are black, set root to red
		if (!isRed(root.darIzquierda()) && !isRed(root.darDerecha()))
			root.asignarColor(RED); 

		root = delete(root, key);
		if (!isEmpty()) root.asignarColor(BLACK); 
		
		return respuesta;
	}


	private NodoBST<K, V> delete(NodoBST<K, V> pNodo, K key) { 
		// assert get(h, key) != null;

		if (key.compareTo(pNodo.darKey()) < 0)  {
			if (!isRed(pNodo.darIzquierda()) && !isRed(pNodo.darIzquierda().darIzquierda()))
				pNodo = moverRojoIzquierda(pNodo);
			pNodo.asignarIzquierda(delete(pNodo.darIzquierda(),key));
		}
		else {
			if (isRed(pNodo.darIzquierda()))
				pNodo = rotarDerecha(pNodo);
			if (key.compareTo(pNodo.darKey()) == 0 && (pNodo.darDerecha() == null))
				return null;
			if (!isRed(pNodo.darDerecha()) && !isRed(pNodo.darDerecha().darIzquierda()))
				pNodo = moverRojoDerecha(pNodo);
			if (key.compareTo(pNodo.darKey()) == 0) {
				NodoBST<K, V> x = min(pNodo.darDerecha());
				pNodo.asignarKey(x.darKey());
				pNodo.asignarValor(x.darValor());

				pNodo.asignarDerecha(deleteMin(pNodo.darDerecha()));
			}
			else pNodo.asignarDerecha(delete(pNodo.darDerecha(),key));
		}
		return balance(pNodo);
	}



	private NodoBST<K, V> balance(NodoBST<K, V> pNodo) {

		if (isRed(pNodo.darDerecha()))pNodo = rotarIzquierda(pNodo);
		if (isRed(pNodo.darIzquierda()) && isRed(pNodo.darIzquierda().darIzquierda())) pNodo =rotarDerecha(pNodo);
		if (isRed(pNodo.darIzquierda()) && isRed(pNodo.darDerecha()))cambiarColores(pNodo);

		
		pNodo.asignarTamano(size(pNodo.darIzquierda())+size(pNodo.darDerecha())+1);
		return pNodo;
	}


	private NodoBST<K, V> moverRojoIzquierda(NodoBST<K, V> pNodo) {
		cambiarColores(pNodo);
		if (isRed(pNodo.darDerecha().darIzquierda())) { 
			pNodo.asignarDerecha(rotarDerecha(pNodo.darDerecha()));
			pNodo = rotarIzquierda(pNodo);
			cambiarColores(pNodo);
		}
		return pNodo;
	}

	private NodoBST<K, V> moverRojoDerecha(NodoBST<K, V> pNodo) {
		cambiarColores(pNodo);

		if (isRed(pNodo.darIzquierda().darIzquierda())) { 
			pNodo = rotarDerecha(pNodo);
			cambiarColores(pNodo);
		}
		return pNodo;
	}


	@Override
	public int darTamano() {
		// TODO Auto-generated method stub
		return size(root);
	}

	@Override
	public boolean isEmpty() {

		return darTamano() == 0;
		// TODO Auto-generated method stub
	}

	private int size(NodoBST<K, V> pNodo){
		if(pNodo == null) return 0;
		else{ return pNodo.darTamano();}
	}

	@Override
	public boolean contains(K key) {
		// TODO Auto-generated method stub
		if(key == null) return false;
		return get(key)!=null;
	}

	@Override
	public K min() {
		if (isEmpty())return null;
		return min(root).darKey();
	}
	private NodoBST<K, V> min(NodoBST<K, V> x) { 
		if (x.darIzquierda() == null) return x; 
		else return min(x.darIzquierda()); 
	} 

	public K max() {
		if (isEmpty())return null;
		return max(root).darKey();
	} 

	// the largest key in the subtree rooted at x; null if no such key
	private NodoBST<K, V> max(NodoBST<K, V> x) { 
		// assert x != null;
		if (x.darDerecha() == null) return x; 
		else return max(x.darDerecha()); 
	}

	@Override
	public void deleteMin() {
        if (isEmpty()) return;

        // if both children of root are black, set root to red
        if (!isRed(root.darIzquierda()) && !isRed(root.darDerecha()))
            root.asignarColor(RED);

        root = deleteMin(root);
        if (!isEmpty()) root.asignarColor(BLACK);
	} 
	private NodoBST<K, V> deleteMin(NodoBST<K, V> pNodo) { 
		if (pNodo.darIzquierda() == null)
			return null;

		if (!isRed(pNodo.darIzquierda()) && !isRed(pNodo.darIzquierda().darIzquierda()))
			pNodo = moverRojoIzquierda(pNodo);

		pNodo.asignarIzquierda(deleteMin(pNodo.darIzquierda()));
		return balance(pNodo);
	}




}

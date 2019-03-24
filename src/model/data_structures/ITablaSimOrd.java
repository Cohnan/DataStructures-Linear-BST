package model.data_structures;

public interface ITablaSimOrd<K extends Comparable<K>, V> extends Iterable<K> {
	
	boolean isEmpty();
	
	void put (K key, V value);
	
	V get(K key);
	
	V delete(K key);
	
	int darTamano();
	
	boolean contains(K key);
	
	K min();
	
	K max();
	
	void deleteMin();
}

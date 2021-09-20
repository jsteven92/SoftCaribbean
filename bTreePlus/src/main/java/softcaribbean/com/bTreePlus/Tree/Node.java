package softcaribbean.com.bTreePlus.Tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import lombok.Data;

@Data
public class Node {
    /** lista de pares de clave-valor de un nodo */
	private List<Key> keys;

	/** Hijos solo de los nodos internos */
	private List<Node> children;

	/** nodo a la izquierda , solo para nodos externos. */
	private Node prev;

	/** Nodo siguiente. Solo para nodos externos */
	private Node next;

	/** padre del nodo. NULL si es raiz */
	private Node parent;

	public Node() {
		this.keys = new ArrayList<>();
		this.children = new ArrayList<>();
		this.prev = null;
		this.next = null;
	}

	/**
	 * insertar lista de claves
	 *
	 * @param keys
	 */
	public void setKeys(List<Key> keys) {
		Iterator<Key> iter = keys.iterator();
		while (iter.hasNext()) {
			this.keys.add(iter.next());
		}
	}

	@Override
	public String toString() {
		return "Keys =" + keys.toString();
	}
}

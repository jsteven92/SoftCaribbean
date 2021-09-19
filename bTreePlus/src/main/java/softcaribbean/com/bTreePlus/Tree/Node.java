package softcaribbean.com.bTreePlus.Tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
	 * devolver lista de claves
	 *
	 * @return clave
	 */
	public List<Key> getKeys() {
		return keys;
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

	/**
	 * insertar lista de hijos
	 *
	 * @return hojos nodo
	 */
	public List<Node> getChildren() {
		return children;
	}

	/**
	 * devolver la lista de hijos nodo
	 *
	 * @param children
	 */
	public void setChildren(List<Node> children) {
		this.children = children;
	}

	/**
	 * devolver nodo anterios
	 *
	 * @return nodo anterios
	 */
	public Node getPrev() {
		return prev;
	}

	/**
	 * insertar nodo anterios
	 *
	 * @param prev
	 */
	public void setPrev(Node prev) {
		this.prev = prev;
	}

	/**
	 * obtener nodo siguiente
	 *
	 * @return nodo siguiente
	 */
	public Node getNext() {
		return next;
	}

	/**
	 * devolver nodo siguiente
	 *
	 * @param next
	
	 */
	public void setNext(Node next) {
		this.next = next;
	}

	/**
	 * obtener nodo padre
	 *
	 * @return nodo padre
	 */
	public Node getParent() {
		return parent;
	}

	/**
     * ingresar nodo padre
	 * @param parent
	 */
	public void setParent(Node parent) {
		this.parent = parent;
	}

	@Override
	public String toString() {
		return "Keys =" + keys.toString();
	}
}

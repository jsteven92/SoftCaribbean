package softcaribbean.com.bTreePlus.Tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import softcaribbean.com.bTreePlus.Entity.Client;



public class BTreePlus {
    /** Grado del arbol m. */
	private int m;

	/** raiz del arbol */
	private Node root;

	public BTreePlus() {

	}

	/**
	 *  Método inicializa el árbol B Plus. Establece el grado del árbol B-Plus como orden de entrada.
	 *
	 * @param order
	 */
	public void initialize(int order) {
		this.m = order;
		this.root = null;
	}

	/**
	 * Este método se utiliza para insertar un nuevo par clave-valor en el árbol B Plus
	 *
	 * @param key
	 * @param client
	 */
	public void insert(double key, Client client) {

		// Caso 1: arbol vacío
		if (null == this.root) {
			/**se crea Nodo,se inserta la clave a la lista y nodo se establece como raiz */
			Node newNode = new Node();
			newNode.getKeys().add(new Key(key, client));
			this.root = newNode;
			// nodo sin padre por ser raiz
			this.root.setParent(null);
		}

		// Caso 2: se tiene un unico nodo y no esta lleno
		else if (this.root.getChildren().isEmpty() && this.root.getKeys().size() < (this.m - 1)) {
			//Para las inserciones hasta que se llene la raiz, actualizamos
            //el nodo raiz y agragamos las nuevas claves-valor
			insertWithinExternalNode(key, client, this.root);
		}

		// Caso 3: Inserción normal
		else {
			Node curr = this.root;
			// Como insertamos el elemento solo en el nodo externo, pasamos al último nivel
			while (!curr.getChildren().isEmpty()) {
				curr = curr.getChildren().get(binarySearchWithinInternalNode(key, curr.getKeys()));
			}
			insertWithinExternalNode(key, client, curr);
			if (curr.getKeys().size() == this.m) {
				// si se llena el nodo externo, lo debemos partir
				splitExternalNode(curr, this.m);
			}
		}

	}

	/**
	 * insertamos el par clave-valor para los nodos externos.
	 *
	 * @param key
	 * @param value
	 * @param node
	 */
	private void insertWithinExternalNode(double key, softcaribbean.com.bTreePlus.Entity.Client client, Node node) {
		// busqueda binaria para encontrar el index,que sera el lugar a insertar el nodo de manera 
        // ordenada
		int indexOfKey = binarySearchWithinInternalNode(key, node.getKeys());
		if (indexOfKey != 0 && node.getKeys().get(indexOfKey - 1).getKey() == key) {
			// la llave existe, agregamos el valor a la lista de claves
			node.getKeys().get(indexOfKey - 1).getclients().add(client);
		} else {
			// clave no existente. agregar clave-valor
			Key newKey = new Key(key, client);
			node.getKeys().add(indexOfKey, newKey);
		}
	}

	/**
	 * partir el nodo esterno
	 *
	 * @param curr
	 * @param m
	 */
	private void splitExternalNode(Node curr, int m) {

		// mitar del nodo
		int midIndex = m / 2;
        //nodo medio
		Node middle = new Node();
        //nodo derecho
		Node rightPart = new Node();

		// se ingresa la parte derecha para tener el elemento medio y los 
        //elementos directamente en el elemento medio
		rightPart.setKeys(curr.getKeys().subList(midIndex, curr.getKeys().size()));
		rightPart.setParent(middle);
		//hacemos el medio como el nodo interno, agregamos solo la clave ya que los nodos internos 
        //del árbol no contienen valores
		middle.getKeys().add(new Key(curr.getKeys().get(midIndex).getKey()));
		middle.getChildren().add(rightPart);
		// Curr sostiene la parte izquierda, así que se actualiza el
        // nodo dividido para que contenga solo la parte izquierda
		curr.getKeys().subList(midIndex, curr.getKeys().size()).clear();

		boolean firstSplit = true;
		// propagar el elemento del medio hacia un nodo padre y fusionarlo
		splitInternalNode(curr.getParent(), curr, m, middle, firstSplit);

	}

	/**
	 * dividimos un nodo interno
	 *
	 * @param curr
	 * @param prev
	 * @param m
	 * @param toBeInserted
	 * @param firstSplit
	 *indica si la división está ocurriendo en el primer nodo interno desde la parte inferior
	 */
	private void splitInternalNode(Node curr, Node prev, int m, Node toBeInserted, boolean firstSplit) {
		if (null == curr) {
			// if we split the root before, then a new root has to be created
            //si partimos la raiz antes, entonces una nueva raiz tiene que haber sido creada
            this.root = toBeInserted;
            //buscamos donde el hijo sera insertado haciendo una
            //busqueda binaria en las claves
			int indexForPrev = binarySearchWithinInternalNode(prev.getKeys().get(0).getKey(), toBeInserted.getKeys());
			prev.setParent(toBeInserted);
			toBeInserted.getChildren().add(indexForPrev, prev);
			if (firstSplit) {
				// actualice la lista vinculada solo para la primera división 
                //(para el nodo externo)
				if (indexForPrev == 0) {
					toBeInserted.getChildren().get(0).setNext(toBeInserted.getChildren().get(1));
					toBeInserted.getChildren().get(1).setPrev(toBeInserted.getChildren().get(0));
				} else {
					toBeInserted.getChildren().get(indexForPrev + 1)
							.setPrev(toBeInserted.getChildren().get(indexForPrev));
					toBeInserted.getChildren().get(indexForPrev - 1)
							.setNext(toBeInserted.getChildren().get(indexForPrev));
				}
			}
		} else {
			// fusionamos el nodo interno con el nodo medio + la derecha de la división anterior
			mergeInternalNodes(toBeInserted, curr);
			if (curr.getKeys().size() == m) {
				// do a split again if the internal node becomes full
				int midIndex = (int) Math.ceil(m / 2.0) - 1;
				Node middle = new Node();
				Node rightPart = new Node();

				// since internal nodes follow a split like the b tree, right
				// part contains elements right of the mid element, and the
				// middle becomes parent of right part
				rightPart.setKeys(curr.getKeys().subList(midIndex + 1, curr.getKeys().size()));
				rightPart.setParent(middle);

				middle.getKeys().add(curr.getKeys().get(midIndex));
				middle.getChildren().add(rightPart);

				List<Node> childrenOfCurr = curr.getChildren();
				List<Node> childrenOfRight = new ArrayList<>();

				int lastChildOfLeft = childrenOfCurr.size() - 1;

				// update the children that have to be sent to the right part
				// from the split node
				for (int i = childrenOfCurr.size() - 1; i >= 0; i--) {
					List<Key> currKeysList = childrenOfCurr.get(i).getKeys();
					if (middle.getKeys().get(0).getKey() <= currKeysList.get(0).getKey()) {
						childrenOfCurr.get(i).setParent(rightPart);
						childrenOfRight.add(0, childrenOfCurr.get(i));
						lastChildOfLeft--;
					} else {
						break;
					}
				}

				rightPart.setChildren(childrenOfRight);

				// update the overfull node to contain just the left part and
				// update its children
				curr.getChildren().subList(lastChildOfLeft + 1, childrenOfCurr.size()).clear();
				curr.getKeys().subList(midIndex, curr.getKeys().size()).clear();

				// propogate split one level up
				splitInternalNode(curr.getParent(), curr, m, middle, false);
			}
		}
	}

	/**
	 *fusionar nodos internos
	 *
	 * @param mergeFrom
	 * @param mergeInto
	 */
	private void mergeInternalNodes(Node mergeFrom, Node mergeInto) {
		Key keyToBeInserted = mergeFrom.getKeys().get(0);
		Node childToBeInserted = mergeFrom.getChildren().get(0);
        //buscamos el index donde la clave esta insertado con una busqueda binaria
		int indexToBeInsertedAt = binarySearchWithinInternalNode(keyToBeInserted.getKey(), mergeInto.getKeys());
		int childInsertPos = indexToBeInsertedAt;
		if (keyToBeInserted.getKey() <= childToBeInserted.getKeys().get(0).getKey()) {
			childInsertPos = indexToBeInsertedAt + 1;
		}
		childToBeInserted.setParent(mergeInto);
		mergeInto.getChildren().add(childInsertPos, childToBeInserted);
		mergeInto.getKeys().add(indexToBeInsertedAt, keyToBeInserted);

		// actualizar la lista de enlaces de nodos externos
		if (!mergeInto.getChildren().isEmpty() && mergeInto.getChildren().get(0).getChildren().isEmpty()) {

			//Si la fusión ocurre en el último elemento, solo el puntero
            //entre el nuevo nodo y el último elemento 
            //anterior debe actualizarse
			if (mergeInto.getChildren().size() - 1 != childInsertPos
					&& mergeInto.getChildren().get(childInsertPos + 1).getPrev() == null) {
				mergeInto.getChildren().get(childInsertPos + 1).setPrev(mergeInto.getChildren().get(childInsertPos));
				mergeInto.getChildren().get(childInsertPos).setNext(mergeInto.getChildren().get(childInsertPos + 1));
			}
			// Si se produce una fusión en el último elemento, solo se debe 
            //actualizar el puntero entre el nuevo nodo y el último elemento anterior
			else if (0 != childInsertPos && mergeInto.getChildren().get(childInsertPos - 1).getNext() == null) {
				mergeInto.getChildren().get(childInsertPos).setPrev(mergeInto.getChildren().get(childInsertPos - 1));
				mergeInto.getChildren().get(childInsertPos - 1).setNext(mergeInto.getChildren().get(childInsertPos));
			}
            
			// Si se produce una fusión en el medio, entonces el elemento 
            //siguiente y los punteros anterior y siguiente del elemento anterior 
            //deben actualizarse
			else {
				mergeInto.getChildren().get(childInsertPos)
						.setNext(mergeInto.getChildren().get(childInsertPos - 1).getNext());
				mergeInto.getChildren().get(childInsertPos).getNext()
						.setPrev(mergeInto.getChildren().get(childInsertPos));
				mergeInto.getChildren().get(childInsertPos - 1).setNext(mergeInto.getChildren().get(childInsertPos));
				mergeInto.getChildren().get(childInsertPos).setPrev(mergeInto.getChildren().get(childInsertPos - 1));
			}
		}

	}

	/**
	 * Helper method - Prints the tree using a level order traversal
	 */
	public void printTree() {
		Queue<Node> queue = new LinkedList<Node>();
		queue.add(this.root);
		queue.add(null);
		Node curr = null;
		int levelNumber = 2;
		System.out.println("Printing level 1");
		while (!queue.isEmpty()) {
			curr = queue.poll();
			if (null == curr) {
				queue.add(null);
				if (queue.peek() == null) {
					break;
				}
				System.out.println("\n" + "Printing level " + levelNumber++);
				continue;
			}

			printNode(curr);

			if (curr.getChildren().isEmpty()) {
				break;
			}
			for (int i = 0; i < curr.getChildren().size(); i++) {
				queue.add(curr.getChildren().get(i));
			}
		}

		curr = curr.getNext();
		while (null != curr) {
			printNode(curr);
			curr = curr.getNext();
		}
		System.out.println("\n" + "***************************************************************");
		System.out.println("\n" + "********************* print end **********************");
		System.out.println("\n" + "***************************************************************");
	}

	/**
	 * Helper method Prints a node of the tree.
	 *
	 * @param curr
	 *            the node to be printed
	 */
	private void printNode(Node curr) {
		for (int i = 0; i < curr.getKeys().size(); i++) {
			System.out.print(curr.getKeys().get(i).getKey() + ":(");
			String values = "";
			for (int j = 0; j < curr.getKeys().get(i).getclients().size(); j++) {
				values = values + curr.getKeys().get(i).getclients().get(j) + ",";
			}
			System.out.print(values.isEmpty() ? ");" : values.substring(0, values.length() - 1) + ");");
		}
		System.out.print("||");
	}

	/**
	 *Se modifica la busqueda binaria en nodo interno
	 * @param key
	 * @param keyList
	 * @return la primer index de la lista con la clave es mayor que la clave de entrada
	 */
	public int binarySearchWithinInternalNode(double key, List<Key> keyList) {
		int st = 0;
		int end = keyList.size() - 1;
		int mid;
		int index = -1;
		// retornamos el primer index si la clave es menor que el primer elemento
		if (key < keyList.get(st).getKey()) {
			return 0;
		}
        // retornamos el tamaño de la lista  + 1 como la nueva posición de la clave si es mayor que el ultimo 
        //elemento
		if (key >= keyList.get(end).getKey()) {
			return keyList.size();
		}
		while (st <= end) {
			mid = (st + end) / 2;
            // la condición garantiza que encontramos un lugar donde la clave
            //es mas pequela que el elemento en este index y sea mayor o igual
            //que el elemento que esta en el index izquiedo. Esta ligar es donde la clave
            //debera insertarse
			if (key < keyList.get(mid).getKey() && key >= keyList.get(mid - 1).getKey()) {
				index = mid;
				break;
			} // seguir con la busqueda binaria
			else if (key >= keyList.get(mid).getKey()) {
				st = mid + 1;
			} else {
				end = mid - 1;
			}
		}
		return index;
	}

	/**
	 * buscar el valor de una clave
	 *
	 * @param key
	 * @return lista de valores de una clave
	 */
	public List<Client> search(double key) {
		List<Client> searchValues = null;

		Node curr = this.root;
		// Recorrer el nodo externo que deberia tener la clave
		//System.out.println(curr.getChildren());
		while (curr.getChildren().size() != 0) {
			curr = curr.getChildren().get(binarySearchWithinInternalNode(key, curr.getKeys()));
		}
		//System.out.println("Nodo Parent -> "+curr.getParent());
		//System.out.println("Nodo hoja -> "+curr);
		List<Key> keyList = curr.getKeys();
        //hacer una busqueda lineal en este nodo para la clave
        //poner los parametros 'searchValues' solo si es exitoso
		for (int i = 0; i < keyList.size(); i++) {
			if (key == keyList.get(i).getKey()) {
				searchValues = keyList.get(i).getclients();
			}
			if (key < keyList.get(i).getKey()) {
				break;
			}
		}

		return searchValues;
	}

	/**
	 * Buscar todos los pares claves-valor  entre clave1 y clave2
	 * @param key1
	 * @param key2
	 * @return lista de pares claves-valor entro dos claves
	 */
	
	public List<Key> search(double key1, double key2) {
		//System.out.println("Searching between keys " + key1 + ", " + key2);
		List<Key> searchKeys = new ArrayList<>();
		Node currNode = this.root;
        //Recorra el nodo externo correspondiente que 'debería' 
        //contener la clave de inicio (clave1)
		while (currNode.getChildren().size() != 0) {
			currNode = currNode.getChildren().get(binarySearchWithinInternalNode(key1, currNode.getKeys()));
		}
		
        //comenzar desde el nodo actual y agregue claves cuyo
        // valor se encuentre entre key1 y key2 con sus pares correspondientes
        // Deténgase si se encuentra el final de la lista o si el valor encontrado en
        // la lista es mayor que key2
		
		boolean endSearch = false;
		while (null != currNode && !endSearch) {
			for (int i = 0; i < currNode.getKeys().size(); i++) {
				if (currNode.getKeys().get(i).getKey() >= key1 && currNode.getKeys().get(i).getKey() <= key2)
					searchKeys.add(currNode.getKeys().get(i));
				if (currNode.getKeys().get(i).getKey() > key2) {
					endSearch = true;
				}
			}
			currNode = currNode.getNext();
		}

		return searchKeys;
	}
}

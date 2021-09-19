package softcaribbean.com.bTreePlus.Tree;

import java.util.ArrayList;
import java.util.List;

import softcaribbean.com.bTreePlus.Entity.Client;

public class Key {
    /** clave. */
	double key;

	/**Lista de valores por clave. solo para nodos externos*/
	List<Client> clients;


	public Key(double key, Client client) {
		this.key = key;
		if (null == this.clients) {
			clients = new ArrayList<>();
		}
		this.clients.add(client);
	}
	
	/**
	 * inicializar la clave
	 *
	 * @param key
	 */
	public Key(double key) {
		this.key = key;
		this.clients = new ArrayList<>();
	}

	/**
	 * devolver clave
	 *
	 * @return la clave
	 */
	public double getKey() {
		return key;
	}

	/**
	 * insertar una clave
	 *
	 * @param key
	 */
	public void setKey(double key) {
		this.key = key;
	}

	/**
	 * devolver una clave
	 *
	 * @return el client
	 */
	public List<Client> getclients() {
		return clients;
	}

	/**
	 * insertar un cliente.
	 *
	 * @param clients
	 */
	public void setclients(List<Client> clients) {
		this.clients = clients;
	}

	public String toString() {
		return "Key [key=" + key + ", clients=" + clients + "]";
	}
}

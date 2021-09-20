package softcaribbean.com.bTreePlus.Tree;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import softcaribbean.com.bTreePlus.Entity.Client;

@Data
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

	public String toString() {
		return "Key [key=" + key + ", clients=" + clients + "]";
	}
}

package softcaribbean.com.bTreePlus.Services;

import java.util.List;

import org.springframework.stereotype.Service;

import softcaribbean.com.bTreePlus.Entity.Client;
import softcaribbean.com.bTreePlus.Tree.BTreePlus;

@Service
public class TreeBServiceImple implements TreeBService{

    /**
     * definimos el grado que va a tener el arbol b
     * y cargamos los datos de la tabla en memoria
     */
    @Override
    public BTreePlus initialize(int degree, List<Client> listClient) {
        BTreePlus tree = new BTreePlus();
        tree.initialize(degree);
        //por cada cliente de la lista cargar al arbol
        listClient.forEach(client -> tree.insert(client.getId(), client));
        
        return tree;
    }
    
}

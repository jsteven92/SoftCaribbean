package softcaribbean.com.bTreePlus.Controller;

import java.util.List;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import softcaribbean.com.bTreePlus.Entity.Client;
import softcaribbean.com.bTreePlus.Services.TableService;
import softcaribbean.com.bTreePlus.Services.TreeBService;
import softcaribbean.com.bTreePlus.Tree.BTreePlus;

@RestController
@RequestMapping(value = "treeBPlus")
public class TreeBPlusRestController {
    BTreePlus tree;

    @Autowired
    TableService tableService;
    @Autowired
    TreeBService treeService;
    private static final int DEGREE = 5;

    /**
     * si no esta cargado el arbol en memoria se inicializa de lo contrario devuelve
     * el arbol ya inicializado
     */
    private void initializeTree() {
        if (null == tree) {
            // se trae los clientes del archivo
            List<Client> listClients = tableService.getDataTable("client");
            // se cargan los clientes al arbol
            this.tree = treeService.initialize(DEGREE, listClients);
        }
    }

    /**
     * crear clientes en el archivo y en el arbol
     * 
     * @param client
     * @return cliente
     */
    @PostMapping
    public ResponseEntity<Client> createClient(@Valid @RequestBody Client client) {
        this.initializeTree();
        System.out.println(client.getId()+" prueba de sistemas");
        // ingresar el nuevo cliente al arbol
        this.tree.insert(client.getId(), client);
        // grabar nuevo cliente al archivo
        tableService.insertDataTable("client", client);

        return ResponseEntity.ok(client);
    }

    /**
     * buscar dentro del arbol un cliente
     * 
     * @param id
     * @return lista de clientes encontrados
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<List<Client>> getCustomer(@PathVariable("id") int id) {
        this.initializeTree();
        //se busca el cliente por id
        List<Client> listClient = this.tree.search(id);

        //si no existe el cliente se informa con un 404
        if (null == listClient || listClient.size() == 0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(listClient);
    }
}

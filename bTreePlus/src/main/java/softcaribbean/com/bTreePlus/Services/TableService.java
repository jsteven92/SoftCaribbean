package softcaribbean.com.bTreePlus.Services;

import java.util.List;

import softcaribbean.com.bTreePlus.Entity.Client;

public interface TableService {
    public List<Client> getDataTable(String table);
    public boolean insertDataTable(String table,Client client);
}

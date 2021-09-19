package softcaribbean.com.bTreePlus.Services;

import java.util.List;

import softcaribbean.com.bTreePlus.Entity.Client;
import softcaribbean.com.bTreePlus.Tree.BTreePlus;

public interface TreeBService {
    public BTreePlus initialize(int degree,List<Client> listClient);
}

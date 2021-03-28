package repository.client;

import model.Client;

import java.util.List;

public interface ClientRepository {
    List<Client> findAll();

    String[] findAllCardNumbers();

    Client findById(Long id);

    Client findByName(String name);

    Client findByCardNumber(String cardNumber);

    boolean update(Client client);

    boolean save(Client client);

    void removeAll();
}

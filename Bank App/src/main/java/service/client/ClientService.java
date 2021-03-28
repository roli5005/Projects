package service.client;

import model.Client;
import model.validator.Notification;
import repository.client.ClientRepository;

public interface ClientService {

    Notification<Client> addClient(Client client);

    Notification<Client> updateClient(Client client);

    Client viewClient(Long id);

    ClientRepository getRepository();

    String generateCardNumber();

}

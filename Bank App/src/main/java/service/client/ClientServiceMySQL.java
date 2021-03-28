package service.client;

import model.Client;
import model.validator.ClientValidator;
import model.validator.Notification;
import repository.client.ClientRepository;
import java.util.Random;


public class ClientServiceMySQL implements ClientService{
    private final ClientRepository clientRepository;

    public ClientServiceMySQL(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }

    @Override
    public Notification<Client> addClient(Client client) {
        ClientValidator validator = new ClientValidator(client);
        Notification<Client> addClientNotification = new Notification<>();
        if(validator.validate().isEmpty()){
            clientRepository.save(client);
            addClientNotification.setResult(client);
        }
        else{
            for (String error: validator.validate()
            ) {
                addClientNotification.addError(error);
            }
        }

        return addClientNotification;
    }

    @Override
    public Notification<Client> updateClient(Client client) {
        ClientValidator validator = new ClientValidator(client);
        Notification<Client> clientNotification = new Notification<>();

        if(validator.validate().isEmpty()){
            clientRepository.update(client);
            clientNotification.setResult(client);
        }
        else{
            for (String error: validator.validate()
                 ) {
                clientNotification.addError(error);
            }
        }

        return clientNotification;
    }

    @Override
    public Client viewClient(Long id) {
        Client client = clientRepository.findById(id);
        return client;
    }

    @Override
    public ClientRepository getRepository() {
        return this.clientRepository;
    }

    @Override
    public String generateCardNumber() {
        String cardNumber = "";

        Random random = new Random();

        for(int i=0;i<16;i++){
            int digit = random.nextInt(10);
            cardNumber += digit;
            if(i==3 || i==7 || i==11) cardNumber += " ";
        }
        return cardNumber;
    }



}

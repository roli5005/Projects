package repository.client;

import database.DBConnectionFactory;
import model.Client;
import model.builder.ClientBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;

import static org.junit.Assert.*;

public class ClientRepositoryMySQLTest {
    private static ClientRepository clientRepository;

    @Before
    public void setUp(){
        Connection connection = new DBConnectionFactory().getConnectionWrapper(true).getConnection();
        clientRepository = new ClientRepositoryMySQL(connection);
        save();
    }

    @Test
    public void findAll() {
        Assert.assertEquals( clientRepository.findAll().size(),1);
    }

    @Test
    public void findAllCardNumbers() {
        Assert.assertEquals(clientRepository.findAllCardNumbers().length,1);
    }

    @Test
    public void findById() {
        Client client = clientRepository.findAll().get(0);
        Client clientFound = clientRepository.findById(client.getId());
        Assert.assertEquals(client.getIdentity_card_number(),clientFound.getIdentity_card_number());
    }

    @Test
    public void findByName() {
        Client client = clientRepository.findAll().get(0);
        Client clientFound = clientRepository.findByName(client.getName());

        Assert.assertEquals(client.getName(),clientFound.getName());
    }

    @Test
    public void findByCardNumber() {
        Client client = clientRepository.findAll().get(0);
        Client clientFound = clientRepository.findByCardNumber(client.getIdentity_card_number());

        Assert.assertEquals(client.getIdentity_card_number(),clientFound.getIdentity_card_number());
    }

    @Test
    public void update() {
        Client client = clientRepository.findAll().get(0);
        client.setAddress("Random Street");
        clientRepository.update(client);
        client = clientRepository.findByName(client.getName());
        Assert.assertEquals(client.getAddress(),"Random Street");

    }

    @Test
    public void save() {
        removeAll();
        Client client = new ClientBuilder().setPersonalNumber("1226553989745")
                .setCardNumber("5522 6699 3254 4102")
                .setAddress("Walmart Street")
                .setPhoneNumber("0789625475")
                .setName("John Dover")
                .build();
        clientRepository.save(client);
    }

    @Test
    public void removeAll() {
        clientRepository.removeAll();
    }
}

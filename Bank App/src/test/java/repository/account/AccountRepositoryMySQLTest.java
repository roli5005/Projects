package repository.account;

import database.DBConnectionFactory;
import model.Account;
import model.Client;
import model.builder.AccountBuilder;
import model.builder.ClientBuilder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryMySQL;

import java.sql.Connection;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

public class AccountRepositoryMySQLTest {
    private static AccountRepository accountRepository;
    private static ClientRepository clientRepository;

    @Before
    public void setUp() throws Exception {
        Connection connection = new DBConnectionFactory().getConnectionWrapper(true).getConnection();
        accountRepository = new AccountRepositoryMySQL(connection);
        clientRepository = new ClientRepositoryMySQL(connection);
        save();
    }

    @After
    public void tearDown() throws Exception {
        removeAll();
    }

    @Test
    public void findAll() {
        List<Account> accountList = accountRepository.findAll();
        Assert.assertEquals(accountList.size(),1);
    }

    @Test
    public void findAllIDs() {
        String[] ids= accountRepository.findAllIDs();
        Assert.assertEquals(ids.length,1);
    }

    @Test
    public void findAllAccountNumbers() {
        String[] accountNumbers = accountRepository.findAllAccountNumbers();
        Assert.assertEquals(accountNumbers.length,1);
    }

    @Test
    public void findById() {
        Account account = accountRepository.findAll().get(0);
        Account account1 = accountRepository.findById(account.getId());

        Assert.assertEquals(account.getIdentification_number(),account1.getIdentification_number());
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
        client = clientRepository.findByName(client.getName());

        Account account = new AccountBuilder().setType("Credit")
                .setBalance((float)1000.99)
                .setIdentificationNumber("RO14 3245 6543 7564")
                .setClientId(client.getId())
                .setDateOfCreation(Date.valueOf(LocalDate.now()))
                .build();
        accountRepository.save(account);
    }

    @Test
    public void update() {
        Account account = accountRepository.findAll().get(0);
        account.setBalance((float) 4999.75);
        accountRepository.update(account);

        Assert.assertTrue(account.getBalance()==(float)4999.75);
    }

    @Test
    public void findByIdentificationNumber() {
        Account account = accountRepository.findAll().get(0);
        Account accountFound = accountRepository.findByIdentificationNumber(account.getIdentification_number());

        Assert.assertEquals(account.getIdentification_number(),accountFound.getIdentification_number());
    }

    @Test
    public void removeByID() {
        Long id = accountRepository.findAll().get(0).getId();
        accountRepository.removeByID(id);

        Assert.assertEquals(accountRepository.findAll().size(),0);
    }

    @Test
    public void removeAll() {
        accountRepository.removeAll();
    }
}

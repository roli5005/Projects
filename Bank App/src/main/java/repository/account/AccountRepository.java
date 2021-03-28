package repository.account;

import model.Account;

import java.util.List;

public interface AccountRepository {
    List<Account> findAll();

    String[] findAllIDs();

    String[] findAllAccountNumbers();

    Account findById(Long id);

    boolean save(Account account);

    boolean update(Account account);

    Account findByIdentificationNumber(String identificationNumber);

    void removeByID(Long id);

    void removeAll();

}

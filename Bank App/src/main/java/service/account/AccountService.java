package service.account;

import model.Account;
import model.validator.Notification;
import repository.account.AccountRepository;

public interface AccountService {
    Notification<Account> createAccount(Account account);

    Notification<Account> updateAccount(Account account);

    void deleteAccount(Long id);

    Account viewAccount(Long id);

    Notification<String> transferMoney(String fromAccountNumber,float amount, String toAccountNumber);

    Notification<Boolean> processBill(Long accountID, float amount);

    String generateIdentificationNumber();

    AccountRepository getRepository();
}

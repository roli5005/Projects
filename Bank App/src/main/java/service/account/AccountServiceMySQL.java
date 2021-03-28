package service.account;

import model.Account;
import model.validator.AccountValidator;
import model.validator.Notification;
import repository.account.AccountRepository;


import java.util.Random;

public class AccountServiceMySQL implements AccountService{
    private AccountRepository accountRepository;

    public AccountServiceMySQL(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    @Override
    public Notification<Account> createAccount(Account account) {
        AccountValidator validator = new AccountValidator(account);
        Notification<Account> accountNotification = new Notification<>();

        if(validator.validate().isEmpty()){
            accountNotification.setResult(account);
            accountRepository.save(account);
        }
        else for (String error: validator.validate()
        ) {
            accountNotification.addError(error);
        }

        return  accountNotification;
    }

    @Override
    public Notification<Account> updateAccount(Account account) {
        AccountValidator validator = new AccountValidator(account);
        Notification<Account> accountNotification = new Notification<>();

        if(validator.validate().isEmpty()){
            accountNotification.setResult(account);
            accountRepository.update(account);
        }
        else for (String error: validator.validate()
        ) {
            accountNotification.addError(error);
        }

        return  accountNotification;
    }

    @Override
    public void deleteAccount(Long id) {
        accountRepository.removeByID(id);
    }

    @Override
    public Account viewAccount(Long id) {
        return accountRepository.findById(id);
    }

    @Override
    public Notification<String> transferMoney(String fromAccountNumber, float amount, String toAccountNumber) {
        Account fromAccount = accountRepository.findByIdentificationNumber(fromAccountNumber);
        Account toAccount = accountRepository.findByIdentificationNumber(toAccountNumber);

        float currentAmountAccount = fromAccount.getBalance();
        fromAccount.setBalance(currentAmountAccount - amount);
        accountRepository.update(fromAccount);

        currentAmountAccount = toAccount.getBalance();
        toAccount.setBalance(currentAmountAccount + amount);
        accountRepository.update(toAccount);

        Notification<String> transferNotification = new Notification<>();
        transferNotification.setResult("Transaction completed successfully!");
        return  transferNotification;
    }

    @Override
    public Notification<Boolean> processBill(Long accountID, float amount) {
        return null;
    }

    @Override
    public String generateIdentificationNumber() {
        String randomIBAN = "RO";
        Random value = new Random();

        int r1 = value.nextInt(10);
        int r2 = value.nextInt(10);

        randomIBAN += r1 +""+ r2 + " ";

        int count = 0;
        int n = 0;
        for(int i =0; i < 12;i++)
        {
            if(count == 4)
            {
                randomIBAN += " ";
                count =0;
            }
            else
                n = value.nextInt(10);
            randomIBAN += Integer.toString(n);
            count++;

        }

        return randomIBAN;
    }

    @Override
    public AccountRepository getRepository() {
        return accountRepository;
    }

}

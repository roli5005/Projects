package model.builder;

import model.Account;

import java.util.Date;

public class AccountBuilder {
    private Account account;

    public AccountBuilder(){account = new Account();}

    public AccountBuilder setId(Long id){
        account.setId(id);
        return this;
    }
    public AccountBuilder setClientId(Long client_id){
        account.setClient_id(client_id);
        return this;
    }
    public AccountBuilder setIdentificationNumber(String identificationNumber){
        account.setIdentification_number(identificationNumber);
        return this;
    }
    public AccountBuilder setType(String type){
        account.setType(type);
        return this;
    }
    public AccountBuilder setBalance(Float balance){
        account.setBalance(balance);
        return this;
    }
    public AccountBuilder setDateOfCreation(Date dateOfCreation){
        account.setDate_of_creation(dateOfCreation);
        return this;
    }
    public Account build(){return account;}
}

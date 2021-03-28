package model.validator;

import model.Account;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class AccountValidator {

    private Account account;
    private List<String> errors;
    private final String IDENTIFICATION_NUMBER = "[A-Z]{2}[0-9]{2} [0-9]{4} [0-9]{4} [0-9]{4}";

    public AccountValidator(Account account){
        this.errors = new ArrayList<>();
        this.account = account;
    }
    public List<String> validate(){
        validateType();
        validateIdentificationNumber();
        return  errors;
    }
    private void validateIdentificationNumber(){
        if(!Pattern.compile(IDENTIFICATION_NUMBER).matcher(account.getIdentification_number()).matches())
            errors.add(account.getIdentification_number()+" Invalid identification number!");
    }
    private void validateType(){
        if (!account.getType().equals("Credit") && !account.getType().equals("Debit"))
            errors.add("Invalid card type!");
    }

}

package model.validator;

import model.Client;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class ClientValidator {
    private final int MIN_NAME_LENGTH = 5;
    private final String CARD_NUMBER_REGEX = "^[0-9]{4} [0-9]{4} [0-9]{4} [0-9]{4}";
    private final String PERSONAL_CODE_REGEX = "[0-9]{13}";
    private final String PHONE_NUMBER_REGEX = "^(\\d{3}[- .]?){2}\\d{4}$";
    private final List<String> errors;

    private final Client client;

    public ClientValidator(Client client) {
        this.client = client;
        errors = new ArrayList<>();
    }
    public List<String> validate() {
        validateName();
        validateCard();
        validatePersonalCode();
        validatePhoneNumber();
        validateAddress();
        return errors;
    }

    private void validateName(){
        if(client.getName().length()<MIN_NAME_LENGTH)
            errors.add("Invalid name!");
    }
    private void validateCard(){
        if(!Pattern.compile(CARD_NUMBER_REGEX).matcher(client.getIdentity_card_number()).matches())
            errors.add("Invalid card number!");
    }
    private void validatePersonalCode(){
        if(!Pattern.compile(PERSONAL_CODE_REGEX).matcher(client.getPersonal_nr_code()).matches())
            errors.add("Invalid personal number code!");
    }
    private void validatePhoneNumber(){
        if(!Pattern.compile(PHONE_NUMBER_REGEX).matcher(client.getPhone_number()).matches())
            errors.add("Invalid phone number!");
    }
    private void validateAddress(){
        if(client.getAddress().equals(""))
            errors.add("Invalid address!");
    }

}

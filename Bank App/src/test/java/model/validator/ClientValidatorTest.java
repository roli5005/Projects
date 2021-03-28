package model.validator;

import model.Client;
import model.builder.ClientBuilder;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ClientValidatorTest {

    @Test
    public void getErrors() {
        Client client = new ClientBuilder().setId(1L)
                .setPhoneNumber("202 555 0125")
                .setName("Ben Dover")
                .setPersonalNumber("1334578933421")
                .setCardNumber("1335 3531 5674 5632")
                .setAddress("Walmart Street")
                .build();
        ClientValidator validator = new ClientValidator(client);
        List<String> noErrors = new ArrayList<String>();
        Assert.assertTrue(validator.validate().isEmpty());
        //Assert.assertNotEquals(validator.getErrors(),noErrors);
    }

}

package model.validator;

import model.Account;
import model.builder.AccountBuilder;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class AccountValidatorTest {

    @Test
    public void getErrors() throws ParseException {
        Account account = new AccountBuilder().setId(1L)
                    .setClientId(2L)
                    .setIdentificationNumber("AT61 1904 3002 3457 3201")
                    .setType("Credit")
                    .setBalance((float)3590.4)
                    .setDateOfCreation(new SimpleDateFormat("dd/MM/yyyy").parse("2/2/2021"))
                    .build();

        AccountValidator validator = new AccountValidator(account);
        List<String> noErrors = new ArrayList<String>();

    }
}

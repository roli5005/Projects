package database;

import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.*;

public class BoostraperTest {

    @Test
    public void execute() throws SQLException {
        Boostraper boostraper = new Boostraper();
        boostraper.execute();
    }
}

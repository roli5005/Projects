package DataAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataConnection {
    public Connection conn = null;
    public static String databaseName = "management";
    public static String url = "jdbc:mysql://localhost:3306/" + databaseName;

    public static String username = "root";
    public static String password = "rootpass";
    public ProductData prod;
    public ClientData client;
    public OrderData order;
    /**
     * constructor
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public DataConnection() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        conn = DriverManager.getConnection(url,username,password);
        prod = new ProductData(conn);
        client = new ClientData(conn);
        order = new OrderData(conn);
    }
}


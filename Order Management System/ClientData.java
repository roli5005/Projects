package DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Model.Client;

public class ClientData {
    public Connection conn;
    public List<Client> clientlist;

    /**
     * constructor
     * @param conn
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    ClientData(Connection conn) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
        this.conn = conn;
        clientlist = new ArrayList<Client>();
    }
    /**
     * creates a new client and inserts it into the list and database
     * @param Name
     * @param Address
     * @throws SQLException
     */
    public void insertClient(String Name,String Address) throws SQLException {
        if(clientExists(Name,Address)==0) {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO `management`.`client` (`Name`,`Address`) VALUES ('"+Name+"','"+Address+"')");
            int status = ps.executeUpdate();

            if(status !=0 )
                System.out.println("success");
            else System.out.println("FAIL\n");
        }

        Client C = new Client();
        C.setName(Name);
        C.setAddress(Address);
        clientlist.add(C);
    }
    /**
     * deletes a client
     * @param Name
     * @throws SQLException
     */
    public void deleteClient(String Name) throws SQLException {
        String sql = "delete from client where Name = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, Name);
        int status = ps.executeUpdate();

        if(status !=0 )
            System.out.println("success");
        else System.out.println("FAIL\n");
        Client C = new Client();
        C.setName(Name);
        int id = this.getClientID(Name);
        C.setAddress(this.getClientAddress(id));
        clientlist.remove(C);
    }
    /**
     * checks if a client exists
     * @param Name
     * @param Address
     * @return
     * @throws SQLException
     */
    int clientExists(String Name, String Address) throws SQLException {
        java.sql.Statement stm = conn.createStatement();
        stm.execute("select Name from client where Name = '"+Name+"'");
        ResultSet result = stm.getResultSet();
        String name = "";
        while(result.next())
            name = result.getString("Name");
        if(name.contentEquals(Name)) {
            stm.execute("select Address from client where Name = '"+Name+"'");
            result = stm.getResultSet();
            while(result.next())
                name = result.getString("Address");
            stm.close();
            if(name.contentEquals(Address))return 1;
            else return 0;
        }
        else return 0;
    }
    /**
     * returns client id
     * @param Name
     * @return
     * @throws SQLException
     */
    int getClientID(String Name) throws SQLException {
        int id=0;
        java.sql.Statement stm = conn.createStatement();
        stm.execute("select idclient from client where Name = '"+Name+"'");
        ResultSet result = stm.getResultSet();
        while(result.next())
            id = result.getInt("idclient");
        return id;
    }
    /**
     * returns client's name
     * @param ID
     * @return
     * @throws SQLException
     */
    String getClientName(int ID) throws SQLException {
        String name="No such client";
        java.sql.Statement stm = conn.createStatement();
        stm.execute("select Name from client where idclient = '"+ID+"'");
        ResultSet result = stm.getResultSet();
        while(result.next())
            name = result.getString("Name");
        return name;
    }
    /**
     * returns client's address
     * @param ID
     * @return
     * @throws SQLException
     */
    String getClientAddress(int ID) throws SQLException {
        String adr="No such address";
        java.sql.Statement stm = conn.createStatement();
        stm.execute("select Address from client where idclient = '"+ID+"'");
        ResultSet result = stm.getResultSet();
        while(result.next())
            adr = result.getString("Address");
        return adr;
    }
    /**
     * returns a list of clients
     * @return
     * @throws SQLException
     */
    public List<Client> getClients()throws SQLException {
        return clientlist;
    }
}

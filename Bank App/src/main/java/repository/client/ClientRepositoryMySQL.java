package repository.client;

import model.Client;
import model.builder.ClientBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientRepositoryMySQL implements ClientRepository{

    private final Connection connection;

    public ClientRepositoryMySQL(Connection connection){
        this.connection = connection;
    }

    @Override
    public List<Client> findAll() {
        List<Client> clients = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from client";
            ResultSet resultSet = statement.executeQuery(sql);

            while(resultSet.next()){
                clients.add(buildClientFromResultset(resultSet));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return clients;
    }

    @Override
    public String[] findAllCardNumbers() {
        List<String> clientCards = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from client";
            ResultSet resultSet = statement.executeQuery(sql);

            while(resultSet.next()){
                clientCards.add(resultSet.getString("identity_card_number"));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return clientCards.toArray(new String[0]);
    }

    @Override
    public Client findById(Long id) {
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from client where id ="+id;
            ResultSet resultSet = statement.executeQuery(sql);

            if (resultSet.next()){
                return buildClientFromResultset(resultSet);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Client findByName(String name){
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from client where name = \'"+name+"\'";
            ResultSet resultSet = statement.executeQuery(sql);

            if (resultSet.next()){
                return buildClientFromResultset(resultSet);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Client findByCardNumber(String cardNumber){
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from client where identity_card_number = \'"+cardNumber+"\'";
            ResultSet resultSet = statement.executeQuery(sql);

            if (resultSet.next()){
                return buildClientFromResultset(resultSet);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean update(Client client) {
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("UPDATE client SET name = ?, identity_card_number = ?, personal_nr_code = ?," +
                            "address = ?, phone_number = ? WHERE id = "+client.getId());
            insertStatement.setString(1, client.getName());
            insertStatement.setString(2, client.getIdentity_card_number());
            insertStatement.setString(3,client.getPersonal_nr_code());
            insertStatement.setString(4,client.getAddress());
            insertStatement.setString(5,client.getPhone_number());
            insertStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean save(Client client) {

        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("INSERT INTO client values (null, ?, ?, ?,?,?)");
            insertStatement.setString(1, client.getName());
            insertStatement.setString(2, client.getIdentity_card_number());
            insertStatement.setString(3,client.getPersonal_nr_code());
            insertStatement.setString(4,client.getAddress());
            insertStatement.setString(5,client.getPhone_number());
            insertStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void removeAll() {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from client where id >= 0";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Client buildClientFromResultset(ResultSet resultSet) throws SQLException {
        return new ClientBuilder().setId(resultSet.getLong("id"))
                .setName(resultSet.getString("name"))
                .setCardNumber(resultSet.getString("identity_card_number"))
                .setPersonalNumber(resultSet.getString("personal_nr_code"))
                .setAddress(resultSet.getString("address"))
                .setPhoneNumber(resultSet.getString("phone_number"))
                .build();
    }
}

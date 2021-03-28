package repository.account;

import model.Account;
import model.builder.AccountBuilder;

import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountRepositoryMySQL implements AccountRepository{
    private Connection connection;

    public AccountRepositoryMySQL(Connection connection){
        this.connection = connection;
    }

    @Override
    public List<Account> findAll() {
        List<Account> accounts = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from client_account";
            ResultSet resultSet = statement.executeQuery(sql);

            while(resultSet.next()){
                accounts.add(buildAccountFromResultset(resultSet));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return accounts;
    }

    @Override
    public String[] findAllIDs() {
        List<String> accounts = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from client_account";
            ResultSet resultSet = statement.executeQuery(sql);

            while(resultSet.next()){
                Account account = buildAccountFromResultset(resultSet);
                accounts.add(account.getId().toString());
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return accounts.toArray(accounts.toArray(new String[0]));
    }

    @Override
    public String[] findAllAccountNumbers() {
        List<String> accounts = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from client_account";
            ResultSet resultSet = statement.executeQuery(sql);

            while(resultSet.next()){
                Account account = buildAccountFromResultset(resultSet);
                accounts.add(account.getIdentification_number());
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return accounts.toArray(accounts.toArray(new String[0]));
    }

    @Override
    public Account findById(Long id) {
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from client_account where id ="+id;
            ResultSet resultSet = statement.executeQuery(sql);

            if (resultSet.next()){
                return buildAccountFromResultset(resultSet);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean save(Account account) {
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("INSERT INTO client_account values (null, ?, ?, ?, ?, ?)");
            insertStatement.setLong(1, account.getClient_id());
            insertStatement.setString(2,account.getIdentification_number());
            insertStatement.setString(3,account.getType());
            insertStatement.setFloat(4,account.getBalance());
            insertStatement.setDate(5, new java.sql.Date(account.getDate_of_creation().getTime()));
            insertStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Account account) {
        try {
            PreparedStatement updateStatement = connection
                    .prepareStatement("UPDATE client_account SET  identification_number = ?" +
                            ", `type` = ?, balance = ? WHERE id = "+account.getId());
            updateStatement.setString(1,account.getIdentification_number());
            updateStatement.setString(2,account.getType());
            updateStatement.setFloat(3,account.getBalance());
            updateStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Account findByIdentificationNumber(String identificationNumber) {
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from client_account where `identification_number` = \'"+identificationNumber+"\'";
            ResultSet resultSet = statement.executeQuery(sql);

            if (resultSet.next()){
                return buildAccountFromResultset(resultSet);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public void removeByID(Long id) {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from client_account where id = "+id;
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeAll() {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from client_account where id >= 0";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Account buildAccountFromResultset(ResultSet resultSet) throws SQLException {
        return new AccountBuilder().setId(resultSet.getLong("id"))
                .setClientId(resultSet.getLong("client_id"))
                .setIdentificationNumber(resultSet.getString("identification_number"))
                .setType(resultSet.getString("type"))
                .setBalance(resultSet.getFloat("balance"))
                .setDateOfCreation(resultSet.getDate("date_of_creation"))
                .build();
    }
}

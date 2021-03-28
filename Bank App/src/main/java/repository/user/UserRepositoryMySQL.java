package repository.user;

import model.User;
import model.builder.UserBuilder;
import model.validator.Notification;
import repository.security.RightsRolesRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryMySQL implements UserRepository{

    private final Connection connection;
    private RightsRolesRepository rightsRolesRepository;

    public UserRepositoryMySQL(Connection connection, RightsRolesRepository rightsRolesRepository){
        this.rightsRolesRepository = rightsRolesRepository;
        this.connection = connection;
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from user";
            ResultSet resultSet = statement.executeQuery(sql);

            while(resultSet.next()){
                users.add( buildUserFromResultset(resultSet));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return users;
    }


    private User buildUserFromResultset(ResultSet resultSet) throws SQLException {
        return new UserBuilder()
                .setId(resultSet.getLong("id"))
                .setUsername(resultSet.getString("username"))
                .setPassword(resultSet.getString("password"))
                .setRoles(rightsRolesRepository.findRolesForUser(resultSet.getLong("id")))
                .build();
    }

    @Override
    public User findById(Long id) {
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from user where id ="+id;
            ResultSet resultSet = statement.executeQuery(sql);

            if (resultSet.next()){
                return buildUserFromResultset(resultSet);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public User findUserByUsername(String username) {
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from user where username = \'" + username + "\'";
            ResultSet resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
                User user = buildUserFromResultset(resultSet);
                return user;
            }

            } catch(SQLException throwables){
                throwables.printStackTrace();
            }
            return null;
    }

    @Override
    public Notification<User> findEmployeeByUsernameAndPassword(String username, String password) {
        Notification<User> findEmployee = new Notification<>();
        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from user where username = \'"+username+"\' and password = \'"+password+"\'";
            ResultSet resultSet = statement.executeQuery(sql);

            if(resultSet.next()){
                sql = "SELECT * from user_role where user_id = "+resultSet.getLong("id");
                User user = buildUserFromResultset(resultSet);
                ResultSet res = statement.executeQuery(sql);

                if(res.next() && res.getLong("role_id")==2){
                    findEmployee.setResult(user);
                }
                else findEmployee.addError("Not a valid employee account!");
            }
            else findEmployee.addError("Invalid email or password!");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return findEmployee;
    }

    @Override
    public Notification<User> findAdminByUsernameAndPassword(String username, String password){
        Notification<User> findAdmin = new Notification<>();

        try {
            Statement statement = connection.createStatement();
            String sql = "Select * from user where username = \'"+username+"\' and password = \'"+password+"\'";
            ResultSet resultSet = statement.executeQuery(sql);

            if(resultSet.next()){
                String sql2 = "SELECT * from user_role where user_id = "+resultSet.getLong("id");
                User user = buildUserFromResultset(resultSet);
                ResultSet res = statement.executeQuery(sql2);

                if(res.next() && res.getLong("role_id")==1){
                    findAdmin.setResult(user);
                }
                else findAdmin.addError("Not an admin account!");
            }
            else findAdmin.addError("Invalid email or password!");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return findAdmin;
    }

    @Override
    public boolean save(User user) {
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("INSERT INTO user values (null, ?, ?)",Statement.RETURN_GENERATED_KEYS);
            insertStatement.setString(1, user.getUsername());
            insertStatement.setString(2, user.getPassword());
            insertStatement.executeUpdate();

            ResultSet rs = insertStatement.getGeneratedKeys();
            rs.next();
            long userId = rs.getLong(1);
            user.setId(userId);

            rightsRolesRepository.addRolesToUser(user, user.getRoles());

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(User user) {
        try {
            PreparedStatement insertStatement = connection
                    .prepareStatement("UPDATE user SET password = ? WHERE `id` = "+user.getId());
            insertStatement.setString(1, user.getPassword());
            insertStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void removeById(Long id) {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from user where `id` = "+id;
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeAll() {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from user where id >= 0";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

package repository.user;

import database.DBConnectionFactory;
import model.Role;
import model.User;
import model.builder.UserBuilder;
import model.validator.Notification;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static database.Constants.Roles.ADMINISTRATOR;
import static org.junit.Assert.*;

public class UserRepositoryMySQLTest {
    private static UserRepository userRepository;
    private static RightsRolesRepository rightsRolesRepository;

    @Before
    public void setUp() throws Exception {
        Connection connection = new DBConnectionFactory().getConnectionWrapper(true).getConnection();
        rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        userRepository = new UserRepositoryMySQL(connection,rightsRolesRepository);
        save();
    }

    @After
    public void tearDown() throws Exception {
        removeAll();
    }

    @Test
    public void findAll() {
        List<User> users = userRepository.findAll();
        Assert.assertEquals(users.size(),2);
    }

    @Test
    public void findById() {
        User user = userRepository.findAll().get(1);
        User userFound = userRepository.findById(user.getId());

        Assert.assertEquals(user.getUsername(),userFound.getUsername());
    }

    @Test
    public void findUserByUsername() {
        User user = userRepository.findAll().get(1);
        User userFound = userRepository.findUserByUsername(user.getUsername());

        Assert.assertEquals(user.getUsername(),userFound.getUsername());
    }

    @Test
    public void findEmployeeByUsernameAndPassword() {
        User employee = userRepository.findAll().get(1);
        User admin = userRepository.findAll().get(0);

        Notification<User> notification = userRepository.findEmployeeByUsernameAndPassword(employee.getUsername(), employee.getPassword());
        assertFalse(notification.hasErrors());

        notification = userRepository.findEmployeeByUsernameAndPassword(admin.getUsername(), admin.getPassword());
        assertTrue(notification.hasErrors());
    }

    @Test
    public void findAdminByUsernameAndPassword() {
        User employee = userRepository.findAll().get(1);
        User admin = userRepository.findAll().get(0);

        Notification<User> notification = userRepository.findAdminByUsernameAndPassword(employee.getUsername(), employee.getPassword());
        assertTrue(notification.hasErrors());

        notification = userRepository.findAdminByUsernameAndPassword(admin.getUsername(), admin.getPassword());
        assertFalse(notification.hasErrors());
    }

    @Test
    public void save() {
        removeAll();
        List<Role> adminRoles = new ArrayList<>();
        adminRoles.add(rightsRolesRepository.findRoleByTitle("administrator"));
        User admin = new UserBuilder().setUsername("admin13@mail.com")
                .setPassword("StrongPass13*")
                .setRoles(adminRoles)
                .build();
        userRepository.save(admin);

        List<Role> employeeRoles = new ArrayList<>();
        employeeRoles.add(rightsRolesRepository.findRoleByTitle("employee"));
        User employee = new UserBuilder().setUsername("employee1@mail.com")
                .setPassword("Employee1*")
                .setRoles(employeeRoles)
                .build();
        userRepository.save(employee);

        Assert.assertEquals(userRepository.findAll().size(),2);
    }

    @Test
    public void update() {
        User admin = userRepository.findAll().get(0);
        admin.setPassword("StrongerPass43*");
        userRepository.update(admin);
        Assert.assertEquals(userRepository.findUserByUsername(admin.getUsername()).getPassword(),admin.getPassword());
    }

    @Test
    public void removeById() {
        User user = userRepository.findAll().get(0);
        String username = user.getUsername();
        userRepository.removeById(user.getId());

        Assert.assertNotEquals(username,userRepository.findAll().get(0).getUsername());

    }

    @Test
    public void removeAll() {
        userRepository.removeAll();
    }
}

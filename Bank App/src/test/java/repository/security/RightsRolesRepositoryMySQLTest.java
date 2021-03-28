package repository.security;

import database.DBConnectionFactory;
import model.Right;
import model.Role;
import model.User;
import model.builder.UserBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class RightsRolesRepositoryMySQLTest {
    private static RightsRolesRepository rightsRolesRepository;
    private static UserRepository userRepository;
    @Before
    public void setUp() throws Exception {
        Connection connection = new DBConnectionFactory().getConnectionWrapper(true).getConnection();
        rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        userRepository = new UserRepositoryMySQL(connection,rightsRolesRepository);
    }

    @Test
    public void addRole() {
        String role = "Client";
        rightsRolesRepository.addRole(role);

        Role role1 = rightsRolesRepository.findRoleByTitle("Client");
        Assert.assertEquals(role1.getRole(),"Client");
    }

    @Test
    public void addRight() {
        String right = "Testing";
        rightsRolesRepository.addRight(right);

        Right right1 = rightsRolesRepository.findRightByTitle(right);
        Assert.assertEquals(right,right1.getRight());
    }

    @Test
    public void findRoleByTitle() {
        Role role1 = rightsRolesRepository.findRoleByTitle("administrator");
        Assert.assertEquals(role1.getRole(),"administrator");
    }

    @Test
    public void findRoleById() {
        Role role = rightsRolesRepository.findRoleById(1L);//admin role id
        Role role1 = rightsRolesRepository.findRoleByTitle("administrator");
        Assert.assertEquals(role.getRole(),role1.getRole());
    }

    @Test
    public void findRightByTitle() {
        Right right1 = rightsRolesRepository.findRightByTitle("add_client");
        Assert.assertEquals("add_client",right1.getRight());
    }

    @Test
    public void addRolesToUser() {
        userRepository.removeAll();
        Role userRole = rightsRolesRepository.findRoleByTitle("employee");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(userRole);
        User user = new UserBuilder().setUsername("testUser@mail.com")
                .setPassword("TestPass34*")
                .setRoles(userRoles)
                .build();
        userRepository.save(user);

        user = userRepository.findUserByUsername(user.getUsername());
        userRoles.add(rightsRolesRepository.findRoleByTitle("administrator"));
        rightsRolesRepository.addRolesToUser(user,userRoles);
        user = userRepository.findUserByUsername(user.getUsername());

        Assert.assertEquals(user.getRoles().get(1).getRole(),userRole.getRole());
    }

    @Test
    public void findRolesForUser() {
        userRepository.removeAll();
        Role userRole = rightsRolesRepository.findRoleByTitle("employee");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(userRole);
        User user = new UserBuilder().setUsername("testUser@mail.com")
                .setPassword("TestPass34*")
                .setRoles(userRoles)
                .build();
        userRepository.save(user);

        user = userRepository.findUserByUsername(user.getUsername());
        List<Role> roles = rightsRolesRepository.findRolesForUser(user.getId());

        Assert.assertEquals(roles.get(0).getRole(),user.getRoles().get(0).getRole());
    }

    @Test
    public void addRoleRight() {
        Role role = rightsRolesRepository.findRoleByTitle("administrator");
        Right right = rightsRolesRepository.findRightByTitle("add_client");
        rightsRolesRepository.addRoleRight(role.getId(), right.getId());
        List<Right> rights = rightsRolesRepository.findRightsForRole(role.getId());

        boolean contains = false;
        for (Right r:rights) {
            if(r.getRight().equals(right.getRight()))
                contains = true;
        }
        Assert.assertTrue(contains);

    }

    @Test
    public void findRightsForRole() {
        Role role = rightsRolesRepository.findRoleByTitle("administrator");
        Right right = rightsRolesRepository.findRightByTitle("add_client");
        rightsRolesRepository.addRoleRight(role.getId(), right.getId());
        List<Right> rights = rightsRolesRepository.findRightsForRole(role.getId());

        boolean contains = false;
        for (Right r:rights) {
            if(r.getRight().equals(right.getRight()))
                contains = true;
        }
        Assert.assertTrue(contains);
    }
}

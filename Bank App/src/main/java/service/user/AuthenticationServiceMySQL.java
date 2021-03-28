package service.user;

import model.Role;
import model.User;
import model.builder.UserBuilder;
import model.validator.Notification;
import model.validator.UserValidator;
import repository.security.RightsRolesRepository;
import repository.user.UserRepository;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Collections;
import java.util.List;

import static database.Constants.Roles.EMPLOYEE;


public class AuthenticationServiceMySQL implements AuthenticationService {

    private final UserRepository userRepository;
    private final RightsRolesRepository rightsRolesRepository;

    public AuthenticationServiceMySQL(UserRepository userRepository, RightsRolesRepository rightsRolesRepository) {
        this.userRepository = userRepository;
        this.rightsRolesRepository = rightsRolesRepository;
    }

    @Override
    public Notification<Boolean> register(String username, String password) {
        Role employeeRole = rightsRolesRepository.findRoleByTitle(EMPLOYEE);
        User user = new UserBuilder()
                .setUsername(username)
                .setPassword(password)
                .setRoles(Collections.singletonList(employeeRole))
                .build();

        UserValidator userValidator = new UserValidator(user);
        boolean userValid = userValidator.validate().isEmpty();
        Notification<Boolean> userRegisterNotification = new Notification<>();

        if (!userValid) {
            userValidator.getErrors().forEach(userRegisterNotification::addError);
            userRegisterNotification.setResult(Boolean.FALSE);
        } else {
            if(!checkIfUserExisting(user)) {
                user.setPassword(encodePassword(password));
                userRegisterNotification.setResult(userRepository.save(user));
            }
            else userRegisterNotification.addError("Username already existing!");
        }

        return userRegisterNotification;
    }

    @Override
    public Notification<User> employeeLogin(String username, String password) {
        return userRepository.findEmployeeByUsernameAndPassword(username, encodePassword(password));
    }

    @Override
    public Notification<User> adminLogin(String username, String password) {
        return userRepository.findAdminByUsernameAndPassword(username,encodePassword(password));
    }

    @Override
    public boolean logout(User user) {
        return false;
    }

    public String encodePassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private boolean checkIfUserExisting(User user){
        List<User> users = userRepository.findAll();
        for (User x:users
             ) {
            if(x.getUsername().equals(user.getUsername())) return true;
        }
        return false;
    }
}

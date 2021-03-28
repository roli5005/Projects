package service.user;

import model.User;
import model.validator.Notification;

/**
 * Created by Alex on 11/03/2017.
 */
public interface AuthenticationService {

    Notification<Boolean> register(String username, String password);

    Notification<User> employeeLogin(String username, String password);

    Notification<User> adminLogin(String username, String password);

    boolean logout(User user);

}

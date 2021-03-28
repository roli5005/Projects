package repository.user;

import model.User;
import model.validator.Notification;

import java.util.List;

public interface UserRepository {

    List<User> findAll();

    User findById(Long id);

    User findUserByUsername(String username);

    Notification<User> findEmployeeByUsernameAndPassword(String username, String password);

    Notification<User> findAdminByUsernameAndPassword(String username, String password);

    boolean save(User user);

    boolean update(User user);

    void removeById(Long id);

    void removeAll();
}

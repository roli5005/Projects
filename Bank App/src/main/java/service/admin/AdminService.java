package service.admin;

import model.Activity;
import model.User;
import model.validator.Notification;
import repository.user.UserRepository;

import java.sql.Date;
import java.util.List;

public interface AdminService {
    Notification<User> createEmployee(User user);

    Notification<User> readEmployee(Long id);

    Notification<User> updateEmployee(User user);

    void deleteEmployee(Long id);

    Notification<List<Activity>> generateReports(String date);

    UserRepository getRepository();
}

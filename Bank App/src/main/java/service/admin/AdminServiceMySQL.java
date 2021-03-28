package service.admin;

import model.Activity;
import model.Role;
import model.User;
import model.validator.Notification;
import model.validator.UserValidator;
import repository.activities.ActivityRepository;
import repository.security.RightsRolesRepository;
import repository.user.UserRepository;

import java.awt.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static database.Constants.Roles.EMPLOYEE;

public class AdminServiceMySQL implements AdminService {
    private UserRepository userRepository;
    private ActivityRepository activityRepository;
    private RightsRolesRepository rightsRolesRepository;

    public AdminServiceMySQL(UserRepository userRepository, ActivityRepository activityRepository, RightsRolesRepository rightsRolesRepository){
        this.userRepository =  userRepository;
        this.activityRepository = activityRepository;
        this.rightsRolesRepository = rightsRolesRepository;
    }

    @Override
    public Notification<User> createEmployee(User user) {
        Notification<User> userNotification = new Notification<>();

        UserValidator validator = new UserValidator(user);

        if(validator.validate().isEmpty()){

            if(userRepository.findUserByUsername(user.getUsername())==null) {
                List<Role> employeeRoles = new ArrayList<>();
                employeeRoles.add(rightsRolesRepository.findRoleById(2L));
                user.setPassword(encodePassword(user.getPassword()));
                user.setRoles(employeeRoles);
                userRepository.save(user);
                userNotification.setResult(user);
            }
            else userNotification.addError("Username is taken!");
        }
        else {
            for (String error: validator.validate()
                 ) {
                userNotification.addError(error);
            }
        }

        return userNotification;
    }

    @Override
    public Notification<User> readEmployee(Long id) {
        Notification<User> userNotification = new Notification<>();

        if(userRepository.findById(id)!=null) {
            User user = userRepository.findById(id);
            userNotification.setResult(user);
        }
        else userNotification.addError("No employee with id = "+id);

        return userNotification;
    }

    @Override
    public Notification<User> updateEmployee(User user) {
        UserValidator validator = new UserValidator(user);
        Notification<User> userNotification = new Notification<>();

        if(validator.validate().isEmpty()){
            user.setPassword(encodePassword(user.getPassword()));
            userRepository.update(user);
            userNotification.setResult(user);
        }
        else{
            for (String error: validator.validate()
            ) {
                userNotification.addError(error);
            }
        }
        return userNotification;
    }

    @Override
    public void deleteEmployee(Long id) {
        userRepository.removeById(id);
    }

    @Override
    public Notification<List<Activity>> generateReports(String date) {
        Notification<List<Activity>> notification = new Notification<>();
        if(isDateValid(date)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate localDate = LocalDate.parse(date, formatter);
            Date activityDate = Date.valueOf(localDate);

            List<Activity> activityList = activityRepository.findBeforeDate(activityDate);
            notification.setResult(activityList);
        }
        else notification.addError("Invalid date!");
        return notification;
    }

    @Override
    public UserRepository getRepository() {
        return userRepository;
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

    private boolean isDateValid(String date)
    {
        try {
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            df.setLenient(false);
            df.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}

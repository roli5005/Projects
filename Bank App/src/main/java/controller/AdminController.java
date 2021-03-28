package controller;

import model.Activity;
import model.User;
import model.builder.UserBuilder;
import model.validator.Notification;
import service.admin.AdminService;
import view.AdminView;
import view.LoginView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.regex.Pattern;

public class AdminController {
    private AdminView adminView;
    private AdminService adminService;
    private long adminID = -1L;

    public AdminController(AdminView adminView, AdminService adminService,long id){
        this.adminView = adminView;
        this.adminService = adminService;
        adminID = id;
        setButtons();
    }

    private void setButtons(){
        adminView.setCreateEmployeeListener(new CreateEmployeeListener());
        adminView.setReadEmployeeListener(new ReadEmployeeListener());
        adminView.setUpdateEmployeeListener(new UpdateEmployeeListener());
        adminView.setDeleteEmployeeListener(new DeleteEmployeeListener());
        adminView.setGenerateReportsListener(new GenerateReportListener());
        adminView.setLogoutListener(new LogoutListener());
    }

    private class CreateEmployeeListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            Notification<User> userNotification = new Notification<>();
            String username = adminView.getUsername();
            String password = adminView.getPassword();
            User user = new UserBuilder().setUsername(username)
                    .setPassword(password)
                    .build();
            userNotification = adminService.createEmployee(user);
            if(userNotification.hasErrors()){
                JOptionPane.showMessageDialog(adminView.getContentPane(), userNotification.getFormattedErrors());
            }else{
                JOptionPane.showMessageDialog(adminView.getContentPane(), "Employee Created successfully!");
            }
        }
    }

    private class ReadEmployeeListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {

            if(Pattern.matches("[0-9]+", adminView.getId())) {
                Notification<User> userNotification = new Notification<>();
                long id = Long.parseLong(adminView.getId());
                userNotification = adminService.readEmployee(id);

                if (userNotification.hasErrors()) {
                    JOptionPane.showMessageDialog(adminView.getContentPane(), userNotification.getFormattedErrors());
                } else {
                    User user = userNotification.getResult();

                    String userInfo = "Username: " + user.getUsername() + "\n Role:" + user.getRoles().get(0).getRole();
                    JOptionPane.showMessageDialog(adminView.getContentPane(), userInfo);
                }
            }
            else
                JOptionPane.showMessageDialog(adminView.getContentPane(), "Insert user ID!");
        }
    }

    private class UpdateEmployeeListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(Pattern.matches("[0-9]+", adminView.getId())) {
                Notification<User> userNotification = new Notification<>();
                long id = Long.parseLong(adminView.getId());
                userNotification = adminService.readEmployee(id);

                if (userNotification.hasErrors()) {

                    JOptionPane.showMessageDialog(adminView.getContentPane(), userNotification.getFormattedErrors());
                } else {
                    User user = userNotification.getResult();
                    String password = adminView.getPassword();
                    user.setPassword(password);

                    userNotification = adminService.updateEmployee(user);

                    if(userNotification.hasErrors()){
                        JOptionPane.showMessageDialog(adminView.getContentPane(), userNotification.getFormattedErrors());
                    }
                    else {
                        String userInfo = "Password for user "+user.getUsername()+"\n was changed to "+password;
                        JOptionPane.showMessageDialog(adminView.getContentPane(), userInfo);
                    }
                }
            }
            else
                JOptionPane.showMessageDialog(adminView.getContentPane(), "Insert user ID!");

        }
    }

    private class DeleteEmployeeListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(Pattern.matches("[0-9]+", adminView.getId())) {
                Notification<User> userNotification = new Notification<>();
                long id = Long.parseLong(adminView.getId());

                userNotification = adminService.readEmployee(id);
                if(userNotification.hasErrors())
                    JOptionPane.showMessageDialog(adminView.getContentPane(), userNotification.getFormattedErrors());
                else{
                    adminService.deleteEmployee(id);
                    JOptionPane.showMessageDialog(adminView.getContentPane(), "User deleted successfully!");
                }
            }
            else JOptionPane.showMessageDialog(adminView.getContentPane(), "Insert user ID!");

        }
    }

    private class GenerateReportListener implements ActionListener{


        @Override
        public void actionPerformed(ActionEvent e) {
            if(Pattern.matches("[0-9]{2}/[0-9]{2}/[0-9]{4}",adminView.getDate())) {
                String date = adminView.getDate();

                Notification<List<Activity>> notification = new Notification<>();
                notification = adminService.generateReports(date);

                if (notification.hasErrors()) {
                    JOptionPane.showMessageDialog(adminView.getContentPane(), notification.getFormattedErrors());
                } else {
                    List<Activity> activityList = notification.getResult();
                    if (activityList.isEmpty()) {
                        JOptionPane.showMessageDialog(adminView.getContentPane(), "No new activities since" +
                                date);
                    } else {
                        String showActivities = "";
                        for (Activity activity : activityList)
                            showActivities += activity.getDate() + "  |  " + activity.getAction() + "  |  "
                                    + activity.getAmount_of_money() + "  |  " + activity.getDetails() + "\n";
                        JOptionPane.showMessageDialog(adminView.getContentPane(), showActivities);
                    }
                }
            }
            else
            JOptionPane.showMessageDialog(adminView.getContentPane(), "Insert valid date!\n" +
                    "Format should be: dd/MM/yyyy");
        }
    }
    private class LogoutListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            adminID = -1;
        }
    }

    public AdminView getAdminView(){return adminView;}

    public long getAdminID(){return adminID;}

    public void setAdminID(long id){adminID = id;
    }

}

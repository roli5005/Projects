package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static javax.swing.BoxLayout.Y_AXIS;

public class AdminView extends JFrame{
    private JButton createEmployee;
    private JButton readEmployee;
    private JButton updateEmployee;
    private JButton deleteEmployee;
    private JButton generateReports;
    private JButton logout;

    private JTextField username;
    private JTextField password;
    private JTextField id;
    private JTextField date;

    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JLabel idLabel;
    private JLabel dateLabel;


    public AdminView() throws HeadlessException {
        setSize(300, 330);
        setLocationRelativeTo(null);
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
        initializeFields();
        add(createEmployee);
        add(readEmployee);
        add(updateEmployee);
        add(deleteEmployee);

        add(usernameLabel);
        add(username);
        add(passwordLabel);
        add(password);
        add(idLabel);
        add(id);

        add(generateReports);
        add(dateLabel);
        add(date);
        add(logout);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void initializeFields(){
        createEmployee = new JButton("Create Employee");
        readEmployee = new JButton("Read Employee");
        updateEmployee = new JButton("Update Employee");
        deleteEmployee = new JButton("Delete Employee");
        generateReports = new JButton("Generate Reports");
        logout = new JButton("Logout");

        username = new JTextField();
        password = new JTextField();
        id = new JTextField();
        date = new JTextField();

        usernameLabel = new JLabel("username: ");
        passwordLabel = new JLabel("password: ");
        idLabel = new JLabel("User id");
        dateLabel = new JLabel("Date");
    }

    public String getUsername() {
        return username.getText();
    }

    public String getPassword() {
        return password.getText();
    }

    public String getId() {
        return id.getText();
    }

    public String getDate(){return date.getText();}

    public void setCreateEmployeeListener(ActionListener actionListener){
        createEmployee.addActionListener(actionListener);
    }

    public void setReadEmployeeListener(ActionListener actionListener){
        readEmployee.addActionListener(actionListener);
    }

    public void setUpdateEmployeeListener(ActionListener actionListener){
        updateEmployee.addActionListener(actionListener);
    }

    public void setDeleteEmployeeListener(ActionListener actionListener){
        deleteEmployee.addActionListener(actionListener);
    }

    public void setGenerateReportsListener(ActionListener actionListener){
        generateReports.addActionListener(actionListener);
    }
    public void setLogoutListener(ActionListener actionListener){
        logout.addActionListener(actionListener);
    }
}

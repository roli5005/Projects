package launcher;

import controller.AdminController;
import controller.AppController;
import controller.EmployeeController;
import controller.LoginController;
import database.DBConnectionFactory;
import model.Role;
import model.User;
import model.builder.UserBuilder;
import repository.account.AccountRepository;
import repository.account.AccountRepositoryMySQL;
import repository.activities.ActivityRepository;
import repository.activities.ActivityRepositoryMySQL;
import repository.client.ClientRepository;
import repository.client.ClientRepositoryMySQL;
import repository.security.RightsRolesRepository;
import repository.security.RightsRolesRepositoryMySQL;
import repository.user.UserRepository;
import repository.user.UserRepositoryMySQL;
import service.account.AccountService;
import service.account.AccountServiceMySQL;
import service.activity.ActivityService;
import service.activity.ActivityServiceMySQL;
import service.admin.AdminService;
import service.admin.AdminServiceMySQL;
import service.client.ClientService;
import service.client.ClientServiceMySQL;
import service.user.AuthenticationService;
import service.user.AuthenticationServiceMySQL;
import view.AdminView;
import view.EmployeeView;
import view.LoginView;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class ComponentFactory {

    private final LoginView loginView;
    private final AdminView adminView;
    private final EmployeeView employeeView;

    private final LoginController loginController;
    private final AdminController adminController;
    private final EmployeeController employeeController;
    private final AppController appController;

    private final AuthenticationServiceMySQL authenticationService;
    private final ClientService clientService;
    private final AccountService accountService;
    private final ActivityService activityService;
    private final AdminService adminService;

    private final UserRepository userRepository;
    private final RightsRolesRepository rightsRolesRepository;
    private final ClientRepository clientRepository;
    private final AccountRepository accountRepository;
    private final ActivityRepository activityRepository;

    private static ComponentFactory instance;

    public static ComponentFactory instance(Boolean componentsForTests) {
        if (instance == null) {
            instance = new ComponentFactory(componentsForTests);
        }
        return instance;
    }

    private ComponentFactory(Boolean componentsForTests) {
        Connection connection = new DBConnectionFactory().getConnectionWrapper(componentsForTests).getConnection();
        rightsRolesRepository = new RightsRolesRepositoryMySQL(connection);
        clientRepository = new ClientRepositoryMySQL(connection);
        accountRepository = new AccountRepositoryMySQL(connection);
        activityRepository = new ActivityRepositoryMySQL(connection);
        userRepository = new UserRepositoryMySQL(connection, rightsRolesRepository);
        authenticationService = new AuthenticationServiceMySQL(this.userRepository, this.rightsRolesRepository);
        clientService = new ClientServiceMySQL(this.clientRepository);
        accountService = new AccountServiceMySQL(this.accountRepository);
        activityService = new ActivityServiceMySQL(this.activityRepository);
        loginView = new LoginView();
        employeeView = new EmployeeView(clientRepository.findAllCardNumbers(), accountRepository.findAllAccountNumbers());
        adminView = new AdminView();
        adminService = new AdminServiceMySQL(userRepository,activityRepository,rightsRolesRepository);
        adminController = new AdminController(adminView,adminService,-1L);
        loginController = new LoginController(loginView, authenticationService);
        employeeController = new EmployeeController(employeeView, clientService, accountService,activityService,-1L);

        appController = new AppController(adminController,employeeController,loginController);
    }

    public void InstantiateUsers(){
        List<Role> adminRoles = new ArrayList<>();
        adminRoles.add(rightsRolesRepository.findRoleById(1L));
        User user = new UserBuilder().setUsername("admin@mail.com")
                .setPassword(authenticationService.encodePassword("admin1*"))
                .setRoles(adminRoles)
                .build();
        userRepository.save(user);

        List<Role> empRoles = new ArrayList<>();
        empRoles.add(rightsRolesRepository.findRoleById(2L));
        User user2 = new UserBuilder().setUsername("employee@mail.com")
                .setPassword(authenticationService.encodePassword("employee2*"))
                .setRoles(empRoles)
                .build();
        userRepository.save(user2);
    }






    public AuthenticationService getAuthenticationService() {
        return authenticationService;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public RightsRolesRepository getRightsRolesRepository() {
        return rightsRolesRepository;
    }

    public LoginView getLoginView() {
        return loginView;
    }

    public EmployeeView getEmployeeView() { return employeeView; }

    public AdminView getAdminView(){return adminView;}

    public AccountRepository getAccountRepository(){return accountRepository;}

    public ClientRepository getClientRepository(){return clientRepository;}

    public LoginController getLoginController() {
        return loginController;
    }

    public AppController getAppController(){return appController;}

}

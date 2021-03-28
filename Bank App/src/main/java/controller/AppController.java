package controller;


public class AppController {
    private AdminController adminController;
    private EmployeeController employeeController;
    private LoginController loginController;


    public AppController(AdminController adminController, EmployeeController employeeController, LoginController loginController) {
        this.adminController = adminController;
        this.employeeController = employeeController;
        this.loginController = loginController;
    }

    public void RunApp(){
        while(true){
            if(loginController.getAdminID() == -1 && loginController.getEmployeeID() == -1)
            while(loginController.getAdminID() == -1 && loginController.getEmployeeID() == -1){

            }
            else
            if(loginController.getAdminID()!=-1){
                adminController.setAdminID(loginController.getAdminID());
                while(adminController.getAdminID()!=-1){

                }
                loginController.setAdminID(-1L);
            }
            else
            if(loginController.getEmployeeID()!=-1){
                employeeController.setEmployeeID(loginController.getEmployeeID());

                while (employeeController.getEmployeeID()!=-1){

                }
                loginController.setEmployeeID(-1L);
            }
        }
    }
}

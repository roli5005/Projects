package launcher;

import database.Boostraper;

import java.sql.SQLException;


public class Launcher {

    public static boolean BOOTSTRAP = false;

    public static void main(String[] args) {
        bootstrap();

        ComponentFactory componentFactory = ComponentFactory.instance(false);
        componentFactory.getAdminView().setVisible(true);
        componentFactory.getEmployeeView().setVisible(true);
        componentFactory.getLoginView().setVisible(true);

        componentFactory.getAppController().RunApp();
    }

    private static void bootstrap() {
        if (BOOTSTRAP) {
            try {
                new Boostraper().execute();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

}

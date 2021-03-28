package database;

import java.util.*;

import static database.Constants.Rights.*;
import static database.Constants.Roles.*;

public class Constants {

    public static class Schemas {
        public static final String TEST = "bank_database_test";
        public static final String PRODUCTION = "bank_database";

        public static final String[] SCHEMAS = new String[]{TEST, PRODUCTION};
    }

    public static class Tables {

        public static final String USER = "user";
        public static final String ROLE = "role";
        public static final String RIGHT = "right";
        public static final String ROLE_RIGHT = "role_right";
        public static final String USER_ROLE = "user_role";
        public static final String CLIENT = "client";
        public static final String CLIENT_ACCOUNT = "client_account";
        public static final String ACTIVITIES = "activities";

        public static final String[] ORDERED_TABLES_FOR_CREATION = new String[]{USER, ROLE, RIGHT, ROLE_RIGHT,USER_ROLE, CLIENT,CLIENT_ACCOUNT,ACTIVITIES};
    }

    public static class Roles {
        public static final String ADMINISTRATOR = "administrator";
        public static final String EMPLOYEE = "employee";

        public static final String[] ROLES = new String[]{ADMINISTRATOR, EMPLOYEE};
    }

    public static class Rights {
        public static final String ADD_CLIENT = "add_client";
        public static final String UPDATE_CLIENT = "update_client";
        public static final String VIEW_CLIENT = "view_client";

        public static final String CREATE_ACCOUNT = "create_account";
        public static final String UPDATE_ACCOUNT = "update_account";
        public static final String DELETE_ACCOUNT = "delete_account";
        public static final String VIEW_ACCOUNT = "view_account";

        public static final String TRANSFER = "transfer";
        public static final String PROCESS_BILLS = "process_bills";

        public static final String CREATE_EMPLOYEE = "create_employee";
        public static final String READ_EMPLOYEE = "read_employee";
        public static final String UPDATE_EMPLOYEE = "update_employee";
        public static final String DELETE_EMPLOYEE = "delete_employee";


        public static final String[] RIGHTS = new String[]{ADD_CLIENT,UPDATE_CLIENT,VIEW_CLIENT,CREATE_ACCOUNT,UPDATE_ACCOUNT,DELETE_ACCOUNT,VIEW_ACCOUNT,
        TRANSFER,PROCESS_BILLS,CREATE_EMPLOYEE,READ_EMPLOYEE,UPDATE_EMPLOYEE,DELETE_EMPLOYEE};
    }



    public static Map<String, List<String>> getRolesRights() {
        Map<String, List<String>> ROLES_RIGHTS = new HashMap<>();
        for (String role : ROLES) {
            ROLES_RIGHTS.put(role, new ArrayList<>());
        }
        ROLES_RIGHTS.get(ADMINISTRATOR).addAll(Arrays.asList(RIGHTS));

        ROLES_RIGHTS.get(EMPLOYEE).addAll(Arrays.asList(ADD_CLIENT,UPDATE_CLIENT,VIEW_CLIENT,CREATE_ACCOUNT,UPDATE_ACCOUNT,DELETE_ACCOUNT,VIEW_ACCOUNT,
                TRANSFER,PROCESS_BILLS));


        return ROLES_RIGHTS;
    }

}

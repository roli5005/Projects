package database;
import static database.Constants.Tables.*;

public class MySQLTableCreationFactory {
    public String getCreateSQLForTable(String table) {
        switch (table) {

            case USER:
                return "CREATE TABLE IF NOT EXISTS user (" +
                        "  id INT NOT NULL AUTO_INCREMENT," +
                        "  username VARCHAR(200) NOT NULL," +
                        "  password VARCHAR(64) NOT NULL," +
                        "  PRIMARY KEY (id)," +
                        "  UNIQUE INDEX id_UNIQUE (id ASC)," +
                        "  UNIQUE INDEX username_UNIQUE (username ASC));";

            case ROLE:
                return "  CREATE TABLE IF NOT EXISTS role (" +
                        "  id INT NOT NULL AUTO_INCREMENT," +
                        "  role VARCHAR(100) NOT NULL," +
                        "  PRIMARY KEY (id)," +
                        "  UNIQUE INDEX id_UNIQUE (id ASC)," +
                        "  UNIQUE INDEX role_UNIQUE (role ASC));";

            case RIGHT:
                return "  CREATE TABLE IF NOT EXISTS `right` (" +
                        "  `id` INT NOT NULL AUTO_INCREMENT," +
                        "  `right` VARCHAR(100) NOT NULL," +
                        "  PRIMARY KEY (`id`)," +
                        "  UNIQUE INDEX `id_UNIQUE` (`id` ASC)," +
                        "  UNIQUE INDEX `right_UNIQUE` (`right` ASC));";

            case ROLE_RIGHT:
                return "  CREATE TABLE IF NOT EXISTS role_right (" +
                        "  id INT NOT NULL AUTO_INCREMENT," +
                        "  role_id INT NOT NULL," +
                        "  right_id INT NOT NULL," +
                        "  PRIMARY KEY (id)," +
                        "  UNIQUE INDEX id_UNIQUE (id ASC)," +
                        "  INDEX role_id_idx (role_id ASC)," +
                        "  INDEX right_id_idx (right_id ASC)," +
                        "  CONSTRAINT role_id" +
                        "    FOREIGN KEY (role_id)" +
                        "    REFERENCES role (id)" +
                        "    ON DELETE CASCADE" +
                        "    ON UPDATE CASCADE," +
                        "  CONSTRAINT right_id" +
                        "    FOREIGN KEY (right_id)" +
                        "    REFERENCES `right` (id)" +
                        "    ON DELETE CASCADE" +
                        "    ON UPDATE CASCADE);";

            case USER_ROLE:
                return  "CREATE TABLE IF NOT EXISTS user_role (" +
                        "  id INT NOT NULL AUTO_INCREMENT," +
                        "  user_id INT NOT NULL," +
                        "  role_id INT NOT NULL," +
                        "  PRIMARY KEY (id)," +
                        "  UNIQUE INDEX id_UNIQUE (id ASC)," +
                        "  INDEX user_id_idx (user_id ASC)," +
                        "  INDEX role_id_idx (role_id ASC)," +
                        "  CONSTRAINT user_fkid" +
                        "    FOREIGN KEY (user_id)" +
                        "    REFERENCES user (id)" +
                        "    ON DELETE CASCADE" +
                        "    ON UPDATE CASCADE," +
                        "  CONSTRAINT role_fkid" +
                        "    FOREIGN KEY (role_id)" +
                        "    REFERENCES role (id)" +
                        "    ON DELETE CASCADE" +
                        "    ON UPDATE CASCADE);";
            case CLIENT:
                return  "   CREATE TABLE IF NOT EXISTS client (" +
                        "  `id` INT NOT NULL AUTO_INCREMENT," +
                        "  `name` VARCHAR(128) NOT NULL," +
                        "  `identity_card_number` VARCHAR(20) NOT NULL," +
                        "  `personal_nr_code` VARCHAR(45) NOT NULL," +
                        "  `address` VARCHAR(255) NOT NULL," +
                        "  `phone_number` VARCHAR(45) NOT NULL," +
                        "  PRIMARY KEY (`id`));";
            case CLIENT_ACCOUNT:
                return  "   CREATE TABLE IF NOT EXISTS client_account (" +
                        "  `id` INT NOT NULL AUTO_INCREMENT," +
                        "  `client_id` INT NOT NULL," +
                        "  `identification_number` VARCHAR(45) NOT NULL," +
                        "  `type` VARCHAR(10) NOT NULL," +
                        "  `balance` FLOAT NOT NULL," +
                        "  `date_of_creation` datetime DEFAULT NULL," +
                        "  PRIMARY KEY (`id`)," +
                        "  INDEX `id_idx` (`client_id` ASC) VISIBLE," +
                        "  CONSTRAINT `id`" +
                        "    FOREIGN KEY (`client_id`)" +
                        "    REFERENCES `client` (`id`)" +
                        "    ON DELETE CASCADE" +
                        "    ON UPDATE CASCADE);";
            case ACTIVITIES:
                return  "   CREATE TABLE IF NOT EXISTS activities (" +
                        "  `activity_id` INT NOT NULL AUTO_INCREMENT," +
                        "  `data` datetime DEFAULT NULL," +
                        "  `employee_id` INT NOT NULL," +
                        "  `action` VARCHAR(45) NOT NULL," +
                        "  `amount_of_money` FLOAT NOT NULL," +
                        "  `details` VARCHAR(512) NOT NULL," +
                        "  PRIMARY KEY (activity_id),;";

            default:
                return "";

        }
    }
}

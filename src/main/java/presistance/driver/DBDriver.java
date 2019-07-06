package presistance.driver;

import org.h2.tools.Server;

import java.sql.SQLException;

public class DBDriver {
    public static void main(String[] args) {
        try {
            Server.main("-ifNotExists");
            System.out.println("DB Launched");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

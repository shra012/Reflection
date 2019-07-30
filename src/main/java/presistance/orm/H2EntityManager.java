package presistance.orm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class H2EntityManager<T> extends ManagedEntityManager<T> {

    public H2EntityManager(Class<T> clss) {
        super(clss);
    }

    public Connection buildConnection() throws SQLException {
        //Connection connection =  DriverManager.getConnection("jdbc:h2:~/Programming/IDE/Intellij/Workspace/Reflection/src/main/resources/h2-db/db-files","sa","");
        Connection connection =  DriverManager.getConnection("jdbc:h2:~/git/Reflection/src/main/resources/h2-db/db-files","sa","");
        return connection;
    }
}

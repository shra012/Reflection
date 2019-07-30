package presistance.providers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import presistance.annotations.Provider;
import presistance.orm.ConnectionProvider;

public class H2ConnectionProvider implements ConnectionProvider {
	
	@Provider
	public Connection buildConnection() throws SQLException {
		//Connection connection =  DriverManager.getConnection("jdbc:h2:~/Programming/IDE/Intellij/Workspace/Reflection/src/main/resources/h2-db/db-files","sa","");
		Connection connection =  DriverManager.getConnection("jdbc:h2:~/git/Reflection/src/main/resources/h2-db/db-files","sa","");
		return connection;
	}
}

package presistance.orm;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionProvider {
	public Connection buildConnection() throws SQLException;
}

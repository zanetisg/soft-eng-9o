package sys.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import sys.log.Log;

public class JDBC {

	private Connection connection;

	public JDBC(String driverProtocol, String driverConnectionDetails, String jdbcClassName) {
		try {
			Class.forName(jdbcClassName);
			this.connection = DriverManager.getConnection("jdbc:" + driverProtocol + ":" + driverConnectionDetails);
			Log.msg(driverProtocol.toUpperCase() + ": connected to " + driverConnectionDetails);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	public Connection getConnection() {
		return this.connection;
	}
}

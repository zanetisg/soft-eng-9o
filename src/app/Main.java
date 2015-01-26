package app;

import sys.db.SQLite;
import app.contollers.UserContoller;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import sys.db.SQLBuilder;

/**
 * Application main class. Initiate application by calling user controller.
 *
 * @author thodoris
 */
public class Main {

	public static final String APP_NAME = "Employee Management System";
	public static final SQLite SQLITE_DRIVER = new SQLite("res/db/ems.db");

	public Main() {
		UserContoller userController = new UserContoller(SQLITE_DRIVER.getConnection());

		// Call user login
		userController.login();
	}

	private void createTables() {

		// Create tables
		try {
			SQLBuilder sql = new SQLBuilder();
			String query = sql.loadFromFile("res/sql/ems.sql").getQuery();
			Statement stmt = SQLITE_DRIVER.getConnection().createStatement();
			stmt.executeUpdate(query);
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new Main();
	}
}

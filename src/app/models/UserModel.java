package app.models;

import java.sql.Connection;

import sys.Model;

public class UserModel extends Model {

	public UserModel(Connection conn) {
		super(conn, "users", "id");
	}

	/**
	 * User login
	 *
	 * @param username
	 * @param password
	 * @return
	 */
	public boolean login(String username, String password) {
		String id = exists("`username` = '" + username + "' AND `password` = '" + password + "'");
		if (id != null) {
			loadData(id);
		}
		return !isEmpty();
	}

	/**
	 * Check if user is a foreman
	 *
	 * @return boolean
	 */
	public boolean isForeMan() {
		if (!isEmpty()) {
			return (getDataValue("is_fm").equals("1"));
		}
		return false;
	}

	/**
	 * Check if user is of human resource department
	 *
	 * @return
	 */
	public boolean isHR() {
		if (!isEmpty()) {
			return (getDataValue("is_hr").equals("1"));
		}
		return false;
	}

}

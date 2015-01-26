package app.models;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import sys.Model;
import sys.log.Log;

public class CommentModel extends Model {

	public CommentModel(Connection conn) {
		super(conn, "comments", "id");
	}

	/**
	 * Delete comments by employee id
	 *
	 * @param employeeId
	 */
	public void deleteByEmployeeId(String employeeId) {
		String sql = getSQLBuilder().delete(getTableName(), "`employee_id` = '" + employeeId + "'").getQuery();
		Log.msg("Execute: " + sql);
		try {
			Statement stmt = getConnection().createStatement();
			int effectedRows = stmt.executeUpdate(sql);
			Log.msg("Effected Rows: " + effectedRows);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Delete comments by user id
	 *
	 * @param userId
	 */
	public void deleteByUserId(String userId) {
		String sql = getSQLBuilder().delete(getTableName(), "`users_id` = '" + userId + "'").getQuery();
		Log.msg("Execute: " + sql);
		try {
			Statement stmt = getConnection().createStatement();
			int effectedRows = stmt.executeUpdate(sql);
			Log.msg("Effected Rows: " + effectedRows);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

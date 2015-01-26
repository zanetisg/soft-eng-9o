package app.models;

import java.sql.Connection;
import java.util.ArrayList;

import sys.Model;
import sys.log.Log;

public class EmployeeModel extends Model {

	public EmployeeModel(Connection conn) {
		super(conn, "employees", "id");
	}

	/**
	 * Employee search
	 *
	 * @param searchQuery
	 * @param colNames
	 * @return Rows of employees
	 */
	public ArrayList<Model> search(String searchQuery, String[] colNames) {
		StringBuilder where = new StringBuilder("`" + colNames[0] + "` like '%" + searchQuery + "%'");
		for (int i = 1; i < colNames.length; i++) {
			where.append(" OR `").append(colNames[i]).append("` like '%").append(searchQuery).append("%'");
		}
		Log.msg(where.toString());
		return getDataList(where.toString());
	}
}

package sys;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import sys.db.SQLBuilder;
import sys.log.Log;

public class Model {

	/**
	 * Database object for executing queries.
	 */
	private final Connection conn;

	/**
	 * SQLBuilder object for generating queries.
	 */
	private final SQLBuilder sql;

	/**
	 * Table name.
	 */
	private final String tblName;

	/**
	 * Primary key field name.
	 */
	private final String pkName;

	/**
	 * Data used in this model.
	 */
	private HashMap<String, String> data;

	/**
	 * Model constructor.
	 *
	 * @param conn
	 * @param tblName
	 * @param pkName
	 */
	public Model(Connection conn, String tblName, String pkName) {
		this.conn = conn;
		this.tblName = tblName;
		this.pkName = pkName;
		this.data = new HashMap<>();
		this.sql = new SQLBuilder();
	}

	/**
	 * Get Connection object.
	 *
	 * @return
	 */
	public Connection getConnection() {
		return conn;
	}

	/**
	 * Get SqlBuilder object.
	 *
	 * @return
	 */
	public SQLBuilder getSQLBuilder() {
		return sql;
	}

	/**
	 * Get table name
	 *
	 * @return
	 */
	public String getTableName() {
		return tblName;
	}

	/**
	 * Get primary key name
	 *
	 * @return
	 */
	public String getPrimaryKeyName() {
		return pkName;
	}

	/**
	 * If data is empty then model is empty. How smart am i? :D
	 *
	 * @return
	 */
	public boolean isEmpty() {
		return data.isEmpty();
	}

	/**
	 * Clear data.
	 */
	public void clearData() {
		data.clear();
	}

	/**
	 * Set model data.
	 *
	 * @param data
	 */
	public void setData(HashMap<String, String> data) {
		this.data = data;
	}

	/**
	 * Set model data value.
	 *
	 * @param key
	 * @param value
	 */
	public void setDataValue(String key, String value) {
		data.remove(key);
		data.put(key, value);
	}

	/**
	 * Get model data value.
	 *
	 * @param key
	 * @return String
	 */
	public String getDataValue(String key) {
		return data.get(key);
	}

	/**
	 * Get a vector of model
	 *
	 * @param where
	 * @return
	 */
	public ArrayList<Model> getDataList(String where) {
		ArrayList<Model> rows = new ArrayList<>();
		String query;
		if (where != null) {
			query = sql.select().from(tblName).where(where).getQuery();
		} else {
			query = sql.select().from(tblName).getQuery();
		}
		try (Statement stmt = this.conn.createStatement()) {
			if (stmt.execute(query)) {
				ResultSet rs = stmt.getResultSet();
				int colCount = rs.getMetaData().getColumnCount();
				int rowCount = 0;
				while (rs.next()) {
					HashMap<String, String> row = new HashMap<>();
					for (int i = 1; i <= colCount; i++) {
						row.put(rs.getMetaData().getColumnName(i), rs.getString(i));
					}
					Model model = new Model(conn, tblName, pkName);
					model.setData(row);
					rows.add(model);
					rowCount++;
				}
				Log.msg("Execute: " + query + ", Rows: " + rowCount);
			}
		} catch (SQLException e) {
			Log.msg(e.getMessage());
		}
		return rows;
	}

	/**
	 * Load model data by primary key value.
	 *
	 * @param pkValue
	 */
	public void loadData(String pkValue) {
		String query = this.sql.select().from(tblName).where("`" + pkName + "` = '" + pkValue + "'").getQuery();
		try (Statement stmt = this.conn.createStatement()) {
			if (stmt.execute(query)) {
				ResultSet rs = stmt.getResultSet();
				int colCount = rs.getMetaData().getColumnCount();
				for (int i = 1; i <= colCount; i++) {
					data.put(rs.getMetaData().getColumnName(i), rs.getString(i));
				}
				Log.msg("Execute: " + query + ", ColumnCount: " + colCount);
			}
			stmt.close();
		} catch (SQLException e) {
			Log.msg(e.getMessage());
		}
	}

	/**
	 * Check if a record already exists.
	 *
	 * @param where
	 * @return String primary key value.
	 */
	public String exists(String where) {
		String pkValue = null;
		String query = this.sql.select(pkName).from(tblName).where(where).getQuery();
		try (Statement stmt = this.conn.createStatement()) {
			if (stmt.execute(query)) {
				ResultSet rs = stmt.getResultSet();
				pkValue = rs.getString(1);
				Log.msg("Execute: " + query + ", Primary Key: " + pkName + " = " + pkValue);
			}
			stmt.close();
		} catch (SQLException e) {
			return pkValue;
		}
		return pkValue;
	}

	/**
	 * Save data. Insert if record already exists, update otherwise.
	 */
	public void save() {
		if (exists("`" + pkName + "` = '" + data.get(pkName) + "'") != null) {
			update();
		} else {
			insert();
		}
	}

	/**
	 * Delete record from table.
	 */
	public void delete() {
		if (!isEmpty()) {
			String query = this.sql.delete(tblName, "`" + pkName + "` = '" + data.get(pkName) + "'").getQuery();
			executeQuery(query);
		} else {
			Log.msg("Delete: No data.");
		}
	}

	/**
	 * Insert record to table.
	 */
	private void insert() {
		if (!isEmpty()) {
			String query = this.sql.insert(tblName, data).getQuery();
			Log.msg("Execute: " + query);
			executeQuery(query);
		} else {
			Log.msg("Insert: No data.");
		}
	}

	/**
	 * Update record in the table.
	 */
	private void update() {
		if (!isEmpty()) {
			String query = this.sql.update(tblName, "`" + pkName + "` = '" + data.get(pkName) + "'", data).getQuery();
			Log.msg("Execute: " + query);
			executeQuery(query);
		} else {
			Log.msg("Update: No data.");
		}
	}

	/**
	 * Execute update queries.
	 *
	 * @param query
	 */
	public void executeQuery(String query) {
		Log.msg("Execute: " + query);
		try (Statement stmt = this.conn.createStatement()) {
			int effectedRows = stmt.executeUpdate(query);
			Log.msg("Effected Rows: " + effectedRows);
			stmt.close();
		} catch (SQLException e) {
			Log.msg(e.getMessage());
		}
	}
}

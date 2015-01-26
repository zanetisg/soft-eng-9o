package sys.db;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class SQLBuilder {

	private StringBuilder query;

	public SQLBuilder select() {
		query = new StringBuilder("SELECT *");
		return this;
	}

	public SQLBuilder select(String fields) {
		query = new StringBuilder("SELECT ");
		query.append(fields);
		return this;
	}

	public SQLBuilder from(String tables) {
		query.append(" FROM ");
		query.append(tables);
		return this;
	}

	public SQLBuilder where(String where) {
		query.append(" WHERE ");
		query.append(where);
		return this;
	}

	public SQLBuilder insert(String table, HashMap<String, String> map) {
		query = new StringBuilder("INSERT INTO ");
		query.append(table);
		query.append(" (`");
		query.append(String.join("`, `", map.keySet()));
		query.append("`) VALUES ('");
		query.append(String.join("', '", map.values()));
		query.append("')");
		return this;
	}

	public SQLBuilder update(String table, String where, HashMap<String, String> map) {
		query = new StringBuilder("UPDATE ");
		query.append(table);
		query.append(" SET ");
		ArrayList<String> set = new ArrayList<>();
		for (String key : map.keySet()) {
			set.add("`" + key + "` = '" + map.get(key) + "'");
		}
		query.append(String.join(", ", set));
		query.append(" WHERE ");
		query.append(where);
		return this;
	}

	public SQLBuilder delete(String table, String where) {
		query = new StringBuilder("DELETE FROM ");
		query.append(table);
		query.append(" WHERE ");
		query.append(where);
		return this;
	}

	public SQLBuilder loadFromFile(String path) throws IOException {
		FileInputStream in = new FileInputStream(path);
		int chr;
		query = new StringBuilder();
		try {
			while ((chr = in.read()) != -1) {
				query.append((char) chr);
			}
		} finally {
			in.close();
		}
		return this;
	}

	public String getQuery() {
		return query.toString();
	}

	public String[] getQueries() {
		return this.getQuery().split(";");
	}
}

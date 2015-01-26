package sys.db;

public class SQLite extends JDBC {

	public SQLite(String filename) {
		super("sqlite", filename, "org.sqlite.JDBC");
	}
}

package wifiset;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DbTest {

	public static void main(String[] args) {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		String dbFileUrl="jdbc:sqlite:wifi.db";
		
		try {
			Class.forName("org.sqlite.JDBC");
			con=DriverManager.getConnection(dbFileUrl);
			System.out.println("SQLite DB Connected");
		}catch(Exception e) {
			System.out.println(e);
		}

	}

}

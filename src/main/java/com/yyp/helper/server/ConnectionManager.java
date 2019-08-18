package com.yyp.helper.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ConnectionManager {

	static Connection getNewConnection() throws Exception {
		String userName = "root";
		String password = "epic_yoga";
		String url = "jdbc:mysql://yogayourpractice.com/epic_yoga?user=root";
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		return DriverManager.getConnection(url, userName, password);
	}

	public static void releaseResources(ResultSet rs, Statement stmt,
			Connection conn) {
		if (rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (stmt != null) {
			try {
				stmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}

package com.nist.billingsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectivity {
	private static final String DATABASE_NAME="billingsystem";
	private static final String DRIVER_NAME="com.mysql.jdbc.Driver";
	private static final String URL="jdbc:mysql://localhost/";
	private static final String USERNAME="root";
	private static final String PASSWORD="";
	
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName(DRIVER_NAME);
		return DriverManager.getConnection(URL+DATABASE_NAME,USERNAME,PASSWORD);
	}
}

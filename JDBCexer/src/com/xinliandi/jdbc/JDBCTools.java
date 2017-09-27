package com.xinliandi.jdbc;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCTools {
	/**
	 * version1
	 * 
	 * @param sql
	 */
	// ִ��SQL����
	public static void update(String sql) {

		Connection connection = null;
		Statement statement = null;
		try {
			// ��ȡ���ݿ�����
			connection = getConnection();
			// ����Connection��createStatemnet������������ȡ
			statement = connection.createStatement();
			// 2������Statement�����executeUpdata��sql��ִ�������в���
			statement.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			releaseDB(null, statement, connection);

		}

	}

	// �ͷ����ݿ���Դ

	public static void releaseDB(ResultSet resultSet, Statement statement,
			Connection connection) {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (statement != null) {
			try {
				statement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (connection != null) {
			try {

				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// ��ȡ���ݿ�����
	public static Connection getConnection() throws Exception {
		Properties properties = new Properties();
		InputStream in = JDBCTools.class.getClassLoader().getResourceAsStream(
				"jdbc.properties");
		properties.load(in);
		String driverClass = properties.getProperty("driverClass");
		String jdbcUrl = properties.getProperty("jdbcUrl");
		String user = properties.getProperty("user");
		String password = properties.getProperty("password");
		// ��������
		Class.forName(driverClass);
		Connection connection = DriverManager.getConnection(jdbcUrl, user,
				password);
		return connection;
	}
}

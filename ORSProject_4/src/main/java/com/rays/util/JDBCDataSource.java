package com.rays.util;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * JDBCDataSource is a utility class that manages database connections using
 * C3P0 connection pooling.
 * <p>
 * This class follows the Singleton pattern and reads connection configuration
 * from the <code>system.properties</code> resource bundle.
 * </p>
 * 
 * @author Aastik Sahu
 */
public final class JDBCDataSource {

	/** Private constructor to prevent instantiation */
	private JDBCDataSource() {
	}

	/** Singleton instance */
	private static JDBCDataSource datasource = null;

	/** C3P0 pooled data source */
	private static ComboPooledDataSource cpds = null;

	/** Resource bundle for database configuration */
	private static ResourceBundle rb = ResourceBundle.getBundle("com.rays.bundle.system");

	/**
	 * Returns the singleton instance of {@code JDBCDataSource}.
	 *
	 * @return JDBCDataSource instance
	 */
	private static JDBCDataSource getInstance() {

		if (datasource == null) {

			datasource = new JDBCDataSource();
			datasource.cpds = new ComboPooledDataSource();
			

			try {
				datasource.cpds.setDriverClass(rb.getString("driver"));
				
				String jdbcUrl = System.getenv("DATABASE_URL");
				if (jdbcUrl == null) {
					jdbcUrl = rb.getString("url");
				}
				datasource.cpds.setJdbcUrl(rb.getString(jdbcUrl));
				
//				datasource.cpds.setJdbcUrl(rb.getString("url"));
				datasource.cpds.setUser(rb.getString("username"));
				datasource.cpds.setPassword(rb.getString("password"));
				datasource.cpds.setMinPoolSize(Integer.parseInt(rb.getString("minPoolSize")));
				datasource.cpds.setMaxPoolSize(Integer.parseInt(rb.getString("maxPoolSize")));
				datasource.cpds.setInitialPoolSize(Integer.parseInt(rb.getString("initialPoolSize")));
				datasource.cpds.setAcquireIncrement(Integer.parseInt(rb.getString("acquireIncrement")));
				datasource.cpds.setMaxIdleTime(Integer.parseInt(rb.getString("timeout")));
			} catch (PropertyVetoException e) {
				e.printStackTrace();
			}

		}
		return datasource;
	}

	/**
	 * Gets a connection from the C3P0 connection pool.
	 *
	 * @return database {@link Connection}
	 * @throws SQLException if a database access error occurs
	 */
	public static Connection getConnection() throws SQLException {
		return getInstance().cpds.getConnection();
	}

	/**
	 * Closes the provided connection safely.
	 *
	 * @param connection the {@link Connection} to close
	 */
	public static void closeConnection(Connection connection) {

		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * Rolls back the current transaction on the provided connection.
	 *
	 * @param connection the {@link Connection} to rollback
	 */
	public static void trnRollback(Connection connection) {

		if (connection != null) {
			try {
				connection.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}

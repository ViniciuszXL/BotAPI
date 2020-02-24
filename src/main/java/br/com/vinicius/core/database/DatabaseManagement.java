package br.com.vinicius.core.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.vinicius.core.Core;
import br.com.vinicius.core.util.Util;

public abstract class DatabaseManagement {

	protected final Core core;
	
	protected Connection connection;
	
	public DatabaseManagement(Core core) {
		this.core = core;
	}
	
	public abstract void startConnection();
	
	protected abstract boolean initializeConnection();
	
	public final boolean isConnected() throws SQLException {
		return connection != null && !connection.isClosed();
	}

	public final Connection getConnection() throws SQLException {
		if (connection == null || connection.isClosed())
			initializeConnection();

		return connection;
	}
	
	public final void executeUpdate(String update) {
		Thread thread = new Thread(new Runnable() {
			public void run() {
				PreparedStatement updateStatement = null;

				try {
					if (connection == null || connection.isClosed())
						initializeConnection();

					updateStatement = connection.prepareStatement(update);
					updateStatement.execute();
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					Util.closeStatement(updateStatement);
				}
			}
		});
		
		thread.start();
	}

	public final void executeUpdate(String update, Object... objects) {
		Thread thread = new Thread(new Runnable() {
			public void run() {
				int id = 1;
				PreparedStatement updateStatement = null;

				try {
					if (connection == null || connection.isClosed())
						initializeConnection();

					updateStatement = connection.prepareStatement(update);
					for (Object object : objects) {
						updateStatement.setObject(id, object);
						id += 1;
					}

					updateStatement.execute();
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					Util.closeStatement(updateStatement);
				}
			}
		});
		
		thread.start();
	}

	public final ResultSet executeQuery(String query) throws SQLException {
		if (connection == null || connection.isClosed())
			initializeConnection();

		PreparedStatement selectStatement = connection.prepareStatement(query);
		return selectStatement.executeQuery();
	}

	public final ResultSet executeQuery(String query, Object... objects) throws SQLException {
		if (connection == null || connection.isClosed())
			initializeConnection();

		int id = 1;
		PreparedStatement selectStatement = connection.prepareStatement(query);
		for (Object object : objects) {
			selectStatement.setObject(id, object);
			id += 1;
		}

		return selectStatement.executeQuery();
	}

	public final void closeConnection() {
		try {
			if (connection != null && !connection.isClosed())
				connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

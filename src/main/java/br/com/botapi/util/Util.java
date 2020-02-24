package br.com.botapi.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Util {

	public static void closeStatement(Statement statement) {
		if (statement == null)
			return;

		try {
			if (!statement.isClosed())
				statement.close();
		} catch (SQLException e) {
		}
	}

	public static void closeResultSet(ResultSet resultSet) {
		if (resultSet == null)
			return;

		try {
			if (!resultSet.isClosed())
				resultSet.close();
		} catch (SQLException e) {
		}
	}
	
}

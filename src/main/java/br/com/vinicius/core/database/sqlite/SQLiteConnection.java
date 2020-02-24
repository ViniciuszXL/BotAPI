package br.com.vinicius.core.database.sqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import br.com.vinicius.core.Core;
import br.com.vinicius.core.database.DatabaseManagement;
import br.com.vinicius.core.file.FileManager;
import br.com.vinicius.core.file.FileType;
import br.com.vinicius.core.formatter.FormattedLogger;

public class SQLiteConnection extends DatabaseManagement {

	protected final Core core;
	protected final FormattedLogger logger;

	protected Connection connection;
	protected String database = null;

	public SQLiteConnection(Core core) {
		super(core);
		
		this.core = core;
		this.logger = new FormattedLogger("SQLITE");
	}

	public final void startConnection() {
		FileManager file = new FileManager("config", FileType.TXT);

		try {
			if (file.hasValue("sqlite-database-enable")) {
				boolean value = file.getBooleanValue("sqlite-database-enable");
				if (!value) {
					logger.log("Modo de salvamento de dados por SQLite: Desabilitado.");
					return;
				}

				logger.log("Modo de salvamento de dados por SQLite: Habilitado.");
				logger.log("Buscando informações do nome da database...");
				boolean hasDatabaseName = file.hasValue("sqlite-database-name");
				if (!hasDatabaseName)
					logger.log("Configuração 'sqlite-database-name' não encontrada! Utilizando a padrão...");
				else
					logger.log("Configuração 'sqlite-database-name' encontrada! Buscando as informações...");

				this.database = !hasDatabaseName ? "database" : file.getStringValue("sqlite-database-name");
			} else {
				logger.error("Não existe a configuração 'sqlite-database-enable'. Conexão com o SQLite foi abortada.");
			}
		} finally {
			logger.log(!this.initializeConnection() ? "Não foi possível iniciar a conexão ao SQLite."
					: "Conexão com SQLite iniciada com sucesso!");
		}
	}

	protected final boolean initializeConnection() {
		try {
			if (this.connection != null && !this.connection.isClosed() || this.database == null)
				return false;

			Class.forName("org.sqlite.JDBC");
			this.connection = DriverManager.getConnection("jdbc:sqlite:" + this.database + ".db");
			return true;
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}
	}
	
}

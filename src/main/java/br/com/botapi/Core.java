package br.com.botapi;

import java.util.Scanner;

import br.com.botapi.database.sqlite.SQLiteConnection;
import br.com.botapi.formatter.FormattedLogger;

public class Core {

	protected final FormattedLogger logger;

	protected final SQLiteConnection sqliteConnection;

	protected CoreManagement coreManagement = null;

	public Core() {
		this.logger = new FormattedLogger("CORE");
		this.sqliteConnection = new SQLiteConnection(this);
	}

	public final FormattedLogger getLogger() {
		return logger;
	}

	public final SQLiteConnection getSQLiteConnection() {
		return sqliteConnection;
	}

	public final void setCoreManagement(CoreManagement coreManagement) {
		this.coreManagement = coreManagement;
	}

	public final CoreManagement getCoreManagement() {
		return coreManagement;
	}

	public final void initializeCore() {
		if (this.coreManagement == null) {
			logger.error("Erro na inicialização do programa! O 'coreManagement' não está devidamente setado!");
			return;
		}

		this.sqliteConnection.startConnection();
	}

	private final void a(String[] args) {
		//core.getCoreManagement().onEnable();
		Scanner scanner = null;
		try {
			scanner = new Scanner(System.in);

			String command = scanner.nextLine();
			if (command.equalsIgnoreCase("sair")) {
				//core.getLogger().log("Sistema desativado com sucesso.");
				System.exit(1);
				return;
			}

			//core.getLogger().error("Comando não encontrado. Único comando disponível é 'sair'.");
		} finally {
			if (scanner != null)
				scanner.close();
		}
	}
}

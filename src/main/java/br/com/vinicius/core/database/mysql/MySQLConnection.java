package br.com.vinicius.core.database.mysql;

import br.com.vinicius.core.Core;
import br.com.vinicius.core.database.DatabaseManagement;

public class MySQLConnection extends DatabaseManagement {
	
	protected final Core core;

	public MySQLConnection(Core core) {
		super(core);
		
		this.core = core;
	}

	@Override
	public void startConnection() {
		
	}

	@Override
	protected boolean initializeConnection() {
		return false;
	}

}

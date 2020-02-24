package br.com.botapi.database.mysql;

import br.com.botapi.Core;
import br.com.botapi.database.DatabaseManagement;

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

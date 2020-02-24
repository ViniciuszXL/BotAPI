package br.com.vinicius.core.api.telegram;

public abstract class TelegramConnectionCommand {
	
	public abstract String getName();
	
	public abstract void runCommand(TelegramModule telegramModule);
	
	public final boolean isSimilar(String name) {
		return this.getName().equalsIgnoreCase(name) ? true : false;
	}
}

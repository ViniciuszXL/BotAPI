package br.com.botapi.api.telegram.modules;

public class TelegramChatMembersModule {

	private final int id;
	
	private boolean isBot = false;
	
	private String first_name;
	private String username;
	
	public TelegramChatMembersModule(int id) {
		this.id = id;
	}
	
	public final int getId() {
		return id;
	}
	
	public final String getFirstName() {
		return first_name;
	}
	
	public final void setFirstName(String first_name) {
		this.first_name = first_name;
	}
	
	public final String getUsername() {
		return username;
	}
	
	public final void setUsername(String username) {
		this.username = username;
	}
	
	public final boolean isBot() {
		return isBot;
	}
	
	public final void setBot(boolean isBot) {
		this.isBot = isBot;
	}
}

package br.com.vinicius.core.api.telegram.modules;

public class TelegramFromModule {

	private final int id;
	
	private boolean isBot;
	
	private String first_name;
	private String last_name;
	private String username;
	private String language_code;
	
	public TelegramFromModule(int id) {
		this.id = id;
	}
	
	public final int getId() {
		return id;
	}
	
	public final boolean isBot() {
		return isBot;
	}
	
	public final void setBot(boolean isBot) {
		this.isBot = isBot;
	}
	
	public final String getFirstName() {
		return first_name;
	}
	
	public final void setFirstName(String first_name) {
		this.first_name = first_name;
	}
	
	public final String getLastName() {
		return last_name;
	}
	
	public final void setLastName(String last_name) {
		this.last_name = last_name;
	}
	
	public final String getUsername() {
		return username;
	}
	
	public final void setUsername(String username) {
		this.username = username;
	}
	
	public final String getLanguageCode() {
		return language_code;
	}
	
	public final void setLanguageCode(String language_code) {
		this.language_code = language_code;
	}
}

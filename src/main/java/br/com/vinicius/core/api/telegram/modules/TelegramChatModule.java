package br.com.vinicius.core.api.telegram.modules;

public class TelegramChatModule {

	private final int id;
	
	private String title;
	private boolean all_members_are_administrators = false;
	
	private String first_name;
	private String last_name;
	private String username;
	
	private String type;
	
	public TelegramChatModule(int id) {
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
	
	public final String getType() {
		return type;
	}
	
	public final void setType(String type) {
		this.type = type;
	}
	
	public final String getTitle() {
		return title;
	}
	
	public final void setTitle(String title) {
		this.title = title;
	}
	
	public final boolean allMembersAreAdministrators() {
		return all_members_are_administrators;
	}
	
	public final void setAllMembersAreAdministrators(boolean all_members_are_administrators) {
		this.all_members_are_administrators = all_members_are_administrators;
	}
}

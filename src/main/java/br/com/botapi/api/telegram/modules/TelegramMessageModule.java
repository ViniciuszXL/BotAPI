package br.com.botapi.api.telegram.modules;

public class TelegramMessageModule {

	private final int message_id;
	
	private int date;
	private String text;
	
	public TelegramMessageModule(int message_id) {
		this.message_id = message_id;
	}
	
	public final int getMessageId() {
		return message_id;
	}
	
	public final int getDate() {
		return date;
	}
	
	public final void setDate(int date) {
		this.date = date;
	}
	
	public final String getText() {
		return text;
	}
	
	public final void setText(String text) {
		this.text = text;
	}
}

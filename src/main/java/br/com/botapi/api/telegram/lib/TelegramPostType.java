package br.com.botapi.api.telegram.lib;

public enum TelegramPostType {

	CHAT_ID("chat_id;%d"),
	CHAT_ID_STRING("chat_id;%s"),
	TEXT("text;%s"),
	PHOTO("photo;%s"),
	DOCUMENT("document;%s"),
	AUDIO("audio;%s"),
	VIDEO("video;%s"),
	VOICE("voice;%s");
	
	private final String type;
	
	private TelegramPostType(String type) {
		this.type = type;
	}
	
	public final String getType() {
		return type;
	}
	
	public final String format(Object...objects) {
		return String.format(type, objects);
	}
}

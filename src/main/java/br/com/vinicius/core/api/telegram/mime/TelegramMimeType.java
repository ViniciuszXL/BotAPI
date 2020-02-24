package br.com.vinicius.core.api.telegram.mime;

import java.util.Arrays;

public enum TelegramMimeType {

	IMAGE("image/jpeg"),
	AUDIO("audio/mpeg"),
	VIDEO("video/x-matroska");
	
	private final String type;
	
	private TelegramMimeType(String type) {
		this.type = type;
	}
	
	public final String getType() {
		return type;
	}
	
	public final boolean isSimilar(String type) {
		return this.type.equalsIgnoreCase(type);
	}

	public static final TelegramMimeType getType(String mime_type) {
		try {
			return Arrays.asList(values()).stream().filter(s -> s.isSimilar(mime_type)).findFirst().orElse(null);
		} catch (Exception e) {
			return null;
		}
	}
}

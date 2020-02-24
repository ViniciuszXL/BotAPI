package br.com.botapi.api.telegram.modules;

import br.com.botapi.api.telegram.mime.TelegramMimeType;

public class TelegramAudioModule {

	private final String file_id;
	
	private String file_unique_id;
	
	private int duration;
	private int file_size;
	
	private TelegramMimeType mime_type;
	
	public TelegramAudioModule(String file_id) {
		this.file_id = file_id;
	}
	
	public final String getFileId() {
		return file_id;
	}
	
	public final TelegramMimeType getMimeType() {
		return mime_type;
	}
	
	public final void setMimeType(String mime_type) {
		this.mime_type = TelegramMimeType.getType(mime_type);
	}
	
	public final String getFileUniqueId() {
		return file_unique_id;
	}
	
	public final void setFileUniqueId(String file_unique_id) {
		this.file_unique_id = file_unique_id;
	}
	
	public final int getFileSize() {
		return file_size;
	}
	
	public final void setFileSize(int file_size) {
		this.file_size = file_size;
	}
	
	public final int getDuration() {
		return duration;
	}
	
	public final void setDuration(int duration) {
		this.duration = duration;
	}
}

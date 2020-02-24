package br.com.vinicius.core.api.telegram.modules;

import br.com.vinicius.core.api.telegram.mime.TelegramMimeType;
import br.com.vinicius.core.api.telegram.modules.extend.TelegramThumbModule;

public class TelegramDocumentModule {

	private final String file_id;

	private TelegramThumbModule thumbModule;

	private String file_name;
	private String file_unique_id;

	private int file_size;

	private TelegramMimeType mime_type;

	public TelegramDocumentModule(String file_id) {
		this.file_id = file_id;
	}

	public final String getFileId() {
		return file_id;
	}

	public final TelegramThumbModule getThumbModule() {
		return thumbModule;
	}

	public final void setThumbModule(TelegramThumbModule thumbModule) {
		this.thumbModule = thumbModule;
	}

	public final String getFileName() {
		return file_name;
	}

	public final void setFileName(String file_name) {
		this.file_name = file_name;
	}

	public final TelegramMimeType getMimeType() {
		return mime_type;
	}

	public final boolean isMimeType(TelegramMimeType mime_type) {
		return this.mime_type.getType().equalsIgnoreCase(mime_type.getType()) ? true : false;
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
}

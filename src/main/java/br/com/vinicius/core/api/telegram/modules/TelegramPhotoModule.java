package br.com.vinicius.core.api.telegram.modules;

public class TelegramPhotoModule {

	private final String file_id;
	
	private String file_unique_id;
	
	private int file_size;
	private int width;
	private int height;
	
	public TelegramPhotoModule(String file_id) {
		this.file_id = file_id;
	}
	
	public final String getFileId() {
		return file_id;
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
	
	public final int getWidth() {
		return width;
	}
	
	public final void setWidth(int width) {
		this.width = width;
	}
	
	public final int getHeight() {
		return height;
	}
	
	public final void setHeight(int height) {
		this.height = height;
	}
}

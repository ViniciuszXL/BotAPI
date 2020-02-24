package br.com.botapi.file;

public enum FileType {

	TXT(".txt");
	
	private final String end;
	
	private FileType(String end) {
		this.end = end;
	}
	
	public final String getEnd() {
		return end;
	}
	
	public final String convert(String name) {
		if (name.endsWith(this.end))
			return name;
		return name + this.end;
	}
}

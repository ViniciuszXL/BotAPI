package br.com.vinicius.core.formatter;

public class FormattedLogger {

	private final String prefix;

	public FormattedLogger(String prefix) {
		this.prefix = "[" + prefix + "] ";
	}

	public final void log(String message) {
		System.out.println(prefix + message);
	}

	public final void logWithoutPrefix(String message) {
		System.out.println(message);
	}
	
	public final void error(String... message) {
		for (String s : message)
			error(s);
	}
	
	public final void error(String message) {
		System.out.println(prefix + "[ERROR] " + message);
	}

	public final void errorWithoutPrefix(String message) {
		System.out.println("[ERROR] " + message);
	}
}

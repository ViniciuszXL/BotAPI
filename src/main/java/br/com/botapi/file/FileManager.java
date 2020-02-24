package br.com.botapi.file;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileManager {

	private final File file;
	
	private List<String> lines;
	
	public FileManager(String fileName, FileType type) {
		try {
			String directory = new File(".").getAbsolutePath();
			directory = directory.substring(0, directory.length() - 1);
			
			this.file = new File(directory, type.convert(fileName));
			FileUtil.createFile(file);
		} finally {
			this.scannerFile();
		}
	}
	
	private final void scannerFile() {
		Scanner scanner = null;
		
		try {
			this.lines = new ArrayList<String>();
			scanner = new Scanner(file);
			
			while (scanner.hasNextLine())
				this.lines.add(scanner.nextLine());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (scanner != null)
				scanner.close();
		}
	}
	
	public final boolean hasValue(String path) {
		return this.lines.stream().filter(s -> s.startsWith(path)).findFirst().orElse(null) == null ? false : true;
	}
	
	public final boolean getBooleanValue(String path) {
		try {
			String string = this.lines.stream().filter(s -> s.startsWith(path)).findFirst().orElse(null);
			if (string == null)
				return false;
			String[] split = string.split("=");
			return Boolean.valueOf(split[1]);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public final String getStringValue(String path) {
		String string = this.lines.stream().filter(s -> s.startsWith(path)).findFirst().orElse(null);
		if (string == null)
			return null;
		String[] split = string.split("=");
		return split[1];
	}
	
	public final void setValue(String path, Object value) {
		String string = this.lines.stream().filter(s -> s.startsWith(path)).findFirst().orElse(null);
		if (string != null) {
			try {
				this.lines.remove(string);
			} finally {
				String[] split = string.split("=");
				this.lines.add(split[0] + "=" + value);	
			}
			return;
		}
		
		this.lines.add(path + "=" + value);
	}
	
	public final void saveConfig() {
		Thread thread = new Thread(new Runnable() {
			public void run() {
				PrintWriter writer = null;
				
				try {
					writer = new PrintWriter(file);
					for (String s : lines)
						writer.println(s);
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (writer != null)
						writer.close();
				}
			}
		});
		
		thread.start();
	}
}

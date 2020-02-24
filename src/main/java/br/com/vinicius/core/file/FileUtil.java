package br.com.vinicius.core.file;

import java.io.File;

public class FileUtil {
	
	public static final boolean existsFile(File parent, String target) {
		if (!existsParent(parent))
			return false;
		
		target = target.endsWith(".yml") ? target : target + ".yml";
		File file = new File(parent, target);
		return file.exists() ? true : false;
	}
	
	public static final boolean existsParent(File parent) {
		return parent.exists() ? true : false;
	}

	public static final void createFile(File file) {
		try {
			if (file.exists())
				return;
			file.createNewFile();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static final void createParent(File parent) {
		try {
			if (!parent.exists())
				parent.mkdir();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static final void createPath(File parent, String path) {
		try {
			File file = new File(parent, path);
			if (!file.exists())
				file.mkdir();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

package com.stealthyone.bukkit.simplepromoter.utils;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.bukkit.plugin.java.JavaPlugin;

import com.stealthyone.bukkit.simplepromoter.storage.CustomFileManager;

public final class FileUtils {

	public final static void copyFileFromJar(JavaPlugin plugin, String fileName) {
		CustomFileManager file = new CustomFileManager(plugin, fileName.replace(".yml", ""));
		InputStream in = plugin.getResource(fileName);
		
		try {
			OutputStream out = new FileOutputStream(file.getFile());
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			out.close();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
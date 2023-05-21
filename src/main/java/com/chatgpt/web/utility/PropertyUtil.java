package com.chatgpt.web.utility;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * プロパティファイルからキーに紐づく値を取得するクラス
 * 
 * @author user
 *
 */
public class PropertyUtil {
	private static final String INIT_FILE_PATH = "/ChatGPTSample/src/main/resources/config.properties";
	private static final Properties properties;

	/**
	 * インスタンス化防止
	 */
	private PropertyUtil() {}

	static {
		properties = new Properties();
		try {
			properties.load(Files.newBufferedReader(Paths.get(INIT_FILE_PATH),StandardCharsets.UTF_8));
		} catch (IOException e) {
            // ファイル読み込みに失敗
			for(StackTraceElement element : e.getStackTrace()) {
				System.out.println(element);
			}
			System.out.println(e.getLocalizedMessage());
            System.out.println(String.format("ファイルの読み込みに失敗しました。ファイル名:%s", INIT_FILE_PATH));
		}
	}

	/**
	 * プロパティ値を取得する
	 * 
	 * @param key
	 * @return 値
	 */
	public static String getProperty(final String key) {
		return properties.getProperty(key);
	}
}

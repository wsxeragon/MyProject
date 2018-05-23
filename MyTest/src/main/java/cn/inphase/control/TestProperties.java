package cn.inphase.control;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class TestProperties {

	public static void main(String[] args) throws IOException {
		testStore();
	}

	public static void testStore() throws IOException {
		Properties properties = new Properties();
		String path = TestProperties.class.getResource("/").getPath();
		path += "my.properties";
		File file = new File(path);

		if (!file.exists()) {
			file.createNewFile();
		}

		FileReader reader = new FileReader(file);

		properties.load(reader);

		System.out.println(properties);

		properties.setProperty("1", "a");
		properties.setProperty("2", "b");
		properties.setProperty("3", "c");

		FileWriter writer = new FileWriter(file);

		properties.store(writer, "test");

		reader.close();
		writer.close();
	}
}

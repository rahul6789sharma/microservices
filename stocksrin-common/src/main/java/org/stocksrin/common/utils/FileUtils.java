package org.stocksrin.common.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class FileUtils {

	public static void main(String[] args) {
		System.out.println(yesterday());
		System.out.println(today());
		System.out.println(new Date());
		long diff = yesterday().getTime() - today().getTime();
		System.out.println("Days: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
	}

	public static String lasFilePath(String dir) {
		List<String> lst = FileUtils.listFilesForFolder(new File(dir));
		String path = dir + lst.get(lst.size() - 1);
		return path;
	}

	public static Object fromJson(String file) throws Exception {
		ObjectMapper mapper = new ObjectMapper();

		try {
			Object data = mapper.readValue(new File(file), Object.class);
			return data;

		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}


	public static void writeDataAsJson(Object data, String fileName) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			File file = new File(fileName);
			mapper.writerWithDefaultPrettyPrinter().writeValue(file, data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void bachup(String sourceFile, String targetFile) {
		Path source = Paths.get(sourceFile);
		Path target = Paths.get(targetFile);
		try {
			if (source.toFile().exists()) {
				System.out.println("taking backup");
				Files.copy(source, target);
			}

		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public static void appendData(String data, String fileName) {

		File file = new File(fileName);
		// if file doesnt exists, then create it
		if (!file.exists()) {
			throw new RuntimeException(fileName + " File not exist");
		}

		try (FileWriter fw = new FileWriter(file.getAbsoluteFile(), true); BufferedWriter bw = new BufferedWriter(fw);) {

			bw.write(data + "\n");

		} catch (IOException e) {
			e.printStackTrace();

		}
	}

	private static Date yesterday() {
		final Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("IST"));
		cal.add(Calendar.DATE, -2);
		return cal.getTime();
	}

	private static Date today() {
		final Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("IST"));
		return cal.getTime();
	}

	public static boolean isYestardaysFileExist(String filePath) throws Exception {
		String fileModifedDate = modifiedDate(filePath);
		String today = DateUtils.dateToString(new Date(), "MM/dd/yyyy");
		if (fileModifedDate.equals(today)) {
			return true;
		}
		return false;
	}

	public static boolean isTodayFileExist(String filePath) throws Exception {
		String fileModifedDate = modifiedDate(filePath);
		String today = DateUtils.dateToString(new Date(), "MM/dd/yyyy");
		if (fileModifedDate.equals(today)) {
			return true;
		}
		return false;
	}

	private static String modifiedDate(String filePath) throws Exception {
		File file = new File(filePath);
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		return sdf.format(Long.valueOf(file.lastModified()));
	}

	public static boolean downloadFile(String url, String toFile) throws Exception {

		boolean status = false;
		ReadableByteChannel rbc = null;

		try (FileOutputStream fos = new FileOutputStream(toFile);) {

			URL website = new URL(url);
			rbc = Channels.newChannel(website.openStream());

			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			fos.close();
			rbc.close();
			status = true;

		} catch (IOException e) {
			throw new Exception("ERROR ! downloadBhavCopy, url " + url + " , Error Msg: " + e.getMessage());
		} finally {
			try {
				if (rbc != null) {
					rbc.close();
				}

			} catch (IOException e) {
				throw new Exception("ERROR ! downloadBhavCopy " + e.getMessage());
			}
		}
		return status;
	}

	public static boolean makeFile(String filepath) {
		try {
			File file = new File(filepath);
			if (file.createNewFile()) {
				return true;
			}
			return false;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static List<String> listFilesForFolder(File folder) {
		List<String> names = new ArrayList<>();
		for (File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				listFilesForFolder(fileEntry);
			} else {
				names.add(fileEntry.getName());
			}
		}
		return names;
	}

	public static boolean makeDir(String dirPath) {
		File file = new File(dirPath);
		if (!file.exists()) {
			if (file.mkdir()) {
				return true;
			}
			return false;
		}
		return false;
	}

	public static void delete(String filePath) {
		try {
			File file = new File(filePath);
			if (file.delete()) {
				System.out.println(file.getName() + " is deleted!");
			} else {
				System.out.println("Delete operation is failed.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean isFileExits(String filepath) {
		File file = new File(filepath);
		return file.exists();
	}
}

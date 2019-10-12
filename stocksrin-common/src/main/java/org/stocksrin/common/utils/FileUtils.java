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
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

import org.stocksrin.common.model.future.Future;
import org.stocksrin.common.model.option.OptionModles;
import org.stocksrin.common.model.trade.Strategy2;
import org.stocksrin.strategy.db.model.StrategyEntity;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class FileUtils {

	public static void main(String[] args) {
		deletedContaningfiles(AppConstant.STOCKSRIN_STRATEGY_DIR_IntraDay_NIFTY, "Expiry");

	}

	public static void deletedContaningfiles(String path, String keyworld) {
		List<String> files = listFilesForFolder(path);
		for (String string : files) {
			if (string.contains(keyworld))

				delete(AppConstant.STOCKSRIN_STRATEGY_DIR_IntraDay_NIFTY + string);

		}

	}

	private static String getMonthly(List<String> expires) throws Exception {
		// String todayDate = DateUtils.dateToString(new Date(), "ddMMMyyyy");
		String todayDate = "30May2019";
		String currentExpiry = expires.get(0);
		String currentExpiryMonth = null;

		if (todayDate.equalsIgnoreCase(currentExpiry)) {
			// System.out.println("today is Expiry");
			currentExpiryMonth = currentExpiry.replaceAll("[^A-Za-z]", "");
			System.out.println(currentExpiryMonth);
		}

		int index = 0;
		for (String string : expires) {
			if (string.toUpperCase().contains(currentExpiryMonth)) {
				index++;
			}
		}
		System.out.println(expires.get(index - 1));
		System.out.println(index);
		return expires.get(index - 1);

	}

	public static String lasFilePath(String dir) {
		List<String> lst = FileUtils.listFilesForFolder(new File(dir));
		String path = dir + lst.get(lst.size() - 1);
		return path;
	}

	public static OptionModles fromJson2(String file) throws Exception {
		ObjectMapper mapper = new ObjectMapper();

		try {
			OptionModles data = mapper.readValue(new File(file), OptionModles.class);
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

	public static Future from(String dataa, Future future) throws Exception {
		ObjectMapper mapper = new ObjectMapper();

		try {
			Future data = mapper.readValue(dataa, Future.class);
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

	public static StrategyEntity fromJsonStrategy2(String file) throws Exception {
		ObjectMapper mapper = new ObjectMapper();

		try {
			StrategyEntity data = mapper.readValue(new File(file), StrategyEntity.class);
			return data;

		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("returning null");
		return null;
	}

	public static Strategy2 fromJsonStrategy(String file) throws Exception {
		ObjectMapper mapper = new ObjectMapper();

		try {
			Strategy2 data = mapper.readValue(new File(file), Strategy2.class);
			return data;

		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("returning null");
		return null;
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
		System.out.println("returning null");
		return null;
	}

	public static void writeDataAsJson(Object data, String fileName) {
		try {
			ObjectMapper mapper = new ObjectMapper();

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

	public static String nextMonth() {
		final Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("IST"));
		cal.add(Calendar.MONTH, 1);
		return months[cal.getTime().getMonth()];
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

	public static List<String> listdir(String path) {
		File dir = new File(path);
		File[] c = dir.listFiles();
		List<File> subDirList = Arrays.asList(c);
		List<String> subDirName = subDirList.stream().map(i -> i.getName()).collect(Collectors.toList());
		return subDirName;
	}

	public static List<String> listFilesForFolder(String folder) {
		return listFilesForFolder(new File(folder));
	}

	public static List<String> listFilesForFolder(File folder) {
		List<String> names = new ArrayList<>();
		if (!folder.exists()) {
			return null;
		}
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

	private static String[] months = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
}

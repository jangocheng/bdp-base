package com.platform.spring.util.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileUtil {
	/**
	 * 以行为单位读取文件，常用于读面向行的格式化文件
	 */
	public static List<String> readFileByLines(String fileName) {
		File file = new File(fileName);
		BufferedReader reader = null;
		List<String> listResult = new ArrayList<String>();
		try {
			reader = new BufferedReader(new FileReader(file));
			String line = null;
			// 一次读入一行，直到读入null为文件结束
			while ((line = reader.readLine()) != null) {
				listResult.add(line);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return listResult;
	}

	/**
	 * 清除数据重新写入数据
	 */
	public static void writeString(String fileName, String content) {
		File file = new File(fileName);
		FileOutputStream fop = null;
		try {
			fop = new FileOutputStream(file);
			// if file doesn't exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			// get the content in bytes
			byte[] contentInBytes = content.getBytes();

			fop.write(contentInBytes);
			fop.flush();
			fop.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fop != null) {
				try {
					fop.close();
				} catch (IOException e1) {
				}
			}
		}
	}

	// 追加写入
	public static void append(String fileName, String content) {
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName, true)));
			out.write(content);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// 追加写入行，每一条数据一行
	public static void appendLine(String fileName, String content) {
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName, true)));
			out.write(content);
			out.newLine();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// 一次性读取文件的全部内容
	public static String readToString(String fileName) throws IOException {
		FileInputStream inputStream = null;
		StringBuffer result = new StringBuffer();
		Scanner sc = null;
		try {
			inputStream = new FileInputStream(fileName);
			sc = new Scanner(inputStream, "UTF-8");
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				result.append(line);
			}
			if (sc.ioException() != null) {
				throw sc.ioException();
			}
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
			if (sc != null) {
				sc.close();
			}
		}
		return result.toString();
	}

	// 一次性读取文件的全部内容
	public static String readByNIO(String file) throws IOException{
		String result = null;
		// 第一步 获取通道
		FileInputStream fis = null;
		FileChannel channel = null;
		try {
			fis = new FileInputStream(file);
			channel = fis.getChannel();
			// 文件内容的大小
			int size = (int) channel.size();
			// 第二步 指定缓冲区
			ByteBuffer buffer = ByteBuffer.allocate(1024 * 1000 * 1000);
			// 第三步 将通道中的数据读取到缓冲区中
			channel.read(buffer);

			Buffer bf = buffer.flip();
			byte[] bt = buffer.array();
			result = new String(bt, 0, size);
			buffer.clear();
			buffer = null;
		} catch (FileNotFoundException e) {
			throw new IOException();
		} catch (IOException e) {
			throw new IOException();
		} finally {
			try {
				channel.close();
				fis.close();
			} catch (IOException e) {
				throw new IOException();
			}

		}
		return result;
	}

}

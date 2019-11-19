package com.chmei.nzbcommon.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @FileName：FileUtils.java
 * @Description：文件操作的工具类
 */

public class FileUtils {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(FileUtils.class);

	private FileUtils() {
	}

	/**
	 * 读取文本文件内容，以行的形式读取
	 *
	 * @param filePathAndName
	 *            带有完整绝对路径的文件名
	 * @return String 返回文本文件的内容
	 */
	public static String readFileContent(String filePathAndName)
			throws IOException {
		return readFileContent(filePathAndName, null, null, 1024);
	}

	/**
	 * 读取文本文件内容，以行的形式读取
	 *
	 * @param filePathAndName
	 *            带有完整绝对路径的文件名
	 * @param encoding
	 *            文本文件打开的编码方式 例如 GBK,UTF-8
	 * @return String 返回文本文件的内容
	 */
	public static String readFileContent(String filePathAndName, String encoding)
			throws IOException {
		return readFileContent(filePathAndName, encoding, null, 1024);
	}

	/**
	 * 读取文本文件内容，以行的形式读取
	 *
	 * @param filePathAndName
	 *            带有完整绝对路径的文件名
	 * @param bufLen
	 *            设置缓冲区大小
	 * @return String 返回文本文件的内容
	 */
	public static String readFileContent(String filePathAndName, int bufLen)
			throws IOException {
		return readFileContent(filePathAndName, null, null, bufLen);
	}

	/**
	 * 读取文本文件内容，以行的形式读取
	 *
	 * @param filePathAndName
	 *            带有完整绝对路径的文件名
	 * @param encoding
	 *            文本文件打开的编码方式 例如 GBK,UTF-8
	 * @param sep
	 *            分隔符 例如：#，默认为空格
	 * @return String 返回文本文件的内容
	 */
	public static String readFileContent(String filePathAndName,
			String encoding, String sep) throws IOException {
		return readFileContent(filePathAndName, encoding, sep, 1024);
	}

	/**
	 * 读取文本文件内容，以行的形式读取
	 *
	 * @param filePathAndName
	 *            带有完整绝对路径的文件名
	 * @param encoding
	 *            文本文件打开的编码方式 例如 GBK,UTF-8
	 * @param sep
	 *            分隔符 例如：#，默认为\n;
	 * @param bufLen
	 *            设置缓冲区大小
	 * @return String 返回文本文件的内容
	 */
	public static String readFileContent(String filePathAndName,
			String encoding, String sep, int bufLen) throws IOException {
		if (filePathAndName == null || "".equals(filePathAndName)) {
			return "";
		}
		String seperator = sep;
		if (sep == null || "".equals(sep)) {
			seperator = "\n";
		}
		if (!new File(filePathAndName).exists()) {
			return "";
		}
		StringBuilder str = new StringBuilder("");

		if (encoding == null || "".equals(encoding.trim())) {
			try (FileInputStream fs = new FileInputStream(filePathAndName);
					InputStreamReader isr = new InputStreamReader(fs);
					BufferedReader br = new BufferedReader(isr, bufLen);) {

				String data;
				while ((data = br.readLine()) != null) {
					str.append(data).append(seperator);
				}
			} catch (IOException e) {
				throw e;

			}

		} else {
			try (FileInputStream fs = new FileInputStream(filePathAndName);
					InputStreamReader isr = new InputStreamReader(fs,
							encoding.trim());
					BufferedReader br = new BufferedReader(isr, bufLen);) {

				String data;
				while ((data = br.readLine()) != null) {
					str.append(data).append(seperator);
				}
			} catch (IOException e) {
				throw e;

			}
		}

		return str.toString();
	}

	/**
	 * 新建一个文件并写入内容
	 *
	 * @param path
	 *            文件全路径
	 * @param fileName
	 *            文件名
	 * @param content
	 *            内容
	 * @return boolean
	 * @throws IOException
	 */
	public static boolean newFile(String path, String fileName,
			StringBuilder content) throws IOException {

		return newFile(path, fileName, content, 1024, false);
	}

	/**
	 * 新建一个文件并写入内容
	 *
	 * @param path
	 *            文件全路径
	 * @param fileName
	 *            文件名
	 * @param content
	 *            内容
	 * @param isWrite
	 * @return boolean
	 * @throws IOException
	 */
	public static boolean newFile(String path, String fileName,
			StringBuilder content, boolean isWrite) throws IOException {

		return newFile(path, fileName, content, 1024, isWrite);
	}

	/**
	 * 新建一个文件并写入内容
	 *
	 * @param path
	 *            文件全路径
	 * @param fileName
	 *            文件名
	 * @param content
	 *            内容
	 * @param bufLen
	 *            设置缓冲区大小
	 * @param isWrite
	 *            是否追加写入文件
	 * @return boolean
	 * @throws IOException
	 */
	public static boolean newFile(String path, String fileName,
			StringBuilder content, int bufLen, boolean isWrite)
			throws IOException {
		if (path == null || "".equals(path) || content == null)
			return false;
		boolean flag;

		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		try (FileWriter fw = new FileWriter(path + File.separator + fileName,
				isWrite); BufferedWriter bw = new BufferedWriter(fw, bufLen);) {
			bw.write(content.toString());
			flag = true;
		} catch (IOException e) {
			LOGGER.error("newFile", e.getMessage());
			throw e;
		}

		return flag;
	}

	/**
	 * 新建一个文件并写入内容
	 *
	 * @param path
	 *            文件全路径
	 * @param fileName
	 *            文件名
	 * @param content
	 *            内容
	 * @return boolean
	 * @throws IOException
	 */
	public static boolean newFile(String path, String fileName, String content)
			throws IOException {

		return newFile(path, fileName, content, 1024, false);
	}

	/**
	 * 新建一个文件并写入内容
	 *
	 * @param path
	 *            文件全路径
	 * @param fileName
	 *            文件名
	 * @param content
	 *            内容
	 * @param isWrite
	 *            是否追加写入文件
	 * @return boolean
	 * @throws IOException
	 */
	public static boolean newFile(String path, String fileName, String content,
			boolean isWrite) throws IOException {

		return newFile(path, fileName, content, 1024, isWrite);
	}

	/**
	 * 新建一个文件并写入内容
	 *
	 * @param path
	 *            文件全路径
	 * @param fileName
	 *            文件名
	 * @param content
	 *            内容
	 * @param bufLen
	 *            设置缓冲区大小
	 * @param isWrite
	 *            是否追加写入文件
	 * @return boolean
	 * @throws IOException
	 */
	public static boolean newFile(String path, String fileName, String content,
			int bufLen, boolean isWrite) throws IOException {

		if (path == null || "".equals(path) || content == null
				|| "".equals(content))
			return false;
		boolean flag;

		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		try (FileWriter fw = new FileWriter(path + File.separator + fileName,
				isWrite); BufferedWriter bw = new BufferedWriter(fw, bufLen);) {
			bw.write(content);
			flag = true;
		} catch (IOException e) {
			LOGGER.error("newFile", "写入文件出错", e);
			throw e;
		}

		return flag;
	}
	
	
	/**
	 * 新建一个文件并写入内容
	 *
	 * @param path
	 *            文件全路径
	 * @param fileName
	 *            文件名
	 * @param content
	 *            内容
	 * @param bufLen
	 *            设置缓冲区大小
	 * @param isWrite
	 *            是否追加写入文件
	 * @return boolean
	 * @throws IOException
	 */
	
	public static boolean newFileAndWrite(String path, String fileName, String content,
			int bufLen, boolean isWrite) throws IOException {
		
		if (path == null || "".equals(path) || content == null
				|| "".equals(content))
			return false;
		
		boolean flag = false;
		//文件夹不存在则创建
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		
		try{
			
			FileWriter fw = new FileWriter(path + File.separator + fileName,isWrite); 
			BufferedWriter bw = new BufferedWriter(fw, bufLen);
			bw.write(content);
			bw.flush();
			bw.newLine();
			bw.close();
			flag = true;
			
		}catch(IOException e){
			LOGGER.error("newFile", "写入文件出错", e);
			throw e;
			
		}
		

		return flag;
		
	}
	
	

	/**
	 * 新建一个目录
	 *
	 * @param filePath
	 *            路径
	 * @return boolean flag
	 * @throws Exception
	 */
	public static boolean newFolder(String filePath) throws IOException {
		boolean flag = false;
		if (filePath == null || "".equals(filePath) || "null".equals(filePath)) {
			return flag;
		}

		File myFilePath = new File(filePath);
		if (!myFilePath.exists()) {
			flag = myFilePath.mkdirs();
		}

		return flag;
	}

	/**
	 * 复制单个文件
	 *
	 * @param oldPath
	 *            String 原文件路径 如：c:/fqf.txt
	 * @param newPath
	 *            String 复制后路径 如：f:
	 * @return boolean
	 */
	public static boolean copyFile(String oldPath, String newPath)
			throws IOException {
		return copyFile(oldPath, newPath, null);
	}

	/**
	 * 复制单个文件
	 *
	 * @param oldPath
	 *            String 原文件路径 如：c:/fqf.txt
	 * @param newPath
	 *            String 复制后路径 如：f:/
	 * @param newFileName
	 * @return boolean
	 */
	public static boolean copyFile(String oldPath, String newPath,
			String newFileName) throws IOException {
		String fileName = "";
		boolean flag = false;
		if (oldPath == null || newPath == null || "".equals(newPath)
				|| "".equals(oldPath)) {
			return flag;
		}
		int byteread = 0;
		File file = null;
		file = new File(newPath);
		if (!file.exists()) {
			file.mkdirs();
		}
		file = new File(oldPath);
		if (newFileName == null || "".equals(newFileName)) {
			fileName = file.getName();
		}else{
			fileName = newFileName;
		}
		if (file.exists()) { // 文件存在时
			try (InputStream inStream = new FileInputStream(oldPath);
					FileOutputStream fs = new FileOutputStream(newPath
							+ File.separator + fileName);) {
				byte[] buffer = new byte[1024 * 8];
				while ((byteread = inStream.read(buffer)) != -1) {
					fs.write(buffer, 0, byteread);
				}
			} catch (IOException e) {
				throw e;
			}
			flag = true;
		}
		return flag;
	}

	/**
	 * 复制整个文件夹内容
	 *
	 * @param oldPath
	 *            String 原文件路径 如：c:/fqf
	 * @param newPath
	 *            String 复制后路径 如：f:/fqf/ff
	 */
	public static void copyFolder(String oldPath, String newPath)
			throws Exception {
		if (oldPath == null || newPath == null || "".equals(newPath)
				|| "".equals(oldPath)) {
			return;
		}

		File temp = null;
		temp = new File(newPath);
		if (!temp.exists()) {
			temp.mkdirs();// 如果文件夹不存在 则建立新文件夹
		}
		temp = new File(oldPath);
		String[] file = temp.list();
		for (int i = 0; i < file.length; i++) {
			if (oldPath.endsWith(File.separator)) {
				temp = new File(oldPath + file[i]);
			} else {
				temp = new File(oldPath + File.separator + file[i]);
			}

			if (temp.isFile()) {
				try (FileInputStream input = new FileInputStream(temp);
						FileOutputStream output = new FileOutputStream(newPath
								+ File.separator + temp.getName());) {
					byte[] b = new byte[1024 * 8];
					int len;
					while ((len = input.read(b)) != -1) {
						output.write(b, 0, len);
					}
					input.close();
					output.flush();
					output.close();
				} catch (IOException e) {
					throw e;
				}
			}
			if (temp.isDirectory()) {// 如果是子文件夹
				copyFolder(oldPath + File.separator + file[i], newPath
						+ File.separator + file[i]);
			}
		}

	}

	/**
	 * 移动单个文件
	 *
	 * @param srcFile
	 *            源文件 如：c:/test.txt
	 * @param destPath
	 *            目标目录 如：c：/test
	 * @param newFileName
	 *            新文件名 如：newTest.txt
	 * @return boolean
	 */
	public static boolean move(String srcFile, String destPath) {
		return move(srcFile, destPath, null);
	}

	/**
	 * 移动单个文件
	 *
	 * @param srcFile
	 *            源文件 如：c:/test.txt
	 * @param destPath
	 *            目标目录 如：c：/test
	 * @param newFileName
	 *            新文件名 如：newTest.txt
	 * @return boolean
	 */
	public static boolean move(String srcFile, String destPath,
			String newFileName) {
		boolean flag = false;
		if (srcFile == null || "".equals(srcFile) || destPath == null
				|| "".equals(destPath)) {
			return flag;
		}
		File file = new File(srcFile);
		File dir = new File(destPath);
		String defaultNewName = newFileName;
		if (newFileName == null || "".equals(newFileName)
				|| "null".equals(newFileName))
			defaultNewName = file.getName();
		flag = file.renameTo(new File(dir, defaultNewName));

		return flag;
	}

	/**
	 * 移动文件到指定目录
	 *
	 * @param oldPath
	 *            如：c:/fqf.txt
	 * @param newPath
	 *            如：d:/
	 */
	public static void moveFile(String oldPath, String newPath)
			throws IOException {
		if (copyFile(oldPath, newPath))
			delFile(oldPath);

	}

	/**
	 * 移动文件到指定目录
	 *
	 * @param oldPath
	 *            如：c:/fqf.txt
	 * @param newPath
	 *            如：d:/
	 * @param newFileName
	 *            新文件名 如：newTest.txt
	 */
	public static void moveFile(String oldPath, String newPath,
			String newFileName) throws IOException {
		if (copyFile(oldPath, newPath, newFileName))
			delFile(oldPath);

	}

	/**
	 * 移动文件夹到指定目录
	 *
	 * @param oldPath
	 *            如：c:/fqf.txt
	 * @param newPath
	 *            如：d:/
	 */
	public static void moveFolder(String oldPath, String newPath)
			throws Exception {
		copyFolder(oldPath, newPath);
		delFolder(oldPath);
	}

	/**
	 * 删除文件
	 *
	 * @param filePathAndName
	 *            文件路径及名称 如c:/fqf.txt
	 */
	public static void delFile(String filePathAndName) {
		if (filePathAndName == null || "".equals(filePathAndName)
				|| "null".equals(filePathAndName))
			return;
		try {
			File myDelFile = new File(filePathAndName);
			if (myDelFile.exists() && !myDelFile.isDirectory()&&myDelFile.delete())
				LOGGER.info("delFile", "删除文件" + myDelFile.getName());

		} catch (Exception e) {
			LOGGER.error("delFile", "删除文件出错", e);
		}

	}

	/**
	 * 删除文件夹
	 *
	 * @param folderPath
	 *            文件夹路径及名称 如c:/fqf
	 */
	public static void delFolder(String folderPath) {
		if (folderPath == null || "".equals(folderPath))
			return;
		try {
			File myFilePath = new File(folderPath);
			if (myFilePath.isDirectory() && myFilePath.exists()) {
				delAllFile(folderPath); // 删除完里面所有内容
				myFilePath.delete(); // 删除空文件夹
			}

		} catch (Exception e) {
			LOGGER.error("delFolder", "删除文件夹操作出错", e);

		}

	}

	/**
	 * 删除文件夹里面的所有文件
	 *
	 * @param path
	 *            文件夹路径 如 c:/fqf
	 */
	public static void delAllFile(String path) {
		if (path == null || "".equals(path))
			return;
		File file = new File(path);
		if (!file.exists() || !file.isDirectory()) {
			return;
		}
		String[] tempList = file.list();
		File temp;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile() && temp.delete()) {
				LOGGER.info("delAllFile", "删除文件成功" + temp.getName());
			}
			if (temp.isDirectory()) {
				delAllFile(path + File.separator + tempList[i]);// 先删除文件夹里面的文件
				delFolder(path + File.separator + tempList[i]);// 再删除空文件夹
			}
		}
	}

	/**
	 * 删除目录以及当前目录下的文件或子目录。用递归就可以实现。
	 *
	 * @param filepath
	 *            目录
	 * @throws Exception
	 */
	public static void del(String filepath) throws IOException {
		if (filepath == null || "".equals(filepath) || "null".equals(filepath))
			return;
		try {
			File f = new File(filepath);// 定义文件路径
			File[] delFile = f.listFiles();
			if(delFile.length==0 && f.delete()){
				return;
			}
			for (File delF : delFile) {
				if (delF.isDirectory()) {
					del(delF.getAbsolutePath());// 递归调用del方法并取得子目录路径
				}else if(delF.delete()){
					LOGGER.info("del", "删除文件成功"+delF.getName());
				}
			}
		} catch (IOException e) {
			throw e;
		}
	}

	/**
	 * 
	 // windows系统路径转换
	 * 
	 * @param filePath
	 * @return
	 */
	public static String getFilePath(String filePath) {
		Properties prop = System.getProperties();
		String os = prop.getProperty("os.name");
		String ret = filePath;
		if (os.startsWith("win") || os.startsWith("Win")) {
			ret = "C:" + filePath;
		}
		return ret;
	}

}
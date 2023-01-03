package com.hse.common.utils;

import java.io.*;

/**
 * @Description:文件工具类
 * @Copyright: Copyright (c) 2017 HYKJ All Rights Reserved
 * @Company: 福建互医科技有限公司
 * @author yangzl 2019-11-12
 * @version 1.00.00
 * @history:
 */
public class FileUtil {

	/**
	 * 下载文件
	 * @param filepath
	 * @param response
	 * @return
	 */
//	public static void downloadFile(String filepath, HttpServletResponse response) throws IOException {
//		File file = new File(filepath);
//		OutputStream out = response.getOutputStream();
//
//		BufferedInputStream br = new BufferedInputStream(new FileInputStream(file));
//		byte[] buf = new byte[1024];
//		int len = 0;
//
////        String filename = file.getName().substring(file.getName().lastIndexOf("\\") + 1);
//		response.reset();
//		response.setContentType("application/x-msdownload");
//		response.setHeader("Content-Disposition", "attachment; filename=" +  java.net.URLEncoder.encode(file.getName(), "UTF-8"));
//
//		while ((len = br.read(buf)) > 0) {
//			out.write(buf, 0, len);
//		}
//		br.close();
//		out.close();
//	}

	/**
	 * 获取文件名
	 * @param originalFilename
	 * @return
	 */
	public static String getFileName(String originalFilename) {
		
		 String name = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
		 return name;
	}
	
	/**
	 * 获取文件扩展名
	 * @param originalFilename
	 * @return
	 */
	public static String getExtName(String originalFilename) {
		
		String ext = originalFilename.substring(originalFilename.lastIndexOf(".") + 1, originalFilename.length());
		return ext;
	}

	public static String readToString(String fileName) {
		String encoding = "UTF-8";
		File file = new File(fileName);
		Long filelength = file.length();
		byte[] filecontent = new byte[filelength.intValue()];
		try {
			FileInputStream in = new FileInputStream(file);
			in.read(filecontent);
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			return new String(filecontent, encoding);
		} catch (UnsupportedEncodingException e) {
			System.err.println("The OS does not support " + encoding);
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 流转二进制数组
	 *
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public static byte[] inputStream2ByteArray(InputStream in) throws IOException {

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024 * 4];
		int n = 0;
		while ((n = in.read(buffer)) != -1) {
			out.write(buffer, 0, n);
		}
		return out.toByteArray();
	}


	/**
	 * 读取文件内容为二进制数组
	 *
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public static byte[] read2ByteArray(String filePath) throws IOException {

		InputStream in = new FileInputStream(filePath);
		byte[] data = inputStream2ByteArray(in);
		in.close();

		return data;
	}

	/**
	 * 迭代删除文件夹
	 * @param dirPath 文件夹路径
	 */
	public static void deleteDir(String dirPath) {

		File file = new File(dirPath);

		if (file.isFile()) {

			file.delete();
		} else {

			File[] files = file.listFiles();

			if (files == null) {

				file.delete();
			} else {

				for (int i = 0; i < files.length; i++) {

					deleteDir(files[i].getAbsolutePath());
				}

				file.delete();
			}
		}
	}

	/**
	 * 删除文件
	 *
	 * @param filePath
	 * @return
	 */
	public static boolean deleteFile(String filePath) {

		File file = new File(filePath);

		if (file.isFile() && file.exists()) {

			/*boolean result = file.delete();

			 if(!result){

			    System.gc();
			    return file.delete();
			 }else {
				 return true;
			 }*/
			file.delete();
			return true;

		} else {
			return false;
		}
	}

	public static void createDirs(String path) {

		File file = new File(path);

		if (!file.exists()) {
			file.mkdirs();
		}
	}

	/**
	 * 创建文件
	 *
	 * @param filePath
	 * @return
	 */
	public static File createFile(String filePath) {

		File tempFile = new File(filePath); // 文件保存路径为pathRoot + path

		if (!tempFile.getParentFile().exists()) {
			tempFile.getParentFile().mkdir();
		}

		if (!tempFile.exists()) {
			tempFile.mkdir();
		} else {
			//存在同名文件则删除
			tempFile.delete();
		}

		return tempFile;
	}
}

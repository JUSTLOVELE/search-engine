package com.hse.common.utils;

import net.coobird.thumbnailator.Thumbnails;

import java.io.File;
import java.io.IOException;

/**
 * @Description:图片工具类
 * @Copyright: Copyright (c) 2017 HYKJ All Rights Reserved
 * @Company: 福建互医科技有限公司
 * @author yangzl 2022-02-23
 * @version 1.00.00
 * @history:
 */
public class PicUtils {
	
/*	public static void main(String[] args) {
		 
		PicUtils.commpressPicForScale("C:\\Users\\123\\Desktop\\1.png",
		"C:\\Users\\123\\Desktop\\12.jpg", Long.valueOf(500), 0.8); // 图片小于500kb
	}*/



	/**
	 * 根据指定大小和指定精度压缩图片
	 * @param originalpath 源图片地址
	 * @param compresspath 目标图片地址
	 * @param prefix
	 * @return false:没有压缩
	 * 			    true:压缩
	 */
	public static boolean compressPicForScale(String originalpath, String compresspath, String prefix) throws IOException {
		
		if(originalpath == null || "".equals(originalpath)) {
			return false;
		}
		
		if(compresspath == null || "".equals(compresspath)) {
			return false;
		}

		File srcFile = new File(originalpath);
		long srcFileSize = srcFile.length();
		long srcFileKb = srcFileSize/1024;

		if(srcFileKb > 200) {

			float size = 400;
			float scale = size/srcFileKb;
			Thumbnails.of(originalpath).scale(scale).outputFormat(prefix).toFile(compresspath);
		}else {
			return false;
		}
		// 1、先转换成jpg
		//Thumbnails.of(originalpath).scale(1f).outputQuality(0.25f).outputFormat(prefix).toFile(compresspath);
		// 递归压缩直到目标文件大小小于200k
		//commpressPicCycle(compresspath, prefix);
		
		return true;
	}
	
	/*private static void commpressPicCycle(String compresspath, String prefix) throws IOException {

		File srcFileJPG = new File(compresspath);
		long srcFileSizeJPG = srcFileJPG.length();

		if (srcFileSizeJPG <= 200 * 1024) {
			_logger.info("现在图片大小:" + srcFileSizeJPG/1024 + "kb");
			return;
		}
		
		Thumbnails.of(compresspath).scale(1f).outputQuality(0.5f).outputFormat(prefix).toFile(compresspath);
		commpressPicCycle(compresspath, prefix);
	}*/
}

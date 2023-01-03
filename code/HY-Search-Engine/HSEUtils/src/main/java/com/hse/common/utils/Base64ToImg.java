package com.hse.common.utils;

import java.io.*;

/**
 * @author yangzl 2021.06.02
 * @version 1.00.00
 * @Description:
 * @Copyright: Copyright (c) 2017 HYKJ All Rights Reserved
 * @Company: 福建互医科技有限公司
 * @history:
 */
public class Base64ToImg {


    public static void main(String[] args) {
        String str = "";
        str = imagetoString("C:\\logs\\22.jpg");
        String generatePath = "C:\\logs\\3.jpg";//生成的图片路径
        stringToImage(str, generatePath);
    }

    /**
     * 图片转化成base64字符串
     * @param imgPath
     * @return
     */
    public static String imagetoString(String imgPath) {
        InputStream in = null;
        byte[] data = null;
        // 读取图片字节数组
        try {
            in = new FileInputStream(imgPath);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        System.out.println(Base64.encode(data));
        return Base64.encode(data);// 返回Base64编码过的字节数组字符串
    }

    /**
     * 对字节数组字符串进行Base64解码并生成图片
     * @param imgStr
     * @return
     */
    public static boolean stringToImage(String imgStr, String generatePath) {

        if (imgStr == null) {
            // 图像数据为空
            return false;
        }
        try {
            // Base64解码
            byte[] b = Base64.decode(imgStr);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {// 调整异常数据
                    b[i] += 256;
                }
            }

            OutputStream out = new FileOutputStream(generatePath);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

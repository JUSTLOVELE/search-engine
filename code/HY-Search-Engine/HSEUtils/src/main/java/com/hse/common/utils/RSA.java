package com.hse.common.utils;

import cn.hutool.Hutool;
import cn.hutool.core.util.StrUtil;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * 1、公钥加密，私钥解密用于信息加密
 * 2、私钥加密，公钥解密用于数字签名
 * https://blog.csdn.net/s706335465/article/details/50344941
 */

/**
 * @Description:RSA加密
 * @Copyright: Copyright (c) 2017 HYKJ All Rights Reserved
 * @Company: 福建互医科技有限公司
 * @author yangzl 2022-02-23
 * @version 1.00.00
 * @history:
 */
public class RSA {
    /**
     * 测试方法
     */
    public static void main(String[] args) {
    	
    	/*String m = "hello";
    	String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAkwib1wvrMtUskleuSgRf5NvPFkusV97ODZvj/ugZJnlbnOMH7uS4I1MExOHf+9q//PyCUdQvRTDR48y6Mt47Kj/an3+QAQM2tcbi4SJfRfs910K5MAC7kG2WHzHA/geLNrdpFiP1sVOOj1o659otbzVeDCnr7Q4biXzAPTFJDRazbtOH6rkdK+9q5r1LLZW3sQ/ohQQ6VFwS6OPNOceHDVuO0bWCeJNoMaP0szNa7T6WCIxdp2xCa6su74FAeofwEh9+/cMB+FeYYeY0Q2+wkGmA9IBmOIzK+py20NxKEaWeqfL2Q+v5SjeCutfiCQc0dEFPn1xN0+4FVKgc7CP3MwIDAQAB";
    	String privateKey = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCTCJvXC+sy1SySV65KBF/k288WS6xX3s4Nm+P+6BkmeVuc4wfu5LgjUwTE4d/72r/8/IJR1C9FMNHjzLoy3jsqP9qff5ABAza1xuLhIl9F+z3XQrkwALuQbZYfMcD+B4s2t2kWI/WxU46PWjrn2i1vNV4MKevtDhuJfMA9MUkNFrNu04fquR0r72rmvUstlbexD+iFBDpUXBLo4805x4cNW47RtYJ4k2gxo/SzM1rtPpYIjF2nbEJrqy7vgUB6h/ASH379wwH4V5hh5jRDb7CQaYD0gGY4jMr6nLbQ3EoRpZ6p8vZD6/lKN4K61+IJBzR0QU+fXE3T7gVUqBzsI/czAgMBAAECggEAAZxgiLSauQ3Y1SmcwTbXDhzSYf4mPzgzrbZ9gvdr/x3pQQjDt3xOACUSsk1/NLUq0Uy66MaYVfu48PP3PkNtBoN4k7LEEx7D5K3vImRMAupDvS3/9WSsqWJp+KGE6cOPZOQRhKqUMIMn1q44Yv3i7Yr6Isv6x7gV2SXCvWji0f/fZ/SiydPPrXl4mVEiAcGn56d23J2AE+Mtj/zYscWGzLpjU6D7FC9PDvv10bDoSBeWIl2GEaeGuuHdkn9tajM+ny/ndB0IVqdU6RXDgU2Vc5H4ivsnCsI/wbJb/qrdPBBrJMMHxUYQdWdDpqzJIfVOlU0+rkYylPto5EaZPM9H4QKBgQDRUbWq+eEeSffpUZk8+Fb44ZOQgUrTwPoXfFRmSK0xHyZq8mXfEn2Sac7xJRtqD7MmLUWgPvpYuw8X25KYSiCNljJsHpr/6XIdBX679Gc7838yOnrkVkBBArKDadQWuPeb78mrPtss3tPVjzohIORbuLY4n3UGhRygLGEliSkz6QKBgQCz0vCsSQ2nbVLBetoikhE6a+C0sCnSeIz2utq/JRRtXypsmggTptajwnoqL7TUss5jCb/Ph0CSjAea3s6ZWNChFQ2lwywgHrO4ic07UMi4qIfcSuNFTYP58XSmjqiuYVxJnZNamXKWag/eZTiQJrtBVw8EoXZOBKjLPdoJbMcsuwKBgQCvU4+lbLQhWLes2MpQBXtj8B5YFdQFVkikz2EqY4md8YeOYQbvOF+x+ap61igGSKw4OAdv2BB9rgFQbHx3zu9BaSNG6MPPw0vv/yBOcsLdtnE+MzmodI5RZd78r/IULk5IcSssovOWu/YXlWaNRS3BnhATkYzGL0zw5PtiI2c9sQKBgQCvNikqtUOIjwzDWGCSrp/6vIIFjGAzoyI2hXn+JLLynndHug2EmFsq1KMT18fqNw7BkQ5DtxVDtQzz6YSEBfXB/jF+Ad1Mrp2OgTZnRR9OTWeTTaSEMjMVph5y6y2QO20gjVi2Pi1YQZ+UoiuqyRJLZ/hKGsia3A9beLip1kN7YQKBgQDC6inLcY/dhzVogMTICYuT48/4aYkP3nZSIDYHHKQfVCBimIK86S+o6LFPoYpTHgtfjLJBHjRAIC4+4Bwv3SViLvh6o/VqGmiUSXEkkVyXxd5DEx6SukInOHYDa+mp21R1Emxdy7KIegaykjigE8XbR1K8i1BWHzhskOmVTx88eQ==";
        String cipher = RSA.encryptByPublic(m, publicKey);
    	System.out.println(cipher.length());*/
        //System.out.println(RSA.decryptByPrivate(cipher, privateKey));
        Map<String, Object> map = new HashMap<String, Object>();
        map = RSA.init();
        String publicKey =  RSA.getPublicKey(map);
        String privateKey = RSA.getPrivateKey(map);
        System.out.println(publicKey);
        System.out.println(privateKey);
        String enStr1 = RSA.encryptByPublic(String.valueOf(Integer.MAX_VALUE), publicKey);
        System.out.println(enStr1.length());
        String destr1 = RSA.decryptByPrivate(enStr1, privateKey);
        System.out.println(destr1);
        //由前四行代码获得公、私密钥
    	/*String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAkwib1wvrMtUskleuSgRf5NvPFkusV97ODZvj/ugZJnlbnOMH7uS4I1MExOHf+9q//PyCUdQvRTDR48y6Mt47Kj/an3+QAQM2tcbi4SJfRfs910K5MAC7kG2WHzHA/geLNrdpFiP1sVOOj1o659otbzVeDCnr7Q4biXzAPTFJDRazbtOH6rkdK+9q5r1LLZW3sQ/ohQQ6VFwS6OPNOceHDVuO0bWCeJNoMaP0szNa7T6WCIxdp2xCa6su74FAeofwEh9+/cMB+FeYYeY0Q2+wkGmA9IBmOIzK+py20NxKEaWeqfL2Q+v5SjeCutfiCQc0dEFPn1xN0+4FVKgc7CP3MwIDAQAB";
    	System.out.println(publicKey);
    	String privateKey = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCTCJvXC+sy1SySV65KBF/k288WS6xX3s4Nm+P+6BkmeVuc4wfu5LgjUwTE4d/72r/8/IJR1C9FMNHjzLoy3jsqP9qff5ABAza1xuLhIl9F+z3XQrkwALuQbZYfMcD+B4s2t2kWI/WxU46PWjrn2i1vNV4MKevtDhuJfMA9MUkNFrNu04fquR0r72rmvUstlbexD+iFBDpUXBLo4805x4cNW47RtYJ4k2gxo/SzM1rtPpYIjF2nbEJrqy7vgUB6h/ASH379wwH4V5hh5jRDb7CQaYD0gGY4jMr6nLbQ3EoRpZ6p8vZD6/lKN4K61+IJBzR0QU+fXE3T7gVUqBzsI/czAgMBAAECggEAAZxgiLSauQ3Y1SmcwTbXDhzSYf4mPzgzrbZ9gvdr/x3pQQjDt3xOACUSsk1/NLUq0Uy66MaYVfu48PP3PkNtBoN4k7LEEx7D5K3vImRMAupDvS3/9WSsqWJp+KGE6cOPZOQRhKqUMIMn1q44Yv3i7Yr6Isv6x7gV2SXCvWji0f/fZ/SiydPPrXl4mVEiAcGn56d23J2AE+Mtj/zYscWGzLpjU6D7FC9PDvv10bDoSBeWIl2GEaeGuuHdkn9tajM+ny/ndB0IVqdU6RXDgU2Vc5H4ivsnCsI/wbJb/qrdPBBrJMMHxUYQdWdDpqzJIfVOlU0+rkYylPto5EaZPM9H4QKBgQDRUbWq+eEeSffpUZk8+Fb44ZOQgUrTwPoXfFRmSK0xHyZq8mXfEn2Sac7xJRtqD7MmLUWgPvpYuw8X25KYSiCNljJsHpr/6XIdBX679Gc7838yOnrkVkBBArKDadQWuPeb78mrPtss3tPVjzohIORbuLY4n3UGhRygLGEliSkz6QKBgQCz0vCsSQ2nbVLBetoikhE6a+C0sCnSeIz2utq/JRRtXypsmggTptajwnoqL7TUss5jCb/Ph0CSjAea3s6ZWNChFQ2lwywgHrO4ic07UMi4qIfcSuNFTYP58XSmjqiuYVxJnZNamXKWag/eZTiQJrtBVw8EoXZOBKjLPdoJbMcsuwKBgQCvU4+lbLQhWLes2MpQBXtj8B5YFdQFVkikz2EqY4md8YeOYQbvOF+x+ap61igGSKw4OAdv2BB9rgFQbHx3zu9BaSNG6MPPw0vv/yBOcsLdtnE+MzmodI5RZd78r/IULk5IcSssovOWu/YXlWaNRS3BnhATkYzGL0zw5PtiI2c9sQKBgQCvNikqtUOIjwzDWGCSrp/6vIIFjGAzoyI2hXn+JLLynndHug2EmFsq1KMT18fqNw7BkQ5DtxVDtQzz6YSEBfXB/jF+Ad1Mrp2OgTZnRR9OTWeTTaSEMjMVph5y6y2QO20gjVi2Pi1YQZ+UoiuqyRJLZ/hKGsia3A9beLip1kN7YQKBgQDC6inLcY/dhzVogMTICYuT48/4aYkP3nZSIDYHHKQfVCBimIK86S+o6LFPoYpTHgtfjLJBHjRAIC4+4Bwv3SViLvh6o/VqGmiUSXEkkVyXxd5DEx6SukInOHYDa+mp21R1Emxdy7KIegaykjigE8XbR1K8i1BWHzhskOmVTx88eQ==";
    	System.out.println(privateKey);
        String str = "dVz+HJ6QTcK0tvAh5gcmEEO2+ZHWRMLwcyP88PoGaoh22SEi8hOk+ayvqMvBq/qnHw/58vepaWBpPZaa3ndk7+jIizs+LkofHkwOHf2Dw34fPHMPKRcaaUqJ+a94Vo1lilROKzK23m1Ety7vusuRKqmFsrUMxW0WA8d0ijw1R6aR5NwQwgfK0fZN9w9FTMl5ysqjWOO7bW9Jh0xaaNyMZu64MRHMy7NfUbBvN2uzUIH9oPVXrbe0YYCpPSHbOuZRMG2RqzkKW22IcA3NvlKTZpW1vAeCwrvTxjq1Ub0ZJTBdXYDnx0s3jW0x10bdx/SnwdfPSOngRQKkZWI1IYP8ug==";
        // 公钥加密，私钥解密
        String enStr1 = RSA.encryptByPublic(str, publicKey);
        System.out.println("公钥加密后："+enStr1);
        String deStr1 = RSA.decryptByPrivate(enStr1, privateKey);
        System.out.println("私钥解密后："+deStr1);
        // 私钥加密，公钥解密
        String enStr2 = RSA.encryptByPrivate(str, privateKey);
        System.out.println("私钥加密后："+enStr2);
        String deStr2 = RSA.decryptByPublic(enStr2, publicKey);
        System.out.println("公钥解密后："+deStr2);
        // 产生签名  
        String sign = sign(enStr2, privateKey);
        System.out.println("签名:"+sign);
        // 验证签名 
        boolean status = verify(enStr2, publicKey, sign);
        System.out.println("状态:"+status);
        */
    }
    
    //定义加密方式
    public static final String KEY_RSA = "RSA";
    //定义公钥关键词
    public static final String KEY_RSA_PUBLICKEY = "RSAPublicKey";
    //定义私钥关键词
    public static final String KEY_RSA_PRIVATEKEY = "RSAPrivateKey";
    //定义签名算法
    private final static String KEY_RSA_SIGNATURE = "MD5withRSA";
    
    private static String RSA_ANDROID = "RSA/ECB/PKCS1Padding";
    private static String RSA_JAVA = "RSA/None/PKCS1Padding";

    /**
     * 验证
     * @param content
     * @param sign
     * @param publicKey
     * @return
     */
    public static boolean verifyBySHA256WithRSA(String content, String sign, String publicKey){

        if (StrUtil.isBlank(publicKey)) {
            //缺少验签公钥
            return false;
        }

        try {

            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKey));
            PublicKey pubKey = KeyFactory.getInstance("RSA").generatePublic(keySpec);
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initVerify(pubKey);
//            signature.update(content.getBytes(charset));
            signature.update(content.getBytes());
            return signature.verify(Base64.getDecoder().decode(sign));
        } catch (Exception e) {
            //验签失败
            return false;
        }
    }

    /**
     * SHA256WithRSA
     *
     * @param content
     * @param privateKey
     * @return
     */
    public static String signBySHA256WithRSA(String content, String privateKey){

        if(StrUtil.isBlank(privateKey)){
            //缺少签名私钥
            return null;
        }

        try {

            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey));
            PrivateKey priKey = KeyFactory.getInstance("RSA").generatePrivate(priPKCS8);
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initSign(priKey);
//            signature.update(content.getBytes(charset));
            signature.update(content.getBytes());

            return Base64.getEncoder().encodeToString(signature.sign());
        } catch (Exception e) {
            //签名失败
            return null;
        }
    }

    //只要在解密后台数据的时候，用android默认的，在加密数据的时候，用java默认的就可以了
    /***
	 * 用公钥解密
	 * 这个是接收到后台传过来的密文，解密用的
	 * @param encryptedData
	 * @param publicKey
	 * @return
	 */
	public static byte[] decryptData(byte[] encryptedData, PublicKey publicKey) {
		try {
			Cipher cipher = Cipher.getInstance(RSA_ANDROID);
			cipher.init(Cipher.DECRYPT_MODE, publicKey);
			return cipher.doFinal(encryptedData);
		} catch (Exception e) {
			return null;
		}
	}

    
    /**
	 * 用公钥加密 <br>
	 * 这个是加密之后用来传给后台的
	 * 
	 * @param data
	 *            需加密数据的byte数据
	 * @param publicKey
	 *            公钥
	 * @return 加密后的byte型数据
	 */
	public static byte[] encryptData(byte[] data, PublicKey publicKey) {
		try {
			Cipher cipher = Cipher.getInstance(RSA_JAVA);
			// 编码前设定编码方式及密钥
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			// 传入编码数据并返回编码结果
			return cipher.doFinal(data);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

    
    /**
     * 生成公私密钥对
     */
    public static Map<String, Object> init() {  
        Map<String, Object> map = null;
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance(KEY_RSA);
            //设置密钥对的bit数，越大越安全，但速度减慢，一般使用512或1024
            generator.initialize(512);
            KeyPair keyPair = generator.generateKeyPair();
            // 获取公钥  
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            // 获取私钥  
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            // 将密钥对封装为Map
            map = new HashMap<String, Object>();
            map.put(KEY_RSA_PUBLICKEY, publicKey);
            map.put(KEY_RSA_PRIVATEKEY, privateKey);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        
        return map;
    }
    
     /** 
     * 获取Base64编码的公钥字符串
     */
    public static String getPublicKey(Map<String, Object> map) {
        String str = "";
        Key key = (Key) map.get(KEY_RSA_PUBLICKEY);
        str = encryptBase64(key.getEncoded());
        return str;  
    }
  
    /** 
     * 获取Base64编码的私钥字符串 
     */
    public static String getPrivateKey(Map<String, Object> map) {
        String str = "";
        Key key = (Key) map.get(KEY_RSA_PRIVATEKEY);
        str = encryptBase64(key.getEncoded());
        return str;
    }
  
    /** 
     * BASE64 解码 
     * @param key 需要Base64解码的字符串 
     * @return 字节数组 
     */  
    public static byte[] decryptBase64(String key) {
        return Base64.getDecoder().decode(key);
    }
  
    /** 
     * BASE64 编码 
     * @param key 需要Base64编码的字节数组 
     * @return 字符串 
     */  
    public static String encryptBase64(byte[] key) {
        return new String(Base64.getEncoder().encode(key));
    }
    
    /**
     * 公钥加密
     * @param encryptingStr
     * @param publicKeyStr
     * @return
     */
    public static String encryptByPublic(String encryptingStr, String publicKeyStr){
        try {
            // 将公钥由字符串转为UTF-8格式的字节数组
            byte[] publicKeyBytes = decryptBase64(publicKeyStr);
            // 获得公钥  
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
            // 取得待加密数据
            byte[] data = encryptingStr.getBytes("UTF-8");
            KeyFactory factory;
            factory = KeyFactory.getInstance(KEY_RSA);
            PublicKey publicKey = factory.generatePublic(keySpec);
            // 对数据加密  
            Cipher cipher = Cipher.getInstance(factory.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            // 返回加密后由Base64编码的加密信息
            return encryptBase64(cipher.doFinal(data));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    /**
     * 私钥解密
     * @param encryptedStr
     * @param privateKeyStr
     * @return
     */
    public static String decryptByPrivate(String encryptedStr, String privateKeyStr){
        try {
            // 对私钥解密  
            byte[] privateKeyBytes = decryptBase64(privateKeyStr);
            // 获得私钥 
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            // 获得待解密数据
            byte[] data = decryptBase64(encryptedStr);
            KeyFactory factory = KeyFactory.getInstance(KEY_RSA);
            PrivateKey privateKey = factory.generatePrivate(keySpec);
            // 对数据解密
            Cipher cipher = Cipher.getInstance(factory.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            // 返回UTF-8编码的解密信息
            return new String(cipher.doFinal(data), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }

    /**
     * 私钥加密
     * @param encryptingStr
     * @param privateKeyStr
     * @return
     */
    public static String encryptByPrivate(String encryptingStr, String privateKeyStr){
        try {
            byte[] privateKeyBytes = decryptBase64(privateKeyStr);
            // 获得私钥  
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes); 
            // 取得待加密数据
            byte[] data = encryptingStr.getBytes("UTF-8");
            KeyFactory factory = KeyFactory.getInstance(KEY_RSA);  
            PrivateKey privateKey = factory.generatePrivate(keySpec); 
            // 对数据加密 
            Cipher cipher = Cipher.getInstance(factory.getAlgorithm());  
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            // 返回加密后由Base64编码的加密信息
            return encryptBase64(cipher.doFinal(data));  
        } catch (Exception e) {
            e.printStackTrace();  
        }
        
        return null;
    }
    
    /**
     * 公钥解密
     * @param encryptedStr
     * @param publicKeyStr
     * @return
     */
    public static String decryptByPublic(String encryptedStr, String publicKeyStr){
        try {
            // 对公钥解密  
            byte[] publicKeyBytes = decryptBase64(publicKeyStr);
            // 取得公钥  
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
            // 取得待加密数据
            byte[] data = decryptBase64(encryptedStr);
            KeyFactory factory = KeyFactory.getInstance(KEY_RSA);  
            PublicKey publicKey = factory.generatePublic(keySpec);
            // 对数据解密  
            Cipher cipher = Cipher.getInstance(factory.getAlgorithm());  
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            // 返回UTF-8编码的解密信息
            return new String(cipher.doFinal(data), "UTF-8");  
        } catch (Exception e) {  
            e.printStackTrace();  
        }
        
        return null;
    }
    
    /**
     * 用私钥对加密数据进行签名
     * @param encryptedStr
     * @param privateKey
     * @return
     */
    public static String sign(String encryptedStr, String privateKey) {
        String str = "";  
        try {
            //将私钥加密数据字符串转换为字节数组
            byte[] data = encryptedStr.getBytes();
            // 解密由base64编码的私钥  
            byte[] bytes = decryptBase64(privateKey);  
            // 构造PKCS8EncodedKeySpec对象  
            PKCS8EncodedKeySpec pkcs = new PKCS8EncodedKeySpec(bytes);  
            // 指定的加密算法  
            KeyFactory factory = KeyFactory.getInstance(KEY_RSA);  
            // 取私钥对象  
            PrivateKey key = factory.generatePrivate(pkcs);  
            // 用私钥对信息生成数字签名  
            Signature signature = Signature.getInstance(KEY_RSA_SIGNATURE);  
            signature.initSign(key);  
            signature.update(data);  
            str = encryptBase64(signature.sign());  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return str;  
    }
    
    /**
     * 校验数字签名 
     * @param encryptedStr
     * @param publicKey
     * @param sign
     * @return 校验成功返回true，失败返回false
     */
    public static boolean verify(String encryptedStr, String publicKey, String sign) {  
        boolean flag = false;
        try {
            //将私钥加密数据字符串转换为字节数组
            byte[] data = encryptedStr.getBytes();
            // 解密由base64编码的公钥  
            byte[] bytes = decryptBase64(publicKey);  
            // 构造X509EncodedKeySpec对象  
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(bytes);  
            // 指定的加密算法  
            KeyFactory factory = KeyFactory.getInstance(KEY_RSA);  
            // 取公钥对象  
            PublicKey key = factory.generatePublic(keySpec);  
            // 用公钥验证数字签名  
            Signature signature = Signature.getInstance(KEY_RSA_SIGNATURE);  
            signature.initVerify(key);  
            signature.update(data);  
            flag = signature.verify(decryptBase64(sign));
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return flag;  
    }
}

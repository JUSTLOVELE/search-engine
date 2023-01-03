package com.hse.common.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:加密工具类
 * @Copyright: Copyright (c) 2017 HYKJ All Rights Reserved
 * @Company: 福建互医科技有限公司
 * @author yangzl 2022-02-23
 * @version 1.00.00
 * @history:
 */
public class EncryptUtil {
	
	public final static String ENCRYPT_KEY = "ENCRYPT_KEY";
	
	public final static String ENCRYPT_DATA = "ENCRYPT_DATA";

	public static void main(String[] args) {

		//System.out.println(get3DESDecrypt("helloworld", "hykjhykjhykjhykjhykjhykj"));
	}
	
	/**
	 * RSA解密
	 * 3DES解密
	 * @param cipher
	 * @param key
	 * @return
	 */
	public static String getDecrypt(String cipher, String key) throws Exception {
		
		key = RSA.decryptByPrivate(key, CommonConstant.Constant_PRIVATE_KEY);
		return get3DESDecrypt(cipher, key);
		
	}
	
	/**
	 * 明文Key
	 * @param cipher
	 * @param key
	 * @return
	 */
	public static String getDecryptWithKey(String cipher, String key) throws Exception {
		return get3DESDecrypt(cipher, key);
	}
	
	/**
	 * 解密Key
	 * @param key
	 * @return
	 */
	public static String getDecryptKey(String key){
		return RSA.decryptByPrivate(key, CommonConstant.Constant_PRIVATE_KEY);
	}
	
	/**
	 * 加密数据
	 * 	铭文用3des
	 *  key用RSA
	 * @param data
	 * @return
	 */
	public static Map<String, Object> getEncrypt(String data) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		String key = getRandomKey();
		data = get3DESEncrypt(data, key);
		key = RSA.encryptByPublic(key, CommonConstant.Constant_PUBLIC_KEY);
		map.put(ENCRYPT_KEY, key);
		map.put(ENCRYPT_DATA, data);
		return map;
	}
	
	/**
	 * 加密数据
	 * 	铭文用3des
	 *  key用RSA
	 * @param data
	 * @return
	 */
	public static String getEncryptData(String data, String key) throws Exception {
		
		//Map<String, Object> map = new HashMap<String, Object>();
		data = get3DESEncrypt(data, key);
		return data;
	}
	
	/**
	 * 加密key
	 * @param key
	 * @return
	 */
	public static String getEncryptKey(String key) {
		return RSA.encryptByPublic(key, CommonConstant.Constant_PUBLIC_KEY);
	}
	
	/**
	 * 获取随机秘钥
	 * @return
	 */
	public static String getRandomKey() {
		
		String str = "";
        for (int i = 0;i<24;i++){
            str = str+ (char)(Math.random()*26+'A');
        }
		
        return str;
		/*Random random = new Random();
		return String.valueOf(random.nextInt(99999999));*/
	}
	
	/**
	 * 3-DES加密
	 * 
	 * @param src
	 *            src 要进行3-DES加密的String
	 * @param spkey
	 *            spkey分配的SPKEY
	 * @return String 3-DES加密后的String
	 */
	public static String get3DESEncrypt(String src, String spkey) throws Exception {
		return TripleDesUtil.encode(src, spkey);
	}

	/**
	 * 3-DES解密
	 * 
	 * @param src
	 *            src 要进行3-DES解密的String
	 * @param spkey
	 *            spkey分配的SPKEY
	 * @return String 3-DES加密后的String
	 */
	public static String get3DESDecrypt(String src, String spkey) throws Exception {
		
		return TripleDesUtil.decode(src, spkey);
	}
}

package com.zftlive.android.tests;

import java.io.UnsupportedEncodingException;

import android.test.AndroidTestCase;
import android.util.Log;

import com.zftlive.android.library.tools.security.Base64;
import com.zftlive.android.library.tools.security.DES;
import com.zftlive.android.library.tools.security.MD5;

/**
 * 安全加密测试用例
 * @author 曾繁添
 * @version 1.0
 *
 */
public class TestSecurity extends AndroidTestCase {

	/**
	 * 加密Key（可以用官方的签名指纹作为加密key，只要官方签名文件不泄露，加密则是安全的）
	 */
	String key = "ajavagongchengshi";
	
	/**
	 * 加密明文
	 */
	String encrypt = "Ajava工程师";
	
	String TAG = "Test";
	
	public void testBase64EnDecrypt(){
		try {
			String encodeResult = Base64.encode(encrypt.getBytes());
			Log.e(TAG, "testBase64EnDecrypt.encodeResult-->"+encodeResult);
			
			String decodeResult = new String(Base64.decode(encodeResult),"UTF-8");
			Log.e(TAG, "testDESEnDecrypt.decodeResult-->"+decodeResult);
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 测试DES加密/解密
	 */
	public void testDESEnDecrypt(){
		try {
			String encodeResult = DES.encrypt(key, encrypt);
			Log.e(TAG, "testDESEnDecrypt.encodeResult-->"+encodeResult);
			
			String decodeResult = DES.decrypt(key, encodeResult);
			Log.e(TAG, "testDESEnDecrypt.decodeResult-->"+decodeResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * MD5加密
	 */
	public void testMD5EnDecrypt(){
		try {
			String encodeResult1 = MD5.encrypt16byte(encrypt);
			Log.e(TAG, "testMD5EnDecrypt.encrypt16byte-->"+encodeResult1);
			
			String encodeResult2 = MD5.encrypt32byte(encrypt);
			Log.e(TAG, "testMD5EnDecrypt.encrypt32byte-->"+encodeResult2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}

/*
 *     Android基础开发个人积累、沉淀、封装、整理共通
 *     Copyright (c) 2016. 曾繁添 <zftlive@163.com>
 *     Github：https://github.com/zengfantian || http://git.oschina.net/zftlive
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */

package com.zftlive.android.library.tools.security;

import android.annotation.SuppressLint;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密工具类
 * 
 * @author 曾繁添
 * @version 1.0
 * 
 */
@SuppressLint("DefaultLocale")
public class MD5 {

	/**
	 * 对数据进行MD5加密
	 * 
	 * @param data
	 *            明文
	 * @return
	 * @throws Exception
	 */
	public static String encrypt16byte(String data) throws Exception {
		// 返回结果
		String result = "";
		try {

			MessageDigest md = MessageDigest.getInstance("MD5");
			md.reset();
			md.update(data.getBytes("UTF-8"));
			byte[] array = md.digest();

			StringBuffer md5StrBuff = new StringBuffer();
			for (int i = 0; i < array.length; i++) {
				if (Integer.toHexString(0xFF & array[i]).length() == 1) {
					md5StrBuff.append("0").append(
							Integer.toHexString(0xFF & array[i]));
				} else {
					md5StrBuff.append(Integer.toHexString(0xFF & array[i]));
				}
			}
			// 16位加密，从第9位到25位
			result = md5StrBuff.substring(8, 24).toString().toLowerCase();
		} catch (Exception e) {
			throw new Exception(e);
		}

		return result;
	}

	/**
	 * 对数据进行MD5加密（增加长度）
	 * 
	 * @param data
	 *            明文
	 * @return
	 * @throws Exception 
	 */
	public static String encrypt32byte(String data) throws Exception {
		byte[] hash;
		try {
			hash = MessageDigest.getInstance("MD5").digest(
					data.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			throw new Exception("Huh, MD5 should be supported?", e);
		} catch (UnsupportedEncodingException e) {
			throw new Exception("Huh, UTF-8 should be supported?", e);
		}

		StringBuilder hex = new StringBuilder(hash.length * 2);
		for (byte b : hash) {
			if ((b & 0xFF) < 0x10)
				hex.append("0");
			hex.append(Integer.toHexString(b & 0xFF));
		}

		return hex.toString().toLowerCase();
	}
}

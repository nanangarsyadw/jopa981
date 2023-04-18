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

package com.zftlive.android.library.tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 校验管理器
 * 
 * @author 曾繁添
 * @version 1.0
 */
public class ToolValidate extends ToolBase{

	/**
	 * 验证数字输入
	 *
	 * @param str 待验证的字符串
	 * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	 */
	public static boolean isNumber(String str) {
		String regex = "^[0-9]*$";
		return match(regex, str);
	}

	/**
	 * 验证验证输入字母
	 *
	 * @param str 待验证的字符串
	 * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	 */
	public static boolean isLetter(String str) {
		String regex = "^[A-Za-z]+$";
		return match(regex, str);
	}

	/**
	 * 验证验证输入汉字
	 *
	 * @param str 待验证的字符串
	 * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	 */
	public static boolean isChinese(String str) {
		String regex = "^[\u4e00-\u9fa5],{0,}$";
		return match(regex, str);
	}

	/**
	 * @param regex
	 * 正则表达式字符串
	 * @param str
	 * 要匹配的字符串
	 * @return 如果str 符合 regex的正则表达式格式,返回true, 否则返回 false;
	 */
	private static boolean match(String regex, String str) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}

	/**
	 * 验证-QQ
	 * 
	 * @param qq
	 *            qq
	 * @return 是否合法
	 */
	public static boolean validateQQ(String qq) {
		return Pattern.compile("^[1-9]\\d{4,9}$").matcher(qq).matches();
	}

	/**
	 * @alias 验证-邮政编码
	 * @param postCode
	 *            邮政编码
	 * @return 是否合法
	 */
	public static boolean validatePostCode(String postCode) {
		return Pattern.compile("^[0-8][0-9]{5}$").matcher(postCode).matches();
	}

	/**
	 * 验证-手机号
	 * 
	 * @param phoneNum
	 *            手机号
	 * @return 是否合法
	 */
	public static boolean validatePhoneNum(String phoneNum) {
		return Pattern
				.compile(
						"^(0|86|17951)?(13[0-9]|15[012356789]|18[02356789]|14[57])[0-9]{8}$")
				.matcher(phoneNum).matches();
	}

	/**
	 * 验证 密码
	 * 
	 * @param passWord
	 * @return
	 */
	public static boolean validatePassWord(String passWord) {
		boolean flag = false;
		if (null != passWord
				&& (6 <= passWord.length() && passWord.length() <= 12)) {
			flag = true;
		}
		return flag;
	}

	/**
	 * 验证-数字
	 * 
	 * @param str
	 * @return 是否合法
	 */
	public static boolean validateInt(String str) {
		return Pattern.compile("^[0-9]*[1-9][0-9]*$").matcher(str).matches();
	}

	/**
	 * 验证 正小数
	 * 
	 * @param str
	 * @return
	 */
	public static boolean validateDouble(String str) {
		boolean flag = true;
		double num = -1;
		try {
			num = Double.parseDouble(str);
		} catch (NumberFormatException e) {
			flag = false;
		}
		if (flag && num < 0)
			flag = false;
		return flag;
	}

	/**
	 * 验正时间格式
	 * 
	 * @param str
	 * @return
	 */
	public static boolean validateDate(String str) {
		boolean flag = true;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");

		try {
			sdf.parse(str);
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}
	
	
	/**////////////////////////////身份证号开始///////////////////////////////////////////////////////**/
	
	
	
	/**
	 * @alias 验证-身份证
	 * @param id_Card
	 *            身份证
	 * @return 是否合法
	 */
	public static boolean validateIDCard(String id_Card) {
		/* 1.是否合法(开始) */
		boolean flag = false;
		/* 1.是否合法(结束) */
		/* 2.验证-身份证(开始) */
		if (15 == id_Card.length() || 18 == id_Card.length()) {
			if (15 == id_Card.length()) {
				id_Card = convert15To18(id_Card);
			}
			if (null != id_Card) {
				flag = validate_Id_Card_18(id_Card);
			}
		}
		/* 2.验证-身份证(结束) */
		return flag;
	}

	/**
	 * @alias 将身份证-15位转换为18位
	 * @param id_Card_15
	 *            身份证-15位
	 * @return 身份证-18位
	 */
	private static String convert15To18(String id_Card_15) {
		/* 1.身份证18位(开始) */
		StringBuilder id_Card_18 = null;
		/* 1.身份证18位(结束) */
		/* 2.是否是数字(开始) */
		if (isDigital(id_Card_15)) {
			/* 2.1.获取出生年月日-格式为yyMMdd(开始) */
			String birthday = id_Card_15.substring(6, 12);
			/* 2.1.获取出生年月日-格式为yyMMdd(结束) */
			/* 2.2.获取出生日期(开始) */
			Date birth_Date = null;
			try {
				birth_Date = new SimpleDateFormat("yyMMdd").parse(birthday);
			} catch (ParseException e) {
				return null;
			}
			/* 2.2.获取出生日期(结束) */
			/* 2.3.获取出生年月日-年份-格式为yyyy(开始) */
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(birth_Date);
			String yyyy = String.valueOf(calendar.get(Calendar.YEAR));
			/* 2.3.获取出生年月日-年份-格式为yyyy(结束) */
			/* 2.4.组装-id_Card_18-前17位(开始) */
			id_Card_18 = new StringBuilder(id_Card_15.substring(0, 6));
			id_Card_18.append(yyyy);
			id_Card_18.append(id_Card_15.substring(8));
			/* 2.4.组装-id_Card_18-前17位(结束) */
			/* 2.5.将字符串转换为整型数组(开始) */
			int[] bit = converStringToInt(id_Card_18.toString());
			/* 2.5.将字符串转换为整型数组(结束) */
			/* 2.6.和值(开始) */
			int sum_17 = getPowerSum(bit);
			/* 2.6.和值(结束) */
			/* 2.7.校验码(开始) */
			String check_Code = getCheckCodeBySum(sum_17);
			if (null == check_Code) {
				return null;
			}
			/* 2.7.校验码(结束) */
			/* 2.8.组装-id_Card_18-最后1位(开始) */
			id_Card_18.append(check_Code);
			/* 2.8.组装-id_Card_18-最后1位(结束) */
		}
		/* 2.是否是数字(结束) */
		return null == id_Card_18 ? null : id_Card_18.toString();
	}

	/**
	 * <p>
	 * 判断18位身份证的合法性
	 * </p>
	 * 根据〖中华人民共和国国家标准GB11643-1999〗中有关公民身份号码的规定，公民身份号码是特征组合码，由十七位数字本体码和一位数字校验码组成。
	 * 排列顺序从左至右依次为：六位数字地址码，八位数字出生日期码，三位数字顺序码和一位数字校验码。
	 * <p>
	 * 顺序码: 表示在同一地址码所标识的区域范围内，对同年、同月、同 日出生的人编定的顺序号，顺序码的奇数分配给男性，偶数分配 给女性。
	 * </p>
	 * <p>
	 * 1.前1、2位数字表示：所在省份的代码； 2.第3、4位数字表示：所在城市的代码； 3.第5、6位数字表示：所在区县的代码；
	 * 4.第7~14位数字表示：出生年、月、日； 5.第15、16位数字表示：所在地的派出所的代码；
	 * 6.第17位数字表示性别：奇数表示男性，偶数表示女性；
	 * 7.第18位数字是校检码：也有的说是个人信息码，一般是随计算机的随机产生，用来检验身份证的正确性。校检码可以是0~9的数字，有时也用x表示。
	 * </p>
	 * <p>
	 * 第十八位数字(校验码)的计算方法为： 1.将前面的身份证号码17位数分别乘以不同的系数。从第一位到第十七位的系数分别为：7 9 10 5 8 4
	 * 2 1 6 3 7 9 10 5 8 4 2
	 * </p>
	 * <p>
	 * 2.将这17位数字和系数相乘的结果相加。
	 * </p>
	 * <p>
	 * 3.用加出来和除以11，看余数是多少？
	 * </p>
	 * 4.余数只可能有0 1 2 3 4 5 6 7 8 9 10这11个数字。其分别对应的最后一位身份证的号码为1 0 X 9 8 7 6 5 4 3
	 * 2。
	 * <p>
	 * 5.通过上面得知如果余数是2，就会在身份证的第18位数字上出现罗马数字的Ⅹ。如果余数是10，身份证的最后一位号码就是2。
	 * </p>
	 * 
	 * @param id_Card_18
	 * @return 是否合法
	 */
	private static boolean validate_Id_Card_18(String id_Card_18) {
		/* 1.获取-id_Card_18-前17位(开始) */
		String id_Card_17 = id_Card_18.substring(0, 17);
		/* 1.获取-id_Card_18-前17位(结束) */
		/* 2.获取-id_Card_18-最后1位(开始) */
		String id_Card_No_18 = id_Card_18.substring(17, 18);
		/* 2.获取-id_Card_18-最后1位(结束) */
		/* 3.是否是数字(开始) */
		if (!isDigital(id_Card_17)) {
			return false;
		}
		/* 3.是否是数字(结束) */
		/* 4.将字符串转换为整型数组(开始) */
		int[] bit = converStringToInt(id_Card_17);
		/* 4.将字符串转换为整型数组(结束) */
		/* 5.和值(开始) */
		int sum_17 = getPowerSum(bit);
		/* 5.和值(结束) */
		/* 6.校验码(开始) */
		String check_Code = getCheckCodeBySum(sum_17);
		/* 6.校验码(结束) */
		/* 7.验证-校验码(开始) */
		if (!id_Card_No_18.equalsIgnoreCase(check_Code)) {
			return false;
		}
		/* 7.验证-校验码(结束) */
		return true;
	}

	/**
	 * @alias 是否是数字
	 * @param id_Card
	 *            身份证
	 * @return 是或否
	 */
	private static boolean isDigital(String id_Card) {
		return id_Card == null || "".equals(id_Card) ? false : id_Card
				.matches("^[0-9]*$");
	}

	/**
	 * 将字符串转换为整型数组
	 * 
	 * @param str
	 *            字符串
	 * @return 整型数组
	 */
	private static int[] converStringToInt(String str) {
		/* 1.整型数组(开始) */
		int[] int_Str = new int[17];
		/* 1.整型数组(结束) */
		/* 2.字符串转换为整型数组(开始) */
		char[] char_Str = str.toCharArray();
		for (int i = 0; i < char_Str.length; i++) {
			int_Str[i] = Integer.valueOf(String.valueOf(char_Str[i]));
		}
		/* 2.字符串转换为整型数组(结束) */
		return int_Str;
	}

	/**
	 * @alias 将身份证的每位和对应位的加权因子相乘之后得到的和值
	 * @param bit
	 *            身份证的每位
	 * @return 和值
	 */
	private static int getPowerSum(int[] bit) {
		/* 1.和值(开始) */
		int sum = 0;
		/* 1.和值(结束) */
		if (power.length != bit.length) {
			return sum;
		}
		/* 2.计算和值(开始) */
		for (int i = 0; i < bit.length; i++) {
			for (int j = 0; j < power.length; j++) {
				if (i == j) {
					sum = sum + bit[i] * power[j];
				}
			}
		}
		/* 2.计算和值(结束) */
		return sum;
	}

	/**
	 * 将和值与11取模得到余数获取校验码
	 * 
	 * @param sum_17
	 *            和值
	 * @return 校验码
	 */
	private static String getCheckCodeBySum(int sum_17) {
		/* 1.校验码(开始) */
		String check_Code = null;
		/* 1.校验码(结束) */
		/* 2.计算校验码(开始) */
		switch (sum_17 % 11) {
		case 10:
			check_Code = "2";
			break;
		case 9:
			check_Code = "3";
			break;
		case 8:
			check_Code = "4";
			break;
		case 7:
			check_Code = "5";
			break;
		case 6:
			check_Code = "6";
			break;
		case 5:
			check_Code = "7";
			break;
		case 4:
			check_Code = "8";
			break;
		case 3:
			check_Code = "9";
			break;
		case 2:
			check_Code = "x";
			break;
		case 1:
			check_Code = "0";
			break;
		case 0:
			check_Code = "1";
			break;
		}
		/* 2.计算校验码(结束) */
		return check_Code;
	}

	/**
	 * @alias 每位加权因子
	 */
	private static int power[] = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5,
			8, 4, 2 };
	
	
	/**////////////////////////////身份证号结束///////////////////////////////////////////////////////**/
	/**
	 * @alias 验证-电子邮件
	 * @param email
	 *            电子邮件
	 * @return 是否合法
	 */
	public static boolean validateEmail(String email) {
		return Pattern
				.compile(
						"^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$")
				.matcher(email).matches();
	}
	/**
	 * 验证-座机号
	 * 
	 * @param cellNum
	 *            座机号
	 * @return 是否合法
	 */
	public static boolean validateCellNum(String cellNum) {
		return Pattern.compile("^((0\\d{2,3})-)(\\d{7,8})(-(\\d{3,}))?$")
				.matcher(cellNum).matches();
	}/**
	 * 验证-车牌号
	 * 
	 * @param carNum
	 *            车牌号
	 * @return 是否合法
	 */
	public static boolean validateCarNum(String carNum) {
		return Pattern.compile("^[\\u4e00-\\u9fa5]{1}[A-Z]{1}[A-Z_0-9]{5}$")
				.matcher(carNum).matches();
	}
	/**
	 * 验证-营业执照
	 * 
	 * @param businessLicense
	 *            营业执照
	 * @return 是否合法
	 */
	public static boolean validateBusinessLicense(String businessLicense) {
		if (!Pattern.compile("\\d{15}").matcher(businessLicense).matches()) {
			return false;
		}
		int s[] = new int[100];
		int p[] = new int[100];
		int a[] = new int[100];
		int m = 10;
		p[0] = m;
		for (int i = 0; i < businessLicense.length(); i++) {
			a[i] = Integer.parseInt(businessLicense.substring(i, i + 1), m);
			s[i] = (p[i] % (m + 1)) + a[i];
			if (0 == s[i] % m) {
				p[i + 1] = 10 * 2;
			} else {
				p[i + 1] = (s[i] % m) * 2;
			}
		}
		if (1 != (s[14] % m)) {
			return false;
		}
		return true;
	}
}

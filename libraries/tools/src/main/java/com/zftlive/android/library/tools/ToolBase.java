package com.zftlive.android.library.tools;

import android.graphics.Color;
import android.text.TextUtils;

import java.util.regex.Pattern;

/**
 * 工具类基类
 *
 * @author 曾繁添
 * @version 1.0
 *
 */
public class ToolBase implements IToolConstant {

    /**
     * 判断从接口拿到的color是否可用，可用的话返回可用的color，否则返回默认的
     *
     * @param strColor
     * @param defaultColor
     * @return
     */
    public static int getColor(String strColor, String defaultColor) {
        String color = isColor(strColor) ? strColor.trim() : defaultColor;
        return Color.parseColor(color);
    }

    /**
     * 判断是否是Color的格式，即#后3、6、8位16进制的格式，返回true后使用str要做trim()过滤空格
     *
     * @param str
     * @return 是否是颜色格式
     */
    public static boolean isColor(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        str = str.trim();
        if (!str.startsWith("#") || (str.length() != 4 && str.length() != 7 && str.length() != 9)) {
            return false;
        }

        String pattern = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3}|[A-Fa-f0-9]{8})$";
        return Pattern.matches(pattern, str);
    }
}

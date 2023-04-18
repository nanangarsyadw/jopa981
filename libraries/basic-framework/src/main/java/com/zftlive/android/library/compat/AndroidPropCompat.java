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

package com.zftlive.android.library.compat;

import com.zftlive.android.library.tools.reflect.ToolMethod;

import java.lang.reflect.InvocationTargetException;

/**
 * 安卓系统属性兼容API
 *
 * @author 曾繁添
 * @version 1.0
 *
 */
public class AndroidPropCompat {

    private static Class<?> sClass;

    private static Class getMyClass() throws ClassNotFoundException {
        if (sClass == null) {
            sClass = Class.forName("android.os.SystemProperties");
        }
        return sClass;
    }

    private static String getInner(String key, String defaultValue) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
        Class clazz = getMyClass();
        return (String) ToolMethod.invokeStaticMethod(clazz, "get", key, defaultValue);
    }

    public static String get(String key, String defaultValue) {
        try {
            return getInner(key, defaultValue);
        } catch (Exception e) {
            e.printStackTrace();
            return defaultValue;
        }
    }
}


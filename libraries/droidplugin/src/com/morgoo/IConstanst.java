package com.morgoo;

import java.io.File;

/**
 * 统一管理配置性业务常量
 *
 * @author zengfantian
 * @version  1.0
 *
 */
public interface IConstanst {

    /**
     *是否DEBUG开发模式
     */
    public static final boolean IS_DEBUG = true;

    /**
     *是否写入文件日志
     */
    public static final boolean IS_WRITTE_FILE_LOG = true;

    /**
     *日志文件大小,默认8MB
     */
    public static final int LOG_FILE_MAX_SIZE = 1024 * 1024 * 8; //8MB

    /**
     * 插件日志目录
     */
    public static final String SDCARD_PLUGIN_LOG_DIR = "zftlive-plugin";

    /**
     * 插件日志目录
     */
    public static final String SDCARD_LOG_DIR = SDCARD_PLUGIN_LOG_DIR + File.separator + "Log" + File.separator;

    /**
     * 插件崩溃日志目录
     */
    public static final String SDCARD_CRASH_LOG_DIR = SDCARD_PLUGIN_LOG_DIR + File.separator + "CrashLog" + File.separator;

}

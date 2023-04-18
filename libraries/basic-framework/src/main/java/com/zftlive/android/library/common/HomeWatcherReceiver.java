package com.zftlive.android.library.common;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

/**
 * Home键监听
 *
 * @author 曾繁添
 * @version 1.0
 *
 */
public class HomeWatcherReceiver extends BroadcastReceiver {
    private static HomeWatcherReceiver mHomeKeyReceiver = null;
    private static OnHomePressListener mOnHomePressListener;
    private static final String LOG_TAG = "HomeReceiver";
    public static final String SYSTEM_DIALOG_REASON_KEY = "reason";
    public static final String SYSTEM_DIALOG_REASON_RECENT_APPS = "recentapps";
    public static final String SYSTEM_DIALOG_REASON_HOME_KEY = "homekey";
    public static final String SYSTEM_DIALOG_REASON_LOCK = "lock";
    public static final String SYSTEM_DIALOG_REASON_ASSIST = "assist";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.i(LOG_TAG, "onReceive: action: " + action);
        if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
            // android.intent.action.CLOSE_SYSTEM_DIALOGS
            String reason = intent.getStringExtra(SYSTEM_DIALOG_REASON_KEY);
            Log.i(LOG_TAG, "reason: " + reason);

            if (SYSTEM_DIALOG_REASON_HOME_KEY.equals(reason)) {
                // 短按Home键
                Log.i(LOG_TAG, "homekey");

            }
            else if (SYSTEM_DIALOG_REASON_RECENT_APPS.equals(reason)) {
                // 长按Home键 或者 activity切换键
                Log.i(LOG_TAG, "long press home key or activity switch");

            }
            else if (SYSTEM_DIALOG_REASON_LOCK.equals(reason)) {
                // 锁屏
                Log.i(LOG_TAG, "lock");
            }
            else if (SYSTEM_DIALOG_REASON_ASSIST.equals(reason)) {
                // samsung 长按Home键
                Log.i(LOG_TAG, "assist");
            }

        }
    }

    /**
     * 注册Home键监听
     * @param context
     */
    public static void registerReceiver(Context context) {
        Log.i(LOG_TAG, "registerHomeKeyReceiver");
        mHomeKeyReceiver = new HomeWatcherReceiver();
        final IntentFilter homeFilter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);

        context.registerReceiver(mHomeKeyReceiver, homeFilter);
    }

    /**
     * 取消Home键监听
     * @param context
     */
    public static void unregisterReceiver(Context context) {
        Log.i(LOG_TAG, "unregisterHomeKeyReceiver");
        if (null != mHomeKeyReceiver) {
            context.unregisterReceiver(mHomeKeyReceiver);
            mHomeKeyReceiver = null;
        }
    }

    /**
     * 设置Home监听
     * @param listener
     */
    public static void setOnHomePressListener(OnHomePressListener listener){
        HomeWatcherReceiver.mOnHomePressListener = listener;
    }

    /**
     * Home键监听
     */
    public interface OnHomePressListener{
        void onHomePress(String reason);
    }

}
package com.zftlive.android.sample.media;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.widget.Toast;

import com.zftlive.android.library.base.BaseBroadcastReceiver;
import com.zftlive.android.library.tools.ToolMedia;

/**
 * 开机启动Service，将全部音量调至最大
 * 
  <uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED" />
  <receiver android:name="com.zftlive.android.sample.media.BootBroadcastReceiver">  
        <intent-filter>  
            <action android:name="android.intent.action.BOOT_COMPLETED"></action>  
            <category android:name="android.intent.category.LAUNCHER" />  
        </intent-filter>  
   </receiver>  
 * @author 曾繁添
 * @version 1.0
 *
 */
public class BootBroadcastReceiver extends BaseBroadcastReceiver {

  @Override
  public void onReceive(Context context, Intent intent) {
    
    //将全部音量设置成最大
    int sysMaxVolume = ToolMedia.gainMaxVolume(context, AudioManager.STREAM_SYSTEM);
    ToolMedia.setVolume(context, AudioManager.STREAM_SYSTEM, sysMaxVolume);
    
    int ringMaxVolume = ToolMedia.gainMaxVolume(context, AudioManager.STREAM_RING);
    ToolMedia.setVolume(context, AudioManager.STREAM_RING, ringMaxVolume);
    
    int notifyMaxVolume = ToolMedia.gainMaxVolume(context, AudioManager.STREAM_NOTIFICATION);
    ToolMedia.setVolume(context, AudioManager.STREAM_NOTIFICATION, notifyMaxVolume);
    
    int musicMaxVolume = ToolMedia.gainMaxVolume(context, AudioManager.STREAM_MUSIC);
    ToolMedia.setVolume(context, AudioManager.STREAM_MUSIC, musicMaxVolume);
    
    int alarmMaxVolume = ToolMedia.gainMaxVolume(context, AudioManager.STREAM_ALARM);
    ToolMedia.setVolume(context, AudioManager.STREAM_ALARM, alarmMaxVolume);
    
    int callMaxVolume = ToolMedia.gainMaxVolume(context, AudioManager.STREAM_VOICE_CALL);
    ToolMedia.setVolume(context, AudioManager.STREAM_VOICE_CALL, callMaxVolume);
    Toast.makeText(context, "音频服务已启动，已将全部音量调至最大", Toast.LENGTH_SHORT).show();
  }

}

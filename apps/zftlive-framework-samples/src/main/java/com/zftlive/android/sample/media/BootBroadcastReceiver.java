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

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
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;

import com.zftlive.android.R;
import com.zftlive.android.library.base.ui.CommonActivity;
import com.zftlive.android.library.tools.ToolMedia;

/**
 * 音频控制示例DEMO
 * 
 * @author 曾繁添
 * 
 * @version 1.0
 *
 */
public class AudioManagerActivity extends CommonActivity implements SeekBar.OnSeekBarChangeListener {

  SeekBar sysSound,ringerSound,notifySound,mediaSound,alarmSound,callSound;
  boolean isInit = false;
  
  @Override
  public int bindLayout() {
    return R.layout.activity_audio_manager;
  }

  @Override
  public void initParams(Bundle parms) {

  }

  @Override
  public void initView(View view) {
    
    //初始化带返回按钮的标题栏
    String strCenterTitle = getResources().getString(R.string.AudioManagerActivity);
    mWindowTitle.initBackTitleBar(strCenterTitle);
    isInit = true;
    sysSound = (SeekBar) findViewById(R.id.sysSound);
    sysSound.setOnSeekBarChangeListener(this);
    sysSound.setMax(ToolMedia.gainMaxVolume(this, AudioManager.STREAM_SYSTEM));
    sysSound.setProgress(ToolMedia.gainCurrentVolume(this, AudioManager.STREAM_SYSTEM));
    
    ringerSound = (SeekBar) findViewById(R.id.ringerSound);
    ringerSound.setOnSeekBarChangeListener(this);
    ringerSound.setMax(ToolMedia.gainMaxVolume(this, AudioManager.STREAM_RING));
    ringerSound.setProgress(ToolMedia.gainCurrentVolume(this, AudioManager.STREAM_RING));
    
    notifySound = (SeekBar) findViewById(R.id.notifySound);
    notifySound.setOnSeekBarChangeListener(this);
    notifySound.setMax(ToolMedia.gainMaxVolume(this, AudioManager.STREAM_NOTIFICATION));
    notifySound.setProgress(ToolMedia.gainCurrentVolume(this, AudioManager.STREAM_NOTIFICATION));
    
    mediaSound = (SeekBar) findViewById(R.id.mediaSound);
    mediaSound.setOnSeekBarChangeListener(this);
    mediaSound.setMax(ToolMedia.gainMaxVolume(this, AudioManager.STREAM_MUSIC));
    mediaSound.setProgress(ToolMedia.gainCurrentVolume(this, AudioManager.STREAM_MUSIC));
    
    alarmSound = (SeekBar) findViewById(R.id.alarmSound);
    alarmSound.setOnSeekBarChangeListener(this);
    alarmSound.setMax(ToolMedia.gainMaxVolume(this, AudioManager.STREAM_ALARM));
    alarmSound.setProgress(ToolMedia.gainCurrentVolume(this, AudioManager.STREAM_ALARM));
    
    callSound = (SeekBar) findViewById(R.id.callSound);
    callSound.setOnSeekBarChangeListener(this);
    callSound.setMax(ToolMedia.gainMaxVolume(this, AudioManager.STREAM_VOICE_CALL));
    callSound.setProgress(ToolMedia.gainCurrentVolume(this, AudioManager.STREAM_VOICE_CALL));
  }

  @Override
  public void doBusiness(Context mContext) {
    isInit = false;
  }

  @Override
  public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
    Log.e(TAG, "当前进度："+progress);
    if(isInit)return;
    
    switch (seekBar.getId()) {
      case R.id.sysSound:
        ToolMedia.setVolume(getContext(), AudioManager.STREAM_SYSTEM, progress,AudioManager.FLAG_PLAY_SOUND | AudioManager.FLAG_SHOW_UI);
        sysSound.setProgress(progress);
        Log.e(TAG,"当前音量："+(progress*100/ToolMedia.gainMaxVolume(this,AudioManager.STREAM_SYSTEM))+ "%");
        break;
      case R.id.ringerSound:
        ToolMedia.setVolume(getContext(), AudioManager.STREAM_RING, progress,AudioManager.FLAG_PLAY_SOUND | AudioManager.FLAG_SHOW_UI);
        ringerSound.setProgress(progress);
        Log.e(TAG,"当前音量："+(progress*100/ToolMedia.gainMaxVolume(this, AudioManager.STREAM_RING))+ " %");
        break;
      case R.id.notifySound:
        ToolMedia.setVolume(getContext(), AudioManager.STREAM_NOTIFICATION, progress,AudioManager.FLAG_PLAY_SOUND | AudioManager.FLAG_SHOW_UI);
        notifySound.setProgress(progress);
        Log.e(TAG, "当前音量："+(progress*100/ToolMedia.gainMaxVolume(this, AudioManager.STREAM_NOTIFICATION))+ " %");
        break;
      case R.id.mediaSound:
        ToolMedia.setVolume(getContext(), AudioManager.STREAM_MUSIC, progress,AudioManager.FLAG_PLAY_SOUND | AudioManager.FLAG_SHOW_UI);
        mediaSound.setProgress(progress);
        Log.e(TAG,"当前音量："+(progress*100/ToolMedia.gainMaxVolume(this, AudioManager.STREAM_MUSIC))+ " %");
        break;
      case R.id.alarmSound:
        ToolMedia.setVolume(getContext(), AudioManager.STREAM_ALARM, progress,AudioManager.FLAG_PLAY_SOUND | AudioManager.FLAG_SHOW_UI);
        alarmSound.setProgress(progress);
        Log.e(TAG, "当前音量："+(progress*100/ToolMedia.gainMaxVolume(this, AudioManager.STREAM_ALARM))+ " %");
        break;
      case R.id.callSound:
        ToolMedia.setVolume(getContext(), AudioManager.STREAM_VOICE_CALL, progress,AudioManager.FLAG_PLAY_SOUND | AudioManager.FLAG_SHOW_UI);
        callSound.setProgress(progress);
        Log.e(TAG, "当前音量："+(progress*100/ToolMedia.gainMaxVolume(this, AudioManager.STREAM_VOICE_CALL))+ " %");
        break;
      default:
        break;
    }
  }

  @Override
  public void onStartTrackingTouch(SeekBar seekBar) {
    
  }

  @Override
  public void onStopTrackingTouch(SeekBar seekBar) {
    
  }

}

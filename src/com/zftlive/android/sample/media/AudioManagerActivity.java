package com.zftlive.android.sample.media;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;

import com.zftlive.android.R;
import com.zftlive.android.library.base.BaseActivity;
import com.zftlive.android.library.tools.ToolMedia;

/**
 * 音频控制示例DEMO
 * 
 * @author 曾繁添
 * 
 * @version 1.0
 *
 */
public class AudioManagerActivity extends BaseActivity implements SeekBar.OnSeekBarChangeListener {

  SeekBar sysSound,ringerSound,notifySound,mediaSound,alarmSound,callSound;
  boolean isInit = false;
  
  @Override
  public int bindLayout() {
    return R.layout.activity_audio_manager;
  }

  @Override
  public void initParms(Bundle parms) {

  }

  @Override
  public void initView(View view) {
    
    //初始化带返回按钮的标题栏
    String strCenterTitle = getResources().getString(R.string.AudioManagerActivity);
    initBackTitleBar(strCenterTitle);
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

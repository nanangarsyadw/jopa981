package com.zftlive.android.library.tools;

import android.content.Context;
import android.media.AudioManager;

/**
 * 多媒体相关工具类
 * 
 * @author 曾繁添
 * @version 1.0
 *
 */
public class ToolMedia {

  /**
   * 获取指定类型音频最大值
   * 
   * @param mContext
   * @param volumeType 音频类型，比如：AudioManager.STREAM_MUSIC
   * @return
   */
  public static int gainMaxVolume(Context mContext,int volumeType){
    AudioManager mAudioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);  
    int maxVolume = mAudioManager.getStreamMaxVolume(volumeType);
    return maxVolume;
  }
  
  /**
   * 获取指定类型音频当前值,取得当前手机的音量，最大值为7，最小值为0，当为0时，手机自动将模式调整为“震动模式”
   * 
   * @param mContext
   * @param volumeType 音频类型，比如：AudioManager.STREAM_MUSIC  
   *    STREAM_ALARM 警报   
        STREAM_MUSIC 音乐回放即媒体音量   
        STREAM_NOTIFICATION 窗口顶部状态栏Notification,   
        STREAM_RING 铃声   
        STREAM_SYSTEM 系统   
        STREAM_VOICE_CALL 通话   
        STREAM_DTMF 双音多频,拨号键的声音
   * @return
   */
  public static int gainCurrentVolume(Context mContext,int volumeType){
    AudioManager mAudioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
    int currentVolume = mAudioManager.getStreamVolume(volumeType); 
    return currentVolume;
  }
  
  /**
   * 设置指定类型的音量
   * @param mContext 
   * @param volumeType 音频类型，比如：AudioManager.STREAM_MUSIC
   * @param value 音量值
   */
  public static void setVolume(Context mContext,int volumeType,int value){
    setVolume(mContext,volumeType,value,AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
  }
  
  /**
   * 设置指定类型的音量
   * @param mContext 
   * @param volumeType 音频类型，比如：AudioManager.STREAM_MUSIC
   * @param value 音量值
   */
  public static void setVolume(Context mContext,int volumeType,int value,int flags){
    AudioManager mAudioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
    mAudioManager.setStreamVolume(volumeType,value,flags);
  }
  
  /**
   * 调出系统音量降低提示框，一步一步降低
   * @param mContext
   * @param volumeType 音频类型，比如：AudioManager.STREAM_MUSIC
   */
  public static void downVolumeStep(Context mContext,int volumeType){
    AudioManager mAudioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
    /**
     * ADJUST_LOWER 降低音量   
       ADJUST_RAISE 升高音量   
       ADJUST_SAME 保持不变,这个主要用于向用户展示当前的音量
     */
    mAudioManager.adjustStreamVolume(volumeType,AudioManager.ADJUST_LOWER,AudioManager.FX_FOCUS_NAVIGATION_UP); 
  }
  
  /**
   * 调出系统音量增加提示框，一步一步调高
   * @param mContext
   * @param volumeType 音频类型，比如：AudioManager.STREAM_MUSIC
   */
  public static void upVolumeStep(Context mContext,int volumeType){
    AudioManager mAudioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
    mAudioManager.adjustStreamVolume(volumeType,AudioManager.ADJUST_RAISE,AudioManager.FX_FOCUS_NAVIGATION_UP);
  }
  
  /**
   * 控制音量
   * @param mContext
   * @param volumeType 音频类型，比如：AudioManager.STREAM_MUSIC
   * @param direction 调高（AudioManager.ADJUST_RAISE）/调低（AudioManager.ADJUST_LOWER）、保持原样（ADJUST_SAME）
   * @param flags 参数选项，FLAG_PLAY_SOUND 调整音量时播放声音   ，FLAG_SHOW_UI 调整时显示音量条,就是按音量键出现的界面，FLAG_REMOVE_SOUND_AND_VIBRATE 无振动无声音
   */
  public static void adjustStreamVolume(Context mContext,int volumeType,int direction, int flags){
    AudioManager mAudioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
    mAudioManager.adjustStreamVolume(volumeType,direction,flags);   
  }
  
}

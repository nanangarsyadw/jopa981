package com.zftlive.android.model;

import java.io.Serializable;

import android.widget.RemoteViews;

/**
 * 发送Notification通知实体
 * 
 * @author 曾繁添
 * @version 1.0
 * 
 */
public class NotificationMessage extends Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = 681166507845221063L;

	/**
	 * 状态栏提示信息图标
	 */
	private int iconResId;

	/**
	 * 状态栏提示信息图标
	 */
	private String statusBarText;

	/**
	 * 消息标题
	 */
	private String msgTitle;

	/**
	 * 消息内容
	 */
	private String msgContent;

	/**
	 * 点击消息跳转的界面
	 */
	private Class forwardComponent;

	/**
	 * 点击消息跳转界面需携带的数据key
	 */
	private String forwardKey;

	/**
	 * 点击消息跳转界面需携带的数据value
	 */
	private Serializable forwardData;

	/**
	 * 自定义消息通知布局View
	 */
	private RemoteViews mRemoteViews;

	public int getIconResId() {
		return iconResId;
	}

	public void setIconResId(int iconResId) {
		this.iconResId = iconResId;
	}

	public String getStatusBarText() {
		return statusBarText;
	}

	public void setStatusBarText(String statusBarText) {
		this.statusBarText = statusBarText;
	}

	public String getMsgTitle() {
		return msgTitle;
	}

	public void setMsgTitle(String msgTitle) {
		this.msgTitle = msgTitle;
	}

	public String getMsgContent() {
		return msgContent;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}

	public Class getForwardComponent() {
		return forwardComponent;
	}

	public void setForwardComponent(Class forwardComponent) {
		this.forwardComponent = forwardComponent;
	}

	public String getForwardKey() {
		return forwardKey;
	}

	public void setForwardKey(String forwardKey) {
		this.forwardKey = forwardKey;
	}

	public Serializable getForwardData() {
		return forwardData;
	}

	public void setForwardData(Serializable forwardData) {
		this.forwardData = forwardData;
	}

	public RemoteViews getmRemoteViews() {
		return mRemoteViews;
	}

	public void setmRemoteViews(RemoteViews mRemoteViews) {
		this.mRemoteViews = mRemoteViews;
	}
}

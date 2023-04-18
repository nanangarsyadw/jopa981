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

package com.zftlive.android.sample.db.entity;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.zftlive.android.library.base.adapter.IAdapterModel;
import com.zftlive.android.library.db.DBPersistentBean;

/**
 * 首页[菜单]数据
 * 
 * @author 曾繁添
 */
@DatabaseTable(tableName = "User")
public class User extends DBPersistentBean implements IAdapterModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1270221627651225756L;

	/**
	 * 序号
	 */
	@DatabaseField
	private String orderNo;
	
	/**
	 * 用户名
	 */
	@DatabaseField
	private String username;

	/**
	 * 电子邮箱
	 */
	@DatabaseField
	private String email;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}

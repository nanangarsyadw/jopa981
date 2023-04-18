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

package com.zftlive.android.library.db;


import com.j256.ormlite.field.DatabaseField;

import java.io.Serializable;

/**
 * 数据持久化实体Bean
 * 
 * @author 曾繁添
 * @version 1.0
 *
 */
public class DBPersistentBean implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 9186493043232411558L;

  /**
   * 主键ID
   */
  @DatabaseField(id = true)
  public String id;

  /**
   * 备注
   */
  @DatabaseField
  public String remark;

  /**
   * 版本号
   */
  @DatabaseField(defaultValue = "1", columnName = "iVersion", version = true)
  public Integer version;

  /**
   * 是否有效
   */
  @DatabaseField(defaultValue = "true")
  public Boolean valid = true;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public Integer getVersion() {
    return version;
  }

  public void setVersion(Integer version) {
    this.version = version;
  }

  public Boolean getValid() {
    return valid;
  }

  public void setValid(Boolean valid) {
    this.valid = valid;
  }
}

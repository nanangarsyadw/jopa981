package com.zftlive.android.library.base;

import com.zftlive.android.library.third.ormlite.field.DatabaseField;

import java.io.Serializable;

/**
 * 实体基类
 * 
 * @author 曾繁添
 * @version 1.0
 * 
 */
public abstract class BaseEntity implements Serializable {

  /**
	 */
  private static final long serialVersionUID = 6337104618534280060L;

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

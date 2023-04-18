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

package com.zftlive.android.library.widget.form;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;

import java.util.Map;

/**
 * 表单工具类
 *
 * @author 曾繁添
 * @version 1.0
 *
 */
public class FormHelper {

  public static final String TAG = FormHelper.class.getSimpleName();

  /**
   * 回填表单数据，根据控件的tag与DTO的key匹配回填Text
   *
   * @param formRoot 表单容器
   * @param formData 表单数据
   */
  public static void fillBackForm(ViewGroup formRoot, Map<String, Object> formData) {
    if (null == formRoot || null == formData || formData.isEmpty()) return;

    if (formRoot.getChildCount() > 0) {
      for (int i = 0; i < formRoot.getChildCount(); i++) {
        View view = formRoot.getChildAt(i);
        Log.d(TAG, "当前控件：" + view.getClass().getName() + "." + view.getTag());
        // 容器级别控件需要进行递归
        if (view instanceof LinearLayout) {
          fillBackForm((LinearLayout) view, formData);
        } else if (view instanceof RelativeLayout) {
          fillBackForm((RelativeLayout) view, formData);
        } else if (view instanceof FrameLayout) {
          fillBackForm((FrameLayout) view, formData);
        } else if (view instanceof AbsoluteLayout) {
          fillBackForm((AbsoluteLayout) view, formData);
        } else if (view instanceof android.widget.RadioGroup) {
          fillBackForm((android.widget.RadioGroup) view, formData);
        } else if (view instanceof com.zftlive.android.library.widget.form.RadioGroup) {
          fillBackForm((com.zftlive.android.library.widget.form.RadioGroup) view, formData);
        } else if (view instanceof TableLayout) {
          fillBackForm((TableLayout) view, formData);
        }

        // 非容器级别控件不用递归
        /**
         * EditText.class
         */
        else if (view instanceof EditText) {
          ((EditText) view).setText(String.valueOf(formData.get(view.getTag())));
        }

        /**
         * RadioButton.class
         */
        else if (view.getClass().getName().equals(android.widget.RadioButton.class.getName())) {
          if (((android.widget.RadioButton) view).getText().toString()
                  .equals(formData.get(view.getTag()))) {
            ((android.widget.RadioButton) view).setChecked(true);
          } else {
            ((android.widget.RadioButton) view).setChecked(false);
          }
        } else if (view.getClass().getName()
                .equals(com.zftlive.android.library.widget.form.RadioButton.class.getName())) {
          com.zftlive.android.library.widget.form.RadioButton mView =
                  (com.zftlive.android.library.widget.form.RadioButton) view;
          if (mView.getText().toString().equals(formData.get(mView.getKey()))) {
            mView.setChecked(true);
          } else {
            mView.setChecked(false);
          }
        }

        /**
         * CheckBox.class(需要拼装选中复选框)
         */
        else if (view.getClass().getName().equals(android.widget.CheckBox.class.getName())) {
          android.widget.CheckBox mView = (android.widget.CheckBox) view;
          String strFormValue = String.valueOf(formData.get(view.getTag()));
          if (strFormValue.indexOf(mView.getText().toString()) != -1) {
            mView.setChecked(true);
          } else {
            mView.setChecked(false);
          }
        } else if (view.getClass().getName()
                .equals(com.zftlive.android.library.widget.form.CheckBox.class.getName())) {
          com.zftlive.android.library.widget.form.CheckBox mView =
                  (com.zftlive.android.library.widget.form.CheckBox) view;
          String strFormValue = String.valueOf(formData.get(mView.getKey()));
          if (strFormValue.indexOf(mView.getText().toString()) != -1) {
            mView.setChecked(true);
          } else {
            mView.setChecked(false);
          }
        }

        /**
         * Spinner.class
         */
        else if (view.getClass().getName().equals(android.widget.Spinner.class.getName())) {
          android.widget.Spinner mView = (android.widget.Spinner) view;
          int mItemCount = mView.getAdapter().getCount();
          for (int j = 0; j < mItemCount; j++) {
            Object item = mView.getAdapter().getItem(j);
            if (item.equals(formData.get(view.getTag()))) {
              mView.setSelection(j);
            }
          }
        } else if (view.getClass().getName()
                .equals(com.zftlive.android.library.widget.form.SingleSpinner.class.getName())) {
          com.zftlive.android.library.widget.form.SingleSpinner mView =
                  (com.zftlive.android.library.widget.form.SingleSpinner) view;
          int mItemCount = mView.getAdapter().getCount();
          for (int j = 0; j < mItemCount; j++) {
            Option item = (Option) mView.getAdapter().getItem(j);
            if (item.getValue().equals(formData.get(mView.getKey()))) {
              mView.setSelection(j);
            }
          }

        }
      }
    }
  }

  /**
   * 获取表单控件数据
   *
   * @param root 当前表单容器
   * @param formData 当前表单数据
   * @return 表单数据（CheckBox多选选项以##拼接）
   */
  public static Map<String, Object> gainForm(ViewGroup root, Map<String, Object> formData) {
    if (root.getChildCount() > 0) {
      for (int i = 0; i < root.getChildCount(); i++) {
        View view = root.getChildAt(i);
        Log.d(TAG, "当前控件：" + view.getClass().getName() + "." + view.getTag());
        // 容器级别控件需要进行递归
        if (view instanceof LinearLayout) {
          gainForm((LinearLayout) view, formData);
        } else if (view instanceof RelativeLayout) {
          gainForm((RelativeLayout) view, formData);
        } else if (view instanceof FrameLayout) {
          gainForm((FrameLayout) view, formData);
        } else if (view instanceof AbsoluteLayout) {
          gainForm((AbsoluteLayout) view, formData);
        } else if (view instanceof android.widget.RadioGroup) {
          gainForm((android.widget.RadioGroup) view, formData);
        } else if (view instanceof com.zftlive.android.library.widget.form.RadioGroup) {
          gainForm((com.zftlive.android.library.widget.form.RadioGroup) view, formData);
        } else if (view instanceof TableLayout) {
          gainForm((TableLayout) view, formData);
        }

        // 非容器级别控件不用递归
        /**
         * EditText.class
         */
        else if (view instanceof EditText) {
          formData.put((String) view.getTag(), ((EditText) view).getText().toString());
        }

        /**
         * RadioButton.class
         */
        else if (view.getClass().getName().equals(android.widget.RadioButton.class.getName())) {
          if (((android.widget.RadioButton) view).isChecked()) {
            formData.put((String) view.getTag(), ((android.widget.RadioButton) view).getText()
                    .toString());
          }
        } else if (view.getClass().getName()
                .equals(com.zftlive.android.library.widget.form.RadioButton.class.getName())) {
          com.zftlive.android.library.widget.form.RadioButton mView =
                  (com.zftlive.android.library.widget.form.RadioButton) view;
          if (mView.isChecked()) {
            formData.put((String) mView.getKey(), mView.getValue());
          }
        }

        /**
         * CheckBox.class(需要拼装选中复选框)
         */
        else if (view.getClass().getName().equals(android.widget.CheckBox.class.getName())) {
          if (((android.widget.CheckBox) view).isChecked()) {
            if (formData.containsKey((String) view.getTag())) {
              Object value = formData.get((String) view.getTag());
              value = value + "##" + ((android.widget.CheckBox) view).getText().toString();
              formData.put((String) view.getTag(), value);
            } else {
              formData.put((String) view.getTag(), ((android.widget.CheckBox) view).getText()
                      .toString());
            }
          }

        } else if (view.getClass().getName()
                .equals(com.zftlive.android.library.widget.form.CheckBox.class.getName())) {

          com.zftlive.android.library.widget.form.CheckBox mView =
                  (com.zftlive.android.library.widget.form.CheckBox) view;
          if (mView.isChecked()) {
            if (formData.containsKey(mView.getKey())) {
              Object value = formData.get(mView.getKey());
              value = value + "##" + mView.getValue();
              formData.put(mView.getKey(), value);
            } else {
              formData.put(mView.getKey(), mView.getValue());
            }
          }
        }

        /**
         * Spinner.class
         */
        else if (view.getClass().getName().equals(android.widget.Spinner.class.getName())) {
          formData.put((String) view.getTag(), ((android.widget.Spinner) view).getSelectedItem()
                  .toString());
        } else if (view.getClass().getName()
                .equals(com.zftlive.android.library.widget.form.SingleSpinner.class.getName())) {
          com.zftlive.android.library.widget.form.SingleSpinner mView =
                  (com.zftlive.android.library.widget.form.SingleSpinner) view;
          formData.put((String) mView.getKey(), mView.getSelectedValue());
        }
      }
    }

    return formData;
  }

  /**
   * 设置表单只读
   *
   * @param formRoot
   */
  public static void readonlyForm(ViewGroup formRoot) {
    if (null == formRoot || formRoot.getChildCount() == 0) return;

    for (int i = 0; i < formRoot.getChildCount(); i++) {
      View view = formRoot.getChildAt(i);
      // 容器级别控件需要进行递归
      if (view instanceof ViewGroup) {
        readonlyForm((ViewGroup) view);
      }
      // 非容器级别控件不用递归
      view.setEnabled(false);
    }
  }

}

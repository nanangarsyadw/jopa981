package com.zftlive.android.sample.recyclerView;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zftlive.android.R;
import com.zftlive.android.library.annotation.Config;
import com.zftlive.android.library.base.adapter.IAdapterModel;
import com.zftlive.android.library.base.adapter.RecyclerViewTemplet;

/**
 * RecyclerView按钮视图模板
 *
 * @author 曾繁添
 * @version 1.0
 */
@Config(layout = R.layout.dynamic_element_item_button_link)
public class ButtonViewTemplet extends RecyclerViewTemplet {

    /**
     * 图标
     */
    private ImageView mIcon;

    /**
     * 按钮文案
     */
    private TextView mButtonText;

    public ButtonViewTemplet(Context mContext, View itemView) {
        super(mContext, itemView);
    }

    @Override
    public void initView() {
        mIcon = (ImageView)findViewById(com.zftlive.android.library.dynamicpage.R.id.iv_button_icon);
        mButtonText = (TextView) findViewById(com.zftlive.android.library.dynamicpage.R.id.tv_button_text);
    }

    @Override
    public void fillData(IAdapterModel model, int position) {

    }
}

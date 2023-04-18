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
 *
 */

package com.zftlive.android.library.dynamicpage.templet;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.zftlive.android.library.Logger;
import com.zftlive.android.library.base.adapter.IAdapterModel;
import com.zftlive.android.library.imageloader.ToolImage;
import com.zftlive.android.library.dynamicpage.R;
import com.zftlive.android.library.dynamicpage.bean.PageListElementBean;
import com.zftlive.android.library.dynamicpage.bean.PageFloorGroupElement;

/**
 * 文章资讯视图模板
 *
 * @author 曾繁添
 * @version 1.0
 */
public class ArticleViewTemplet extends AbsPageViewTemplet {

    /**
     * 上下占位间隙
     */
    private View mTopSapce, mButtomSpace;

    /**
     * 顶部作者区域
     */
    private ViewGroup mTopAuthorGroup;

    /**
     * 作者头像、文章缩略图
     */
    private ImageView mAuthorImg, mArticleThumb;

    /**
     * 作者名称、发布时间、标题、摘要、类别标签
     */
    private TextView mAuthorName, mReleaseDT, mTitle, mSummary, mTagLabel;

    /**
     * 图片显示option
     */
    private DisplayImageOptions mRoundOption,mFadeOption;

    public ArticleViewTemplet(Context mContext) {
        super(mContext);
        mRoundOption = ToolImage.getRoundOptions(R.drawable.dynamic_common_default_user_avtar);
        mFadeOption = ToolImage.getFadeOptions(R.drawable.dynamic_common_default_picture);
    }

    @Override
    public int bindLayout() {
        return R.layout.dynamic_element_item_article;
    }

    @Override
    public void initView() {
        //上下分割线
        mTopSapce = findViewById(R.id.top_space);
        mButtomSpace = findViewById(R.id.buttom_space);
        //作者区域
        mTopAuthorGroup = (ViewGroup) findViewById(R.id.ll_author_tag);
        mAuthorImg = (ImageView) findViewById(R.id.iv_author_avatar);
        mAuthorName = (TextView) findViewById(R.id.tv_author_name);
        mReleaseDT = (TextView) findViewById(R.id.tv_release_datetime);
        //文章标题、缩略图
        mArticleThumb = (ImageView) findViewById(R.id.iv_list_thumb);
        mTitle = (TextView) findViewById(R.id.tv_article_title);
        mSummary = (TextView) findViewById(R.id.tv_summary);
        mTagLabel = (TextView) findViewById(R.id.tv_label);
    }

    @Override
    public void fillData(IAdapterModel model, int postion) {
        PageListElementBean rowBean = (PageListElementBean) model;
        if (null == rowBean || rowBean.pageFloorGroupElement == null || rowBean.pageFloorGroupElement.article == null) {
            Logger.e(TAG, postion + "-->数据为空");
            return;
        }

        PageFloorGroupElement data = rowBean.pageFloorGroupElement;

        //第一个不显示顶部的线
        mTopSapce.setVisibility(0 == postion ? View.GONE :View.VISIBLE);
        if(!TextUtils.isEmpty(data.article.authorImg)){
            ToolImage.getInstance().displayImage(data.article.authorImg,mAuthorImg,mRoundOption);
        }
        mAuthorName.setText(data.article.authorName);
        mReleaseDT.setText(data.article.releaseDT);
        if(!TextUtils.isEmpty(data.article.thumb)){
            ToolImage.getInstance().displayImage(data.article.thumb,mArticleThumb,mFadeOption, mCommonLoadListener);
        }
        mTitle.setText(data.article.title);
        mSummary.setText(data.article.summary);
        mTagLabel.setText(data.article.tag);
        mTagLabel.setVisibility(TextUtils.isEmpty(data.article.tag)?View.GONE:View.VISIBLE);
    }
}

package com.zftlive.android.sample.recyclerView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zftlive.android.R;
import com.zftlive.android.library.base.adapter.IAdapterModel;
import com.zftlive.android.library.annotation.Config;
import com.zftlive.android.library.base.adapter.RecyclerViewTemplet;

/**
 * RecyclerView文章视图模板
 *
 * @author 曾繁添
 * @version 1.0
 */
@Config(layout = R.layout.dynamic_element_item_article)
public class ArticleViewTemplet extends RecyclerViewTemplet {

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

    public ArticleViewTemplet(Context mContext, View itemView) {
        super(mContext, itemView);
    }

    @Override
    public void initView() {
        //上下分割线
        mTopSapce = findViewById(com.zftlive.android.library.dynamicpage.R.id.top_space);
        mButtomSpace = findViewById(com.zftlive.android.library.dynamicpage.R.id.buttom_space);
        //作者区域
        mTopAuthorGroup = (ViewGroup) findViewById(com.zftlive.android.library.dynamicpage.R.id.ll_author_tag);
        mAuthorImg = (ImageView) findViewById(com.zftlive.android.library.dynamicpage.R.id.iv_author_avatar);
        mAuthorName = (TextView) findViewById(com.zftlive.android.library.dynamicpage.R.id.tv_author_name);
        mReleaseDT = (TextView) findViewById(com.zftlive.android.library.dynamicpage.R.id.tv_release_datetime);
        //文章标题、缩略图
        mArticleThumb = (ImageView) findViewById(com.zftlive.android.library.dynamicpage.R.id.iv_list_thumb);
        mTitle = (TextView) findViewById(com.zftlive.android.library.dynamicpage.R.id.tv_article_title);
        mSummary = (TextView) findViewById(com.zftlive.android.library.dynamicpage.R.id.tv_summary);
        mTagLabel = (TextView) findViewById(com.zftlive.android.library.dynamicpage.R.id.tv_label);
    }

    @Override
    public void fillData(IAdapterModel model, int position) {

    }
}

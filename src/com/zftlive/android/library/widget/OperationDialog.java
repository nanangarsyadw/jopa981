package com.zftlive.android.library.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zftlive.android.R;
import com.zftlive.android.library.base.BaseEntity;
import com.zftlive.android.library.base.BaseMAdapter;

import java.util.List;

/**
 * 自定义操作对话框
 * 
 * @author 曾繁添
 * @version 1.0
 * 
 */
public class OperationDialog extends Dialog {

  /**
   * 主标题、副标题
   */
  private TextView mMinTitleTV, mSubTitleTV;

  /**
   * 标题栏关闭按钮
   */
  private ImageButton mTitleCloseBtn;

  /**
   * 选项列表List
   */
  private ListView mItemList;

  /**
   * 标题栏、底部占位View
   */
  private RelativeLayout mDialogTitle, mButtomBlank;

  /**
   * 点击条目Item列表适配器
   */
  private DialogItemAdapter mItemListAdapter;

  /** 日志输出标志 **/
  protected final static String TAG = OperationDialog.class.getSimpleName();
  
  private boolean isShowTitle = true;
  private String mMainTitle = "";
  private String mSubTitle = "";
  private boolean isShowClose = true;
  private boolean isShowButtomBlank = true;
  private List<ItemBean> mItemData;
  private Activity mContext;
  private AdapterView.OnItemClickListener mItemClickListener;

  private OperationDialog(Builder mBuilder) {
    super(mBuilder.mContext,gainResId(mBuilder.mContext, "style", "OperationDialog"));
    setContentView(gainResId(mBuilder.mContext, "layout", "common_operation_dialog"));
    //初始化界面
    isShowTitle = mBuilder.isShowTitle;
    mMainTitle = mBuilder.mMainTitle;
    mSubTitle = mBuilder.mSubTitle;
    isShowClose = mBuilder.isShowClose;
    isShowButtomBlank = mBuilder.isShowButtomBlank;
    mItemData = mBuilder.mItemData;
    mContext = mBuilder.mContext;
    mItemClickListener = mBuilder.mItemClickListener;
    initView();
  }

  /**
   * 初始化控件
   */
  private void initView() {
    mDialogTitle = (RelativeLayout) findViewById(gainResId(getContext(), "id", "rl_title"));
    mDialogTitle.setVisibility(isShowTitle?View.VISIBLE:View.GONE);
    
    mButtomBlank = (RelativeLayout) findViewById(gainResId(getContext(), "id", "rl_blank"));
    mButtomBlank.setVisibility(isShowButtomBlank?View.VISIBLE:View.GONE);
    
    mMinTitleTV = (TextView) findViewById(gainResId(getContext(), "id", "tv_main_title"));
    mMinTitleTV.setText(mMainTitle);
    mMinTitleTV.setVisibility(TextUtils.isEmpty(mMainTitle)?View.GONE:View.VISIBLE);
    
    mSubTitleTV = (TextView) findViewById(gainResId(getContext(), "id", "tv_sub_title"));
    mSubTitleTV.setText(mSubTitle);
    mSubTitleTV.setVisibility(TextUtils.isEmpty(mSubTitle)?View.GONE:View.VISIBLE);
    
    mTitleCloseBtn = (ImageButton) findViewById(gainResId(getContext(), "id", "ib_close"));
    mTitleCloseBtn.setVisibility(isShowClose?View.VISIBLE:View.GONE);
    mTitleCloseBtn.setOnClickListener(new View.OnClickListener() {
      
      @Override
      public void onClick(View v) {
        if(isShowing()){
          dismiss();
        }
      }
    });
    
    mItemList = (ListView) findViewById(gainResId(getContext(), "id", "lv_item_list"));
    mItemListAdapter = new DialogItemAdapter(mContext);
    mItemList.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
    mItemList.setAdapter(mItemListAdapter);
    if(null != mItemData){
      mItemListAdapter.addItem(mItemData);
      mItemListAdapter.notifyDataSetChanged();
    }
    if(null != mItemClickListener){
      mItemList.setOnItemClickListener(mItemClickListener);
    }
  }

  /**
   * 获取资源文件id
   * 
   * @param mContext 上下文
   * @param resType 资源类型（drawable/string/layout/style/dimen/id/color/array等）
   * @param resName 资源文件名称
   * @return
   */
  private static int gainResId(Context mContext, String resType, String resName) {
    int result = -1;
    try {
      String packageName = mContext.getPackageName();
      result = mContext.getResources().getIdentifier(resName, resType, packageName);
    } catch (Exception e) {
      result = -1;
      Log.w(TAG, "获取资源文件失败，原因：" + e.getMessage());
    }
    return result;
  }
  
  /**
   * item点击列表Adapter
   * 
   */
  class DialogItemAdapter extends BaseMAdapter {

    public DialogItemAdapter(Activity mContext) {
      super(mContext);
    }
    
    @Override
    public View getView(int position, View itemView, ViewGroup parent) {
      
      // 查找控件
      ViewHolder holder = null;
      if(null == itemView){
          itemView = LayoutInflater.from(getActivity()).inflate(R.layout.common_operation_dialog_item, parent,false);
          itemView.setBackgroundResource(R.drawable.selector_common_btn_white_grally_nobold);
          holder = new ViewHolder();
          holder.tv_item_ltitle1 = (TextView) itemView.findViewById(R.id.tv_item_ltitle1);
          holder.tv_item_ltitle2 = (TextView) itemView.findViewById(R.id.tv_item_ltitle2);
          holder.tv_item_rtitle1 = (TextView) itemView.findViewById(R.id.tv_item_rtitle1);
          holder.ib_item_right_go = (ImageButton) itemView.findViewById(R.id.ib_item_right_go);
          holder.ib_item_right_ok = (ImageButton) itemView.findViewById(R.id.ib_item_right_ok);
          holder.buttom_line = itemView.findViewById(R.id.buttom_line);
          //缓存View
          itemView.setTag(holder);
      }else {
          holder = (ViewHolder) itemView.getTag();
      }
      
      //装填数据
      final ItemBean rowData = (ItemBean)getItem(position);
      
      holder.tv_item_ltitle1.setText(rowData.mLeftMainTitle);
      holder.tv_item_ltitle1.setVisibility(TextUtils.isEmpty(rowData.mLeftMainTitle)?View.GONE:View.VISIBLE);
      holder.tv_item_ltitle2.setText(rowData.mLeftSubTitle);
      holder.tv_item_ltitle2.setVisibility(TextUtils.isEmpty(rowData.mLeftSubTitle)?View.GONE:View.VISIBLE);
      holder.tv_item_rtitle1.setText(rowData.mRightMainTitle);
      holder.tv_item_rtitle1.setVisibility(TextUtils.isEmpty(rowData.mRightMainTitle)?View.GONE:View.VISIBLE);
      
      holder.ib_item_right_go.setVisibility(rowData.isShowGo?View.VISIBLE:View.GONE);
      holder.ib_item_right_ok.setVisibility(rowData.isShowOkay?View.VISIBLE:View.GONE);
      //最后一项时，隐藏line
      holder.buttom_line.setVisibility((position+1 == getCount())?View.GONE:View.VISIBLE);
      
      return itemView;
    }
    
    class ViewHolder {
      TextView tv_item_ltitle1,tv_item_ltitle2, tv_item_rtitle1 ;
      ImageButton ib_item_right_go,ib_item_right_ok;
      View buttom_line;
    }
  }

  /**
   * Item点击条目Bean
   *
   */
  public static class ItemBean extends BaseEntity{

    private static final long serialVersionUID = -669966940645778118L;
    
    /**
     * Item左-主标题
     */
    public String mLeftMainTitle;
    
    /**
     * Item左-副标题
     */
    public String mLeftSubTitle;
    
    /**
     * Item右-主标题
     */
    public String mRightMainTitle;
    
    /**
     * 是否显示右箭头图标( > )
     */
    public Boolean isShowGo = true;
    
    /**
     * 是否显示正确对勾图标
     */
    public Boolean isShowOkay = false;
    
  }
  
  /**
   * Dialog构造器
   */
  public static class Builder {
    private boolean isShowTitle = true;
    private String mMainTitle;
    private String mSubTitle;
    private boolean isShowClose = true;
    private boolean isShowButtomBlank = true;
    private Activity mContext;
    private List<ItemBean> mItemData;
    private AdapterView.OnItemClickListener mItemClickListener;

    public Builder(Activity mContext) {
      this.mContext = mContext;
    }

    public Builder showTitleClose(boolean isShow) {
      this.isShowClose = isShow;
      return this;
    }

    public Builder showTopHeader(boolean isShow) {
      this.isShowTitle = isShow;
      return this;
    }

    public Builder showButtomFooter(boolean isShow) {
      this.isShowButtomBlank = isShow;
      return this;
    }

    public Builder setMainTitle(String mMainTitle) {
      this.mMainTitle = mMainTitle;
      return this;
    }

    public Builder setSubTitle(String mSubTitle) {
      this.mSubTitle = mSubTitle;
      return this;
    }

    public Builder setItemData(List<ItemBean> mItemData){
      this.mItemData = mItemData;
      return this;
    }
    
    public Builder setItemClickListener(AdapterView.OnItemClickListener mItemClickListener){
      this.mItemClickListener = mItemClickListener;
      return this;
    } 
    
    public OperationDialog build() {
      return new OperationDialog(this);
    }
  }
}

package com.zftlive.android.library.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
  
  /**
   * 触发弹窗的Activity
   */
  private Activity mContext;

  protected OperationDialog(DialogBuilder mBuilder) {
    super(mBuilder.mContext,mBuilder.mStyleResId);
    setContentView(gainResId(mBuilder.mContext, "layout", "common_operation_dialog"));
    //初始化界面
    initView(mBuilder);
  }

  /**
   * 初始化控件
   */
  private void initView(final DialogBuilder mBuilder) {
    mContext = mBuilder.mContext;
    
    mDialogTitle = (RelativeLayout) findViewById(gainResId(getContext(), "id", "rl_title"));
    mDialogTitle.setVisibility(mBuilder.isShowTitle?View.VISIBLE:View.GONE);
    
    mButtomBlank = (RelativeLayout) findViewById(gainResId(getContext(), "id", "rl_blank"));
    mButtomBlank.setVisibility(mBuilder.isShowButtomBlank?View.VISIBLE:View.GONE);
    
    mMinTitleTV = (TextView) findViewById(gainResId(getContext(), "id", "tv_main_title"));
    mMinTitleTV.setText(mBuilder.mMainTitle);
    mMinTitleTV.setVisibility(TextUtils.isEmpty(mBuilder.mMainTitle)?View.GONE:View.VISIBLE);
    
    mSubTitleTV = (TextView) findViewById(gainResId(getContext(), "id", "tv_sub_title"));
    mSubTitleTV.setText(mBuilder.mSubTitle);
    mSubTitleTV.setVisibility(TextUtils.isEmpty(mBuilder.mSubTitle)?View.GONE:View.VISIBLE);
    
    mTitleCloseBtn = (ImageButton) findViewById(gainResId(getContext(), "id", "ib_close"));
    mTitleCloseBtn.setVisibility(mBuilder.isShowClose?View.VISIBLE:View.GONE);
    mTitleCloseBtn.setOnClickListener(new View.OnClickListener() {
      
      @Override
      public void onClick(View v) {
        if(isShowing()){
          dismiss();
        }
      }
    });
    
    //初始化Item列表
    mItemList = (ListView) findViewById(gainResId(getContext(), "id", "lv_item_list"));
    mItemListAdapter = new DialogItemAdapter(mContext);
    mItemList.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
    mItemList.setAdapter(mItemListAdapter);
    if(null != mBuilder.mItemData){
      mItemListAdapter.addItem(mBuilder.mItemData);
      mItemListAdapter.notifyDataSetChanged();
    }
    
    //设置Listview点击事件
    mItemList.setOnItemClickListener(new AdapterView.OnItemClickListener(){

      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        dismiss();
        if(null != mBuilder.mItemClickListener){
          mBuilder.mItemClickListener.onItemClick(parent, view, position, id);
        }
      }
    });
    
    //设置窗体显示的位置和宽度
    getWindow().setGravity(mBuilder.mGravity);  
    
    //是否撑满屏幕宽度
    if(mBuilder.isFillScreenWith){
      WindowManager.LayoutParams windowparams = getWindow().getAttributes();  
      windowparams.width = gainScreenDisplay().widthPixels;  
      getWindow().setAttributes(windowparams);
    }
    
    //点击其他区域是否关闭窗体
    setCanceledOnTouchOutside(mBuilder.canCancelOutside);
  }

  /**
   * 获取屏幕材质,宽度高度等信息
   * @return
   */
  private DisplayMetrics gainScreenDisplay(){
    DisplayMetrics mDisplayMetrics = new DisplayMetrics();
    WindowManager windowMgr = (WindowManager)getContext().getSystemService(Context.WINDOW_SERVICE);
    windowMgr.getDefaultDisplay().getMetrics(mDisplayMetrics);
    return mDisplayMetrics;
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
          int mLayoutResId = gainResId(mContext, "layout", "common_operation_dialog_item");
          int mBgResId = gainResId(mContext, "drawable", "selector_common_btn_white_grally_nobold");
          itemView = LayoutInflater.from(getActivity()).inflate(mLayoutResId, parent,false);
          itemView.setBackgroundResource(mBgResId);
          holder = new ViewHolder();
          holder.tv_item_lMaintitle = (TextView) itemView.findViewById(gainResId(mContext, "id", "tv_item_ltitle1"));
          holder.tv_item_lSubtitle = (TextView) itemView.findViewById(gainResId(mContext, "id", "tv_item_ltitle2"));
          holder.tv_item_Centertitle = (TextView) itemView.findViewById(gainResId(mContext, "id", "tv_item_center_title"));
          holder.tv_item_rMaintitle = (TextView) itemView.findViewById(gainResId(mContext, "id", "tv_item_rtitle1"));
          holder.ib_item_right_go = (ImageButton) itemView.findViewById(gainResId(mContext, "id", "ib_item_right_go"));
          holder.ib_item_right_ok = (ImageButton) itemView.findViewById(gainResId(mContext, "id", "ib_item_right_ok"));
          holder.buttom_line = itemView.findViewById(gainResId(mContext, "id", "buttom_line"));
          //缓存View
          itemView.setTag(holder);
      }else {
          holder = (ViewHolder) itemView.getTag();
      }
      
      //装填数据
      final ItemBean rowData = (ItemBean)getItem(position);
      
      holder.tv_item_lMaintitle.setText(rowData.leftMainTitle);
      holder.tv_item_lMaintitle.setVisibility(TextUtils.isEmpty(rowData.leftMainTitle)?View.GONE:View.VISIBLE);
      holder.tv_item_lSubtitle.setText(rowData.leftSubTitle);
      holder.tv_item_lSubtitle.setVisibility(TextUtils.isEmpty(rowData.leftSubTitle)?View.GONE:View.VISIBLE);
      holder.tv_item_Centertitle.setText(rowData.centerTitle);
      holder.tv_item_Centertitle.setVisibility(TextUtils.isEmpty(rowData.centerTitle)?View.GONE:View.VISIBLE);
      holder.tv_item_rMaintitle.setText(rowData.rightMainTitle);
      holder.tv_item_rMaintitle.setVisibility(TextUtils.isEmpty(rowData.rightMainTitle)?View.GONE:View.VISIBLE);
      
      holder.ib_item_right_go.setVisibility(rowData.isShowGo?View.VISIBLE:View.GONE);
      holder.ib_item_right_ok.setVisibility(rowData.isShowOkay?View.VISIBLE:View.GONE);
      //最后一项时，隐藏line
      holder.buttom_line.setVisibility((position+1 == getCount())?View.GONE:View.VISIBLE);
      
      //按钮类的控件会抢焦点，导致选择器失效
      holder.ib_item_right_go.setFocusable(false);
      holder.ib_item_right_ok.setFocusable(false);
      
      return itemView;
    }
    
    class ViewHolder {
      TextView tv_item_lMaintitle,tv_item_lSubtitle,tv_item_Centertitle, tv_item_rMaintitle ;
      ImageButton ib_item_right_go,ib_item_right_ok;
      View buttom_line;
    }
  }

  /**
   * Item点击事件
   *
   */
  public interface ItemClickListener extends AdapterView.OnItemClickListener{
    
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
    public String leftMainTitle;
    
    /**
     * Item左-副标题
     */
    public String leftSubTitle;
    
    /**
     * Item中间-标题（只有中间一个标题时使用）
     */
    public String centerTitle;
    
    /**
     * Item右-主标题
     */
    public String rightMainTitle;
    
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
  public static class DialogBuilder {
    private boolean isShowTitle = true;
    private CharSequence mMainTitle;
    private CharSequence mSubTitle;
    private boolean isShowClose = true;
    private boolean isShowButtomBlank = true;
    private Activity mContext;
    private List<ItemBean> mItemData;
    private ItemClickListener mItemClickListener;
    private int mStyleResId = 0;
    private int mGravity = Gravity.BOTTOM;
    private boolean canCancelOutside = true;
    private boolean isFillScreenWith = true;

    public DialogBuilder(Activity mContext) {
      this.mContext = mContext;
      mStyleResId = gainResId(mContext, "style", "OperationDialog");
    }
    
    /**
     * 是否显示标题栏关闭按钮
     * @param isShow
     * @return
     */
    public DialogBuilder showTitleClose(boolean isShow) {
      this.isShowClose = isShow;
      return this;
    }

    /**
     * 是否显示窗体标题栏Header
     * @param isShow
     * @return
     */
    public DialogBuilder showTopHeader(boolean isShow) {
      this.isShowTitle = isShow;
      return this;
    }

    /**
     * 是否显示底部Footer
     * @param isShow
     * @return
     */
    public DialogBuilder showButtomFooter(boolean isShow) {
      this.isShowButtomBlank = isShow;
      return this;
    }

    /**
     * 设置窗体主标题，支持HTML格式文本
     * @param mMainTitle
     * @return
     */
    public DialogBuilder setMainTitle(CharSequence mMainTitle) {
      this.mMainTitle = mMainTitle;
      return this;
    }

    /**
     * 设置窗体副标题，支持HTML格式文本
     * @param mSubTitle
     * @return
     */
    public DialogBuilder setSubTitle(CharSequence mSubTitle) {
      this.mSubTitle = mSubTitle;
      return this;
    }

    /**
     * 设置Item列表数据
     * @param mItemData
     * @return
     */
    public DialogBuilder setItemData(List<ItemBean> mItemData){
      this.mItemData = mItemData;
      return this;
    }
    
    /**
     * 设置Item点击事件
     * @param mItemClickListener
     * @return
     */
    public DialogBuilder setItemClickListener(ItemClickListener mItemClickListener){
      this.mItemClickListener = mItemClickListener;
      return this;
    } 
    
    /**
     * 设置窗体主题
     * @param mStyleId style样式名称id
     * @return
     */
    public DialogBuilder setTheme(int mStyleId){
      this.mStyleResId = mStyleId;
      return this;
    }
    
    /**
     * 设置窗体所在位置 ，默认Gravity.BOTTOM
     * @param mGravity 
     * @return
     */
    public DialogBuilder setGravity(int mGravity){
      this.mGravity = mGravity;
      return this;
    } 
    
    /**
     * 点击窗体其他地方是否可以关闭
     * @param cancelable
     * @return
     */
    public DialogBuilder setCanceledOnTouchOutside(boolean cancelable){
      this.canCancelOutside = cancelable;
      return this;
    } 
    
    /**
     * 设置窗体宽度是否撑满屏幕宽度
     * @param cancelable
     * @return
     */
    public DialogBuilder setFillScreenWith(boolean isFillScreenWith){
      this.isFillScreenWith = isFillScreenWith;
      return this;
    }
    
    /**
     * 创建一个Dialog
     * @return
     */
    public OperationDialog build() {
      return new OperationDialog(this);
    }
  }
}

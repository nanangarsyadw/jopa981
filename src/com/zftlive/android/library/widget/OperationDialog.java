package com.zftlive.android.library.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zftlive.android.library.base.BaseMAdapter;

import java.util.ArrayList;
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
   * 方向-水平
   */
  public static final int HORIZONTAL = LinearLayout.HORIZONTAL;

  /**
   * 方向-垂直
   */
  public static final int VERTICAL = LinearLayout.VERTICAL;

  /**
   * 主标题、副标题,窗体Body消息标题、消息文本
   */
  private TextView mMinTitleTV, mSubTitleTV, mBodyTitleTV, mBodyMsgTV;

  /**
   * 标题栏关闭按钮
   */
  private ImageButton mTitleCloseBtn;

  /**
   * 标题栏、底部占位View
   */
  private ViewGroup mDialogTitle, mCustomViewContainer, mHOpreationBtnContainer,
      mVOpreationBtnContainer, mButtomBlank;

  /**
   * 选项列表List
   */
  private ListView mItemList;

  /**
   * 点击条目Item列表适配器
   */
  private DialogItemAdapter mItemListAdapter;

  /**
   * 关闭事件回调
   */
  private CancelListener mCancelListener;

  /** 日志输出标志 **/
  protected final static String TAG = OperationDialog.class.getSimpleName();

  /**
   * 触发弹窗的Activity
   */
  private Activity mContext;

  protected OperationDialog(DialogBuilder mBuilder) {
    super(mBuilder.mContext, mBuilder.mStyleResId);
    this.mCancelListener = mBuilder.mCancelListener;
    setContentView(gainResId(mBuilder.mContext, "layout", "common_operation_dialog"));
    // 初始化界面
    initView(mBuilder);
  }

  /**
   * 初始化控件
   */
  private void initView(final DialogBuilder mBuilder) {
    mContext = mBuilder.mContext;

    // Header-标题栏-->标题栏容器
    mDialogTitle = (RelativeLayout) findViewById(gainResId(getContext(), "id", "rl_title"));
    mDialogTitle.setVisibility(mBuilder.isShowTitle ? View.VISIBLE : View.GONE);
    // Header-标题栏-->主标题
    mMinTitleTV = (TextView) findViewById(gainResId(getContext(), "id", "tv_main_title"));
    mMinTitleTV.setText(mBuilder.mMainTitle);
    mMinTitleTV.setVisibility(TextUtils.isEmpty(mBuilder.mMainTitle) ? View.GONE : View.VISIBLE);
    // Header-标题栏-->副标题
    mSubTitleTV = (TextView) findViewById(gainResId(getContext(), "id", "tv_sub_title"));
    mSubTitleTV.setText(mBuilder.mSubTitle);
    mSubTitleTV.setVisibility(TextUtils.isEmpty(mBuilder.mSubTitle) ? View.GONE : View.VISIBLE);
    // Header-标题栏-->关闭按钮
    mTitleCloseBtn = (ImageButton) findViewById(gainResId(getContext(), "id", "ib_close"));
    mTitleCloseBtn.setVisibility(mBuilder.isShowClose ? View.VISIBLE : View.GONE);
    mTitleCloseBtn.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View v) {
        if (isShowing()) {
          dismiss();
        }
      }
    });
    // Body-->自定义布局
    mCustomViewContainer =
        (RelativeLayout) findViewById(gainResId(getContext(), "id", "rl_custom_view"));
    if (null != mBuilder.mCustomView) {
      mCustomViewContainer.addView(mBuilder.mCustomView);
    }

    // Body-List--> 初始化Item列表
    mItemList = (ListView) findViewById(gainResId(getContext(), "id", "lv_item_list"));
    mItemListAdapter = new DialogItemAdapter(mContext, mBuilder.isShowItemLastLine);
    mItemList.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
    mItemList.setAdapter(mItemListAdapter);
    if (null != mBuilder.mItemData) {
      mItemListAdapter.addItem(mBuilder.mItemData);
      mItemListAdapter.notifyDataSetChanged();
      mItemList.setVisibility(View.VISIBLE);
    }
    // 设置Listview点击事件
    mItemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        dismiss();
        if (null != mBuilder.mItemClickListener) {
          mBuilder.mItemClickListener.onItemClick(parent, view, position, id);
        }
      }
    });

    // Body-->消息标题、消息文本
    mBodyTitleTV = (TextView) findViewById(gainResId(getContext(), "id", "tv_body_title"));
    mBodyTitleTV.setText(mBuilder.mBodyTitle);
    mBodyTitleTV.setVisibility(TextUtils.isEmpty(mBuilder.mBodyTitle) ? View.GONE : View.VISIBLE);
    mBodyMsgTV = (TextView) findViewById(gainResId(getContext(), "id", "tv_body_msg"));
    mBodyMsgTV.setText(mBuilder.mBodyMsg);
    mBodyMsgTV.setVisibility(TextUtils.isEmpty(mBuilder.mBodyMsg) ? View.GONE : View.VISIBLE);

    // Body-->操作按钮集合
    if (mBuilder.mOperationBtnList.size() > 0) {
      mHOpreationBtnContainer =
          (LinearLayout) findViewById(gainResId(mContext, "id", "ll_h_opreation_btns"));
      mVOpreationBtnContainer =
          (LinearLayout) findViewById(gainResId(mContext, "id", "ll_v_opreation_btns"));
      switch (mBuilder.mOperateBtnDirection) {
        case HORIZONTAL:
          mHOpreationBtnContainer.setVisibility(View.VISIBLE);
          mVOpreationBtnContainer.setVisibility(View.GONE);
          buildOpreationButton(mBuilder, mHOpreationBtnContainer);
          break;
        case VERTICAL:
          mVOpreationBtnContainer.setVisibility(View.VISIBLE);
          mHOpreationBtnContainer.setVisibility(View.GONE);
          buildOpreationButton(mBuilder, mVOpreationBtnContainer);
          break;
        default:
          break;
      }
    }

    // Footer-->占位View
    mButtomBlank = (RelativeLayout) findViewById(gainResId(getContext(), "id", "rl_blank"));
    mButtomBlank.setVisibility(mBuilder.isShowButtomBlank ? View.VISIBLE : View.GONE);

    // 设置窗体显示的位置和宽度
    getWindow().setGravity(mBuilder.mGravity);

    // 是否撑满屏幕宽度
    if (mBuilder.isFillScreenWith) {
      WindowManager.LayoutParams windowparams = getWindow().getAttributes();
      windowparams.width = gainScreenDisplay().widthPixels;
      getWindow().setAttributes(windowparams);
    }

    // 点击其他区域是否关闭窗体
    setCanceledOnTouchOutside(mBuilder.canCancelOutside);
  }

  /**
   * 复写关闭对话框方法，对外公开回调取消事件
   */
  @Override
  public void cancel() {
    if (null != mCancelListener) {
      mCancelListener.onCancel();
    }
    super.cancel();
  }

  /**
   * 构建按钮
   * 
   * @param mBtnBean
   * @return
   */
  private void buildOpreationButton(final DialogBuilder mBuilder, ViewGroup mContainner) {
    // 数据合法性校验
    if (null == mBuilder || null == mContainner || null == mBuilder.mOperationBtnList
        || mBuilder.mOperationBtnList.size() <= 0) return;
    int size = mBuilder.mOperationBtnList.size();

    int mHeight = dipToPx(mContext, 50);
    LinearLayout.LayoutParams mBtnLp =
        new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, mHeight);
    if (HORIZONTAL == mBuilder.mOperateBtnDirection) {
      mBtnLp.width = 0;
      mBtnLp.weight = 10 * 1.0f / size;
    }
    // 分割线布局参数,没有权重
    int mSplitLineH = dipToPx(mContext, 0.33f);
    LinearLayout.LayoutParams mSplitLp =
        new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, mSplitLineH);
    if (HORIZONTAL == mBuilder.mOperateBtnDirection) {
      mSplitLp.width = mSplitLineH;
      mSplitLp.height = mHeight;
    } else {
      mSplitLp.height = mSplitLineH;
    }

    // 动态追加按钮
    for (int i = 0; i < size; i++) {
      // 增加分割线
      View split = new View(mContext);
      split.setBackgroundColor(Color.parseColor("#DDDDDD"));
      split.setLayoutParams(mSplitLp);
      mContainner.addView(split);

      ButtonBean mBtnBean = mBuilder.mOperationBtnList.get(i);
      Button btn = new Button(mContext);
      btn.setId(mBtnBean.id);
      btn.setText(mBtnBean.label);
      if (!TextUtils.isEmpty(mBtnBean.textColor)) {
        btn.setTextColor(Color.parseColor(mBtnBean.textColor));
      }
      btn.setTextSize(TypedValue.COMPLEX_UNIT_SP, mBtnBean.textSize);
      btn.setBackgroundResource(gainResId(getContext(), "drawable",
          "selector_common_btn_white_grally_nobold"));
      btn.setLayoutParams(mBtnLp);
      btn.setOnClickListener(new View.OnClickListener() {

        @Override
        public void onClick(View v) {
          dismiss();
          if (null != mBuilder.mOperateClickListener) {
            mBuilder.mOperateClickListener.onClick(v);
          }
        }
      });
      btn.setFocusable(false);
      mContainner.addView(btn);
    }
  }

  /**
   * 获取屏幕材质,宽度高度等信息
   * 
   * @return
   */
  private DisplayMetrics gainScreenDisplay() {
    DisplayMetrics mDisplayMetrics = new DisplayMetrics();
    WindowManager windowMgr = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
    windowMgr.getDefaultDisplay().getMetrics(mDisplayMetrics);
    return mDisplayMetrics;
  }

  /**
   * 根据手机的分辨率从 dip 的单位 转成为 px(像素)
   * 
   * @param context 上下文
   * @param dpValue 需要转化的dip值
   * @return int 转化后的px值
   */
  private int dipToPx(Context context, float dipValue) {
    final float scale = context.getResources().getDisplayMetrics().density;
    return (int) (dipValue * scale + 0.5f);
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

    boolean isShowLastLine = true;

    public DialogItemAdapter(Activity mContext) {
      this(mContext, true);
    }

    public DialogItemAdapter(Activity mContext, Boolean hasLastLine) {
      super(mContext);
      isShowLastLine = hasLastLine;
    }

    @Override
    public View getView(int position, View itemView, ViewGroup parent) {

      // 查找控件
      ViewHolder holder = null;
      if (null == itemView) {
        int mLayoutResId = gainResId(mContext, "layout", "common_operation_dialog_item");
        itemView = LayoutInflater.from(getActivity()).inflate(mLayoutResId, parent, false);
        holder = new ViewHolder();
        holder.tv_item_lMaintitle =
            (TextView) itemView.findViewById(gainResId(mContext, "id", "tv_item_ltitle1"));
        holder.tv_item_lSubtitle =
            (TextView) itemView.findViewById(gainResId(mContext, "id", "tv_item_ltitle2"));
        holder.tv_item_Centertitle =
            (TextView) itemView.findViewById(gainResId(mContext, "id", "tv_item_center_title"));
        holder.tv_item_rMaintitle =
            (TextView) itemView.findViewById(gainResId(mContext, "id", "tv_item_rtitle1"));
        holder.ib_item_right_go =
            (ImageButton) itemView.findViewById(gainResId(mContext, "id", "ib_item_right_go"));
        holder.ib_item_right_ok =
            (ImageButton) itemView.findViewById(gainResId(mContext, "id", "ib_item_right_ok"));
        holder.buttom_line = itemView.findViewById(gainResId(mContext, "id", "buttom_line"));
        // 缓存View
        itemView.setTag(holder);
      } else {
        holder = (ViewHolder) itemView.getTag();
      }

      // 装填数据
      final ItemBean rowData = (ItemBean) getItem(position);
      // Body-List-Item-->左主标题文本、是否可见、字体颜色
      holder.tv_item_lMaintitle.setText(rowData.leftMainTitle);
      holder.tv_item_lMaintitle.setVisibility(TextUtils.isEmpty(rowData.leftMainTitle)
          ? View.GONE
          : View.VISIBLE);
      String maintitleColor = rowData.leftMainTitleTextColor;
      if (TextUtils.isEmpty(rowData.leftMainTitleTextColor)) {
        maintitleColor = "#333333";
      }
      holder.tv_item_lMaintitle.setTextColor(Color.parseColor(maintitleColor));

      // Body-List-Item-->左副标题文本、是否可见、字体颜色、背景
      holder.tv_item_lSubtitle.setText(rowData.leftSubTitle);
      holder.tv_item_lSubtitle.setVisibility(TextUtils.isEmpty(rowData.leftSubTitle)
          ? View.GONE
          : View.VISIBLE);
      String subtitleColor = rowData.leftSubTitleTextColor;
      if (TextUtils.isEmpty(rowData.leftSubTitleTextColor)) {
        subtitleColor = "#999999";
      }
      holder.tv_item_lSubtitle.setTextColor(Color.parseColor(subtitleColor));
      if (rowData.leftSubTitleBgResId != 0 && rowData.leftSubTitleBgResId != -1) {
        holder.tv_item_lSubtitle.setBackgroundResource(rowData.leftSubTitleBgResId);
        holder.tv_item_lSubtitle.setVisibility(View.VISIBLE);
      } else {
        holder.tv_item_lSubtitle.setBackgroundResource(0);
        holder.tv_item_lSubtitle.setVisibility(View.GONE);
      }

      // Body-List-Item-->居中文本、是否可见、字体颜色
      holder.tv_item_Centertitle.setText(rowData.centerTitle);
      holder.tv_item_Centertitle.setVisibility(TextUtils.isEmpty(rowData.centerTitle)
          ? View.GONE
          : View.VISIBLE);
      String centertitleColor = rowData.centerTitleTextColor;
      if (TextUtils.isEmpty(rowData.centerTitleTextColor)) {
        centertitleColor = "#999999";
      }
      holder.tv_item_Centertitle.setTextColor(Color.parseColor(centertitleColor));

      // Body-List-Item-->右主标题文本、是否可见、字体颜色
      holder.tv_item_rMaintitle.setText(rowData.rightMainTitle);
      holder.tv_item_rMaintitle.setVisibility(TextUtils.isEmpty(rowData.rightMainTitle)
          ? View.GONE
          : View.VISIBLE);
      String rMaintitleColor = rowData.rightMainTitleTextColor;
      if (TextUtils.isEmpty(rowData.rightMainTitleTextColor)) {
        rMaintitleColor = "#359df5";
      }
      holder.tv_item_rMaintitle.setTextColor(Color.parseColor(rMaintitleColor));

      // Body-List-Item-->右箭头指示图标、选中对勾是否可见
      holder.ib_item_right_go.setVisibility(rowData.isShowGo ? View.VISIBLE : View.GONE);
      holder.ib_item_right_ok.setVisibility(rowData.isShowOkay ? View.VISIBLE : View.GONE);
      // 最后一项时，隐藏line
      holder.buttom_line.setVisibility(isShowLastLine ? View.VISIBLE : View.GONE);

      // 按钮类的控件会抢焦点，导致选择器失效
      holder.ib_item_right_go.setFocusable(false);
      holder.ib_item_right_ok.setFocusable(false);

      return itemView;
    }

    class ViewHolder {
      TextView tv_item_lMaintitle, tv_item_lSubtitle, tv_item_Centertitle, tv_item_rMaintitle;
      ImageButton ib_item_right_go, ib_item_right_ok;
      View buttom_line;
    }
  }

  /**
   * Item点击事件
   * 
   */
  public interface ItemClickListener extends AdapterView.OnItemClickListener {

  }

  /**
   * 操作按钮点击事件
   * 
   */
  public interface OperationClickListener extends View.OnClickListener {

  }

  /**
   * Dialog关闭取消事件
   */
  public interface CancelListener {
    void onCancel();
  }

  /**
   * 按钮属性Bean
   * 
   */
  public static class ButtonBean {

    public ButtonBean() {
      this(android.R.id.button1, "我知道了");
    }

    public ButtonBean(int id, String label) {
      this(id, label, "#666666");
    }

    public ButtonBean(int id, String label, String textColor) {
      this(id, label, 18, textColor);
    }

    public ButtonBean(int id, String label, int textSize, String textColor) {
      super();
      this.id = id;
      this.label = label;
      this.textSize = textSize;
      this.textColor = textColor;
    }

    /**
     * 定义按钮的id
     */
    public int id = android.R.id.button1;

    /**
     * 按钮显示文本
     */
    public String label;

    /**
     * 按钮文本字体大小，默认18sp
     */
    public int textSize = 18;

    /**
     * 按钮文本字体颜色，默认#666666
     */
    public String textColor = "#666666";

  }

  /**
   * Item点击条目Bean
   * 
   */
  public static class ItemBean {

    public ItemBean() {

    }

    public ItemBean(String centerTitle) {
      this.centerTitle = centerTitle;
    }

    public ItemBean(String centerTitle, String centerTitleTextColor) {
      this.centerTitle = centerTitle;
      this.centerTitleTextColor = centerTitleTextColor;
    }

    public ItemBean(String centerTitle, String centerTitleTextColor, Boolean isShowGo) {
      this.centerTitle = centerTitle;
      this.centerTitleTextColor = centerTitleTextColor;
      this.isShowGo = isShowGo;
    }

    public ItemBean(String leftMainTitle, String leftSubTitle, String rightMainTitle,
        Boolean isShowGo) {
      this.leftMainTitle = leftMainTitle;
      this.leftSubTitle = leftSubTitle;
      this.rightMainTitle = rightMainTitle;
      this.isShowGo = isShowGo;
    }

    public ItemBean(String leftMainTitle, String leftSubTitle, String rightMainTitle,
        Boolean isShowGo, Boolean isShowOkay) {
      this.leftMainTitle = leftMainTitle;
      this.leftSubTitle = leftSubTitle;
      this.rightMainTitle = rightMainTitle;
      this.isShowGo = isShowGo;
      this.isShowOkay = isShowOkay;
    }

    public ItemBean(String leftMainTitle, String leftSubTitle, int leftSubTitleBgResId,
        String rightMainTitle, Boolean isShowGo) {
      this.leftMainTitle = leftMainTitle;
      this.leftSubTitle = leftSubTitle;
      this.leftSubTitleBgResId = leftSubTitleBgResId;
      this.rightMainTitle = rightMainTitle;
      this.isShowGo = isShowGo;
    }

    public ItemBean(String leftMainTitle, String leftMainTitleTextColor, String leftSubTitle,
        int leftSubTitleBgResId, String leftSubTitleTextColor, String centerTitle,
        String centerTitleTextColor, String rightMainTitle, String rightMainTitleTextColor,
        Boolean isShowGo, Boolean isShowOkay) {
      this.leftMainTitle = leftMainTitle;
      this.leftMainTitleTextColor = leftMainTitleTextColor;
      this.leftSubTitle = leftSubTitle;
      this.leftSubTitleBgResId = leftSubTitleBgResId;
      this.leftSubTitleTextColor = leftSubTitleTextColor;
      this.centerTitle = centerTitle;
      this.centerTitleTextColor = centerTitleTextColor;
      this.rightMainTitle = rightMainTitle;
      this.rightMainTitleTextColor = rightMainTitleTextColor;
      this.isShowGo = isShowGo;
      this.isShowOkay = isShowOkay;
    }

    /**
     * Item左-主标题
     */
    public String leftMainTitle;

    /**
     * Item左-主标题字体颜色，默认#333333
     */
    public String leftMainTitleTextColor;

    /**
     * Item左-副标题
     */
    public String leftSubTitle;

    /**
     * Item左-副标题背景资源名称
     */
    public int leftSubTitleBgResId = 0;

    /**
     * Item左-副标题字体颜色，默认#999999
     */
    public String leftSubTitleTextColor;

    /**
     * Item中间-标题（只有中间一个标题时使用）
     */
    public String centerTitle;

    /**
     * Item中间-标题（只有中间一个标题时使用）字体颜色，默认#999999
     */
    public String centerTitleTextColor;

    /**
     * Item右-主标题
     */
    public String rightMainTitle;

    /**
     * Item右-主标题字体颜色，默认#359df5
     */
    public String rightMainTitleTextColor;

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
    private Activity mContext;
    private CancelListener mCancelListener;
    private int mStyleResId = 0;
    private int mGravity = Gravity.BOTTOM;
    private boolean canCancelOutside = true;
    private boolean isFillScreenWith = true;
    private boolean isShowTitle = true;
    private CharSequence mMainTitle;
    private CharSequence mSubTitle;
    private boolean isShowClose = true;
    private List<ItemBean> mItemData = new ArrayList<ItemBean>();
    private ItemClickListener mItemClickListener;
    private Boolean isShowItemLastLine = true;
    private CharSequence mBodyTitle;
    private CharSequence mBodyMsg;
    private int mOperateBtnDirection = HORIZONTAL;
    private ArrayList<ButtonBean> mOperationBtnList = new ArrayList<ButtonBean>();
    private OperationClickListener mOperateClickListener;
    private View mCustomView;
    private boolean isShowButtomBlank = true;

    public DialogBuilder(Activity mContext) {
      this.mContext = mContext;
      mStyleResId = gainResId(mContext, "style", "DialogTopButtomAnimation");
    }

    /**
     * 是否带动画弹出Dialog
     * 
     * @param hasAnim
     * @return
     */
    public DialogBuilder hasAnimation(boolean hasAnim) {
      if (!hasAnim) {
        mStyleResId = gainResId(mContext, "style", "DialogNoAnimation");
      }
      return this;
    }

    /**
     * 是否显示标题栏关闭按钮
     * 
     * @param isShow
     * @return
     */
    public DialogBuilder showTitleClose(boolean isShow) {
      this.isShowClose = isShow;
      return this;
    }

    /**
     * 是否显示窗体标题栏Header
     * 
     * @param isShow
     * @return
     */
    public DialogBuilder showTopHeader(boolean isShow) {
      this.isShowTitle = isShow;
      return this;
    }

    /**
     * 是否显示底部Footer
     * 
     * @param isShow
     * @return
     */
    public DialogBuilder showButtomFooter(boolean isShow) {
      this.isShowButtomBlank = isShow;
      return this;
    }

    /**
     * 设置窗体主标题，支持HTML格式文本
     * 
     * @param mMainTitle
     * @return
     */
    public DialogBuilder setMainTitle(CharSequence mMainTitle) {
      this.mMainTitle = mMainTitle;
      return this;
    }

    /**
     * 设置窗体副标题，支持HTML格式文本
     * 
     * @param mSubTitle
     * @return
     */
    public DialogBuilder setSubTitle(CharSequence mSubTitle) {
      this.mSubTitle = mSubTitle;
      return this;
    }

    /**
     * 设置窗体Body区域的标题，支持HTML格式文本
     * 
     * @param mBodyTitle
     * @return
     */
    public DialogBuilder setBodyTitle(CharSequence mBodyTitle) {
      this.mBodyTitle = mBodyTitle;
      return this;
    }

    /**
     * 设置窗体Body区域的消息文本，支持HTML格式文本
     * 
     * @param mBodyTitleTV
     * @return
     */
    public DialogBuilder setBodyMsg(CharSequence mBodyMsg) {
      this.mBodyMsg = mBodyMsg;
      return this;
    }

    /**
     * 设置操作按钮的方向，水平/垂直,默认水平方向OperationDialog.HORIZONTAL
     * 
     * @param mBodyTitleTV
     * @return
     */
    public DialogBuilder setOperationBtnDirection(int mDirection) {
      this.mOperateBtnDirection = mDirection;
      return this;
    }

    /**
     * 添加操作按钮
     * 
     * @param btn 按钮属性Bean
     * @return
     */
    public DialogBuilder addOperationBtn(ButtonBean btn) {
      mOperationBtnList.add(btn);
      return this;
    }

    /**
     * 在指定的位置索引添加操作按钮
     * 
     * @param btn 按钮属性Bean
     * @return
     */
    public DialogBuilder addOperationBtn(int index, ButtonBean btn) {
      mOperationBtnList.add(index, btn);
      return this;
    }

    /**
     * 添加操作按钮集合
     * 
     * @param btn 按钮属性Bean集合
     * @return
     */
    public DialogBuilder addOperationBtn(List<ButtonBean> btn) {
      mOperationBtnList.addAll(btn);
      return this;
    }

    /**
     * 添加item行数据
     * 
     * @param btn 按钮属性Bean
     * @return
     */
    public DialogBuilder addItemData(ItemBean btn) {
      mItemData.add(btn);
      return this;
    }

    /**
     * 在指定的位置索引添加item行数据
     * 
     * @param btn 按钮属性Bean
     * @return
     */
    public DialogBuilder addItemData(int index, ItemBean btn) {
      mItemData.add(index, btn);
      return this;
    }

    /**
     * 添加Item列表数据
     * 
     * @param mItemData
     * @return
     */
    public DialogBuilder addItemData(List<ItemBean> mItemData) {
      if (null != mItemData) {
        this.mItemData.addAll(mItemData);
      }
      return this;
    }

    /**
     * 设置Item列表数据，推荐使用addItemData方法
     * 
     * @param mItemData
     * @return
     */
    @Deprecated
    public DialogBuilder setItemData(List<ItemBean> mItemData) {
      if (null != mItemData) {
        this.mItemData.addAll(mItemData);
      }
      return this;
    }

    /**
     * 设置ItemList最后一根分隔线是否显示
     * 
     * @param hasLastLine
     * @return
     */
    public DialogBuilder showItemListLastLine(boolean hasLastLine) {
      this.isShowItemLastLine = hasLastLine;
      return this;
    }

    /**
     * 设置Item点击事件
     * 
     * @param mItemClickListener
     * @return
     */
    public DialogBuilder setItemClickListener(ItemClickListener mItemClickListener) {
      this.mItemClickListener = mItemClickListener;
      return this;
    }

    /**
     * 设置Dialog关闭事件
     * 
     * @param mCancelListener
     * @return
     */
    public DialogBuilder setCancelListener(CancelListener mCancelListener) {
      this.mCancelListener = mCancelListener;
      return this;
    }

    /**
     * 设置Dialog操作按钮点击事件
     * 
     * @param mOperateClickListener
     * @return
     */
    public DialogBuilder setOperationClickListener(OperationClickListener mOperateClickListener) {
      this.mOperateClickListener = mOperateClickListener;
      return this;
    }

    /**
     * 设置窗体主题
     * 
     * @param mStyleId style样式名称id
     * @return
     */
    public DialogBuilder setTheme(int mStyleId) {
      this.mStyleResId = mStyleId;
      return this;
    }

    /**
     * 设置窗体所在位置 ，默认Gravity.BOTTOM
     * 
     * @param mGravity
     * @return
     */
    public DialogBuilder setGravity(int mGravity) {
      this.mGravity = mGravity;
      return this;
    }

    /**
     * 点击窗体其他地方是否可以关闭
     * 
     * @param cancelable
     * @return
     */
    public DialogBuilder setCanceledOnTouchOutside(boolean cancelable) {
      this.canCancelOutside = cancelable;
      return this;
    }

    /**
     * 设置窗体宽度是否撑满屏幕宽度
     * 
     * @param cancelable
     * @return
     */
    public DialogBuilder setFillScreenWith(boolean isFillScreenWith) {
      this.isFillScreenWith = isFillScreenWith;
      return this;
    }

    /**
     * 设置窗体自定义布局
     * 
     * @param cancelable
     * @return
     */
    public DialogBuilder setCustomView(View mCustomView) {
      this.mCustomView = mCustomView;
      return this;
    }

    /**
     * 创建一个Dialog
     * 
     * @return
     */
    public OperationDialog build() {
      return new OperationDialog(this);
    }
  }
}

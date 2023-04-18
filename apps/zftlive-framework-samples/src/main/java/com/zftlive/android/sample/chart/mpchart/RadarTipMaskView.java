package com.zftlive.android.sample.chart.mpchart;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.zftlive.android.R;

import java.text.DecimalFormat;

/**
 * Created by Java on 2017/7/1.
 */
public class RadarTipMaskView extends MarkerView {

    TextView mTipsText;

    DecimalFormat format = new DecimalFormat("##0");

    /**
     * Constructor. Sets up the MarkerView with a custom layout resource.
     *
     * @param context
     * @param layoutResource the layout resource to use for the MarkerView
     */
    public RadarTipMaskView(Context context, int layoutResource) {
        super(context, layoutResource);
        mTipsText = (TextView) findViewById(R.id.tv_tips_text);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        mTipsText.setText("<" + e.getX() + "," + e.getY() + ">");
        mTipsText.setText(format.format(e.getY()));
        //测量布局之前设置文本
        super.refreshContent(e, highlight);
    }
}

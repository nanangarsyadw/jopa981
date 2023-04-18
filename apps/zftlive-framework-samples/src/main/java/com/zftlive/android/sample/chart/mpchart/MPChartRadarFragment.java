package com.zftlive.android.sample.chart.mpchart;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;

import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.zftlive.android.R;
import com.zftlive.android.library.base.ui.BaseFragmentV4;
import com.zftlive.android.library.tools.ToolUnit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Java on 2017/7/1.
 */
public class MPChartRadarFragment extends BaseFragmentV4 {

    RadarChart mChart;

    @Override
    public int bindLayout() {
        return R.layout.fragment_mpchart_radar;
    }

    @Override
    public void initParams(Bundle params) {

    }

    @Override
    public void initView(View view) {

        //title
        configChartBasic();

        //XY轴坐标系配置
        configXYAixis();

        //提示浮层
        tipPopMask();

        //图例配置
        configLegend();

        //设置数据源
        genChartData();

        //渲染
        mChart.invalidate();
    }

    @Override
    public void loadDataOnce() {
        super.loadDataOnce();
    }

    /**
     * 图表标题+框架线条+旋转手势基础配置
     */
    private void configChartBasic() {
        //屏幕宽度
        int mScreenWidth = getResources().getDisplayMetrics().widthPixels;

        mChart = (RadarChart) findViewById(R.id.chart_radar);
        //图表描述文字
        mChart.getDescription().setText("《王者荣耀》玩家对战资料雷达");
        mChart.getDescription().setTextAlign(Paint.Align.LEFT);
        mChart.getDescription().setPosition(ToolUnit.dipToPx(mActivity, 16), ToolUnit.dipToPx(mActivity, 16));
        mChart.getDescription().setTextColor(Color.RED);
        mChart.getDescription().setTextSize(20);
        mChart.getDescription().setEnabled(true);
        //背景色
        mChart.setBackgroundColor(Color.parseColor("#030917"));

        //线条配置
        mChart.setWebAlpha((int) (0.40 * 255));
        mChart.setWebColor(Color.parseColor("#CCFFFFFF"));//每一块分割边线
        mChart.setWebColorInner(Color.parseColor("#CCFFFFFF"));//每一个圆圈内部区域颜色
        //内部已经dp转成px
        mChart.setWebLineWidth(2.5f);
        mChart.setWebLineWidthInner(2.0f);
        //默认旋转300度+禁止手势旋转
        mChart.setRotationAngle(300);
        mChart.setRotationEnabled(false);
    }

    /**
     * 配置图例样式
     */
    private void configLegend() {
        //图例样式设置
        Legend mLegend = mChart.getLegend();
        mChart.getLegend().setOrientation(Legend.LegendOrientation.HORIZONTAL);
        mLegend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        mLegend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        mLegend.setTextColor(Color.WHITE);
        mChart.getLegend().setTextSize(16);
//        mLegend.setXEntrySpace(7f);
//        mLegend.setYEntrySpace(5f);
    }

    private void configXYAixis() {

        //X轴样式(分析类型、指标-label)
        XAxis xAxis = mChart.getXAxis();
        xAxis.setDrawLabels(true);
        xAxis.setYOffset(0f);
        xAxis.setXOffset(0f);
        xAxis.setTextSize(14);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setValueFormatter(new IAxisValueFormatter() {

            String[] mXLabelFormatter = new String[]{"战绩KDA", "生存", "团战", "发育", "输出", "推进"};

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                //图形是圆形，数据需要取余
                return mXLabelFormatter[(int) value % mXLabelFormatter.length];
            }
        });
        //Y轴样式(数值刻度-value(0-100具体数字))
        YAxis mYAxis = mChart.getYAxis();
        mYAxis.setAxisMinimum(0);
        mYAxis.setAxisMaximum(1);
        mYAxis.setDrawLabels(true);//Y轴刻度
        mYAxis.setTextSize(10f);
        mYAxis.setTextColor(Color.parseColor("#D8FFFFFF"));
    }

    /**
     * 生成每组数据+对应的样式配置
     *
     * @return
     */
    private void genChartData() {

        //组装每一组数据
        List<RadarEntry> mRadarDataList = new ArrayList<>();
        //"战绩KDA", "生存", "团战", "发育", "输出", "推进"
        mRadarDataList.add(new RadarEntry(0.45f));
        mRadarDataList.add(new RadarEntry(0.52f));
        mRadarDataList.add(new RadarEntry(0.58f));
        mRadarDataList.add(new RadarEntry(0.48f));
        mRadarDataList.add(new RadarEntry(0.53f));
        mRadarDataList.add(new RadarEntry(0.95f));

        //数据源+图例文本
        RadarDataSet mPerGroupData = new RadarDataSet(mRadarDataList, "#鲁班七号#玩家");
        mPerGroupData.setDrawValues(false);//不绘制具体值
        //描边颜色
        mPerGroupData.setColor(Color.YELLOW);
        mPerGroupData.setDrawFilled(true);
        mPerGroupData.setLineWidth(2f);
        //填充颜色
        mPerGroupData.setFillColor(Color.parseColor("#2d4d64"));
        mPerGroupData.setFillAlpha((int) (0.40 * 255));
        //点击对应的点悬浮高亮提示点
        mPerGroupData.setDrawHighlightCircleEnabled(true);
        //高亮点对应的十字架
        mPerGroupData.setDrawHighlightIndicators(false);

        //fill
        mChart.setData(new RadarData(mPerGroupData));
    }

    /**
     * mask
     */
    private void tipPopMask() {
        //点击对应的坐标提示浮层
        mChart.setMarker(new RadarTipMaskView(mActivity, R.layout.fragment_mpchart_radar_markerview));
        //点击是否绘制提示浮层
//        mChart.setDrawMarkers(false);
    }
}

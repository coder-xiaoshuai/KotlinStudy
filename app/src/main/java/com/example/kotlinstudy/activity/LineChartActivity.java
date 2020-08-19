
package com.example.kotlinstudy.activity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.kotlinstudy.R;
import com.example.kotlinstudy.bean.Star;
import com.example.kotlinstudy.utils.StarUtils;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.components.YAxis.AxisDependency;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

/**
 * Example of a dual axis {@link LineChart} with multiple data sets.
 *
 * @version 3.1.0
 * @since 1.7.4
 */
public class LineChartActivity extends DemoBase implements
        OnChartValueSelectedListener {

    private LineChart chart;
    private RelativeLayout rlShowData;
    private TextView tvShowData;
    private TextView tvAllFans;
    private boolean hasShowData = false;
    private Star star1, star2, star3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rlShowData = findViewById(R.id.rl_show_data);
        tvShowData = findViewById(R.id.text_show_data);
        tvAllFans = findViewById(R.id.text_all_fans);


        chart = findViewById(R.id.chart1);
        chart.setOnChartValueSelectedListener(this);

        star1 = StarUtils.INSTANCE.getStar1();
        star2 = StarUtils.INSTANCE.getStar2();
        star3 = StarUtils.INSTANCE.getStar3();

        initView();
        // no description text
        chart.getDescription().setEnabled(false);

        // enable touch gestures
        chart.setTouchEnabled(true);

        chart.setDragDecelerationFrictionCoef(0.9f);

        // enable scaling and dragging
        chart.setDragEnabled(true);
        chart.setScaleEnabled(false);//禁止缩放
        chart.setDrawGridBackground(false);
        chart.setHighlightPerDragEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        chart.setPinchZoom(true);

        // set an alternative background color
        chart.setBackgroundColor(Color.WHITE);

        setData();

        chart.animateX(1500);

        // get the legend (only possible after setting data)
        Legend l = chart.getLegend();

        // modify the legend ...
        l.setForm(LegendForm.LINE);
        l.setTypeface(tfLight);
        l.setTextSize(11f);
        l.setTextColor(ContextCompat.getColor(this, R.color.color_text_gray_99));
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setFormToTextSpace(5f);//设置文字和左边图形距离

        XAxis xAxis = chart.getXAxis();
//        xAxis.setTypeface(tfLight);
        xAxis.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        xAxis.setTextSize(11f);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //设置x坐标文字
        xAxis.setAxisMinimum(0);
        xAxis.setAxisMaximum(18);
        xAxis.setLabelCount(6);
        xAxis.setCenterAxisLabels(true);//设置下面日期居中
        xAxis.setValueFormatter(new ValueFormatter() {
            private String[] labels = new String[]{"07/19", "07/25", "07/31", "08/06", "08/12", "08/18"};

            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                Log.i("xy", "value" + value);
                if (value >= 0 && value / 3 < labels.length) {
                    return labels[(int) value / 3];
                }
                return "";
            }
        });


        YAxis leftAxis = chart.getAxisLeft();
//        leftAxis.setTypeface(tfLight);
        leftAxis.setTextColor(ContextCompat.getColor(this, R.color.color_text_gray_99));
        leftAxis.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        leftAxis.setAxisMaximum(15f);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setLabelCount(3);
        leftAxis.setDrawGridLines(true);
        leftAxis.setGridColor(ContextCompat.getColor(this, R.color.gray_f2));
        leftAxis.setGridLineWidth(1.5f);
        leftAxis.setGranularityEnabled(true);

        //去掉y轴线
        leftAxis.setDrawAxisLine(false);
        //不显示y右坐标轴
        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setEnabled(false);


        //设置数据监听
        chart.setOnChartGestureListener(new OnChartGestureListener() {
            @Override
            public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
                //显示指示线
                chart.getData().setHighlightEnabled(true);
                if (hasShowData) {
                    rlShowData.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
                //隐藏指示线
                chart.getData().setHighlightEnabled(false);
                rlShowData.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onChartLongPressed(MotionEvent me) {

            }

            @Override
            public void onChartDoubleTapped(MotionEvent me) {

            }

            @Override
            public void onChartSingleTapped(MotionEvent me) {

            }

            @Override
            public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

            }

            @Override
            public void onChartScale(MotionEvent me, float scaleX, float scaleY) {

            }

            @Override
            public void onChartTranslate(MotionEvent me, float dX, float dY) {

            }
        });
    }

    private void initView() {
        tvAllFans.setText("总粉丝数量" + star1.getAllFansCount() + "万");
    }

    private void setData() {

        ArrayList<Entry> values1 = new ArrayList<>();

        for (int i = 0; i < star1.getLastIncreaseFans().size(); i++) {
//            int val = (int) (Math.random() * (range / 2));
            values1.add(new Entry(i, star1.getLastIncreaseFans().get(i).getIncreaseCount()));
        }

        ArrayList<Entry> values2 = new ArrayList<>();

        for (int i = 0; i < star2.getLastIncreaseFans().size(); i++) {
//            int val = (int) (Math.random() * (range / 2));
            values2.add(new Entry(i, star2.getLastIncreaseFans().get(i).getIncreaseCount()));
        }

        ArrayList<Entry> values3 = new ArrayList<>();

        for (int i = 0; i < star3.getLastIncreaseFans().size(); i++) {
//            int val = (int) (Math.random() * (range / 2));
            values3.add(new Entry(i, star3.getLastIncreaseFans().get(i).getIncreaseCount()));
        }

        LineDataSet set1, set2, set3;

        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) chart.getData().getDataSetByIndex(0);
            set2 = (LineDataSet) chart.getData().getDataSetByIndex(1);
            set3 = (LineDataSet) chart.getData().getDataSetByIndex(2);
            set1.setValues(values1);
            set2.setValues(values2);
            set3.setValues(values3);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(values1, star1.getName());

            set1.setAxisDependency(AxisDependency.LEFT);
            set1.setColor(ContextCompat.getColor(this,R.color.color_chart_blue));
            set1.setDrawCircles(false);//拐点是否是圆形
            set1.setDrawValues(false);//是否绘制数值
//            set1.setCircleColor(Color.WHITE);
            set1.setLineWidth(2f);
            set1.setCircleRadius(3f);
            set1.setFillAlpha(65);
            set1.setFillColor(ColorTemplate.getHoloBlue());//设置圆滑曲线
            set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            set1.setHighlightLineWidth(2f);
            set1.setDrawHorizontalHighlightIndicator(false);//不显示水平高亮线
            set1.setHighLightColor(ContextCompat.getColor(this,R.color.color_chart_high));
            set1.setDrawCircleHole(false);
            //set1.setFillFormatter(new MyFillFormatter(0f));
            //set1.setDrawHorizontalHighlightIndicator(false);
            //set1.setVisible(false);
            //set1.setCircleHoleColor(Color.WHITE);

            // create a dataset and give it a type
            set2 = new LineDataSet(values2, star2.getName());
            set2.setAxisDependency(AxisDependency.LEFT);
            set2.setColor(ContextCompat.getColor(this,R.color.color_chart_red));
            set2.setDrawCircles(false);//拐点是否是圆形
            set2.setDrawValues(false);//是否绘制数值
//            set2.setCircleColor(Color.WHITE);
            set2.setLineWidth(2f);
            set2.setCircleRadius(3f);
            set2.setFillAlpha(65);
            set2.setFillColor(Color.RED);
            set2.setDrawCircleHole(false);
            set2.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            set2.setHighlightLineWidth(2f);
            set2.setDrawHorizontalHighlightIndicator(false);//不显示水平高亮线
            set2.setHighLightColor(ContextCompat.getColor(this,R.color.color_chart_high));
            //set2.setFillFormatter(new MyFillFormatter(900f));

            set3 = new LineDataSet(values3, star3.getName());
            set3.setAxisDependency(AxisDependency.LEFT);
            set3.setColor(ContextCompat.getColor(this,R.color.color_chart_green));
            set3.setDrawCircles(false);//拐点是否是圆形
            set3.setDrawValues(false);//是否绘制数值
//            set3.setCircleColor(Color.WHITE);
            set3.setLineWidth(2f);
            set3.setCircleRadius(3f);
            set3.setFillAlpha(65);
            set3.setFillColor(ColorTemplate.colorWithAlpha(Color.YELLOW, 200));
            set3.setDrawCircleHole(false);
            set3.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            set3.setHighlightLineWidth(2f);
            set3.setDrawHorizontalHighlightIndicator(false);//不显示水平高亮线
            set3.setHighLightColor(ContextCompat.getColor(this,R.color.color_chart_high));

            // create a data object with the data sets
            LineData data = new LineData(set1, set2, set3);
            data.setValueTextColor(Color.WHITE);
            data.setValueTextSize(9f);

            // set data
            chart.setData(data);
        }
    }

    /**
     * 进行数据转换
     *
     * @param object
     * @return
     */
    private LineDataSet transData(Object object) {
        return null;
    }


    @Override
    protected void saveToGallery() {
        saveToGallery(chart, "LineChartActivity2");
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        chart.centerViewToAnimated(e.getX(), e.getY(), chart.getData().getDataSetByIndex(h.getDataSetIndex())
                .getAxisDependency(), 500);
        rlShowData.setVisibility(View.VISIBLE);
        tvShowData.setText(star1.getLastIncreaseFans().get((int) e.getX()).getDataStr() + "\n" + star1.getName() + ":" + star1.getLastIncreaseFans().get((int) e.getX()).getIncreaseCount() + "万粉\n" + star2.getName() + ":" + star2.getLastIncreaseFans().get((int) e.getX()).getIncreaseCount() + "万粉\n" + star3.getName() + ":" + star3.getLastIncreaseFans().get((int) e.getX()).getIncreaseCount() + "万粉");
        hasShowData = true;

    }

    @Override
    public void onNothingSelected() {
        Log.i("Nothing selected", "Nothing selected.");
        rlShowData.setVisibility(View.GONE);
    }

}

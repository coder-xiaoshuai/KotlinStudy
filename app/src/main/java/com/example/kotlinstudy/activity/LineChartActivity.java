
package com.example.kotlinstudy.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.kotlinstudy.R;
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
 * @since 1.7.4
 * @version 3.1.0
 */
public class LineChartActivity extends DemoBase implements OnSeekBarChangeListener,
        OnChartValueSelectedListener {

    private LineChart chart;
    private SeekBar seekBarX, seekBarY;
    private TextView tvX, tvY;
    private RelativeLayout rlShowData;
    private TextView tvShowData;
    private boolean hasShowData = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_linechart);

        setTitle("LineChartActivity3");

        tvX = findViewById(R.id.tvXMax);
        tvY = findViewById(R.id.tvYMax);

        rlShowData = findViewById(R.id.rl_show_data);
        tvShowData = findViewById(R.id.text_show_data);

        seekBarX = findViewById(R.id.seekBar1);
        seekBarX.setOnSeekBarChangeListener(this);

        seekBarY = findViewById(R.id.seekBar2);
        seekBarY.setOnSeekBarChangeListener(this);

        chart = findViewById(R.id.chart1);
        chart.setOnChartValueSelectedListener(this);

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

        // add data
        seekBarX.setProgress(20);
        seekBarY.setProgress(150);

        chart.animateX(1500);

        // get the legend (only possible after setting data)
        Legend l = chart.getLegend();

        // modify the legend ...
        l.setForm(LegendForm.CIRCLE);
        l.setTypeface(tfLight);
        l.setTextSize(11f);
        l.setTextColor(ContextCompat.getColor(this,R.color.color_text_black_33));
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setFormToTextSpace(5f);//设置文字和左边图形距离

//        l.setYOffset(10f);//设置文字竖直偏移量

        XAxis xAxis = chart.getXAxis();
        xAxis.setTypeface(tfLight);
        xAxis.setTextSize(11f);
        xAxis.setTextColor(Color.BLACK);
        Log.i("xy", "min---->" + xAxis.mAxisMinimum);
        Log.i("xy", "max---->" + xAxis.mAxisMaximum);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //设置x坐标文字
        Log.i("xy","---->"+xAxis.mEntries);
        xAxis.setAxisMinimum(0);
        xAxis.setAxisMaximum(6);
        xAxis.setValueFormatter(new ValueFormatter() {
            private String[] labels = new String[]{"08/01", "08/02", "08/03", "08/04", "08/05", "08/06", "08/07"};

            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                if (value < labels.length && value >= 0) {
                    return labels[(int) value];
                }
                return "";
            }
        });


        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setTypeface(tfLight);
        leftAxis.setTextColor(Color.BLACK);
        leftAxis.setAxisMaximum(200f);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setLabelCount(3);
        leftAxis.setDrawGridLines(true);
        leftAxis.setGranularityEnabled(true);

        //去掉y轴线
        leftAxis.setDrawAxisLine(false);
//
        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setEnabled(false);
        rightAxis.setTypeface(tfLight);
        rightAxis.setTextColor(Color.RED);
        rightAxis.setAxisMaximum(900);
        rightAxis.setAxisMinimum(-200);
        rightAxis.setDrawGridLines(false);
        rightAxis.setDrawZeroLine(false);
        rightAxis.setGranularityEnabled(false);


        //设置数据监听
        chart.setOnChartGestureListener(new OnChartGestureListener() {
            @Override
            public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
                if (hasShowData){
                    rlShowData.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
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
//        chart.setOnTouchListener(new ChartTouchListener(chart) {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (event.getAction() == MotionEvent.ACTION_UP||event.getAction() == MotionEvent.ACTION_CANCEL){
//                    rlShowData.setVisibility(View.INVISIBLE);
//                }
//                return false;
//            }
//        });

    }

    private void setData(int count, int range) {

        ArrayList<Entry> values1 = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            int val = (int) (Math.random() * (range / 2));
            values1.add(new Entry(i, val));
        }

        ArrayList<Entry> values2 = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            int val = (int) (Math.random() * range);
            values2.add(new Entry(i, val));
        }

        ArrayList<Entry> values3 = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            int val = (int) (Math.random() * range);
            values3.add(new Entry(i, val));
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
            set1 = new LineDataSet(values1, "明星1");

            set1.setAxisDependency(AxisDependency.LEFT);
            set1.setColor(ColorTemplate.getHoloBlue());
            set1.setDrawCircles(false);//拐点是否是圆形
            set1.setDrawValues(false);//是否绘制数值
//            set1.setCircleColor(Color.WHITE);
            set1.setLineWidth(2f);
            set1.setCircleRadius(3f);
            set1.setFillAlpha(65);
            set1.setFillColor(ColorTemplate.getHoloBlue());
            set1.setHighlightLineWidth(2f);
            set1.setDrawHorizontalHighlightIndicator(false);//不显示水平高亮线
            set1.setHighLightColor(Color.rgb(244, 117, 117));
            set1.setDrawCircleHole(false);
            //set1.setFillFormatter(new MyFillFormatter(0f));
            //set1.setDrawHorizontalHighlightIndicator(false);
            //set1.setVisible(false);
            //set1.setCircleHoleColor(Color.WHITE);

            // create a dataset and give it a type
            set2 = new LineDataSet(values2, "明星2");
            set2.setAxisDependency(AxisDependency.LEFT);
            set2.setColor(Color.RED);
            set2.setDrawCircles(false);//拐点是否是圆形
            set2.setDrawValues(false);//是否绘制数值
//            set2.setCircleColor(Color.WHITE);
            set2.setLineWidth(2f);
            set2.setCircleRadius(3f);
            set2.setFillAlpha(65);
            set2.setFillColor(Color.RED);
            set2.setDrawCircleHole(false);
            set2.setHighlightLineWidth(2f);
            set2.setDrawHorizontalHighlightIndicator(false);//不显示水平高亮线
            set2.setHighLightColor(Color.rgb(244, 117, 117));
            //set2.setFillFormatter(new MyFillFormatter(900f));

            set3 = new LineDataSet(values3, "明星3");
            set3.setAxisDependency(AxisDependency.LEFT);
            set3.setColor(Color.YELLOW);
            set3.setDrawCircles(false);//拐点是否是圆形
            set3.setDrawValues(false);//是否绘制数值
//            set3.setCircleColor(Color.WHITE);
            set3.setLineWidth(2f);
            set3.setCircleRadius(3f);
            set3.setFillAlpha(65);
            set3.setFillColor(ColorTemplate.colorWithAlpha(Color.YELLOW, 200));
            set3.setDrawCircleHole(false);
            set3.setHighlightLineWidth(2f);
            set3.setDrawHorizontalHighlightIndicator(false);//不显示水平高亮线
            set3.setHighLightColor(Color.rgb(244, 117, 117));

            // create a data object with the data sets
            LineData data = new LineData(set1, set2, set3);
            data.setValueTextColor(Color.WHITE);
            data.setValueTextSize(9f);

            // set data
            chart.setData(data);
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        tvX.setText(String.valueOf(seekBarX.getProgress()));
        tvY.setText(String.valueOf(seekBarY.getProgress()));

        setData(seekBarX.getProgress(), seekBarY.getProgress());

        // redraw
        chart.invalidate();
    }

    @Override
    protected void saveToGallery() {
        saveToGallery(chart, "LineChartActivity2");
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        Log.i("xy", "x" + e.getX() + ",y" + e.getY());
        Log.i("xy", "data" + e.getData());
        chart.centerViewToAnimated(e.getX(), e.getY(), chart.getData().getDataSetByIndex(h.getDataSetIndex())
                .getAxisDependency(), 500);
        rlShowData.setVisibility(View.VISIBLE);
        tvShowData.setText("明星1：" + e.getY() + "万粉\n明星2：" + e.getY() + "万粉\n明星3：" + e.getY() + "万粉");
        hasShowData = true;

    }

    @Override
    public void onNothingSelected() {
        Log.i("Nothing selected", "Nothing selected.");
        rlShowData.setVisibility(View.GONE);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {}

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {}
}

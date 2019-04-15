package com.example.lpy.myapplication.custom;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lpy.myapplication.R;
import com.example.lpy.myapplication.bean.MovieSchedule;
import com.example.lpy.myapplication.utils.TimeUtil;

import java.text.SimpleDateFormat;

/**
 * 时间轴区间表示计划日程
 */
public class TimeAxisView extends LinearLayout {

    private Context mContext;
    private View mView;
    private float oneHourSize60; //一个小时表示的尺寸(总长度除以24可获得)
    private float oneMimuteSize1; //一分钟表示的尺寸
    private int mWidth; //一个计划所占的宽度
    private SimpleDateFormat format = new SimpleDateFormat("HH:mm");

    public TimeAxisView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        mView = LayoutInflater.from(context).inflate(R.layout.view_time_axis, this);
    }

    //生成放入控件的代码，并添加进ConstraintLayout里
    public void setNewTextView(MovieSchedule movieItem, String currentDay) {
        String startTime = TimeUtil.getDateString(movieItem.getStartTime());
        String endTime = TimeUtil.getDateString(movieItem.getEndTime());
        //生成一个放入的控件
        TextView textView = new TextView(mContext);
        textView.setBackground(getResources().getDrawable(R.color.colorAccent80));
        textView.setBackground(getResources().getDrawable(R.drawable.bg_textview));
        textView.setTextColor(getResources().getColor(R.color.color_ffffff));
        textView.setText(movieItem.getMovieName() + "\n" + startTime + "-" + endTime);
        textView.setGravity(Gravity.CENTER);
        textView.setPadding(6, 0, 6, 0);
        textView.setTextSize(12);
//        //底下获取Calendar，为了拿到一天的时间
//        //获取开始时间的Calendar，这个东西很强大，能单独设置年月日时分秒为多少
//        Calendar calendar1 = Calendar.getInstance();
//        calendar1.setTimeInMillis(planData.meetingStartTime);
//        //获取结束时间的Calendar
//        Calendar calendar2 = Calendar.getInstance();
//        calendar2.setTimeInMillis(planData.meetingEndTime);
        //设置高度，算出他们直接差了多少分钟，然后乘以分钟所占的像素
//        int height = (int) ((calendar2.getTimeInMillis() - calendar1.getTimeInMillis()) /
//                60000 * oneMimutePx1);
        RelativeLayout relativeLayout = (RelativeLayout) mView.findViewById(R.id.containerLinear);
        oneHourSize60 = relativeLayout.getWidth() / 24f;
//        float textWidth = oneHourSize60 * 2;
        double textWidth = 0;
        double marginLeft = 0;
        if (movieItem.getStartTime().substring(0, 10).equals(movieItem.getEndTime().substring(0, 10))) {
            textWidth = (double) (TimeUtil.getStringToDate(movieItem.getEndTime()) - TimeUtil.getStringToDate(movieItem.getStartTime())) / (double) (1000 * 60 * 60) * oneHourSize60;
            marginLeft = (double) (TimeUtil.getStringToDate(movieItem.getStartTime()) - TimeUtil.getStringToDate(movieItem.getStartTime().substring(0, 10) + " 00:00:00")) / (double) (1000 * 60 * 60) * oneHourSize60;
        } else {
            if (movieItem.getStartTime().substring(0, 10).equals(currentDay)) {
                textWidth = (double) (TimeUtil.getStringToDate(movieItem.getEndTime()) - TimeUtil.getStringToDate(movieItem.getStartTime())) / (double) (1000 * 60 * 60) * oneHourSize60;
                marginLeft = (double) (TimeUtil.getStringToDate(movieItem.getStartTime()) - TimeUtil.getStringToDate(movieItem.getStartTime().substring(0, 10) + " 00:00:00")) / (double) (1000 * 60 * 60) * oneHourSize60;
//                textView.setText(movieItem.getMovieName().substring(0,movieItem.getMovieName().length()/2) + "\n" + startTime+"-");
            } else if (movieItem.getEndTime().substring(0, 10).equals(currentDay)) {
                textWidth = (double) (TimeUtil.getStringToDate(movieItem.getEndTime()) - TimeUtil.getStringToDate(movieItem.getEndTime().substring(0, 10) + " 00:00:00")) / (double) (1000 * 60 * 60) * oneHourSize60;
                marginLeft = 0;
//                textView.setText(movieItem.getMovieName().substring(movieItem.getMovieName().length()/2,movieItem.getMovieName().length()) + "\n" + endTime);
            }
        }
        textView.setWidth((int) textWidth);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        params.setMargins((int) marginLeft, 3, 0, 3);
        textView.setLayoutParams(params);
        relativeLayout.addView(textView);

    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     * DisplayMetrics类中属性density
     */
    public static int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}

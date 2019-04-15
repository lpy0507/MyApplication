package com.example.lpy.myapplication.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.lpy.myapplication.R;
import com.example.lpy.myapplication.bean.MovieSchedule;
import com.example.lpy.myapplication.custom.TimeAxisView;

import java.util.ArrayList;
import java.util.List;

/**
 * 时间轴
 */
public class TimeAxisActivity extends AppCompatActivity {

    private TextView dateTv1, dateTv2, dateTv3;
    private TimeAxisView timeAxis1, timeAxis2, timeAxis3;
    private LinearLayout timeLinear2, timeLinear3;

    private List<MovieSchedule> movieList = new ArrayList<>();
    private List<String> days = new ArrayList<>();//用于展示预约了哪几天

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_axis);
        initView();
        initData();
        setData();
    }

    private void setData() {
        if (days.size() == 1) { //只预约了一天
            dateTv1.setText(days.get(0));
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < movieList.size(); i++) {
                        if (movieList.get(i).getStartTime().substring(0, 10).equals(days.get(0)) || movieList.get(i).getEndTime().substring(0, 10).equals(days.get(0)))
                            timeAxis1.setNewTextView(movieList.get(i), days.get(0));
                    }
                }
            }, 300);
        } else if (days.size() == 2) { //预约了两天
            timeLinear2.setVisibility(View.VISIBLE);
            dateTv1.setText(days.get(1));
            dateTv2.setText(days.get(0));
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < movieList.size(); i++) {
                        if (movieList.get(i).getStartTime().substring(0, 10).equals(days.get(1)) || movieList.get(i).getEndTime().substring(0, 10).equals(days.get(1)))
                            timeAxis1.setNewTextView(movieList.get(i), days.get(1));
                        if (movieList.get(i).getStartTime().substring(0, 10).equals(days.get(0)) || movieList.get(i).getEndTime().substring(0, 10).equals(days.get(0)))
                            timeAxis2.setNewTextView(movieList.get(i), days.get(0));
                    }
                }
            }, 300);
        } else { //预约了今明后三天
            timeLinear2.setVisibility(View.VISIBLE);
            timeLinear3.setVisibility(View.VISIBLE);
            dateTv1.setText(days.get(2));
            dateTv2.setText(days.get(1));
            dateTv3.setText(days.get(0));
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < movieList.size(); i++) {
                        if (movieList.get(i).getStartTime().substring(0, 10).equals(days.get(2)) || movieList.get(i).getEndTime().substring(0, 10).equals(days.get(2)))
                            timeAxis1.setNewTextView(movieList.get(i), days.get(2));
                        if (movieList.get(i).getStartTime().substring(0, 10).equals(days.get(1)) || movieList.get(i).getEndTime().substring(0, 10).equals(days.get(1)))
                            timeAxis2.setNewTextView(movieList.get(i), days.get(1));
                        if (movieList.get(i).getStartTime().substring(0, 10).equals(days.get(0)) || movieList.get(i).getEndTime().substring(0, 10).equals(days.get(0)))
                            timeAxis3.setNewTextView(movieList.get(i), days.get(0));
                    }
                }
            }, 300);
        }
    }

    private void initData() {
        movieList.add(new MovieSchedule("惊奇队长", "2019-01-10 01:00:00", "2019-01-10 03:36:38"));

        movieList.add(new MovieSchedule("复仇者联盟4:绝地反击", "2019-01-11 00:00:00", "2019-01-11 03:36:38"));
        movieList.add(new MovieSchedule("钢铁侠4", "2019-01-11 10:20:00", "2019-01-11 13:36:28"));
        movieList.add(new MovieSchedule("无敌浩克:觉醒归来", "2019-01-11 22:30:00", "2019-01-12 01:36:38"));

        movieList.add(new MovieSchedule("变形金刚6:战役再起", "2019-01-12 02:40:00", "2019-01-12 04:06:18"));
        movieList.add(new MovieSchedule("海贼王剧场版:霸王对决", "2019-01-12 07:55:00", "2019-01-12 09:36:38"));
        movieList.add(new MovieSchedule("安图恩的心脏", "2019-01-12 12:00:00", "2019-01-12 15:10:30"));
        movieList.add(new MovieSchedule("卢克的救赎", "2019-01-12 21:21:21", "2019-01-12 23:58:11"));

        movieList.add(new MovieSchedule("超时空之战", "2019-01-13 03:25:00", "2019-01-13 05:50:38"));
        movieList.add(new MovieSchedule("超时空漩涡", "2019-01-13 06:00:00", "2019-01-13 08:36:55"));
        movieList.add(new MovieSchedule("七宗罪", "2019-01-13 15:30:00", "2019-01-13 18:10:10"));
        String lastDay = movieList.get(movieList.size() - 1).getStartTime().substring(0, 10);
        String tempDay = lastDay;
        days.add(lastDay);
        for (int i = movieList.size() - 1; i >= 0; i--) {
            if (!movieList.get(i).getStartTime().substring(0, 10).equals(tempDay)) {
                tempDay = movieList.get(i).getStartTime().substring(0, 10);
                days.add(tempDay);
            }
            if (days.size() == 3) break;
        }
    }

    private void initView() {
        dateTv1 = (TextView) findViewById(R.id.dateTv1);
        dateTv2 = (TextView) findViewById(R.id.dateTv2);
        dateTv3 = (TextView) findViewById(R.id.dateTv3);
        timeAxis1 = (TimeAxisView) findViewById(R.id.timeAxis1);
        timeAxis2 = (TimeAxisView) findViewById(R.id.timeAxis2);
        timeAxis3 = (TimeAxisView) findViewById(R.id.timeAxis3);
        timeLinear2 = (LinearLayout) findViewById(R.id.timeLinear2);
        timeLinear3 = (LinearLayout) findViewById(R.id.timeLinear3);
    }
}
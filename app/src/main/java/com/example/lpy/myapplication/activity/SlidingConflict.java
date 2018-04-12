package com.example.lpy.myapplication.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.lpy.myapplication.R;
import com.example.lpy.myapplication.custom.HorizontalScrollView;
import com.example.lpy.myapplication.custom.HorizontalScrollViewEx;

import java.util.ArrayList;
import java.util.List;

public class SlidingConflict extends AppCompatActivity {

    private HorizontalScrollViewEx horizontalScrollView;

    private List<String> data1 = new ArrayList<>();
    private List<String> data2 = new ArrayList<>();
    private List<String> data3 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sliding_conflict);

        horizontalScrollView = (HorizontalScrollViewEx) findViewById(R.id.horizontalScrollView);

        for (int i = 0; i < 30; i++) {
            data1.add("列表一..." + i);
            data2.add("列表二..." + i);
            data3.add("列表三..." + i);
        }

        ListView listView1 = new ListView(this);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data1);
        listView1.setAdapter(adapter1);

        ListView listView2 = new ListView(this);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data2);
        listView2.setAdapter(adapter2);

        ListView listView3 = new ListView(this);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data3);
        listView3.setAdapter(adapter3);

        ViewGroup.LayoutParams params
                = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        horizontalScrollView.addView(listView1, params);
        horizontalScrollView.addView(listView2, params);
        horizontalScrollView.addView(listView3, params);
    }
}

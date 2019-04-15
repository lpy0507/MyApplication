package com.example.lpy.myapplication.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.lpy.myapplication.R;
import com.example.lpy.myapplication.custom.FreeDragView;
import com.nineoldandroids.animation.ObjectAnimator;

public class FreeDragActivity extends AppCompatActivity {

    private FreeDragView freeDragView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_drag);

//        freeDragView = (FreeDragView) findViewById(R.id.freeDragView);
//        ObjectAnimator.ofFloat(freeDragView, "translationX", 0, 300).setDuration(2000).start();
//
//        /**
//         * 透明状态栏
//         */
//        String sdk = Build.VERSION.SDK_INT + "";
//        Log.e("***sdk===", sdk);
//        if (Build.VERSION.SDK_INT >= 21) {
//            View decorView = getWindow().getDecorView();
//            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
//            decorView.setSystemUiVisibility(option);
//            getWindow().setNavigationBarColor(Color.TRANSPARENT);
//            getWindow().setStatusBarColor(Color.TRANSPARENT);
//        }
//        ActionBar bar = getSupportActionBar();
//        bar.hide();
    }
}

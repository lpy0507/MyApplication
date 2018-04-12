package com.example.lpy.myapplication.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.example.lpy.myapplication.R;
import com.example.lpy.myapplication.custom.MagicArray;

/**
 * 自定义光能魔法阵动画
 */
public class MagicActivity extends Activity {
    private MagicArray magicArray;

    static Demo sDemo;
    private int a = 1;

    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
            String b = "Hello！" + a;
            Toast.makeText(MagicActivity.this, b, Toast.LENGTH_SHORT).show();
            a++;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magic);
        magicArray = (MagicArray) findViewById(R.id.magicArray);
        if (sDemo == null) {
            sDemo = new Demo();
                   }
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        }, 60 * 1000 * 10);
    }
    class Demo {
    }
}

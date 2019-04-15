package com.example.lpy.myapplication.others;

import android.content.Context;
import android.view.View;

import com.example.lpy.myapplication.utils.LogUtil;

public class MyView extends View {
    public MyView(Context context) {
        super(context);
        init();
    }

    public interface MyListener {
        void myCallBack();
    }

    public void init() {
        ListenerCollector listenerCollector = new ListenerCollector();
        listenerCollector.setsListener(this, myListener);
    }

    private MyListener myListener = new MyListener() {
        @Override
        public void myCallBack() {
            LogUtil.e("内存泄漏", "--MyListener->myCallBack");
        }
    };
}

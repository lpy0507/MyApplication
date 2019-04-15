package com.example.lpy.myapplication.others;

import android.view.View;

import java.util.WeakHashMap;

public class ListenerCollector {
    public static WeakHashMap<View,MyView.MyListener> sListener = new WeakHashMap();
    public void setsListener(View view,MyView.MyListener listener){
        sListener.put(view,listener);
    }
    public static void clearListener(){
            sListener.clear();
    }
}

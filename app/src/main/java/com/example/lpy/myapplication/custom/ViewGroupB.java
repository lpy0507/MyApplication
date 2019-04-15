package com.example.lpy.myapplication.custom;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.example.lpy.myapplication.utils.LogUtil;
import com.nineoldandroids.view.ViewHelper;

public class ViewGroupB extends LinearLayout {

    private int mLastX = 0;
    private int mLastY = 0;

    public ViewGroupB(Context context) {
        super(context);
    }

    public ViewGroupB(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewGroupB(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        LogUtil.e("事件分发>>>","ViewGroupB--->dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        LogUtil.e("事件分发>>>","ViewGroupB--->onInterceptTouchEvent");
        return super.onInterceptTouchEvent(ev);
//        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogUtil.e("事件分发>>>","ViewGroupB--->onTouchEvent");
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = x-mLastX;
                int deltaY = y-mLastY;
                int transX= (int) (ViewHelper.getTranslationX(this)+deltaX);
                int transY= (int) (ViewHelper.getTranslationY(this)+deltaY);
                ViewHelper.setTranslationX(this,transX);
                ViewHelper.setTranslationY(this,transY);
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        mLastX = x;
        mLastY = y;
        return true;
    }
}

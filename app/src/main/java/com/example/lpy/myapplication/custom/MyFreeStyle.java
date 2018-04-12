package com.example.lpy.myapplication.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 自定义View练习
 * Created by LPY on 2018/3/5.
 */

public class MyFreeStyle extends View {

    private Paint mPaint = new Paint();

    public MyFreeStyle(Context context) {
        super(context);
    }

    public MyFreeStyle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public MyFreeStyle(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        mPaint.setColor(Color.WHITE);
        /**
         * 笔画有3种模式：1.STROKE:描边；
         *                2.FILL:填充；
         *                3.FILL_AND_STROKE:描边并填充。
         */
//        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3);
        mPaint.setStyle(Paint.Style.FILL);
//        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRGB(40, 150, 200); //设置画布颜色

        /********画点********/
        mPaint.setColor(Color.BLACK);
        canvas.drawPoint(20, 20, mPaint);
        canvas.drawPoints(new float[]{
                30, 20,
                40, 20,
                50, 20}, mPaint);
        /********画线*********/
        mPaint.setColor(Color.RED);
        canvas.drawLine(30, 30, 80, 80, mPaint);
        canvas.drawLines(new float[]{
                80, 30, 30, 80,
                55, 30, 55, 80,
                30, 55, 80, 55
        }, mPaint);
        /********画矩形*********/
        mPaint.setColor(Color.DKGRAY);
        Rect rect = new Rect(150, 50, 350, 150);
        canvas.drawRect(rect, mPaint);
        mPaint.setColor(Color.CYAN);
        RectF rectF = new RectF(150, 50, 350, 150);
        canvas.drawRoundRect(rectF, 50, 50, mPaint);
        /********画椭圆*********/
        mPaint.setColor(Color.GREEN);
        RectF rectF1 = new RectF(500, 50, 700, 150);
        canvas.drawOval(rectF1, mPaint);
        /********画圆*********/
        mPaint.setColor(Color.LTGRAY);
        canvas.drawCircle(600, 300, 100, mPaint);
        /********画圆弧*********/
        RectF rectF2 = new RectF(30, 200, 230, 400);
        mPaint.setARGB(0, 40, 150, 200);
        canvas.drawRect(rectF2, mPaint);
        mPaint.setColor(Color.YELLOW);
        canvas.drawArc(rectF2, -30, -300, true, mPaint);
        mPaint.setColor(Color.BLACK);
        canvas.drawCircle(130, 250, 15, mPaint);
        mPaint.setColor(Color.RED);
        canvas.drawCircle(200, 300, 20, mPaint);
        canvas.drawCircle(250, 300, 20, mPaint);
        canvas.drawCircle(300, 300, 20, mPaint);
        /********画图表*********/
        RectF rectF3 = new RectF(30, 500, 230, 700);
        mPaint.setARGB(0, 40, 150, 200);
        canvas.drawRect(rectF2, mPaint);
        mPaint.setColor(Color.RED);
        canvas.drawArc(rectF3, -90, 200, true, mPaint);
        mPaint.setColor(Color.BLUE);
        canvas.drawArc(rectF3, 110, 60, true, mPaint);
        mPaint.setColor(Color.GREEN);
        canvas.drawArc(rectF3, 170, 100, true, mPaint);
    }
}

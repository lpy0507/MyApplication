package com.example.lpy.myapplication.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import com.nineoldandroids.animation.ValueAnimator;

/**
 * 贝塞尔曲线
 * Created by LPY on 2018/1/22.
 */
public class Bezier extends View {

    private Paint mPaint;
    private float centerA; //A圆心
    private float radiusA; //A半径
    private float centerB; //B圆心
    private float radiusB; //B半径
    private float maxDistance; //最大移动距离
    private boolean isOutRange; //是否超出范围
    private boolean isDisappear; //是否消失

    private PointF[] pointFsA = new PointF[2]; //存储A相对于B的两个点的坐标
    private PointF[] pointFsB = new PointF[2]; //存储B相对于A的两个点的坐标

    private PointF bezierCenterPiontF = new PointF(); //贝赛尔曲线中心控制点坐标

    private PointF centerAPointF = new PointF(centerA, centerA); //A圆心坐标
    private PointF centerBPointF = new PointF(centerB, centerB); //B圆心坐标

    public Bezier(Context context) {
        this(context, null);
    }

    public Bezier(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Bezier(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //获得两个圆心之间的临时距离
        float distance = (float) Math.sqrt(Math.pow(centerBPointF.y - centerAPointF.y, 2) + Math.pow(centerBPointF.x - centerAPointF.x, 2));
        distance = Math.min(distance, maxDistance);

        //A随着距离变大而逐渐缩小的计算
        float percent = distance / maxDistance;
        float radiusAPercentChange = ((Number) radiusA).floatValue() + percent * (((Number) (radiusA * 0.2f)).floatValue() - ((Number) radiusA).floatValue());

        //计算AB之间的偏移量
        float yOffset = centerAPointF.y - centerBPointF.y;
        float xOffset = centerAPointF.x - centerBPointF.x;
        //两圆心斜率
        Double lineK = 0.0;
        if (xOffset != 0f) {
            lineK = (double) (yOffset / xOffset);
        }

        //
        pointFsB = getIntersectionPoints(centerBPointF, radiusB, lineK);
        pointFsA = getIntersectionPoints(centerAPointF, radiusAPercentChange, lineK);

        bezierCenterPiontF = new PointF((centerAPointF.x + centerBPointF.x) / 2.0f, (centerAPointF.y + centerBPointF.y) / 2.0f);

        canvas.save();
        if (!isDisappear) {
            if (!isOutRange) {
                Path path = new Path();
                path.moveTo(pointFsA[0].x, pointFsA[0].y);
                path.quadTo(bezierCenterPiontF.x, bezierCenterPiontF.y, pointFsB[0].x, pointFsB[0].y);
                path.lineTo(pointFsB[1].x, pointFsB[1].y);
                path.quadTo(bezierCenterPiontF.x, bezierCenterPiontF.y, pointFsA[1].x, pointFsA[1].y);
                path.close();
                canvas.drawPath(path, mPaint);
                canvas.drawCircle(centerAPointF.x, centerAPointF.y, radiusAPercentChange, mPaint);
            }
            canvas.drawCircle(centerBPointF.x, centerBPointF.y, radiusB, mPaint);
        }
        canvas.restore();
    }

    public static PointF[] getIntersectionPoints(PointF pMiddle, float radius, Double lineK) {
        PointF[] points = new PointF[2];
        float radian, xOffset = 0, yOffset = 0;
        if (lineK != null) {
            radian = (float) Math.atan(lineK);
            xOffset = (float) (Math.sin(radian) * radius);
            yOffset = (float) (Math.cos(radian) * radius);
        } else {
            xOffset = radius;
            yOffset = 0;
        }
        points[0] = new PointF(pMiddle.x + xOffset, pMiddle.y - yOffset);
        points[1] = new PointF(pMiddle.x - xOffset, pMiddle.y + yOffset);
        return points;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float downx;
        float downy;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isOutRange = false;
                isDisappear = false;
                downx = event.getX();
                downy = event.getY();
                centerBPointF.set(downx, downy);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                downx = event.getX();
                downy = event.getY();
                centerBPointF.set(downx, downy);
                invalidate();
                //超过最大值断开
                float distance = (float) Math.sqrt(Math.pow(centerBPointF.y - centerAPointF.y, 2) + Math.pow(centerBPointF.x - centerAPointF.x, 2));
                if (distance > maxDistance) {
                    isOutRange = true;
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_CANCEL:
                if (isOutRange) {
                    distance = (float) Math.sqrt(Math.pow(centerBPointF.y - centerAPointF.y, 2) + Math.pow(centerBPointF.x - centerAPointF.x, 2));
                    if (distance > maxDistance) {
                        isOutRange = true;
                        invalidate();
                    } else {
                        centerBPointF.set(centerAPointF.x, centerAPointF.y);
                        invalidate();
                    }
                } else {
                    final PointF tempMovePointF = new PointF(centerBPointF.x, centerBPointF.y);
                    ValueAnimator valueAnimator = ValueAnimator.ofFloat(1.0f);
                    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            float percent = animation.getAnimatedFraction();
                            PointF pointF = new PointF(((Number) (tempMovePointF.x)).floatValue() + (((Number) (centerAPointF.x)).floatValue() - ((Number) (tempMovePointF.x)).floatValue() * percent),
                                    ((Number) (tempMovePointF.y)).floatValue() + (((Number) (centerAPointF.y)).floatValue() - ((Number) (tempMovePointF.y)).floatValue() * percent));
                            centerBPointF.set(pointF.x, pointF.y);
                            invalidate();
                        }
                    });
                    valueAnimator.setInterpolator(new OvershootInterpolator(4));
                    valueAnimator.setDuration(500);
                    valueAnimator.start();
                }
                break;
        }
        return true;
    }
}

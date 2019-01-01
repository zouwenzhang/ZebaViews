package com.zeba.views.click;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

public class ViewClickRippleHelper {
    private Paint mPaint;
    private float rate = 0f;
    private int runTime = 250;
    private float speed = 0f;// 速度
    private long startTime = 0;
    private long sumTime = 0;
    private int radius = 0;

    private View.OnClickListener mClickListener;
    public ViewClickRippleHelper(View v){
        init(v.getContext());
    }
    public void init(Context context) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setFilterBitmap(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.parseColor("#eeeeee"));
        touchSlop= ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public void setOnClickListener(View.OnClickListener listener){
        mClickListener=listener;
    }

    public void setRippleColor(String color){
        mPaint.setColor(Color.parseColor(color));
    }

    public void onSizeChanged(View v){
        radius = v.getMeasuredWidth() / 2;
        speed = (radius * 1f) / runTime;
    }

    private boolean isRuning = false;

    public void onDrawStart(View v,Canvas canvas) {
        if (isRuning) {
            if (rate >= 1f) {
                isRuning = false;
                if (mClickListener != null) {
                    mClickListener.onClick(v);
                }
            } else {
                long t = System.currentTimeMillis();
                long time = (t - startTime);
                sumTime += time;
                float ra = (sumTime * 1f) / runTime;
                int rd = (int) (ra * radius);
//				Loggers.e("rd=" + rd + ",rad=" + radius + ",w="
//						+ getMeasuredWidth());
                startTime = t;
                rate = ra;
                int a = (int) (255 - (255 * rate));
                if (a < 0) {
                    a = 0;
                }
                mPaint.setAlpha(a);
                canvas.drawCircle(v.getMeasuredWidth() / 2,
                        v.getMeasuredHeight() / 2, rd, mPaint);
            }
        }
    }

    public void onDrawEnd(View v){
        if (isRuning) {
            v.invalidate();
        }
    }

    private int mEvents = 0;
    private float downY = 0;
    private float lastY = 0f;
    private float downX = 0;
    private int touchSlop;// 系统认为的最小滑动距离

    public boolean onTouchEvent(View v,MotionEvent ev) {
        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                downY = ev.getY();
                lastY = downY;
                downX = ev.getX();
                mEvents = 1;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
            case MotionEvent.ACTION_POINTER_UP:
                // 过滤多点触碰
                mEvents = -1;
                break;
            case MotionEvent.ACTION_MOVE:
                if (mEvents == -1) {
                    break;
                }
                lastY = ev.getY();
                float f = lastY - downY;
                if (Math.abs(lastY - downY) > touchSlop) {
                    mEvents = -11;
                    break;
                }
                if (Math.abs(ev.getX() - downX) > touchSlop) {
                    mEvents = -1;
                    break;
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
            case MotionEvent.ACTION_UP:
                if (mEvents == 1) {
                    rate = 0;
                    isRuning = true;
                    sumTime = 0;
                    startTime = System.currentTimeMillis();
                    v.invalidate();
                    return true;
                }
            default:
                break;
        }
        return true;
    }
}

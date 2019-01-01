package com.zeba.views.click;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;


public class ViewClickRipple2Helper{
    private RippleDetector mDetector;

    public void init(Context context,View view,RippleDetector.Callback callback) {
        int defaultColor=Color.parseColor("#999999");
        Drawable drawable=view.getBackground();
        if(drawable!=null&&drawable instanceof ColorDrawable){
            ColorDrawable colorDrawable=(ColorDrawable) drawable;
            defaultColor=colorDrawable.getColor();
        }
        mDetector = new RippleDetector(context, view, callback, defaultColor);
    }

    public void onSizeChanged(int w, int h) {
        mDetector.onSizeChanged(w, h);
    }

    public boolean onTouchEvent(MotionEvent event,boolean superResult) {
        return mDetector.onTouchEvent(event, superResult);
    }

    public void cancelAnimator() {
        mDetector.cancelAnimator();
    }

    protected void onDraw(View v,Canvas canvas) {
        if (v.isInEditMode()) {
            return;
        }
        mDetector.draw(canvas);
    }

    /**
     * When the view performClick, we should ensure the background animation appears.
     * So we will handle the action in mDetector;
     *
     * @return
     */
    public boolean performClick() {
        return mDetector.handlePerformClick();
    }

    /**
     * When the view performClick, we should ensure the background animation appears.
     * So we will handle the action in mDetector;
     *
     * @return
     */
    public boolean performLongClick() {
        return mDetector.handlePerformLongClick();
    }

}

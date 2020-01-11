package com.zeba.views.click;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class ShapeTextView extends AppCompatTextView implements ViewSuperCallBack {
    private ViewClickHelper clickHelper;
    public ShapeTextView(Context context) {
        super(context);
        init(context,null);
    }

    public ShapeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public ShapeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context,AttributeSet attrs){
        clickHelper=new ViewClickHelper(this);
        clickHelper.getShape().init(context,attrs);
        clickHelper.init();
    }

    public CShape getShape(){
        return clickHelper.getShape();
    }

    public void setShapeDrawable(){
        clickHelper.setDrawable();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        clickHelper.onSizeChanged(w,h,oldw,oldh);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return clickHelper.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        clickHelper.onDraw(canvas);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

    }

    @Override
    public boolean performClick() {
        return clickHelper.performClick();
    }

    @Override
    public boolean performLongClick() {
        return clickHelper.performLongClick();
    }


    @Override
    public boolean superPerformClick() {
        return super.performClick();
    }

    @Override
    public boolean superPerformLongClick() {
        return super.performLongClick();
    }

    @Override
    public boolean superOnTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);

    }

}

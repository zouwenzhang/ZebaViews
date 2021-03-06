package com.zeba.views;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.google.android.flexbox.FlexboxLayout;
import com.zeba.views.attr.SAttr;
import com.zeba.views.click.ViewClickHelper;
import com.zeba.views.click.ViewSuperCallBack;
import com.zeba.views.css.AnimCSS;
import com.zeba.views.css.StyleCSS;
import com.zeba.views.interfaces.SViewer;


public class SFlexLayout extends FlexboxLayout implements ViewSuperCallBack, SViewer {

    private ViewClickHelper clickHelper;
    private StyleCSS styleCSS=new StyleCSS();
    private AnimCSS animCSS=new AnimCSS();
    private SAttr sAttr;

    public SFlexLayout(Context context) {
        super(context);
        init(context,null);
    }

    public SFlexLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public SFlexLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context,AttributeSet attrs){
        sAttr=new SAttr(context,attrs);
        clickHelper=new ViewClickHelper(this);
        reloadAttr(context);
    }

    @Override
    public void reloadAttr(Context context) {
        styleCSS.setCSS(this,sAttr);
        animCSS.setCSS(this,sAttr.anim);
        if(sAttr.shadow!=null&&!"".equals(sAttr.shadow)){
            setLayerType(LAYER_TYPE_SOFTWARE,null);
        }
        setShapeDrawable();
    }

    public SAttr getSAttr(){
        return sAttr;
    }

    public void setShapeDrawable(){
        clickHelper.setDrawable(sAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        clickHelper.onSizeChanged(w,h,oldw,oldh);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        styleCSS.initFinish(this);
        post(new Runnable() {
            @Override
            public void run() {
                animCSS.init();
            }
        });
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    public void animStart(){
        animCSS.start();
    }

    public AnimCSS anim(String css){
        animCSS.setCSS(this,css);
        return animCSS;
    }

    public AnimCSS getAnimCSS(){
        return animCSS;
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

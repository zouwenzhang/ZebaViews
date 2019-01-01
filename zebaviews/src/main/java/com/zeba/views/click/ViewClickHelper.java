package com.zeba.views.click;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.zeba.views.R;

public class ViewClickHelper {
    private ViewClickScaleHelper scaleHelper;
    private ViewClickRipple2Helper ripple2Helper;
    private int scaleTime=0;
    private View view;
    private ViewSuperCallBack superCallBack;

    public ViewClickHelper(View v){
        view=v;
        if(v!=null&&v instanceof ViewSuperCallBack){
            superCallBack=(ViewSuperCallBack) v;
        }
    }

    public void init(Context context, AttributeSet attrs){
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ClickShowType);
        String tag=typedArray.getString(R.styleable.ClickShowType_showType);
        float scaleTo=typedArray.getFloat(R.styleable.ClickShowType_scaleTo,0.9f);
        scaleTime=typedArray.getInteger(R.styleable.ClickShowType_scaleTime,120);
        typedArray.recycle();
        if("0".equals(tag)){
            scaleHelper=new ViewClickScaleHelper(view,scaleTo);
        }else if("2".equals(tag)){
            Drawable drawable=new ShapeHelper(context,attrs).getColorDrawable();
            if(drawable!=null){
                view.setBackground(drawable);
            }else{
                ripple2Helper=new ViewClickRipple2Helper();
                ripple2Helper.init(context, view, new RippleDetector.Callback() {
                    @Override
                    public void performClickAfterAnimation() {
                        if(superCallBack!=null){
                            superCallBack.superPerformClick();
                        }
                    }
                    @Override
                    public void performLongClickAfterAnimation() {
                        if(superCallBack!=null){
                            superCallBack.superPerformLongClick();
                        }
                    }
                });
            }
        }
    }

    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        if(ripple2Helper!=null){
            ripple2Helper.onSizeChanged(w,h);
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(scaleHelper!=null){
                    scaleHelper.onPressed(true);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if(scaleHelper!=null){
                    scaleHelper.onPressed(false);
                }
                break;
        }
        boolean rs=false;
        if(superCallBack!=null){
            rs=superCallBack.superOnTouchEvent(event);
        }
        if(ripple2Helper!=null){
            ripple2Helper.onTouchEvent(event,rs);
        }
        return rs;
    }

    public void onDraw(Canvas canvas) {
        if(ripple2Helper!=null){
            ripple2Helper.onDraw(view,canvas);
        }
    }

    public boolean performClick() {
        if(ripple2Helper!=null){
            return ripple2Helper.performClick();
        }
        if(scaleHelper!=null){
            view.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(superCallBack!=null){
                        superCallBack.superPerformClick();
                    }
                }
            }, scaleTime);
            return true;
        }
        if(superCallBack!=null){
            return superCallBack.superPerformClick();
        }
        return true;
    }

    public boolean performLongClick() {
        if(ripple2Helper!=null){
            return ripple2Helper.performLongClick();
        }
        if(superCallBack!=null){
            return superCallBack.superPerformLongClick();
        }
        return true;
    }

}

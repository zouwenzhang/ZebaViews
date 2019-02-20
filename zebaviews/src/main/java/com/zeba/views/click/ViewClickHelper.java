package com.zeba.views.click;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;

import java.lang.ref.WeakReference;

public class ViewClickHelper {
    private ViewClickScaleHelper scaleHelper;
    private ViewClickRipple2Helper ripple2Helper;
    private WeakReference<View> wrView;
    private ViewSuperCallBack superCallBack;
    private CShape shapeInfo;

    public ViewClickHelper(View v){
        wrView=new WeakReference<>(v);
        if(v!=null&&v instanceof ViewSuperCallBack){
            superCallBack=(ViewSuperCallBack) v;
        }
        shapeInfo=new CShape(this);
    }

    public void init(){
        if(wrView==null||wrView.get()==null){
            return;
        }
        View v=wrView.get();
        Drawable drawable=v.getBackground();
        if(drawable==null){
            drawable=ShapeHelper.getShapeDrawable(shapeInfo);
            v.setBackground(drawable);
        }
        setDrawable(drawable);
    }

    public CShape getShape(){
        return shapeInfo;
    }

    public void setDrawable(){
        if(wrView==null||wrView.get()==null){
            return;
        }
        Drawable drawable=ShapeHelper.getShapeDrawable(shapeInfo);
        if(shapeInfo.getShowType()==0){
            wrView.get().setBackground(drawable);
        }else{
            setDrawable(drawable);
        }
    }

    private void setDrawable(Drawable drawable){
        if(wrView==null||wrView.get()==null){
            return;
        }
        View view=wrView.get();
        if(shapeInfo.getShowType()==1){
            scaleHelper=new ViewClickScaleHelper(view,shapeInfo.getScaleTo());
        }else if(shapeInfo.getShowType()==3){
            Drawable rippleDrawable=RippleHelper.getRippleDrawable(drawable,shapeInfo);
            if(rippleDrawable!=null){
                view.setBackground(rippleDrawable);
            }else{
                ripple2Helper=new ViewClickRipple2Helper();
                ripple2Helper.init(view.getContext(), view,shapeInfo.getPressedColor(), new RippleDetector.Callback() {
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
        if(wrView==null||wrView.get()==null){
            return;
        }
        if(ripple2Helper!=null){
            ripple2Helper.onDraw(wrView.get(),canvas);
        }
    }

    public boolean performClick() {
        if(ripple2Helper!=null){
            return ripple2Helper.performClick();
        }
        if(scaleHelper!=null){
            if(wrView==null||wrView.get()==null){
                if(superCallBack!=null){
                    return superCallBack.superPerformClick();
                }
                return true;
            }
            wrView.get().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(superCallBack!=null){
                        superCallBack.superPerformClick();
                    }
                }
            }, shapeInfo.getScaleTime());
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

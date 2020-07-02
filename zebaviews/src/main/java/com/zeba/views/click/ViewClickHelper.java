package com.zeba.views.click;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import java.lang.ref.WeakReference;

public class ViewClickHelper {
    private ViewClickScaleHelper scaleHelper;
    private ViewClickAlpha2Helper alpha2Helper;
    private ViewClickRipple2Helper ripple2Helper;
    private WeakReference<View> wrView;
    private ViewSuperCallBack superCallBack;
    private CShape shapeInfo;
    private int minMoveDistance;
    private float mStartX;
    private float mStartY;
    private int actionState=0;

    public ViewClickHelper(View v){
        wrView=new WeakReference<>(v);
        if(v!=null&&v instanceof ViewSuperCallBack){
            superCallBack=(ViewSuperCallBack) v;
        }
        shapeInfo=new CShape(this);
        minMoveDistance= ViewConfiguration.get(v.getContext()).getScaledTouchSlop();
    }

    public void init(){
        if(wrView==null||wrView.get()==null){
            return;
        }
        View v=wrView.get();
        Drawable drawable=v.getBackground();
        if(drawable==null||shapeInfo.getDefaultColor()!=0){
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
            view.setBackground(drawable);
            scaleHelper=new ViewClickScaleHelper(view,shapeInfo.getScaleTo(),shapeInfo.getScaleTime());
        }else if(shapeInfo.getShowType()==2){
            view.setBackground(drawable);
            alpha2Helper=new ViewClickAlpha2Helper(view,shapeInfo.getAlphaTo(),shapeInfo.getAlphaTime());
        }else if(shapeInfo.getShowType()==3){
            Drawable rippleDrawable=RippleHelper.getRippleDrawable(drawable,shapeInfo);
            if(rippleDrawable!=null){
                view.setBackground(rippleDrawable);
            }else{
                view.setBackground(drawable);
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
        if(wrView.get().isEnabled()&&wrView.get().isClickable()){
            switch(event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    mStartX=event.getRawX();
                    mStartY=event.getRawY();
                    actionState=0;
                    wrView.get().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(actionState!=0){
                                return;
                            }
                            actionState=1;
                            if(scaleHelper!=null){
                                scaleHelper.onPressed(true);
                            }else if(alpha2Helper!=null){
                                alpha2Helper.onPressed(true);
                            }
                        }
                    }, 200);
                    break;
                case MotionEvent.ACTION_MOVE:
                    if(Math.abs(event.getRawX()-mStartX)>minMoveDistance||Math.abs(event.getRawY()-mStartY)>minMoveDistance){
                        if(actionState==0){
                            actionState=-1;
                        }
                    }
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    if(actionState==0&&event.getAction()==MotionEvent.ACTION_CANCEL){
                        actionState=-1;
                        break;
                    }
                    if(actionState==0){
                        actionState=2;
                        if(scaleHelper!=null){
                            scaleHelper.onPressed(true);
                            scaleHelper.onPressed(false);
                        }else if(alpha2Helper!=null){
                            alpha2Helper.onPressed(true);
                            alpha2Helper.onPressed(false);
                        }
                    }else if(actionState==1){
                        actionState=2;
                        if(scaleHelper!=null){
                            scaleHelper.onPressed(false);
                        }else if(alpha2Helper!=null){
                            alpha2Helper.onPressed(false);
                        }
                    }
                    break;
            }
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
            }, shapeInfo.getScaleTime()*2);
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

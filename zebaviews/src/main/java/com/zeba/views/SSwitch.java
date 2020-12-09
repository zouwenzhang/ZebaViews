package com.zeba.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import com.zeba.views.attr.SAttr;
import com.zeba.views.attr.SAttrSwitch;
import com.zeba.views.css.AnimCSS;
import com.zeba.views.css.StyleCSS;
import com.zeba.views.interfaces.SViewer;

public class SSwitch extends FrameLayout implements SViewer {
    private SAttr sAttr;
    private StyleCSS styleCSS=new StyleCSS();
    private AnimCSS animCSS=new AnimCSS();
    private SFrameLayout offBgView;
    private SFrameLayout onBgView;
    private SFrameLayout pointView;
    private boolean isChecked=false;
    private boolean isRunning=false;
    public SSwitch(@NonNull Context context) {
        super(context);
        initView(context,null);
    }

    public SSwitch(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context,attrs);
    }

    public SSwitch(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context,attrs);
    }

    private void initView(Context context,AttributeSet attrs){
        sAttr=new SAttrSwitch(context,attrs);
        offBgView=new SFrameLayout(context);
        addView(offBgView, LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
        onBgView=new SFrameLayout(context);
        onBgView.setAlpha(0f);
        addView(onBgView, LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
        pointView=new SFrameLayout(context);
        addView(pointView, LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        reloadAttr(context);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                setChecked(!isChecked);
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int h=MeasureSpec.getSize(heightMeasureSpec);
        int pwh=(int)(h*0.8f);
        LayoutParams lp=(LayoutParams) pointView.getLayoutParams();
        lp.gravity= Gravity.LEFT|Gravity.CENTER_VERTICAL;
        lp.leftMargin=(int)(h*0.1f);
        lp.topMargin=(int)(h*0.1f);
        lp.rightMargin=(int)(h*0.1f);
        lp.bottomMargin=(int)(h*0.1f);
        lp.width=pwh;
        lp.height=pwh;
        pointView.setLayoutParams(lp);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        offBgView.getSAttr().setDefaultColor(sAttr.getOffBgColor());
        offBgView.getSAttr().setRoundRadius(h/2);
        offBgView.reloadAttr(getContext());
        onBgView.getSAttr().setDefaultColor(sAttr.getOnBgColor());
        onBgView.getSAttr().setRoundRadius(h/2);
        onBgView.reloadAttr(getContext());
        pointView.getSAttr().setDefaultColor(sAttr.getFgColor());
        pointView.getSAttr().setRoundRadius(pwh/2);
        pointView.reloadAttr(getContext());
        setChecked(isChecked,false);
    }

    @Override
    public void reloadAttr(Context context) {
        styleCSS.setCSS(this,sAttr);
        animCSS.setCSS(this,sAttr.anim);
    }

    @Override
    public SAttr getSAttr() {
        return sAttr;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean isCheck){
        setChecked(isCheck,true);
    }

    public void setChecked(boolean isCheck,boolean isAnim){
        if(isRunning){
            return;
        }
        if(switchCheckListener!=null&&!switchCheckListener.onChange(isCheck)){
            return;
        }
        isChecked=isCheck;
        if(!isAnim){
            if(isCheck){
                onBgView.setAlpha(1f);
                pointView.setTranslationX(getMeasuredWidth()-(getMeasuredHeight()));
            }else{
                onBgView.setAlpha(0f);
                pointView.setTranslationX(0);
            }
        }else{
            isRunning=true;
            if(isCheck){
                new AnimCSS().view(pointView)
                        .style("d:200;mx:0,"+(getMeasuredWidth()-getMeasuredHeight())+";ms:s9;")
                        .start();
                new AnimCSS().view(onBgView)
                        .style("d:200;a:1;af:0;at:1;")
                        .onEnd(new Runnable() {
                            @Override
                            public void run() {
                                onBgView.setAlpha(1f);
                                isRunning=false;
                            }
                        }).start();
            }else{
                new AnimCSS().view(pointView)
                        .style("d:200;mx:"+(getMeasuredWidth()-getMeasuredHeight())+",0;ms:s9;")
                        .start();
                new AnimCSS().view(onBgView)
                        .style("d:200;a:1;af:1;at:0;")
                        .onEnd(new Runnable() {
                            @Override
                            public void run() {
                                onBgView.setAlpha(0f);
                                isRunning=false;
                            }
                        }).start();
            }
        }
    }

    public void setSwitchCheckListener(SwitchCheckListener listener){
        switchCheckListener=listener;
    }

    private SwitchCheckListener switchCheckListener;

    public interface SwitchCheckListener{
        boolean onChange(boolean isCheck);
    }
}

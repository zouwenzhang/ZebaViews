package com.zeba.views.css;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnimCSS {

    private static boolean devMode=true;

    private Map<String,String> map=new HashMap<>();
    private AnimatorSet lastSet;
    private WeakReference<View> refV;
    private Runnable startRunnable;
    private Runnable endRunnable;
    private Runnable cancelRunnable;
    private Runnable repeatRunnable;
    private Runnable pauseRunnable;
    private Runnable resumeRunnable;
    private int startShowState=-1;
    private int endShowState=-1;
    private int vWidth;
    private int vHeight;

    public AnimCSS(View v,String style){
        map.putAll(CSSFormat.form(style));
        refV=new WeakReference<>(v);
        if(map.size()!=0){
            if(map.get("d")==null){
                map.put("d","300");
            }
            if(map.get("sd")==null){
                map.put("sd","300");
            }
            if(map.get("one")==null){
                map.put("one","1");
            }
            if(map.get("in")==null){
                map.put("in","1");
            }
        }
        init();
    }

    public AnimCSS(String style){
        map.putAll(CSSFormat.form(style));
        if(map.size()!=0){
            if(map.get("d")==null){
                map.put("d","300");
            }
        }
    }

    public AnimCSS(){

    }

    public AnimCSS style(String style){
        map.putAll(CSSFormat.form(style));
        if(map.size()!=0){
            if(map.get("d")==null){
                map.put("d","300");
            }
        }
        return this;
    }

    public AnimCSS add(String key,String value){
        map.put(key,value);
        return this;
    }

    public AnimCSS view(View v){
        refV=new WeakReference<>(v);
        vWidth=v.getMeasuredWidth();
        vHeight=v.getMeasuredHeight();
        return this;
    }

    public AnimCSS postWH(final Runnable runnable){
        refV.get().post(new Runnable() {
            @Override
            public void run() {
                vWidth=refV.get().getMeasuredWidth();
                vHeight=refV.get().getMeasuredHeight();
                if(runnable!=null){
                    runnable.run();
                }
            }
        });
        return this;
    }

    public AnimCSS postWHClose(){
        postWHClose(null);
        return this;
    }

    public AnimCSS postWHClose(final Runnable runnable){
        refV.get().post(new Runnable() {
            @Override
            public void run() {
                vWidth=refV.get().getMeasuredWidth();
                vHeight=refV.get().getMeasuredHeight();
                close();
                if(runnable!=null){
                    runnable.run();
                }
            }
        });
        return this;
    }

    public AnimCSS endGone(){
        endShowState=View.GONE;
        return this;
    }

    public AnimCSS endHide(){
        endShowState=View.INVISIBLE;
        return this;
    }

    public AnimCSS endShow(){
        endShowState=View.VISIBLE;
        return this;
    }

    public AnimCSS startGone(){
        startShowState=View.GONE;
        return this;
    }

    public AnimCSS startHide(){
        startShowState=View.INVISIBLE;
        return this;
    }

    public AnimCSS startShow(){
        startShowState=View.VISIBLE;
        return this;
    }

    public AnimCSS gone(){
        refV.get().setVisibility(View.GONE);
        return this;
    }

    public AnimCSS hide(){
        refV.get().setVisibility(View.INVISIBLE);
        return this;
    }

    public AnimCSS close(){
        if(map.get("csy")!=null){
            refV.get().getLayoutParams().height=0;
        }
        if(map.get("csx")!=null){
            refV.get().getLayoutParams().width=0;
        }
        refV.get().requestLayout();
        return this;
    }

    public AnimCSS show(){
        refV.get().setVisibility(View.VISIBLE);
        return this;
    }

    public static void open(){
        devMode=false;
    }

    public void init(){
        if(devMode){
            return;
        }
        View v=refV.get();
        if(map.size()!=0&&!"0".equals(map.get("show"))&&!"0".equals(map.get("in"))){
            if(v.getVisibility()==View.INVISIBLE){
                if(!isShowed()){
                    create();
                    if(!"0".equals(map.get("auto"))&&!"1".equals(map.get("tc"))){
                        start();
                    }
                }
            }else{
                v.setVisibility(View.INVISIBLE);
            }
        }
    }

    public boolean isShowed(){
        if(map!=null&&!"1".equals(map.get("one"))){
            return false;
        }
        if(lastSet!=null){
            return true;
        }
        return false;
    }

    private Animator.AnimatorListener animatorListener=new Animator.AnimatorListener() {
        @SuppressLint("WrongConstant")
        @Override
        public void onAnimationStart(Animator animator) {
            if(lastSet!=animator){
                return;
            }
            if(startShowState!=-1){
                refV.get().setVisibility(startShowState);
            }else{
                refV.get().setVisibility(View.VISIBLE);
            }
            if(startRunnable!=null){
                startRunnable.run();
            }
        }
        @SuppressLint("WrongConstant")
        @Override
        public void onAnimationEnd(Animator animator) {
            if(lastSet!=animator){
                return;
            }
            if(endShowState!=-1){
                refV.get().setVisibility(endShowState);
            }
            if(endRunnable!=null){
                endRunnable.run();
            }
        }
        @Override
        public void onAnimationCancel(Animator animator) {
            if(lastSet!=animator){
                return;
            }
            if(cancelRunnable!=null){
                cancelRunnable.run();
            }
        }
        @Override
        public void onAnimationRepeat(Animator animator) {
            if(lastSet!=animator){
                return;
            }
            if(repeatRunnable!=null){
                repeatRunnable.run();
            }
        }
    };

    private Animator.AnimatorPauseListener animatorPauseListener=new Animator.AnimatorPauseListener() {
        @Override
        public void onAnimationPause(Animator animator) {
            if(lastSet!=animator){
                return;
            }
            if(pauseRunnable!=null){
                pauseRunnable.run();
            }
        }
        @Override
        public void onAnimationResume(Animator animator) {
            if(lastSet!=animator){
                return;
            }
            if(resumeRunnable!=null){
                resumeRunnable.run();
            }
        }
    };

    private ValueAnimator.AnimatorUpdateListener updateXListener=new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            float a=(float)valueAnimator.getAnimatedValue();
            refV.get().getLayoutParams().width=(int)(vWidth*a);
            refV.get().requestLayout();
        }
    };

    private ValueAnimator.AnimatorUpdateListener updateYListener=new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            float a=(float)valueAnimator.getAnimatedValue();
            refV.get().getLayoutParams().height=(int)(vHeight*a);
            refV.get().requestLayout();
        }
    };

    public boolean isTextChangeStart(){
        if("1".equals(map.get("tc"))){
            return true;
        }
        return false;
    }

    public AnimCSS onStart(Runnable runnable){
        startRunnable=runnable;
        return this;
    }

    public AnimCSS onEnd(Runnable runnable){
        endRunnable=runnable;
        return this;
    }

    public AnimCSS onCancel(Runnable runnable){
        cancelRunnable=runnable;
        return this;
    }

    public AnimCSS onRepeat(Runnable runnable){
        repeatRunnable=runnable;
        return this;
    }

    public AnimCSS onPause(Runnable runnable){
        pauseRunnable=runnable;
        return this;
    }

    public AnimCSS onResume(Runnable runnable){
        resumeRunnable=runnable;
        return this;
    }

    public AnimCSS create(){
        if(lastSet!=null&&lastSet.isRunning()){
            lastSet.cancel();
            lastSet=null;
        }
        View v=refV.get();
        AnimatorSet set=new AnimatorSet();
        set.setDuration(Integer.parseInt(map.get("d")));
        set.addListener(animatorListener);
        set.addPauseListener(animatorPauseListener);
        List<Animator> list=new ArrayList<>();
        Float mf=null;
        int vw=v.getMeasuredWidth();
        int vh=v.getMeasuredHeight();
        if(map.get("mf")!=null){
            mf=Float.parseFloat(map.get("mf"));
        }
        if(map.get("m")!=null){
            ObjectAnimator animator=null;
            if("l".equals(map.get("m"))){
                animator=ObjectAnimator.ofFloat(v,"translationX",mf==null?-vw:-mf*vw,0);
            }else if("r".equals(map.get("m"))){
                animator=ObjectAnimator.ofFloat(v,"translationX",mf==null?vw:mf*vw,0);
            }else if("t".equals(map.get("m"))){
                animator=ObjectAnimator.ofFloat(v,"translationY",mf==null?-vh:-mf*vh,0);
            }else if("b".equals(map.get("m"))){
                animator=ObjectAnimator.ofFloat(v,"translationY",mf==null?vh:mf*vh,0);
            }
            if(animator!=null){
                animator.setInterpolator(interpolator(map.get("ms")));
                list.add(animator);
            }
        }else if(map.get("mx")!=null){
            String vs=map.get("mx");
            float[] vss=CSSFormat.toFloat2(vs);
            ObjectAnimator animator=ObjectAnimator.ofFloat(v,"translationX",vss[0],vss[1]);
            animator.setInterpolator(interpolator(map.get("ms")));
            list.add(animator);
        }else if(map.get("my")!=null){
            String vs=map.get("my");
            float[] vss=CSSFormat.toFloat2(vs);
            ObjectAnimator animator=ObjectAnimator.ofFloat(v,"translationY",vss[0],vss[1]);
            animator.setInterpolator(interpolator(map.get("ms")));
            list.add(animator);
        }
        if(map.get("a")!=null){
            float f=0f;
            float t=1f;
            if(map.get("af")!=null){
                f=Float.parseFloat(map.get("af"));
            }
            if(map.get("at")!=null){
                t=Float.parseFloat(map.get("at"));
            }
            ObjectAnimator animator=ObjectAnimator.ofFloat(v,"alpha",f,t);
            animator.setInterpolator(interpolator(map.get("as")));
            list.add(animator);
        }
        if(map.get("s")!=null||map.get("sx")!=null||map.get("sy")!=null){
            float f=0f;
            float t=1f;
            if(map.get("sf")!=null){
                f=Float.parseFloat(map.get("sf"));
            }
            if(map.get("st")!=null){
                t=Float.parseFloat(map.get("st"));
            }
            if(map.get("sy")==null){
                ObjectAnimator animator1=ObjectAnimator.ofFloat(v,"scaleX",f,t);
                animator1.setInterpolator(interpolator(map.get("ss")));
                if(map.get("csx")!=null){
                    animator1.addUpdateListener(updateXListener);
                }
                list.add(animator1);
            }
            if(map.get("sx")==null){
                ObjectAnimator animator2=ObjectAnimator.ofFloat(v,"scaleY",f,t);
                animator2.setInterpolator(interpolator(map.get("ss")));
                if(map.get("csy")!=null){
                    animator2.addUpdateListener(updateYListener);
                }
                list.add(animator2);
            }
        }
        set.playTogether(list);
        lastSet=set;
        return this;
    }

    public void start(){
        if(refV==null||refV.get()==null){
            return;
        }
        refV.get().post(new Runnable() {
            @Override
            public void run() {
                if(lastSet==null){
                    create();
                }
                if(map.get("sd")==null){
                    lastSet.start();
                }else{
                    long t=Long.parseLong(map.get("sd"));
                    refV.get().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(lastSet!=null){
                                lastSet.start();
                            }
                        }
                    },t);
                }
            }
        });
    }

    private TimeInterpolator interpolator(String key){
        if("s1".equals(key)){
            return new OvershootInterpolator();//动画会超过目标值一些，然后再弹回来
        }
        if("s3".equals(key)){
            return new LinearInterpolator();////匀速
        }
        if("s4".equals(key)){
            return new AccelerateInterpolator();//持续加速
        }
        if("s5".equals(key)){
            return new DecelerateInterpolator();//持续减速到0
        }
        if("s6".equals(key)){
            return new AnticipateInterpolator();//先回拉一下再进行正常动画
        }
        if("s7".equals(key)){
            return new AnticipateOvershootInterpolator();//开始前回拉，最后超过一些然后回弹
        }
        if("s8".equals(key)){
            return new BounceInterpolator();//在目标值处弹跳
        }
        if("s9".equals(key)){
            return new FastOutSlowInInterpolator();//先加速再减速
        }
        if("s10".equals(key)){
            return new LinearOutSlowInInterpolator();//持续减速
        }
        return new AccelerateDecelerateInterpolator();//先加速 在减速(默认的)
    }

}


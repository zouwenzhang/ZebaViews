package com.zeba.views.click;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.util.Log;
import android.view.View;

public class ViewClickScaleHelper {
	private View view;
	private boolean isPressed=false;
	private ValueAnimator scaleAnimDown;
	private ValueAnimator scaleAnimUp;
	private float scale;
	private int scaleTime;
	public ViewClickScaleHelper(View v,float scaleTo,int scaleTime) {
		view=v;
		scale=scaleTo;
		this.scaleTime=scaleTime;
		initDown();
		initUp();
	}

	public void setScale(float s){
		scale=s;
		initDown();
		initUp();
	}
	public void onPressed(boolean pressed){
		isPressed=pressed;
		if(pressed){
			scaleAnimUp.cancel();
			scaleAnimDown.start();
		}else if(!scaleAnimDown.isRunning()){
			scaleAnimUp.start();
		}
	}
	private void initDown(){
		scaleAnimDown = ValueAnimator.ofFloat(1f,scale);
		scaleAnimDown.setDuration(scaleTime);
		scaleAnimDown.addListener(new Animator.AnimatorListener() {
			@Override
			public void onAnimationStart(Animator arg0) {
				
			}
			@Override
			public void onAnimationRepeat(Animator arg0) {
				
			}
			@Override
			public void onAnimationEnd(Animator arg0) {
				if(!isPressed){
					scaleAnimUp.start();
				}
			}
			@Override
			public void onAnimationCancel(Animator arg0) {
				
			}
		});
		scaleAnimDown.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator amin) {
				float va=(Float) amin.getAnimatedValue();
				view.setScaleX(va);
				view.setScaleY(va);
			}
		});
	}
	private void initUp(){
		scaleAnimUp = ValueAnimator.ofFloat(scale,1f);
		scaleAnimUp.setDuration(scaleTime);
		scaleAnimUp.addListener(new Animator.AnimatorListener() {
			@Override
			public void onAnimationStart(Animator arg0) {
				
			}
			@Override
			public void onAnimationRepeat(Animator arg0) {
				
			}
			@Override
			public void onAnimationEnd(Animator arg0) {
				
			}
			@Override
			public void onAnimationCancel(Animator arg0) {
				
			}
		});
		scaleAnimUp.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator amin) {
				float va=(Float) amin.getAnimatedValue();
				view.setScaleX(va);
				view.setScaleY(va);
			}
		});
	}

	public void printState(){
		Log.e("zwz","scale="+scale);
		Log.e("zwz","scaleTime="+scaleTime);
	}
}

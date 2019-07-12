package com.zeba.views.click;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.view.View;

public class ViewClickAlpha2Helper {
	private View view;
	private boolean isPressed=false;
	private ValueAnimator alphaAnimDown;
	private ValueAnimator alphaAnimUp;
	private float alpha;
	private int alphaTime=200;
	public ViewClickAlpha2Helper(View v, float alphaTo,int alphaTime) {
		view=v;
		alpha =alphaTo;
		this.alphaTime=alphaTime;
		initDown();
		initUp();
	}

	public void setAlpha(float s){
		alpha =s;
		initDown();
		initUp();
	}
	public void onPressed(boolean pressed){
		isPressed=pressed;
		if(pressed){
			alphaAnimUp.cancel();
			alphaAnimDown.start();
		}else if(!alphaAnimDown.isRunning()){
			alphaAnimUp.start();
		}
	}
	private void initDown(){
		alphaAnimDown = ValueAnimator.ofFloat(1f, alpha);
		alphaAnimDown.setDuration(alphaTime);
		alphaAnimDown.addListener(new Animator.AnimatorListener() {
			@Override
			public void onAnimationStart(Animator arg0) {
				
			}
			@Override
			public void onAnimationRepeat(Animator arg0) {
				
			}
			@Override
			public void onAnimationEnd(Animator arg0) {
				if(!isPressed){
					alphaAnimUp.start();
				}
			}
			@Override
			public void onAnimationCancel(Animator arg0) {
				
			}
		});
		alphaAnimDown.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator amin) {
				float va=(Float) amin.getAnimatedValue();
				view.setAlpha(va);
			}
		});
	}
	private void initUp(){
		alphaAnimUp = ValueAnimator.ofFloat(alpha,1f);
		alphaAnimUp.setDuration(alphaTime);
		alphaAnimUp.addListener(new Animator.AnimatorListener() {
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
		alphaAnimUp.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator amin) {
				float va=(Float) amin.getAnimatedValue();
				view.setAlpha(va);
			}
		});
	}
}

package com.zeba.views.click;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.view.MotionEvent;
import android.view.View;

public class ViewClickAlphaHelper {
	private long startTime;
	private boolean isDown=false;
	private Drawable mDrawable2;
	private float animTime=500f;
	protected void draw(View v,Canvas canvas) {
		if(mDrawable2!=null){
			if(isDown){
				mDrawable2.setAlpha(255);
			}else{
				long t=System.currentTimeMillis()-startTime;
				if(t<animTime){
					int a=(int) (255-255*(t/animTime));
					mDrawable2.setAlpha(a);
					mDrawable2.draw(canvas);
					v.postInvalidate();
				}else{
					mDrawable2.setAlpha(255);
				}
			}
		}
	}
	private Drawable mDownDrawable;
	public void onTouchEvent(View v,MotionEvent event) {
		switch(event.getAction()){
		case MotionEvent.ACTION_DOWN:
			isDown=true;
			v.invalidate();
			StateListDrawable d1= getStateListDrawable(v);
			if(d1!=null){
				mDownDrawable=d1.getCurrent();
			}
			break;
		case MotionEvent.ACTION_UP:
			isDown=false;
			StateListDrawable d2= getStateListDrawable(v);
			if(d2!=null&&!d2.getCurrent().equals(mDownDrawable)){
				mDrawable2=d2.getCurrent();
			}
			if(mDrawable2!=null){
				startTime=System.currentTimeMillis();
				v.postInvalidate();
			}
			break;
		}
	}
	public void onPressed(View v,boolean pressed){
		if(pressed){
			isDown=true;
			v.invalidate();
			StateListDrawable d1= getStateListDrawable(v);
			if(d1!=null){
				mDrawable2=d1.getCurrent();
			}
		}else{
			isDown=false;
//			StateListDrawable d2= getStateListDrawable(v);
//			if(d2!=null&&!d2.getCurrent().equals(mDownDrawable)){
//				mDrawable2=d2.getCurrent();
//			}
			if(mDrawable2!=null){
				startTime=System.currentTimeMillis();
				v.postInvalidate();
			}
		}
	}
	private StateListDrawable getStateListDrawable(View v){
		if(v.getBackground() instanceof StateListDrawable){
			return (StateListDrawable)v.getBackground();
		}
		return null;
	}
}

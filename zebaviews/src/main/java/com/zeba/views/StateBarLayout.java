package com.zeba.views;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

public class StateBarLayout extends FrameLayout{

	public StateBarLayout(Context context) {
		super(context);
		init();
	}
	public StateBarLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	public StateBarLayout(Context context, AttributeSet attrs, int defStyleAttr,
			int defStyleRes) {
		super(context, attrs, defStyleAttr);
		init();
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		measureChildren(widthMeasureSpec, heightMeasureSpec);
		int w=MeasureSpec.getSize(widthMeasureSpec);
		int h=MeasureSpec.getSize(heightMeasureSpec);
		h=getStatusBarHeight();
		setMeasuredDimension(w, h);
	}
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
//		super.onLayout(changed, left, top, right, bottom);
		for(int i=0;i<getChildCount();i++){
			View v=getChildAt(i);
			int t=getStatusBarHeight();
			v.layout(0, t,
					v.getMeasuredWidth(), t+v.getMeasuredHeight());
		}
	}
	@Override
	protected void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(canvas);
	}
	private void init(){
		
	}
	public int getStatusBarHeight() {
		if(VERSION.SDK_INT < VERSION_CODES.KITKAT) {
			return 0;
		  }
		  int result = 0;
		  int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
		  if (resourceId > 0) {
		      result = getResources().getDimensionPixelSize(resourceId);
		  }
		  return result;
		}
}

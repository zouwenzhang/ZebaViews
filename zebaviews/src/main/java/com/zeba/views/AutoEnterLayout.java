package com.zeba.views;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class AutoEnterLayout extends ViewGroup {

	private int mVerticalGap = 0;
	private int mHorizontalGap = 0;

	private List<Integer> childOfLine; //Save the count of child views of each line;
	private List<Integer> childList;

	public AutoEnterLayout(Context context) {
		super(context);
	}

	public AutoEnterLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.AutoEnterLayout);
		mHorizontalGap = ta.getDimensionPixelSize(R.styleable.AutoEnterLayout_horizontalGap, 0);
		mVerticalGap = ta.getDimensionPixelSize(R.styleable.AutoEnterLayout_verticalGap, 0);
		ta.recycle();
	}

	public AutoEnterLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.AutoEnterLayout);
		mHorizontalGap = ta.getDimensionPixelSize(R.styleable.AutoEnterLayout_horizontalGap, 0);
		mVerticalGap = ta.getDimensionPixelSize(R.styleable.AutoEnterLayout_verticalGap, 0);
		ta.recycle();
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		layoutWrapContent();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		childOfLine = new ArrayList<>();
		childList=new ArrayList<>();
		int childCount = getChildCount();
		int totalHeight = 0;
		int totalWidth = MeasureSpec.getSize(widthMeasureSpec);
		int curLineChildCount = 0;
		int curLineWidth = 0;
		int maxHeight = 0;
		for (int i = 0; i < childCount; i++) {
			View childItem = getChildAt(i);
			if(childItem.getVisibility()==GONE){
				continue;
			}
			childList.add(i);
			measureChild(childItem, widthMeasureSpec, heightMeasureSpec);
			int childHeight = childItem.getMeasuredHeight();
			int childWidth = childItem.getMeasuredWidth();
			if (curLineWidth + childWidth <= totalWidth) {
				curLineWidth += childWidth;
				maxHeight = Math.max(childHeight, maxHeight);
				curLineChildCount++;
			} else {
				childOfLine.add(curLineChildCount);
				curLineWidth = childWidth;
				curLineChildCount = 1;
				totalHeight += maxHeight;
				maxHeight = childHeight;
			}

		}
		childOfLine.add(curLineChildCount);
		for (int i = childOfLine.size()-1; i>=0; i--) {
			if (childOfLine.get(i) == 0) {
				childOfLine.remove(i);
			}
		}
		totalHeight += (mVerticalGap * (childOfLine.size() - 1) + maxHeight);
		setMeasuredDimension(totalWidth, totalHeight);
	}

	private void layoutWrapContent() {
		int index = 0;
		int curHeight = 0;
		int childIndex=0;
		if(childList.size()==0){
			return;
		}
		for (int i = 0; i < childOfLine.size(); i++) {
			int childCount = childOfLine.get(i);
			int maxHeight = 0;
			int lineWidth = 0;
			int target = index + childCount;
			for ( ; index < target; index++) {
				View item = getChildAt(childList.get(childIndex));
				childIndex++;
				maxHeight = Math.max(maxHeight, item.getMeasuredHeight());
				item.layout(lineWidth, curHeight, lineWidth + item.getMeasuredWidth(), curHeight + item.getMeasuredHeight());
				lineWidth += item.getMeasuredWidth() + mHorizontalGap;
			}
			curHeight += maxHeight + mVerticalGap;
		}
	}

	@Override
	public void addView(View child) {
		super.addView(child);
	}

	public void setHorizontalGap(int horizontalGap) {
		this.mHorizontalGap = horizontalGap;
	}

	public void setVerticalGap(int verticalGap) {
		this.mVerticalGap = verticalGap;
	}

}

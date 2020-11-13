package com.zeba.views;

import android.content.Context;
import android.util.AttributeSet;

import com.zeba.views.click.ShapeFrameLayout;

public class SFrameLayout extends ShapeFrameLayout {

    public SFrameLayout(Context context) {
        this(context,null);
    }

    public SFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }


}

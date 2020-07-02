package com.zeba.views;

import android.content.Context;
import android.util.AttributeSet;

import com.zeba.views.click.ShapeLinearLayout;

public class SLinearLayout extends ShapeLinearLayout {


    public SLinearLayout(Context context) {
        this(context,null);
    }

    public SLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

}

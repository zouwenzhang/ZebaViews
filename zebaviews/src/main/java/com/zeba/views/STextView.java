package com.zeba.views;

import android.content.Context;
import android.util.AttributeSet;

import com.zeba.views.click.ShapeTextView;

public class STextView extends ShapeTextView {

    public STextView(Context context) {
        this(context,null);
    }

    public STextView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public STextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

}

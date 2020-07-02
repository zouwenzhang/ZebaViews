package com.zeba.views;

import android.content.Context;
import android.util.AttributeSet;

import com.zeba.views.click.ShapeImageView;

public class SImageView extends ShapeImageView {

    public SImageView(Context context) {
        this(context,null);
    }

    public SImageView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

}

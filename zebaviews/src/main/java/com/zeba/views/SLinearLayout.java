package com.zeba.views;

import android.content.Context;
import android.util.AttributeSet;

import com.zeba.views.attr.SAttr;
import com.zeba.views.click.ShapeLinearLayout;

public class SLinearLayout extends ShapeLinearLayout {

    private SAttr sAttr;

    public SLinearLayout(Context context) {
        this(context,null);
    }

    public SLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        sAttr=new SAttr(context,attrs);
    }

    public SAttr getSAttr(){
        return sAttr;
    }

}

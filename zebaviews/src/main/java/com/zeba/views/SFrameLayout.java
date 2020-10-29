package com.zeba.views;

import android.content.Context;
import android.util.AttributeSet;

import com.zeba.views.attr.SAttr;
import com.zeba.views.click.ShapeFrameLayout;

public class SFrameLayout extends ShapeFrameLayout {

    private SAttr sAttr;
    public SFrameLayout(Context context) {
        this(context,null);
    }

    public SFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        sAttr=new SAttr(context,attrs);
    }

    public SAttr getSAttr(){
        return sAttr;
    }


}

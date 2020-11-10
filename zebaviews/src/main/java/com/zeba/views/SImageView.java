package com.zeba.views;

import android.content.Context;
import android.util.AttributeSet;

import com.zeba.views.attr.SAttr;
import com.zeba.views.click.ShapeImageView;
import com.zeba.views.databind.ResBinder;
import com.zeba.views.interfaces.SViewer;

public class SImageView extends ShapeImageView implements SViewer {
    private SAttr sAttr;
    private ResBinder resBinder;

    public SImageView(Context context) {
        this(context,null);
    }

    public SImageView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        sAttr=new SAttr(context,attrs);
        resBinder=new ResBinder(this,sAttr);
    }

    public SAttr getSAttr(){
        return sAttr;
    }

    @Override
    public ResBinder getResBinder(){
        return resBinder;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        resBinder.clear();
    }

}

package com.zeba.views.attr;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;

import com.zeba.views.R;

public class SAttrSwitch extends SAttr{
    public int offBgColor;
    public int onBgColor;
    public int fgColor;
    public SAttrSwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SSwitch);
        offBgColor=typedArray.getColor(R.styleable.SSwitch_offBgColor, Color.parseColor("#e8e8e8"));
        onBgColor=typedArray.getColor(R.styleable.SSwitch_onBgColor, Color.parseColor("#3399ff"));
        fgColor=typedArray.getColor(R.styleable.SSwitch_fgColor, Color.parseColor("#ffffff"));
        typedArray.recycle();
    }

    @Override
    public int getOffBgColor() {
        return offBgColor;
    }

    @Override
    public void setOffBgColor(int offBgColor) {
        this.offBgColor = offBgColor;
    }

    @Override
    public int getOnBgColor() {
        return onBgColor;
    }

    @Override
    public void setOnBgColor(int onBgColor) {
        this.onBgColor = onBgColor;
    }

    @Override
    public int getFgColor() {
        return fgColor;
    }

    @Override
    public void setFgColor(int fgColor) {
        this.fgColor = fgColor;
    }
}

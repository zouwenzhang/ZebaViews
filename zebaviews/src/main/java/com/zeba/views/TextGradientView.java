package com.zeba.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

public class TextGradientView extends android.support.v7.widget.AppCompatTextView {

    private int startColor;
    private int endColor;

    public TextGradientView(Context context, AttributeSet attrs) {
        super(context,attrs);
        init(context,attrs);
    }

    public TextGradientView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs){
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TextGradientView);
        startColor= typedArray.getColor(R.styleable.TextGradientView_startColor,0);
        endColor=typedArray.getColor(R.styleable.TextGradientView_endColor,0);
        typedArray.recycle();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if(w>0&&startColor!=0&&endColor!=0&&w!=oldw){
            LinearGradient linearGradient=new LinearGradient(0,0,w,0,new int[]{startColor,endColor},null,Shader.TileMode.REPEAT);
            getPaint().setShader(linearGradient);
        }
    }
}

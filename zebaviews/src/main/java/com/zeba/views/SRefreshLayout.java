package com.zeba.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;

public class SRefreshLayout extends SwipeRefreshLayout {

    private static int[] DefColors;

    public static void setDefColors(int[] colors){
        DefColors=colors;
    }

    public SRefreshLayout(@NonNull Context context) {
        super(context);
        init(context,null);
    }

    public SRefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    private void init(Context context,AttributeSet attrs){
        if(attrs!=null){
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SRefreshLayout);
            String colors=typedArray.getString(R.styleable.SRefreshLayout_schemeColors);
            try{
                if(colors!=null&&!"".equals(colors)){
                    if(colors.contains(";")){
                        String[] cs=colors.split(";");
                        int[] css=new int[cs.length];
                        for(int i=0;i<cs.length;i++){
                            css[i]=Color.parseColor(cs[i]);
                        }
                        setColorSchemeColors(css);
                    }else{
                        int color= Color.parseColor(colors);
                        setColorSchemeColors(new int[]{color});
                    }
                }else if(DefColors!=null){
                    setColorSchemeColors(DefColors);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            typedArray.recycle();
        }else{
            if(DefColors!=null){
                setColorSchemeColors(DefColors);
            }
        }
    }
}

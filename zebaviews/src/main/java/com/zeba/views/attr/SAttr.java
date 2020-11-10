package com.zeba.views.attr;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.zeba.views.R;
import com.zeba.views.css.CSSFormat;

import java.util.HashMap;
import java.util.Map;

public class SAttr {
    private Map<String,String> attrMap=new HashMap<>();
    private int strokeWidth;
    private int strokeColor;
    private float roundRadius;
    private int defaultColor;
    private float topLeftRadius;
    private float topRightRadius;
    private float bottomLeftRadius;
    private float bottomRightRadius;
    private int pressedColor;
    private Map<String,String> line;
    private Map<String,String> sweep;
    private Map<String,String> circle;
    private Map<String,String> shadow;
    private Map<String,String> svg;

    private int showType;
    private float scaleTo;
    private int scaleTime=100;
    private float alphaTo;
    private int alphaTime=100;
    public SAttr(Context context,AttributeSet attrs){
        if(attrs==null){
            line=new HashMap<>();
            sweep=new HashMap<>();
            circle=new HashMap<>();
            shadow=new HashMap<>();
            svg=new HashMap<>();
        }
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ShapeTextView);
        pressedColor = typedArray.getColor(R.styleable.ShapeTextView_pressedColor, 0);
        strokeWidth = typedArray.getDimensionPixelOffset(R.styleable.ShapeTextView_strokeWidth, 0);
        strokeColor = typedArray.getColor(R.styleable.ShapeTextView_strokeColor, 0);
        defaultColor = typedArray.getColor(R.styleable.ShapeTextView_defaultColor, 0);
        roundRadius = typedArray.getDimensionPixelOffset(R.styleable.ShapeTextView_roundRadius, 0);
        topLeftRadius = typedArray.getDimensionPixelOffset(R.styleable.ShapeTextView_topLeftRadius, 0);
        topRightRadius = typedArray.getDimensionPixelOffset(R.styleable.ShapeTextView_topRightRadius, 0);
        bottomLeftRadius = typedArray.getDimensionPixelOffset(R.styleable.ShapeTextView_bottomLeftRadius, 0);
        bottomRightRadius = typedArray.getDimensionPixelOffset(R.styleable.ShapeTextView_bottomRightRadius, 0);
        String type=typedArray.getString(R.styleable.ShapeTextView_showType);
        String style=typedArray.getString(R.styleable.ShapeTextView_css);
        attrMap.put("css",style);
        String anim=typedArray.getString(R.styleable.ShapeTextView_anim);
        attrMap.put("anim",anim);
        String ttf=typedArray.getString(R.styleable.ShapeTextView_ttf);
        attrMap.put("ttf",ttf);
        String lines=typedArray.getString(R.styleable.ShapeTextView_gradientLine);
        line= CSSFormat.form(lines);
        String sweeps=typedArray.getString(R.styleable.ShapeTextView_gradientSweep);
        sweep= CSSFormat.form(sweeps);
        String circles=typedArray.getString(R.styleable.ShapeTextView_gradientCircle);
        circle=CSSFormat.form(circles);
        String shadows=typedArray.getString(R.styleable.ShapeTextView_shadow);
        shadow=CSSFormat.form(shadows);
        String svgs=typedArray.getString(R.styleable.ShapeTextView_svg);
        svg=CSSFormat.form(svgs);
        attrMap.put("fieldName",typedArray.getString(R.styleable.ShapeTextView_fieldName));
        attrMap.put("bg",typedArray.getString(R.styleable.ShapeTextView_bg));
        attrMap.put("img",typedArray.getString(R.styleable.ShapeTextView_img));
        attrMap.put("imgLeft",typedArray.getString(R.styleable.ShapeTextView_imgLeft));
        attrMap.put("imgTop",typedArray.getString(R.styleable.ShapeTextView_imgTop));
        attrMap.put("imgRight",typedArray.getString(R.styleable.ShapeTextView_imgRight));
        attrMap.put("imgBottom",typedArray.getString(R.styleable.ShapeTextView_imgBottom));
        attrMap.put("pageName",typedArray.getString(R.styleable.ShapeTextView_pageName));
        attrMap.put("pageCode",typedArray.getString(R.styleable.ShapeTextView_pageCode));
        attrMap.put("pageText",typedArray.getString(R.styleable.ShapeTextView_pageText));
        attrMap.put("pageHint",typedArray.getString(R.styleable.ShapeTextView_pageHint));
        initShowType(type);
        scaleTo=typedArray.getFloat(R.styleable.ShapeTextView_scaleTo,0.95f);
        scaleTime=typedArray.getInteger(R.styleable.ShapeTextView_scaleTime,100);
        alphaTo=typedArray.getFloat(R.styleable.ShapeTextView_alphaTo,0.7f);
        alphaTime=typedArray.getInteger(R.styleable.ShapeTextView_alphaTime,200);
        typedArray.recycle();
    }

    private void initShowType(String type){
        if("none".equals(type)||"0".equals(type)){
            showType=0;
        }else if("scale".equals(type)||"1".equals(type)){
            showType=1;
        }else if("alpha".equals(type)||"2".equals(type)){
            showType=2;
        }else if("ripple".equals(type)||"3".equals(type)){
            showType=3;
        }
    }

    public String getBg(){
        return attrMap.get("bg");
    }

    public String getImg(){
        return attrMap.get("img");
    }

    public String getImgLeft(){
        return attrMap.get("imgLeft");
    }

    public String getImgTop(){
        return attrMap.get("imgTop");
    }

    public String getImgRight(){
        return attrMap.get("imgRight");
    }

    public String getImgBottom(){
        return attrMap.get("imgBottom");
    }

    public String getFieldName(){
        return attrMap.get("fieldName");
    }

    public String getPageName(){
        return attrMap.get("pageName");
    }
    public String getPageCode(){
        return attrMap.get("pageCode");
    }
    public String getPageText(){
        return attrMap.get("pageText");
    }
    public String getPageHint(){
        return attrMap.get("pageHint");
    }
}

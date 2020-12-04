package com.zeba.views.attr;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.zeba.views.R;
import com.zeba.views.css.CSSFormat;

import java.util.HashMap;
import java.util.Map;

public class SAttr {
    public int strokeWidth;
    public int strokeColor;
    public float roundRadius;
    public int defaultColor;
    public float topLeftRadius;
    public float topRightRadius;
    public float bottomLeftRadius;
    public float bottomRightRadius;
    public int pressedColor;
    public int loadingColor;
    public String css;
    public String anim;
    public String ttf;
    public String fieldName;
    public String bg;
    public String img;
    public String imgLeft;
    public String imgTop;
    public String imgRight;
    public String imgBottom;
    public String pageName;
    public String pageCode;
    public String pageText;
    public String pageHint;
    public Map<String,String> line;
    public Map<String,String> sweep;
    public Map<String,String> circle;
    public Map<String,String> shadow;
    public Map<String,String> svg;

    public int showType;
    public float scaleTo=0.95f;
    public int scaleTime=100;
    public float alphaTo=0.7f;
    public int alphaTime=200;
    public SAttr(Context context,AttributeSet attrs){
        if(attrs==null){
            line=new HashMap<>();
            sweep=new HashMap<>();
            circle=new HashMap<>();
            shadow=new HashMap<>();
            svg=new HashMap<>();
            return;
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
        setShowType(type);
        css=typedArray.getString(R.styleable.ShapeTextView_css);
        anim=typedArray.getString(R.styleable.ShapeTextView_anim);
        ttf=typedArray.getString(R.styleable.ShapeTextView_ttf);
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
        fieldName=typedArray.getString(R.styleable.ShapeTextView_fieldName);
        bg=typedArray.getString(R.styleable.ShapeTextView_bg);
        img=typedArray.getString(R.styleable.ShapeTextView_img);
        imgLeft=typedArray.getString(R.styleable.ShapeTextView_imgLeft);
        imgTop=typedArray.getString(R.styleable.ShapeTextView_imgTop);
        imgRight=typedArray.getString(R.styleable.ShapeTextView_imgRight);
        imgBottom=typedArray.getString(R.styleable.ShapeTextView_imgBottom);
        pageName=typedArray.getString(R.styleable.ShapeTextView_pageName);
        pageCode=typedArray.getString(R.styleable.ShapeTextView_pageCode);
        pageText=typedArray.getString(R.styleable.ShapeTextView_pageText);
        pageHint=typedArray.getString(R.styleable.ShapeTextView_pageHint);
        scaleTo=typedArray.getFloat(R.styleable.ShapeTextView_scaleTo,0.95f);
        scaleTime=typedArray.getInteger(R.styleable.ShapeTextView_scaleTime,100);
        alphaTo=typedArray.getFloat(R.styleable.ShapeTextView_alphaTo,0.7f);
        alphaTime=typedArray.getInteger(R.styleable.ShapeTextView_alphaTime,200);
        onAttr(attrs,typedArray);
        typedArray.recycle();
    }

    public void onAttr(AttributeSet attrs,TypedArray typedArray){

    }

    public boolean hasDrawable(){
        if(strokeColor!=0||roundRadius!=0||defaultColor!=0){
            return true;
        }
        if(line!=null&&!line.isEmpty()){
            return true;
        }
        if(sweep!=null&&!sweep.isEmpty()){
            return true;
        }
        if(circle!=null&&!circle.isEmpty()){
            return true;
        }
        if(shadow!=null&&!shadow.isEmpty()){
            return true;
        }
        return false;
    }

    public void setShowType(String type){
        if("scale".equals(type)||"1".equals(type)){
            showType=1;
        }else if("alpha".equals(type)||"2".equals(type)){
            showType=2;
        }else if("ripple".equals(type)||"3".equals(type)){
            showType=3;
        }else{
            showType=0;
        }
    }

    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public void setStrokeColor(int strokeColor) {
        this.strokeColor = strokeColor;
    }

    public void setRoundRadius(float roundRadius) {
        this.roundRadius = roundRadius;
    }

    public void setDefaultColor(int defaultColor) {
        this.defaultColor = defaultColor;
    }

    public void setTopLeftRadius(float topLeftRadius) {
        this.topLeftRadius = topLeftRadius;
    }

    public void setTopRightRadius(float topRightRadius) {
        this.topRightRadius = topRightRadius;
    }

    public void setBottomLeftRadius(float bottomLeftRadius) {
        this.bottomLeftRadius = bottomLeftRadius;
    }

    public void setBottomRightRadius(float bottomRightRadius) {
        this.bottomRightRadius = bottomRightRadius;
    }

    public void setPressedColor(int pressedColor) {
        this.pressedColor = pressedColor;
    }

    public void setLoadingColor(int loadingColor) {
        this.loadingColor = loadingColor;
    }

    public void setCss(String css) {
        this.css = css;
    }

    public void setAnim(String anim) {
        this.anim = anim;
    }

    public void setTtf(String ttf) {
        this.ttf = ttf;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public void setBg(String bg) {
        this.bg = bg;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setImgLeft(String imgLeft) {
        this.imgLeft = imgLeft;
    }

    public void setImgTop(String imgTop) {
        this.imgTop = imgTop;
    }

    public void setImgRight(String imgRight) {
        this.imgRight = imgRight;
    }

    public void setImgBottom(String imgBottom) {
        this.imgBottom = imgBottom;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public void setPageCode(String pageCode) {
        this.pageCode = pageCode;
    }

    public void setPageText(String pageText) {
        this.pageText = pageText;
    }

    public void setPageHint(String pageHint) {
        this.pageHint = pageHint;
    }

    public void setLine(Map<String, String> line) {
        this.line = line;
    }

    public void setSweep(Map<String, String> sweep) {
        this.sweep = sweep;
    }

    public void setCircle(Map<String, String> circle) {
        this.circle = circle;
    }

    public void setShadow(Map<String, String> shadow) {
        this.shadow = shadow;
    }

    public void setSvg(Map<String, String> svg) {
        this.svg = svg;
    }

    public void setScaleTo(float scaleTo) {
        this.scaleTo = scaleTo;
    }

    public void setScaleTime(int scaleTime) {
        this.scaleTime = scaleTime;
    }

    public void setAlphaTo(float alphaTo) {
        this.alphaTo = alphaTo;
    }

    public void setAlphaTime(int alphaTime) {
        this.alphaTime = alphaTime;
    }

    public int getOffBgColor() {
        return 0;
    }

    public void setOffBgColor(int offBgColor) {
    }

    public int getOnBgColor() {
        return 0;
    }

    public void setOnBgColor(int onBgColor) {
    }

    public int getFgColor() {
        return 0;
    }

    public void setFgColor(int fgColor) {
    }
}

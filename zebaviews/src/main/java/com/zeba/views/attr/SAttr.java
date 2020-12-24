package com.zeba.views.attr;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.zeba.views.R;

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
    public String gLine;
    public String gSweep;
    public String gCircle;
    public String shadow;
    public String svg;

    public int showType;
    public float scaleTo=0.95f;
    public int scaleTime=100;
    public float alphaTo=0.7f;
    public int alphaTime=200;
    public SAttr(Context context,AttributeSet attrs){
        if(attrs==null){
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
        gLine =typedArray.getString(R.styleable.ShapeTextView_gradientLine);
        gSweep=typedArray.getString(R.styleable.ShapeTextView_gradientSweep);
        gCircle =typedArray.getString(R.styleable.ShapeTextView_gradientCircle);
        shadow=typedArray.getString(R.styleable.ShapeTextView_shadow);
        svg=typedArray.getString(R.styleable.ShapeTextView_svg);
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
        if(gLine !=null&&!gLine.isEmpty()){
            return true;
        }
        if(gSweep!=null&&!gSweep.isEmpty()){
            return true;
        }
        if(gCircle !=null&&!gCircle.isEmpty()){
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

    public void setGLine(String line) {
        this.gLine = line;
    }

    public void setGSweep(String sweep) {
        this.gSweep = sweep;
    }

    public void setGCircle(String circle) {
        this.gCircle = circle;
    }

    public void setShadow(String shadow) {
        this.shadow = shadow;
    }

    public void setSvg(String svg) {
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

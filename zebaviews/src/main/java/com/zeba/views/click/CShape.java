package com.zeba.views.click;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.zeba.views.R;

import java.lang.ref.WeakReference;

public class CShape {
    private int strokeWidth;
    private int strokeColor;
    private float roundRadius;
    private int defaultColor;
    private float topLeftRadius;
    private float topRightRadius;
    private float bottomLeftRadius;
    private float bottomRightRadius;
    private int pressedColor;

    private int showType;
    private float scaleTo;
    private int scaleTime=100;
    private float alphaTo;
    private int alphaTime=100;

    private WeakReference<ViewClickHelper> wrHelper;

//    public CShape(ViewClickHelper helper, Context context, AttributeSet attrs){
//        wrHelper=new WeakReference<>(helper);
//        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ShapeButton);
//        pressedColor = typedArray.getColor(R.styleable.ShapeButton_pressedColor, 0);
//        strokeWidth = typedArray.getDimensionPixelOffset(R.styleable.ShapeButton_strokeWidth, 0);
//        strokeColor = typedArray.getColor(R.styleable.ShapeButton_strokeColor, 0);
//        defaultColor = typedArray.getColor(R.styleable.ShapeButton_defaultColor, 0);
//        roundRadius = typedArray.getDimensionPixelOffset(R.styleable.ShapeButton_roundRadius, 0);
//        topLeftRadius = typedArray.getDimensionPixelOffset(R.styleable.ShapeButton_topLeftRadius, 0);
//        topRightRadius = typedArray.getDimensionPixelOffset(R.styleable.ShapeButton_topRightRadius, 0);
//        bottomLeftRadius = typedArray.getDimensionPixelOffset(R.styleable.ShapeButton_bottomLeftRadius, 0);
//        bottomRightRadius = typedArray.getDimensionPixelOffset(R.styleable.ShapeButton_bottomRightRadius, 0);
//        typedArray.recycle();
//        typedArray = context.obtainStyledAttributes(attrs, R.styleable.ClickShowType);
//        showType= typedArray.getInt(R.styleable.ClickShowType_showType,0);
//        scaleTo=typedArray.getFloat(R.styleable.ClickShowType_scaleTo,0.9f);
//        scaleTime=typedArray.getInteger(R.styleable.ClickShowType_scaleTime,120);
//        typedArray.recycle();
//    }

    public CShape(ViewClickHelper helper){
        wrHelper=new WeakReference<>(helper);
    }

    protected void init(Context context, AttributeSet attrs){
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
        initShowType(type);
//        showType= typedArray.getInteger(R.styleable.ShapeTextView_showType,0);
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

    public void update(){
        if(wrHelper!=null&&wrHelper.get()!=null){
            wrHelper.get().setDrawable();
        }
    }

    public int getStrokeWidth() {
        return strokeWidth;
    }

    public CShape strokeWidth(int strokeWidth){
        setStrokeWidth(strokeWidth);
        return this;
    }

    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public int getStrokeColor() {
        return strokeColor;
    }

    public CShape strokeColor(int strokeColor){
        setStrokeColor(strokeColor);
        return this;
    }

    public void setStrokeColor(int strokeColor) {
        this.strokeColor = strokeColor;
    }

    public float getRoundRadius() {
        return roundRadius;
    }

    public CShape roundRadius(float roundRadius){
        setRoundRadius(roundRadius);
        return this;
    }

    public void setRoundRadius(float roundRadius) {
        this.roundRadius = roundRadius;
    }

    public int getDefaultColor() {
        return defaultColor;
    }

    public CShape defaultColor(int color){
        setDefaultColor(color);
        return this;
    }

    public void setDefaultColor(int defaultColor) {
        this.defaultColor = defaultColor;
    }

    public float getTopLeftRadius() {
        return topLeftRadius;
    }

    public CShape topLeftRadius(float radius){
        setTopLeftRadius(radius);
        return this;
    }

    public void setTopLeftRadius(float topLeftRadius) {
        this.topLeftRadius = topLeftRadius;
    }

    public float getTopRightRadius() {
        return topRightRadius;
    }

    public CShape topRightRadius(float radius){
        setTopRightRadius(radius);
        return this;
    }
    public void setTopRightRadius(float topRightRadius) {
        this.topRightRadius = topRightRadius;
    }

    public float getBottomLeftRadius() {
        return bottomLeftRadius;
    }

    public CShape bottomLeftRadius(float radius){
        setBottomLeftRadius(radius);
        return this;
    }

    public void setBottomLeftRadius(float bottomLeftRadius) {
        this.bottomLeftRadius = bottomLeftRadius;
    }

    public float getBottomRightRadius() {
        return bottomRightRadius;
    }

    public CShape bottomRightRadius(float radius){
        setBottomRightRadius(radius);
        return this;
    }

    public void setBottomRightRadius(float bottomRightRadius) {
        this.bottomRightRadius = bottomRightRadius;
    }

    public int getPressedColor() {
        return pressedColor;
    }

    public CShape pressedColor(int color){
        setPressedColor(color);
        return this;
    }

    public void setPressedColor(int pressedColor) {
        this.pressedColor = pressedColor;
    }

    public int getShowType() {
        return showType;
    }

    public CShape showNone(){
        setShowType(0);
        return this;
    }

    public CShape showScale(){
        setShowType(1);
        return this;
    }

    public CShape showRipple(){
        setShowType(3);
        return this;
    }

    public void setShowType(int showType) {
        this.showType = showType;
    }

    public float getScaleTo() {
        return scaleTo;
    }

    public void setScaleTo(float scaleTo) {
        this.scaleTo = scaleTo;
    }

    public int getScaleTime() {
        return scaleTime;
    }

    public void setScaleTime(int scaleTime) {
        this.scaleTime = scaleTime;
    }

    public float getAlphaTo() {
        return alphaTo;
    }

    public void setAlphaTo(int alphaTo) {
        this.alphaTo = alphaTo;
    }

    public int getAlphaTime() {
        return alphaTime;
    }

    public void setAlphaTime(int alphaTime) {
        this.alphaTime = alphaTime;
    }
}

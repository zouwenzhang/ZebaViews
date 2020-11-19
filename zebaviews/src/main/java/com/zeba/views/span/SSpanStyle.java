package com.zeba.views.span;

import android.content.Context;
import android.graphics.drawable.Drawable;

public class SSpanStyle {
    private String text;
    private Integer textColor;
    private Integer bColor;
    private Integer textSize;
    private boolean underLine=false;
    private boolean bold=false;
    private int type;
    private Drawable img;

    public SSpanStyle underLine(){
        underLine=true;
        return this;
    }

    public SSpanStyle bold(){
        bold=true;
        return this;
    }

    public SSpanStyle text(String text){
        this.text=text;
        return this;
    }

    public SSpanStyle type(int type){
        this.type=type;
        return this;
    }

    public SSpanStyle img(Drawable imgRes){
        this.img =imgRes;
        img.setBounds(0, 0, img.getIntrinsicWidth(),
                img.getIntrinsicHeight());
        return this;
    }

    public SSpanStyle textColor(int color){
        textColor=color;
        return this;
    }

    public SSpanStyle bColor(int color){
        bColor=color;
        return this;
    }

    public SSpanStyle textSize(Context context, int size){
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        float fs=size*fontScale;
        float density = context.getResources().getDisplayMetrics().density;
        textSize=(int) (fs / density + 0.5f);
        return this;
    }

    public boolean isBold() {
        return bold;
    }

    public void setBold(boolean bold) {
        this.bold = bold;
    }

    public boolean isUnderLine() {
        return underLine;
    }

    public void setUnderLine(boolean underLine) {
        this.underLine = underLine;
    }

    public int getType(){
        return type;
    }

    public String getText() {
        return text;
    }

    public Drawable getImg(){
        return img;
    }

    public Integer getTextColor() {
        return textColor;
    }

    public Integer getBColor() {
        return bColor;
    }

    public Integer getTextSize() {
        return textSize;
    }

    public static SSpanStyle textStyle(String text){
        return new SSpanStyle().text(text).type(1);
    }

    public static SSpanStyle imgStyle(Context context, int resId){
        Drawable drawable=context.getResources().getDrawable(resId);
        return new SSpanStyle().img(drawable).type(2).text("@IMG");
    }
}

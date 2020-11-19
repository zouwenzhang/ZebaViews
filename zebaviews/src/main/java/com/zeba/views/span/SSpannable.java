package com.zeba.views.span;

import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;

import java.util.ArrayList;
import java.util.List;

public class SSpannable {

    private List<SSpanStyle> styleList=new ArrayList<>();
    private StringBuilder textBuilder=new StringBuilder();

    public SSpannable add(SSpanStyle style){
        styleList.add(style);
        textBuilder.append(style.getText());
        return this;
}

    public SpannableString create(){
        SpannableString ss = new SpannableString(textBuilder.toString());
        int start=0;
        for(int i=0;i<styleList.size();i++){
            SSpanStyle style=styleList.get(i);
            int end=start+style.getText().length();
            if(style.getType()==1){
                if(style.getTextColor()!=null){
                    ForegroundColorSpan span = new ForegroundColorSpan(style.getTextColor());
                    ss.setSpan(span,start,end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                }
                if(style.getBColor()!=null){
                    BackgroundColorSpan span = new BackgroundColorSpan(style.getBColor());
                    ss.setSpan(span,start,end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                }
                if(style.getTextSize()!=null){
                    AbsoluteSizeSpan absoluteSizeSpan = new AbsoluteSizeSpan(style.getTextSize(), true);
                    ss.setSpan(absoluteSizeSpan, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                }
                if(style.isUnderLine()){
                    UnderlineSpan underlineSpan=new UnderlineSpan();
                    ss.setSpan(underlineSpan,start,end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                }
                if(style.isBold()){
                    try{
                        StyleSpan styleSpan=new StyleSpan(Typeface.BOLD);
                        ss.setSpan(styleSpan,start,end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    }catch (NoSuchMethodError e){
                        e.printStackTrace();
                    }
                }

            }else if(style.getType()==2){
                ImageSpan imageSpan = new ImageSpan(style.getImg(), ImageSpan.ALIGN_BASELINE);
                ss.setSpan(imageSpan, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            }
            start=end;
        }
        return ss;
    }

}

package com.zeba.views.css;

import android.graphics.Color;
import android.widget.TextView;

import com.zeba.views.span.SSpanStyle;
import com.zeba.views.span.SSpannable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpanCSS {
    private List<Map<String,String>> spanList=new ArrayList<>();
    private Map<String,String> textMap;

    public void setSpan(TextView tv){
        SSpannable sSpannable=new SSpannable();
        for(Map<String,String> map:spanList){
            SSpanStyle style=SSpanStyle.textStyle(map.get("text"));
            if(map.containsKey("color")){
                style.textColor(Color.parseColor(map.get("color")));
            }
            if(map.containsKey("size")){
                style.textSize(tv.getContext(),Integer.parseInt(map.get("size")));
            }
            if(map.containsKey("bold")){
                style.setBold(true);
            }
            if(map.containsKey("underLine")){
                style.setUnderLine(true);
            }
            sSpannable.add(style);
        }
        tv.setText(sSpannable.create());
    }

    public SpanCSS text(String text){
        textMap=new HashMap<>();
        textMap.put("text",text);
        spanList.add(textMap);
        return this;
    }

    public SpanCSS color(String value){
        textMap.put("color",value);
        return this;
    }

    public SpanCSS size(int value){
        textMap.put("size",value+"");
        return this;
    }

    public SpanCSS bold(){
        textMap.put("bold","1");
        return this;
    }

    public SpanCSS underLine(){
        textMap.put("underLine","1");
        return this;
    }
}

package com.zeba.views.css;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class TypeFaceManager {

    private static Map<String,Typeface> cacheMap=new HashMap<>();

    public static Typeface get(Context context,String name){
        if(!cacheMap.containsKey(name)){
            Typeface tf=Typeface.createFromAsset(context.getAssets(),"ttf/"+name+".ttf");
            cacheMap.put(name,tf);
        }
        return cacheMap.get(name);
    }

    public static void initTypeFace(Context context, TextView tv,String ttf){
        if(ttf!=null&&ttf.length()!=0){
            if(tv.getTypeface().isBold()){
                tv.setTypeface(TypeFaceManager.get(context,ttf), Typeface.BOLD);
            }else{
                tv.setTypeface(TypeFaceManager.get(context,ttf), Typeface.NORMAL);
            }
        }
    }
}

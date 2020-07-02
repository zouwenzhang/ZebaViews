package com.zeba.views.utils;

import android.graphics.Color;

import java.util.HashMap;
import java.util.Map;

public class CSSFormat {

    public static Map<String,String> form(String css){
        Map<String,String> map=new HashMap<>();
        if(css!=null&&css.length()!=0){
            css=css.replaceAll(" ","");
            String[] ps=css.split(";");
            for(String p:ps){
                if(p.contains(":")){
                    String[] pp=p.split(":");
                    map.put(pp[0],pp[1]);
                }
            }
        }
        return map;
    }

    public static int[] toColors(String colors){
        String[] cs=colors.split(",");
        int[] css=new int[cs.length];
        for(int i=0;i<cs.length;i++){
            css[i]= Color.parseColor(cs[i]);
        }
        return css;
    }
}

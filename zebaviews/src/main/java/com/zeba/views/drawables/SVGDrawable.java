package com.zeba.views.drawables;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Build;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.view.View;

import com.zeba.views.utils.DpSpUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class SVGDrawable {
    private Drawable vector;
    private Map<String,String> map;
    private int w;
    private int h;
    private String g;
    private int m;

    public SVGDrawable(Context context, Map<String,String> info){
        InputStream is= null;
        map=info;
        try {
            is = context.getAssets().open("svg/"+info.get("n")+".svg");
            if(Build.VERSION.SDK_INT>=21){
                vector=VectorDrawable.createFromStream(is,"src");
            }else{
                vector=VectorDrawableCompat.createFromStream(is,"src");
            }
            if(info.get("w")!=null){
                w= DpSpUtil.dip2px(context,Float.parseFloat(info.get("w")));
            }
            if(info.get("h")!=null){
                h= DpSpUtil.dip2px(context,Float.parseFloat(info.get("h")));
            }
            if(info.get("m")!=null){
                m=DpSpUtil.dip2px(context,Float.parseFloat(info.get("m")));
            }
            g=info.get("g");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Drawable drawable(){
        return vector;
    }

    public void setBounds(View v){
        int vw=v.getMeasuredWidth();
        int vh=v.getMeasuredHeight();
        if(w==0){
            w=vw;
        }
        if(h==0){
            h=vh;
        }
        if(g!=null){
            g="l";
        }
        if("l".equals(g)){
            vector.setBounds(m,(vh-h)/2,m+w,(vh-h)/2-(h/2));
        }else if("t".equals(g)){
            vector.setBounds((vw-w)/2,m,(vw-w)/2-(w/2),m+h);
        }else if("r".equals(g)){
            vector.setBounds(vw-m-w,(vh-h)/2,vw-w,(vh-h)/2-(h/2));
        }else if("b".equals(g)){
            vector.setBounds((vw-w)/2,vh-m-h,(vw-w)/2-(w/2),vh-m);
        }
    }

}

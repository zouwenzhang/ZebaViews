package com.zeba.views.css;

import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zeba.views.attr.SAttr;
import com.zeba.views.utils.DpSpUtil;

import java.util.Map;

public class StyleCSS {

    private Map<String,String> sMap;

    public void setCSS(View v, SAttr attr){
        sMap=CSSFormat.form(attr.css);
        if(sMap.get("p")!=null){
            int pp=dp(v,"p");
            v.setPadding(pp,pp,pp,pp);
        }else{
            int[] pd=new int[4];
            pd[0]=v.getPaddingLeft();pd[1]=v.getPaddingTop();pd[2]=v.getPaddingRight();pd[3]=v.getPaddingBottom();
            boolean find=false;
            if(sMap.get("pl")!=null){
                pd[0]=dp(v,"pl");
                find=true;
            }
            if(sMap.get("pt")!=null){
                pd[1]=dp(v,"pt");
                find=true;
            }
            if(sMap.get("pr")!=null){
                pd[2]=dp(v,"pr");
                find=true;
            }
            if(sMap.get("pb")!=null){
                pd[3]=dp(v,"pb");
                find=true;
            }
            if(find){
                v.setPadding(pd[0],pd[1],pd[2],pd[3]);
            }
        }
        if(sMap.get("si")!=null&&v instanceof TextView){
            TextView tv=(TextView) v;
            tv.setTextSize(Float.parseFloat(sMap.get("si")));
        }
        if(sMap.get("o")!=null&&v instanceof LinearLayout){
            LinearLayout tv=(LinearLayout) v;
            tv.setOrientation("v".equals(sMap.get("o"))?LinearLayout.VERTICAL:LinearLayout.HORIZONTAL);
        }
        if(sMap.get("g")!=null){
            if(v instanceof LinearLayout){
                LinearLayout tv=(LinearLayout) v;
                tv.setGravity(getG("g"));
            }else if(v instanceof TextView){
                TextView tv=(TextView) v;
                tv.setGravity(getG("g"));
            }
        }
        if(sMap.get("r")!=null){
            attr.roundRadius=dp(v,"r");
        }else{
            if(sMap.get("rtl")!=null){
                attr.topLeftRadius=dp(v,"rtl");
            }
            if(sMap.get("rtr")!=null){
                attr.topRightRadius=dp(v,"rtr");
            }
            if(sMap.get("rbl")!=null){
                attr.bottomLeftRadius=dp(v,"rbl");
            }
            if(sMap.get("rbr")!=null){
                attr.bottomRightRadius=dp(v,"rbr");
            }
        }
        if(sMap.get("sw")!=null){
            attr.strokeWidth=dp(v,"sw");
        }
        if(sMap.get("v")!=null){
            v.setVisibility("1".equals(sMap.get("v"))?View.VISIBLE:View.GONE);
        }
    }

    public void initFinish(View v){
        if(v.getLayoutParams()==null){
            return;
        }
        if(sMap.get("lg")!=null){
            if(v.getLayoutParams() instanceof LinearLayout.LayoutParams){
                LinearLayout.LayoutParams lp=(LinearLayout.LayoutParams)v.getLayoutParams();
                lp.gravity=getG("lg");
            }else if(v.getLayoutParams() instanceof FrameLayout.LayoutParams){
                FrameLayout.LayoutParams lp=(FrameLayout.LayoutParams)v.getLayoutParams();
                lp.gravity=getG("lg");
            }
        }
        if(sMap.get("m")!=null){
            int m=dp(v,"m");
            if(v.getLayoutParams() instanceof LinearLayout.LayoutParams){
                LinearLayout.LayoutParams lp=(LinearLayout.LayoutParams)v.getLayoutParams();
                lp.leftMargin=m;lp.topMargin=m;lp.rightMargin=m;lp.bottomMargin=m;
            }else if(v.getLayoutParams() instanceof FrameLayout.LayoutParams){
                FrameLayout.LayoutParams lp=(FrameLayout.LayoutParams)v.getLayoutParams();
                lp.leftMargin=m;lp.topMargin=m;lp.rightMargin=m;lp.bottomMargin=m;
            }
            v.setLayoutParams(v.getLayoutParams());
        }else{
            int[] ms=new int[4];
            if(v.getLayoutParams() instanceof LinearLayout.LayoutParams){
                LinearLayout.LayoutParams lp=(LinearLayout.LayoutParams)v.getLayoutParams();
                ms[0]=lp.leftMargin;ms[1]=lp.topMargin;ms[2]=lp.rightMargin;ms[3]=lp.bottomMargin;
            }else if(v.getLayoutParams() instanceof FrameLayout.LayoutParams){
                FrameLayout.LayoutParams lp=(FrameLayout.LayoutParams)v.getLayoutParams();
                ms[0]=lp.leftMargin;ms[1]=lp.topMargin;ms[2]=lp.rightMargin;ms[3]=lp.bottomMargin;
            }
            boolean find=false;
            if(sMap.get("ml")!=null){
                ms[0]=dp(v,"ml");
                find=true;
            }
            if(sMap.get("mt")!=null){
                ms[1]=dp(v,"mt");
                find=true;
            }
            if(sMap.get("mr")!=null){
                ms[2]=dp(v,"mr");
                find=true;
            }
            if(sMap.get("mb")!=null){
                ms[3]=dp(v,"mb");
                find=true;
            }
            if(find){
                if(v.getLayoutParams() instanceof LinearLayout.LayoutParams){
                    LinearLayout.LayoutParams lp=(LinearLayout.LayoutParams)v.getLayoutParams();
                    lp.leftMargin=ms[0];lp.topMargin=ms[1];lp.rightMargin=ms[2];lp.bottomMargin=ms[3];
                }else if(v.getLayoutParams() instanceof FrameLayout.LayoutParams){
                    FrameLayout.LayoutParams lp=(FrameLayout.LayoutParams)v.getLayoutParams();
                    lp.leftMargin=ms[0];lp.topMargin=ms[1];lp.rightMargin=ms[2];lp.bottomMargin=ms[3];
                }
                v.setLayoutParams(v.getLayoutParams());
            }
        }
    }

    private int dp(View v,String key){
        return DpSpUtil.dip2px(v.getContext(),Float.parseFloat(sMap.get(key)));
    }

    private int getG(String key){
        if("c".equals(sMap.get(key))){
            return Gravity.CENTER;
        }else if("cv".equals(sMap.get(key))){
            return Gravity.CENTER_VERTICAL;
        }else if("ch".equals(sMap.get(key))){
            return Gravity.CENTER_HORIZONTAL;
        }else if("l".equals(sMap.get(key))){
            return Gravity.LEFT;
        }else if("t".equals(sMap.get(key))){
            return Gravity.TOP;
        }else if("r".equals(sMap.get(key))){
            return Gravity.RIGHT;
        }else if("b".equals(sMap.get(key))){
            return Gravity.BOTTOM;
        }else if("r".equals(sMap.get(key))){
            return Gravity.RIGHT;
        }else if("cv|l".equals(sMap.get(key))){
            return Gravity.CENTER_VERTICAL|Gravity.LEFT;
        }else if("cv|r".equals(sMap.get(key))){
            return Gravity.CENTER_VERTICAL|Gravity.RIGHT;
        }else if("ch|t".equals(sMap.get(key))){
            return Gravity.CENTER_HORIZONTAL|Gravity.TOP;
        }else if("ch|b".equals(sMap.get(key))){
            return Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM;
        }
        return Gravity.LEFT;
    }

}

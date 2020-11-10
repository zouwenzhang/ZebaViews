package com.zeba.views.databind;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zeba.views.attr.SAttr;
import com.zeba.views.interfaces.ResBinderCallBack;

public class ResBinder {

    private View view;
    private SAttr attr;
    private String pageName;
    private String pageCode;
    private ResBinderCallBack resBinderCallBack;

    public ResBinder(View view,SAttr attr){
        this.view=view;
        pageName=attr.getPageName();
        pageCode=attr.getPageCode();
        this.attr=attr;
    }

    public void initRes(){
        if(!hasValue(pageName)||!hasValue(pageCode)||resBinderCallBack==null){
            view=null;
            return;
        }
        if(hasValue(attr.getBg())){
            view.setBackground(loadDrawable(attr.getBg()));
        }else if(view instanceof ImageView){
            ImageView iv=(ImageView) view;
            if(hasValue(attr.getImg())){
                iv.setImageDrawable(loadDrawable(attr.getImg()));
            }
        }else if(view instanceof TextView){
            TextView tv=(TextView)view;
            Drawable[] ds=new Drawable[4];
            if(hasValue(attr.getImgLeft())){
                ds[0]=loadDrawable(attr.getImgLeft());
            }else if(hasValue(attr.getImgTop())){
                ds[1]=loadDrawable(attr.getImgTop());
            }else if(hasValue(attr.getImgRight())){
                ds[2]=loadDrawable(attr.getImgRight());
            }else if(hasValue(attr.getImgBottom())){
                ds[3]=loadDrawable(attr.getImgBottom());
            }
            tv.setCompoundDrawablesWithIntrinsicBounds(ds[0],ds[1],ds[2],ds[3]);
            if(hasValue(attr.getPageHint())){
                tv.setHint(getText(attr.getPageHint()));
            }
            if(hasValue(attr.getPageText())){
                tv.setText(getText(attr.getPageText()));
            }
        }
    }

    public void clear(){
        view=null;
        this.attr=null;
        resBinderCallBack=null;
    }

    public void setResBinderCallBack(ResBinderCallBack callBack){
        resBinderCallBack=callBack;
    }

    private boolean hasValue(String value){
        if(value!=null&&!"".equals(value)){
            return true;
        }
        return false;
    }

    private Drawable loadDrawable(String name){
        return resBinderCallBack.getImgDrawable(name);
//        File dir= view.getContext().getExternalFilesDir("webpage");
//        File file=new File(dir.getAbsoluteFile()+File.separator+pageName+File.separator+pageCode+File.separator+name);
//        Bitmap bitmap= BitmapFactory.decodeFile(file.getAbsolutePath());
//        if(name.startsWith("x_")){
//            bitmap.setDensity(320);
//        }else if(name.startsWith("xx_")){
//            bitmap.setDensity(480);
//        }else if(name.startsWith("xxx_")){
//            bitmap.setDensity(640);
//        }
//        BitmapDrawable drawable=new BitmapDrawable(view.getResources(),bitmap);
//        return drawable;
    }

    private String getText(String name){
        return resBinderCallBack.getResText(name);
//        Map<String,String> map= ResManager.getTextMap(pageName+"_"+pageCode).get(name);
//        if(isEnglish()){
//            if(map.containsKey("en")){
//                return map.get("en");
//            }
//        }
//        return map.get("def");
    }

//    private boolean isEnglish(){
//        Configuration config= view.getResources().getConfiguration();
//        if(config.locale.getCountry().equals(Locale.ENGLISH.getCountry())){
//            return true;
//        }
//        return false;
//    }
}

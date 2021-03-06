package com.zeba.views.click;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;

import com.zeba.views.attr.SAttr;
import com.zeba.views.drawables.ZBGDrawable;
import com.zeba.views.css.CSSFormat;

import java.util.Map;

public class ShapeHelper {

    public static Drawable getShapeDrawable(SAttr attr) {
        int strokeWidth=attr.strokeWidth;
        int strokeColor=attr.strokeColor;
        float roundRadius=attr.roundRadius;
        int defaultColor=attr.defaultColor;
        float topLeftRadius=attr.topLeftRadius;
        float topRightRadius=attr.topRightRadius;
        float bottomLeftRadius=attr.bottomLeftRadius;
        float bottomRightRadius=attr.bottomRightRadius;
        ZBGDrawable dw = new ZBGDrawable();
        dw.setShape(GradientDrawable.RECTANGLE);
        if (strokeWidth != 0) {
            dw.setStroke(strokeWidth, strokeColor == 0 ? Color.TRANSPARENT : strokeColor);
        }
        if(attr.gLine !=null&&!"".equals(attr.gLine)){
            Map<String,String> map=CSSFormat.form(attr.gLine);
            dw.setDither(true);
            dw.setColors(CSSFormat.toColors(map.get("c")));
            dw.setGradientType(GradientDrawable.LINEAR_GRADIENT);
            int a=Integer.parseInt(map.get("a"));//角度
            switch(a){
                case 0:dw.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);break;
                case 45:dw.setOrientation(GradientDrawable.Orientation.TL_BR);break;
                case 90:dw.setOrientation(GradientDrawable.Orientation.TOP_BOTTOM);break;
                case 135:dw.setOrientation(GradientDrawable.Orientation.TR_BL);break;
                case 180:dw.setOrientation(GradientDrawable.Orientation.RIGHT_LEFT);break;
                case 225:dw.setOrientation(GradientDrawable.Orientation.BR_TL);break;
                case 270:dw.setOrientation(GradientDrawable.Orientation.BOTTOM_TOP);break;
                case 315:dw.setOrientation(GradientDrawable.Orientation.BL_TR);break;
            }
        }else if(attr.gCircle !=null&&!"".equals(attr.gCircle)){
            Map<String,String> map=CSSFormat.form(attr.gCircle);
            dw.setDither(true);
            dw.setColors(CSSFormat.toColors(map.get("c"))); //添加颜色组
            dw.setGradientType(GradientDrawable.RADIAL_GRADIENT);//设置半径渐变
            dw.setGradientRadius(Float.parseFloat(map.get("r")));//渐变的半径值
        }else if(attr.gSweep!=null&&!"".equals(attr.gSweep)){
            Map<String,String> map=CSSFormat.form(attr.gSweep);
            dw.setDither(true);
            dw.setColors(CSSFormat.toColors(map.get("c"))); //添加颜色组
            dw.setGradientType(GradientDrawable.SWEEP_GRADIENT);//设置扫描渐变
            dw.setGradientCenter(Float.parseFloat(map.get("cx")),Float.parseFloat(map.get("cy")));//渐变中心点
        }else if (defaultColor != 0) {
            dw.setColor(defaultColor);
        }
        if (roundRadius != 0) {
            dw.setCornerRadius(roundRadius);
        } else if (topLeftRadius != 0 || topRightRadius != 0 || bottomRightRadius != 0 || bottomLeftRadius != 0) {
            dw.setCornerRadii(new float[]{topLeftRadius, topLeftRadius, topRightRadius, topRightRadius, bottomRightRadius, bottomRightRadius, bottomLeftRadius, bottomLeftRadius});
        }
        if(attr.shadow!=null&&!"".equals(attr.shadow)){
            dw.setShadow(CSSFormat.form(attr.shadow));
        }
        return dw;
    }

}

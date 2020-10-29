package com.zeba.views.click;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;

import com.zeba.views.drawables.ZBGDrawable;
import com.zeba.views.css.CSSFormat;

public class ShapeHelper {

    public static Drawable getShapeDrawable(CShape info) {
        int strokeWidth=info.getStrokeWidth();
        int strokeColor=info.getStrokeColor();
        float roundRadius=info.getRoundRadius();
        int defaultColor=info.getDefaultColor();
        float topLeftRadius=info.getTopLeftRadius();
        float topRightRadius=info.getTopRightRadius();
        float bottomLeftRadius=info.getBottomLeftRadius();
        float bottomRightRadius=info.getBottomRightRadius();
        ZBGDrawable dw = new ZBGDrawable();
        dw.setShape(GradientDrawable.RECTANGLE);
        if (strokeWidth != 0) {
            dw.setStroke(strokeWidth, strokeColor == 0 ? Color.TRANSPARENT : strokeColor);
        }
        if(info.getLine().size()!=0){
            dw.setDither(true);
            dw.setColors(CSSFormat.toColors(info.getLine().get("c")));
            dw.setGradientType(GradientDrawable.LINEAR_GRADIENT);
            int a=Integer.parseInt(info.getLine().get("a"));//角度
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
        }else if(info.getCircle().size()!=0){
            dw.setDither(true);
            dw.setColors(CSSFormat.toColors(info.getCircle().get("c"))); //添加颜色组
            dw.setGradientType(GradientDrawable.RADIAL_GRADIENT);//设置半径渐变
            dw.setGradientRadius(Float.parseFloat(info.getCircle().get("r")));//渐变的半径值
        }else if(info.getSweep().size()!=0){
            dw.setDither(true);
            dw.setColors(CSSFormat.toColors(info.getCircle().get("c"))); //添加颜色组
            dw.setGradientType(GradientDrawable.SWEEP_GRADIENT);//设置扫描渐变
            dw.setGradientCenter(Float.parseFloat(info.getCircle().get("cx")),Float.parseFloat(info.getCircle().get("cy")));//渐变中心点
        }else if (defaultColor != 0) {
            dw.setColor(defaultColor);
        }
        if (roundRadius != 0) {
            dw.setCornerRadius(roundRadius);
        } else if (topLeftRadius != 0 || topRightRadius != 0 || bottomRightRadius != 0 || bottomLeftRadius != 0) {
            dw.setCornerRadii(new float[]{topLeftRadius, topLeftRadius, topRightRadius, topRightRadius, bottomRightRadius, bottomRightRadius, bottomLeftRadius, bottomLeftRadius});
        }
        if(info.getShadow().size()!=0){
            dw.setShadow(info.getShadow());
        }
        return dw;
    }

}

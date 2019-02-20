package com.zeba.views.click;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;

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
        GradientDrawable dw = new GradientDrawable();
        dw.setShape(GradientDrawable.RECTANGLE);
        if (strokeWidth != 0) {
            dw.setStroke(strokeWidth, strokeColor == 0 ? Color.TRANSPARENT : strokeColor);
        }
        if (defaultColor != 0) {
            dw.setColor(defaultColor);
        }
        if (roundRadius != 0) {
            dw.setCornerRadius(roundRadius);
        } else if (topLeftRadius != 0 || topRightRadius != 0 || bottomRightRadius != 0 || bottomLeftRadius != 0) {
            dw.setCornerRadii(new float[]{topLeftRadius, topLeftRadius, topRightRadius, topRightRadius, bottomRightRadius, bottomRightRadius, bottomLeftRadius, bottomLeftRadius});
        }
        return dw;
    }

}

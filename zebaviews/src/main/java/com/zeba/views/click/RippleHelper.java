package com.zeba.views.click;

import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Build;

public class RippleHelper {

    public static Drawable getRippleDrawable(Drawable drawable,CShape info) {
        int pressedColor=info.getPressedColor();
        if (pressedColor == 0) {
            pressedColor = getLightOrDarken(info.getDefaultColor(), 0.2D);
            info.setPressedColor(pressedColor);
        }
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            ColorStateList pressedColorDw = ColorStateList.valueOf(pressedColor);
            return new RippleDrawable(pressedColorDw, drawable, ShapeHelper.getShapeDrawable(info));
        }
//        else if(!isRipple){
////            StateListDrawable stateListDrawable = new StateListDrawable();
////            stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, getGradientDrawable(pressedColor, parameter));
////            stateListDrawable.addState(new int[]{android.R.attr.state_focused}, getGradientDrawable(pressedColor, parameter * 2));
////            stateListDrawable.addState(new int[]{}, getGradientDrawable(0, 0));
////            return stateListDrawable;
////        }
        return null;
    }

    private static Drawable getShape(final CShape info) {
        ShapeDrawable mask = new ShapeDrawable(new RectShape() {
            @Override
            public void draw(Canvas canvas, Paint paint) {
                float roundRadius=info.getRoundRadius();
                float topLeftRadius=info.getTopLeftRadius();
                float topRightRadius=info.getTopRightRadius();
                float bottomRightRadius=info.getBottomRightRadius();
                float bottomLeftRadius=info.getBottomLeftRadius();
                final float width = this.getWidth();
                final float height = this.getHeight();
                RectF rectF = new RectF(0, 0, width, height);
                if (roundRadius != 0) {
                    canvas.drawRoundRect(rectF, roundRadius, roundRadius, paint);
                } else if (topLeftRadius != 0 || topRightRadius != 0 || bottomRightRadius != 0 || bottomLeftRadius != 0) {
                    Path path = new Path();
                    path.addRoundRect(rectF, new float[]{topLeftRadius, topLeftRadius, topRightRadius, topRightRadius, bottomRightRadius, bottomRightRadius, bottomLeftRadius, bottomLeftRadius}, Path.Direction.CW);
                    canvas.drawPath(path, paint);
                } else {
                    canvas.drawRect(rectF, paint);
                }
            }
        });
        return mask;
    }

    //单色变暗
    private static int darkenColor(int color, double parameter) {
        return (int) Math.max(color - color * parameter, 0);
    }

    //颜色变暗
    private static int darkenColors(int color, double parameter) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        red = darkenColor(red, parameter);
        green = darkenColor(green, parameter);
        blue = darkenColor(blue, parameter);
        int alpha = Color.alpha(color);
        return Color.argb(alpha, red, green, blue);
    }

    //单色变亮
    private static int lightColor(int color, double parameter) {
        return (int) Math.min(color + color * parameter, 255);
    }

    //颜色变亮
    private static int lightColors(int color, double parameter) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        red = lightColor(red, parameter);
        green = lightColor(green, parameter);
        blue = lightColor(blue, parameter);
        int alpha = Color.alpha(color);
        return Color.argb(alpha, red, green, blue);
    }

    //判断颜色 变亮或变暗
    private static boolean isLightOrDarken(int color, double parameter) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return red + (red * parameter) < 255 && green + (green * parameter) < 255 && blue + (blue * parameter) < 255;
    }

    //获取变亮或变暗颜色
    private static int getLightOrDarken(int color, double parameter) {
        parameter = parameter < 0 ? 0 : parameter > 1 ? 1 : parameter;
        if (isLightOrDarken(color, parameter)) {
            return lightColors(color, parameter);
        } else {
            return darkenColors(color, parameter);
        }
    }

}

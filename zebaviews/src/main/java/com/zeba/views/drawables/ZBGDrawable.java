package com.zeba.views.drawables;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.Nullable;

import java.util.Map;

public class ZBGDrawable extends GradientDrawable {

    private Paint mShadowPaint;
    private float mShadowRadius;
    private float mRoundRadius;
    private int mShadowColor;
    private float mOffsetX;
    private float mOffsetY;
    private RectF mRect;

    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        if(mShadowPaint==null){
            super.setBounds(left,top,right,bottom);
            return;
        }
        mRect = new RectF(left + mShadowRadius - mOffsetX, top + mShadowRadius - mOffsetY, right - mShadowRadius - mOffsetX,
                bottom - mShadowRadius - mOffsetY);
        super.setBounds((int)mRect.left, (int)mRect.top, (int)mRect.right, (int)mRect.bottom);
    }

    @Override
    public void draw(Canvas canvas) {
        if(mShadowPaint!=null){
            canvas.drawRoundRect(mRect, mRoundRadius, mRoundRadius, mShadowPaint);
        }
        super.draw(canvas);
    }

    @Override
    public void setAlpha(int alpha) {
        super.setAlpha(alpha);
        mShadowPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        super.setColorFilter(colorFilter);
        mShadowPaint.setColorFilter(colorFilter);
    }

    public void setShadow(Map<String,String> map){
        mShadowRadius=Float.parseFloat(map.get("sr"));
        mRoundRadius=Float.parseFloat(map.get("r"));
        mOffsetX=Float.parseFloat(map.get("x"));
        mOffsetY=Float.parseFloat(map.get("y"));
        mShadowColor=Color.parseColor(map.get("c"));
        mShadowPaint = new Paint();
        mShadowPaint.setColor(Color.TRANSPARENT);
        mShadowPaint.setAntiAlias(true);
        mShadowPaint.setShadowLayer(mShadowRadius, mOffsetX, mOffsetY, mShadowColor);
        mShadowPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP));
    }
}

package com.zeba.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;

@SuppressLint("AppCompatCustomView")
public class RoundImageView extends ImageView {
    private int type=0;
    private static final int TYPE_CIRCLE = 0;
    private static final int TYPE_ROUND = 1;

    private Paint paint;
    private int roundLeftTop;
    private int roundLeftBottom;
    private int roundRightTop;
    private int roundRightBottom;
    private Paint paint2;
    public RoundImageView(Context context) {
        this(context, null);
    }

    public RoundImageView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.RoundImageView);
        roundLeftTop=a.getDimensionPixelSize(
                R.styleable.RoundImageView_leftTopRadius, (int) TypedValue
                        .applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                                0, getResources()
                                        .getDisplayMetrics()));
        roundLeftBottom=a.getDimensionPixelSize(
                R.styleable.RoundImageView_leftBottomRadius, (int) TypedValue
                        .applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                                0, getResources()
                                        .getDisplayMetrics()));
        roundRightTop=a.getDimensionPixelSize(
                R.styleable.RoundImageView_rightTopRadius, (int) TypedValue
                        .applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                                0, getResources()
                                        .getDisplayMetrics()));
        roundRightBottom=a.getDimensionPixelSize(
                R.styleable.RoundImageView_rightBottomRadius, (int) TypedValue
                        .applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                                0, getResources()
                                        .getDisplayMetrics()));
        int radius = a.getDimensionPixelSize(
                R.styleable.RoundImageView_borderRadius, (int) TypedValue
                        .applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                                0, getResources()
                                        .getDisplayMetrics()));// 默认为10dp
        type = a.getInt(R.styleable.RoundImageView_roundType, TYPE_CIRCLE);// 默认为Circle
        a.recycle();
        if(radius!=0){
            roundLeftTop=radius;
            roundLeftBottom=radius;
            roundRightTop=radius;
            roundRightBottom=radius;
        }
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));

        paint2 = new Paint();
        paint2.setXfermode(null);
    }

    @Override
    public void draw(Canvas canvas) {
        if(getWidth()==0||getHeight()==0){
            super.draw(canvas);
            return;
        }
        if(type==TYPE_CIRCLE){
            roundLeftTop=getMeasuredWidth()/2;
            roundLeftBottom=getMeasuredHeight()/2;
            roundRightTop=getMeasuredWidth()/2;
            roundRightBottom=getMeasuredHeight()/2;
        }
        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_4444);
        Canvas canvas2 = new Canvas(bitmap);
        super.draw(canvas2);
        drawLiftUp(canvas2);
        drawLiftDown(canvas2);
        drawRightUp(canvas2);
        drawRightDown(canvas2);
        canvas.drawBitmap(bitmap, 0, 0, paint2);
        bitmap.recycle();
    }

    private void drawLiftUp(Canvas canvas) {
        if(roundLeftTop<=0){
            return;
        }
        Path path = new Path();
        path.moveTo(0, roundLeftTop);
        path.lineTo(0, 0);
        path.lineTo(roundLeftTop, 0);
        path.arcTo(new RectF(0, 0, roundLeftTop * 2, roundLeftTop * 2), -90, -90);
        path.close();
        canvas.drawPath(path, paint);
    }

    private void drawLiftDown(Canvas canvas) {
        if(roundLeftBottom<=0){
            return;
        }
        Path path = new Path();
        path.moveTo(0, getHeight() - roundLeftBottom);
        path.lineTo(0, getHeight());
        path.lineTo(roundLeftBottom, getHeight());
        path.arcTo(new RectF(0, getHeight() - roundLeftBottom * 2, roundLeftBottom * 2, getHeight()), 90, 90);
        path.close();
        canvas.drawPath(path, paint);
    }

    private void drawRightDown(Canvas canvas) {
        if(roundRightBottom<=0){
            return;
        }
        Path path = new Path();
        path.moveTo(getWidth() - roundRightBottom, getHeight());
        path.lineTo(getWidth(), getHeight());
        path.lineTo(getWidth(), getHeight() - roundRightBottom);
        path.arcTo(new RectF(getWidth() - roundRightBottom * 2, getHeight() - roundRightBottom * 2, getWidth(), getHeight()), -0, 90);
        path.close();
        canvas.drawPath(path, paint);
    }

    private void drawRightUp(Canvas canvas) {
        if(roundRightTop<=0){
            return;
        }
        Path path = new Path();
        path.moveTo(getWidth(), roundRightTop);
        path.lineTo(getWidth(), 0);
        path.lineTo(getWidth() - roundRightTop, 0);
        path.arcTo(new RectF(getWidth() - roundRightTop * 2, 0, getWidth(), 0 + roundRightTop * 2), -90, 90);
        path.close();
        canvas.drawPath(path, paint);
    }

    public void setRoundRadius(int borderRadius) {
        int pxVal = dp2px(borderRadius);
        roundLeftTop=pxVal;
        roundLeftBottom=pxVal;
        roundRightTop=pxVal;
        roundRightBottom=pxVal;
        invalidate();
    }

    public void setType(int type) {
        if (this.type != type) {
            this.type = type;
            if (this.type != TYPE_ROUND && this.type != TYPE_CIRCLE) {
                this.type = TYPE_CIRCLE;
            }
            requestLayout();
        }

    }

    public int dp2px(int dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, getResources().getDisplayMetrics());
    }
}

package com.zeba.views.click;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;

import com.zeba.views.drawables.SVGDrawable;
import com.zeba.views.utils.AnimCSS;
import com.zeba.views.utils.StyleCSS;

import java.util.Map;

public class ShapeImageView extends AppCompatImageView implements ViewSuperCallBack {
    private ViewClickHelper clickHelper;
    private StyleCSS styleCSS;
    private AnimCSS animCSS;
    private SVGDrawable svgDrawable;

    private int type=1;
    private static final int TYPE_CIRCLE = 0;
    private static final int TYPE_ROUND = 1;

    private Paint paint;
    private float roundLeftTop;
    private float roundLeftBottom;
    private float roundRightTop;
    private float roundRightBottom;
    private Paint paint2;

    public ShapeImageView(Context context) {
        super(context);
        init(context,null);
    }

    public ShapeImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public ShapeImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context,AttributeSet attrs){
        clickHelper=new ViewClickHelper(this);
        Map<String,String> map= clickHelper.getShape().init(context,attrs);
        clickHelper.init();
        styleCSS =new StyleCSS(this,map.get("css"));
        animCSS =new AnimCSS(map.get("anim"));
        animCSS.init(this);
        if(clickHelper.getShape().getSvg().size()!=0){
            svgDrawable=new SVGDrawable(context,clickHelper.getShape().getSvg());
        }
        if(clickHelper.getShape().getRoundRadius()!=0){
            roundLeftTop=clickHelper.getShape().getRoundRadius();
            roundLeftBottom=clickHelper.getShape().getRoundRadius();
            roundRightTop=clickHelper.getShape().getRoundRadius();
            roundRightBottom=clickHelper.getShape().getRoundRadius();
        }else{
            roundLeftTop=clickHelper.getShape().getTopLeftRadius();
            roundLeftBottom=clickHelper.getShape().getBottomLeftRadius();
            roundRightTop=clickHelper.getShape().getTopRightRadius();
            roundRightBottom=clickHelper.getShape().getBottomRightRadius();
        }
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));

        paint2 = new Paint();
        paint2.setXfermode(null);
    }

    public CShape getShape(){
        return clickHelper.getShape();
    }

    public void setShapeDrawable(){
        clickHelper.setDrawable();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        clickHelper.onSizeChanged(w,h,oldw,oldh);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        styleCSS.initFinish(this);
        post(new Runnable() {
            @Override
            public void run() {
                animCSS.init(ShapeImageView.this);
            }
        });
    }

    public void animStart(){
        animCSS.start();
    }

    public AnimCSS anim(String css){
        animCSS=new AnimCSS(css);
        return animCSS;
    }

    public AnimCSS getAnimCSS(){
        return animCSS;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return clickHelper.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        clickHelper.onDraw(canvas);
    }

    @Override
    public boolean performClick() {
        return clickHelper.performClick();
    }

    @Override
    public boolean performLongClick() {
        return clickHelper.performLongClick();
    }


    @Override
    public boolean superPerformClick() {
        return super.performClick();
    }

    @Override
    public boolean superPerformLongClick() {
        return super.performLongClick();
    }

    @Override
    public boolean superOnTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
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

        if(svgDrawable!=null){
            svgDrawable.setBounds(this);
            svgDrawable.drawable().draw(canvas);
        }
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

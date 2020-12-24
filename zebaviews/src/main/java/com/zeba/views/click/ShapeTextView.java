package com.zeba.views.click;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.widget.AppCompatTextView;
import android.text.SpannableString;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;

import com.zeba.views.R;
import com.zeba.views.attr.SAttr;
import com.zeba.views.databind.ResBinder;
import com.zeba.views.drawables.SVGDrawable;
import com.zeba.views.css.AnimCSS;
import com.zeba.views.css.StyleCSS;
import com.zeba.views.css.TypeFaceManager;
import com.zeba.views.interfaces.SViewer;

import java.util.Map;

public class ShapeTextView extends AppCompatTextView implements ViewSuperCallBack,SViewer {
    private ViewClickHelper clickHelper;
    private CircularProgressDrawable loadingDrawable;
    private int loadingSize;
    private float loadingStrokeWidth;
    private int loadingPadding;
    private String loadingHint;
    private String text;
    private StyleCSS styleCSS=new StyleCSS();
    private AnimCSS animCSS=new AnimCSS();
    private SVGDrawable svgDrawable;
    private SAttr sAttr;
    public ShapeTextView(Context context) {
        this(context,null);
    }

    public ShapeTextView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ShapeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context,AttributeSet attrs){
        sAttr=new SAttr(context,attrs);
        clickHelper=new ViewClickHelper(this);
        if(attrs!=null){
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ShapeTextView);
            loadingSize =typedArray.getDimensionPixelOffset(R.styleable.ShapeTextView_loadingSize,0);
            loadingStrokeWidth=typedArray.getDimensionPixelOffset(R.styleable.ShapeTextView_loadingStrokeWidth,dp2px(2));
            loadingPadding=typedArray.getDimensionPixelOffset(R.styleable.ShapeTextView_loadingPadding,dp2px(10));
            loadingHint=typedArray.getString(R.styleable.ShapeTextView_loadingHint);
            sAttr.loadingColor=typedArray.getColor(R.styleable.ShapeTextView_loadingColor,getCurrentTextColor());
            typedArray.recycle();
        }
        reloadAttr(context);
    }

    @Override
    public void reloadAttr(Context context) {
        styleCSS.setCSS(this,sAttr);
        animCSS.setCSS(this,sAttr.anim);
        loadingDrawable = new CircularProgressDrawable(getContext());
        loadingDrawable.setColorSchemeColors(sAttr.loadingColor);
        loadingDrawable.setStrokeWidth(loadingStrokeWidth);
        text=getText().toString();
        if(loadingHint==null||loadingHint.length()==0){
            loadingHint=text;
        }
        TypeFaceManager.initTypeFace(context,this,sAttr.ttf);
        if(sAttr.svg!=null&&!"".equals(sAttr.svg)){
            svgDrawable=new SVGDrawable(context,sAttr.svg);
        }
        if(sAttr.shadow!=null&&!"".equals(sAttr.shadow)){
            setLayerType(LAYER_TYPE_SOFTWARE,null);
        }
        setShapeDrawable();
    }

    @Override
    public SAttr getSAttr() {
        return sAttr;
    }

    public void refreshView(){
        reloadAttr(getContext());
    }

    public String getFieldName(){
        return sAttr.fieldName;
    }

    public void setText(String text) {
        this.text = text;
        super.setText(text);
        if(animCSS.isTextChangeStart()){
            animStart();
        }
    }

    public void setText(SpannableString text){
        super.setText(text);
        if(animCSS.isTextChangeStart()){
            animStart();
        }
    }

    public void setLoadingHint(String hint){
        loadingHint=hint;
    }

    public void showLoading(String hint){
        loadingHint=hint;
        showLoading();
    }

    public void showLoading(){
        super.setText(loadingHint);
        int sx=(int)(getWidth()/2-getPaint().measureText(loadingHint)/2-loadingSize-loadingPadding);
        int sy=getHeight()/2- loadingSize /2;
        loadingDrawable.setBounds(sx,sy,sx+ loadingSize,sy+ loadingSize);
        loadingDrawable.start();
        postInvalidate();
    }

    public void dismissLoading(){
        loadingDrawable.stop();
        super.setText(text);
    }

    public boolean isLoading(){
        return loadingDrawable.isRunning();
    }

    public void setShapeDrawable(){
        clickHelper.setDrawable(sAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        clickHelper.onSizeChanged(w,h,oldw,oldh);
        if(loadingSize ==0){
            loadingSize =(int)(getTextSize()*1.5f);
        }
        if(svgDrawable!=null){
            svgDrawable.setBounds(this);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        styleCSS.initFinish(this);
        post(new Runnable() {
            @Override
            public void run() {
                animCSS.init();
            }
        });
    }

    public void animStart(){
        animCSS.start();
    }

    public AnimCSS anim(String css){
        animCSS.setCSS(this,css);
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
        if(loadingDrawable.isRunning()){
            loadingDrawable.draw(canvas);
            postInvalidate();
        }
        if(svgDrawable!=null){
            svgDrawable.drawable().draw(canvas);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

    }

    @Override
    public boolean performClick() {
        if(loadingDrawable.isRunning()){
            return true;
        }
        return clickHelper.performClick();
    }

    @Override
    public boolean performLongClick() {
        if(loadingDrawable.isRunning()){
            return true;
        }
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

    public int dp2px(int dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, getResources().getDisplayMetrics());
    }

    public void setDrawableLeft(int resId){
        setDrawables(resId,0);
    }

    public void setDrawableLeft(Drawable drawable){
        setDrawables(drawable,0);
    }

    public void setDrawableTop(int resId){
        setDrawables(resId,1);
    }

    public void setDrawableTop(Drawable drawable){
        setDrawables(drawable,1);
    }

    public void setDrawableRight(int resId){
        setDrawables(resId,2);
    }

    public void setDrawableRight(Drawable drawable){
        setDrawables(drawable,2);
    }

    public void setDrawableBottom(int resId){
        setDrawables(resId,3);
    }

    public void setDrawableBottom(Drawable drawable){
        setDrawables(drawable,3);
    }

    private void setDrawables(int resId,int index){
        Drawable[] dws= getCompoundDrawables();
        dws[index]= getResources().getDrawable(resId);
        setCompoundDrawablesWithIntrinsicBounds(dws[0],dws[1],dws[2],dws[3]);
    }

    private void setDrawables(Drawable drawable,int index){
        Drawable[] dws= getCompoundDrawables();
        dws[index]= drawable;
        setCompoundDrawablesWithIntrinsicBounds(dws[0],dws[1],dws[2],dws[3]);
    }

}

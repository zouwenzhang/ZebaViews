package com.zeba.views.click;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;

import com.zeba.views.R;
import com.zeba.views.STextView;
import com.zeba.views.drawables.SVGDrawable;
import com.zeba.views.utils.AnimCSS;
import com.zeba.views.utils.StyleCSS;

public class ShapeTextView extends AppCompatTextView implements ViewSuperCallBack {
    private ViewClickHelper clickHelper;
    private CircularProgressDrawable loadingDrawable;
    private int loadingSize;
    private float loadingStrokeWidth;
    private int loadingPadding;
    private String loadingHint;
    private String text;
    private StyleCSS styleCSS;
    private AnimCSS animCSS;
    private SVGDrawable svgDrawable;
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
        clickHelper=new ViewClickHelper(this);
        clickHelper.getShape().init(context,attrs);
        clickHelper.init();
        if(attrs==null){
            return;
        }
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ShapeTextView);
        loadingSize =typedArray.getDimensionPixelOffset(R.styleable.ShapeTextView_loadingSize,0);
        loadingStrokeWidth=typedArray.getDimensionPixelOffset(R.styleable.ShapeTextView_loadingStrokeWidth,dp2px(2));
        loadingPadding=typedArray.getDimensionPixelOffset(R.styleable.ShapeTextView_loadingPadding,dp2px(10));
        loadingHint=typedArray.getString(R.styleable.ShapeTextView_loadingHint);
        String style=typedArray.getString(R.styleable.ShapeTextView_css);
        String anim=typedArray.getString(R.styleable.ShapeTextView_anim);
        typedArray.recycle();
        loadingDrawable = new CircularProgressDrawable(getContext());
        loadingDrawable.setColorSchemeColors(getTextColors().getDefaultColor());
        loadingDrawable.setStrokeWidth(loadingStrokeWidth);
        text=getText().toString();
        if(loadingHint==null||loadingHint.length()==0){
            loadingHint=text;
        }
        styleCSS =new StyleCSS(this,style);
        animCSS =new AnimCSS(anim);
        animCSS.init(this);
        if(clickHelper.getShape().getSvg().size()!=0){
            svgDrawable=new SVGDrawable(context,clickHelper.getShape().getSvg());
        }
    }

    public void showLoading(String hint){
        loadingHint=hint;
        showLoading();
    }

    public void showLoading(){
        setText(loadingHint);
        int sx=(int)(getWidth()/2-getPaint().measureText(loadingHint)/2-loadingSize-loadingPadding);
        int sy=getHeight()/2- loadingSize /2;
        loadingDrawable.setBounds(sx,sy,sx+ loadingSize,sy+ loadingSize);
        loadingDrawable.start();
        postInvalidate();
    }

    public void dismissLoading(){
        loadingDrawable.stop();
        setText(text);
    }

    public boolean isLoading(){
        return loadingDrawable.isRunning();
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
                animCSS.init(ShapeTextView.this);
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
}

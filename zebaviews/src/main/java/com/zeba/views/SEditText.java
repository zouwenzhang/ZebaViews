package com.zeba.views;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.zeba.views.click.CShape;
import com.zeba.views.click.ViewClickHelper;
import com.zeba.views.interfaces.TextChangeListener;
import com.zeba.views.utils.AnimCSS;
import com.zeba.views.utils.StyleCSS;

import java.util.Map;

public class SEditText extends AppCompatEditText implements TextWatcher {

    private ViewClickHelper clickHelper;
    private StyleCSS styleCSS;
    private AnimCSS animCSS;

    public SEditText(Context context) {
        this(context,null);
    }

    public SEditText(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    public TextChangeListener textChangeListener;

    private void init(Context context,AttributeSet attrs){
        clickHelper=new ViewClickHelper(this);
        Map<String,String> map= clickHelper.getShape().init(context,attrs);
        clickHelper.init();
        styleCSS =new StyleCSS(this,map.get("css"));
        animCSS =new AnimCSS(this,map.get("anim"));
        if(clickHelper.getShape().getShadow().size()!=0){
            setLayerType(LAYER_TYPE_SOFTWARE,null);
        }
        addTextChangedListener(this);
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
                animCSS.init();
            }
        });
    }

    public void animStart(){
        animCSS.start();
    }

    public AnimCSS anim(String css){
        animCSS=new AnimCSS(this,css);
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

    public void textChange(TextChangeListener listener){
        textChangeListener=listener;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }
    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if(textChangeListener!=null){
            textChangeListener.onChange(charSequence);
        }
    }
    @Override
    public void afterTextChanged(Editable editable) {

    }
}

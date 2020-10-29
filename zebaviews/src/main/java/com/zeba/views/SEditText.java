package com.zeba.views;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.zeba.views.attr.SAttr;
import com.zeba.views.click.CShape;
import com.zeba.views.click.ViewClickHelper;
import com.zeba.views.interfaces.TextChangeListener;
import com.zeba.views.css.AnimCSS;
import com.zeba.views.css.StyleCSS;
import com.zeba.views.css.TypeFaceManager;
import com.zeba.views.databind.ViewDataBinder;

import org.zeba.obj.proxy.ProxyFunc;

import java.util.Map;

public class SEditText extends AppCompatEditText implements TextWatcher {

    private ViewClickHelper clickHelper;
    private StyleCSS styleCSS;
    private AnimCSS animCSS;
    private View clearView;
    private ViewDataBinder dataBinder=new ViewDataBinder(this);
    private SAttr sAttr;

    public SEditText(Context context) {
        super(context);
        init(context,null);
    }

    public SEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public SEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private TextChangeListener textChangeListener;

    private void init(Context context,AttributeSet attrs){
        sAttr=new SAttr(context,attrs);
        setBackground(null);
        clickHelper=new ViewClickHelper(this);
        Map<String,String> map= clickHelper.getShape().init(context,attrs);
        clickHelper.init();
        styleCSS =new StyleCSS(this,map.get("css"));
        animCSS =new AnimCSS(this,map.get("anim"));
        TypeFaceManager.initTypeFace(context,this,map);
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
        dataBinder.onDestroy();
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
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        clickHelper.onDraw(canvas);
    }

    public void textChange(TextChangeListener listener){
        textChangeListener=listener;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }
    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if(charSequence.length()!=0){
            showClearView();
        }else{
            hideClearView();
        }
        if(textChangeListener!=null){
            textChangeListener.onChange(charSequence);
        }
        if(dataBinder!=null&&charSequence!=null){
            dataBinder.setValueToObj(charSequence.toString());
        }
    }
    @Override
    public void afterTextChanged(Editable editable) {

    }

    public void bindData(Object obj,String name){
        dataBinder.bind(obj,name);
    }

    public <T> void bindData(Object obj, String name, ProxyFunc<T> func){
        dataBinder.bindData(obj,name,func);
    }

    public String getFieldName(){
        return sAttr.getFieldName();
    }

    public void setSearchClickListener(final SearchClickListener listener){
        setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        setImeActionLabel("actionSearch",EditorInfo.IME_ACTION_SEARCH);
        setOnEditorActionListener(new OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if(actionId==EditorInfo.IME_ACTION_SEARCH){
                    listener.onSearchClick(getText().toString());
                    hideKeyboard(SEditText.this);
                    return true;
                }
                return false;
            }
        });
    }

    public void hideKeyboard(View view) {
        InputMethodManager manager = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void setClearView(View view){
        clearView=view;
        clearView.setVisibility(GONE);
        clearView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                setText("");
            }
        });
    }

    protected void showClearView(){
        if(clearView==null||clearView.getVisibility()==VISIBLE){
            return;
        }
        new AnimCSS().view(clearView).style("s:1;sf:0;st:1;d:150;").onStart(new Runnable() {
            @Override
            public void run() {
                clearView.setVisibility(VISIBLE);
            }
        }).start();
    }

    protected void hideClearView(){
        if(clearView==null||clearView.getVisibility()==GONE){
            return;
        }
        new AnimCSS().view(clearView).style("s:1;sf:1;st:0;d:150;").onEnd(new Runnable() {
            @Override
            public void run() {
                clearView.setVisibility(GONE);
            }
        }).start();
    }

    public interface SearchClickListener{
        void onSearchClick(String text);
    }

    public SAttr getSAttr(){
        return sAttr;
    }
}

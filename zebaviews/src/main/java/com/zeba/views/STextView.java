package com.zeba.views;

import android.content.Context;
import android.util.AttributeSet;

import com.zeba.views.click.ShapeTextView;
import com.zeba.views.databind.ViewDataBinder;

import org.zeba.obj.proxy.ProxyFunc;

public class STextView extends ShapeTextView {

    private ViewDataBinder dataBinder=new ViewDataBinder(this);

    public STextView(Context context) {
        this(context,null);
    }

    public STextView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public STextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void bindData(Object obj,String name){
        dataBinder.bind(obj,name);
    }

    public <T> void bindData(Object obj,String name,ProxyFunc<T> func){
        dataBinder.bindData(obj,name,func);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        dataBinder.onDestroy();
    }

}

package com.zeba.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

public class SListView extends RecyclerView {

    public SListView(Context context) {
        super(context);
        init(context,null);
    }

    public SListView(Context context,AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public SListView(Context context,AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context,attrs);
    }

    private void init(Context context,AttributeSet attrs){
        LinearLayoutManager llm=new LinearLayoutManager(context);
        llm.setOrientation(RecyclerView.VERTICAL);
        if(attrs!=null){
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SListView);
            String d=typedArray.getString(R.styleable.SListView_direction);
            if("row".equals(d)||"0".equals(d)){
                llm.setOrientation(RecyclerView.HORIZONTAL);
            }
            typedArray.recycle();
        }
        setLayoutManager(llm);
    }
}

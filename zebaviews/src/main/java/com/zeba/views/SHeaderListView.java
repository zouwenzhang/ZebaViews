package com.zeba.views;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.zeba.views.adapter.SQuickAdapter;

public class SHeaderListView extends SFrameLayout{

    private View headerView;
    private View stickView;
    private View stickViewBg;
    private RecyclerView rvList;

    public SHeaderListView(Context context) {
        super(context);
        initView(context);
    }

    public SHeaderListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public SHeaderListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context){
        rvList=new RecyclerView(context);
        LinearLayoutManager lm=new LinearLayoutManager(context);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        rvList.setLayoutManager(lm);
        addView(rvList, ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        post(new Runnable() {
            @Override
            public void run() {
                loadView();
            }
        });
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    public RecyclerView getRvList(){
        return rvList;
    }

    private void loadView(){
        if(getChildCount()>=3){
            stickView=getChildAt(1);
            stickViewBg=new View(getContext());
            ViewGroup.LayoutParams lp=new ViewGroup.LayoutParams(stickView.getMeasuredWidth(),stickView.getMeasuredHeight());
            stickViewBg.setLayoutParams(lp);
            stickView.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {

                    return false;
                }
            });

        }
        if(getChildCount()>=2){
            headerView=getChildAt(0);
            removeViewAt(0);
        }
        if(rvList.getAdapter() instanceof SQuickAdapter){
            SQuickAdapter adapter=(SQuickAdapter)rvList.getAdapter();
            if(headerView!=null){
                adapter.addHeaderView(headerView,0);
            }
            if(stickViewBg!=null){
                adapter.addHeaderView(stickViewBg,1);
            }
        }
    }
}

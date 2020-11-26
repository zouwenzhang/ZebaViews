package com.zeba.views.adapter;

import android.animation.Animator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zeba.views.SRefreshLayout;

import java.lang.ref.WeakReference;
import java.util.List;

public abstract class SQuickAdapter<T> extends BaseQuickAdapter<T,BaseViewHolder> {
    private int pageNo=1;
    private int pageSize=10;
    private WeakReference<Context> weakContext;
    private int animCount=0;
    public void skipPageNo(){
        pageNo++;
    }
    public void setLoadEndState(int size){
        if(size<pageSize){
            loadMoreEnd();
        }else{
            loadMoreComplete();
        }
    }

    public int getPageNo(boolean isRefresh) {
        if(isRefresh){
            pageNo=1;
        }
        return pageNo;
    }

    public SQuickAdapter refreshPageNo(boolean isRefresh){
        if(isRefresh){
            pageNo=1;
        }
        return this;
    }

    public int getPageNo() {
        return pageNo;
    }

    public int getPageSize(){
        return pageSize;
    }

    public void setPageSize(int size){
        pageSize=size;
    }

    public SQuickAdapter(int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);
    }

    public SQuickAdapter(@Nullable List<T> data) {
        super(data);
    }

    public SQuickAdapter(int layoutResId) {
        super(layoutResId);
        openLoadAnimation(SLIDEIN_BOTTOM);
    }

    public SQuickAdapter() {
        openLoadAnimation(SLIDEIN_BOTTOM);
    }

    @Override
    protected void startAnim(Animator anim, int index) {
//        super.startAnim();
        if(animCount>pageSize){
            return;
        }
        anim.setDuration(300);
        anim.setStartDelay(index*150);
        anim.setInterpolator(new LinearInterpolator());
        animCount++;
    }

    @Override
    public void setEmptyView(View emptyView) {
        super.setEmptyView(emptyView);
        hideEmptyView();
    }

    public void showEmptyView(){
        if(getEmptyView()!=null){
            getEmptyView().setVisibility(View.VISIBLE);
        }
    }

    public void hideEmptyView(){
        if(getEmptyView()!=null){
            getEmptyView().setVisibility(View.GONE);
        }
    }

    public Context getContext() {
        if(weakContext==null){
            return null;
        }
        return weakContext.get();
    }

    public void setContext(Context context) {
        weakContext=new WeakReference<>(context);
    }

    public void loadData(List list){
        loadData(pageNo==1,null,list);
    }

    public void loadData(boolean isRefresh,List list){
        loadData(isRefresh,null,list);
    }

    public void loadData(SRefreshLayout refreshLayout,List list){
        loadData(pageNo==1,refreshLayout,list);
    }

    public void loadData(boolean isRefresh,SRefreshLayout refreshLayout,List list){
        if(refreshLayout!=null){
            refreshLayout.setRefreshing(false);
        }
        if(!isLoadMoreEnable()){
            setEnableLoadMore(true);
        }
        if(list==null){
            return;
        }
        if(list.size()==0&&isRefresh){
            showEmptyView();
        }else{
            hideEmptyView();
        }
        if(isRefresh){
            setNewData(list);
        }else{
            addData(list);
        }
        skipPageNo();
        setLoadEndState(list.size());
    }
}

package com.zeba.views.adapter;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public abstract class SLinAdapter<T> {
    private WeakReference<Context> wefContext;
    private LinearLayout group;
    private List<T> data;
    public SLinAdapter(Context context){
        wefContext=new WeakReference<>(context);
    }

    public Context getContext(){
        if(wefContext==null){
            return null;
        }
        return wefContext.get();
    }

    public void setAdapter(LinearLayout linearLayout){
        group=linearLayout;
    }


    public int getLayoutId(int position, T item){
        return 0;
    }

    protected int getItemContentViewId(){
        return 0;
    }

    protected View getItemContentView(){
        return null;
    }

    private View createView(int position, T item){
        int layoutId=getLayoutId(position,item);
        if(layoutId!=0){
            return View.inflate(getContext(), layoutId,null);
        }
        layoutId=getItemContentViewId();
        if(layoutId!=0){
            return View.inflate(getContext(), layoutId,null);
        }
        return getItemContentView();
    }

    public abstract void onBindDataView(View view, int position, T item, boolean isBindData);


    private View.OnClickListener onItemClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(itemClickListener!=null){
                int p=(int)(v.getTag());
                itemClickListener.onItemClick(v,p);
            }
        }
    };

    public void addOnItemClick(View view, int position){
        view.setTag(position);
        view.setOnClickListener(onItemClickListener);
    }

    public interface ItemClickListener{
        void onItemClick(View view, int position);
    }

    private ItemClickListener itemClickListener;

    public void setOnItemClickListener(ItemClickListener listener){
        itemClickListener=listener;
    }
    private View.OnClickListener onItemChildClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(itemChildClickListener!=null){
                int p=(int)(v.getTag());
                itemChildClickListener.onItemChildClick(v,p);
            }
        }
    };

    public void addOnItemChildClick(View view, int position){
        view.setTag(position);
        view.setOnClickListener(onItemChildClickListener);
    }

    public interface ItemChildClickListener{
        void onItemChildClick(View view, int position);
    }

    private ItemChildClickListener itemChildClickListener;

    public void setOnItemChildClickListener(ItemChildClickListener listener){
        itemChildClickListener=listener;
    }

    public List<T> getData(){
        return data;
    }

    public void setNewData(List<T> data){
        if(data==null){
            data=new ArrayList<>();
        }
        this.data=data;
        notifyDataSetChanged(false);
    }

    public void clearViews(){
        if(group!=null){
            group.removeAllViews();
        }
    }

    public void addData(T item){
        if(data==null){
            data=new ArrayList<>();
        }
        data.add(item);
        notifyDataSetChanged(false);
    }

    public void removeData(int position){
        if(data.size()>0&&data.size()>position){
            data.remove(position);
            notifyDataSetChanged(false);
        }
    }

    public void notifyDataSetChanged(boolean isBindData){
        if(data==null){
            data=new ArrayList<>();
        }
        for(int i=0;i<data.size();i++){
            if(group.getChildCount()<=i){
                View view=createView(i,data.get(i));
                onBindDataView(view,i,data.get(i),isBindData);
                group.addView(view);
            }else{
                group.getChildAt(i).setVisibility(View.VISIBLE);
                onBindDataView(group.getChildAt(i),i,data.get(i),isBindData);
            }
            if(itemClickListener!=null){
                addOnItemClick(group.getChildAt(i),i);
            }
        }
        if(data.size()<group.getChildCount()){
            for(int i=data.size();i<group.getChildCount();i++){
                group.getChildAt(i).setVisibility(View.GONE);
            }
        }
    }
}

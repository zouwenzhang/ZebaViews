package com.zeba.views.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class GridGroupAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int GROUP_ITEM_TYPE = 1;
    public static final int CHILD_ITEM_TYPE = 2;
    private List<GridGroupHeader> mData = new ArrayList<>();
    private List<GridGroupItem> mShowData=new ArrayList<>();
    private Map<View,GridGroupItem> clickMap=new HashMap<>();

    public GridGroupAdapter() {
    }

    private GridLayoutManager manager;
    public void setRecyclerView(RecyclerView recyclerView, int spanCount){
        manager = new GridLayoutManager(recyclerView.getContext(), spanCount, OrientationHelper.VERTICAL, false);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                //当是group时就让他显示一列，如果是child就让他显示两列
                return getItemViewType(position) == GROUP_ITEM_TYPE ? manager.getSpanCount() : 1;
            }
        });
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(this);
    }

    public void setData(List<GridGroupHeader> list) {
        if(list==null||list.size()==0){
            return;
        }
        mData=list;
        mShowData.clear();
        for(int i=0;i<list.size();i++){
            GridGroupHeader header=list.get(i);
            header.setHeaderPosition(i);
            mShowData.add(header);
            if(header.getItemList().size()==0){
                continue;
            }
            for(int j=0;j<header.getItemList().size();j++){
                mShowData.add(header.getItemList().get(j));
            }
        }
        notifyDataSetChanged();
    }

    public abstract int getGroupLayoutId();
    public abstract int getBodyLayoutId();
    public abstract void convertGroup(GroupViewHolder holder,GridGroupHeader item);
    public abstract void convertBody(BodyHolder holder,GridGroupBody item);

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder holder = null;
        if (viewType == GROUP_ITEM_TYPE) {
            view = LayoutInflater.from(parent.getContext()).inflate(getGroupLayoutId(), parent, false);
            holder = new GroupViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(getBodyLayoutId(), parent, false);
            holder = new BodyHolder(view);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == GROUP_ITEM_TYPE) {
            GroupViewHolder holder1 = (GroupViewHolder) holder;
            convertGroup(holder1,(GridGroupHeader) mShowData.get(position));
        } else {
            BodyHolder holder1 = (BodyHolder) holder;
            convertBody(holder1,(GridGroupBody) mShowData.get(position));
        }
        holder.itemView.setOnClickListener(clickListener);
        clickMap.put(holder.itemView,mShowData.get(position));
    }

    @Override
    public int getItemCount() {
        return mShowData == null ? 0 : mShowData.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mShowData.get(position).getType()==0) {
            return GROUP_ITEM_TYPE;
        } else {
            return CHILD_ITEM_TYPE;
        }
    }

    public List<GridGroupHeader> getData(){
        return mData;
    }

    private View.OnClickListener clickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            GridGroupItem item=clickMap.get(v);
            if(item!=null&&itemListener!=null){
                itemListener.onItemClick(item);
            }
        }
    };

    private GridGroupItemListener itemListener;
    public void setGridGroupItemListener(GridGroupItemListener listener){
        itemListener=listener;
    }

    /**
     * 分组
     */
    public class GroupViewHolder extends RecyclerView.ViewHolder {
        public GroupViewHolder(View itemView) {
            super(itemView);
        }

    }

    /**
     * 成员
     */
    public class BodyHolder extends RecyclerView.ViewHolder {
        public BodyHolder(View itemView) {
            super(itemView);
        }
    }
}


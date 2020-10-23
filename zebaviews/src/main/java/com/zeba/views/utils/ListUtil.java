package com.zeba.views.utils;

import com.zeba.views.SRefreshLayout;
import com.zeba.views.adapter.SQuickAdapter;

import java.util.List;

public class ListUtil {

    public static void refreshData(boolean isRefresh,
                                   SRefreshLayout refreshLayout,
                               SQuickAdapter adapter,
                               List list){
        if(refreshLayout!=null){
            refreshLayout.setRefreshing(false);
        }
        if(!adapter.isLoadMoreEnable()){
            adapter.setEnableLoadMore(true);
        }
        if(list==null){
            return;
        }
        if(list.size()==0&&isRefresh){
            adapter.showEmptyView();
        }else{
            adapter.hideEmptyView();
        }
        if(isRefresh){
            adapter.setNewData(list);
        }else{
            adapter.addData(list);
        }
        adapter.skipPageNo();
        adapter.setLoadEndState(list.size());
    }
}

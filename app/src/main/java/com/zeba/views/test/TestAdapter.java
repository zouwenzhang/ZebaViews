package com.zeba.views.test;

import android.support.annotation.NonNull;

import com.chad.library.adapter.base.BaseViewHolder;
import com.zeba.views.adapter.SQuickAdapter;

import java.util.ArrayList;
import java.util.List;

public class TestAdapter extends SQuickAdapter<String> {

    public TestAdapter() {
        super(R.layout.item_test);
        List<String> list=new ArrayList<>();
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        setNewData(list);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, String item) {

    }
}

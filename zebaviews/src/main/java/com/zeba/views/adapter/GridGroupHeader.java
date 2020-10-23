package com.zeba.views.adapter;

import java.util.ArrayList;
import java.util.List;

public class GridGroupHeader implements GridGroupItem{
    private int headerPosition;
    private List<GridGroupBody> itemList=new ArrayList<>();

    public void addItem(GridGroupBody item){
        item.setItemPosition(itemList.size());
        item.setHeaderPosition(headerPosition);
        itemList.add(item);
    }

    public int getHeaderPosition() {
        return headerPosition;
    }

    public void setHeaderPosition(int headerPosition) {
        this.headerPosition = headerPosition;
        for(int i=0;i<itemList.size();i++){
            itemList.get(i).setHeaderPosition(headerPosition);
        }
    }

    public List<GridGroupBody> getItemList() {
        return itemList;
    }

    public void setItemList(List<GridGroupBody> itemList) {
        this.itemList = itemList;
    }

    @Override
    public int getType() {
        return 0;
    }
}

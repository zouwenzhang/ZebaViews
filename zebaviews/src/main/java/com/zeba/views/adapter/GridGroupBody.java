package com.zeba.views.adapter;

public class GridGroupBody implements GridGroupItem{
    private int headerPosition;
    private int itemPosition;

    public int getHeaderPosition() {
        return headerPosition;
    }

    public void setHeaderPosition(int headerPosition) {
        this.headerPosition = headerPosition;
    }

    public int getItemPosition() {
        return itemPosition;
    }

    public void setItemPosition(int itemPosition) {
        this.itemPosition = itemPosition;
    }

    @Override
    public int getType() {
        return 1;
    }
}

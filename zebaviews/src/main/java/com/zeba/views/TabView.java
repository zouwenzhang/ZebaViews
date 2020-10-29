package com.zeba.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zeba.views.css.AnimCSS;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class TabView extends SLinearLayout{
    private static Map<String, List<TabView>> checkGroup=new HashMap<>();
    private ImageView ivSelect;
    private ImageView ivUnSelect;
    private TextView tvText;
    private boolean isSelect=false;
    private int selectColor;
    private int unSelectColor;
    private String groupId;
    private OnSelectChangeListener selectChangeListener;
    private OnSelectClickListener selectClickListener;
    private String tabId;

    public TabView(Context context) {
        this(context,null);
    }

    public TabView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TabView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context,AttributeSet attrs){
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);
        FrameLayout fl=new FrameLayout(context);
        FrameLayout.LayoutParams lp=new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,FrameLayout.LayoutParams.WRAP_CONTENT);
        lp.gravity= Gravity.CENTER;
        ivUnSelect=new ImageView(context);
        fl.addView(ivUnSelect,lp);
        lp=new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,FrameLayout.LayoutParams.WRAP_CONTENT);
        lp.gravity= Gravity.CENTER;
        ivSelect=new ImageView(context);
        ivSelect.setAlpha(0f);
        fl.addView(ivSelect,lp);
        addView(fl);
        tvText=new TextView(context);
        LinearLayout.LayoutParams lp2=new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        addView(tvText,lp2);
        if(attrs!=null){
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TabView);
            Drawable selectD= array.getDrawable(R.styleable.TabView_selectIcon);
            Drawable unSelectD= array.getDrawable(R.styleable.TabView_unSelectIcon);
            groupId=array.getString(R.styleable.TabView_groupId);
            tabId=array.getString(R.styleable.TabView_tabId);
            if(selectD!=null){
                ivSelect.setImageDrawable(selectD);
            }
            if(unSelectD!=null){
                ivUnSelect.setImageDrawable(unSelectD);
            }
            LayoutParams llp=new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
            float pd= array.getDimension(R.styleable.TabView_iconPadding,0);
            llp.bottomMargin=(int)pd;
            fl.setLayoutParams(llp);
            float textSize=array.getDimension(R.styleable.TabView_android_textSize,14);
            tvText.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);
            selectColor=array.getColor(R.styleable.TabView_selectTextColor, Color.BLACK);
            unSelectColor=array.getColor(R.styleable.TabView_unSelectTextColor, Color.BLACK);
            tvText.setTextColor(unSelectColor);
            String text=array.getString(R.styleable.TabView_android_text);
            tvText.setText(text);
            float iconWidth=array.getDimension(R.styleable.TabView_iconWidth,0);
            float iconHeight=array.getDimension(R.styleable.TabView_iconHeight,0);
            if(iconWidth!=0&&iconHeight!=0){
                ivSelect.getLayoutParams().width=(int)iconWidth;
                ivSelect.getLayoutParams().height=(int)iconHeight;
                ivUnSelect.getLayoutParams().width=(int)iconWidth;
                ivUnSelect.getLayoutParams().height=(int)iconHeight;
                ivSelect.setLayoutParams(ivSelect.getLayoutParams());
                ivUnSelect.setLayoutParams(ivUnSelect.getLayoutParams());
            }
            array.recycle();
        }
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectClickListener!=null){
                    if(!selectClickListener.onClick()){
                        return;
                    }
                }
                if(groupId!=null&&groupId.length()!=0&&isSelect){
                    return;
                }
                setSelected(!isSelect);
                if(selectChangeListener!=null){
                    selectChangeListener.onChange(TabView.this,isSelect,tabId);
                }
            }
        });
    }

    public String getTabId(){
        return tabId;
    }

    public ImageView getSelectV(){
        return ivSelect;
    }

    public ImageView getUnSelectV(){
        return ivUnSelect;
    }

    public TextView getTextV(){
        return tvText;
    }

    public void setOnSelectChangeListener(OnSelectChangeListener listener){
        selectChangeListener=listener;
    }

    public void setOnSelectClickListener(OnSelectClickListener listener){
        selectClickListener=listener;
    }

    public void setSelected(boolean isSelect){
        if(this.isSelect==isSelect){
            return;
        }
        groupChange();
        this.isSelect=isSelect;
        if(isSelect){
            showSelected();
        }else{
            dismissSelected();
        }
    }

    public void showSelected(){
        new AnimCSS()
                .view(ivSelect)
                .style("s:1;sf:0;st:1;ss:s2;d:200;")
                .onStart(new Runnable() {
                    @Override
                    public void run() {
                        ivSelect.setAlpha(1f);
                        tvText.setTextColor(selectColor);
                    }
                }).start();
        new AnimCSS()
                .view(ivUnSelect)
                .style("s:1;sf:1;st:0;ss:s2;d:200;")
                .onEnd(new Runnable() {
                    @Override
                    public void run() {
                        ivUnSelect.setAlpha(0f);
                    }
                }).start();
    }

    public void dismissSelected(){
        new AnimCSS()
                .view(ivUnSelect)
                .style("s:1;sf:0;st:1;ss:s2;d:200;")
                .onStart(new Runnable() {
                    @Override
                    public void run() {
                        ivUnSelect.setAlpha(1f);
                        tvText.setTextColor(unSelectColor);
                    }
                }).start();
        new AnimCSS()
                .view(ivSelect)
                .style("s:1;sf:1;st:0;ss:s2;d:200;")
                .onEnd(new Runnable() {
                    @Override
                    public void run() {
                        ivSelect.setAlpha(0f);
                    }
                }).start();
    }

    public interface OnSelectChangeListener{
        void onChange(TabView view,boolean isSelect,String tabId);
    }

    public interface OnSelectClickListener{
        boolean onClick();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if(groupId!=null&&groupId.length()!=0){
            if(!checkGroup.containsKey(groupId)){
                List<TabView> list=new LinkedList<>();
                checkGroup.put(groupId,list);
            }
            checkGroup.get(groupId).add(this);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if(groupId!=null&&groupId.length()!=0){
            if(checkGroup.containsKey(groupId)){
                checkGroup.get(groupId).remove(this);
            }
        }
    }

    private boolean groupChange(){
        if(groupId!=null&&checkGroup.containsKey(groupId)){
            List<TabView> list=checkGroup.get(groupId);
            for(TabView v:list){
                if(v!=this){
                    v.setSelected(false);
                }
            }
            return true;
        }
        return false;
    }

    public static void setSelectGroup(String groupId,String tabId){
        if(groupId!=null&&checkGroup.containsKey(groupId)){
            List<TabView> list=checkGroup.get(groupId);
            for(TabView v:list){
                if(v.tabId.equals(tabId)){
                    v.setSelected(true);
                }else{
                    v.setSelected(false);
                }
            }
        }
    }
}

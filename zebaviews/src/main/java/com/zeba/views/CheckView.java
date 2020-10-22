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

import com.zeba.views.utils.AnimCSS;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class CheckView extends SLinearLayout{
    private static Map<String, List<CheckView>> checkGroup=new HashMap<>();
    private ImageView ivSelect;
    private ImageView ivUnSelect;
    private TextView tvText;
    private boolean isSelect=false;
    private Field dataField;
    private Object dataObj;
    private Object selectValue;
    private Object unSelectValue;
    private String groupId;
    private OnSelectChangeListener selectChangeListener;
    private OnSelectClickListener selectClickListener;

    public CheckView(Context context) {
        this(context,null);
    }

    public CheckView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CheckView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context,AttributeSet attrs){
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);
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
        addView(tvText);
        if(attrs!=null){
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CheckView);
            Drawable selectD= array.getDrawable(R.styleable.CheckView_selectIcon);
            Drawable unSelectD= array.getDrawable(R.styleable.CheckView_unSelectIcon);
            groupId=array.getString(R.styleable.CheckView_groupId);
            if(selectD!=null){
                ivSelect.setImageDrawable(selectD);
            }
            if(unSelectD!=null){
                ivUnSelect.setImageDrawable(unSelectD);
            }
            LinearLayout.LayoutParams llp=new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
            float pd= array.getDimension(R.styleable.CheckView_iconPadding,0);
            llp.rightMargin=(int)pd;
            fl.setLayoutParams(llp);
            float textSize=array.getDimension(R.styleable.CheckView_android_textSize,14);
            tvText.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);
            int color=array.getColor(R.styleable.CheckView_android_textColor, Color.BLACK);
            tvText.setTextColor(color);
            String text=array.getString(R.styleable.CheckView_android_text);
            tvText.setText(text);
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
                    selectChangeListener.onChange(isSelect);
                }
            }
        });
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

    public void bindData(Object obj,String name,Integer selectValue){
        bindData(obj,name,selectValue,null);
    }

    public void bindData(Object obj,String name){
        bindData(obj,name,new Integer(1),new Integer(0));
    }

    public void bindData(Object obj,String name,Object selectValue,Object unSelectValue){
        this.selectValue=selectValue;
        this.unSelectValue=unSelectValue;
        try {
            dataField=obj.getClass().getDeclaredField(name);
            dataField.setAccessible(true);
            if(dataField.getType()==Integer.class){
                Integer v= (Integer) dataField.get(obj);
                if(v!=null&&v.intValue()==(int)selectValue){
                    setSelected(true);
                }
            }else if(dataField.getType()==String.class){
                String v=(String)dataField.get(obj);
                if(v!=null&&selectValue.equals(v)){
                    setSelected(true);
                }
            }else if(dataField.getType()==Boolean.class){
                Boolean v=dataField.getBoolean(obj);
                if(v!=null&&v){
                    setSelected(true);
                }
            }
            dataObj=obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        try{
            if(dataField!=null&&dataObj!=null){
                if(isSelect){
                    if(selectValue!=null){
                        dataField.set(dataObj,selectValue);
                    }
                }else{
                    if(unSelectValue!=null){
                        dataField.set(dataObj,unSelectValue);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
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
        void onChange(boolean isSelect);
    }

    public interface OnSelectClickListener{
        boolean onClick();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if(groupId!=null&&groupId.length()!=0){
            if(!checkGroup.containsKey(groupId)){
                List<CheckView> list=new LinkedList<>();
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

    protected boolean groupChange(){
        if(groupId!=null&&checkGroup.containsKey(groupId)){
            List<CheckView> list=checkGroup.get(groupId);
            for(CheckView v:list){
                if(v!=this){
                    v.setSelected(false);
                }
            }
            return true;
        }
        return false;
    }
}

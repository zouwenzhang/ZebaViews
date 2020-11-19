package com.zeba.views.attr;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.zeba.views.R;
import com.zeba.views.css.CSSFormat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SAttrText extends SAttr{

    public List<Map<String,String>> span=new ArrayList<>();

    public SAttrText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onAttr(AttributeSet attrs, TypedArray typedArray) {
        super.onAttr(attrs, typedArray);
        String spans= typedArray.getString(R.styleable.ShapeTextView_spanCss);
        span= CSSFormat.formList(spans);
    }

    public void setSpan(List<Map<String, String>> span) {
        this.span = span;
    }
}

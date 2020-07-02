package com.zeba.views.test;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.zeba.base.BaseDialog;
import com.zeba.base.utils.MoneyUtil;

public class GrabTopOutPriceDialog extends BaseDialog {

    private EditText etPrice;

    public GrabTopOutPriceDialog(@NonNull Context context) {
        super(context);

    }

    @Override
    protected int getContentViewId() {
        return R.layout.dialog_grab_top_out_price;
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        etPrice=view.findViewById(R.id.et_price);
        view.findViewById(R.id.tv_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        setShowGravity(Gravity.BOTTOM);
    }


}

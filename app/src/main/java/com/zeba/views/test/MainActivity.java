package com.zeba.views.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.zeba.views.SHeaderListView;
import com.zeba.views.click.ShapeTextView;
import com.zeba.views.css.AnimCSS;

public class MainActivity extends AppCompatActivity {

    private ShapeTextView tv1;
    private AnimCSS animCSS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AnimCSS.open();
        setContentView(R.layout.activity_main);//@BindLayout
//        tv1=findViewById(R.id.tv_button);
//        findViewById(R.id.tv_button).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.e("zwz","aaaa");
//                Toast.makeText(MainActivity.this,"click1",Toast.LENGTH_SHORT).show();
//                if(tv1.isLoading()){
//                    tv1.dismissLoading();
//                }else {
//                    tv1.showLoading();
//                }
//            }
//        });
//        findViewById(R.id.tv_button2).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MainActivity.this,"click2",Toast.LENGTH_SHORT).show();
//                tv1.dismissLoading();
//            }
//        });
        getWindow().getDecorView().findViewWithTag("tv_scale_test").setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        findViewById(R.id.tv_button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(MainActivity.this,"click3",Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(MainActivity.this,WebActivity.class));
//                new GrabTopOutPriceDialog(MainActivity.this).show();
//                animCSS.start();
            }
        });
//        tv1.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                tv1.setText("adfsd123");
//            }
//        },200);
//        TestAdapter adapter=new TestAdapter();
//        SHeaderListView listView=findViewById(R.id.h_list_view);
//        adapter.bindToRecyclerView(listView.getRvList());
    }
}

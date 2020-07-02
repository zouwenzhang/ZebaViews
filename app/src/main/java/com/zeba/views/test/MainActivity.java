package com.zeba.views.test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.zeba.views.click.ShapeTextView;
import com.zeba.views.utils.AnimCSS;

public class MainActivity extends AppCompatActivity {

    private ShapeTextView tv1;
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
        findViewById(R.id.tv_button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(MainActivity.this,"click3",Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(MainActivity.this,WebActivity.class));
                new GrabTopOutPriceDialog(MainActivity.this).show();
            }
        });
        tv1=findViewById(R.id.tv_title);
        tv1.postDelayed(new Runnable() {
            @Override
            public void run() {
                tv1.setText("adfsd123");
            }
        },200);
    }
}

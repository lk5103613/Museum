package com.shmuseum.musesum;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.shmuseum.adapter.GoodsDescriptionAdapter;
import com.shmuseum.customeview.CircleFlowIndicator;
import com.shmuseum.customeview.ViewFlow;
import com.shmuseum.entity.GoodInfo;

public class GoodsDescriptionActivity extends Activity {
    private ViewFlow viewFlow;
    private CircleFlowIndicator indicator;
    private List<GoodInfo> goods;
    private GoodsDescriptionAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        int flag= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        super.onCreate(savedInstanceState);
        Window window=this.getWindow();
        window.setFlags(flag, flag);
        setContentView(R.layout.activity_goods_description);
        initView();

        View v=findViewById(R.id.content);
        Intent intent=getIntent();
        if(intent!=null) {
            Bitmap bitmap=intent.getParcelableExtra("bitmap");
            v.setBackgroundDrawable(new BitmapDrawable(getResources(), bitmap));
        }
    }

    private void initView(){
        viewFlow = (ViewFlow) findViewById(R.id.viewflow);
        indicator = (CircleFlowIndicator) findViewById(R.id.indicator);

        initDataSource();

        indicator.setColors(Color.parseColor("#EBD8A0"), Color.parseColor("#B1A68A"), 7, 2);
        viewFlow.setFlowIndicator(indicator);
        viewFlow.setAdapter(adapter);
    }

    public void back(View v) {
        this.finish();
    }

    private void initDataSource(){
        goods = new ArrayList<>();
        goods.add(new GoodInfo(R.drawable.goods_description, "榫卯", "        中国家具把各个部件连接起来的\"榫卯\"做法是家具制造的主要结构方式。各种榫卯做法不同，应用范围不同，但他们在每件家具上都具有形体结构的\"关节\"作用。\r\n        若榫卯使用得当，两块木结构之间就能严密扣合，达到\"天衣无缝\"的成图。它是古代木匠必须具备的基本技能，工匠手艺的高低，通过榫卯的结构就能清楚的反映出来。"));

        adapter = new GoodsDescriptionAdapter(this, goods);
    }
}

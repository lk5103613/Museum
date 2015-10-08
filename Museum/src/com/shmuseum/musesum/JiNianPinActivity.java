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
import android.widget.ImageView;
import android.widget.TextView;

import com.shmuseum.adapter.IntroAdapter;
import com.shmuseum.customeview.CircleFlowIndicator;
import com.shmuseum.customeview.ViewFlow;
import com.shmuseum.entity.GoodInfo;


public class JiNianPinActivity extends Activity {

    private ImageView backIV;
    private TextView titleTV;
    private ViewFlow viewFlow;
    private CircleFlowIndicator indicator;

    private List<GoodInfo> goods;
    private IntroAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        int flag= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        super.onCreate(savedInstanceState);
        Window window=this.getWindow();
        window.setFlags(flag, flag);

        setContentView(R.layout.activity_ji_nian_pin);
        initView();

        View v=findViewById(R.id.content);
        Intent intent=getIntent();
        if(intent!=null) {
            Bitmap bitmap=intent.getParcelableExtra("bitmap");
            v.setBackgroundDrawable(new BitmapDrawable(getResources(), bitmap));
        }

    }

    private void initView(){
        backIV = (ImageView) findViewById(R.id.imageview1);
        titleTV = (TextView) findViewById(R.id.textview1);
        viewFlow = (ViewFlow) findViewById(R.id.viewflow);
        indicator = (CircleFlowIndicator) findViewById(R.id.indicator);

        initDataSource();

        backIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                finish();
            }
        });
        indicator.setColors(Color.parseColor("#EBD8A0"), Color.parseColor("#B1A68A"), 7, 2);
        viewFlow.setFlowIndicator(indicator);
        viewFlow.setAdapter(adapter);
    }

    /**
     * 初始化数据
     */
    private void initDataSource(){
        goods = new ArrayList<GoodInfo>();
        goods.add(new GoodInfo(R.drawable.img_good, "桌旗八吉祥", "    古时有个秀才，几步成诗，诗才可比曹植。一天，秀才在西湖边赏景时，看到对面一位婀娜多姿的姑娘款款而来。秀才诗兴大发，吟诗两句：“远见一姑娘，金莲三寸长”。姑娘心中暗喜，秀才如此赞美我。但是且慢，请往下听第三第四句：“为何这般短——横量”     ", "Լ240x34cm", "人民币750元"));
        goods.add(new GoodInfo(R.drawable.img_good, "桌旗八吉祥", "    古时有个秀才，几步成诗，诗才可比曹植。一天，秀才在西湖边赏景时，看到对面一位婀娜多姿的姑娘款款而来。秀才诗兴大发，吟诗两句：“远见一姑娘，金莲三寸长”。姑娘心中暗喜，秀才如此赞美我。但是且慢，请往下听第三第四句：“为何这般短——横量”     ", "Լ240x34cm", "人民币750元"));
        goods.add(new GoodInfo(R.drawable.img_good, "桌旗八吉祥", "    古时有个秀才，几步成诗，诗才可比曹植。一天，秀才在西湖边赏景时，看到对面一位婀娜多姿的姑娘款款而来。秀才诗兴大发，吟诗两句：“远见一姑娘，金莲三寸长”。姑娘心中暗喜，秀才如此赞美我。但是且慢，请往下听第三第四句：“为何这般短——横量”     ", "Լ240x34cm", "人民币750元"));
        goods.add(new GoodInfo(R.drawable.img_good, "桌旗八吉祥", "    古时有个秀才，几步成诗，诗才可比曹植。一天，秀才在西湖边赏景时，看到对面一位婀娜多姿的姑娘款款而来。秀才诗兴大发，吟诗两句：“远见一姑娘，金莲三寸长”。姑娘心中暗喜，秀才如此赞美我。但是且慢，请往下听第三第四句：“为何这般短——横量”     ", "Լ240x34cm", "人民币750元"));
        goods.add(new GoodInfo(R.drawable.img_good, "桌旗八吉祥", "    古时有个秀才，几步成诗，诗才可比曹植。一天，秀才在西湖边赏景时，看到对面一位婀娜多姿的姑娘款款而来。秀才诗兴大发，吟诗两句：“远见一姑娘，金莲三寸长”。姑娘心中暗喜，秀才如此赞美我。但是且慢，请往下听第三第四句：“为何这般短——横量”     ", "Լ240x34cm", "人民币750元"));

        adapter = new IntroAdapter(this, goods);
    }

}

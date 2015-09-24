package com.shmuseum.musesum;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ShowDetailActivity extends Activity {
    private int mMenuType = -1;
    private TextView mTxtMenuIndicator;
    private ImageView mapImg;

    @Override
    protected void onResume() {
        super.onResume();
        overridePendingTransition(R.anim.in_from_right, R.anim.out_from_right);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);

        mTxtMenuIndicator = (TextView) findViewById(R.id.menu_indicator);
        mapImg = (ImageView) findViewById(R.id.map);

        Intent intent = getIntent();
        mMenuType = intent.getIntExtra(GuideActivity.MENU_TYPE, -1);
        setMenuType(mMenuType);
    }

    private void setMenuType(int type) {
        switch (type) {
            case GuideActivity.MENU_EVELATOR:
                setDrawableLeft(R.drawable.elevator);
                mTxtMenuIndicator.setText("升降电梯-博物馆4楼北侧");
                mapImg.setImageResource(R.drawable.map);
                break;
            case GuideActivity.MENU_GIFT:
                setDrawableLeft(R.drawable.gift);
                mapImg.setImageResource(R.drawable.map);
                break;
            case GuideActivity.MENU_TEA:
                setDrawableLeft(R.drawable.map);
                mTxtMenuIndicator.setText("茶楼-博物馆2楼西侧");
                mapImg.setImageResource(R.drawable.map);
                break;
            case GuideActivity.MENU_WC:
                setDrawableLeft(R.drawable.wc);
                mTxtMenuIndicator.setText("公共卫生间-博物馆4楼西侧");
                mapImg.setImageResource(R.drawable.map);
                break;
        }
    }

    private void setDrawableLeft(int drawable) {
        Drawable drawable1 = getResources().getDrawable(drawable);
        drawable1.setBounds(0, 0, 80, 80);
        mTxtMenuIndicator.setCompoundDrawables(drawable1, null, null, null);
        
    }

    public void back(View v) {
        this.finish();
    }

}

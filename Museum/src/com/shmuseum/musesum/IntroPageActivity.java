package com.shmuseum.musesum;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class IntroPageActivity extends Activity {

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro_page);

        mContext = this;
    }

    public void toGuide(View v) {
        Intent intent = new Intent(mContext, GuideActivity.class);
        startActivity(intent);
    }

}

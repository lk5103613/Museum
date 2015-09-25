package com.shmuseum.musesum;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class WelcomeActivity extends Activity {

	private Context mContext;
	private Handler mHandler;
	private ImageView mWelcomeImg;
	private Animation mFadeOut;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		mContext = this;

		mWelcomeImg = (ImageView) findViewById(R.id.welcome_img);
		mFadeOut = AnimationUtils.loadAnimation(mContext, R.anim.fade_out);
		mFadeOut.setFillAfter(true);

		mHandler = new Handler();

		mFadeOut.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				Intent intent = new Intent(mContext, IntroPageActivity.class);
				startActivity(intent);
				WelcomeActivity.this.finish();
			}
		});

		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				mWelcomeImg.startAnimation(mFadeOut);
			}
		}, 500);
	}

}

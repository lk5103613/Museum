package com.shmuseum.musesum;

import java.lang.reflect.Field;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.animation.LinearInterpolator;

import com.shmuseum.adapter.IndexPagerAdapter;
import com.shmuseum.customeview.FixedSpeedScroller;
import com.shmuseum.customeview.HackyViewPager;
import com.shmuseum.fragment.IPagerListener;
import com.shmuseum.transform.ZoomOutPageTransformer;

public class IndexActivity extends FragmentActivity implements IPagerListener {
	private HackyViewPager myViewPager;
	private IndexPagerAdapter mAdapter;
	private FragmentManager mFm;
	public static final int FRAGMENT_GUIDE = 0;
	public static final int FRAGMENT_RECOMMEND = FRAGMENT_GUIDE + 1;
	private int mCurrentItem = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_index_layout);
		myViewPager = (HackyViewPager) findViewById(R.id.index_pager);
		mFm = getSupportFragmentManager();
		mAdapter = new IndexPagerAdapter(mFm);
		myViewPager.setAdapter(mAdapter);
		myViewPager.setLocked(true);
		myViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
		myViewPager.setPageMargin(0);
		setViewPagerScrollSpeed();
		
		myViewPager.setCurrentItem(2);
	}

	@Override
	public void changePage() {
		mCurrentItem--;
		myViewPager.setCurrentItem(mCurrentItem);
	}

	@Override
	public void onBackPressed() {
		if(mCurrentItem == 2) 
			finish();
		else {
			mCurrentItem++;
			myViewPager.setCurrentItem(mCurrentItem);
		}
	}

	/**
	 * 设置ViewPager的滑动速度
	 * 
	 * */
	private void setViewPagerScrollSpeed() {
		try {
			Field mScroller = null;
			mScroller = ViewPager.class.getDeclaredField("mScroller");
			mScroller.setAccessible(true);
			FixedSpeedScroller scroller = new FixedSpeedScroller(
					myViewPager.getContext(), new LinearInterpolator());
			mScroller.set(myViewPager, scroller);
		} catch (Exception e) {

		}	
	}
	
	@Override
	protected void onDestroy() {
		myViewPager.destroyDrawingCache();
		super.onDestroy();
	}

}

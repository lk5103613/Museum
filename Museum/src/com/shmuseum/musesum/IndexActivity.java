package com.shmuseum.musesum;

import java.lang.reflect.Field;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
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
		
	}

	@Override
	public void changePage(int index) {
		myViewPager.setCurrentItem(index);

	}

	@Override
	public void onBackPressed() {
		// super.onBackPressed();
		switch (myViewPager.getCurrentItem()) {
		case FRAGMENT_GUIDE:
			finish();
			break;
		case FRAGMENT_RECOMMEND:
			myViewPager.setCurrentItem(FRAGMENT_GUIDE);
			break;

		default:
			this.finish();
			break;
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
		} catch (NoSuchFieldException e) {

		} catch (IllegalArgumentException e) {

		} catch (IllegalAccessException e) {

		}
	}

}

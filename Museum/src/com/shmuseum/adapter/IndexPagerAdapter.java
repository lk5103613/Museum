package com.shmuseum.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.shmuseum.fragment.GuideFragment;
import com.shmuseum.fragment.RecommendPathFragment;

public class IndexPagerAdapter extends FragmentPagerAdapter{

	private GuideFragment mGuideFragment = new GuideFragment();
	private RecommendPathFragment mRecommendPathFragment = new RecommendPathFragment();
	
	private Fragment[] mFragments = new Fragment[]{mGuideFragment,mRecommendPathFragment};

	
	public IndexPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {
		return mFragments[index];
	}

	@Override
	public int getCount() {
		return mFragments.length;
	}
	
	public void destory(){
		System.out.println("adapter destory");
		((ViewGroup)mGuideFragment.getView()).removeAllViews();
		((ViewGroup)mRecommendPathFragment.getView()).removeAllViews();
		mFragments = null;
		mGuideFragment = null;
		mRecommendPathFragment = null;
	}

}

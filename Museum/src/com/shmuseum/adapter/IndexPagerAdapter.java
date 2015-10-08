package com.shmuseum.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.shmuseum.fragment.GuideFragment;
import com.shmuseum.fragment.IndexFragment;
import com.shmuseum.fragment.RecommendPathFragment;

public class IndexPagerAdapter extends FragmentStatePagerAdapter{

	private IndexFragment mIndex1 = new IndexFragment();
	private GuideFragment mGuideFragment = new GuideFragment();
	private RecommendPathFragment mRecommendPathFragment = new RecommendPathFragment();
	
	private Fragment[] mFragments = new Fragment[]{mRecommendPathFragment,mGuideFragment,mIndex1};
	
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

}

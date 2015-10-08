package com.shmuseum.transform;

import com.shmuseum.customeview.FoldingLayout;
import com.shmuseum.fragment.OnFoldListener;

import android.view.View;
import android.view.ViewGroup;
import android.annotation.SuppressLint;
import android.support.v4.view.ViewPager;

public class ZoomOutPageTransformer implements ViewPager.PageTransformer {
	private static final float MIN_SCALE = 0.85f;
	private static final float MIN_ALPHA = 0.5f;

	@SuppressLint("NewApi")
	public void transformPage(final View view, float position) {
		int pageWidth = view.getWidth();

		FoldingLayout foldingLayout = (FoldingLayout) ((ViewGroup) view)
				.getChildAt(0);

		foldingLayout.setNumberOfFolds(5);
		foldingLayout.setFoldListener(new OnFoldListener() {

			@Override
			public void onStartFold() {
			}

			@Override
			public void onEndFold() {
			}

		});

		 if (position ==-1) {
				view.setTranslationX(-pageWidth);
			}else if (position < 0) {
			view.setTranslationX(pageWidth * -position);
			foldingLayout.setFoldFactor(-position);
			 
		} else if (position > 0) {
			foldingLayout.setFoldFactor(position);
		} else if (position==0) {
			foldingLayout.setFoldFactor(0);
			view.setTranslationX(0);
		}

	}
	
	
	
}

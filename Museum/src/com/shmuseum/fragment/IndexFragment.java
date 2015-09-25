package com.shmuseum.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.shmuseum.musesum.R;

public class IndexFragment extends Fragment {
	
	private ImageView mIndexImg;
	private ImageView mLevel1;
	private ImageView mLevel2;
	private ImageView mLevel3;
	private ImageView mLevel4;
	private ImageView mStepText;
	private ImageView mTextDes;
	private IPagerListener mListener;
	private Handler mHandler;
	private int mAnimDuration = 1000;
	
	private Animation mLevelAnim;
	private Animation mLevelAnim2;
	private Animation mLevelAnim3;
	private Animation mLevelAnim4;
	private Animation mTextIn;
	private Animation mTextDesAnim;
	private ImageView[] mLevels;
	private Animation[] mAnims;
	private int mCurrentLevel = 0;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_index, container, false);
		mHandler = new Handler(new Callback() {
			@Override
			public boolean handleMessage(Message msg) {
				mLevels[msg.what].setVisibility(View.VISIBLE);
				mLevels[msg.what].startAnimation(mAnims[msg.what]);
				return false;
			}
		});
		mLevel1 = (ImageView) view.findViewById(R.id.level1);
		mLevel2 = (ImageView) view.findViewById(R.id.level2);
		mLevel3 = (ImageView) view.findViewById(R.id.level3);
		mLevel4 = (ImageView) view.findViewById(R.id.level4);
		mLevels = new ImageView[]{mLevel4, mLevel3, mLevel2, mLevel1};
		mTextIn = AnimationUtils.loadAnimation(getActivity(), R.anim.text_move_in);
		mTextDesAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.des_move_in);
		mLevelAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.level_in);
		mLevelAnim2 = AnimationUtils.loadAnimation(getActivity(), R.anim.level_in);
		mLevelAnim3 = AnimationUtils.loadAnimation(getActivity(), R.anim.level_in);
		mLevelAnim4 = AnimationUtils.loadAnimation(getActivity(), R.anim.level_in);
		mStepText = (ImageView) view.findViewById(R.id.step_4_text);
		mTextDes = (ImageView) view.findViewById(R.id.text_des);
		mAnims = new Animation[]{mLevelAnim, mLevelAnim2, mLevelAnim3, mLevelAnim4};
		mLevelAnim.setFillAfter(true);
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (mCurrentLevel < mLevels.length) {
					mHandler.sendEmptyMessage(mCurrentLevel);
					try {
						Thread.sleep(800);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					mCurrentLevel++;
				}
			}
		}).start();
		mStepText.startAnimation(mTextIn);
		mTextDes.startAnimation(mTextDesAnim);
		mLevel1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mListener.changePage();
			}
		});
		return view;
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mListener = (IPagerListener) activity;
	}

}

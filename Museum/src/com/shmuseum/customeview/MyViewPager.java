package com.shmuseum.customeview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.volley.toolbox.ImageLoader;
import com.shmuseum.adapter.PhotoPagerAdapter;
import com.shmuseum.musesum.R;

/**
 * 包含左右滑动图片以及下方小圆点的组合控件
 */
public class MyViewPager extends RelativeLayout {
    private Context mContext;
    private LayoutInflater mInflater;
    private LinearLayout mDotsContainer;
    private HackyViewPager mViewPager;
    private ImageView[] mDots;
    private String[] mPaths;
    private int mCurrentPosition = -1;
    private View[] mImgs;
    private FullScreenDialog mDialog;

    public MyViewPager(Context context) {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public MyViewPager(Context context, AttributeSet attributeSet, int style) {
        super(context, attributeSet, style);
    }

    public void init(Context context, String[] paths, View[] imgs, FullScreenDialog dialog, ImageLoader imageLoader) {
        this.mCurrentPosition = -1;
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        this.mPaths = paths;
        this.mImgs = imgs;
        this.mDialog = dialog;
        View v = mInflater.inflate(R.layout.page_view, this, true);
        mDotsContainer = (LinearLayout) v.findViewById(R.id.dots_container);
        mViewPager = (HackyViewPager) v.findViewById(R.id.photo_view_pager);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                setCurDot(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
        mViewPager.setAdapter(new PhotoPagerAdapter(mContext, mPaths, mImgs, dialog, imageLoader));
        initDots(context);
    }

    /**
     * 初始化指示点
     * @param context
     */
    private void initDots(Context context) {
        mDotsContainer.removeAllViews();
        mDots = new ImageView[mPaths.length];
        for(int i=0; i< mPaths.length; i++) {
            final int finalI = i;
            ImageView img = new ImageView(context);
            img.setImageResource(R.drawable.dot_selector);
            img.setPadding(30, 30, 30, 30);
            img.setClickable(true);
            img.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    setCurDot(finalI);
                    setCurView(finalI);
                }
            });
            mDots[i] = img;
            mDotsContainer.addView(img);
            setCurDot(0);
        }
    }

    /**
     * 设置指示点当前位置
     * @param position 指示点位置
     */
    private void setCurDot(int position) {
        if (position < 0 || position > mDots.length - 1 || mCurrentPosition == position) {
            return;
        }
        mDots[position].setEnabled(false);
        if(mCurrentPosition != -1)
            mDots[mCurrentPosition].setEnabled(true);
        mCurrentPosition = position;
    }

    /**
     * 设置图片显示位置
     * @param position 图片显示位置
     */
    private void setCurView(int position) {
        if (position < 0 || position >= mDots.length) {
            return;
        }
        mViewPager.setCurrentItem(position);
    }

    public void setCurrentPosition(int position) {
        setCurDot(position);
        setCurView(position);
        mCurrentPosition = position;
    }

}

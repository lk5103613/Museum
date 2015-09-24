package com.shmuseum.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;
import com.shmuseum.customeview.FullScreenDialog;
import com.shmuseum.musesum.R;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class PhotoPagerAdapter extends PagerAdapter {

    private Context mContext;
    private String[] mPaths;
    private View[] mImgs;
    private FullScreenDialog mDialog;
    private ImageLoader mImageLoader;

    public PhotoPagerAdapter(Context context, String[] paths, View[] imgs, FullScreenDialog mDialog,
                             ImageLoader imageLoader) {
        this.mContext = context;
        this.mPaths = paths;
        this.mImgs = imgs;
        this.mDialog = mDialog;
        this.mImageLoader = imageLoader;
    }

    @Override
    public int getCount() {
        return mPaths.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == o;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = null;
        if(mImgs == null || mImgs.length == 0) {
            view = new PhotoView(mContext);
            ((PhotoView)view).setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
                @Override
                public void onViewTap(View view, float v, float v1) {
                    if(mDialog.isShowing())
                        mDialog.dismiss();
                }
            });
        } else {
            view = mImgs[position];
        }
        mImageLoader.get(mPaths[position], ImageLoader.getImageListener((ImageView)view,
                R.color.white, R.color.white));
//        ((ImageView) view).setImageResource(mDrawables[position]);
        container.addView(view, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }
}

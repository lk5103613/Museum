package com.shmuseum.fragment;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoViewAttacher;
import uk.co.senab.photoview.PhotoViewAttacher.OnPhotoTapListener;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.nineoldandroids.animation.ObjectAnimator;
import com.shmuseum.customeview.DoteView;
import com.shmuseum.entity.MapPoint;
import com.shmuseum.musesum.GoodsDetailActivity;
import com.shmuseum.musesum.R;
import com.shmuseum.musesum.ShowDetailActivity;
import com.shmuseum.musesum.SittingRoomActivity;
import com.shmuseum.musesum.StudyRoomActivity;
import com.shmuseum.utils.DensityUtil;

public class GuideFragment extends Fragment implements View.OnClickListener {

	public static final String MENU_TYPE = "menu_type";
	public static final String ICON_INDEX = "icon_index";

	public static final int MENU_WC = 0;
	public static final int MENU_TEA = 1;
	public static final int MENU_GIFT = 2;
	public static final int MENU_EVELATOR = 3;

	private Context mContext;
	private Boolean mIsBottomShow = false;
	private Boolean mNeedEnterAnimation = false;
	private List<MapPoint> markers;
	private List<MapPoint> photoPoints;
	private Handler mHandler;

	private List<MapPoint> biggerPoints;
	private List<MapPoint> biggers;

	private LinearLayout mSlideView;
	private ObjectAnimator mSlideUp;
	private ObjectAnimator mSlideDown;
	private TextView mBtnShowBottom;
	private LinearLayout mMenuTea;
	private TextView mShowPath;

	private DoteView doteView;
	private Bitmap bmp;
	private IPagerListener mPagerListener;
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mPagerListener = (IPagerListener)activity;
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_guide, container, false);
		mContext = getActivity();
		mHandler = new Handler();

		mSlideView = (LinearLayout) view.findViewById(R.id.slide_up_menu);
		doteView = (DoteView) view.findViewById(R.id.dote_view_1);
		doteView.setMarkerResourceId(R.drawable.marker2);
		doteView.setRedMarkerResourceId(R.drawable.marker2_selected);
		doteView.setVisibility(View.INVISIBLE);
		mBtnShowBottom = (TextView) view.findViewById(R.id.btn_show_bottom);
		mMenuTea = (LinearLayout) view.findViewById(R.id.menu_tea);
		mShowPath = (TextView) view.findViewById(R.id.show_path);
		
		mBtnShowBottom.setOnClickListener(this);
		mMenuTea.setOnClickListener(this);
		mShowPath.setOnClickListener(this);

		float px = DensityUtil.dip2px(mContext, 60f);
		mSlideDown = ObjectAnimator.ofFloat(mSlideView, "translationY", -px,
				px + 10);
		mSlideDown.setDuration(300);
		mSlideUp = ObjectAnimator.ofFloat(mSlideView, "translationY", px, -px);
		mSlideUp.setDuration(300);

		doteView.postDelayed(new Runnable() {
			@Override
			public void run() {
				setCacheBitmap();
				LayoutParams params = (LayoutParams) doteView.getLayoutParams();
				params.width = LayoutParams.MATCH_PARENT;
				drawMarkers();
				drawBiggers();
				mHandler.postDelayed(new Runnable() {
					@Override
					public void run() {
						doteView.setOnPhotoTapListener(new OnPhotoTapListener() {
							@Override
							public void onPhotoTap(View view, float x, float y) {
								System.out.println(" x    " + x + "     y" + y);
							}
						});
						doteView.setScale(DoteView.DEFAULT_SCALE, false);
						doteView.setVisibility(View.VISIBLE);
					}
				}, 0);
			}
		}, 0);

		doteView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
			@Override
			public void onPhotoTap(View view, float x, float y) {
				int i = 0;
				for (MapPoint mp : biggerPoints) {
					float minMpX = mp.x - 0.04f;
					float maxMpX = mp.x + 0.04f;
					float minMpY = mp.y - 0.04f;
					float maxMpY = mp.y + 0.04f;
					if ((x >= minMpX && x <= maxMpX)
							&& (y >= minMpY && y <= maxMpY)) {
						Intent intent = null;
						if (biggers.get(i).id == 1)
							intent = new Intent(mContext,
									StudyRoomActivity.class);
						else
							intent = new Intent(mContext,
									SittingRoomActivity.class);
						startActivity(intent);
						return;
					}
					i++;
				}

				i = 0;
				for (MapPoint mp : photoPoints) {
					float minMpX = mp.x - 0.04f;
					float maxMpX = mp.x + 0.04f;
					float minMpY = mp.y - 0.04f;
					float maxMpY = mp.y + 0.04f;
					if ((x >= minMpX && x <= maxMpX)
							&& (y >= minMpY && y <= maxMpY)) {
						Intent intent = new Intent(mContext,
								GoodsDetailActivity.class);
						intent.putExtra(ICON_INDEX, markers.get(i).id);
						startActivity(intent);
						return;
					}
					i++;
				}
			}
		});
		return view;
	}

	public void drawMarkers() {
		markers = new ArrayList<MapPoint>();
		photoPoints = new ArrayList<MapPoint>();

		// 裁剪之后
		MapPoint mp1 = new MapPoint(0.43644f, 0.7016702f, 1);
		MapPoint mp2 = new MapPoint(0.46812f, 0.200395f, 5);
		MapPoint mp3 = new MapPoint(0.540316f, 0.208543f, 7);
		MapPoint mp5 = new MapPoint(0.7015491f, 0.27355f, 13);
		MapPoint mp6 = new MapPoint(0.70187f, 0.35099f, 16);
		MapPoint mp7 = new MapPoint(0.70187f, 0.37627f, 17);
		MapPoint mp8 = new MapPoint(0.70187f, 0.39627f, 18);
		MapPoint mp9 = new MapPoint(0.595474f, 0.4523f, 20);
		MapPoint mp10 = new MapPoint(0.192827f, 0.705442f, 28);
		MapPoint mp11 = new MapPoint(0.192827f, 0.728f, 29);
		MapPoint mp12 = new MapPoint(0.196128f, 0.762579f, 30);
		MapPoint mp13 = new MapPoint(0.196128f, 0.796797f, 31);
		MapPoint mp14 = new MapPoint(0.7645705f, 0.676895f, 40);
		MapPoint mp15 = new MapPoint(0.61284f, 0.7782f, 38);
		MapPoint mp16 = new MapPoint(0.6509665f, 0.6249648f, 41);
		MapPoint mp17 = new MapPoint(0.6896772f, 0.60352486f, 44);
		MapPoint mp18 = new MapPoint(0.2009f, 0.798f);
		MapPoint mp19 = new MapPoint(0.2009f, 0.798f);
		MapPoint mp20 = new MapPoint(0.2009f, 0.798f);
		MapPoint mp21 = new MapPoint(0.2009f, 0.798f);
		MapPoint mp22 = new MapPoint(0.2009f, 0.798f);
		MapPoint mp23 = new MapPoint(0.2009f, 0.798f);
		MapPoint mp24 = new MapPoint(0.2009f, 0.798f);
		
		photoPoints.add(mp1);
		photoPoints.add(mp2);
		photoPoints.add(mp3);
		photoPoints.add(mp5);
		photoPoints.add(mp6);
		photoPoints.add(mp7);
		photoPoints.add(mp8);

		int i = 1;
		for (MapPoint mp : photoPoints) {
			MapPoint newMp = new MapPoint(
					(float) (doteView.canvasWidth * (mp.x - 0.02)),
					(float) (doteView.canvasHeight * (mp.y)), i);
			i++;
			markers.add(newMp);
		}
		doteView.addMarker(markers);
	}

	public void drawBiggers() {
		biggerPoints = new ArrayList<MapPoint>();
		MapPoint mp1 = new MapPoint(0.5589668f, 0.12810344f);
		MapPoint mp2 = new MapPoint(0.5051194f, 0.54216376f);
		biggerPoints.add(mp1);
		biggerPoints.add(mp2);
		biggers = new ArrayList<MapPoint>();
		for (int i = 0; i < biggerPoints.size(); i++) {
			MapPoint biggerMp = new MapPoint(doteView.canvasWidth
					* biggerPoints.get(i).x, doteView.canvasHeight
					* biggerPoints.get(i).y, i);
			biggers.add(biggerMp);
		}
		doteView.addBigger(biggers);
	}

	private void setCacheBitmap() {
		doteView.setDrawingCacheEnabled(true);
		bmp = doteView.getDrawingCache();
		doteView.setImageBitmap(bmp);
		doteView.setZoomable(true);
	}

	@Override
	public void onClick(View v) {
		mNeedEnterAnimation = true;
		Intent intent = new Intent(mContext, ShowDetailActivity.class);
		switch (v.getId()) {
		case R.id.btn_show_bottom:
			if (mIsBottomShow)
				mSlideDown.start();
			else {
				mSlideView.setVisibility(View.VISIBLE);
				mSlideUp.start();
			}
			mIsBottomShow = !mIsBottomShow;
			break;
		case R.id.menu_elevator:
			intent.putExtra(MENU_TYPE, MENU_EVELATOR);
			startActivity(intent);
			break;
		case R.id.menu_gift:
			intent.putExtra(MENU_TYPE, MENU_GIFT);
			startActivity(intent);
			break;
		case R.id.menu_tea:
			intent.putExtra(MENU_TYPE, MENU_TEA);
			startActivity(intent);
			break;
		case R.id.menu_wc:
			intent.putExtra(MENU_TYPE, MENU_WC);
			startActivity(intent);
			break;
		case R.id.show_path:
//			intent = new Intent(mContext, RecommendPathFragment.class);
//			startActivity(intent);
			mPagerListener.changePage();
			break;
		default:
			break;
		}
		

	}
	
	
	

}

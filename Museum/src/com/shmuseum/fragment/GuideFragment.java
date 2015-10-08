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
		doteView.setMarkerResourceId(R.drawable.marker);
		doteView.setRedMarkerResourceId(R.drawable.marker_selected);
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
		MapPoint mp1 = new MapPoint(0.5144f, 0.302f, 1);
		MapPoint mp2 = new MapPoint(0.50312f, 0.180395f, 5);
		MapPoint mp3 = new MapPoint(0.565316f, 0.180543f, 7);
		MapPoint mp4 = new MapPoint(0.7295491f, 0.26355f, 13);
		MapPoint mp5 = new MapPoint(0.725987f, 0.32099f, 16);
		MapPoint mp6 = new MapPoint(0.725987f, 0.34627f, 17);
		MapPoint mp7 = new MapPoint(0.725987f, 0.36627f, 18);
		MapPoint mp8 = new MapPoint(0.595474f, 0.4313f, 20);
		MapPoint mp9 = new MapPoint(0.195827f, 0.680442f, 28);
		MapPoint mp10 = new MapPoint(0.195827f, 0.703f, 29);
		MapPoint mp11 = new MapPoint(0.209128f, 0.742579f, 30);
		MapPoint mp12 = new MapPoint(0.209128f, 0.780797f, 31);
		MapPoint mp13 = new MapPoint(0.7795705f, 0.65495f, 40);
		MapPoint mp14 = new MapPoint(0.64284f, 0.7582f, 38);
		MapPoint mp15 = new MapPoint(0.6600665f, 0.5989648f, 41);
		MapPoint mp16 = new MapPoint(0.6926772f, 0.5849648f, 44);
		MapPoint mp17 = new MapPoint(0.5272556f, 0.0245f,51);
		MapPoint mp18 = new MapPoint(0.67675f, 0.07418f,52);
		MapPoint mp19 = new MapPoint(0.67675f, 0.118f,53);
		MapPoint mp20 = new MapPoint(0.66789f, 0.15080f,54);
		MapPoint mp21 = new MapPoint(0.539316f, 0.1390543f,59);
		MapPoint mp22 = new MapPoint(0.670385f, 0.530f,61);
		MapPoint mp23 = new MapPoint(0.670385f, 0.496f,62);
		MapPoint mp24 = new MapPoint(0.635385f, 0.550f,63);
		MapPoint mp25 = new MapPoint(0.5106f, 0.4799f,64);
		
		photoPoints.add(mp1);
		photoPoints.add(mp2);
		photoPoints.add(mp3);
		photoPoints.add(mp4);
		photoPoints.add(mp5);
		photoPoints.add(mp6);
		photoPoints.add(mp7);
		photoPoints.add(mp8);
		photoPoints.add(mp9);
		photoPoints.add(mp10);
		photoPoints.add(mp11);
		photoPoints.add(mp12);
		photoPoints.add(mp13);
		photoPoints.add(mp14);
		photoPoints.add(mp15);
		photoPoints.add(mp16);
		photoPoints.add(mp17);
		photoPoints.add(mp18);
		photoPoints.add(mp19);
		photoPoints.add(mp20);
		photoPoints.add(mp21);
		photoPoints.add(mp22);
		photoPoints.add(mp23);
		photoPoints.add(mp24);
		photoPoints.add(mp25);

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
		MapPoint mp1 = new MapPoint(0.5589668f, 0.12310344f);
		MapPoint mp2 = new MapPoint(0.5051194f, 0.53516376f);
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

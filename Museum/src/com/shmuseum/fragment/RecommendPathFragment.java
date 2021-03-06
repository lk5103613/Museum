package com.shmuseum.fragment;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import uk.co.senab.photoview.PhotoViewAttacher;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.reflect.TypeToken;
import com.nineoldandroids.animation.ValueAnimator;
import com.shmuseum.adapter.SlidingPagerAdapter;
import com.shmuseum.customeview.DoteView;
import com.shmuseum.entity.ItemEntity;
import com.shmuseum.entity.MapPoint;
import com.shmuseum.musesum.GoodsDetailActivity;
import com.shmuseum.musesum.R;
import com.shmuseum.network.APIS;
import com.shmuseum.network.GsonUtil;
import com.shmuseum.network.MyNetworkUtil;

public class RecommendPathFragment extends Fragment {
	private DoteView doteView;
	private Handler mHandler;
	private ValueAnimator valueAnimator;
	private ViewPager mSlidingPager;
	private Bitmap bmp;
	private List<View> views;
	private LayoutInflater mInflater;
	private List<MapPoint> markers;
	private List<MapPoint> photoPoints;
	private List<ItemEntity> entities;
	SlidingPagerAdapter mAdapter =  null;
	private Context mContext;
	private boolean mHasDrawed = false;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mContext = getActivity();
		View view = inflater.inflate(R.layout.fragment_recommend_path, container,false);
		initView(view);
		
		return view;
	}

	public void initView(View view){

		mSlidingPager = (ViewPager) view.findViewById(R.id.sliding_view_pager);
		mSlidingPager.setPageMargin(30);
		mSlidingPager.setOffscreenPageLimit(3);

		mSlidingPager
				.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
					@Override
					public void onPageScrolled(int position,
											   float positionOffset, int positionOffsetPixels) {
					}

					@Override
					public void onPageSelected(int position) {
						doteView.setCurrentId(entities.get(position).exhibitId);
					}

					@Override
					public void onPageScrollStateChanged(int state) {

					}
				});

		mHandler = new Handler();
		mInflater = LayoutInflater.from(mContext);
		doteView = (DoteView) view.findViewById(R.id.dote_view);
		doteView.setVisibility(View.INVISIBLE);
		doteView.setMarkerResourceId(R.drawable.marker);
		doteView.setRedMarkerResourceId(R.drawable.marker_selected);
		doteView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
			@Override
			public void onPhotoTap(View view, float x, float y) {
				int i = 0;
				for (MapPoint mp : photoPoints) {
					float minMpX = mp.x - 0.04f;
					float maxMpX = mp.x + 0.1f;
					float minMpY = mp.y - 0.04f;
					float maxMpY = mp.y + 0.1f;
					if ((x >= minMpX && x <= maxMpX)
							&& (y >= minMpY && y <= maxMpY)) {
						Intent intent = new Intent(mContext,
								GoodsDetailActivity.class);
						intent.putExtra(GuideFragment.ICON_INDEX, i + 1);
						startActivity(intent);
						break;
					}
					i++;
				}
			}
		});
//		initDrawAnimation();
	}
	
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if(!mHasDrawed && isVisibleToUser) {
			initDrawAnimation();
			mHasDrawed = true;
		}
	}
	
	@Override
	public void onResume() {
		fetchData(new Response.Listener<JSONArray>() {
			@Override
			public void onResponse(JSONArray response) {
				Type type = new TypeToken<List<ItemEntity>>() {}.getType();
				entities = GsonUtil.gson.fromJson(response.toString(), type);
				initItemView(entities);
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Toast.makeText(mContext, "璇锋鏌ョ綉缁�",
						Toast.LENGTH_LONG).show();
			}
		});
		super.onResume();
	}

	public void drawMarkers() {
		markers = new ArrayList<MapPoint>();
		photoPoints = new ArrayList<MapPoint>();
		
		MapPoint mp1 = new MapPoint(0.5104f, 0.302f, 1);
		MapPoint mp2 = new MapPoint(0.50312f, 0.180395f, 5);
		MapPoint mp3 = new MapPoint(0.565316f, 0.180543f, 7);
		MapPoint mp4 = new MapPoint(0.7795491f, 0.26355f, 13);
		
		MapPoint mp5 = new MapPoint(0.775987f, 0.32099f, 16);
		MapPoint mp6 = new MapPoint(0.775987f, 0.34627f, 17);
		MapPoint mp7 = new MapPoint(0.775987f, 0.36627f, 18);
		MapPoint mp8 = new MapPoint(0.625474f, 0.4313f, 20);
		
		MapPoint mp9 = new MapPoint(0.115827f, 0.680442f, 28);
		MapPoint mp10 = new MapPoint(0.115827f, 0.703f, 29);
		MapPoint mp11 = new MapPoint(0.149128f, 0.742579f, 30);
		MapPoint mp12 = new MapPoint(0.149128f, 0.780797f, 31);
		
		MapPoint mp13 = new MapPoint(0.8295705f, 0.65495f, 40);
		MapPoint mp14 = new MapPoint(0.66284f, 0.7582f, 38);
		
		MapPoint mp15 = new MapPoint(0.7000665f, 0.5989648f, 41);
		MapPoint mp16 = new MapPoint(0.7326772f, 0.5849648f, 44);
		
		MapPoint mp17 = new MapPoint(0.5322556f, 0.0245f,51);
		MapPoint mp18 = new MapPoint(0.72675f, 0.07418f,52);
		MapPoint mp19 = new MapPoint(0.72675f, 0.118f,53);
		MapPoint mp20 = new MapPoint(0.71789f, 0.15080f,54);
		MapPoint mp21 = new MapPoint(0.579316f, 0.1390543f,59);
		
		MapPoint mp22 = new MapPoint(0.710385f, 0.530f,61);
		MapPoint mp23 = new MapPoint(0.710385f, 0.496f,62);
		MapPoint mp24 = new MapPoint(0.675385f, 0.550f,63);
		
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

	private void fetchData(Response.Listener<JSONArray> listener,
			Response.ErrorListener errorListener) {
		JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,
				APIS.FIND_ALL, null, listener, errorListener);
		MyNetworkUtil.getInstance(mContext).addToRequstQueue(
				request);
	}

	private void initItemView(List<ItemEntity> entities) {
		views = new ArrayList<View>();
		for (ItemEntity entity : entities) {
			View view = mInflater.inflate(R.layout.sliding_item, null);
			ImageView img = (ImageView) view.findViewById(R.id.item_img);
			TextView txtName = (TextView) view.findViewById(R.id.item_name);
			TextView txtFavorite = (TextView) view.findViewById(R.id.item_fav);
			ImageView btnLeft = (ImageView) view.findViewById(R.id.btn_left);
			ImageView btnRight = (ImageView) view.findViewById(R.id.btn_right);
			btnLeft.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if (mSlidingPager.getCurrentItem()-1 >=0) {
						mSlidingPager.setCurrentItem(mSlidingPager.getCurrentItem()-1);
					}
					
				}
			});
			
			btnRight.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if (mSlidingPager.getCurrentItem()+1 < mSlidingPager.getChildCount()) {
						mSlidingPager.setCurrentItem(mSlidingPager.getCurrentItem()+1);
					}
					
				}
			});
			
			txtName.setText(entity.name);
			txtFavorite.setText("已有" + entity.zan_cnt + "人点赞");
			String iconUrl = APIS.BASE_URL + entity.icon;
			MyNetworkUtil
					.getInstance(mContext)
					.getImageLoader()
					.get(iconUrl,ImageLoader.getImageListener(img, R.color.white,
									R.color.white));
			views.add(view);
		}
		if(mAdapter == null) {
			mAdapter = new SlidingPagerAdapter(mContext, views);
			mSlidingPager.setAdapter(mAdapter);
			doteView.setCurrentId(entities.get(0).exhibitId);
		}
		else {
			mAdapter.setViews(views);
			mAdapter.notifyDataSetChanged();
		}
	}

	private void setCacheBitmap() {
		doteView.setDrawingCacheEnabled(true);
		bmp = doteView.getDrawingCache();
		doteView.setImageBitmap(bmp);
		doteView.setZoomable(true);
	}

	private void initDrawAnimation() {
		valueAnimator = ValueAnimator.ofInt(0, 27);
		valueAnimator.setTarget(doteView);
		valueAnimator.setInterpolator(new LinearInterpolator());
		valueAnimator.setDuration(8000);
		valueAnimator
				.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
					int prevIndex = -1;

					@Override
					public void onAnimationUpdate(ValueAnimator animation) {
						int value = (Integer) animation.getAnimatedValue();
						if (prevIndex != value) {
							drawPath(value);
							prevIndex = value;
						}
					}
				});

		doteView.postDelayed(new Runnable() {

			@Override
			public void run() {
				showBitmap();
				mHandler.postDelayed(new Runnable() {
					@Override
					public void run() {
						doteView.setScale(DoteView.DEFAULT_SCALE, 0, 0, false);
						doteView.setVisibility(View.VISIBLE);
						valueAnimator.start();
						drawMarkers();
					}
				}, 0);
				LayoutParams params = (LayoutParams) doteView
						.getLayoutParams();
				params.width = LayoutParams.MATCH_PARENT;
			}
		}, 0);
	}

	private void showBitmap() {
		doteView.setDrawingCacheEnabled(true);
		Bitmap temp = doteView.getDrawingCache();
		bmp = temp.copy(temp.getConfig(), true);
		doteView.setImageBitmap(bmp);
		doteView.setZoomable(true);
		doteView.setDrawingCacheEnabled(false);
	}

	private void drawPath(int index) {

		switch (index) {
		case 0:
			doteView.addLine(doteView.imgWidth * 0.239f, 0, true);
			break;
		case 1:
			doteView.addLine(0, -doteView.imgHeight * 0.1f, true);
			break;
		case 2:
			doteView.addLine(doteView.imgWidth * 0.135f,
					-doteView.imgHeight * 0.067f, false);
			break;
		case 3:
			doteView.addLine(0, doteView.imgHeight * 0.08f, true);
			break;
		case 4:
			doteView.addLine(-doteView.imgWidth * 0.04f, 0, false);
			break;
		case 5:
			doteView.addLine(0, doteView.imgHeight * 0.0647f, false);
			break;
		case 6:
			doteView.addLine(doteView.imgWidth * 0.34f, 0, true);
			break;
		case 7:
			doteView.addLine(doteView.imgWidth * 0.035f,
					doteView.imgHeight * 0.013f, false);
			doteView.addLine(0, doteView.imgHeight * 0.014f, false);
			break;
		case 8:
			doteView.addLine(-doteView.imgWidth * 0.06f, 0, false);
			break;
		case 9:
			doteView.addLine(0, doteView.imgHeight * 0.11f, true);
			break;
		case 10:
			doteView.addLine(doteView.imgWidth * 0.036f, 0, false);
			break;
		case 11:
			doteView.addLine(0, doteView.imgHeight * 0.015f, false);
			break;
		case 12:
			/*doteView.addCircle(160, 100, doteView.imgWidth * 0.04f,
					doteView.imgHeight * 0.016f);*/
			
			doteView.addLine(-doteView.imgWidth * 0.036f, doteView.imgHeight * 0.016f, false);
			
			break;
		case 13:
			doteView.addLine(-doteView.imgWidth * 0.24f, 0, true);
			break;
		case 14:
			doteView.addLine(0, -doteView.imgHeight * 0.01f, false);
			break;
		case 15:
			doteView.addLine(-doteView.imgWidth * 0.12f, 0, false);
			break;
		case 16:
			doteView.addLine(0, doteView.imgHeight * 0.255f, true);
			break;
		case 17:
			doteView.addLine(doteView.imgWidth * 0.45f, 0, true);
			break;
		case 18:
			doteView.addLine(-doteView.imgWidth * 0.08f,
					doteView.imgHeight * 0.043f, false);
			break;
		case 19:
			doteView.addLine(-doteView.imgWidth * 0.04f,
					-doteView.imgHeight * 0.01f, false);
			break;
		case 20:
			doteView.addLine(-doteView.imgWidth * 0.4f,
					doteView.imgHeight * 0.166f, true);
			break;
		case 21:
			doteView.addLine(0, -doteView.imgHeight * 0.155f, true);
			break;
		case 22:
			doteView.addLine(-doteView.imgWidth * 0.0308f, 0, false);
			break;
		case 23:
			doteView.addLine(0, -doteView.imgHeight * 0.15f, true);
			break;
		case 24:
			doteView.addLine(0, -doteView.imgHeight * 0.18f, true);
			break;
		case 25:
			doteView.addLine(doteView.imgWidth * 0.06f, 0, false);
			break;
		case 26:
			doteView.addLine(0, -doteView.imgHeight * 0.035f, false);
			break;
		case 27:
			doteView.addLine(-doteView.imgWidth * 0.239f, 0, true);
			break;
		default:
			break;
		}
	}

	public void back(View v) {
	}
}

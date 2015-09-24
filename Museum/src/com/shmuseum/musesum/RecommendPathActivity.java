package com.shmuseum.musesum;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import uk.co.senab.photoview.PhotoViewAttacher;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import com.shmuseum.network.APIS;
import com.shmuseum.network.GsonUtil;
import com.shmuseum.network.MyNetworkUtil;

public class RecommendPathActivity extends Activity {
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recommend_path);

		mSlidingPager = (ViewPager) findViewById(R.id.sliding_view_pager);
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
		mInflater = LayoutInflater.from(this);
		doteView = (DoteView) findViewById(R.id.dote_view);
		doteView.setVisibility(View.INVISIBLE);
		doteView.setMarkerResourceId(R.drawable.marker2);
		doteView.setRedMarkerResourceId(R.drawable.marker2_selected);
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
						Intent intent = new Intent(RecommendPathActivity.this,
								GoodsDetailActivity.class);
						intent.putExtra(GuideActivity.ICON_INDEX, i + 1);
						startActivity(intent);
						break;
					}
					i++;
				}
			}
		});

		initDrawAnimation();

	}

	@Override
	protected void onResume() {
		super.onResume();
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
				Toast.makeText(RecommendPathActivity.this, "请检查网络",
						Toast.LENGTH_LONG).show();
			}
		});
	}

	public void drawMarkers() {
		markers = new ArrayList<>();
		photoPoints = new ArrayList<>();
		MapPoint mp1 = new MapPoint(0.5624f, 0.1966f);
        MapPoint mp2 = new MapPoint(0.52297f, 0.3118f);
        MapPoint mp3 = new MapPoint(0.758f, 0.3813961f);
        MapPoint mp4 = new MapPoint(0.699468f, 0.60937f);
        MapPoint mp5 = new MapPoint(0.6658f, 0.7695f);
        MapPoint mp6 = new MapPoint(0.1239f, 0.798f);
		photoPoints.add(mp1);
		photoPoints.add(mp2);
		photoPoints.add(mp3);
		photoPoints.add(mp4);
		photoPoints.add(mp5);
		photoPoints.add(mp6);
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
		MyNetworkUtil.getInstance(getApplicationContext()).addToRequstQueue(
				request);
	}

	private void initItemView(List<ItemEntity> entities) {
		views = new ArrayList<>();
		for (ItemEntity entity : entities) {
			View view = mInflater.inflate(R.layout.sliding_item, null);
			ImageView img = (ImageView) view.findViewById(R.id.item_img);
			TextView txtName = (TextView) view.findViewById(R.id.item_name);
			TextView txtFavorite = (TextView) view.findViewById(R.id.item_fav);
			txtName.setText(entity.name);
			txtFavorite.setText("已有" + entity.zan_cnt + "人点赞");
			String iconUrl = APIS.BASE_URL + entity.icon;
			MyNetworkUtil
					.getInstance(getApplicationContext())
					.getImageLoader()
					.get(iconUrl,
							ImageLoader.getImageListener(img, R.color.white,
									R.color.white));
			views.add(view);
		}
		if(mAdapter == null) {
			mAdapter = new SlidingPagerAdapter(RecommendPathActivity.this, views);
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
			doteView.addCircle(175, 100, doteView.imgWidth * 0.04f,
					doteView.imgHeight * 0.016f);
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
		this.finish();
	}
}

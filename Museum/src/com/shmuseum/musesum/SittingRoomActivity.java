package com.shmuseum.musesum;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.gson.reflect.TypeToken;
import com.shmuseum.adapter.SlidingPagerAdapter;
import com.shmuseum.customeview.DoteView;
import com.shmuseum.entity.ItemEntity;
import com.shmuseum.entity.MapPoint;
import com.shmuseum.network.APIS;
import com.shmuseum.network.GsonUtil;
import com.shmuseum.network.MyNetworkUtil;

public class SittingRoomActivity extends Activity {

    private Context mContext;
    private List<ItemEntity> entities;
    private LayoutInflater mInflater;
    private ViewPager mSlidingPager;
    private List<View> views;
    private DoteView mDoteView;
    private List<MapPoint> mIconPercents;
    private List<MapPoint> mIcons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sitting_room);

        mContext = this;
        mInflater = LayoutInflater.from(mContext);

        mSlidingPager = (ViewPager) findViewById(R.id.sliding_view_pager);
        mSlidingPager.setPageMargin(30);
        mSlidingPager.setOffscreenPageLimit(3);
        mDoteView = (DoteView) findViewById(R.id.study_room_map);
        mDoteView.setMarkerResourceId(R.drawable.marker2);
        mDoteView.setRedMarkerResourceId(R.drawable.marker2_selected);

        mSlidingPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mDoteView.setCurrentId(entities.get(position).exhibitId);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mDoteView.postDelayed(new Runnable() {
            @Override
            public void run() {
                showBitmap();
            }
        }, 500);
        mDoteView.postDelayed(new Runnable() {
            @Override
            public void run() {
                initIcon();
            }
        }, 1000);
        fetchData(new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Type type = new TypeToken<List<ItemEntity>>() {
                }.getType();
                entities = GsonUtil.gson.fromJson(response.toString(), type);
                initItemView(entities);
                mDoteView.setCurrentId(entities.get(0).exhibitId);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext, "请检查网络", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showBitmap() {
        mDoteView.setDrawingCacheEnabled(false);
        mDoteView.setDrawingCacheEnabled(true);
        Bitmap temp = mDoteView.getDrawingCache();
        Bitmap bmp =temp.copy(temp.getConfig(), true);
        mDoteView.setImageBitmap(bmp);
        mDoteView.setZoomable(true);
    }

    public void back(View view) {
        this.finish();
    }

    private void initIcon() {
        mIconPercents = new ArrayList<>();
        MapPoint mp1 = new MapPoint(0.250871f, 0.58011604f);
        MapPoint mp2 = new MapPoint(0.2658025f, 0.85101276f);
        MapPoint mp3 = new MapPoint(0.52831808f, 0.48896546f);
        MapPoint mp4 = new MapPoint(0.76836637f, 0.5780959f);
        MapPoint mp5 = new MapPoint(0.7702499f, 0.8446382f);
        MapPoint mp6 = new MapPoint(0.52831808f, 0.25062393f);
        mIconPercents.add(mp1);
        mIconPercents.add(mp2);
        mIconPercents.add(mp3);
        mIconPercents.add(mp4);
        mIconPercents.add(mp5);
        mIconPercents.add(mp6);
        mIcons = new ArrayList<>();
        for(int i=0; i<mIconPercents.size(); i++) {
            MapPoint mp = new MapPoint(mDoteView.canvasWidth * mIconPercents.get(i).x,
                    mDoteView.canvasHeight * mIconPercents.get(i).y, 1 + i);
            mIcons.add(mp);
            mDoteView.addMarker(mIcons);
        }
    }

    private void fetchData(Response.Listener<JSONArray> listener, Response.ErrorListener errorListener) {
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, APIS.FIND_ALL, null,
                listener, errorListener);
        MyNetworkUtil.getInstance(getApplicationContext()).addToRequstQueue(request);
    }

    private void initItemView(List<ItemEntity> entities) {
        views = new ArrayList<>();
        for(ItemEntity entity : entities) {
            View view = mInflater.inflate(R.layout.sliding_item, null);
            ImageView img = (ImageView) view.findViewById(R.id.item_img);
            TextView txtName = (TextView) view.findViewById(R.id.item_name);
            TextView txtFavorite = (TextView) view.findViewById(R.id.item_fav);
            txtName.setText(entity.name);
            txtFavorite.setText("已有" + entity.zan_cnt + "人点赞");
            String iconUrl = APIS.BASE_URL + entity.icon;
            MyNetworkUtil.getInstance(getApplicationContext()).getImageLoader().get(iconUrl,
                    ImageLoader.getImageListener(img, R.color.white, R.color.white));
            views.add(view);
        }
        mSlidingPager.setAdapter(new SlidingPagerAdapter(mContext, views));
    }

}

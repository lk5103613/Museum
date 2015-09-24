package com.shmuseum.musesum;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoViewAttacher;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.nineoldandroids.animation.ObjectAnimator;
import com.shmuseum.customeview.DoteView;
import com.shmuseum.entity.MapPoint;
import com.shmuseum.utils.DensityUtil;

public class GuideActivity extends Activity {

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


    private DoteView doteView;
    private Bitmap bmp;
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        mContext = this;

        mHandler = new Handler();

        mSlideView = (LinearLayout) findViewById(R.id.slide_up_menu);
        doteView = (DoteView) findViewById(R.id.dote_view);
        doteView.setMarkerResourceId(R.drawable.marker2);
        doteView.setRedMarkerResourceId(R.drawable.marker2_selected);
        doteView.setVisibility(View.INVISIBLE);

        float px = DensityUtil.dip2px(mContext, 60f);
        mSlideDown = ObjectAnimator.ofFloat(mSlideView, "translationY", -px, px+10);
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
                        doteView.setScale(DoteView.DEFAULT_SCALE, false);
                        doteView.setVisibility(View.VISIBLE);
                    }
                }, 0);
            }
        }, 0);

        doteView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
            	
            	System.out.println("x " + x +"  " + y);
                int i = 0;
                for(MapPoint mp : biggerPoints) {
                    float minMpX = mp.x - 0.04f;
                    float maxMpX = mp.x + 0.04f;
                    float minMpY = mp.y - 0.04f;
                    float maxMpY = mp.y + 0.04f;
                    if ((x >= minMpX && x <= maxMpX) && (y >= minMpY && y <= maxMpY)) {
                        Intent intent = null;
                        if(biggers.get(i).id == 1)
                            intent = new Intent(mContext, StudyRoomActivity.class);
                        else
                            intent = new Intent(mContext, SittingRoomActivity.class);
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
                  if ((x >= minMpX && x <= maxMpX) && (y >= minMpY && y <= maxMpY)) {
                      Intent intent = new Intent(mContext, GoodsDetailActivity.class);
                      intent.putExtra(ICON_INDEX, markers.get(i).id);
                      startActivity(intent);
                      return;
                  }
                  i++;
              }
            }
        });
    }

    public void drawMarkers() {
        markers = new ArrayList<>();
        photoPoints = new ArrayList<>();
        
        //裁剪之后
        MapPoint mp1 = new MapPoint(0.5624f, 0.1966f);
        MapPoint mp2 = new MapPoint(0.52297f, 0.3118f);
        MapPoint mp3 = new MapPoint(0.72f, 0.3803961f);
        MapPoint mp5 = new MapPoint(0.694468f, 0.60937f);
        MapPoint mp6 = new MapPoint(0.6448f, 0.7615f);
        MapPoint mp7 = new MapPoint(0.2009f, 0.798f);

        photoPoints.add(mp1);
        photoPoints.add(mp2);
        photoPoints.add(mp3);
        photoPoints.add(mp5);
        photoPoints.add(mp6);
        photoPoints.add(mp7);
        
        int i=1;
        for(MapPoint mp : photoPoints) {
            MapPoint newMp = new MapPoint((float)(doteView.canvasWidth * (mp.x - 0.02)), (float)(doteView.canvasHeight * (mp.y)), i);
            i++;
            markers.add(newMp);
        }
        doteView.addMarker(markers);
    }

    public void drawBiggers() {
        biggerPoints = new ArrayList<>();
        MapPoint mp1 = new MapPoint(0.5589668f, 0.12810344f);
        MapPoint mp2 = new MapPoint(0.5051194f, 0.54216376f);
        biggerPoints.add(mp1);
        biggerPoints.add(mp2);
        biggers = new ArrayList<>();
        for(int i=0; i<biggerPoints.size(); i++) {
            MapPoint biggerMp = new MapPoint(doteView.canvasWidth * biggerPoints.get(i).x,
                    doteView.canvasHeight * biggerPoints.get(i).y, i);
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


    public void showBottomMenu(View v) {
        if(mIsBottomShow)
            mSlideDown.start();
        else {
            mSlideView.setVisibility(View.VISIBLE);
            mSlideUp.start();
        }
        mIsBottomShow = !mIsBottomShow;
    }

    public void showPath(View v) {
        Intent intent = new Intent(mContext, RecommendPathActivity.class);
        startActivity(intent);
    }

    public void showDetail(View v) {
        mNeedEnterAnimation = true;
        Intent intent = new Intent(mContext, ShowDetailActivity.class);
        switch (v.getId()) {
            case R.id.menu_elevator:
                intent.putExtra(MENU_TYPE, MENU_EVELATOR);
                break;
            case R.id.menu_gift:
                intent.putExtra(MENU_TYPE, MENU_GIFT);
                break;
            case R.id.menu_tea:
                intent.putExtra(MENU_TYPE, MENU_TEA);
                break;
            case R.id.menu_wc:
                intent.putExtra(MENU_TYPE, MENU_WC);
                break;
        }
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mNeedEnterAnimation)
            overridePendingTransition(R.anim.back_from_right, R.anim.remove_to_right);
    }
}

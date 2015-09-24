package com.shmuseum.musesum;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.shmuseum.customeview.FullScreenDialog;
import com.shmuseum.customeview.MyViewPager;
import com.shmuseum.customeview.ShareView;
import com.shmuseum.entity.Goods;
import com.shmuseum.fragment.GuideFragment;
import com.shmuseum.network.APIS;
import com.shmuseum.network.GsonUtil;
import com.shmuseum.network.MyNetworkUtil;
import com.shmuseum.utils.DensityUtil;
import com.shmuseum.utils.GaussianBlur;

public class GoodsDetailActivity extends Activity {

    public static final String JIE_SHUO_CONTENT = "jie_shuo_content";

    private int mIconIndex = -1;
    private Context mContext;
    private Goods mGoods;
    private ImageLoader mImageLoader;

    private TextView mTxtTitle;
    private TextView mTxtLike;

    private FullScreenDialog mDialog;
    private MyViewPager mViewPager;
    private MyViewPager mDialogPageView;
    private LinearLayout mSmImgContainer;
    private ShareView mShareView;
    private LinearLayout container;

    private String[] mBgPicPaths;
    private ImageView[] mImgs;
    private TextView[] mSmImgs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);

        mContext = getApplicationContext();
        mImageLoader = MyNetworkUtil.getInstance(mContext).getImageLoader();

        mTxtTitle = (TextView) findViewById(R.id.title);
        mTxtLike = (TextView) findViewById(R.id.like_count);
        mSmImgContainer = (LinearLayout) findViewById(R.id.sm_img_container);
        mViewPager = (MyViewPager) findViewById(R.id.dialog_view_pager);
        mShareView = (ShareView)findViewById(R.id.share_view);
        container = (LinearLayout) findViewById(R.id.container);

        mIconIndex = getIntent().getIntExtra(GuideFragment.ICON_INDEX, -1);
        if(mIconIndex != -1) {
            fetchData(new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    mGoods = GsonUtil.gson.fromJson(response.toString(), Goods.class);
                    initUI(mGoods);
                    initBgImgs(mGoods);
                    mViewPager.init(mContext, mBgPicPaths, mImgs, mDialog, mImageLoader);
                    mViewPager.setCurrentPosition(0);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(mContext, "请检查网络", Toast.LENGTH_LONG).show();
                }
            });
        }

    }

    private void initBgImgs(Goods goods) {
        List<String> pics = new ArrayList<>();
        if(!goods.pic0.equals(""))
            pics.add(APIS.BASE_URL + goods.pic0);
        if(!goods.pic1.equals(""))
            pics.add(APIS.BASE_URL + goods.pic1);
        if(!goods.pic2.equals(""))
            pics.add(APIS.BASE_URL + goods.pic2);
        if(!goods.pic3.equals(""))
            pics.add(APIS.BASE_URL + goods.pic3);
        if(!goods.pic4.equals(""))
            pics.add(APIS.BASE_URL + goods.pic4);
        if(!goods.pic5.equals(""))
            pics.add(APIS.BASE_URL + goods.pic5);
        mImgs = new ImageView[pics.size()];
        int i=0;
        mBgPicPaths = new String[pics.size()];
        for(String pic : pics) {
            mBgPicPaths[i] = pic;
            ImageView img = new ImageView(mContext);
            img.setLayoutParams(new ViewGroup.LayoutParams(DensityUtil.dip2px(mContext, 240f), ViewGroup.LayoutParams.WRAP_CONTENT));
            img.setClickable(true);
            mImageLoader.get(pic, ImageLoader.getImageListener(img, R.color.white, R.color.white));
            final int finalI = i;
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showFullScreenDialog(finalI);
                }
            });
            mImgs[i] = img;
            i++;
        }
        initSmImgs(new int[]{R.drawable.detail_bottom_f, R.drawable.detail_bottom_s,
                R.drawable.detail_bottom_t}, new String[]{"细节", "榫卯", "知识"});
    }

    private void initSmImgs(int[] pics, String[] texts) {
        mSmImgs = new TextView[pics.length];
        for(int i=0; i<pics.length; i++) {
            TextView img = new TextView(mContext);
            img.setTextColor(getResources().getColor(R.color.goods_detail_sm_icon_text_color));
            setDrawableTop(pics[i], img);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(150,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            if(i != 0)
                lp.setMargins(30, 0, 0, 0);
            img.setLayoutParams(lp);
            img.setGravity(Gravity.CENTER);
            img.setCompoundDrawablePadding(20);
            img.setTag(i);
            img.setText(texts[i]);

            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int index = (int) v.getTag();
                    if (index == 1) {
                        Intent intent = new Intent(mContext, GoodsDescriptionActivity.class);
                        intent.putExtra("bitmap", getBlur(getContainerImage()));
                        startActivity(intent);
                    }
                }
            });
            mSmImgs[i] = img;
            mSmImgContainer.addView(img);
        }
    }

    public void back(View v) {
        this.finish();
    }

    private void setDrawableTop(int drawableId, TextView v) {
        Drawable drawable= getResources().getDrawable(drawableId);
        drawable.setBounds(0, 0, 150, 150);
        v.setCompoundDrawables(null, drawable, null, null);
    }

    private void initUI(Goods goods) {
        mTxtTitle.setText(goods.name);
        mTxtLike.setText(goods.zan_cnt + "次");
    }

    private void fetchData(Response.Listener<JSONObject> listener,
                           Response.ErrorListener errorListener) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                APIS.GET_EXHIBITION_DETAIL + mIconIndex, null, listener, errorListener);
        MyNetworkUtil.getInstance(mContext).addToRequstQueue(request);
    }

    public void toJieShuo(View v) {
        if(mGoods == null || mGoods.jieshuo_content == null || mGoods.jieshuo_content.trim().equals("")) {
            Toast.makeText(mContext, "没有解说", Toast.LENGTH_LONG).show();
            return;
        }
        Intent intent = new Intent(mContext, JieShuoActivity.class);
        intent.putExtra("bitmap", getBlur(getContainerImage()));
        intent.putExtra(JIE_SHUO_CONTENT, GsonUtil.gson.toJson(mGoods));
        startActivity(intent);
    }

    public void toVideo(View v) {
//        if(mGoods.video_url == null || mGoods.video_url.trim().equals("")) {
//            Toast.makeText(mContext, "没有视屏", Toast.LENGTH_LONG).show();
//            return;
//        }
        Intent intent = new Intent(mContext, VideoActivity.class);
        intent.putExtra("bitmap", getBlur(getContainerImage()));
        intent.putExtra(JIE_SHUO_CONTENT, GsonUtil.gson.toJson(mGoods));
        startActivity(intent);
    }

    /**
     *  跳转到 纪念品 页面 IntroActivity
     * @param v
     */
    public void toJiNianPin(View v) {
        Intent intent = new Intent(this, JiNianPinActivity.class);
        intent.putExtra("bitmap", getBlur(getContainerImage()));
        startActivity(intent);
    }

    public void toShare(View v) {
        mShareView.setVisibility(getBlur(getContainerImage()), View.VISIBLE);
    }

    private Bitmap getBlur(Bitmap bkg) {
        float scaleFactor = 8;//图片质量的压缩率,越高质量越低,但是处理速度最好,这个值就可以
        float radius = 2;//像素模糊的级别,越大越模糊,这个值应该正好

        Bitmap bitmap = Bitmap.createBitmap(
                (int) (bkg.getWidth() / scaleFactor),
                (int) (bkg.getHeight() / scaleFactor),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.scale(1 / scaleFactor, 1 / scaleFactor);
        Paint paint = new Paint();
        paint.setFlags(Paint.FILTER_BITMAP_FLAG);
        canvas.drawBitmap(bkg, 0, 0, paint);

        bitmap = GaussianBlur.doBlur(bitmap, (int) radius, true);
        return bitmap;

    }

    private Bitmap getContainerImage() {
        container.setDrawingCacheEnabled(false);
        container.setDrawingCacheEnabled(true);
        Bitmap temp = container.getDrawingCache();
        Bitmap bmp =temp.copy(temp.getConfig(), true);
        return bmp;
    }

    public void share(View v) {

    }

    private void showFullScreenDialog(int position) {
        if(mDialog == null) {
            mDialog = new FullScreenDialog(GoodsDetailActivity.this);
            mDialogPageView = new MyViewPager(GoodsDetailActivity.this);
        }
        mDialogPageView.init(mContext, mBgPicPaths, null, mDialog, mImageLoader);
        mDialogPageView.setCurrentPosition(position);
        mDialog.show();
        mDialog.setContentView(mDialogPageView, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

}

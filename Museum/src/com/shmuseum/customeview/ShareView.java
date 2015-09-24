package com.shmuseum.customeview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;
import com.shmuseum.musesum.R;
import com.shmuseum.utils.DensityUtil;

import java.util.zip.Inflater;

/**
 * Created by dell on 2015/8/14.
 */
public class ShareView extends RelativeLayout {

    private RelativeLayout container;
    private ImageView shareImg;
    private ObjectAnimator inAnim;
    private ObjectAnimator outAnim;
    private Context context;

    public ShareView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initView(context);
    }
    public ShareView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }
    private void initView(Context context){
        this.context = context;
        View.inflate(context, R.layout.share_view_layout, this);
        container = (RelativeLayout) findViewById(R.id.container);
        shareImg = (ImageView) findViewById(R.id.share_img);
        setBackgroundColor(getResources().getColor(R.color.white));
        initAnim();
    }

    public void setBackground(Bitmap bitmap){
        container.setBackgroundDrawable(new BitmapDrawable(getResources(), bitmap));
    }

    public void setVisibility(Bitmap bitmap,int visibility) {

        Log.i("test", "visi" + visibility);

        switch (visibility) {

            case View.VISIBLE:
                setVisibility(visibility);
                if (bitmap!= null) {
                    setBackground(bitmap);
                 }
                inAnim.start();
                break;
            case View.INVISIBLE:
                outAnim.start();
                break;
        }
    }

    /**
     * 获取view高度 单位px
     * @return
     */
    public int getHeight(View view) {
        try{
            LayoutParams lp = (LayoutParams) view.getLayoutParams();
            return lp.height;
        } catch (Exception e) {
            return -1;
        }
    }

    private void initAnim() {
        inAnim = ObjectAnimator.ofFloat(shareImg, "translationY", DensityUtil.dip2px(context,100),0);
        inAnim.setDuration(300);


        outAnim = ObjectAnimator.ofFloat(shareImg,"translationY",0,DensityUtil.dip2px(context,100));
        outAnim.setDuration(500);
        outAnim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        setVisibility(null,INVISIBLE);
        return true;
    }
}

package com.shmuseum.network;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class MyNetworkUtil {

    private RequestQueue mQueue;
    private ImageLoader mLoader;
    private static Context mContext;
    private static MyNetworkUtil mInstance;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    private MyNetworkUtil(Context context) {
        mContext = context.getApplicationContext();
        mQueue = getRequestQueue();

        mLoader = new ImageLoader(mQueue,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap> cache = new LruCache<>(20*1024*1024);
                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }
                });
    }

    public RequestQueue getRequestQueue() {
        if(mQueue == null) {
            mQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return mQueue;
    }

    public <T> void addToRequstQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public ImageLoader getImageLoader() {
        return mLoader;
    }

    public static synchronized MyNetworkUtil getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new MyNetworkUtil(context);
        }
        return mInstance;
    }

}

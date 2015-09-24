package com.shmuseum.musesum;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.shmuseum.entity.Goods;
import com.shmuseum.network.APIS;
import com.shmuseum.network.GsonUtil;
import com.shmuseum.utils.DateUtil;

public class JieShuoActivity extends Activity {
    private SeekBar seekBar;
    private TextView time1,time2;

    private ImageView play;

    private MediaPlayer mediaPlayer;
    private SurfaceHolder surfaceHolder;
    private ViewGroup mMainContainer;

    private TextView mTxtTitle;
    private TextView mTxtJieShouContent;

    private ScrollView scrollView;

    private boolean scrollStatus = true;	//是否滚动
    private Goods mGoods;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jie_shuo);

        mMainContainer = (ViewGroup) findViewById(R.id.main_container);
        Intent intent = getIntent();
        if(intent != null) {
            String goodsStr = intent.getStringExtra(GoodsDetailActivity.JIE_SHUO_CONTENT);
            mGoods = GsonUtil.gson.fromJson(goodsStr, Goods.class);
            Bitmap bitmap=intent.getParcelableExtra("bitmap");
            mMainContainer.setBackgroundDrawable(new BitmapDrawable(getResources(), bitmap));
        }

        mTxtTitle = (TextView) findViewById(R.id.txtTitle);
        mTxtTitle.setText(mGoods.name);
        mTxtJieShouContent = (TextView) findViewById(R.id.jieshou_content);
        mTxtJieShouContent.setText(mGoods.jieshuo_content);
        play = (ImageView) findViewById(R.id.play);
        time1 = (TextView) findViewById(R.id.time1);
        time2 = (TextView) findViewById(R.id.time2);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        scrollStatus = false;
                        break;
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:
                        scrollStatus = true;

                        int sheight = scrollView.getChildAt(0).getHeight()-scrollView.getHeight();
                        int h = scrollView.getScrollY()*mediaPlayer.getDuration()/sheight;

                        mediaPlayer.seekTo(h);
                        break;
                }
                return false;
            }
        });

        sfCreated();
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress = seekBar.getProgress();
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    mediaPlayer.seekTo(progress);
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                if(mGoods.jieshuo_url.trim().equals("")) {
                    Toast.makeText(JieShuoActivity.this, "没有解说", Toast.LENGTH_LONG).show();
                    return;
                }
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    play.setImageResource(R.drawable.icon_04);
                }else{
                    mediaPlayer.start();
                    play.setImageResource(R.drawable.zt);
                }
            }
        });
    }

    private void sfCreated() {
        // 必须在surface创建后才能初始化MediaPlayer,否则不会显示图像
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setDisplay(surfaceHolder);
        if(mGoods.jieshuo_url.trim().equals("")){
            return;
        }
        // 设置显示视频显示在SurfaceView上
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String path = APIS.BASE_URL + mGoods.jieshuo_url;
                    mediaPlayer.setDataSource(path);
                    mediaPlayer.prepare();
                } catch (Exception e) {
                    e.printStackTrace();
                    play.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(JieShuoActivity.this, "音频正在加载中", Toast.LENGTH_LONG).show();
                            play.setImageResource(R.drawable.icon_04);
                        }
                    });
                }
            }
        }).start();
//        try {
//            AssetFileDescriptor fileDescriptor = getAssets().openFd("2.mp3");
//            mediaPlayer
//                    .setDataSource(fileDescriptor.getFileDescriptor(),
//                            fileDescriptor.getStartOffset(),
//                            fileDescriptor.getLength());
//            mediaPlayer.prepare();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                seekBar.setMax(mediaPlayer.getDuration());
                setTimeCount(mediaPlayer.getDuration());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (mediaPlayer != null) {
                            int current = mediaPlayer.getCurrentPosition();
                            seekBar.setProgress(current);
                            setTimeCurr(current);

                            if (scrollStatus)
                                jumpScrollView(mediaPlayer.getDuration(), current);

                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
            }
        });
    }

    private void jumpScrollView(int countHeight,int currHeight){
        int sheight = scrollView.getChildAt(0).getHeight()-scrollView.getHeight();
        int scheight = currHeight*sheight/countHeight;

        scrollView.scrollTo(0, scheight);
    }

    private void setTimeCurr(final int current){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                long timer = DateUtil.conversionDate("2000-10-10 00:00:00", "yyyy-MM-dd HH:mm:ss").getTime();

                SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
                time1.setText(formatter.format(new Date(timer + current)));
            }
        });
    }

    private void setTimeCount(final int count){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                long timer = DateUtil.conversionDate("2000-10-10 00:00:00", "yyyy-MM-dd HH:mm:ss").getTime();
                SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
                time2.setText("/"+formatter.format(new Date(timer+count)));
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        mediaPlayer.release();
        mediaPlayer = null;
        // Activity销毁时停止播放，释放资源。不做这个操作，即使退出还是能听到视频播放的声音
    }

    public void back(View v) {
        this.finish();
    }

}

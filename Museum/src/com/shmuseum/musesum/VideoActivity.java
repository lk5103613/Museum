package com.shmuseum.musesum;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.shmuseum.entity.Goods;
import com.shmuseum.network.GsonUtil;
import com.shmuseum.utils.DateUtil;

public class VideoActivity extends Activity {

    private SurfaceView surfaceView;
    private SeekBar seekBar;
    private TextView time1,time2;
    private ViewGroup mMainContainer;

    private TextView mTxtTitle;

    private ImageView play;

    private MediaPlayer mediaPlayer;
    private SurfaceHolder surfaceHolder;

    private Goods mGoods;
    private Thread mediaThread;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        Intent intent = getIntent();

        mMainContainer = (ViewGroup) findViewById(R.id.main_container);

        if(intent != null) {
            String goodsStr = intent.getStringExtra(GoodsDetailActivity.JIE_SHUO_CONTENT);
            mGoods = GsonUtil.gson.fromJson(goodsStr, Goods.class);
            Bitmap bitmap=intent.getParcelableExtra("bitmap");
            mMainContainer.setBackgroundDrawable(new BitmapDrawable(getResources(), bitmap));
        }

        mTxtTitle = (TextView) findViewById(R.id.txtTitle);
        mTxtTitle.setText(mGoods.name);
        surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        play = (ImageView) findViewById(R.id.play);
        time1 = (TextView) findViewById(R.id.time1);
        time2 = (TextView) findViewById(R.id.time2);
        seekBar = (SeekBar) findViewById(R.id.seekBar);

        surfaceHolder = surfaceView.getHolder();// SurfaceHolder是SurfaceView的控制接口
        surfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                sfCreated(holder);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width,
                                       int height) {

            }
        }); // 因为这个类实现了SurfaceHolder.Callback接口，所以回调参数直接this
        surfaceHolder.setFixedSize(320, 220);// 显示的分辨率,不设置为视频默认
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);// Surface类型

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
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    play.setImageResource(R.drawable.icon_04);
                } else {
                    mediaPlayer.start();
                    play.setImageResource(R.drawable.zt);
                }
            }
        });
    }

    private void sfCreated(final SurfaceHolder holder) {
        // 必须在surface创建后才能初始化MediaPlayer,否则不会显示图像
//        String path = APIS.BASE_URL + mGoods.video_url;

        if(mediaPlayer == null)
            mediaPlayer = new MediaPlayer();
        else
            mediaPlayer.reset();
        mediaThread =  new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String path = "http://k.youku.com/player/getFlvPath/sid/0439568193971123bde4f_00/st/mp4/fileid/03002001005295C830E7560195C55FFA6A0658-87E5-08D6-EA92-1176F9D63AC6?K=65ece419814db4e3261e8228&amp;hd=1&amp;myp=0&amp;ts=94&amp;ypp=0&amp;ep=ciaXE0yLXs8J5irYiT8bZH62IXNZXP4J9h%2BHgdJjALshSuG66jbSx%2Bi3S%2FlCF%2FAcAScDZp2AraHnbEUcYfhE3G0Q30bcP%2FqVj%2Fnh5axSw5Z2b20wcbykslSZQDbw&amp;ctype=12&amp;ev=1&amp;token=7361&amp;oip=3027497194";
                    mediaPlayer.setDataSource(path);
                    mediaPlayer.prepare();
                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mediaPlayer.setDisplay(surfaceHolder);

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
                } catch (Exception e) {
                    e.printStackTrace();
                    play.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(VideoActivity.this, "视屏正在加载中，请稍后", Toast.LENGTH_LONG).show();
                            play.setImageResource(R.drawable.zt);
                        }
                    });
                    
                    if (!isFinishing()) {
                    	sfCreated(holder);
					}
                }
            }
        });
        mediaThread.start();
    }

    private void setTimeCurr(final int current){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                long timer = DateUtil.conversionDate("2000-10-10 00:00:00", "yyyy-MM-dd HH:mm:ss").getTime();

                SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
                time1.setText(formatter.format(new Date(timer+current)));
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
        mediaThread.interrupt();
        // Activity销毁时停止播放，释放资源。不做这个操作，即使退出还是能听到视频播放的声音
    }

    public void back(View v) {
        this.finish();
    }

}

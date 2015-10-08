package com.shmuseum.customeview;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.shmuseum.entity.MapPoint;
import com.shmuseum.musesum.R;
import com.shmuseum.utils.DensityUtil;

@SuppressLint("UseValueOf")
public class DoteView extends PhotoView {

    // 最大放大倍数
	private static final float MAX_SCALE =1.8f ;
    // 最小放大倍数
	private static final float MIN_SCALE = 1;
    // 初始放大倍数
	public static final float DEFAULT_SCALE = 1.5f;

    // 放大镜宽
    private int BIGGER_WIDTH = 20;
    // 放大镜高
    private int BIGGER_HEIGHT = 20;

    // 小人宽
    private int MARKER_WIDTH = 20;
    // 小人高
    private int MARKER_HEIGHT = 22;

	private Paint mPaint;
	private Path mPath;

	private Paint arrowPaint;
	private Path arrowPath;

	private float phase = 18;
	float x = 0;
	float y = 0;
	float orgWidth = 813;
	float orgHeight = 2125;

	public float imgWidth;
	public float imgHeight;

	public float canvasWidth;
	public float canvasHeight;

	private float topOrgX;
	private float topOrgY;

	private Handler mHandler;

	List<RectF> rectFs = new ArrayList<RectF>();
	List<Integer> beginAngs = new ArrayList<Integer>();
	List<Integer> sweepAngs = new ArrayList<Integer>();

	List<MapPoint> markers = new ArrayList<MapPoint>();
	List<MapPoint> biggerPoint = new ArrayList<MapPoint>();
	
	private Bitmap markerBitmap;
	private Bitmap redMarkerBitmap;
	private Bitmap biggerBitmap;

	private Canvas canvas;

	private int currentId = -1;
	private Context mContext;
	private MapPoint mCurrentMarker;

	public DoteView(Context context) {
		super(context);
		this.mContext = context;
		
		initDote();
	}

	public DoteView(Context context, AttributeSet set) {
		super(context, set);
		this.mContext = context;
		
		initDote();
	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		if (imgWidth == 0 && imgHeight == 0) {
			canvasWidth = getWidth();
			canvasHeight = getHeight();

			imgHeight = getHeight();
			imgWidth = imgHeight / orgHeight * orgWidth;

			topOrgX = canvasWidth / 2 - imgWidth / 2;
			topOrgY = 0;

			x = topOrgX;
			y = topOrgY + imgHeight * 0.264f;

			mPath.moveTo(x, y);

			//虚线
//			PathEffect pe = new DashPathEffect(new float[] { 8f, 8f }, phase);
//			mPaint.setPathEffect(pe);
		}

		Bitmap canBitmap = Bitmap.createBitmap((int) getWidth(), (int) imgHeight,
				Bitmap.Config.ARGB_4444);
		Canvas myCanvas = new Canvas(canBitmap);

		myCanvas.drawPath(mPath, mPaint);
		myCanvas.drawPath(arrowPath, arrowPaint);

		for (int i = 0; i < rectFs.size(); i++) {
			myCanvas.drawArc(rectFs.get(i), beginAngs.get(i), sweepAngs.get(i),
					false, mPaint);
		}
		for (int i = 0; i < markers.size(); i++) {
			if (markers.get(i).id == currentId) {
				this.mCurrentMarker = markers.get(i);
				drawImage(myCanvas, redMarkerBitmap,
						(int) (markers.get(i).x) - MARKER_WIDTH / 2, (int) (markers.get(i).y) - MARKER_HEIGHT / 2, MARKER_WIDTH,
						MARKER_HEIGHT, 0, 0);
			} else {
				drawImage(myCanvas, markerBitmap,
						(int) (markers.get(i).x) - MARKER_WIDTH/2,
						(int) (markers.get(i).y) -MARKER_HEIGHT/2, MARKER_WIDTH, MARKER_HEIGHT, 0, 0);
			}
		}
		for (int i = 0; i < biggerPoint.size(); i++) {
			drawImage(myCanvas, biggerBitmap, (int)biggerPoint.get(i).x - BIGGER_WIDTH/2,
					(int)biggerPoint.get(i).y - BIGGER_HEIGHT/2, BIGGER_WIDTH, BIGGER_HEIGHT, 0, 0);
		}
		
		canvas.drawBitmap(canBitmap, getDisplayMatrix(), mPaint);

	}

	/*---------------------------------
	 * 绘制图片
	 * @param       x屏幕上的x坐标
	 * @param       y屏幕上的y坐标
	 * @param       w要绘制的图片的宽度
	 * @param       h要绘制的图片的高度
	 * @param       bx图片上的x坐标
	 * @param       by图片上的y坐标
	 *
	 * @return      null
	 ------------------------------------*/
	public void drawImage(Canvas canvas, Bitmap blt, int x, int y, int w,
			int h, int bx, int by) {
		Rect src = new Rect();// 图片 >>原矩形
		Rect dst = new Rect();// 屏幕 >>目标矩形

		src.left = bx;
		src.top = by;
		src.right = bx + w;
		src.bottom = by + h;

		dst.left = x;
		dst.top = y;
		dst.right = x + w;
		dst.bottom = y + h;
		// 画出指定的位图，位图将自动--》缩放/自动转换，以填补目标矩形
		// 这个方法的意思就像 将一个位图按照需求重画一遍，画后的位图就是我们需要的了
		canvas.drawBitmap(blt, null, dst, mPaint);
		src = null;
		dst = null;
	}

	public void initDote() {
		setZoomable(true);

		setMaximumScale(MAX_SCALE);
		setMinimumScale(MIN_SCALE);

		mHandler = new Handler();
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);//抗锯齿
		mPaint.setStyle(Style.STROKE);
		mPaint.setStrokeWidth(2);
		mPaint.setColor(getResources().getColor(R.color.line_color));
		mPath = new Path();

		arrowPath = new Path();
		arrowPaint = new Paint();
		arrowPaint.setColor(Color.BLACK);
		arrowPaint.setStyle(Style.FILL);
		arrowPaint.setStrokeWidth(2);

		biggerBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bigger);
		
		MARKER_HEIGHT = DensityUtil.dip2px(mContext, MARKER_HEIGHT);
		MARKER_WIDTH = DensityUtil.dip2px(mContext, MARKER_WIDTH);
		
		BIGGER_WIDTH = DensityUtil.dip2px(mContext, BIGGER_WIDTH);
		BIGGER_HEIGHT = DensityUtil.dip2px(mContext, BIGGER_HEIGHT);
		
	}

	public void setMarkerResourceId(int id) {
		markerBitmap = BitmapFactory.decodeResource(getResources(),id);
	}

	public void setRedMarkerResourceId(int id) {
		redMarkerBitmap = BitmapFactory.decodeResource(getResources(), id);
	}

	public void addLine(float endX, float endY, boolean hasArrow) {
		drawAL(x, y, x + endX, y + endY, hasArrow);
		x = x + endX;
		y = y + endY;
		mPath.lineTo(x, y);
		invalidate();
	}

	public void addCircle(int beginAng, int sweepAng, float rx, float ry) {
		Log.i("width ", rx + " " + ry);

		RectF oval = new RectF();
		oval.left = x - rx;
		oval.top = y;
		oval.right = x + rx;
		oval.bottom = y + ry;
		if (beginAng <= 0) {
			x += rx;
			y += ry;
		} else {
			x -= rx;
			y += ry;
		}

		rectFs.add(oval);
		beginAngs.add(beginAng);
		sweepAngs.add(sweepAng);

		mPath.moveTo(x, y);
		invalidate();

	}

	@SuppressLint("UseValueOf")
	private void drawAL(float sx, float sy, float ex, float ey, boolean hasArrow) {
		float centerX = (sx + ex) / 2;
		float centerY = (sy + ey) / 2;

		double H = 20;
		double L = 11;
		int x3 = 0;
		int y3 = 0;
		int x4 = 0;
		int y4 = 0;
		double awrad = Math.atan(L / H);
		double arraow_len = Math.sqrt(L * L + H * H);
		double[] arrXY_1 = rotateVec(centerX - sx, centerY - sy, awrad, true,
				arraow_len);
		double[] arrXY_2 = rotateVec(centerX - sx, centerY - sy, -awrad, true,
				arraow_len);
		double x_3 = centerX - arrXY_1[0];
		double y_3 = centerY - arrXY_1[1];
		double x_4 = centerX - arrXY_2[0];
		double y_4 = centerY - arrXY_2[1];
		Double X3 = new Double(x_3);
		x3 = X3.intValue();
		Double Y3 = new Double(y_3);
		y3 = Y3.intValue();
		Double X4 = new Double(x_4);
		x4 = X4.intValue();
		Double Y4 = new Double(y_4);
		y4 = Y4.intValue();

		if (hasArrow) {
			arrowPath.moveTo(centerX, centerY);
			arrowPath.lineTo(x3, y3);
			arrowPath.lineTo(x4, y4);
			arrowPath.close();
		}
	}

	public double[] rotateVec(float px, float py, double ang, boolean isChLen,
			double newLen) {
		double mathstr[] = new double[2];
		double vx = px * Math.cos(ang) - py * Math.sin(ang);
		double vy = px * Math.sin(ang) + py * Math.cos(ang);
		if (isChLen) {
			double d = Math.sqrt(vx * vx + vy * vy);
			vx = vx / d * newLen;
			vy = vy / d * newLen;
			mathstr[0] = vx;
			mathstr[1] = vy;
		}
		return mathstr;
	}

	public void addMarker(List<MapPoint> markers) {
		this.markers = markers;
		invalidate();
	}
	
	public void setCurrentId(int id) {
		this.currentId = id;
        for (int i = 0; i < markers.size(); i++) {
            if (markers.get(i).id == currentId) {
                setScale(getScale() - 0.1f, false);
                setScale(getScale() + 0.1f, markers.get(i).x + 100, markers.get(i).y, false);
            }
        }
		invalidate();
	}

	public void addBigger(List<MapPoint> biggers) {
		this.biggerPoint = biggers;
		invalidate();
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return super.onTouchEvent(event);
	}
	
}

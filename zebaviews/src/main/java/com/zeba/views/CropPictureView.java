package com.zeba.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class CropPictureView extends View {
	public boolean isDebug=false;

	private Bitmap bitmap;
	private Matrix matrix;
	private Matrix savedMatrix;
	private Paint mPaint;
	private Rect mRect;
	private Rect mRectr;

	private static final int NONE = 0;
	private static final int DRAG = 1;
	private static final int ZOOM = 2;
	private int mode = NONE;
	private float oldDist;
	private PointF startPoint = new PointF();
	private PointF middlePoint = new PointF();
	private PointF movePoint = new PointF();
	private PointF distPoint = new PointF();
	private PointF lastPoint =new PointF();
	private float[] points;
	private float mCropLeft;
	private float mCropTop;
	private float mCropLeftv;
	private float mCropTopv;
	private float bmpWidth;
	private float bmpHeight;
	private float bitmapWidth;
	private float bitmapHeight;
	private int mWidth;
	private int mHeight;
	private float scalen;
	private float mscale=1;
	private Rect mTopLayerRect;
	private Rect mBottomLayerRect;
	private Paint mLayerPaint;
	private ScaleMoveViewListener smvl;

	public CropPictureView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		matrix = new Matrix();
		savedMatrix =new Matrix();
		mPaint=new Paint();
		mPaint.setStrokeWidth(3);
		mPaint.setStyle(Style.STROKE);
		mPaint.setColor(Color.parseColor("#ffffff"));
		mRect=new Rect();
		mRectr=new Rect();
		mRect.left=100;
		mRect.top=100;
		mRect.right=500;
		mRect.bottom=500;
		mCropLeft=mRect.left;
		mCropTop=mRect.top;
		mCropLeftv=0;
		mCropTopv=0;
		distPoint.set(mCropLeft,mCropTop);
		mTopLayerRect=new Rect();
		mBottomLayerRect=new Rect();
		mLayerPaint=new Paint();
		mLayerPaint.setStyle(Style.FILL);
		mLayerPaint.setColor(Color.parseColor("#88000000"));
	}
	public void setScaleMoveViewListener(ScaleMoveViewListener sListener){
		smvl=sListener;
	}
	public void setStrokeColor(int color){
		mPaint.setColor(color);
	}
	public void initRectLocation(int w,int h){
		mWidth=w;
		mHeight=h;
		mRect.left=0;
		mRect.right=w;
		mRect.top=h/2-w/2;
		mRect.bottom=mRect.top+w;
		mTopLayerRect.left=0;
		mTopLayerRect.top=0;
		mTopLayerRect.right=w;
		mTopLayerRect.bottom=mRect.top;
		mBottomLayerRect.left=0;
		mBottomLayerRect.top=mRect.bottom;
		mBottomLayerRect.right=w;
		mBottomLayerRect.bottom=h;
	}
	public void moveCropRect(int x,int y){
		mRect.left+=x;
		mRect.right+=x;
		mRect.top+=y;
		mRect.bottom+=y;
		mRectr.left+=x;
		mRectr.right+=x;
		mRectr.top+=y;
		mRectr.bottom+=y;
	}
	public void initShowBitmap(){
		if(bitmap==null){
			return;
		}
		bmpWidth=bitmap.getWidth();
		bmpHeight=bitmap.getHeight();
		bitmapWidth=bitmap.getWidth();
		bitmapHeight=bitmap.getHeight();
		points=new float[]{0,0,bitmap.getWidth(),bitmap.getHeight()};
		float s=1f;
		if(bitmapWidth<mWidth){
			s=mWidth/bitmapWidth;
		}
		if(bitmapHeight<mHeight){
			if(mHeight/bitmapHeight>s){
				s=mHeight/bitmapHeight;
			}
		}
		if(s!=1f){
			matrix.postScale(s,s);
			savedMatrix.set(matrix);
			mscale=s;
		}
		invalidate();
	}
	public void setBitmap(Bitmap bt) {
		bitmap = bt;
		post(new Runnable() {
			@Override
			public void run() {
				initShowBitmap();
			}
		});
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		mWidth=getMeasuredWidth();
		mHeight=getMeasuredHeight();
		initRectLocation(mWidth,mHeight);

	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
			case MotionEvent.ACTION_DOWN:
				savedMatrix.set(matrix);
				startPoint.set(event.getX(), event.getY());
				movePoint.set(event.getX(), event.getY());
				lastPoint.x=event.getX();
				lastPoint.y=event.getY();
				mode = DRAG;
				if(smvl!=null){
					smvl.TouchDown();
				}
				break;
			case MotionEvent.ACTION_UP:
				if(smvl!=null){
					smvl.TouchUp();
				}
			case MotionEvent.ACTION_POINTER_UP:
				if(mode == ZOOM){
					bmpWidth*=scalen;
					bmpHeight*=scalen;
					mscale=bmpWidth/bitmapWidth;
				}
				mode = NONE;
				break;
			case MotionEvent.ACTION_POINTER_DOWN:
				oldDist = spacing(event);
				if (oldDist > 10f) {
					savedMatrix.set(matrix);
					midPoint(middlePoint, event);
					mode = ZOOM;
				}
				break;
			case MotionEvent.ACTION_MOVE:
				if (mode == DRAG) {
					matrix.set(savedMatrix);
					float x=event.getX() - startPoint.x;
					float y=event.getY()- startPoint.y;
					mCropLeftv-=event.getX() - movePoint.x;
					mCropTopv-=event.getY()- movePoint.y;
					distPoint.set(mCropLeftv,mCropTopv);
					movePoint.set(event.getX(), event.getY());
					matrix.postTranslate(x,y);
					points=new float[]{0,0,bitmap.getWidth(),bitmap.getHeight()};
					matrix.mapPoints(points);
					if(points[0]>0){
						x=0;
					}
					if(points[2]<mRect.right){
						x=0;
					}
					if(points[1]>mRect.top){
						y=0;
					}
					if(points[3]<mRect.bottom){
						y=0;
					}
					matrix.set(savedMatrix);
					matrix.postTranslate(x,y);
					savedMatrix.set(matrix);
					lastPoint.x=x;
					lastPoint.y=y;
					startPoint.x=event.getX();
					startPoint.y=event.getY();
				} else if (mode == ZOOM) {
					float newDist = spacing(event);
					if (newDist > 10f) {
						matrix.set(savedMatrix);
						float sc = newDist / oldDist;
						matrix.postScale(sc, sc, middlePoint.x, middlePoint.y);
						points=new float[]{0,0,bitmap.getWidth(),bitmap.getHeight()};
						matrix.mapPoints(points);
						boolean isLan=false;
						if(points[0]>0){
							isLan=true;
						}
						if(points[2]<mRect.right){
							isLan=true;
						}
						if(points[1]>mRect.top){
							isLan=true;
						}
						if(points[3]<mRect.bottom){
							isLan=true;
						}
						if(isLan){
							if(isDebug){
								Log.e("zwz","not ok scalen="+scalen);
							}
							matrix.set(savedMatrix);
							matrix.postScale(scalen, scalen, middlePoint.x, middlePoint.y);
							if(isGoIn()){
								matrix.set(savedMatrix);
							}
						}else{
							scalen=sc;
							if(isDebug){
								Log.e("zwz","ok scalen="+scalen);
							}
						}
					}
				}
				break;
		}
		invalidate();
		return true;
	}
	private boolean isGoIn(){
		points=new float[]{0,0,bitmap.getWidth(),bitmap.getHeight()};
		matrix.mapPoints(points);
		boolean isLan=false;
		if(points[0]>0){
			isLan=true;
		}
		if(points[2]<mRect.right){
			isLan=true;
		}
		if(points[1]>mRect.top){
			isLan=true;
		}
		if(points[3]<mRect.bottom){
			isLan=true;
		}
		return isLan;
	}
	public Bitmap getCropedBitmap(){
		points=new float[]{0,0,bitmap.getWidth(),bitmap.getHeight()};
		matrix.mapPoints(points);
		if(isDebug){
			Log.e("zwz","p0="+points[0]+",p1="+points[1]+",p2="+points[2]+",p3="+points[3]);
		}
		RectF mRectfF=new RectF();
		mRectfF.left=mRect.left;
		mRectfF.top=mRect.top;
		mRectfF.right=mRect.right;
		mRectfF.bottom=mRect.bottom;
		if(points[0]<0){
			float pyx=Math.abs(points[0]);
			mRectfF.left+=pyx;
			mRectfF.right+=pyx;
			points[0]+=pyx;
			points[2]+=pyx;
		}else{
			float pyx=Math.abs(points[0]);
			mRectfF.left-=pyx;
			mRectfF.right-=pyx;
			points[0]-=pyx;
			points[2]-=pyx;
		}
		if(points[1]<0){
			float pyy=Math.abs(points[1]);
			mRectfF.top+=pyy;
			mRectfF.bottom+=pyy;
			points[1]+=pyy;
			points[3]+=pyy;
		}else{
			float pyy=Math.abs(points[1]);
			mRectfF.top-=pyy;
			mRectfF.bottom-=pyy;
			points[1]-=pyy;
			points[3]-=pyy;
		}
		mscale=points[2]/bitmap.getWidth();
		int x=(int) (mRectfF.left/mscale);
		int y=(int) (mRectfF.top/mscale);
		int w=(int) ((mRectfF.right-mRectfF.left)/mscale);
		int h=(int) ((mRectfF.bottom-mRectfF.top)/mscale);
		return Bitmap.createBitmap(bitmap,x,y,w,h);
	}
	private float spacing(MotionEvent event) {
		float x = event.getX(0) - event.getX(1);
		float y = event.getY(0) - event.getY(1);
		return (float)Math.sqrt(x * x + y * y);
	}

	private void midPoint(PointF point, MotionEvent event) {
		float x = event.getX(0) + event.getX(1);
		float y = event.getY(0) + event.getY(1);
		point.set(x / 2, y / 2);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawARGB(255, 0, 0, 0);
		if(bitmap!=null){
			canvas.drawBitmap(bitmap, matrix, null);
		}
		canvas.drawRect(mTopLayerRect, mLayerPaint);
		canvas.drawRect(mBottomLayerRect, mLayerPaint);
		canvas.drawRect(mRect,mPaint);


	}
	public interface ScaleMoveViewListener{
		public void TouchDown();
		public void TouchUp();
	}
}

package kr.co.redstrap.campusting.util;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;

public class CircleDrawable extends Drawable {

	private Bitmap mBitmap;
	private RectF mRectF;
	private Paint mPaint;
	private int mBitmapWidth;
	private int mBitmapHeight;

	public CircleDrawable() {
		// TODO Auto-generated constructor stub
	}

	public CircleDrawable(Bitmap bitmap) {
		this.mBitmap = bitmap;
		mRectF = new RectF();
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		BitmapShader shader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
		mPaint.setShader(shader);
		mBitmapWidth = mBitmap.getWidth();
		mBitmapHeight = mBitmap.getHeight();
	}

	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		canvas.drawOval(mRectF, mPaint);
	}

	@Override
	public void setDither(boolean dither) {
		// TODO Auto-generated method stub
		super.setDither(dither);
	}

	@Override
	public void setFilterBitmap(boolean filter) {
		// TODO Auto-generated method stub
		super.setFilterBitmap(filter);
	}

	@Override
	protected void onBoundsChange(Rect bounds) {
		super.onBoundsChange(bounds);
		mRectF.set(bounds);
	}

	@Override
	public int getIntrinsicWidth() {
		return mBitmapWidth;
	}

	@Override
	public int getIntrinsicHeight() {
		return mBitmapHeight;
	}

	@Override
	public void setAlpha(int alpha) {
		if (mPaint.getAlpha() != alpha) {
			mPaint.setAlpha(alpha);
			invalidateSelf();
		}
	}

	@Override
	public void setColorFilter(ColorFilter cf) {
		mPaint.setColorFilter(cf);
	}

	@Override
	public int getOpacity() {
		return PixelFormat.TRANSLUCENT;
	}

	public void setAntiAlias(boolean aa) {
		mPaint.setAntiAlias(aa);
		invalidateSelf();
	}

	public Bitmap getBitmap() {
		return mBitmap;
	}

}

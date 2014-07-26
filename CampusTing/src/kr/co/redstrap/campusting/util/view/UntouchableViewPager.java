package kr.co.redstrap.campusting.util.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class UntouchableViewPager extends ViewPager {

	private boolean mIsEnabled;

	public UntouchableViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		mIsEnabled = true;
		// TODO Auto-generated constructor stub
	}
	public UntouchableViewPager(Context context) {
		super(context);
		mIsEnabled = true;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return mIsEnabled ? super.onInterceptTouchEvent(arg0) : false;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return mIsEnabled ? super.onTouchEvent(arg0) : false;
	}
	
	public void setPagingEnabled(boolean enabled) {
		mIsEnabled = enabled;
	}
}

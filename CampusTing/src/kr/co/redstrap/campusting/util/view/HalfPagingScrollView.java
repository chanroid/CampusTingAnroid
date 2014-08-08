package kr.co.redstrap.campusting.util.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.HorizontalScrollView;

public class HalfPagingScrollView extends HorizontalScrollView {

	public HalfPagingScrollView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}
	
	

	public HalfPagingScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}



	public HalfPagingScrollView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}



	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		// TODO Auto-generated method stub
		super.onScrollChanged(l, t, oldl, oldt);
		Log.i("HalfPagingScrollView", "l : " + l + ", oldl :" + oldl);
	}
	
}

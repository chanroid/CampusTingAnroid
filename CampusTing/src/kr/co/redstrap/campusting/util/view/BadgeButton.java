package kr.co.redstrap.campusting.util.view;

import kr.co.redstrap.campusting.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BadgeButton extends LinearLayout {
	
	private ImageView button;
	private TextView badge;
	
	private boolean zeroHide;

	public BadgeButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
		// TODO Auto-generated constructor stub
	}

	public BadgeButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
		// TODO Auto-generated constructor stub
	}
	
	public BadgeButton(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}

	private void init() {
		inflate(getContext(), R.layout.button_badge, this);
		
		button = (ImageView) findViewById(R.id.badgeBtn);
		badge = (TextView) findViewById(R.id.badgeBtnText);
	}
	
	public void setImage(Drawable image) {
		button.setImageDrawable(image);
	}
	
	public void setImage(int resId) {
		button.setImageResource(resId);
	}
	
	public void setImage(Bitmap bitmap) {
		button.setImageBitmap(bitmap);
	}
	
	public void setBadgeCount(int count) {
		badge.setText(String.valueOf(count));
		if (count == 0 && zeroHide) {
			badge.setVisibility(INVISIBLE);
		} else {
			badge.setVisibility(VISIBLE);
		}
	}
	
	public void setZeroHide(boolean hide) {
		zeroHide = hide;
	}
	
}

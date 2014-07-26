package kr.co.redstrap.campusting.main;

import kr.co.redstrap.campusting.R;
import kr.co.redstrap.campusting.common.AbsCTLayout;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainBuildingPopupLayout extends AbsCTLayout {

	public interface Callback {
		public void onCloseClick();
		public void onConfirmClick();
	}
	
	private Callback callback;
	
	private Button closeBtn;
	private ImageView mainImg;
	private TextView titleText;
	private TextView descText;
	private Button confirmClick;
	
	public MainBuildingPopupLayout(Context ctx) {
		super(ctx);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getRootViewId() {
		// TODO Auto-generated method stub
		return R.layout.popup_building_main;
	}

	@Override
	public void inflateViews() {
		// TODO Auto-generated method stub
		
		Listener listener = new Listener();

		closeBtn = (Button) findViewById(R.id.popupCloseBtn);
		closeBtn.setOnClickListener(listener);
		mainImg = (ImageView) findViewById(R.id.popupMainImage);
		titleText = (TextView) findViewById(R.id.popupTitle);
		descText = (TextView) findViewById(R.id.popupDesc);
		confirmClick = (Button) findViewById(R.id.popupConfirmBtn);
		confirmClick.setOnClickListener(listener);
	}
	
	public void setMainImage(int resId) {
		mainImg.setImageResource(resId);
	}
	
	public void setMainImage(Drawable mainImage) {
		// TODO Auto-generated method stub
		mainImg.setImageDrawable(mainImage);
	}
	
	public void setTitle(int resId) {
		titleText.setText(resId);
	}
	
	public void setTitle(CharSequence cs) {
		titleText.setText(cs);
	}
	
	public void setDesc(int resId) {
		descText.setText(resId);
	}
	
	public void setDesc(CharSequence cs) {
		descText.setText(cs);
	}
	
	public void setCallback(Callback callback) {
		this.callback = callback;
	}
	
	private class Listener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.popupCloseBtn:
				callback.onCloseClick();
				break;
			case R.id.popupConfirmBtn:
				callback.onConfirmClick();
				break;
			}
		}
		
	}

}

package kr.co.redstrap.campusting.main;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import kr.co.redstrap.campusting.R;
import kr.co.redstrap.campusting.common.AbsCTLayout;

public class PremiumSearchPopupLayout extends AbsCTLayout {
	
	
	public interface Callback {
		public void onCloseClick();
	}
	
	private Callback callback;
	
	private ImageButton closeBtn;
	
	public void setCallback(Callback callback) {
		this.callback = callback;
	}

	public PremiumSearchPopupLayout(Context ctx) {
		super(ctx);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getRootViewId() {
		// TODO Auto-generated method stub
		return R.layout.popup_search_premium;
	}

	@Override
	public void inflateViews() {
		// TODO Auto-generated method stub

		Listener l = new Listener();
		
		closeBtn = (ImageButton) findViewById(R.id.premiumsearchBackBtn);
		closeBtn.setOnClickListener(l);
		
	}
	
	private class Listener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.premiumsearchBackBtn:
				callback.onCloseClick();
				break;
			}
		}
		
	}

}

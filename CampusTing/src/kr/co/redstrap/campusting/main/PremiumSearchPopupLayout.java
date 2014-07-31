package kr.co.redstrap.campusting.main;

import android.content.Context;
import kr.co.redstrap.campusting.R;
import kr.co.redstrap.campusting.common.AbsCTLayout;

public class PremiumSearchPopupLayout extends AbsCTLayout {
	
	
	public interface Callback {
		
	}
	
	private Callback callback;
	
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

	}

}

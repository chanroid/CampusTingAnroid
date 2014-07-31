package kr.co.redstrap.campusting.main;

import android.content.Context;
import kr.co.redstrap.campusting.R;
import kr.co.redstrap.campusting.common.AbsCTLayout;

public class MatchingResultLayout extends AbsCTLayout {

	public interface Callback {
		public void onBackClick();
	}
	
	private Callback callback;
	
	public void setCallback(Callback callback) {
		this.callback = callback;
	}
	
	public MatchingResultLayout(Context ctx) {
		super(ctx);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getRootViewId() {
		// TODO Auto-generated method stub
		return R.layout.activity_matching_result;
	}

	@Override
	public void inflateViews() {
		// TODO Auto-generated method stub

	}

}

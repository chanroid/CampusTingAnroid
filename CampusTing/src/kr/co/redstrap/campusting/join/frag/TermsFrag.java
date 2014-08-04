package kr.co.redstrap.campusting.join.frag;

import kr.co.redstrap.campusting.join.AbsJoinFrag;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TermsFrag extends AbsJoinFrag {

	// 뷰
	private TermsLayout layout;

	//

	public TermsFrag() {
		super();
	}

	@Override
	public void onResume() {
		super.onResume();
		Log.i("TermsFrag :: onResume", "onResume");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Log.i("TermsFrag :: onCreateView", "onCreateView");
		layout = new TermsLayout(actContext);

		return layout.getView();
	}

	@Override
	public boolean isComfirmed() {
		// TODO Auto-generated method stub
		return true;
	}

}

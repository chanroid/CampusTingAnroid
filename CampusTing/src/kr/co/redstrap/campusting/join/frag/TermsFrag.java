package kr.co.redstrap.campusting.join.frag;

import kr.co.redstrap.campusting.join.AbsJoinFrag;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TermsFrag extends AbsJoinFrag implements TermsLayout.Callback {

	// 뷰
	private TermsLayout layout;

	//
	private boolean polishConfirmed;
	private boolean privacyConfirmed;

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
		layout.setCallback(this);

		return layout.getView();
	}

	@Override
	public boolean isComfirmed() {
		// TODO Auto-generated method stub
		return polishConfirmed && privacyConfirmed;
	}

	@Override
	public void onPolicyClick() {
		// TODO Auto-generated method stub
		polishConfirmed = true;
		if (isComfirmed())
			callback.goNext(0);
	}

	@Override
	public void onPrivacyClick() {
		// TODO Auto-generated method stub
		privacyConfirmed = true;
		if (isComfirmed())
			callback.goNext(0);
	}

}

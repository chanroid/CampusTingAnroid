package kr.co.redstrap.campusting.join.frag;

import kr.co.redstrap.campusting.join.AbsJoinFrag;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class NormalJoinFrag extends AbsJoinFrag {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Log.i("NormalJoinFrag :: onCreateView", "onCreateView");
		NormalJoinLayout rootView = new NormalJoinLayout(actContext);

		return rootView.getView();
	}

}

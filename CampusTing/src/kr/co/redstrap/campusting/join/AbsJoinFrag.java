package kr.co.redstrap.campusting.join;

import android.app.Activity;
import android.support.v4.app.Fragment;

public class AbsJoinFrag extends Fragment {

	public JoinActivity actContext;

	public AbsJoinFrag() {

	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.actContext = (JoinActivity) activity;
	}

}

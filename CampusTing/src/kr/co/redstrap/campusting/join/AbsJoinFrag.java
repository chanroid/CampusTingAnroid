package kr.co.redstrap.campusting.join;

import android.app.Activity;
import android.support.v4.app.Fragment;

public abstract class AbsJoinFrag extends Fragment {
	
	public interface Callback {
		public void goNext(int currentIndex);
	}
	
	public Callback callback;
	
	public void setCallback(Callback callback) {
		this.callback = callback;
	}

	public JoinActivity actContext;

	public AbsJoinFrag() {

	}
	
	public abstract boolean isComfirmed();

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.actContext = (JoinActivity) activity;
	}

}

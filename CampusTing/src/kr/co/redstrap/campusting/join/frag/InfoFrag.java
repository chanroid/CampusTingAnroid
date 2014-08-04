package kr.co.redstrap.campusting.join.frag;

import kr.co.redstrap.campusting.R;
import kr.co.redstrap.campusting.join.AbsJoinFrag;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class InfoFrag extends AbsJoinFrag {

	private InfoClickListener infoClick;

	// 뷰
	// 20140703 chanroid
	// 뷰가 하나 뿐이라 아직은 굳이 분리할 필요가 없음
	private Button finalConfirm;

	public InfoFrag() {
		super();
	}

	@Override
	public void onResume() {
		super.onResume();
		Log.i("InfoFrag :: onResume", "onResume");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i("InfoFrag :: onCreateView", "onCreateView");
		View rootView = inflater.inflate(R.layout.activity_join_info,
				container, false);

		infoClick = new InfoClickListener();

		// 뷰 연결
		finalConfirm = (Button) rootView.findViewById(R.id.joininfoConfirmBtn);

		// 뷰 셋팅
		finalConfirm.setOnClickListener(infoClick);
		return rootView;
	}

	private class InfoClickListener implements OnClickListener {

		@Override
		public void onClick(View view) {
			if (view == finalConfirm) {

			}
		}

	}

	@Override
	public boolean isComfirmed() {
		// TODO Auto-generated method stub
		return true;
	}
}

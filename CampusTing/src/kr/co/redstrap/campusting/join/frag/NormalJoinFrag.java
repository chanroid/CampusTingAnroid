package kr.co.redstrap.campusting.join.frag;

import kr.co.redstrap.campusting.join.AbsJoinFrag;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class NormalJoinFrag extends AbsJoinFrag implements NormalJoinLayout.Callback {
	
	public static class NormalJoinInfo {
		public String email;
		public String pw; // 연동 코드로 대체
		public String nickName;
		public String gender;
		public String birth;
	}
	
	private NormalJoinLayout layout;
	public NormalJoinInfo info;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Log.i("NormalJoinFrag :: onCreateView", "onCreateView");
		layout = new NormalJoinLayout(actContext);
		layout.setCallback(this);

		return layout.getView();
	}
	
	public void setNormalJoinInfo(NormalJoinInfo info) {
		this.info = info;
		layout.setBirth(info.birth);
		layout.setEmailId(info.email);
		layout.setGender(info.gender);
		layout.setPwText(info.pw);
	}

	@Override
	public boolean isComfirmed() {
		// TODO Auto-generated method stub
		return layout.isConfirmed();
	}

	@Override
	public void onNextClick() {
		// TODO Auto-generated method stub
		callback.goNext(1);
	}

}

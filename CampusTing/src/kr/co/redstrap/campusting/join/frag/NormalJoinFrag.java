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
		public boolean gender;
		public String birth;
		public String promoCode;
	}
	
	private NormalJoinLayout layout;
	private NormalJoinInfo info;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Log.i("NormalJoinFrag :: onCreateView", "onCreateView");
		
		if (layout == null) {
			layout = new NormalJoinLayout(actContext);
			layout.setCallback(this);
		}

		layout.setBirth(info.birth);
		layout.setEmailId(info.email);
		layout.setGender(info.gender);
		layout.setPwText(info.pw);

		return layout.getView();
	}
	
	public void setNormalJoinInfo(NormalJoinInfo info) {
		this.info = info;
	}
	
	public NormalJoinInfo getInfo() {
		this.info = new NormalJoinInfo();
		
		info.birth = layout.getBirth();
		info.email = layout.getEmailId();
		info.gender = layout.getGender();
		info.nickName = layout.getNickname();
		info.pw = layout.getPwText();
		info.promoCode = layout.getPromoCode();
		
		return this.info;
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

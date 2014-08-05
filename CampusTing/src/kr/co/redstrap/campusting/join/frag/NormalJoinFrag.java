package kr.co.redstrap.campusting.join.frag;

import kr.co.redstrap.campusting.join.AbsJoinFrag;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class NormalJoinFrag extends AbsJoinFrag implements NormalJoinLayout.Callback {
	
	private NormalJoinLayout layout;
	private Object normalJoinInfo;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		Log.i("NormalJoinFrag :: onCreateView", "onCreateView");
		layout = new NormalJoinLayout(actContext);
		layout.setCallback(this);

		return layout.getView();
	}
	
	public void setNormalJoinInfo(Object info) {
		// 20140805 chanroid 해당 객체 생성되면 바꿀것
		// 대체로 객체에 있는 정보들 레이아웃에 세팅하는 코드
	}

	@Override
	public boolean isComfirmed() {
		// TODO Auto-generated method stub
		return layout.isConfirmed();
	}

	@Override
	public void onNextClick() {
		// TODO Auto-generated method stub
		
	}

}

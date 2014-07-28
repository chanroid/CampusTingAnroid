package kr.co.redstrap.campusting.join.frag;

import java.util.ArrayList;
import java.util.HashMap;

import kr.co.redstrap.campusting.join.AbsJoinFrag;
import kr.co.redstrap.campusting.main.MainActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ConfirmUnivFrag extends AbsJoinFrag implements ConfirmUnivFragLayout.Callback {
	
	private ConfirmUnivFragLayout layout;
	
	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		ArrayList<HashMap<String,String>> groupData = new ArrayList<HashMap<String,String>>();
		HashMap<String, String> map1 = new HashMap<String, String>();
		map1.put("title", "대학교 인증");
		HashMap<String, String> map2 = new HashMap<String, String>();
		map2.put("title", "직장 인증");
		groupData.add(map1);
		groupData.add(map2);
		
		layout = new ConfirmUnivFragLayout(actContext);
		layout.setCallback(this);
		
		return layout.getView();
	}

	@Override
	public void onUnivCardImageClick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUnivNameChanged(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUnivMailChanged(String mail) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUnivMailCodeRequestClick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUnivMailCodeConfirmClick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onJobCardImageClick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConfirmClick() {
		// TODO Auto-generated method stub

		// 20140723 chanroid 일단 그냥 메인으로 넘어가게 설정. 나중에 액티비티 구현할때 마저 구현
		startActivity(new Intent(getActivity(), MainActivity.class));
		getActivity().finish();
	}
}

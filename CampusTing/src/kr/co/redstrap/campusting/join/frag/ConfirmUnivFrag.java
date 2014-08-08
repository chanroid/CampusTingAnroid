package kr.co.redstrap.campusting.join.frag;

import kr.co.redstrap.campusting.join.AbsJoinFrag;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ConfirmUnivFrag extends AbsJoinFrag implements ConfirmUnivFragLayout.Callback {
	
	private ConfirmUnivFragLayout layout;
	
	public int univNum = -1;
	public int univState = 0;
	public String univMail = "";
	public String univMailCode = "";
	
	public String univCardImage = "";
	
	public String jobImage = "";
	
	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		layout = new ConfirmUnivFragLayout(actContext);
		layout.setCallback(this);
		
		return layout.getView();
	}

	@Override
	public void onUnivCardImageClick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUnivChanged(int num) {
		// TODO Auto-generated method stub
		univState = num;
	}

	@Override
	public void onUnivMailChanged(String mail) {
		// TODO Auto-generated method stub
		univMail = mail;
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
		if (isComfirmed())
			callback.goNext(4);
		else {
			// 20140808 chanroid 뭔가 안됐다고 알려줘야함
		}
	}

	@Override
	public boolean isComfirmed() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void onUnivMailConfirmClick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUnivCardConfirmClick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onJobConfirmClick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUnivStateChanged(int position) {
		// TODO Auto-generated method stub
		univState = position;
	}

}

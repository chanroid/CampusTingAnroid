package kr.co.redstrap.campusting.setting;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ModifyPhotoFragment extends Fragment implements ModifyPhotoLayout.Callback {

	private ModifyPhotoLayout layout;
	
	private ArrayList<String> profileImages = new ArrayList<String>();
	
	public ArrayList<String> getProfileImages() {
		return profileImages;
	}
	
	private int photoCount;
	
	public void setPhotoCount(int count) {
		photoCount = count;
		// 20140812 chanroid 이걸로 뭔가 로딩을 해야함
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		layout = new ModifyPhotoLayout(getActivity());
		layout.setCallback(this);
		
		return layout.getView();
	}

	@Override
	public void onProfileImageClick(int index) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
	}
}

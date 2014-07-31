package kr.co.redstrap.campusting.main;

import kr.co.redstrap.campusting.setting.ModifyProfileActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class IdCardFragment extends Fragment implements IdCardLayout.Callback {

	private IdCardLayout layout;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		layout = new IdCardLayout(getActivity());
		layout.setCallback(this);
		
		return layout.getView();
	}
	
	@Override
	public void onProfileImageClick(int position) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onModifyProfileClick() {
		// TODO Auto-generated method stub
		startActivity(new Intent(getActivity(), ModifyProfileActivity.class));
	}

}

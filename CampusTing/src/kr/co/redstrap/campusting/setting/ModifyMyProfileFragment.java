package kr.co.redstrap.campusting.setting;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ModifyMyProfileFragment extends Fragment {
	
	private ModifyMyProfileLayout layout;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		layout = new ModifyMyProfileLayout(getActivity());
		
		return layout.getView();
	}

}

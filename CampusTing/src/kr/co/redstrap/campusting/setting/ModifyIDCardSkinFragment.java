package kr.co.redstrap.campusting.setting;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ModifyIDCardSkinFragment extends Fragment implements ModifyIDCardSkinLayout.Callback {

	private ModifyIDCardSkinLayout layout;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		layout = new ModifyIDCardSkinLayout(getActivity());
		layout.setCallback(this);
		
		return layout.getView();
	}

	@Override
	public void onSkinClick(int index) {
		// TODO Auto-generated method stub
		
	}
}

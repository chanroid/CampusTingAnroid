package kr.co.redstrap.campusting.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ChatFragment extends Fragment {

	private ChatFragmentLayout layout;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub\\
		
		layout = new ChatFragmentLayout(getActivity());
		
		return layout.getView();
	}
}

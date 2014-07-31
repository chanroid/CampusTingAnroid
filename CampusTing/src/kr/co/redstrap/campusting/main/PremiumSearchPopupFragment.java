package kr.co.redstrap.campusting.main;

import kr.co.redstrap.campusting.main.PremiumSearchPopupLayout.Callback;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

public class PremiumSearchPopupFragment extends DialogFragment implements Callback {

	private PremiumSearchPopupLayout layout;
	
	private int type;
	
	public PremiumSearchPopupFragment(int type) {
		// TODO Auto-generated constructor stub
		this.type = type;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Dialog dialog = super.onCreateDialog(savedInstanceState);
		dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		
		return dialog;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		layout = new PremiumSearchPopupLayout(getActivity());
		layout.setCallback(this);
		
		return layout.getView();
	}
}

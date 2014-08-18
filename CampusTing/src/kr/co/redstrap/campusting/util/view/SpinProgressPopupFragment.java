package kr.co.redstrap.campusting.util.view;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

public class SpinProgressPopupFragment extends DialogFragment {
	
	private SpinProgressPopupView layout;

	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Dialog dialog = super.onCreateDialog(savedInstanceState);

		dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		dialog.getWindow().setBackgroundDrawable(
				new ColorDrawable(Color.TRANSPARENT));

		return dialog;
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		setCancelable(false);
		layout = new SpinProgressPopupView(getActivity());
		layout.start();
		
		return layout.getView();
	}
	
	@Override
	public void dismiss() {
		// TODO Auto-generated method stub
		layout.stop();
		super.dismiss();
	}
}

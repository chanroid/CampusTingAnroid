package kr.co.redstrap.campusting.main;

import kr.co.redstrap.campusting.setting.SettingActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MoreFragment extends Fragment implements MoreLayout.Callback {

	private MoreLayout layout;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		layout = new MoreLayout(getActivity());
		layout.setCallback(this);

		return layout.getView();
	}

	@Override
	public void onProfileClick() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onNoticeClick() {
		// TODO Auto-generated method stub
		getActivity().startActivity(new Intent(getActivity(), NoticeActivity.class));
	}

	@Override
	public void onStoreClick() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onQAClick() {
		// TODO Auto-generated method stub
		getActivity().startActivity(new Intent(getActivity(), QAActivity.class));
	}

	@Override
	public void onSettingClick() {
		// TODO Auto-generated method stub
		getActivity().startActivity(new Intent(getActivity(), SettingActivity.class));
	}
}

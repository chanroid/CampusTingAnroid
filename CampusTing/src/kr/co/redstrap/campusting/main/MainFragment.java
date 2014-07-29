package kr.co.redstrap.campusting.main;

import kr.co.redstrap.campusting.constant.CampusTingConstant.Building;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MainFragment extends Fragment implements MainFragmentLayout.Callback {

	private MainFragmentLayout layout;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		layout = new MainFragmentLayout(getActivity());
		layout.setCallback(this);
		
		return layout.getView();
	}

	private void showBuildingDialog(int type) {
		MainBuildingPopupFragment dialog = new MainBuildingPopupFragment(type);
		dialog.show(getActivity().getSupportFragmentManager(), "tag");
	}


	@Override
	public void onAlertListClick() {
		// TODO Auto-generated method stub
		startActivity(new Intent(getActivity(), AlertListActivity.class));
		
	}

	@Override
	public void onShopClick() {
		// TODO Auto-generated method stub
		startActivity(new Intent(getActivity(), PurchaseActivity.class));
	}

	@Override
	public void onMainBuildingClick() {
		// TODO Auto-generated method stub
		showBuildingDialog(Building.MAIN);
	}

	@Override
	public void onGymClick() {
		// TODO Auto-generated method stub
		showBuildingDialog(Building.GYM);
		
	}

	@Override
	public void onCafeClick() {
		// TODO Auto-generated method stub
		showBuildingDialog(Building.CAFE);
		
	}

	@Override
	public void onLibraryClick() {
		// TODO Auto-generated method stub
		showBuildingDialog(Building.LIBRARY);
		
	}

	@Override
	public void onDomirotiesClick() {
		// TODO Auto-generated method stub
		showBuildingDialog(Building.DOMITORY);
		
	}

	@Override
	public void onCircleRoomClick() {
		// TODO Auto-generated method stub
		showBuildingDialog(Building.CIRCLEROOM);
		
	}

	@Override
	public void onHofClick() {
		// TODO Auto-generated method stub
		showBuildingDialog(Building.HOF);
		
	}

	@Override
	public void onClubClick() {
		// TODO Auto-generated method stub
		showBuildingDialog(Building.CLUB);
	}

	@Override
	public void onDayNightClick() {
		// TODO Auto-generated method stub
		
	}

}

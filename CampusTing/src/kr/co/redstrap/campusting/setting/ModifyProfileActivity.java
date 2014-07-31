package kr.co.redstrap.campusting.setting;

import kr.co.redstrap.campusting.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

public class ModifyProfileActivity extends FragmentActivity implements ModifyProfileLayout.Callback {

	private ModifyProfileLayout layout;
	
	private Fragment modifyPhotoFragment;
	private Fragment modifyProfileFragment;
	private Fragment modifyAppealFragment;
	private Fragment modifyIDCardSkinFragment;
	
	private static final int FRAGMENT_CONTAINER = R.id.modifyprofileFragmentContainer;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		
		layout = new ModifyProfileLayout(this);
		layout.setCallback(this);
		
		setContentView(layout.getView());
		initFragment();
		switchFragment(modifyPhotoFragment);
	}

	private void initFragment() {
		modifyPhotoFragment = new ModifyPhotoFragment();
		modifyProfileFragment = new ModifyMyProfileFragment();
		modifyAppealFragment = new ModifyAppealFragment();
		modifyIDCardSkinFragment = new ModifyIDCardSkinFragment();
		
		FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
		trans.add(FRAGMENT_CONTAINER, modifyPhotoFragment);
		trans.add(FRAGMENT_CONTAINER, modifyProfileFragment);
		trans.add(FRAGMENT_CONTAINER, modifyAppealFragment);
		trans.add(FRAGMENT_CONTAINER, modifyIDCardSkinFragment);
		trans.commit();
	}
	
	private void switchFragment(Fragment fragment) {
		FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
		trans.hide(modifyPhotoFragment);
		trans.hide(modifyProfileFragment);
		trans.hide(modifyAppealFragment);
		trans.hide(modifyIDCardSkinFragment);
		
		trans.show(fragment);
		trans.commit();
	}

	@Override
	public void onBackClick() {
		// TODO Auto-generated method stub
		finish();
	}

	@Override
	public void onPhotoTabClick() {
		// TODO Auto-generated method stub
		switchFragment(modifyPhotoFragment);
	}

	@Override
	public void onInfoTabClick() {
		// TODO Auto-generated method stub
		switchFragment(modifyProfileFragment);
	}

	@Override
	public void onAppealTabClick() {
		// TODO Auto-generated method stub
		switchFragment(modifyAppealFragment);
	}

	@Override
	public void onIDCardSkinTabClick() {
		// TODO Auto-generated method stub
		switchFragment(modifyIDCardSkinFragment);
	}

	@Override
	public void onConfirmClick() {
		// TODO Auto-generated method stub
		
	}
}

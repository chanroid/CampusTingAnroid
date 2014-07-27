package kr.co.redstrap.campusting.setting;

import kr.co.redstrap.campusting.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

public class ModifyProfileActivity extends FragmentActivity implements ModifyProfileLayout.Callback {

	private ModifyProfileLayout layout;
	
	private static final int FRAGMENT_CONTAINER = R.id.modifyprofileFragmentContainer;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		
		layout = new ModifyProfileLayout(this);
		layout.setCallback(this);
		
		setContentView(layout.getView());
	}

	private void initFragment() {
//		mainFrag = new MainFragment();
//		idCardFrag = new IdCardFragment();
//		moreFrag = new MoreFragment();
//		
//		FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
//		trans.add(FRAGMENT_CONTAINER, mainFrag);
//		trans.add(FRAGMENT_CONTAINER, idCardFrag);
//		trans.add(FRAGMENT_CONTAINER, moreFrag);
//		trans.commit();
	}
	
	private void switchFragment(Fragment fragment) {
//		FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
//		trans.hide(mainFrag);
//		trans.hide(idCardFrag);
//		trans.hide(moreFrag);
//		
//		trans.show(fragment);
//		trans.commit();
	}

	@Override
	public void onBackClick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPhotoTabClick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onInfoTabClick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAppealTabClick() {
		// TODO Auto-generated method stub
		
	}
}

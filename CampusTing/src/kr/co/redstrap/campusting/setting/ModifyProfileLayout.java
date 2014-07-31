package kr.co.redstrap.campusting.setting;

import kr.co.redstrap.campusting.R;
import kr.co.redstrap.campusting.common.AbsCTLayout;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckedTextView;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class ModifyProfileLayout extends AbsCTLayout {

	public interface Callback {
		public void onBackClick();
		public void onPhotoTabClick();
		public void onInfoTabClick();
		public void onAppealTabClick();
		public void onIDCardSkinTabClick();
		public void onConfirmClick();
	}
	
	private Callback callback;
	
	public void setCallback(Callback callback) {
		this.callback = callback;
	}
	
	private ImageButton backBtn;
	
	private LinearLayout confirmBtn;
	
	private CheckedTextView photoTab;
	private CheckedTextView infoTab;
	private CheckedTextView appealTab;
	private CheckedTextView idcardSkinTab;
	
	public ModifyProfileLayout(Context ctx) {
		super(ctx);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getRootViewId() {
		// TODO Auto-generated method stub
		return R.layout.activity_setting_modify_profile;
	}

	@Override
	public void inflateViews() {
		// TODO Auto-generated method stub

		Listener l = new Listener();
		
		backBtn = (ImageButton) findViewById(R.id.modifyProfileBackBtn);
		backBtn.setOnClickListener(l);
		photoTab = (CheckedTextView) findViewById(R.id.modifyProfilePhotoTab);
		photoTab.setOnClickListener(l);
		infoTab = (CheckedTextView) findViewById(R.id.modifyProfileInfoTab);
		infoTab.setOnClickListener(l);
		appealTab = (CheckedTextView) findViewById(R.id.modifyProfileAppealTab);
		appealTab.setOnClickListener(l);
		idcardSkinTab = (CheckedTextView) findViewById(R.id.modifyProfileEditCardSkinTab);
		idcardSkinTab.setOnClickListener(l);
		confirmBtn = (LinearLayout) findViewById(R.id.modifyProfileConfirmBtn);
		confirmBtn.setOnClickListener(l);
		
	}
	
	private void switchTabBackground(View v) {
		
	}
	
	
	private class Listener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.modifyProfileBackBtn:
				callback.onBackClick();
				break;
			case R.id.modifyProfilePhotoTab:
				callback.onPhotoTabClick();
				switchTabBackground(photoTab);
				break;
			case R.id.modifyProfileInfoTab:
				callback.onInfoTabClick();
				switchTabBackground(infoTab);
				break;
			case R.id.modifyProfileAppealTab:
				callback.onAppealTabClick();
				switchTabBackground(appealTab);
				break;
			case R.id.modifyProfileEditCardSkinTab:
				callback.onIDCardSkinTabClick();
				switchTabBackground(idcardSkinTab);
				break;
			case R.id.modifyProfileConfirmBtn:
				callback.onConfirmClick();
				break;
			}
		}
		
	}
}

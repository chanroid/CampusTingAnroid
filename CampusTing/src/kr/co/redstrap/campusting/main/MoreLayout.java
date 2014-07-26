package kr.co.redstrap.campusting.main;

import kr.co.redstrap.campusting.R;
import kr.co.redstrap.campusting.common.AbsCTLayout;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MoreLayout extends AbsCTLayout {

	public MoreLayout(Context ctx) {
		super(ctx);
		// TODO Auto-generated constructor stub
	}

	public interface Callback {
		public void onProfileClick();
		public void onNoticeClick();
		public void onStoreClick();
		public void onQAClick();
		public void onSettingClick();
	}
	
	private Callback callback;
	
	private RelativeLayout moreProfileBtn;
	
	private RelativeLayout moreNoticeBtn;
	private RelativeLayout moreStoreBtn;
	private RelativeLayout moreQABtn;
	private RelativeLayout moreSettingBtn;
	
	private ImageView moreProfileImg;
	private TextView moreNickText;
	private TextView moreUserinfoText;
	
	public void setCallback(Callback callback) {
		this.callback = callback;
	}


	@Override
	public int getRootViewId() {
		// TODO Auto-generated method stub
		return R.layout.fragment_more;
	}

	@Override
	public void inflateViews() {
		// TODO Auto-generated method stub
		
		Listener listener = new Listener();
		
		moreProfileBtn = (RelativeLayout) findViewById(R.id.moreProfileBtn);
		moreProfileBtn.setOnClickListener(listener);
		moreNoticeBtn = (RelativeLayout) findViewById(R.id.moreNoticeBtn);
		moreNoticeBtn.setOnClickListener(listener);
		moreStoreBtn = (RelativeLayout) findViewById(R.id.moreStoreBtn);
		moreStoreBtn.setOnClickListener(listener);
		moreQABtn = (RelativeLayout) findViewById(R.id.moreQABtn);
		moreQABtn.setOnClickListener(listener);
		moreSettingBtn = (RelativeLayout) findViewById(R.id.moreSettingBtn);
		moreSettingBtn.setOnClickListener(listener);
		
		moreProfileImg = (ImageView) findViewById(R.id.moreProfileImg);
		moreNickText = (TextView) findViewById(R.id.moreNicknameText);
		moreUserinfoText = (TextView) findViewById(R.id.moreUserInfoText);
		
	}
	
	private class Listener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.moreNoticeBtn:
				callback.onNoticeClick();
				break;
			case R.id.moreStoreBtn:
				callback.onStoreClick();
				break;
			case R.id.moreQABtn:
				callback.onQAClick();
				break;
			case R.id.moreSettingBtn:
				callback.onSettingClick();
				break;
			case R.id.moreProfileBtn:
				callback.onProfileClick();
				break;
			}
		}
		
	}

}

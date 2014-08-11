package kr.co.redstrap.campusting.main;

import kr.co.redstrap.campusting.R;
import kr.co.redstrap.campusting.common.AbsCTLayout;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

public class MainLayout extends AbsCTLayout {

	public interface Callback {
		public void onIdCardClick();

		public void onHistoryClick();

		public void onMainClick();

		public void onChatClick();

		public void onMoreClick();
	}

	private Callback callback;

	private RelativeLayout idcardBtn;
	private RelativeLayout historyBtn;
	private RelativeLayout mainBtn;
	private RelativeLayout chatBtn;
	private RelativeLayout moreBtn;

	public MainLayout(Context ctx) {
		super(ctx);
		// TODO Auto-generated constructor stub
	}
	
	public void setCallback(Callback callback) {
		this.callback = callback;
	}

	@Override
	public int getRootViewId() {
		// TODO Auto-generated method stub
		return R.layout.activity_main_fragment;
	}

	@Override
	public void inflateViews() {
		// TODO Auto-generated method stub
		
		Listener listener = new Listener();

		idcardBtn = (RelativeLayout) findViewById(R.id.mainIdCardBtn);
		idcardBtn.setOnClickListener(listener);
		historyBtn = (RelativeLayout) findViewById(R.id.mainHistoryBtn);
		historyBtn.setOnClickListener(listener);
		mainBtn = (RelativeLayout) findViewById(R.id.mainBtn);
		mainBtn.setOnClickListener(listener);
		chatBtn = (RelativeLayout) findViewById(R.id.mainChatBtn);
		chatBtn.setOnClickListener(listener);
		moreBtn = (RelativeLayout) findViewById(R.id.mainMoreBtn);
		moreBtn.setOnClickListener(listener);

		switchButtonBackground(mainBtn);
		
	}
	
	private void switchButtonBackground(View v) {
		idcardBtn.setBackgroundResource(R.drawable.abc_ab_bottom_solid_light_holo);
		historyBtn.setBackgroundResource(R.drawable.abc_ab_bottom_solid_light_holo);
		mainBtn.setBackgroundResource(R.drawable.abc_ab_bottom_solid_light_holo);
		chatBtn.setBackgroundResource(R.drawable.abc_ab_bottom_solid_light_holo);
		moreBtn.setBackgroundResource(R.drawable.abc_ab_bottom_solid_light_holo);
		
		v.setBackgroundColor(Color.DKGRAY);
	}
	
	public void switchButtonBackground(int index) {
		switch (index) {
		case 0:
			switchButtonBackground(idcardBtn);
			break;
		case 1:
			switchButtonBackground(historyBtn);
			break;
		case 2:
			switchButtonBackground(mainBtn);
			break;
		case 3:
			switchButtonBackground(chatBtn);
			break;
		case 4:
			switchButtonBackground(moreBtn);
			break;
		}
	}

	private class Listener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.mainIdCardBtn:
				callback.onIdCardClick();
				break;
			case R.id.mainHistoryBtn:
				callback.onHistoryClick();
				break;
			case R.id.mainBtn:
				callback.onMainClick();
				break;
			case R.id.mainChatBtn:
				callback.onChatClick();
				break;
			case R.id.mainMoreBtn:
				callback.onMoreClick();
				break;
			}
			switchButtonBackground(v);
		}

	}

}

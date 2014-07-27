package kr.co.redstrap.campusting.setting;

import kr.co.redstrap.campusting.R;
import kr.co.redstrap.campusting.common.AbsCTLayout;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.CheckedTextView;
import android.widget.ImageButton;

public class PolishLayout extends AbsCTLayout {

	public interface Callback {
		public void onBackClick();
		// 탭전환은 그냥 여기서 알아서 하면 되므로 따로 콜백 없음
	}
	
	private Callback callback;
	
	public void setCallback(Callback callback) {
		this.callback = callback;
	}
	
	private ImageButton backBtn;
	private CheckedTextView polishTab;
	private CheckedTextView privacyTab;
	private WebView polishWebView;
	
	public PolishLayout(Context ctx) {
		super(ctx);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getRootViewId() {
		// TODO Auto-generated method stub
		return R.layout.activity_setting_polish_privacy;
	}

	@Override
	public void inflateViews() {
		// TODO Auto-generated method stub
		Listener l = new Listener();
		
		backBtn = (ImageButton) findViewById(R.id.polishBackBtn);
		backBtn.setOnClickListener(l);
		polishTab = (CheckedTextView) findViewById(R.id.polishTab);
		polishTab.setOnClickListener(l);
		privacyTab = (CheckedTextView) findViewById(R.id.privacyTab);
		privacyTab.setOnClickListener(l);
		polishWebView = (WebView) findViewById(R.id.policyWebView);
	}
	
	private class Listener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.polishBackBtn:
				callback.onBackClick();
				break;
			case R.id.polishTab:
				break;
			case R.id.privacyTab:
				break;
			}
		}
		
	}

}

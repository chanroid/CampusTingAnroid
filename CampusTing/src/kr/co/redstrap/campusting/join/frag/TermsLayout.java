package kr.co.redstrap.campusting.join.frag;

import kr.co.redstrap.campusting.R;
import kr.co.redstrap.campusting.common.AbsCTLayout;
import android.content.Context;
import android.webkit.WebView;
import android.widget.Button;

public class TermsLayout extends AbsCTLayout {
	
	public interface Callback {
		public void onPolicyClick();
		public void onPrivacyClick();
	}

	private WebView policyWebView;
	private Button policyBtn;
	private WebView privacyWebView;
	private Button privacyBtn;

	public TermsLayout(Context ctx) {
		super(ctx);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getRootViewId() {
		// TODO Auto-generated method stub
		return R.layout.activity_join_terms;
	}

	@Override
	public void inflateViews() {
		// TODO Auto-generated method stub

		policyWebView = (WebView) findViewById(R.id.policyWebView);
		policyBtn = (Button) findViewById(R.id.policyBtn);
		privacyBtn = (Button) findViewById(R.id.privacyBtn);
		privacyWebView = (WebView) findViewById(R.id.privacyWebView);
	}

}

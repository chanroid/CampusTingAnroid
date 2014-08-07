package kr.co.redstrap.campusting.join.frag;

import kr.co.redstrap.campusting.R;
import kr.co.redstrap.campusting.common.AbsCTLayout;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class TermsLayout extends AbsCTLayout {
	
	public interface Callback {
		public void onPolicyClick();
		public void onPrivacyClick();
	}
	
	private Callback callback;
	
	public void setCallback(Callback callback) {
		this.callback = callback;
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
		
		Listener listener = new Listener();

		policyWebView = (WebView) findViewById(R.id.policyWebView);
		policyBtn = (Button) findViewById(R.id.policyBtn);
		policyBtn.setOnClickListener(listener);
		privacyBtn = (Button) findViewById(R.id.privacyBtn);
		privacyBtn.setOnClickListener(listener);
		privacyWebView = (WebView) findViewById(R.id.privacyWebView);
		
		WebViewClient client = new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				view.loadUrl(url);
				return true;
			}
		};
		
		policyWebView.setWebViewClient(client);
		privacyWebView.setWebViewClient(client);
		
		policyWebView.loadUrl("http://www.naver.com/");
		privacyWebView.loadUrl("http://www.dcinside.com/");
	}
	
	private class Listener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.policyBtn:
				callback.onPolicyClick();
				policyBtn.setEnabled(false);
				break;
			case R.id.privacyBtn:
				callback.onPrivacyClick();
				privacyBtn.setEnabled(false);
				break;
			}
		}
		
	}

}

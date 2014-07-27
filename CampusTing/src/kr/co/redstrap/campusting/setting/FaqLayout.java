package kr.co.redstrap.campusting.setting;

import kr.co.redstrap.campusting.R;
import kr.co.redstrap.campusting.common.AbsCTLayout;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.ImageButton;

public class FaqLayout extends AbsCTLayout {
	
	public interface Callback {
		public void onBackClick();
	}
	
	private Callback callback;
	
	public void setCallback(Callback callback) {
		this.callback = callback;
	}

	private ImageButton backBtn;
	private WebView webView;
	
	public FaqLayout(Context ctx) {
		super(ctx);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getRootViewId() {
		// TODO Auto-generated method stub
		return R.layout.activity_faq;
	}

	@Override
	public void inflateViews() {
		// TODO Auto-generated method stub
		
		Listener listener = new Listener();

		backBtn = (ImageButton) findViewById(R.id.faqBackBtn);
		backBtn.setOnClickListener(listener);
		webView = (WebView) findViewById(R.id.faqwebView);
		
	}
	
	private class Listener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.faqBackBtn:
				callback.onBackClick();
				break;
			}
		}
		
	}

}

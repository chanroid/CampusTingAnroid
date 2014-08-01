package kr.co.redstrap.campusting.main;

import kr.co.redstrap.campusting.R;
import kr.co.redstrap.campusting.common.AbsCTLayout;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class NoticeLayout extends AbsCTLayout {
	
	public interface Callback {
		public void onBackClick();
	}
	
	private Callback callback;
	
	private ImageButton backBtn;
	
	public void setCallback(Callback callback) {
		this.callback = callback;
	}
	
	public NoticeLayout(Context ctx) {
		super(ctx);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getRootViewId() {
		// TODO Auto-generated method stub
		return R.layout.activity_notice;
	}

	@Override
	public void inflateViews() {
		// TODO Auto-generated method stub
		
		Listener l = new Listener();
		
		backBtn = (ImageButton) findViewById(R.id.noticeBackBtn);
		backBtn.setOnClickListener(l);
	}
	
	private class Listener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			switch (v.getId()) {
			case R.id.noticeBackBtn:
				callback.onBackClick();
				break;
			}
			
		}
		
	}
	
}

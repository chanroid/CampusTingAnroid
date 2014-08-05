package kr.co.redstrap.campusting.join;

import kr.co.redstrap.campusting.R;
import kr.co.redstrap.campusting.common.AbsCTLayout;
import kr.co.redstrap.campusting.util.view.UntouchableViewPager;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class JoinLayout extends AbsCTLayout {

	public interface Callback {
		public void onBackClick();
		public void onTermsClick();
		public void onMyInfoClick();
		public void onProfileClick();
		public void onPhotoClick();
		public void onJobUnivClick();
	}
	
	private Callback callback;
	
	private UntouchableViewPager pager;
	
	private ImageButton backBtn;
	
	private LinearLayout termsBtn;
	private LinearLayout myinfoBtn;
	private LinearLayout profileBtn;
	private LinearLayout photoBtn;
	private LinearLayout jobunivBtn;
	
	public JoinLayout(Context ctx, FragmentPagerAdapter adapter) {
		super(ctx);

		// 어댑터를 바로 등록해 줘야 하므로 생성자에서 세팅
		pager = (UntouchableViewPager) findViewById(R.id.pager);
		pager.setOffscreenPageLimit(4);
		pager.setPagingEnabled(false);
		pager.setAdapter(adapter);

		// TODO Auto-generated constructor stub
	}
	
	public void setCallback(Callback callback) {
		this.callback = callback;
	}

	@Override
	public int getRootViewId() {
		// TODO Auto-generated method stub
		return R.layout.activity_join;
	}

	@Override
	public void inflateViews() {
		// TODO Auto-generated method stub
		
		Listener listener = new Listener();
		
		backBtn = (ImageButton) findViewById(R.id.joinBackBtn);
		backBtn.setOnClickListener(listener);
		
		termsBtn = (LinearLayout) findViewById(R.id.joinIndicator1);
		termsBtn.setOnClickListener(listener);
		myinfoBtn = (LinearLayout) findViewById(R.id.joinIndicator2);
		myinfoBtn.setOnClickListener(listener);
		profileBtn = (LinearLayout) findViewById(R.id.joinIndicator3);
		profileBtn.setOnClickListener(listener);
		photoBtn = (LinearLayout) findViewById(R.id.joinIndicator4);
		photoBtn.setOnClickListener(listener);
		jobunivBtn = (LinearLayout) findViewById(R.id.joinIndicator5);
		jobunivBtn.setOnClickListener(listener);
		
		highlightIndicatorButton(termsBtn);
		
	}
	
	public void setCampustingJoin(boolean flag) {
		if (!flag) {
			myinfoBtn.setVisibility(View.GONE);
		}
	}
	
	private void highlightIndicatorButton(View highlightView) {
		termsBtn.setBackgroundColor(Color.WHITE);
		myinfoBtn.setBackgroundColor(Color.WHITE);
		profileBtn.setBackgroundColor(Color.WHITE);
		photoBtn.setBackgroundColor(Color.WHITE);
		jobunivBtn.setBackgroundColor(Color.WHITE);

		highlightView.setBackgroundColor(Color.LTGRAY);
	}
	
	public void setCurrentPage(int page) {
		pager.setCurrentItem(page, true);
	}
	
	private class Listener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (v != backBtn)
				highlightIndicatorButton(v);
			
			switch (v.getId()) {
			case R.id.joinBackBtn:
				callback.onBackClick();
				break;
			case R.id.joinIndicator1:
				callback.onTermsClick();
				break;
			case R.id.joinIndicator2:
				callback.onMyInfoClick();
				break;
			case R.id.joinIndicator3:
				callback.onProfileClick();
				break;
			case R.id.joinIndicator4:
				callback.onPhotoClick();
				break;
			case R.id.joinIndicator5:
				callback.onJobUnivClick();
				break;
			}
		}
		
	}
	
}

package kr.co.redstrap.campusting.setting;

import kr.co.redstrap.campusting.R;
import kr.co.redstrap.campusting.common.AbsCTLayout;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ModifyJobLayout extends AbsCTLayout {

	public interface Callback {
		public void onJobCardImageClick();

		public void onJobConfirmClick();

		public void onBackClick();
	}

	private Drawable expandedArrow;
	private Drawable collapsedArrow;

	private ImageButton backBtn;
	
	// 직업인증 부분
	private LinearLayout jobListBtn;
	private ImageView joblistBtnArrow;
	private LinearLayout jobLayout;
	private ImageButton jobCardImage;
	private Button confirmJobBtn;

	private Button confirmBtn;

	private Callback callback;
	
	public void setCallback(Callback callback) {
		this.callback = callback;
	}

	public ModifyJobLayout(Context ctx) {
		super(ctx);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getRootViewId() {
		// TODO Auto-generated method stub
		return R.layout.activity_modify_job;
	}

	@Override
	public void inflateViews() {
		// TODO Auto-generated method stub
		
		expandedArrow = getContext().getResources().getDrawable(
				R.drawable.com_facebook_tooltip_blue_topnub);
		collapsedArrow = getContext().getResources().getDrawable(
				R.drawable.com_facebook_tooltip_blue_bottomnub);

		Listener listener = new Listener();
		backBtn = (ImageButton) findViewById(R.id.modifyJobBackBtn);
		backBtn.setOnClickListener(listener);

		jobListBtn = (LinearLayout) findViewById(R.id.jobConfirmBtnLayout);
		jobListBtn.setOnClickListener(listener);
		joblistBtnArrow = (ImageView) findViewById(R.id.jobConfirmBtnLayoutArrow);

		jobLayout = (LinearLayout) findViewById(R.id.jobConfirmLayout);
		jobCardImage = (ImageButton) findViewById(R.id.jobCardImage);
		jobCardImage.setOnClickListener(listener);

		confirmJobBtn = (Button) findViewById(R.id.confirmJobBtn);
		confirmJobBtn.setOnClickListener(listener);

		confirmBtn = (Button) findViewById(R.id.confirmBtn);
		confirmBtn.setOnClickListener(listener);

		clickJobConfirmLayout();
	}

	private class Listener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.jobCardImage:
				callback.onJobCardImageClick();
				break;
			case R.id.confirmJobBtn:
				callback.onJobConfirmClick();
				break;
			case R.id.jobConfirmBtnLayout:
				clickJobConfirmLayout();
				break;
			case R.id.modifyJobBackBtn:
				callback.onBackClick();
				break;
			}
		}

	}

	private void clickJobConfirmLayout() {
		if (jobLayout.getVisibility() == View.VISIBLE) {
			jobLayout.setVisibility(View.GONE);
			joblistBtnArrow.setImageDrawable(collapsedArrow);
		} else {
			jobLayout.setVisibility(View.VISIBLE);
			joblistBtnArrow.setImageDrawable(expandedArrow);
		}
	}

	public void setJobCardImage(Bitmap jobcardImage2) {
		// TODO Auto-generated method stub
		jobCardImage.setImageBitmap(jobcardImage2);
	}

}

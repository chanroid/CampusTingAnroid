package kr.co.redstrap.campusting.join.frag;

import kr.co.redstrap.campusting.R;
import kr.co.redstrap.campusting.common.AbsCTLayout;
import kr.co.redstrap.campusting.common.SimpleTextWatcher;
import kr.co.redstrap.campusting.util.ViewUtil;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class ConfirmUnivFragLayout extends AbsCTLayout {

	public interface Callback {
		public void onUnivCardImageClick();

		public void onUnivMailChanged(String mail);

		public void onUnivMailCodeRequestClick(); // 인증메일발송

		public void onUnivMailCodeConfirmClick(); // 인증번호입력

		public void onJobCardImageClick();

		public void onConfirmClick();

		public void onUnivMailConfirmClick();

		public void onUnivCardConfirmClick();

		public void onJobConfirmClick();

		public void onUnivStateChanged(int position);

		public void onUnivChanged(int num);
	}

	private Drawable expandedArrow;
	private Drawable collapsedArrow;

	// 뷰
	private LinearLayout univListBtn;
	private ImageView univListBtnArrow;

	// 대학인증 이메일인증 부분
	private LinearLayout univLayout;
	private RadioGroup univStateSpinner;
	private Button univConfirmBtn;

	private AutoCompleteTextView univAutoTextView;

	private EditText univMailTextView;
	private Button univMailSendBtn;

	private EditText univMailCodeEditText;
	private Button univMailCodeBtn;

	// 대학인증 사진활영 부분
	private LinearLayout univCardBtnLayout;
	private ImageView univCardBtnArrow;
	private LinearLayout univCardLayout;
	private RadioGroup univCardStateSpinner;
	private ImageButton univCardImage;
	private Button univCardConfirmBtn;

	// 직업인증 부분
	private LinearLayout jobListBtn;
	private ImageView joblistBtnArrow;
	private LinearLayout jobLayout;
	private ImageButton jobCardImage;
	private Button confirmJobBtn;

	private Button confirmBtn;

	private Callback callback;

	public ConfirmUnivFragLayout(Context ctx) {
		super(ctx);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getRootViewId() {
		// TODO Auto-generated method stub
		return R.layout.activity_join_confirm_univ;
	}

	public void setCallback(Callback callback) {
		// TODO Auto-generated method stub
		this.callback = callback;
	}

	@Override
	public void inflateViews() {
		// TODO Auto-generated method stub

		expandedArrow = getContext().getResources().getDrawable(
				R.drawable.com_facebook_tooltip_blue_topnub);
		collapsedArrow = getContext().getResources().getDrawable(
				R.drawable.com_facebook_tooltip_blue_bottomnub);

		Listener listener = new Listener();

		univListBtn = (LinearLayout) findViewById(R.id.univConfirmBtnLayout);
		univListBtn.setOnClickListener(listener);
		univListBtnArrow = (ImageView) findViewById(R.id.univConfirmBtnLayoutArrow);

		univStateSpinner = (RadioGroup) findViewById(R.id.univStateSpinner);
		univStateSpinner.setOnCheckedChangeListener(listener);

		univLayout = (LinearLayout) findViewById(R.id.univLayout);
		univCardImage = (ImageButton) findViewById(R.id.univCardImage);
		univCardImage.setOnClickListener(listener);

		univAutoTextView = (AutoCompleteTextView) findViewById(R.id.univAutoEditText);
		// 이거는 어댑터만 지정해도 된다고 함

		univMailTextView = (EditText) findViewById(R.id.univMailEditText);
		univMailTextView.addTextChangedListener(new SimpleTextWatcher() {
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if (ViewUtil.isTypeEmail(s.toString())) {
					ViewUtil.setGood(univMailTextView);
					callback.onUnivMailChanged(s.toString());
				} else {
					ViewUtil.setBad(univMailTextView);
				}
			}
		});
		univMailSendBtn = (Button) findViewById(R.id.univMailSendBtn);
		univMailSendBtn.setOnClickListener(listener);

		univMailCodeEditText = (EditText) findViewById(R.id.univMailCodeEditText);
		univMailCodeBtn = (Button) findViewById(R.id.univMailCodeBtn);
		univMailCodeBtn.setOnClickListener(listener);

		univConfirmBtn = (Button) findViewById(R.id.univMailConfirmBtn);
		univConfirmBtn.setOnClickListener(listener);

		univCardBtnLayout = (LinearLayout) findViewById(R.id.univConfirmPhotoBtnLayout);
		univCardBtnLayout.setOnClickListener(listener);
		univCardBtnArrow = (ImageView) findViewById(R.id.univConfirmPhotoBtnLayoutArrow);
		univCardLayout = (LinearLayout) findViewById(R.id.univConfirmPhotoLayout);
		univCardStateSpinner = (RadioGroup) findViewById(R.id.univPhotoStateSpinner);
		univCardStateSpinner.setOnCheckedChangeListener(listener);
		univCardImage = (ImageButton) findViewById(R.id.univCardImage);
		univCardImage.setOnClickListener(listener);
		univCardConfirmBtn = (Button) findViewById(R.id.univCardImageConfirmBtn);
		univCardConfirmBtn.setOnClickListener(listener);

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

		clickUnivConfirmLayout();
	}

	private void clickUnivConfirmLayout() {
		if (univLayout.getVisibility() == View.VISIBLE) {
			univLayout.setVisibility(View.GONE);
			univListBtnArrow.setImageDrawable(collapsedArrow);
		} else {
			univLayout.setVisibility(View.VISIBLE);
			univListBtnArrow.setImageDrawable(expandedArrow);
		}

		jobLayout.setVisibility(View.GONE);
		joblistBtnArrow.setImageDrawable(collapsedArrow);
		univCardLayout.setVisibility(View.GONE);
		univCardBtnArrow.setImageDrawable(collapsedArrow);
	}

	private void clickUnivPhotoConfirmLayout() {
		if (univCardLayout.getVisibility() == View.VISIBLE) {
			univCardLayout.setVisibility(View.GONE);
			univCardBtnArrow.setImageDrawable(collapsedArrow);
		} else {
			univCardLayout.setVisibility(View.VISIBLE);
			univCardBtnArrow.setImageDrawable(expandedArrow);
		}

		jobLayout.setVisibility(View.GONE);
		joblistBtnArrow.setImageDrawable(collapsedArrow);
		univLayout.setVisibility(View.GONE);
		univListBtnArrow.setImageDrawable(collapsedArrow);
	}

	private void clickJobConfirmLayout() {
		if (jobLayout.getVisibility() == View.VISIBLE) {
			jobLayout.setVisibility(View.GONE);
			joblistBtnArrow.setImageDrawable(collapsedArrow);
		} else {
			jobLayout.setVisibility(View.VISIBLE);
			joblistBtnArrow.setImageDrawable(expandedArrow);
		}

		univLayout.setVisibility(View.GONE);
		univListBtnArrow.setImageDrawable(collapsedArrow);
	}

	private class Listener implements OnClickListener, OnItemSelectedListener,
			OnCheckedChangeListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.univConfirmBtnLayout:
				clickUnivConfirmLayout();
				break;
			case R.id.univMailSendBtn:
				callback.onUnivMailCodeRequestClick();
				break;
			case R.id.univMailCodeBtn:
				callback.onUnivMailCodeConfirmClick();
				break;
			case R.id.univCardImage:
				callback.onUnivCardImageClick();
				break;
			case R.id.jobConfirmBtnLayout:
				clickJobConfirmLayout();
				break;
			case R.id.univConfirmPhotoBtnLayout:
				clickUnivPhotoConfirmLayout();
				break;
			case R.id.jobCardImage:
				callback.onJobCardImageClick();
				break;
			case R.id.univCardImageConfirmBtn:
				callback.onUnivCardConfirmClick();
				break;
			case R.id.confirmJobBtn:
				callback.onJobConfirmClick();
				break;
			case R.id.univMailConfirmBtn:
				callback.onUnivMailConfirmClick();
				break;
			case R.id.confirmBtn:
				callback.onConfirmClick();
				break;
			}
		}

		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			// TODO Auto-generated method stub
			switch (parent.getId()) {
			case R.id.univStateSpinner:
				callback.onUnivStateChanged(position);
				break;
			case R.id.univPhotoStateSpinner:
				callback.onUnivStateChanged(position);
				break;
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub

		}

	}

}

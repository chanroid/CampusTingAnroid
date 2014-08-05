package kr.co.redstrap.campusting.join.frag;

import kr.co.redstrap.campusting.R;
import kr.co.redstrap.campusting.common.AbsCTLayout;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class ConfirmUnivFragLayout extends AbsCTLayout {
	
	public interface Callback {
		public void onUnivCardImageClick();
		
		public void onUnivNameChanged(String name);
		
		public void onUnivMailChanged(String mail);
		
		public void onUnivMailCodeRequestClick(); // 인증메일발송
		public void onUnivMailCodeConfirmClick(); // 인증번호입력
		
		public void onJobCardImageClick();
		
		public void onConfirmClick();
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
	private CheckedTextView univAutoCheck;

	private EditText univMailTextView;
	private CheckedTextView univMailCheck;
	private Button univMailSendBtn;

	private EditText univMailCodeEditText;
	private CheckedTextView univMailCodeCheck;
	private Button univMailCodeBtn;

	// 대학인증 사진활영 부분
	private LinearLayout univCardBtnLayout;
	private ImageButton univCardBtnArrow;
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
		
//		expandedArrow = getContext().getResources().getDrawable(R.drawable.com_facebook_tooltip_blue_topnub);
//		collapsedArrow = getContext().getResources().getDrawable(R.drawable.com_facebook_tooltip_blue_bottomnub);
		
		
		Listener listener = new Listener();
		
		univListBtn = (LinearLayout) findViewById(R.id.univConfirmBtnLayout);
		univListBtn.setOnClickListener(listener);
		univListBtnArrow = (ImageView) findViewById(R.id.univConfirmBtnLayoutArrow);
		
		univStateSpinner = (RadioGroup) findViewById(R.id.univStateSpinner);
		univStateSpinner.setOnCheckedChangeListener(listener);

		univLayout = (LinearLayout) findViewById(
				R.id.univLayout);
		univCardImage = (ImageButton) findViewById(R.id.univCardImage);
		univCardImage.setOnClickListener(listener);

		univAutoTextView = (AutoCompleteTextView) findViewById(R.id.univAutoEditText);
		// 이거는 어댑터만 지정해도 된다고 함
		univAutoCheck = (CheckedTextView) findViewById(R.id.univAutoCheck);

		univMailTextView = (EditText) findViewById(R.id.univMailEditText);
		univMailTextView.addTextChangedListener(listener);
		univMailCheck = (CheckedTextView) findViewById(R.id.univMailCheck);
		univMailSendBtn = (Button) findViewById(R.id.univMailSendBtn);
		univMailSendBtn.setOnClickListener(listener);

		univMailCodeEditText = (EditText) findViewById(R.id.univMailCodeEditText);
		univMailCodeCheck = (CheckedTextView) findViewById(R.id.univMailCodeCheck);
		univMailCodeBtn = (Button) findViewById(R.id.univMailCodeBtn);
		univMailCodeBtn.setOnClickListener(listener);
		
		
		jobListBtn = (LinearLayout) findViewById(R.id.jobConfirmBtnLayout);
		jobListBtn.setOnClickListener(listener);
		joblistBtnArrow = (ImageView) findViewById(R.id.jobConfirmBtnLayoutArrow);
		
		jobLayout = (LinearLayout) findViewById(R.id.jobConfirmLayout);
		jobCardImage = (ImageButton) findViewById(R.id.jobCardImage);
		jobCardImage.setOnClickListener(listener);
		
		confirmJobBtn = (Button) findViewById(R.id.confirmJobBtn);
		confirmJobBtn.setOnClickListener(listener);
		
		
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
	}
	
	private void clickUnivPhotoConfirmLayout() {
		
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
	
	private class Listener implements OnClickListener, OnItemSelectedListener, TextWatcher, OnCheckedChangeListener {

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			
		}

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
			case R.id.jobCardImage:
				callback.onJobCardImageClick();
				break;
			case R.id.confirmJobBtn:
				callback.onConfirmClick();
				break;
			}
		}

		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			// TODO Auto-generated method stub
			
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

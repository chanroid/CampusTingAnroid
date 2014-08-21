package kr.co.redstrap.campusting.setting;

import kr.co.redstrap.campusting.R;
import kr.co.redstrap.campusting.common.AbsCTLayout;
import kr.co.redstrap.campusting.common.SimpleTextWatcher;
import kr.co.redstrap.campusting.util.ViewUtil;
import android.content.Context;
import android.graphics.Bitmap;
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

public class ModifyUnivLayout extends AbsCTLayout {

	public interface Callback {
		public void onUnivCardImageClick();

		public void onUnivMailChanged(String mail);

		public void onUnivMailCodeRequestClick(); // 인증메일발송

		public void onUnivMailCodeConfirmClick(); // 인증번호입력

		public void onUnivMailConfirmClick();

		public void onUnivCardConfirmClick();

		public void onUnivStateChanged(int position);

		public void onUnivChanged(int num);

		public void onUnivMailCodeChanged(String string);
		
		public void onConfirmClick();

		public void onBackClick();
	}
	
	private ImageButton backBtn;
	
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

	private Button confirmBtn;

	private Callback callback;
	
	public void setCallback(Callback callback) {
		this.callback = callback;
	}
	
	public ModifyUnivLayout(Context ctx) {
		super(ctx);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getRootViewId() {
		// TODO Auto-generated method stub
		return R.layout.activity_modify_univ;
	}

	@Override
	public void inflateViews() {
		// TODO Auto-generated method stub

		expandedArrow = getContext().getResources().getDrawable(
				R.drawable.com_facebook_tooltip_blue_topnub);
		collapsedArrow = getContext().getResources().getDrawable(
				R.drawable.com_facebook_tooltip_blue_bottomnub);

		Listener listener = new Listener();
		
		backBtn = (ImageButton) findViewById(R.id.modifyUnivBackBtn);
		backBtn.setOnClickListener(listener);

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
		univMailCodeEditText.addTextChangedListener(new SimpleTextWatcher() {
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				callback.onUnivMailCodeChanged(s.toString());
			}
		});
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

		univLayout.setVisibility(View.GONE);
		univListBtnArrow.setImageDrawable(collapsedArrow);
	}

	public void setConfirmedUnivMail(boolean confirmed) {
		univAutoTextView.setEnabled(!confirmed);
		univMailTextView.setEnabled(!confirmed);
		univMailCodeBtn.setEnabled(!confirmed);
		univMailCodeEditText.setEnabled(!confirmed);
		univMailSendBtn.setEnabled(!confirmed);
		univStateSpinner.setEnabled(!confirmed);
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
			case R.id.univConfirmPhotoBtnLayout:
				clickUnivPhotoConfirmLayout();
				break;
			case R.id.univMailCodeBtn:
				callback.onUnivMailCodeConfirmClick();
				break;
			case R.id.univCardImage:
				callback.onUnivCardImageClick();
				break;
			case R.id.univCardImageConfirmBtn:
				callback.onUnivCardConfirmClick();
				break;
			case R.id.univMailConfirmBtn:
				callback.onUnivMailConfirmClick();
				break;
			case R.id.confirmBtn:
				callback.onConfirmClick();
				break;
			case R.id.modifyUnivBackBtn:
				callback.onBackClick();
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

	public void setUnivCardImage(Bitmap univImage) {
		// TODO Auto-generated method stub
		univCardImage.setImageBitmap(univImage);
	}

}

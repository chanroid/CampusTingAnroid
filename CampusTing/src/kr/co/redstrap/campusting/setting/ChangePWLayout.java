package kr.co.redstrap.campusting.setting;

import kr.co.redstrap.campusting.R;
import kr.co.redstrap.campusting.common.AbsCTLayout;
import kr.co.redstrap.campusting.common.LoginInfo;
import kr.co.redstrap.campusting.common.SimpleTextWatcher;
import kr.co.redstrap.campusting.util.SHA256;
import kr.co.redstrap.campusting.util.ViewUtil;
import android.content.Context;
import android.text.Editable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class ChangePWLayout extends AbsCTLayout {
	
	public interface Callback {
		public void onBackClick();
		public void onChangePWClick();
	}
	
	private ImageButton backBtn;
	private EditText currentpwEdit;
	private EditText changepwEdit;
	private EditText changepwConfirmEdit;
	private Button changepwConfirmBtn;
	
	private Callback callback;
	
	public void setCallback(Callback callback) {
		this.callback = callback;
	}

	public ChangePWLayout(Context ctx) {
		super(ctx);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getRootViewId() {
		// TODO Auto-generated method stub
		return R.layout.activity_password_change;
	}
	
	public String getCurrentPW() {
		return currentpwEdit.getText().toString();
	}
	
	public String getChangePW() {
		if (changepwEdit.getText().toString().equals(changepwConfirmEdit.getText().toString()))
			return changepwEdit.getText().toString();
		else
			return null;
	}

	@Override
	public void inflateViews() {
		// TODO Auto-generated method stub
		
		Listener l = new Listener();
		
		backBtn = (ImageButton) findViewById(R.id.changepwBackBtn);
		backBtn.setOnClickListener(l);
		currentpwEdit = (EditText) findViewById(R.id.changepwCurrentpwEdit);
		currentpwEdit.addTextChangedListener(new SimpleTextWatcher() {
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				super.afterTextChanged(s);
				String encodedPW = SHA256.getCipherText(LoginInfo.getInstance(getContext()).getUserPw());
				if (encodedPW.equals(s.toString())) {
					ViewUtil.setGood(currentpwEdit);
				} else
					ViewUtil.setBad(currentpwEdit);
			}
		});
		changepwEdit = (EditText) findViewById(R.id.changepwChangepwEdit);
		changepwEdit.addTextChangedListener(new SimpleTextWatcher() {
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				super.afterTextChanged(s);
				if (s.length() > 5)
					ViewUtil.setGood(changepwEdit);
				else
					ViewUtil.setBad(changepwEdit);
			}
		});
		changepwConfirmEdit = (EditText) findViewById(R.id.changepwChangepwConfirmEdit);
		changepwConfirmEdit.addTextChangedListener(new SimpleTextWatcher() {
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				super.afterTextChanged(s);
				if (changepwEdit.getText().toString().equals(s.toString()))
					ViewUtil.setGood(changepwConfirmEdit);
				else
					ViewUtil.setBad(changepwConfirmEdit);
			}
		});
		changepwConfirmBtn = (Button) findViewById(R.id.changepwConfirmBtn);
		changepwConfirmBtn.setOnClickListener(l);

	}

	private class Listener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.changepwBackBtn:
				callback.onBackClick();
				break;
			case R.id.changepwConfirmBtn:
				callback.onChangePWClick();
				break;
			}
		}
		
	}
	
}

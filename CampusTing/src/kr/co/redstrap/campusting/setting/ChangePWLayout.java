package kr.co.redstrap.campusting.setting;

import kr.co.redstrap.campusting.R;
import kr.co.redstrap.campusting.common.AbsCTLayout;
import android.content.Context;
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

	@Override
	public void inflateViews() {
		// TODO Auto-generated method stub
		
		Listener l = new Listener();
		
		backBtn = (ImageButton) findViewById(R.id.changepwBackBtn);
		backBtn.setOnClickListener(l);
		currentpwEdit = (EditText) findViewById(R.id.changepwCurrentpwEdit);
		changepwEdit = (EditText) findViewById(R.id.changepwChangepwEdit);
		changepwConfirmEdit = (EditText) findViewById(R.id.changepwChangepwConfirmEdit);
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

package kr.co.redstrap.campusting.main;

import kr.co.redstrap.campusting.R;
import kr.co.redstrap.campusting.common.AbsCTLayout;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class QALayout extends AbsCTLayout {
	
	public interface Callback {
		public void onBackClick();
		public void onConfirmClick();
	}
	
	private Callback callback;
	
	private ImageButton backBtn;
	private EditText qaEdit;
	private Button confirmBtn;
	
	public void setCallback(Callback callback) {
		this.callback = callback;
	}
	
	public String getQADescText() {
		return qaEdit.getText().toString();
	}

	public QALayout(Context ctx) {
		super(ctx);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getRootViewId() {
		// TODO Auto-generated method stub
		return R.layout.activity_qa;
	}

	@Override
	public void inflateViews() {
		// TODO Auto-generated method stub

		Listener l = new Listener();
		
		backBtn = (ImageButton) findViewById(R.id.qaBackBtn);
		backBtn.setOnClickListener(l);
		qaEdit = (EditText) findViewById(R.id.qaEditText);
		confirmBtn = (Button) findViewById(R.id.qaConfirmBtn);
		confirmBtn.setOnClickListener(l);
	}
	
	private class Listener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.qaBackBtn:
				callback.onBackClick();
				break;
			case R.id.qaConfirmBtn:
				callback.onConfirmClick();
				break;
			}
		}
		
	}

}

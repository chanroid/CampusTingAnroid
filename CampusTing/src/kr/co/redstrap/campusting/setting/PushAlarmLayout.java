package kr.co.redstrap.campusting.setting;

import kr.co.redstrap.campusting.R;
import kr.co.redstrap.campusting.common.AbsCTLayout;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PushAlarmLayout extends AbsCTLayout {

	public interface Callback {
		public void onBackClick();
		public void onAlarmsetClick();
		public void onAlarmAM9Click();
		public void onAlarmPM7Click();
		public void onReceiveTingClick();
		public void onMessageClick();
		public void onEventClick();
		public void onConfirmClick();
	}
	
	private Callback callback;
	
	public void setCallback(Callback callback) {
		this.callback = callback;
	}
	
	private ImageButton backBtn;
	private RelativeLayout alarmSetBtn;
	private RelativeLayout alarmAM9Btn;
	private RelativeLayout alarmPM7Btn;
	private RelativeLayout alarmReceiveTingBtn;
	private RelativeLayout alarmMessageBtn;
	private RelativeLayout alarmEventBtn;
	private Button alarmConfirmBtn;
	
	private TextView alarmSetText;
	private TextView alarmAM9Text;
	private TextView alarmPM7Text;
	private TextView alarmReceiveTingText;
	private TextView alarmMessageText;
	private TextView alarmEventText;
	
	public PushAlarmLayout(Context ctx) {
		super(ctx);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getRootViewId() {
		// TODO Auto-generated method stub
		return R.layout.activity_pushalarm;
	}

	@Override
	public void inflateViews() {
		// TODO Auto-generated method stub
		
		Listener l = new Listener();

		backBtn = (ImageButton) findViewById(R.id.alarmBackBtn);
		backBtn.setOnClickListener(l);
		alarmSetBtn = (RelativeLayout) findViewById(R.id.alarmSetBtn);
		alarmSetBtn.setOnClickListener(l);
		alarmAM9Btn = (RelativeLayout) findViewById(R.id.alarmAM9Btn);
		alarmAM9Btn.setOnClickListener(l);
		alarmPM7Btn	= (RelativeLayout) findViewById(R.id.alarmPM7Btn);
		alarmPM7Btn.setOnClickListener(l);
		alarmReceiveTingBtn = (RelativeLayout) findViewById(R.id.alarmReceiveTingBtn);
		alarmReceiveTingBtn.setOnClickListener(l);
		alarmMessageBtn = (RelativeLayout) findViewById(R.id.alarmReceiveMessageBtn);
		alarmMessageBtn.setOnClickListener(l);
		alarmEventBtn = (RelativeLayout) findViewById(R.id.alarmEventBtn);
		alarmEventBtn.setOnClickListener(l);
		alarmConfirmBtn = (Button) findViewById(R.id.alarmSettingConfirmBtn);
		alarmConfirmBtn.setOnClickListener(l);
		
		alarmSetText = (TextView) findViewById(R.id.alarmUseAlarmText);
		alarmAM9Text = (TextView) findViewById(R.id.alarmAM9Text);
		alarmPM7Text = (TextView) findViewById(R.id.alarmPM9Text);
		alarmReceiveTingText = (TextView) findViewById(R.id.alarmReceiveTingText);
		alarmMessageText = (TextView) findViewById(R.id.alarmReceiveMessageText);
		
	}
	
	private class Listener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.alarmBackBtn:
				callback.onBackClick();
				break;
			case R.id.alarmSetBtn:
				callback.onAlarmsetClick();
				break;
			case R.id.alarmAM9Btn:
				callback.onAlarmAM9Click();
				break;
			case R.id.alarmPM7Btn:
				callback.onAlarmPM7Click();
				break;
			case R.id.alarmReceiveTingBtn:
				callback.onReceiveTingClick();
				break;
			case R.id.alarmReceiveMessageBtn:
				callback.onMessageClick();
				break;
			case R.id.alarmEventBtn:
				callback.onEventClick();
				break;
			case R.id.alarmSettingConfirmBtn:
				callback.onConfirmClick();
				break;
			}
		}
		
	}

}

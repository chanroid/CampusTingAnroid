package kr.co.redstrap.campusting.main;

import kr.co.redstrap.campusting.R;
import kr.co.redstrap.campusting.common.AbsCTLayout;
import kr.co.redstrap.campusting.util.view.BadgeButton;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainFragmentLayout extends AbsCTLayout {

	public interface Callback {
		public void onAlertListClick();

		public void onShopClick();

		public void onMainBuildingClick();

		public void onGymClick();

		public void onCafeClick();

		public void onLibraryClick();

		public void onDomirotiesClick();

		public void onCircleRoomClick();

		public void onHofClick();

		public void onClubClick();

	}

	private Callback callback;

	private BadgeButton alertListBtn;
	private BadgeButton shopBtn;
	private TextView clockText;

	// 20140723 chanroid 아마도 레이아웃이 될것 같지만 편의성을 위해 일단 이미지버튼으로 지정
	// 낮
	private RelativeLayout dayBuildingLayout;
	private ImageButton mainBuildingBtn;
	private ImageButton gymBtn;
	private ImageButton cafeBtn;
	private ImageButton libraryBtn;
	// 밤 (아직 안만듬)
	private RelativeLayout nightBuildingLayout;
	private ImageButton dormitoriesBtn;
	private ImageButton circleroomBtn;
	private ImageButton hofBtn;
	private ImageButton clubBtn;
	
	public void setCallback(Callback callback) {
		this.callback = callback;
	}

	public MainFragmentLayout(Context ctx) {
		super(ctx);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getRootViewId() {
		// TODO Auto-generated method stub
		return R.layout.fragment_main;
	}

	@Override
	public void inflateViews() {
		// TODO Auto-generated method stub

		Listener listener = new Listener();

		alertListBtn = (BadgeButton) findViewById(R.id.mainAlertBtn);
		alertListBtn.setOnClickListener(listener);
		shopBtn = (BadgeButton) findViewById(R.id.mainShopBtn);
		shopBtn.setOnClickListener(listener);
		clockText = (TextView) findViewById(R.id.mainClockText);

		dayBuildingLayout = (RelativeLayout) findViewById(R.id.mainDayLayout);
		mainBuildingBtn = (ImageButton) findViewById(R.id.mainBuildingBtn);
		mainBuildingBtn.setOnClickListener(listener);
		gymBtn = (ImageButton) findViewById(R.id.gymBtn);
		gymBtn.setOnClickListener(listener);
		cafeBtn = (ImageButton) findViewById(R.id.cafeBtn);
		cafeBtn.setOnClickListener(listener);
		libraryBtn = (ImageButton) findViewById(R.id.libraryBtn);
		libraryBtn.setOnClickListener(listener);

		// 20140723 chanroid 밤껀 만들고 나서 선언
		nightBuildingLayout = (RelativeLayout) findViewById(R.id.mainNightLayout);
		dormitoriesBtn = (ImageButton) findViewById(R.id.domitoryBtn);
		dormitoriesBtn.setOnClickListener(listener);
		circleroomBtn = (ImageButton) findViewById(R.id.circleRoomBtn);
		circleroomBtn.setOnClickListener(listener);
		hofBtn = (ImageButton) findViewById(R.id.hofBtn);
		hofBtn.setOnClickListener(listener);
		clubBtn = (ImageButton) findViewById(R.id.clubBtn);
		clubBtn.setOnClickListener(listener);

	}

	private class Listener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.mainAlertBtn:
				callback.onAlertListClick();
				break;
			case R.id.mainShopBtn:
				callback.onShopClick();
				break;
			case R.id.mainBuildingBtn:
				callback.onMainBuildingClick();
				break;
			case R.id.gymBtn:
				callback.onGymClick();
				break;
			case R.id.libraryBtn:
				callback.onLibraryClick();
				break;
			case R.id.cafeBtn:
				callback.onCafeClick();
				break;
			case R.id.domitoryBtn:
				callback.onDomirotiesClick();
				break;
			case R.id.circleRoomBtn:
				callback.onCircleRoomClick();
				break;
			case R.id.hofBtn:
				callback.onHofClick();
				break;
			case R.id.clubBtn:
				callback.onClubClick();
				break;
			}
		}

	}

}

package kr.co.redstrap.campusting.main;

import kr.co.redstrap.campusting.R;
import kr.co.redstrap.campusting.common.AbsCTLayout;
import kr.co.redstrap.campusting.util.view.BadgeButton;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
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

		public void onDayNightClick();
	}

	private Callback callback;
	
	private HorizontalScrollView daynightPager;

	private BadgeButton alertListBtn;
	private BadgeButton shopBtn;
	private TextView clockText;
	private Button dayNightBtn;

	// 20140723 chanroid 아마도 레이아웃이 될것 같지만 편의성을 위해 일단 이미지버튼으로 지정
	// 낮
	private ImageButton mainBuildingBtn;
	private ImageButton gymBtn;
	private ImageButton cafeBtn;
	private ImageButton libraryBtn;
	
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
		dayNightBtn = (Button) findViewById(R.id.mainDayNightBtn);
		dayNightBtn.setOnClickListener(listener);


		daynightPager = (HorizontalScrollView) findViewById(R.id.mainBuildingPager);
		
		mainBuildingBtn = (ImageButton) daynightPager.findViewById(R.id.mainBuildingBtn);
		mainBuildingBtn.setOnClickListener(listener);
		gymBtn = (ImageButton) daynightPager.findViewById(R.id.gymBtn);
		gymBtn.setOnClickListener(listener);
		cafeBtn = (ImageButton) daynightPager.findViewById(R.id.cafeBtn);
		cafeBtn.setOnClickListener(listener);
		libraryBtn = (ImageButton) daynightPager.findViewById(R.id.libraryBtn);
		libraryBtn.setOnClickListener(listener);

		dormitoriesBtn = (ImageButton) daynightPager.findViewById(R.id.domitoryBtn);
		dormitoriesBtn.setOnClickListener(listener);
		circleroomBtn = (ImageButton) daynightPager.findViewById(R.id.circleRoomBtn);
		circleroomBtn.setOnClickListener(listener);
		hofBtn = (ImageButton) daynightPager.findViewById(R.id.hofBtn);
		hofBtn.setOnClickListener(listener);
		clubBtn = (ImageButton) daynightPager.findViewById(R.id.clubBtn);
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
			case R.id.mainDayNightBtn:
				callback.onDayNightClick();
				break;
			}
		}

	}

}

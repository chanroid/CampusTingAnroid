package kr.co.redstrap.campusting.main;

import kr.co.redstrap.campusting.R;
import kr.co.redstrap.campusting.common.AbsCTLayout;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class IdCardLayout extends AbsCTLayout {
	
	public interface Callback {
		/**
		 * 
		 * @param position 메인은 0, 밑에껀 1부터 시작
		 */
		public void onProfileImageClick(int position);
		public void onModifyProfileClick();
		
		// 탭은 그냥 내부에서 처리
	}

	private Callback callback;
	
	private CheckedTextView idcardTab;
	private CheckedTextView charmingPointTab;
	
	// 학생증 부분
	private LinearLayout idcardLayout;
	private LinearLayout modifyProfileBtn;
	// 학생증 카드 부분
	private ImageView mainImage;
	
	private TextView simpleintroText;
	private TextView nicknameText;
	private TextView simpleinfoText;
	
	private ImageButton profile1Img;
	private ImageButton profile2Img;
	private ImageButton profile3Img;
	private ImageButton profile4Img;
	
	// 프로필 부분
	private TextView profileAboutmeText;
	private TextView profileCharacterText;
	private TextView profileBodyText;
	private TextView profileSchoolText;
	private TextView profileBloodText;
	private TextView profileReligionText;
	private TextView profileDrinksmokeText;
	private TextView profileCoupleCountText;
	
	private TextView profileSimpleIntroDescText;
	private TextView profileFaceText;
	private TextView profileHobbyText;
	private TextView profileFavoriteText;
	
	// 호감도 부분
	private LinearLayout charmingPointLayout;
	
	private TextView faceCharmingPointText;
	private Button faceCharmingPointBtn;
	private TextView profileCharmingPointText;
	private Button profileChramingPointButton;
	
	public void setCallback(Callback callback) {
		this.callback = callback;
	}
	
	public IdCardLayout(Context ctx) {
		super(ctx);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getRootViewId() {
		// TODO Auto-generated method stub
		return R.layout.fragment_idcard;
	}

	@Override
	public void inflateViews() {
		// TODO Auto-generated method stub
		
		Listener listener = new Listener();
		
		idcardTab = (CheckedTextView) findViewById(R.id.idcardTab);
		idcardTab.setOnClickListener(listener);
		charmingPointTab = (CheckedTextView) findViewById(R.id.charmingPointTab);
		charmingPointTab.setOnClickListener(listener);
		
		idcardLayout = (LinearLayout) findViewById(R.id.idcardLayout);

		modifyProfileBtn = (LinearLayout) findViewById(R.id.modifyProfileBtn);
		modifyProfileBtn.setOnClickListener(listener);
		
		mainImage = (ImageView) findViewById(R.id.idcardMainProfileImage);
		mainImage.setOnClickListener(listener);
		
		simpleintroText = (TextView) findViewById(R.id.idcardSimpleIntroText);
		nicknameText = (TextView) findViewById(R.id.idcardNickText);
		simpleinfoText = (TextView) findViewById(R.id.idcardSimpleProfileText);
		
		profile1Img = (ImageButton) findViewById(R.id.idcardProfileImage1);
		profile1Img.setOnClickListener(listener);
		profile2Img = (ImageButton) findViewById(R.id.idcardProfileImage2);
		profile2Img.setOnClickListener(listener);
		profile3Img = (ImageButton) findViewById(R.id.idcardProfileImage3);
		profile3Img.setOnClickListener(listener);
		profile4Img = (ImageButton) findViewById(R.id.idcardProfileImage4);
		profile4Img.setOnClickListener(listener);
		
		profileAboutmeText = (TextView) findViewById(R.id.profileAboutmeText);
		profileCharacterText = (TextView) findViewById(R.id.profileCharacterText);
		profileBodyText = (TextView) findViewById(R.id.profileBodyText);
		profileSchoolText = (TextView) findViewById(R.id.profileSchoolText);
		profileBloodText = (TextView) findViewById(R.id.profileBloodText);
		profileReligionText = (TextView) findViewById(R.id.profileReligionText);
		profileDrinksmokeText = (TextView) findViewById(R.id.profileDrinksmokeText);
		profileCoupleCountText = (TextView) findViewById(R.id.profileCoupleText);
		profileSimpleIntroDescText = (TextView) findViewById(R.id.profileSimpleIntroDescText);
		
		charmingPointLayout = (LinearLayout) findViewById(R.id.charmingPointLayout);
		
		
	}
	
	private class Listener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.idcardTab:
				idcardLayout.setVisibility(View.VISIBLE);
				charmingPointLayout.setVisibility(View.GONE);
				
				idcardTab.setBackgroundColor(Color.parseColor("#7ed9eb"));
				charmingPointTab.setBackgroundColor(Color.WHITE);
				break;
			case R.id.charmingPointTab:
				charmingPointLayout.setVisibility(View.VISIBLE);
				idcardLayout.setVisibility(View.GONE);
				
				idcardTab.setBackgroundColor(Color.WHITE);
				charmingPointTab.setBackgroundColor(Color.parseColor("#7ed9eb"));
				break;
			case R.id.modifyProfileBtn:
				callback.onModifyProfileClick();
				break;
			case R.id.idcardMainProfileImage:
				callback.onProfileImageClick(0);
				break;
			case R.id.idcardProfileImage1:
				callback.onProfileImageClick(1);
				break;
			case R.id.idcardProfileImage2:
				callback.onProfileImageClick(2);
				break;
			case R.id.idcardProfileImage3:
				callback.onProfileImageClick(3);
				break;
			case R.id.idcardProfileImage4:
				callback.onProfileImageClick(4);
				break;
			}
		}
		
	}

}

package kr.co.redstrap.campusting.main;

import kr.co.redstrap.campusting.R;
import kr.co.redstrap.campusting.common.AbsCTLayout;
import kr.co.redstrap.campusting.util.web.LoadImage;
import kr.co.redstrap.campusting.util.web.LoadImage.ImageKind;
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
		public void onMainProfileImageClick();
		/**
		 * 
		 * @param position 메인은 0, 밑에껀 1부터 시작
		 */
		public void onProfileImageClick(int position);
		public void onModifyProfileClick();
		public void onShowFaceCharmingClick();
		public void onShowProfileCharmingClick();
		
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
	private Button profileCharmingPointButton;
	
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
		
		faceCharmingPointBtn = (Button) findViewById(R.id.faceCharmingPointBtn);
		faceCharmingPointBtn.setOnClickListener(listener);
		faceCharmingPointText = (TextView) findViewById(R.id.faceCharmingPointText);
		profileCharmingPointText = (TextView) findViewById(R.id.profileCharmingPointText);
		profileCharmingPointButton = (Button) findViewById(R.id.profileCharmingPointButton);
		profileCharmingPointButton.setOnClickListener(listener);
		
	}
	
	public void setSimpleinfoText(String simpleinfoText) {
		this.simpleinfoText.setText(simpleinfoText);
	}
	
	public void setNicknameText(String nicknameText) {
		this.nicknameText.setText(nicknameText);
	}
	
	public void setSimpleintroText(String simpleintroText) {
		this.simpleintroText.setText(simpleintroText);
		this.profileAboutmeText.setText(simpleintroText);
	}
	
	public void setProfileImage(int index, String url) {
		switch (index) {
		case 0:
			LoadImage.load(profile1Img, url, ImageKind.PROFILE);
			break;
		case 1:
			LoadImage.load(profile2Img, url, ImageKind.PROFILE);
			break;
		case 2:
			LoadImage.load(profile3Img, url, ImageKind.PROFILE);
			break;
		case 3:
			LoadImage.load(profile4Img, url, ImageKind.PROFILE);
			break;
		default:
			LoadImage.load(mainImage, url, ImageKind.PROFILE);
			break;
		}
	}
	
	public void setProfileAboutmeText(String profileAboutmeText) {
		this.profileAboutmeText.setText(profileAboutmeText);
	}
	
	public void setProfileCharacterText(String profileCharacterText) {
		this.profileCharacterText.setText(profileCharacterText);
	}
	
	public void setProfileBodyText(String profileBodyText) {
		this.profileBodyText.setText(profileBodyText);
	}
	
	public void setProfileSchoolText(String profileSchoolText) {
		this.profileSchoolText.setText(profileSchoolText);
	}
	
	public void setProfileBloodText(String profileBloodText) {
		this.profileBloodText.setText(profileBloodText);
	}
	
	public void setProfileReligionText(String profileReligionText) {
		this.profileReligionText.setText(profileReligionText);
	}
	
	public void setProfileDrinksmokeText(String profileDrinksmokeText) {
		this.profileDrinksmokeText.setText(profileDrinksmokeText);
	}
	
	public void setProfileCoupleCountText(String profileCoupleCountText) {
		this.profileCoupleCountText.setText(profileCoupleCountText);
	}
	
	public void setProfileSimpleIntroDescText(
			String profileSimpleIntroDescText) {
		this.profileSimpleIntroDescText.setText(profileSimpleIntroDescText);
	}
	
	public void setProfileFaceText(String profileFaceText) {
		this.profileFaceText.setText(profileFaceText);
	}
	
	public void setProfileHobbyText(String profileHobbyText) {
		this.profileHobbyText.setText(profileHobbyText);
	}
	
	public void setProfileFavoriteText(String profileFavoriteText) {
		this.profileFavoriteText.setText(profileFavoriteText);
	}
	
	public void setProfileCharmingPointText(String profileCharmingPointText) {
		this.profileCharmingPointText.setText(profileCharmingPointText);
	}
	
	public void setProfileCharmingPointButtonText(String profileCharmingPointButton) {
		this.profileCharmingPointButton.setText(profileCharmingPointButton);
	}
	
	public void setFaceCharmingPointText(String faceCharmingPointText) {
		this.faceCharmingPointText.setText(faceCharmingPointText);
	}
	
	public void setFaceCharmingPointBtnText(String faceCharmingPointBtn) {
		this.faceCharmingPointBtn.setText(faceCharmingPointBtn);
	}
	
	private class Listener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.profileCharmingPointButton:
				callback.onShowProfileCharmingClick();
				break;
			case R.id.faceCharmingPointBtn:
				callback.onShowFaceCharmingClick();
				break;
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
				callback.onMainProfileImageClick();
				break;
			case R.id.idcardProfileImage1:
				callback.onProfileImageClick(0);
				break;
			case R.id.idcardProfileImage2:
				callback.onProfileImageClick(1);
				break;
			case R.id.idcardProfileImage3:
				callback.onProfileImageClick(2);
				break;
			case R.id.idcardProfileImage4:
				callback.onProfileImageClick(3);
				break;
			}
		}
		
	}

}

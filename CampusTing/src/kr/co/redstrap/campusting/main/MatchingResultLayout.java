package kr.co.redstrap.campusting.main;

import kr.co.redstrap.campusting.R;
import kr.co.redstrap.campusting.common.AbsCTLayout;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MatchingResultLayout extends AbsCTLayout {

	private LinearLayout matchingResultIdCardContainer;
	private ImageView matchingResultSuffleImage;

	private RelativeLayout idcardLayout1;
	private TextView simpleIntroText1;
	private TextView nicknameIntroText1;
	private TextView simpleProfileText1;
	private ImageView profileImage1;
	private ImageButton profile1Image1;
	private ImageButton profile2Image1;
	private ImageButton profile3Image1;
	private ImageButton profile4Image1;

	private RelativeLayout idcardLayout2;
	private TextView simpleIntroText2;
	private TextView nicknameIntroText2;
	private TextView simpleProfileText2;
	private ImageView profileImage2;
	private ImageButton profile1Image2;
	private ImageButton profile2Image2;
	private ImageButton profile3Image2;
	private ImageButton profile4Image2;

	public void setSimpleIntroText(int index, String text) {
		if (index == 0)
			simpleIntroText1.setText(text);
		else if (index == 1)
			simpleIntroText2.setText(text);
	}

	public void setNickName(int index, String text) {
		if (index == 0)
			nicknameIntroText1.setText(text);
		else if (index == 1)
			nicknameIntroText2.setText(text);
	}

	public void setSimpleProfileText(int index, String text) {
		if (index == 0)
			simpleProfileText1.setText(text);
		else if (index == 1)
			simpleProfileText2.setText(text);
	}

	public void setProfileImage(int index, int imageIndex, String url) {

	}

	public interface Callback {
		public void onIdCardClick(int index);

		public void onBackClick();
	}

	private Callback callback;

	private ImageButton backBtn;

	public void setCallback(Callback callback) {
		this.callback = callback;
	}

	public MatchingResultLayout(Context ctx) {
		super(ctx);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getRootViewId() {
		// TODO Auto-generated method stub
		return R.layout.activity_matching_result;
	}

	@Override
	public void inflateViews() {
		// TODO Auto-generated method stub

		Listener l = new Listener();

		backBtn = (ImageButton) findViewById(R.id.matchingresultProfileBackBtn);
		backBtn.setOnClickListener(l);

		matchingResultIdCardContainer = (LinearLayout) findViewById(R.id.matchingResultIdCardContainer);
		matchingResultSuffleImage = (ImageView) findViewById(R.id.matchingResultBg);

		idcardLayout1 = (RelativeLayout) findViewById(R.id.matchingResultCard1);
		idcardLayout1.setOnClickListener(l);

		profileImage1 = (ImageView) idcardLayout1
				.findViewById(R.id.idcardMainProfileImage);
		profile1Image1 = (ImageButton) idcardLayout1
				.findViewById(R.id.idcardProfileImage1);
		profile2Image1 = (ImageButton) idcardLayout1
				.findViewById(R.id.idcardProfileImage2);
		profile3Image1 = (ImageButton) idcardLayout1
				.findViewById(R.id.idcardProfileImage3);
		profile4Image1 = (ImageButton) idcardLayout1
				.findViewById(R.id.idcardProfileImage4);
		simpleIntroText1 = (TextView) idcardLayout1
				.findViewById(R.id.idcardSimpleIntroText);
		nicknameIntroText1 = (TextView) idcardLayout1
				.findViewById(R.id.idcardNickText);
		simpleProfileText1 = (TextView) idcardLayout1
				.findViewById(R.id.idcardSimpleProfileText);

		idcardLayout2 = (RelativeLayout) findViewById(R.id.matchingResultCard2);
		idcardLayout2.setOnClickListener(l);

		profileImage2 = (ImageView) idcardLayout2
				.findViewById(R.id.idcardMainProfileImage);
		profile1Image2 = (ImageButton) idcardLayout2
				.findViewById(R.id.idcardProfileImage1);
		profile2Image2 = (ImageButton) idcardLayout2
				.findViewById(R.id.idcardProfileImage2);
		profile3Image2 = (ImageButton) idcardLayout2
				.findViewById(R.id.idcardProfileImage3);
		profile4Image2 = (ImageButton) idcardLayout2
				.findViewById(R.id.idcardProfileImage4);
		simpleIntroText2 = (TextView) idcardLayout2
				.findViewById(R.id.idcardSimpleIntroText);
		nicknameIntroText2 = (TextView) idcardLayout2
				.findViewById(R.id.idcardNickText);
		simpleProfileText2 = (TextView) idcardLayout2
				.findViewById(R.id.idcardSimpleProfileText);

	}

	private class Listener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.matchingresultProfileBackBtn:
				callback.onBackClick();
				break;
			case R.id.matchingResultCard1:
				callback.onIdCardClick(0);
				break;
			case R.id.matchingResultCard2:
				callback.onIdCardClick(1);
				break;
			}
		}

	}
}

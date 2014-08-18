package kr.co.redstrap.campusting.login;

import kr.co.redstrap.campusting.R;
import kr.co.redstrap.campusting.common.AbsCTLayout;
import android.content.Context;
import android.widget.ImageView;

public class IntroLayout extends AbsCTLayout {
	
	private ImageView introImg;

	public IntroLayout(Context ctx) {
		super(ctx);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getRootViewId() {
		// TODO Auto-generated method stub
		return R.layout.activity_intro;
	}

	@Override
	public void inflateViews() {
		// TODO Auto-generated method stub

		introImg = (ImageView) findViewById(R.id.intro_img);
		
	}
	
	public void setDay(boolean day) {
		if (day)
			introImg.setImageResource(R.drawable.splash1_1);
		else
			introImg.setImageResource(R.drawable.splash2_1);
	}

}

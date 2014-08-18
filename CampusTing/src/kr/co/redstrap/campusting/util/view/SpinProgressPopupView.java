package kr.co.redstrap.campusting.util.view;

import kr.co.redstrap.campusting.R;
import kr.co.redstrap.campusting.common.AbsCTLayout;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.widget.ImageView;

public class SpinProgressPopupView extends AbsCTLayout {

	private ImageView loadingImage;
	
	public SpinProgressPopupView(Context ctx) {
		super(ctx);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getRootViewId() {
		// TODO Auto-generated method stub
		return R.layout.popup_progress;
	}

	@Override
	public void inflateViews() {
		// TODO Auto-generated method stub
		
		loadingImage = (ImageView) findViewById(R.id.loadingImg);
		
	}
	
	public void start() {
		AnimationDrawable drawable = (AnimationDrawable) loadingImage.getDrawable();
		drawable.start();
	}
	
	public void stop() {
		AnimationDrawable drawable = (AnimationDrawable) loadingImage.getDrawable();
		drawable.stop();
	}

}

package kr.co.redstrap.campusting.setting;

import java.util.ArrayList;

import kr.co.redstrap.campusting.R;
import kr.co.redstrap.campusting.common.AbsCTLayout;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class ModifyPhotoLayout extends AbsCTLayout {
	
	public interface Callback {
		public void onProfileImageClick(int index);
	}
	
	private ImageButton profileImage1;
	private ImageButton profileImage2;
	private ImageButton profileImage3;
	private ImageButton profileImage4;
	
	public void setImages(ArrayList<String> images) {
		
	}
	
	private Callback callback;
	
	public void setCallback(Callback callback) {
		this.callback = callback;
	}

	public ModifyPhotoLayout(Context ctx) {
		super(ctx);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getRootViewId() {
		// TODO Auto-generated method stub
		return R.layout.fragment_modify_profile_photo;
	}

	@Override
	public void inflateViews() {
		// TODO Auto-generated method stub
		
		Listener l = new Listener();
		
		profileImage1 = (ImageButton) findViewById(R.id.inputImage0);
		profileImage1.setOnClickListener(l);
		profileImage2 = (ImageButton) findViewById(R.id.inputImage1);
		profileImage2.setOnClickListener(l);
		profileImage3 = (ImageButton) findViewById(R.id.inputImage2);
		profileImage3.setOnClickListener(l);
		profileImage4 = (ImageButton) findViewById(R.id.inputImage3);
		profileImage4.setOnClickListener(l);
	}
	
	private class Listener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.inputImage0:
				callback.onProfileImageClick(0);
				break;
			case R.id.inputImage1:
				callback.onProfileImageClick(1);
				break;
			case R.id.inputImage2:
				callback.onProfileImageClick(2);
				break;
			case R.id.inputImage3:
				callback.onProfileImageClick(3);
				break;
			}
		}
		
	}

}

package kr.co.redstrap.campusting.setting;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import kr.co.redstrap.campusting.R;
import kr.co.redstrap.campusting.common.AbsCTLayout;
import kr.co.redstrap.campusting.util.CircleDrawable;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore.Images.Media;
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

	private ArrayList<ImageButton> imageHolder;

	public void setImages(ArrayList<String> images) {
		int size = images.size();
		for (int i = 0; i < size; i++) {
			ImageButton image = imageHolder.get(i);
			try {
				Bitmap bm = Media.getBitmap(
						((Activity) getContext()).getContentResolver(),
						Uri.fromFile(new File(images.get(i))));
				image.setImageDrawable(new CircleDrawable(bm));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
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

		imageHolder = new ArrayList<ImageButton>();
		imageHolder.add(profileImage1);
		imageHolder.add(profileImage2);
		imageHolder.add(profileImage3);
		imageHolder.add(profileImage4);
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

package kr.co.redstrap.campusting.join.frag;

import java.util.ArrayList;

import kr.co.redstrap.campusting.MainApp;
import kr.co.redstrap.campusting.R;
import kr.co.redstrap.campusting.common.AbsCTLayout;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class PictureLayout extends AbsCTLayout {
	
	public interface Callback {
		public void onPictureClick(int count);
		public void onPictureConfirmClick();
	}

	private PictureLayout.Callback callback;
	private ArrayList<ImageView> viewHolder;
	private Drawable emptyIcon;
	// 뷰
	private ImageButton pictureInput00;
	private ImageButton pictureInput01;
	private ImageButton pictureInput02;
	private ImageButton pictureInput03;
	private Button pictureConfirm;
	
	public PictureLayout(Context ctx) {
		super(ctx);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getRootViewId() {
		// TODO Auto-generated method stub
		return R.layout.activity_join_picture;
	}
	
	public void setPictureClickListener(PictureLayout.Callback listener) {
		this.callback = listener;
	}
	
	public ArrayList<ImageView> getImageHolder() {
		return viewHolder;
	}
	
	public void setEmptyIcon(int i) {
		viewHolder.get(i).setImageDrawable(emptyIcon);
	}
	
	@Override
	public void inflateViews() {
		// TODO Auto-generated method stub

		viewHolder = new ArrayList<ImageView>();
		emptyIcon = MainApp.appContext.getResources().getDrawable(R.drawable.perm_group_camera_selected);

		// 뷰 연결
		pictureInput00 = (ImageButton) findViewById(R.id.inputImage0);
		pictureInput01 = (ImageButton) findViewById(R.id.inputImage1);
		pictureInput02 = (ImageButton) findViewById(R.id.inputImage2);
		pictureInput03 = (ImageButton) findViewById(R.id.inputImage3);
		pictureConfirm = (Button) findViewById(R.id.inputImageConfirmBtn);

		Listener listener = new Listener();
		pictureConfirm.setOnClickListener(listener);
		pictureInput00.setOnClickListener(listener);
		pictureInput01.setOnClickListener(listener);
		pictureInput02.setOnClickListener(listener);
		pictureInput03.setOnClickListener(listener);
		
		// 서비스
		viewHolder.add(pictureInput00);
		viewHolder.add(pictureInput01);
		viewHolder.add(pictureInput02);
		viewHolder.add(pictureInput03);
	}
	
	private class Listener implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.inputImageConfirmBtn:
				callback.onPictureConfirmClick();
				break;
			case R.id.inputImage0:
				callback.onPictureClick(0);
				break;
			case R.id.inputImage1:
				callback.onPictureClick(1);
				break;
			case R.id.inputImage2:
				callback.onPictureClick(2);
				break;
			case R.id.inputImage3:
				callback.onPictureClick(3);
				break;
				
			}
		}
	}

}

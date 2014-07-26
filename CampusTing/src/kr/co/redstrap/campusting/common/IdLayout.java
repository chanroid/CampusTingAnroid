package kr.co.redstrap.campusting.common;

import kr.co.redstrap.campusting.R;
import android.content.Context;
import android.graphics.Point;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

public class IdLayout extends AbsCTLayout {

	private ScrollView idScrollInterface;
	private RelativeLayout idLayoutMain;
	private ImageView mainImage;
	private ImageView subImage00;
	private ImageView subImage01;
	private ImageView subImage02;
	private ImageView subImage03;

	private int targetPosition; // 가운데서 볼 사진 번호
	private int pictureNum; // 총 사진 갯수
	
	public IdLayout(Context ctx) {
		super(ctx);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getRootViewId() {
		// TODO Auto-generated method stub
		return R.layout.activity_id;
	}

	@Override
	public void inflateViews() {
		// TODO Auto-generated method stub

		// 뷰 연결
		idScrollInterface = (ScrollView) findViewById(R.id.id_scroll_interface);
		idLayoutMain = (RelativeLayout) findViewById(R.id.id_layout_main);
		mainImage = (ImageView) findViewById(R.id.id_img_main);
		subImage00 = (ImageView) findViewById(R.id.id_img_sub_00);
		subImage01 = (ImageView) findViewById(R.id.id_img_sub_01);
		subImage02 = (ImageView) findViewById(R.id.id_img_sub_02);
		subImage03 = (ImageView) findViewById(R.id.id_img_sub_03);

		// pictureNum과 targetPosition에 의해 표시할 이미지 세팅하는 부분이 필요함
	}

	/**
	 * 스크롤뷰 첫 페이지의 크기에 딱 맞게 idMain의 크기를 설정해줌
	 * 
	 * 20140702 chanroid
	 * 아직 사용하지 않지만 코드 이식 차원에서 작성해둠
	 * 
	 * @param idScroll
	 *            idMain이 담겨있는 스크롤뷰
	 * @param idMain
	 */
	private void setMainLayoutSize(View idScroll, View idMain) {
		Point outSize = new Point();
		WindowManager wmng = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
		wmng.getDefaultDisplay().getRealSize(outSize);
		
		int result = 0;
		int resourceId = getContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			result = getContext().getResources().getDimensionPixelSize(resourceId);
		}

		idMain.setLayoutParams(new LayoutParams(outSize.x - (idScroll.getPaddingLeft() + idScroll.getPaddingRight()), outSize.y
				- (result + idScroll.getPaddingTop() + idScroll.getPaddingBottom())));

	}

	
	public void setPictureNum(int pictureNum) {
		this.pictureNum = pictureNum;
	}

	public void setTargetPosition(int targetPosition) {
		this.targetPosition = targetPosition;
	}

	public void setOnClickListener(OnClickListener listener) {
		mainImage.setOnClickListener(listener);
		subImage00.setOnClickListener(listener);
		subImage01.setOnClickListener(listener);
		subImage02.setOnClickListener(listener);
		subImage03.setOnClickListener(listener);
	}

}

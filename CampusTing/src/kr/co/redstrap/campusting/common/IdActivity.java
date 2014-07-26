package kr.co.redstrap.campusting.common;

import java.util.ArrayList;

import kr.co.redstrap.campusting.MainApp;
import kr.co.redstrap.campusting.R;
import kr.co.redstrap.campusting.util.LruImgGetter;
import kr.co.redstrap.campusting.vo.User;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

/**
 * 학생증 표시되는 액티비티
 * 
 * @author rbi_bi_3
 *
 */
public class IdActivity extends Activity {

	private LruImgGetter imgGetter;
	private User mUser;
	private int targetPosition;
	private IdClickListener idClickListener;
	private int pictureNum;
	private ArrayList<String> pictureList = new ArrayList<String>();
	
	private IdLayout layout;

	public IdActivity() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		idClickListener = new IdClickListener();
		layout = new IdLayout(this);
		
		layout.setOnClickListener(idClickListener);
		setContentView(layout.getView());

		// 변수 셋팅
//		imgGetter = LruImgGetter.getInstance(MainApp.appContext);
		// 20140702 chanroid
		// 이미지 불러오는 부분 모두 AUIL로 대체해야 함
		mUser = (User) getIntent().getSerializableExtra("user"); // 유저 정보 - 필수

		// 서비스
//		setMainLayoutSize(idScrollInterface, idLayoutMain); 
		// 스크롤뷰의 크기에 학생증 메인을 맞춤

	}

	private class IdClickListener implements android.view.View.OnClickListener {

		@Override
		public void onClick(View v) {
			
		}

	}
}

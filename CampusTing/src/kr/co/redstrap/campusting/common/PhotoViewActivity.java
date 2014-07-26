package kr.co.redstrap.campusting.common;

import java.util.ArrayList;

import kr.co.redstrap.campusting.MainApp;
import kr.co.redstrap.campusting.R;
import kr.co.redstrap.campusting.util.LruImgGetter;
import kr.co.redstrap.campusting.util.indicator.CirclePageIndicator;
import kr.co.redstrap.campusting.util.photoview.PhotoView;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class PhotoViewActivity extends FragmentActivity {
	// 변수
	private ArrayList<String> pictureList;
	private int initialPosition;
	private LruImgGetter imgGetter;
	// 뷰
	private ViewPager pager;
	private CirclePageIndicator indicator;

	public PhotoViewActivity() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_photo_view);

		// 변수 셋팅
		imgGetter = LruImgGetter.getInstance(MainApp.appContext);
		// 데이터 셋팅
		Intent data = getIntent();
		pictureList = (ArrayList<String>) data.getSerializableExtra("photos");
		initialPosition = data.getIntExtra("position", 0);
		Log.i("포토뷰 초기 셋팅", initialPosition + " // " + pictureList.toString());

		// 뷰 연결
		pager = (ViewPager) findViewById(R.id.photo_pager);
		indicator = (CirclePageIndicator) findViewById(R.id.photo_indicator);

		PagerAdapter adapter = new PagerAdapter() {

			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				PhotoView view = new PhotoView(MainApp.appContext);
				imgGetter.loadBitmap(pictureList.get(position), view, LruImgGetter.SIZE_NORMAL, false);
				container.addView(view);
				return view;
			}

			@Override
			public void destroyItem(ViewGroup container, int position, Object object) {
				container.removeView((View) object);
			}

			@Override
			public boolean isViewFromObject(View view, Object object) {
				return view == object;
			}

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return pictureList.size();
			}
		};
		pager.setAdapter(adapter);
		pager.setOffscreenPageLimit(4);
		indicator.setViewPager(pager, initialPosition);
	}

	@Override
	protected void onPause() {
		super.onPause();
		overridePendingTransition(0, R.anim.fade_out);
	}
}

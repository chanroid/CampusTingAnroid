package kr.co.redstrap.campusting;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import kr.co.redstrap.campusting.util.AnimUtil;
import kr.co.redstrap.campusting.util.VibratorUtil;
import kr.co.redstrap.campusting.util.web.UrlUtil;
import kr.co.redstrap.campusting.vo.User;
import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Vibrator;
import android.util.Log;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * 
 * @author play
 * 
 */
public class MainApp extends Application {

	public static MainApp appContext;
	public static User mainUser = new User();

	public MainApp() {
		super();
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		// 공통으로 사용할 시스템 서비스 관련 유틸 로딩.
		// Memory Leak을 방지하기 위해 ApplicationContext를 이용함.
		appContext = this;
		initVibratorUtil(this);
		initAnimUtil(this);
		initUrlUtil();
		initImageLoader(this);
	}
	
	/**
	 * 이미지 로딩 라이브러리 초기화 {@link ImageLoader}
	 * 
	 * @param context
	 */
	private void initImageLoader(Context context) {
		ImageLoaderConfiguration conf = new ImageLoaderConfiguration.Builder(context)
			.threadPriority(Thread.NORM_PRIORITY - 2)
			.denyCacheImageMultipleSizesInMemory()
			.threadPoolSize(10)
			.taskExecutor(AsyncTask.SERIAL_EXECUTOR)
			.taskExecutorForCachedImages(AsyncTask.SERIAL_EXECUTOR)
			.build();
		
		ImageLoader.getInstance().init(conf);
	}

	/**
	 * 진동 컨트롤 유틸 로딩 {@link VibratorUtil}
	 * 
	 * @param context
	 */
	private void initVibratorUtil(Context context) {
		VibratorUtil.vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
	}

	/**
	 * 애니메이션 유틸 로딩 {@link AnimUtil}
	 * 
	 * @param context
	 */
	private void initAnimUtil(Context context) {
		AnimUtil.appContext = context;
	}

	/**
	 * URL 입력값 유틸 로딩 {@link UrlUtil}
	 * 20140630 chanroid 아래 경로에 파일이 없음. 다른 형식으로 파싱하는 방법 사용해도 무방할 듯
	 * 20140703 해당 경로의 패키지 안에 있음...;;
	 */
	public void initUrlUtil() {
		InputStream propStream = null;

		Properties prop = new Properties();
		try {
			propStream = MainApp.class.getResource("util/web/CampusTing.properties").openStream();
			prop.load(propStream);
			UrlUtil.campusTingWebUrl = prop.getProperty("campusTingWebUrl");
		} catch (FileNotFoundException e) {
			Log.e("FileNotFoundException", e.toString());
			e.printStackTrace();
		} catch (IOException e) {
			Log.e("IOException", e.toString());
			e.printStackTrace();
		} finally {
			if (propStream != null) {
				try {
					propStream.close();
				} catch (IOException e) {
					Log.e("IOException", e.toString());
					e.printStackTrace();
				}
			}
			// To drop useless instance
			prop = null;
		}
	}

}

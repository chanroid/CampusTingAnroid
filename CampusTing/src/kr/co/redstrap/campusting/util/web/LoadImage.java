package kr.co.redstrap.campusting.util.web;

import android.graphics.Bitmap;
import android.os.Handler;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * 이미지로딩할때 쓰는 클래스
 * 
 * @author rbi_bi_3
 *
 */
public class LoadImage {
	
	/**
	 * 로딩이미지 등등의 구분을 위해 해당 값들을 enum으로 지정
	 * 
	 * @author rbi_bi_3
	 *
	 */
	public static enum ImageKind {
		
		// 리소스 정해지면 지정
		FULL(0, 0, 0),
		PROFILE(0, 0, 0),
		SMALL(0, 0, 0);
		
		public int loadingImage;
		public int emptyImage;
		public int failImage;
		
		private ImageKind(int loading, int empty, int fail) {
			this.loadingImage = loading;
			this.emptyImage = empty;
			this.failImage = fail;
		}
	}
	
	public static void load(ImageView image, String url, ImageKind kind) {
		DisplayImageOptions options = new DisplayImageOptions.Builder()
        .showImageOnLoading(kind.loadingImage)
        .showImageForEmptyUri(kind.emptyImage)
        .showImageOnFail(kind.failImage)
        .resetViewBeforeLoading(false)  // default
        .cacheInMemory(true) // default
        .cacheOnDisk(true)
        .considerExifParams(false) // default
        .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2) // default
        .bitmapConfig(Bitmap.Config.ARGB_8888) // default
        .displayer(new FadeInBitmapDisplayer(500)) // default
        .handler(new Handler()) // default
        .build();
		
		ImageLoader.getInstance().displayImage(url, image, options);
	}
	
	public static void load(ImageView image, String url, ImageKind kind, ImageLoadingListener listener) {
		DisplayImageOptions options = new DisplayImageOptions.Builder()
        .showImageOnLoading(kind.loadingImage)
        .showImageForEmptyUri(kind.emptyImage)
        .showImageOnFail(kind.failImage)
        .resetViewBeforeLoading(false)  // default
        .cacheInMemory(true) // default
        .cacheOnDisk(true)
        .considerExifParams(false) // default
        .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2) // default
        .bitmapConfig(Bitmap.Config.ARGB_8888) // default
        .displayer(new FadeInBitmapDisplayer(500)) // default
        .handler(new Handler()) // default
        .build();
		
		ImageLoader.getInstance().displayImage(url, image, options, listener);
	}
	// 종류 추가되는대로
}

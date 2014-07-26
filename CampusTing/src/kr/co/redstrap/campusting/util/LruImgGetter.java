package kr.co.redstrap.campusting.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ConcurrentHashMap;

import kr.co.redstrap.campusting.util.web.UrlUtil;
import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore.Images.ImageColumns;
import android.provider.MediaStore.Images.Thumbnails;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.ImageView;

public class LruImgGetter {

	static int id = 0;
	static int fid = 0;
	static int cid = 0;

	/**
	 * 외부 URL 여부
	 */
	private boolean isVia;

	private static LruImgGetter lruImgGetter;

	private Context mContext;

	/**
	 * 메모리 캐쉬
	 */
	private LruCache<String, Bitmap> mMemoryCache;

	// Size of Image
	/**
	 * 원본 사이즈
	 */
	public static final int SIZE_NORMAL = 0;

	/**
	 * 프리뷰 사이즈
	 */
	public static final int SIZE_MINI = 1;

	/**
	 * 아이콘 사이즈
	 */
	public static final int SIZE_MICRO = 2;

	/**
	 * 결과물을 원형으로 출력하고 싶을 경우
	 */
	private boolean isRound;

	// List of Process :: 중복된 작업을 회피하기 위해 사용
	private ConcurrentHashMap<String, Boolean> webProcessList = new ConcurrentHashMap<String, Boolean>();
	private ConcurrentHashMap<String, Boolean> fileProcessList = new ConcurrentHashMap<String, Boolean>();

	private int webWorkCounter = 0;
	private final int webWorkLimiter = 4;
	private int fileWorkCounter = 0;
	private int fileWorkLimiter = 4;

	// Fields for DiskCache
	File cacheDirFile;
	String cacheDirPath;

	private LruImgGetter() {
		super();
		init();
	}

	private LruImgGetter(Context context) {
		super();
		this.mContext = context;
		cacheDirFile = mContext.getCacheDir();
		cacheDirPath = cacheDirFile.getAbsolutePath();

		init();
	}

	/**
	 * 싱글턴 패턴 + 더블 if 동기화 방식으로 객체 생성
	 * 
	 * @param context
	 *            가능하면 {@link Application}의 {@link Context}를 집어넣어주시길
	 * @return
	 */
	public static LruImgGetter getInstance(Context context) {
		if (lruImgGetter == null) {
			synchronized (LruImgGetter.class) {
				if (lruImgGetter == null) {
					lruImgGetter = new LruImgGetter(context);
				}
			}
		}
		return lruImgGetter;
	}

	// Get max available VM memory, exceeding this amount will throw an
	// OutOfMemory exception. Stored in kilobytes as LruCache takes an
	// int in its constructor.
	private void init() {
		final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

		// Use 1/8th of the available memory for this memory cache.
		final int cacheSize = maxMemory / 8;

		mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {

			@Override
			protected int sizeOf(String key, Bitmap bitmap) {
				// The cache size will be measured in kilobytes rather than
				// number of items.
				return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
			}
		};
	}

	public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
		if (getBitmapFromMemCache(key) == null) {
			mMemoryCache.put(key, bitmap);
		}
	}

	public Bitmap getBitmapFromMemCache(String key) {
		return mMemoryCache.get(key);
	}

	/**
	 * 
	 * 
	 * @param path
	 *            Uri의 경우 toString()을 사용하여 입력해주세요
	 * @param imageView
	 *            path의 이미지를 적용할 이미지뷰
	 * @param sizeType
	 *            {@link #SIZE_MICRO}, {@link #SIZE_MINI}, {@link #SIZE_NORMAL}
	 * @param isRound
	 *            최종 이미지 출력을 원으로 하고 싶다면 true로 주세요
	 */
	public void loadBitmap(String path, ImageView imageView, int sizeType, boolean isRound) {
		this.isRound = isRound;
		if (path == null) {
			imageView.setImageDrawable(null);
			return;
		}
		if (path.startsWith("content:")) {
			if (cancelPotentialWork(path, imageView)) {
				final Bitmap bitmap = getBitmapFromMemCache(sizeType + path);
				if (bitmap != null) {
					imageView.setImageBitmap(bitmap);
				} else {
					imageView.setImageDrawable(null);
					CursorBitmapWorkerTask task = new CursorBitmapWorkerTask(path, imageView);
					tagging(imageView, task);
					task.execute(sizeType);
				}
			}
			return;
		}
		if (path.startsWith("http:")) {
			this.isVia = true;
			path = path.substring(7);
		} else {
			this.isVia = false;
		}
		if (cancelPotentialWork(path, imageView)) {
			String targetPath = getTargetPath(path, sizeType);

			final Bitmap bitmap = getBitmapFromMemCache(targetPath);
			if (bitmap != null) {
				if (isRound) {
					imageView.setImageDrawable(new CircleDrawable(bitmap));
				} else {
					imageView.setImageBitmap(bitmap);
				}
			} else {
				if (isCacheExist(targetPath)) {
					imageView.setImageDrawable(null);
					FileBitmapWorkerTask task = new FileBitmapWorkerTask(targetPath, imageView);
					tagging(imageView, task);

					task.execute();

				} else {
					imageView.setImageDrawable(null);
					WebBitmapWorkerTask task = new WebBitmapWorkerTask(targetPath, imageView);
					tagging(imageView, task);
					task.execute();
				}
			}
		}
	}

	abstract class BitmapWorker extends AsyncTask<Integer, Void, Bitmap> {

		public abstract String getpath();
	}

	class CursorBitmapWorkerTask extends BitmapWorker {

		private final WeakReference<ImageView> imageViewReference;
		private final String path;
		private final int viewWidth;
		private final int viewHeight;

		public CursorBitmapWorkerTask(String path, ImageView imageView) {
			// Use a WeakReference to ensure the ImageView can be garbage collected
			this.path = path;
			this.viewWidth = imageView.getWidth();
			this.viewHeight = imageView.getHeight();
			imageViewReference = new WeakReference<ImageView>(imageView);
		}

		// Decode image in background.
		@Override
		protected Bitmap doInBackground(Integer... params) {
			cid++;
			Log.i("Ŀ����Ŀ" + cid, "++ ���� ++" + path);

			final int sizeType = params[0];

			Bitmap bitmap = null;

			Cursor cursor = mContext.getContentResolver().query(Uri.parse(path), null, null, null, null);
			cursor.moveToNext();
			long origId = cursor.getLong(cursor.getColumnIndex(ImageColumns._ID));
			String origData = cursor.getString(cursor.getColumnIndex(ImageColumns.DATA));
			cursor.close();

			if (sizeType == SIZE_MICRO) {
				bitmap = Thumbnails.getThumbnail(mContext.getContentResolver(), origId, Thumbnails.MICRO_KIND, null);
			} else if (sizeType == SIZE_MINI) {
				bitmap = Thumbnails.getThumbnail(mContext.getContentResolver(), origId, Thumbnails.MINI_KIND, null);
			} else {
				bitmap = BitmapFactory.decodeFile(origData);
			}

			if (bitmap != null) {
				addBitmapToMemoryCache(sizeType + path, bitmap);
			}

			return bitmap;
		}

		// Once complete, see if ImageView is still around and set bitmap.
		@Override
		protected void onPostExecute(Bitmap bitmap) {
			if (isCancelled()) {
				Log.i("Ŀ����Ŀ" + cid, "!! ��� !!" + path);
				bitmap = null;
			}

			if (imageViewReference != null && bitmap != null) {
				final ImageView imageView = imageViewReference.get();
				if (imageView != null) {
					if (isRound) {
						imageView.setImageDrawable(new CircleDrawable(bitmap));
					} else {
						imageView.setImageBitmap(bitmap);
					}
				}
				Log.i("Ŀ����Ŀ" + cid, "���" + path);
			}

		}

		@Override
		public String getpath() {
			return path;
		}
	}

	class FileBitmapWorkerTask extends BitmapWorker {

		private final WeakReference<ImageView> imageViewReference;
		private final String path;
		private final int viewWidth;
		private final int viewHeight;

		public FileBitmapWorkerTask(String path, ImageView imageView) {
			// Use a WeakReference to ensure the ImageView can be garbage collected
			this.path = path;
			this.viewWidth = imageView.getWidth();
			this.viewHeight = imageView.getHeight();
			imageViewReference = new WeakReference<ImageView>(imageView);
		}

		// Decode image in background.
		@Override
		protected Bitmap doInBackground(Integer... params) {
			fid++;
			Log.i("���Ͽ�Ŀ" + fid, "++ ���� ++" + path);
			boolean isWorker = false;
			Bitmap bitmap = null;

			synchronized (FileBitmapWorkerTask.class) {
				if (fileProcessList.get(path) == null) {
					fileProcessList.put(path, true);
					isWorker = true;
				}
			}

			synchronized (FileBitmapWorkerTask.class) {
				while (fileProcessList.get(path) && !isWorker) {
					Log.i("���Ͽ�Ŀ" + id, "## ��� ##" + path);
					try {
						FileBitmapWorkerTask.class.wait();
					} catch (InterruptedException e) {
						Log.i("���Ͽ�Ŀ" + id, "���ع���" + path);
					}
				}
			}

			if (isWorker) {

				synchronized (FileBitmapWorkerTask.class) {
					if (fileWorkCounter > fileWorkLimiter) {
						try {
							FileBitmapWorkerTask.class.wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {
						fileWorkCounter++;
					}
				}

				bitmap = BitmapFactory.decodeFile(cacheDirPath + "/" + path);
				// bitmap = decodeSampledBitmapFromFile(cacheDirPath + "/" + path, viewWidth, viewHeight);
				if (bitmap != null) {
					addBitmapToMemoryCache(path, bitmap);
				}

				fileProcessList.put(path, false);
				Log.i("���Ͽ�Ŀ" + id, "-- �ٿ�Ϸ� --" + path);
				synchronized (FileBitmapWorkerTask.class) {
					fileWorkCounter--;
					FileBitmapWorkerTask.class.notifyAll();
				}
			}

			bitmap = getBitmapFromMemCache(path);
			if (bitmap != null) {
				return bitmap;
			} else {
				bitmap = BitmapFactory.decodeFile(cacheDirPath + "/" + path);
				// bitmap = decodeSampledBitmapFromFile(cacheDirPath + "/" + path, viewWidth, viewHeight);
				if (bitmap != null) {
					addBitmapToMemoryCache(path, bitmap);
				}
			}
			return bitmap;
		}

		// Once complete, see if ImageView is still around and set bitmap.
		@Override
		protected void onPostExecute(Bitmap bitmap) {
			if (isCancelled()) {
				Log.i("���Ͽ�Ŀ" + fid, "!! ��� !!" + path);
				bitmap = null;
			}

			if (imageViewReference != null && bitmap != null) {
				final ImageView imageView = imageViewReference.get();
				if (imageView != null) {
					if (isRound) {
						imageView.setImageDrawable(new CircleDrawable(bitmap));
					} else {
						imageView.setImageBitmap(bitmap);
					}
				}
				Log.i("���Ͽ�Ŀ" + fid, "���" + path);
			}
		}

		@Override
		public String getpath() {
			return path;
		}
	}

	class WebBitmapWorkerTask extends BitmapWorker {

		private final WeakReference<ImageView> imageViewReference;
		private final String path;
		private final int viewWidth;
		private final int viewHeight;

		public WebBitmapWorkerTask(String path, ImageView imageView) {
			// Use a WeakReference to ensure the ImageView can be garbage collected

			this.path = path;
			this.viewWidth = imageView.getWidth();
			this.viewHeight = imageView.getHeight();
			imageViewReference = new WeakReference<ImageView>(imageView);
		}

		// Decode image in background.
		@Override
		protected Bitmap doInBackground(Integer... params) {
			id++;
			Log.i("WebBitmapWorkerTask" + id, "++ ���� ++" + path);
			boolean isWorker = false;
			Bitmap bitmap = null;
			synchronized (WebBitmapWorkerTask.class) {
				if (webProcessList.get(path) == null) {
					webProcessList.put(path, true);
					isWorker = true;
				}
			}

			synchronized (WebBitmapWorkerTask.class) {
				while (webProcessList.get(path) && !isWorker) {
					Log.i("WebBitmapWorkerTask" + id, "## ��� ##" + path);
					try {
						WebBitmapWorkerTask.class.wait();
					} catch (InterruptedException e) {
						Log.i("WebBitmapWorkerTask" + id, "���ع���" + path);
					}
				}
			}

			if (isWorker) {

				synchronized (WebBitmapWorkerTask.class) {
					if (webWorkCounter > webWorkLimiter) {
						try {
							WebBitmapWorkerTask.class.wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {
						webWorkCounter++;
					}
				}

				FileOutputStream fos = null;
				InputStream is = null;
				try {
					URL url = null;
					fos = new FileOutputStream(makeEmptyCacheFile(path));
					if (isVia) {
						url = new URL("http://" + path);
						Log.i("WebBitmapWorkerTask", url.toString());
					} else {
						url = new URL(UrlUtil.campusTingWebUrl + "files/" + path);
						Log.i("WebBitmapWorkerTask", url.toString());
					}
					is = (InputStream) url.getContent();
					byte[] buffer = new byte[64 * 1024];
					int byteLength = 0;
					while ((byteLength = is.read(buffer)) != -1) {
						fos.write(buffer, 0, byteLength);
					}

					fos.flush();

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (is != null) {
						try {
							is.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					if (fos != null) {
						try {
							fos.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}

				bitmap = BitmapFactory.decodeFile(cacheDirPath + "/" + path);
				// bitmap = decodeSampledBitmapFromFile(cacheDirPath + "/" + path, viewWidth, viewHeight);
				if (bitmap != null) {
					addBitmapToMemoryCache(path, bitmap);
				}

				webProcessList.put(path, false);
				Log.i("����Ŀ" + id, "-- �ٿ�Ϸ� --" + path);
				synchronized (WebBitmapWorkerTask.class) {
					webWorkCounter--;
					WebBitmapWorkerTask.class.notifyAll();
				}
			}

			bitmap = getBitmapFromMemCache(path);
			if (bitmap != null) {
				return bitmap;
			} else {
				bitmap = BitmapFactory.decodeFile(cacheDirPath + "/" + path);
				// bitmap = decodeSampledBitmapFromFile(cacheDirPath + "/" + path, viewWidth, viewHeight);
				if (bitmap != null) {
					addBitmapToMemoryCache(path, bitmap);
				}
			}

			return bitmap;
		}

		@Override
		protected void onPostExecute(Bitmap bitmap) {
			if (isCancelled()) {
				Log.i("����Ŀ" + id, "!! ��ҵ� !!" + path);
				bitmap = null;
			}

			if (imageViewReference != null && bitmap != null) {
				final ImageView imageView = imageViewReference.get();
				if (imageView != null) {
					if (isRound) {
						imageView.setImageDrawable(new CircleDrawable(bitmap));
					} else {
						imageView.setImageBitmap(bitmap);
					}
				}
				Log.i("����Ŀ" + fid, "���" + path);
			}

		}

		@Override
		public String getpath() {
			return path;
		}

		private File makeEmptyCacheFile(String path) {
			File targetFile = new File(cacheDirPath, path);

			if (path.indexOf('/') != -1) {
				File targetDir = new File(cacheDirPath, getFilePath(path));
				targetDir.mkdirs();
				targetFile = new File(targetDir, getFileName(path));
			} else {
				targetFile = new File(cacheDirPath, path);
			}

			try {
				targetFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return targetFile;
		}

		private String getFileName(String path) {
			return path.substring(path.lastIndexOf('/') + 1);
		}

		private String getFilePath(String path) {
			return path.substring(0, path.lastIndexOf('/'));
		}
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * 
	 * @param path
	 *            : ĳ�� ���� ���
	 * @return >> "path"�� ĳ�� ������ ������ ��� "boolean true"�� ��ȯ
	 */
	private boolean isCacheExist(String path) {

		File targetFile = new File(cacheDirPath, path);
		boolean result = targetFile.exists();

		if (webProcessList.get(path) != null && webProcessList.get(path) == true) {
			return false;
		}

		if (targetFile.length() == 0) {
			return false;
		}

		return result;
	}

	/**
	 * �̹����信 ǥ���� �̹��� ������ ��ΰ� �ٲ��ٸ� ������ �۾��� �����Ѵ�
	 * 
	 * @param path
	 *            : �̹��� ���� ���
	 * @param imageView
	 *            : ��� �̹�����
	 * @return : �۾��� ������ true, ��ΰ� �ٲ��ٸ� cancel(true)�� �õ��� ��� ��ȯ
	 */
	public static boolean cancelPotentialWork(String path, ImageView imageView) {
		final BitmapWorker bitmapWorkerTask = getBitmapWorkerTask(imageView);

		if (bitmapWorkerTask != null) {
			final String currentPath = bitmapWorkerTask.getpath();
			if (currentPath != path) {
				Log.i("�۾� ��� ����", path + " / new :   / " + currentPath + " :: " + bitmapWorkerTask.cancel(true));
			} else {
				// The same work is already in progress
				return false;
			}
		}
		// No task associated with the ImageView, or an existing task was cancelled
		return true;
	}

	/**
	 * BitmapWorker�� WeakReference�� ����� �̹����信 �±��Ѵ�
	 * 
	 * @param imageView
	 * @param bitmapWorker
	 */
	private void tagging(ImageView imageView, BitmapWorker bitmapWorker) {
		WeakReference<BitmapWorker> bitmapWorkerTaskReference = new WeakReference<BitmapWorker>(bitmapWorker);
		imageView.setTag(bitmapWorkerTaskReference);
	}

	/**
	 * 
	 * @param imageView
	 * @return : �ش� �̹����信 �۾� ���� BitmapWorkerTask�� ��ȯ
	 */
	private static BitmapWorker getBitmapWorkerTask(ImageView imageView) {
		if (imageView != null) {
			WeakReference<BitmapWorker> bitmapWorkerReference = (WeakReference<BitmapWorker>) imageView.getTag();
			if (bitmapWorkerReference != null) {
				return bitmapWorkerReference.get();
			}
		}
		return null;
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////////////////////////////////////////

	public static Bitmap decodeSampledBitmapFromFile(String path, int reqWidth, int reqHeight) {

		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(path, options);
	}

	public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {

		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(res, resId, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeResource(res, resId, options);
	}

	public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			final int halfHeight = height / 2;
			final int halfWidth = width / 2;

			// Calculate the largest inSampleSize value that is a power of 2 and keeps both
			// height and width larger than the requested height and width.
			while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
				inSampleSize *= 2;
			}
		}

		return inSampleSize;
	}

	public void deleteTargetCache(String path) {
		String targetPath = null;
		for (int i = 0; i < 3; i++) {
			targetPath = getTargetPath(path, i);
			webProcessList.remove(targetPath);
			fileProcessList.remove(targetPath);
			mMemoryCache.remove(targetPath);
			clearApplicationCache(targetPath);
		}
	}

	public void clearApplicationCache() {
		webProcessList.clear();
		fileProcessList.clear();
		mMemoryCache.evictAll();
		this.clearApplicationCache(cacheDirFile);
	}

	private void clearApplicationCache(File dir) {
		if (dir == null) {
			dir = cacheDirFile;
		}
		if (dir == null) {
			return;
		}
		java.io.File[] children = dir.listFiles();
		try {
			for (int i = 0; i < children.length; i++)
				if (children[i].isDirectory())
					clearApplicationCache(children[i]);
				else
					children[i].delete();
		} catch (Exception e) {
		}
	}

	public boolean clearApplicationCache(String path) {
		return new File(cacheDirPath, path).delete();
	}

	private String getTargetPath(String path, int sizeType) {
		String targetPath = path;
		if (sizeType != LruImgGetter.SIZE_NORMAL) {

			int dotPoint = path.length() - 4;
			String prePath = path.substring(0, dotPoint);
			String postPath = path.substring(dotPoint);

			if (sizeType == LruImgGetter.SIZE_MINI) {
				targetPath = prePath + "_mini" + postPath;
			} else {
				targetPath = prePath + "_micro" + postPath;
			}
		}
		return targetPath;
	}

}

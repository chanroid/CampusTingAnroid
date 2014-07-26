package kr.co.redstrap.campusting.deprecated;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

public abstract class WebTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {

	protected ConUtil conUtil;
	protected Map<String, String> queryMap;

	protected WebTask() {
		super();
	}

	protected WebTask(String request) {
		super();
		conUtil = new ConUtil(request);
		queryMap = new HashMap<String, String>();
	}

	/**
	 * 쿼리문에 사용할 Parameter + Value를 Map 형태로 입력
	 * 
	 * @param key
	 *            쿼리문의 Parameter
	 * @param value
	 *            쿼리문의 Value
	 * @param isKorean
	 *            한글 Value를 보내고 싶을 때 true로 주면 UTF-8로 인코딩하여 보냄
	 * @return
	 */
	public String put(String key, String value, boolean... isKorean) {
		if (isKorean != null && isKorean.length == 1) {
			try {
				return queryMap.put(key, URLEncoder.encode(value, "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				Log.e("UnsupportedEncodingException", e.toString());
			}
		}
		return queryMap.put(key, value);
	}

	/**
	 * 
	 * @param key
	 *            쿼리문의 Parameter
	 * @return 쿼리문의 Value
	 */
	public String get(String key) {
		return queryMap.get(key);
	}

	/**
	 * THREAD_POOL_EXECUTOR를 이용한 병렬 쓰레드
	 * 
	 * @param listGetter
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public AsyncTask<Params, Progress, Result> executeParallel() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			return this.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
		} else {
			return this.execute();
		}
	}

	/**
	 * SERIAL_EXECUTOR를 이용한 직렬 쓰레드
	 * 
	 * @param listGetter
	 */
	@SuppressWarnings("unchecked")
	public AsyncTask<Params, Progress, Result> executeSerial() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			return this.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
		} else {
			return this.execute();
		}
	}

}
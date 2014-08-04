package kr.co.redstrap.campusting.common;

import java.util.Vector;

import android.os.AsyncTask;
import android.os.Build;

public abstract class AbsCTSyncTask<P, R> extends AsyncTask<P, Integer, R> {
	
	public interface CTSyncTaskCallback<P, R> {
		public void onStartTask(AbsCTSyncTask<P, R> task);
		public void onProgressTask(AbsCTSyncTask<P, R> task, int progress);
		public void onErrorTask(AbsCTSyncTask<P, R> task, ErrorResult error);
		public void onSuccessTask(AbsCTSyncTask<P, R> task, R result);
	}
	
	public Vector<CTSyncTaskCallback<P, R>> callbacks = new Vector<CTSyncTaskCallback<P,R>>();
	
	public void addCallback(CTSyncTaskCallback<P, R> callback) {
		if (callbacks.contains(callback))
			return;
		else
			callbacks.add(callback);
	}
	
	public void removeCallback(CTSyncTaskCallback<P, R> callback) {
		if (!callbacks.contains(callback))
			return;
		else
			callbacks.remove(callback);
	}
	
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		for (CTSyncTaskCallback<P, R> callback : callbacks) {
			callback.onStartTask(this);
		}
	}
	
	@Override
	protected void onProgressUpdate(Integer... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
		for (CTSyncTaskCallback<P, R> callback : callbacks) {
			callback.onProgressTask(this, values[0]);
		}
	}
	
	/**
	 * 성공했을때와 실패했을때 타입이 다르게 들어옴<br>
	 * 타입에 따라 성공 실패여부 확인 후 콜백
	 * 
	 */
	@Override
	protected void onPostExecute(R result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		for (CTSyncTaskCallback<P, R> callback : callbacks) {
			if (result instanceof ErrorResult) {
				callback.onErrorTask(this, (ErrorResult) result);
			} else {
				callback.onSuccessTask(this, result);
			}
		}
	}

	/**
	 * THREAD_POOL_EXECUTOR를 이용한 병렬 쓰레드
	 * 
	 * @param listGetter
	 * @return 
	 * @return
	 */
	public AsyncTask<P, Integer, R> executeParallel(P... params) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			return this.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
		} else {
			return this.execute(params);
		}
	}

	/**
	 * SERIAL_EXECUTOR를 이용한 직렬 쓰레드
	 * 
	 * @param listGetter
	 */
	public AsyncTask<P, Integer, R> executeSerial(P... params) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			return this.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, params);
		} else {
			return this.execute(params);
		}
	}

}

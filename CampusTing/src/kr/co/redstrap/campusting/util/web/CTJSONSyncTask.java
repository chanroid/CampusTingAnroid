package kr.co.redstrap.campusting.util.web;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import kr.co.redstrap.campusting.common.AbsCTSyncTask;
import kr.co.redstrap.campusting.common.ErrorProcessor;
import kr.co.redstrap.campusting.common.ErrorResult;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import android.util.Log;

public class CTJSONSyncTask extends AbsCTSyncTask<String, Object> {
	
	private ArrayList<NameValuePair> httpParams = new ArrayList<NameValuePair>();

	@Override
	/**
	 * 인자값으로 url을 넣으면 됨<br>
	 * 리턴값은 
	 */
	protected Object doInBackground(String... params) {
		// TODO Auto-generated method stub
		HttpClient client = new DefaultHttpClient();
		
		HttpParams netparams = client.getParams();
		HttpConnectionParams.setConnectionTimeout(netparams, getTimeout());
		HttpConnectionParams.setSoTimeout(netparams, getTimeout());
		
		try {
			Log.d("CTJSONSyncTask", "url : " + UrlUtil.campusTingWebUrl + params[0]);
			HttpResponse response = client.execute(getRequest(UrlUtil.campusTingWebUrl + params[0]));
			BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), HTTP.UTF_8));
			
			StringBuffer buffer = new StringBuffer();
			String line = null;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			
			JSONObject result = new JSONObject(buffer.toString());
			Log.i("CTJSONSyncTask", result.toString());
			if (ErrorProcessor.isError(result)) {
				ErrorResult error = new ErrorResult(result.getInt("errCode"), result.getString("errMsg"));
				return (Object) error;
			} else {
				return (Object) result;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return (Object) ErrorResult.resultFromException(e);
		}
	}
	
	public void addHttpParam(String key, String value) {
		httpParams.add(new BasicNameValuePair(key, value));
	}
	
	public ArrayList<NameValuePair> getHttpParams() {
		return httpParams;
	}
	
	/**
	 * 기본 타임아웃 지정
	 * 
	 * @return
	 */
	public int getTimeout() {
		return 5000;
	}
	
	/**
	 * 여러모로 post로만 쓰는게 좋음<br>
	 * 파일전송이 필요할 경우 MultipartEntity 사용
	 * 
	 * @param params
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public HttpPost getRequest(String params) throws UnsupportedEncodingException {
		HttpPost httpPost = new HttpPost(params);
		addHttpParam("device", "android"); // test
		UrlEncodedFormEntity entityRequest = 
				new UrlEncodedFormEntity(getHttpParams(), HTTP.UTF_8);
		
		httpPost.setEntity(entityRequest);
		return httpPost;
	}
	
}

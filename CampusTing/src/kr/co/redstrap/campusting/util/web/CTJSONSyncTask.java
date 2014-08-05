package kr.co.redstrap.campusting.util.web;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import kr.co.redstrap.campusting.common.AbsCTSyncTask;
import kr.co.redstrap.campusting.common.ErrorProcessor;
import kr.co.redstrap.campusting.common.ErrorResult;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import android.util.Log;

public class CTJSONSyncTask extends AbsCTSyncTask<String, Object> {

	private ArrayList<NameValuePair> httpParams = new ArrayList<NameValuePair>();
	private Map<String, FileBody> httpFileParams = new HashMap<String, FileBody>();

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
			Log.d("CTJSONSyncTask", "url : " + UrlUtil.campusTingWebUrl
					+ params[0]);
			HttpResponse response = client
					.execute(getRequest(UrlUtil.campusTingWebUrl + params[0]));
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					response.getEntity().getContent(), HTTP.UTF_8));

			StringBuffer buffer = new StringBuffer();
			String line = null;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}

			JSONObject result = new JSONObject(buffer.toString());
			Log.i("CTJSONSyncTask", result.toString());
			if (ErrorProcessor.isError(result)) {
				ErrorResult error = new ErrorResult(result.getInt("errCode"),
						result.getString("errMsg"));
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
	
	public void addHttpParam(String key, int value) {
		httpParams.add(new BasicNameValuePair(key, String.valueOf(value)));
	}

	public void addHttpFileParam(String key, String path) {
		httpFileParams.put(key, new FileBody(new File(URI.create(path))));
	}

	public ArrayList<NameValuePair> getHttpParams() {
		return httpParams;
	}

	public Map<String, FileBody> getHttpFileParams() {
		return httpFileParams;
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
	@SuppressWarnings("deprecation")
	public HttpPost getRequest(String params) throws UnsupportedEncodingException {
		HttpPost httpPost = new HttpPost(params);
		addHttpParam("device", "android"); // test
		
		String boundary = "*****BOUNDARY*****";
		
		MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE, boundary, Charset.forName(HTTP.UTF_8));
		for (NameValuePair param : httpParams) {
			entity.addPart(param.getName(), new StringBody(param.getValue(), ContentType.TEXT_PLAIN));
		}
		
		for (String key : httpFileParams.keySet()) {
			entity.addPart(key, httpFileParams.get(key));
		}
		
		httpPost.setEntity(entity);
		return httpPost;
	}
}

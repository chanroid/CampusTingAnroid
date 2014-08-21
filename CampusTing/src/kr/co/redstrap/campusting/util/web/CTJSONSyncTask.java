package kr.co.redstrap.campusting.util.web;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import kr.co.redstrap.campusting.common.AbsCTSyncTask;
import kr.co.redstrap.campusting.common.ErrorProcessor;
import kr.co.redstrap.campusting.common.ErrorResult;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import android.util.Log;

public class CTJSONSyncTask extends AbsCTSyncTask<String, Object> {

	private ArrayList<NameValuePair> httpParams = new ArrayList<NameValuePair>();
	private Map<String, File> httpFileParams = new HashMap<String, File>();

	@Override
	/**
	 * 인자값으로 url을 넣으면 됨<br>
	 * 리턴값은 
	 */
	protected Object doInBackground(String... params) {
		// TODO Auto-generated method stub

		try {
			String boundary = "*****BOUNDARY*****";

			URL url = new URL(UrlUtil.campusTingWebUrl + params[0]);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
	        conn.setRequestProperty("Connection", "Keep-Alive");
	        conn.setRequestProperty("Content-Type", "multipart/form-data;boundary="
	                + boundary);
			DataOutputStream paramWriter = new DataOutputStream(conn.getOutputStream());
			
			paramWriter.writeBytes("--" + boundary + "\r\n");
			
			if (params != null) {
				addHttpParam("device", "android");
				for (NameValuePair p : httpParams) {
					if (!"".equals(p.getValue()) || p.getValue() != null) {
						paramWriter.writeBytes("Content-Disposition: form-data; name=\"" + p.getName() + "\"" + "\r\n");
						paramWriter.writeBytes("\r\n");
						paramWriter.writeUTF(p.getValue());
						paramWriter.writeBytes("\r\n");
						paramWriter.writeBytes("--" + boundary + "\r\n");
					}
				}
				
				for (String key : httpFileParams.keySet()) {
					Log.d("CTJSONSyncTask", "send file");
					paramWriter.writeBytes("Content-Disposition: form-data; name=\"" + key + "\";filename=\""+ httpFileParams.get(key).getName() + "\"" + "\r\n"); 
					paramWriter.writeBytes("\r\n"); 

					FileInputStream fileInputStream = new FileInputStream(httpFileParams.get(key));
					int bytesAvailable = fileInputStream.available(); 
					int maxBufferSize = 1024;
					int bufferSize = Math.min(bytesAvailable, maxBufferSize); 
					byte[] buffer = new byte[bufferSize];
					 
					int bytesRead = fileInputStream.read(buffer, 0, bufferSize); 
					while (bytesRead > 0) 
					{ 
						paramWriter.write(buffer, 0, bufferSize); 
						bytesAvailable = fileInputStream.available(); 
						bufferSize = Math.min(bytesAvailable, maxBufferSize); 
						bytesRead = fileInputStream.read(buffer, 0, bufferSize); 
					} 
					paramWriter.writeBytes("\r\n");
					paramWriter.writeBytes("--" + boundary + "\r\n");
					fileInputStream.close();
				}
			}

			paramWriter.flush();

			JSONObject result = new JSONObject(
					StringUtils.stringFromStream(conn.getInputStream()));
			Log.i("CTJSONSyncTask", result.toString());

			if (paramWriter != null)
				paramWriter.close();
			if (conn != null)
				conn.disconnect();
			
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
		addHttpParam(key, String.valueOf(value));
	}

	public void addHttpParam(String key, double lng) {
		// TODO Auto-generated method stub
		addHttpParam(key, String.valueOf(lng));
	}
	
	public void addHttpFileParam(String key, String path) {
		try {
			httpFileParams.put(key, new File(path));
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<NameValuePair> getHttpParams() {
		return httpParams;
	}

	public Map<String, File> getHttpFileParams() {
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
	public HttpUriRequest getRequest(String params)
			throws UnsupportedEncodingException {
		HttpPost httpPost = new HttpPost(params);
		addHttpParam("device", "android"); // test

		String boundary = "*****BOUNDARY*****";

		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		builder.setBoundary(boundary);
		builder.setCharset(Charset.forName(HTTP.UTF_8));
		builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

		for (NameValuePair param : httpParams) {
			try {
				builder.addTextBody(
						param.getName(),
						param.getValue(),
						ContentType.create("text/plain",
								Charset.forName(HTTP.UTF_8)));
				Log.i("CTJSONSyncTask",
						param.getName() + ", " + param.getValue());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		for (String key : httpFileParams.keySet()) {
			try {
				builder.addBinaryBody(key, httpFileParams.get(key));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		httpPost.setEntity(builder.build());
		return httpPost;
	}

}

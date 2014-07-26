package kr.co.redstrap.campusting.common;

import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

public class ErrorResult {
	
	/**
	 * exception을 에러 표시 객체로 변환
	 * 
	 * @param e
	 */
	public static ErrorResult resultFromException(Exception e) {
		if (e instanceof IOException) {
			
		} else if (e instanceof JSONException) {
			
		} else if (e instanceof ClientProtocolException) {
			
		} else if (e instanceof MalformedURLException) {
			
		} else if (e instanceof InterruptedException) {
			
		} else if (e instanceof NullPointerException) {
			
		}
		return new ErrorResult(99, "알 수 없는 오류가 발생했습니다.");
		// 새로운 exception이 생기면 계속 추가
	}
	
	public ErrorResult(int code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public int code;
	public String message;
}

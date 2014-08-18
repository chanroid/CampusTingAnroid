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
		int code = 99;
		String message = "알 수 없는 오류가 발생했습니다.";
		if (e instanceof IOException) {
			message = "네트워크 연결이 되지 않습니다.";
		} else if (e instanceof JSONException) {
			message = "잘못된 데이터를 전송받았습니다.";
		} else if (e instanceof ClientProtocolException) {
			message = "잘못된 요청입니다.";
		} else if (e instanceof MalformedURLException) {
			message = "잘못된 요청입니다.";
		} else if (e instanceof InterruptedException) {
			
		} else if (e instanceof NullPointerException) {
			
		}
		return new ErrorResult(code, message);
		// 새로운 exception이 생기면 계속 추가
	}
	
	public ErrorResult(int code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public int code;
	public String message;
}

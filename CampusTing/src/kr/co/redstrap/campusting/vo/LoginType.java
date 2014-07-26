package kr.co.redstrap.campusting.vo;

import java.io.Serializable;

public class LoginType implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6919368508158691666L;

	private String loginTypeId;
	private String loginType;
	private String link;

	public LoginType() {
		// TODO Auto-generated constructor stub
	}

	public LoginType(String loginTypeId, String loginType, String link) {
		super();
		this.loginTypeId = loginTypeId;
		this.loginType = loginType;
		this.link = link;
	}

	public String getLoginTypeId() {
		return loginTypeId;
	}

	public void setLoginTypeId(String loginTypeId) {
		this.loginTypeId = loginTypeId;
	}

	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	@Override
	public String toString() {
		return "LoginType [loginTypeId=" + loginTypeId + ", loginType=" + loginType + ", link=" + link + "]";
	}

}

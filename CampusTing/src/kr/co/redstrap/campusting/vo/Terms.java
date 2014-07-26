package kr.co.redstrap.campusting.vo;

import java.io.Serializable;

public class Terms implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9182086849396927752L;

	private String termsId;
	private String termsName;
	private String termsContent;

	public Terms() {
		// TODO Auto-generated constructor stub
	}

	public Terms(String termsId, String termsName, String termsContent) {
		super();
		this.termsId = termsId;
		this.termsName = termsName;
		this.termsContent = termsContent;
	}

	public String getTermsId() {
		return termsId;
	}

	public void setTermsId(String termsId) {
		this.termsId = termsId;
	}

	public String getTermsName() {
		return termsName;
	}

	public void setTermsName(String termsName) {
		this.termsName = termsName;
	}

	public String getTermsContent() {
		return termsContent;
	}

	public void setTermsContent(String termsContent) {
		this.termsContent = termsContent;
	}

	@Override
	public String toString() {
		return "TERMS [termsId=" + termsId + ", termsName=" + termsName + ", termsContent=" + termsContent + "]";
	}

}

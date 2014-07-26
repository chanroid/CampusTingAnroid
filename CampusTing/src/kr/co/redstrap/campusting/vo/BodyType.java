package kr.co.redstrap.campusting.vo;

import java.io.Serializable;

public class BodyType implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -951061345231542225L;

	private String bodyTypeId;
	private String type;
	private String explanation;

	public BodyType() {
		// TODO Auto-generated constructor stub
	}

	public BodyType(String bodyTypeId, String type, String explanation) {
		super();
		this.bodyTypeId = bodyTypeId;
		this.type = type;
		this.explanation = explanation;
	}

	public String getBodyTypeId() {
		return bodyTypeId;
	}

	public void setBodyTypeId(String bodyTypeId) {
		this.bodyTypeId = bodyTypeId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getExplanation() {
		return explanation;
	}

	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}

	@Override
	public String toString() {
		return "BodyType [bodyTypeId=" + bodyTypeId + ", type=" + type + ", explanation=" + explanation + "]";
	}

}

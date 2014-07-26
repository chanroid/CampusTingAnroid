package kr.co.redstrap.campusting.vo;

import java.io.Serializable;

public class Personality implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7704863573518980301L;

	private String personalityId;
	private String type;
	private String explanation;

	public Personality() {
		// TODO Auto-generated constructor stub
	}

	public Personality(String personalityId, String type, String explanation) {
		super();
		this.personalityId = personalityId;
		this.type = type;
		this.explanation = explanation;
	}

	public String getPersonalityId() {
		return personalityId;
	}

	public void setPersonalityId(String personalityId) {
		this.personalityId = personalityId;
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
		return "Personality [personalityId=" + personalityId + ", type=" + type + ", explanation=" + explanation + "]";
	}

}

package kr.co.redstrap.campusting.setting;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ModifyAppealFragment extends Fragment implements
		ModifyAppealLayout.Callback {
	public ModifyAppealLayout layout;

	private String onelineIntro;
	private String appealFace1;
	private String appealFace2;
	private String appealFace3;
	private String appealHobby1;
	private String appealHobby2;
	private String appealHobby3;
	private String appealIdealType1;
	private String appealIdealType2;
	private String appealIdealType3;

	public void setOneLineText(String text) {
		layout.setOneLineText(text);
		onelineIntro = text;
	}

	public String getOneLineText() {
		return onelineIntro;
	}

	public void setFaceAppealText(int index, String text) {
		layout.setFaceAppealText(index, text);
		switch (index) {
		case 0:
			appealFace1 = text;
			break;
		case 1:
			appealFace2 = text;
			break;
		case 2:
			appealFace3 = text;
			break;
		}
	}

	public String getFaceAppealText(int index) {
		switch (index) {
		case 0:
			return appealFace1;
		case 1:
			return appealFace2;
		case 2:
			return appealFace3;
		}
		return null;
	}

	public void setHobbyAppealText(int index, String text) {
		layout.setHobbyAppealText(index, text);
		switch (index) {
		case 0:
			appealHobby1 = text;
			break;
		case 1:
			appealHobby2 = text;
			break;
		case 2:
			appealHobby3 = text;
			break;
		}
	}

	public String getHobbyAppealText(int index) {
		switch (index) {
		case 0:
			return appealHobby1;
		case 1:
			return appealHobby2;
		case 2:
			return appealHobby3;
		}
		return null;
	}

	public void setIdealTypeText(int index, String text) {
		layout.setIdealTypeText(index, text);
		switch (index) {
		case 0:
			appealIdealType1 = text;
			break;
		case 1:
			appealIdealType2 = text;
			break;
		case 2:
			appealIdealType3 = text;
			break;
		}
	}

	public String getIdealTypeText(int index) {
		switch (index) {
		case 0:
			return appealIdealType1;
		case 1:
			return appealIdealType2;
		case 2:
			return appealIdealType3;
		}
		return null;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		layout = new ModifyAppealLayout(getActivity());
		layout.setCallback(this);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		if (layout == null) {
			layout = new ModifyAppealLayout(getActivity());
			layout.setCallback(this);
		}

		return layout.getView();
	}

	@Override
	public void onOneLineEdited(String onelineappeal) {
		// TODO Auto-generated method stub
		this.onelineIntro = onelineappeal;
	}

	@Override
	public void onFaceEdited(int index, String face) {
		// TODO Auto-generated method stub
		switch (index) {
		case 0:
			appealFace1 = face;
			break;
		case 1:
			appealFace2 = face;
			break;
		case 2:
			appealFace3 = face;
			break;
		}
	}

	@Override
	public void onHobbyEdited(int index, String hobby) {
		// TODO Auto-generated method stub
		switch (index) {
		case 0:
			appealHobby1 = hobby;
			break;
		case 1:
			appealHobby2 = hobby;
			break;
		case 2:
			appealHobby3 = hobby;
			break;
		}
	}

	@Override
	public void onIdealTypeEdited(int index, String idealType) {
		// TODO Auto-generated method stub
		switch (index) {
		case 0:
			appealIdealType1 = idealType;
			break;
		case 1:
			appealIdealType2 = idealType;
			break;
		case 2:
			appealIdealType3 = idealType;
			break;
		}
	}
}

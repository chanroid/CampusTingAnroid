package kr.co.redstrap.campusting.main;

import kr.co.redstrap.campusting.R;
import kr.co.redstrap.campusting.constant.CampusTingConstant;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

public class MainBuildingPopupFragment extends DialogFragment implements MainBuildingPopupLayout.Callback, CampusTingConstant {
	
	private MainBuildingPopupLayout layout;
	private int type;
	
	public MainBuildingPopupFragment(int type) {
		// TODO Auto-generated constructor stub
		this.type = type;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Dialog dialog = super.onCreateDialog(savedInstanceState);
		dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		
		return dialog;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		layout = new MainBuildingPopupLayout(getActivity());
		layout.setCallback(this);
		
		String title = "건물이름";
		String desc = "어디는 어쩌고 저쩌고 해서 무료 또는 돈\n입장하시겠습니까?";
		Drawable mainImage = getActivity().getResources().getDrawable(R.drawable.icon);
		
		// 20140723 chanroid 나중에 다 리소스로 바꿔야 함
		switch (type) {
		case Building.MAIN:
			Log.d("mainBtn", "본관");
			title = "본관";
			desc = "본관은 무료입니다.\n입장하시겠습니까?";
			break;
		case Building.GYM:
			title = "체육관";
			desc = "체육관과 카페 중 한곳은\n하루 한번 무료로 입장 가능합니다.\n입장하시겠습니까?";
			break;
		case Building.CAFE:
			title = "카페";
			desc = "카페와 체육관 중 한곳은\n하루 한번 무료로 입장 가능합니다.\n입장하시겠습니까?";
			break;
		case Building.LIBRARY:
			title = "도서관";
			desc = "도서관은 프리미엄이라 하트 몇개가 필요합니다.\n입장하시겠습니까?";
			break;
		case Building.DOMITORY:
			title = "기숙사";
			desc = "기숙사는 무료입니다.\n입장하시겠습니까?";
			break;
		case Building.CIRCLEROOM:
			title = "동아리방";
			desc = "동아리방과 호프 중 한곳은\n하루 한번 무료로 입장 가능합니다.\n입장하시겠습니까?";
			break;
		case Building.HOF:
			title = "호프";
			desc = "호프와 동아리방 중 한곳은\n하루 한번 무료로 입장 가능합니다.\n입장하시겠습니까?";
			break;
		case Building.CLUB:
			title = "클럽";
			desc = "클럽은 프리미엄이라 하트 몇개가 필요합니다.\n입장하시겠습니까?";
			break;
		}
		
		layout.setTitle(title);
		layout.setDesc(desc);
		layout.setMainImage(mainImage);
		
		return layout.getView();
	}

	@Override
	public void onCloseClick() {
		// TODO Auto-generated method stub
		dismiss();
	}

	@Override
	public void onConfirmClick() {
		// TODO Auto-generated method stub
		dismiss();
		// 20140731 chanroid 전달할 인자값들 좀 필요할듯
		startActivity(new Intent(getActivity(), MatchingResultActivity.class));
	}
}

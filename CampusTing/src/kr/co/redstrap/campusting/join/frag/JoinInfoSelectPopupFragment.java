package kr.co.redstrap.campusting.join.frag;

import java.util.ArrayList;
import java.util.Map;

import kr.co.redstrap.campusting.R;
import kr.co.redstrap.campusting.common.ErrorResult;
import kr.co.redstrap.campusting.join.JoinInfoSelectItems;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class JoinInfoSelectPopupFragment extends DialogFragment implements
		JoinInfoSelectPopupLayout.Callback {

	public interface Callback {
		public void onConfirmed(int requestCode,
				ArrayList<Map<String, Object>> confirmedItems);
	}

	private int requestCode;
	private Callback callback;
	private BaseAdapter adapter;
	private ArrayList<Map<String, Object>> gridItems;
	private ArrayList<Map<String, Object>> selectItems = new ArrayList<Map<String, Object>>();

	private JoinInfoSelectPopupLayout layout;

	private int selectableCount = 1;

	public void setCallback(Callback callback) {
		this.callback = callback;
	}

	public JoinInfoSelectPopupFragment(int requestCode) {
		// TODO Auto-generated constructor stub
		// 여기서 코드 기반으로 리스트 어댑터 아이템 세팅
		this.requestCode = requestCode;

		if (requestCode == JoinInfoSelectItems.CHARACTER) {
			selectableCount = 3;
		} else {
			selectableCount = 1;
		}

		gridItems = JoinInfoSelectItems.get(requestCode);
		adapter = new BaseAdapter() {
			public View getView(int position, View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub

				if (convertView == null) {
					convertView = View.inflate(getActivity(),
							R.layout.layout_listitem_joininfo_selectdata, null);
				}

				ImageView icon = (ImageView) convertView
						.findViewById(R.id.selectItemIcon);
				TextView title = (TextView) convertView
						.findViewById(R.id.selectItemName);

				@SuppressWarnings("unchecked")
				Map<String, Object> map = ((Map<String, Object>) getItem(position));
				Integer iconRes = (Integer) map.get("icon");
				String titleText = (String) map.get("title");

				icon.setImageResource(iconRes);
				title.setText(titleText);

				// 20140807 chanroid 임시로 색상으로 하고 나중에 이미지로 변경
				if (selectItems.contains(map)) {
					convertView.setBackgroundColor(getResources().getColor(
							android.R.color.holo_blue_light));
				} else {
					convertView.setBackgroundColor(Color.TRANSPARENT);
				}

				return convertView;
			}

			@Override
			public long getItemId(int position) {
				// TODO Auto-generated method stub
				return 0l;
			}

			@Override
			public Object getItem(int position) {
				// TODO Auto-generated method stub
				return gridItems.get(position);
			}

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return gridItems.size();
			}
		};
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

		layout = new JoinInfoSelectPopupLayout(getActivity(), adapter);
		layout.setCallback(this);
		layout.setSelectableCount(selectableCount);

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
		// 20140806 chanroid 선택된 아이템 번호로 실제 데이터를 넘겨주어야 함
		// 선택해야할 갯수 체크하는 로직 필요
		if (selectItems.size() == selectableCount) {
			dismiss();
			callback.onConfirmed(requestCode, selectItems);
		} else {
			layout.showErrorDialog(new ErrorResult(0, "최소 " + selectableCount
					+ "개 이상 선택해주세요."));
		}
	}

	@Override
	public void onItemSelected(int position) {
		// TODO Auto-generated method stub
		Map<String, Object> targetData = gridItems.get(position);
		if (selectItems.contains(targetData)) {
			selectItems.remove(targetData);
		} else {
			if (selectItems.size() >= selectableCount) {
				selectItems.remove(0);
			}
			selectItems.add(targetData);
		}
		adapter.notifyDataSetChanged();
		layout.setSelectCount(selectItems.size());
	}
}

package kr.co.redstrap.campusting.common;

import java.util.ArrayList;

import kr.co.redstrap.campusting.R;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ListDialogFragment extends DialogFragment {

	public interface Callback {
		public void onItemClick(int position);
	}

	private Callback callback;

	public void setCallback(Callback callback) {
		this.callback = callback;
	}
	
	private ArrayList<String> items;
	
	public ListDialogFragment(ArrayList<String> items) {
		// TODO Auto-generated constructor stub
		this.items = items;
	}

	private ListView listView;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Dialog dialog = super.onCreateDialog(savedInstanceState);
		dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		dialog.getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));

		return dialog;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View dialogView = View.inflate(getActivity(),
				R.layout.popup_listdialog, null);
		listView = (ListView) dialogView.findViewById(R.id.listdialogListView);
		
		Listener l = new Listener();
		listView.setOnItemClickListener(l);
		listView.setAdapter(new BaseAdapter() {
			
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
				
				if (convertView == null) {
					convertView = View.inflate(getActivity(), R.layout.layout_listitem_listdialog, null);
				}
				
				TextView title = (TextView) convertView.findViewById(R.id.listdialogItemTitle);
				title.setText(items.get(position));
				
				return convertView;
			}
			
			@Override
			public long getItemId(int position) {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public Object getItem(int position) {
				// TODO Auto-generated method stub
				return items.get(position);
			}
			
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return items.size();
			}
		});

		return dialogView;
	}
	
	private class Listener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			callback.onItemClick(position);
		}
		
	}

}

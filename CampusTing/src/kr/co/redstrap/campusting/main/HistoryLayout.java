package kr.co.redstrap.campusting.main;

import kr.co.redstrap.campusting.R;
import kr.co.redstrap.campusting.common.AbsCTLayout;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class HistoryLayout extends AbsCTLayout {

	private CheckedTextView allTingTab;
	private CheckedTextView receiveTingTab;
	private CheckedTextView sendTingTab;
	private CheckedTextView confirmTingTab;
	
	private ListView historyListView;
	
	public interface Callback {
		public void onAllTabClick();
		public void onReceiveTabClick();
		public void onSendTabClick();
		public void onConfirmTabClick();
		public void onTingItemClick(int index);
	}
	
	private Callback callback;
	
	public void setCallback(Callback callback) {
		this.callback = callback;
	}
	
	public HistoryLayout(Context ctx) {
		super(ctx);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getRootViewId() {
		// TODO Auto-generated method stub
		return R.layout.fragment_history;
	}

	@Override
	public void inflateViews() {
		// TODO Auto-generated method stub
		
		Listener l = new Listener();
		
		allTingTab = (CheckedTextView) findViewById(R.id.historyAlltingTab);
		allTingTab.setOnClickListener(l);
		receiveTingTab = (CheckedTextView) findViewById(R.id.historyReceiveTingTab);
		receiveTingTab.setOnClickListener(l);
		sendTingTab = (CheckedTextView) findViewById(R.id.historySendTingTab);
		sendTingTab.setOnClickListener(l);
		confirmTingTab = (CheckedTextView) findViewById(R.id.historyConfirmTingTab);
		confirmTingTab.setOnClickListener(l);
		historyListView = (ListView) findViewById(R.id.historyListView);
		historyListView.setOnItemClickListener(l);

	}

	private class Listener implements OnClickListener, OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			callback.onTingItemClick(position);
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.historyAlltingTab:
				callback.onAllTabClick();
				break;
			case R.id.historyReceiveTingTab:
				callback.onReceiveTabClick();
				break;
			case R.id.historySendTingTab:
				callback.onSendTabClick();
				break;
			case R.id.historyConfirmTingTab:
				callback.onConfirmTabClick();
				break;
			}
		}
		
	}
	
}

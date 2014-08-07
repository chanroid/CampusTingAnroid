package kr.co.redstrap.campusting.join.frag;

import kr.co.redstrap.campusting.R;
import kr.co.redstrap.campusting.common.AbsCTLayout;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class JoinInfoSelectPopupLayout extends AbsCTLayout {

	public interface Callback {

		public void onCloseClick();

		public void onItemSelected(int position);

		public void onConfirmClick();
	}

	private Callback callback;

	public void setCallback(Callback callback) {
		this.callback = callback;
	}

	private ImageButton closeBtn;
	private TextView titleText;
	private LinearLayout selectCheckLayout;
	private CheckedTextView selectCheck1;
	private CheckedTextView selectCheck2;
	private CheckedTextView selectCheck3;
	private GridView selectGrid;
	private Button selectConfirmBtn;

	public void setTitle(String title) {
		titleText.setText(title);
	}

	public JoinInfoSelectPopupLayout(Context ctx, BaseAdapter adapter) {
		super(ctx);
		// TODO Auto-generated constructor stub
		selectGrid.setAdapter(adapter);
	}
	
	public void setSelectableCount(int count) {
		if (count > 1)
			selectCheckLayout.setVisibility(View.VISIBLE);
		else
			selectCheckLayout.setVisibility(View.GONE);

		selectCheck1.setVisibility(count >= 1 ? View.VISIBLE : View.GONE);
		selectCheck2.setVisibility(count >= 2 ? View.VISIBLE : View.GONE);
		selectCheck3.setVisibility(count == 3 ? View.VISIBLE : View.GONE);
	}

	public void setSelectCount(int count) {
		selectCheck1.setChecked(count >= 1);
		selectCheck2.setChecked(count >= 2);
		selectCheck3.setChecked(count == 3);
	}

	@Override
	public int getRootViewId() {
		// TODO Auto-generated method stub
		return R.layout.popup_select;
	}

	@Override
	public void inflateViews() {
		// TODO Auto-generated method stub

		Listener listener = new Listener();

		closeBtn = (ImageButton) findViewById(R.id.selectBackBtn);
		closeBtn.setOnClickListener(listener);
		titleText = (TextView) findViewById(R.id.selectTitle);
		selectCheckLayout = (LinearLayout) findViewById(R.id.selectCheckLayout);
		selectCheck1 = (CheckedTextView) findViewById(R.id.selectCheck1);
		selectCheck2 = (CheckedTextView) findViewById(R.id.selectCheck2);
		selectCheck3 = (CheckedTextView) findViewById(R.id.selectCheck3);

		selectGrid = (GridView) findViewById(R.id.selectGrid);
		selectGrid.setOnItemClickListener(listener);

		selectConfirmBtn = (Button) findViewById(R.id.selectConfirmBtn);
		selectConfirmBtn.setOnClickListener(listener);
	}

	private class Listener implements OnClickListener, OnItemClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.selectBackBtn:
				callback.onCloseClick();
				break;
			case R.id.selectConfirmBtn:
				callback.onConfirmClick();
			}
		}

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub

			Log.i(JoinInfoSelectPopupLayout.class.getName(), "checked : "
					+ position);
			callback.onItemSelected(position);
		}

	}

}

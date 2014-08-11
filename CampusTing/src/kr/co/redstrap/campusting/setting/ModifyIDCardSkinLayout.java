package kr.co.redstrap.campusting.setting;

import kr.co.redstrap.campusting.R;
import kr.co.redstrap.campusting.common.AbsCTLayout;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class ModifyIDCardSkinLayout extends AbsCTLayout {

	private ImageView mainImage;
	private TextView simpleIntroText;
	private TextView nicknameText;
	private TextView simpleProfileText;
	
	private ImageButton profileImage1;
	private ImageButton profileImage2;
	private ImageButton profileImage3;
	private ImageButton profileImage4;
	
	private GridView skinlistGrid;
	
	public interface Callback {
		public void onSkinClick(int index);
	}
	
	private Callback callback;
	
	public void setCallback(Callback callback) {
		this.callback = callback;
	}
	
	public ModifyIDCardSkinLayout(Context ctx) {
		super(ctx);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getRootViewId() {
		// TODO Auto-generated method stub
		return R.layout.fragment_modify_idcard_skin;
	}

	@Override
	public void inflateViews() {
		// TODO Auto-generated method stub
		
		Listener l = new Listener();
		
		mainImage = (ImageView) findViewById(R.id.idcardMainProfileImage);
		simpleIntroText = (TextView) findViewById(R.id.idcardSimpleIntroText);
		nicknameText = (TextView) findViewById(R.id.idcardNickText);
		simpleProfileText = (TextView) findViewById(R.id.idcardSimpleProfileText);
		
		profileImage1 = (ImageButton) findViewById(R.id.idcardProfileImage1);
		profileImage2 = (ImageButton) findViewById(R.id.idcardProfileImage2);
		profileImage3 = (ImageButton) findViewById(R.id.idcardProfileImage3);
		profileImage4 = (ImageButton) findViewById(R.id.idcardProfileImage4);
		
		skinlistGrid = (GridView) findViewById(R.id.idcardskinGrid);
		skinlistGrid.setOnItemClickListener(l);
	}
	
	private class Listener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			callback.onSkinClick(position);
		}
		
	}

}

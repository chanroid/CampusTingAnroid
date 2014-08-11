package kr.co.redstrap.campusting.setting;

import android.content.Context;
import android.text.Editable;
import android.widget.EditText;
import kr.co.redstrap.campusting.R;
import kr.co.redstrap.campusting.common.AbsCTLayout;
import kr.co.redstrap.campusting.common.SimpleTextWatcher;

public class ModifyAppealLayout extends AbsCTLayout {

	public interface Callback {
		public void onOneLineEdited(String onelineappeal);
		public void onFaceEdited(int index, String face);
		public void onHobbyEdited(int index, String hobby);
		public void onIdealTypeEdited(int index, String idealType);
	}
	
	private Callback callback;
	
	public void setCallback(Callback callback) {
		this.callback = callback;
	}
	
	public void setOneLineText(String text) {
		appealOneLineEdit.setText(text);
	}
	
	public void setFaceAppealText(int index, String text) {
		switch (index) {
		case 0:
			appealFaceEdit1.setText(text);
			break;
		case 1:
			appealFaceEdit2.setText(text);
			break;
		case 2:
			appealFaceEdit3.setText(text);
			break;
		}
	}
	
	public void setHobbyAppealText(int index, String text) {
		switch (index) {
		case 0:
			appealHobbyEdit1.setText(text);
			break;
		case 1:
			appealHobbyEdit2.setText(text);
			break;
		case 2:
			appealHobbyEdit3.setText(text);
			break;
		}
	}
	
	public void setIdealTypeText(int index, String text) {
		switch (index) {
		case 0:
			appealIdealTypeEdit1.setText(text);
			break;
		case 1:
			appealIdealTypeEdit2.setText(text);
			break;
		case 2:
			appealIdealTypeEdit3.setText(text);
			break;
		}
	}
	
	private EditText appealOneLineEdit;
	private EditText appealFaceEdit1;
	private EditText appealFaceEdit2;
	private EditText appealFaceEdit3;
	private EditText appealHobbyEdit1;
	private EditText appealHobbyEdit2;
	private EditText appealHobbyEdit3;
	private EditText appealIdealTypeEdit1;
	private EditText appealIdealTypeEdit2;
	private EditText appealIdealTypeEdit3;
	
	public ModifyAppealLayout(Context ctx) {
		super(ctx);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getRootViewId() {
		// TODO Auto-generated method stub
		return R.layout.fragment_modify_profile_appeal;
	}

	@Override
	public void inflateViews() {
		// TODO Auto-generated method stub
		
		appealOneLineEdit = (EditText) findViewById(R.id.appealOnelineEdit);
		appealOneLineEdit.addTextChangedListener(new SimpleTextWatcher() {
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				callback.onOneLineEdited(s.toString());
			}
		});
		appealFaceEdit1 = (EditText) findViewById(R.id.appealFaceEdit1);
		appealFaceEdit1.addTextChangedListener(new SimpleTextWatcher() {
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				callback.onFaceEdited(0, s.toString());
			}
		});
		appealFaceEdit2 = (EditText) findViewById(R.id.appealFaceEdit2);
		appealFaceEdit2.addTextChangedListener(new SimpleTextWatcher() {
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				callback.onFaceEdited(1, s.toString());
			}
		});
		appealFaceEdit3 = (EditText) findViewById(R.id.appealFaceEdit3);
		appealFaceEdit3.addTextChangedListener(new SimpleTextWatcher() {
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				callback.onFaceEdited(2, s.toString());
			}
		});
		appealHobbyEdit1 = (EditText) findViewById(R.id.appealHobbyEdit1);
		appealHobbyEdit1.addTextChangedListener(new SimpleTextWatcher() {
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				callback.onHobbyEdited(0, s.toString());
			}
		});
		appealHobbyEdit2 = (EditText) findViewById(R.id.appealHobbyEdit2);
		appealHobbyEdit2.addTextChangedListener(new SimpleTextWatcher() {
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				callback.onHobbyEdited(1, s.toString());
			}
		});
		appealHobbyEdit3 = (EditText) findViewById(R.id.appealHobbyEdit3);
		appealHobbyEdit3.addTextChangedListener(new SimpleTextWatcher() {
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				callback.onHobbyEdited(2, s.toString());
			}
		});
		appealIdealTypeEdit1 = (EditText) findViewById(R.id.appealFavoriteEdit1);
		appealIdealTypeEdit1.addTextChangedListener(new SimpleTextWatcher() {
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				callback.onIdealTypeEdited(0, s.toString());
			}
		});
		appealIdealTypeEdit2 = (EditText) findViewById(R.id.appealFavoriteEdit2);
		appealIdealTypeEdit2.addTextChangedListener(new SimpleTextWatcher() {
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				callback.onIdealTypeEdited(1, s.toString());
			}
		});
		appealIdealTypeEdit3 = (EditText) findViewById(R.id.appealFavoriteEdit3);
		appealIdealTypeEdit3.addTextChangedListener(new SimpleTextWatcher() {
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				callback.onIdealTypeEdited(2, s.toString());
			}
		});
		
		
	}

}

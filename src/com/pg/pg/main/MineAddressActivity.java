package com.pg.pg.main;

import com.pg.pg.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MineAddressActivity extends Activity {
	
	private EditText diqutext;
	private Button  zhucequeding;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
       this.requestWindowFeature(Window.FEATURE_NO_TITLE);
       setContentView(R.layout.activity_mineaddress);  
       
       diqutext = (EditText) findViewById(R.id.diquEditText);
       diqutext.setInputType(InputType.TYPE_NULL);
       diqutext.setOnClickListener(listener);
       
       zhucequeding = (Button) findViewById(R.id.zhucequeding);
       zhucequeding.setOnClickListener(listener);
	}
	
	OnClickListener   listener = new OnClickListener() {
		@Override  
		public void onClick(View view) {  
			 int buttonid = view.getId();
			 switch (buttonid) {
			    case R.id.diquEditText:
			    	Log.d("=com.pg.pg.main.MineAddressActivity=", "==listener==diquEditText====");
			    	startActivity(new Intent(getApplication(), MineAddressActivity.class));
			    	break;
			    case R.id.zhucequeding:
			    	Log.d("=com.pg.pg.main.MineAddressActivity=", "==listener==zhucequeding====");
			    	startActivity(new Intent(getApplication(), MineAddressActivity.class));
			    	break;
			    default:
			       break;
			 
			 }
		}
	};
	
}

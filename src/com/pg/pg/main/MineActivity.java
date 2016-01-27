package com.pg.pg.main;

import com.pg.pg.R;
import com.pg.pg.bean.Pgdr_userApp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MineActivity extends Activity{
	 
	private TextView dizhi;
	private TextView dingdan;
	private TextView phone;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
       this.requestWindowFeature(Window.FEATURE_NO_TITLE);
       setContentView(R.layout.activity_mine);  
       dizhi = (TextView) findViewById(R.id.dizhi);
       dingdan = (TextView) findViewById(R.id.dingdan);
       dizhi.setOnClickListener(textViewlistener);
       dingdan.setOnClickListener(textViewlistener);
       phone = (TextView) findViewById(R.id.phone);
       phone.setText(((Pgdr_userApp) getApplication()).getUser_mobile());
	}
	
	OnClickListener   textViewlistener = new OnClickListener() {
		@Override  
		public void onClick(View view) {  
			 int buttonid = view.getId();
			 switch (buttonid) {
			    case R.id.dizhi:
			    	Log.d("=com.pg.pg.main.MineActivity=", "==textViewlistener==dizhi====");
			    	startActivity(new Intent(getApplication(), MineAddressActivity.class));
			    	break;
			    case R.id.dingdan:
			    	Log.d("=com.pg.pg.main.MineActivity=", "==textViewlistener==dingdan====");
			    	break;
			    default:
			       break;
			 
			 }
		}
	};
}

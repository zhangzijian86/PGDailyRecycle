package com.pg.pg.main;

import com.pg.pg.R;
import com.pg.pg.bean.Pgdr_userApp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

public class MyOrderDetailActivity extends Activity {
	private TextView phone;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_myorderdetail);  
        phone = (TextView) findViewById(R.id.phone);
        phone.setText(((Pgdr_userApp) getApplication()).getUser_mobile());
	}
}

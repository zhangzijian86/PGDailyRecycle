package com.pg.pg.main;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TextView;

public class RecycleActivity extends Activity{
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		TextView tv = new TextView(this);
		tv.setText("RecycleActivity");
		tv.setGravity(Gravity.CENTER);
		setContentView(tv);
	}

}

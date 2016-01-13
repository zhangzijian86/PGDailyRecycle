package com.pg.pg.main;

import com.pg.pg.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

public class MineActivity extends Activity{
	 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TextView tv = new TextView(this);
		tv.setText("MineActivity");
		tv.setGravity(Gravity.CENTER);
		setContentView(tv);
	}
}

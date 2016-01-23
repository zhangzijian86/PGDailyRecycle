package com.pg.pg.main;

import com.pg.pg.R;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TabHost;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class MainActivity  extends TabActivity implements OnCheckedChangeListener{
	
	private TabHost mTabHost;
	private Intent mRcycleIntent;
//	private Intent mBIntent;
	private Intent mMineIntent;
	RadioButton mRcycleRadioButton;
	RadioButton mMineRadioButton;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);        
        this.mRcycleIntent = new Intent(this,RecycleActivity.class);
        this.mMineIntent = new Intent(this,MineActivity.class);
        mRcycleRadioButton = ((RadioButton) findViewById(R.id.radio_button0));
        mRcycleRadioButton.setOnCheckedChangeListener(this);
        mMineRadioButton = ((RadioButton) findViewById(R.id.radio_button1));
        mMineRadioButton.setOnCheckedChangeListener(this);
        setupIntent();
        mRcycleRadioButton.setChecked(true);
    }
    
	private void setupIntent() {
		this.mTabHost = getTabHost();
		TabHost localTabHost = this.mTabHost;

		localTabHost.addTab(buildTabSpec("A_TAB", R.string.main_home,
				R.drawable.icon_1_n, this.mRcycleIntent));

		localTabHost.addTab(buildTabSpec("B_TAB", R.string.main_my,
				R.drawable.icon_2_n, this.mMineIntent));		
	}
    
	private TabHost.TabSpec buildTabSpec(String tag, int resLabel, int resIcon,
			final Intent content) {
		return this.mTabHost.newTabSpec(tag).setIndicator(getString(resLabel),
				getResources().getDrawable(resIcon)).setContent(content);
	}
	
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if(isChecked){
			switch (buttonView.getId()) {
				case R.id.radio_button0:
					this.mTabHost.setCurrentTabByTag("A_TAB");
					mRcycleRadioButton.setTextColor(Color.parseColor("#109200"));//
					mMineRadioButton.setTextColor(Color.parseColor("#000000"));//
					Drawable drawable0=this.getResources().getDrawable(R.drawable.home_lv); 
					drawable0.setBounds(0, 0, drawable0.getMinimumWidth(), drawable0.getMinimumHeight()-1);
					Drawable drawable1=this.getResources().getDrawable(R.drawable.ren_hei); 					
					drawable1.setBounds(0, 0, drawable1.getMinimumWidth(), drawable1.getMinimumHeight()-1);
					mRcycleRadioButton.setCompoundDrawables(null,drawable0,null,null);
					mMineRadioButton.setCompoundDrawables(null,drawable1,null,null);
					break;
				case R.id.radio_button1:
					this.mTabHost.setCurrentTabByTag("B_TAB");
					mRcycleRadioButton.setTextColor(Color.parseColor("#000000"));//
					mMineRadioButton.setTextColor(Color.parseColor("#109200"));//
					Drawable drawable2=this.getResources().getDrawable(R.drawable.home_hei); 
					drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight()-1);
					Drawable drawable3=this.getResources().getDrawable(R.drawable.ren_lv); 
					drawable3.setBounds(0, 0, drawable3.getMinimumWidth(), drawable3.getMinimumHeight()-1);
					mRcycleRadioButton.setCompoundDrawables(null,drawable2,null,null);
					mMineRadioButton.setCompoundDrawables(null,drawable3,null,null);
					break;
			}		
		}
	}
}

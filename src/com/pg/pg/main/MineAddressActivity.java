package com.pg.pg.main;

import com.pg.pg.R;
import com.pg.pg.bean.Pgdr_userApp;
import com.pg.pg.wheel.active.BaseWhellActivity;
import com.pg.pg.wheel.active.WhellActivity;
import com.pg.pg.wheel.widget.OnWheelChangedListener;
import com.pg.pg.wheel.widget.WheelView;
import com.pg.pg.wheel.widget.adapters.ArrayWheelAdapter;

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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MineAddressActivity  extends BaseWhellActivity implements OnClickListener, OnWheelChangedListener {
	
	private WheelView mViewProvince;
	private WheelView mViewCity;
	private WheelView mViewDistrict;
	private Button mBtnConfirm;
	
	private EditText diqutext;
	private EditText xiangxidizhiEditText;
	private EditText shoujiEditText;
	private EditText lianxirenEditText;
	private Button  zhucequeding;
	
	private LinearLayout diquxuanze;;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
       this.requestWindowFeature(Window.FEATURE_NO_TITLE);
       setContentView(R.layout.activity_mineaddress);  
       
       diqutext = (EditText) findViewById(R.id.diquEditText);
       xiangxidizhiEditText = (EditText) findViewById(R.id.xiangxidizhiEditText);
       shoujiEditText = (EditText) findViewById(R.id.shoujiEditText);
       lianxirenEditText = (EditText) findViewById(R.id.lianxirenEditText);
       
       shoujiEditText.setText( ((Pgdr_userApp) getApplication()).getUser_mobile());
       lianxirenEditText.setText( ((Pgdr_userApp) getApplication()).getUser_name());
       
       diqutext.setInputType(InputType.TYPE_NULL);
       diqutext.setOnClickListener(this);  
       
       
       
       
       if(((Pgdr_userApp) getApplication()).getUser_address()!=null&&!((Pgdr_userApp) getApplication()).getUser_address().equals("")){
    	   String address[] = ((Pgdr_userApp) getApplication()).getUser_address().split(":");
    	   diqutext.setText(address[0]);
    	   if(address.length>=2){
    		   xiangxidizhiEditText.setText(address[1]);
    	   }
       }
       
       
       
       zhucequeding = (Button) findViewById(R.id.zhucequeding);
       zhucequeding.setOnClickListener(this);
       
       diquxuanze = (LinearLayout)findViewById(R.id.diquxuanze);
       diquxuanze.setVisibility(View.GONE);
       
		setUpViews();
		setUpListener();
		setUpData();
		mCurrentDistrictName = "昌平区";
	}
	
	private void setUpViews() {
		mViewProvince = (WheelView) findViewById(R.id.id_province);
		mViewCity = (WheelView) findViewById(R.id.id_city);
		mViewDistrict = (WheelView) findViewById(R.id.id_district);
		mBtnConfirm = (Button) findViewById(R.id.btn_confirm);
	}
	
	private void setUpListener() {
    	// 添加change事件
    	mViewProvince.addChangingListener(this);
    	// 添加change事件
    	mViewCity.addChangingListener(this);
    	// 添加change事件
    	mViewDistrict.addChangingListener(this);
    	// 添加onclick事件
    	mBtnConfirm.setOnClickListener(this);
    }
	
	private void setUpData() {
		initProvinceDatas();
		mViewProvince.setViewAdapter(new ArrayWheelAdapter<String>(MineAddressActivity.this, mProvinceDatas));
		mViewProvince.setCurrentItem(1);
		// 设置可见条目数量
		mViewProvince.setVisibleItems(7);
		mViewCity.setVisibleItems(7);
		mViewDistrict.setVisibleItems(7);
		updateCities();
		updateAreas();
	}

	@Override
	public void onChanged(WheelView wheel, int oldValue, int newValue) {
		// TODO Auto-generated method stub
		if (wheel == mViewProvince) {
			Log.d("=com.pg.pg.main.MineAddressActivity=", "==onChanged===mViewProvince===");
			updateCities();
		} else if (wheel == mViewCity) {
			Log.d("=com.pg.pg.main.MineAddressActivity=", "==onChanged===mViewCity===");
			updateAreas();
		} else if (wheel == mViewDistrict) {
			Log.d("=com.pg.pg.main.MineAddressActivity=", "==onChanged===mViewDistrict=newValue=="+newValue);
			mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[newValue];
			mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
			Log.d("=com.pg.pg.main.MineAddressActivity=", "==onChanged===mCurrentDistrictName==="+mCurrentDistrictName);
		}
	}

	/**
	 * 根据当前的市，更新区WheelView的信息
	 */
	private void updateAreas() {
		int pCurrent = mViewCity.getCurrentItem();
		mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
		String[] areas = mDistrictDatasMap.get(mCurrentCityName);

		if (areas == null) {
			areas = new String[] { "" };
		}
		mViewDistrict.setViewAdapter(new ArrayWheelAdapter<String>(this, areas));
		mViewDistrict.setCurrentItem(0);
		mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[0];
	}

	/**
	 * 根据当前的省，更新市WheelView的信息
	 */
	private void updateCities() {
		int pCurrent = mViewProvince.getCurrentItem();
		mCurrentProviceName = mProvinceDatas[pCurrent];
		String[] cities = mCitisDatasMap.get(mCurrentProviceName);
		if (cities == null) {
			cities = new String[] { "" };
		}
		mViewCity.setViewAdapter(new ArrayWheelAdapter<String>(this, cities));
		mViewCity.setCurrentItem(0);
		updateAreas();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_confirm:
			diquxuanze.setVisibility(View.GONE);
			diqutext.setText(mCurrentProviceName+" "+mCurrentCityName+" "+mCurrentDistrictName);
			break;
	    case R.id.diquEditText:
	    	Log.d("=com.pg.pg.main.MineAddressActivity=", "==listener==diquEditText====");
	    	diquxuanze.setVisibility(View.VISIBLE);
	    	break;
	    case R.id.zhucequeding:
	    	Log.d("=com.pg.pg.main.MineAddressActivity=", "==listener==zhucequeding====");
	    	break;
		default:
			break;
		}
	}

	private void showSelectedResult() {		
		Toast.makeText(MineAddressActivity.this, "当前选中:"+mCurrentProviceName+","+mCurrentCityName+","
				+mCurrentDistrictName+","+mCurrentZipCode, Toast.LENGTH_SHORT).show();
	}
	
}

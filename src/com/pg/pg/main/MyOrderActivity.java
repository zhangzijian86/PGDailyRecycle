package com.pg.pg.main;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.pg.pg.R;
import com.pg.pg.bean.Pgdr_userApp;
import com.pg.pg.bean.Ppdr_dailyrecycle;
import com.pg.pg.json.JsonUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class MyOrderActivity extends Activity {
	private List<Ppdr_dailyrecycle> myorderlist = new ArrayList<Ppdr_dailyrecycle>();	
	private List<Ppdr_dailyrecycle> myorderlistdetail = new ArrayList<Ppdr_dailyrecycle>();	
	private Pgdr_userApp puser;
	private TextView phone;
	private ImageView fanhui;
	ListView listviewdatail;
	private LinearLayout linefanhui;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_myorder);    
        puser = (Pgdr_userApp) getApplication();
        Intent intent = getIntent(); 
        String result = intent.getStringExtra("result");
        init(result);
        phone = (TextView) findViewById(R.id.phone);
        phone.setText(((Pgdr_userApp) getApplication()).getUser_mobile());
        listviewdatail = (ListView)findViewById(R.id.myorderlistviewdatail);	
        Log.d("MyOrderActivity", "==puser.getUser_type()=="+puser.getUser_type());
	 	if(puser.getUser_type()!=null&&puser.getUser_type().equals("2")){
	 		listviewdatail.setVisibility(View.VISIBLE);
	 	}else{
	 		listviewdatail.setVisibility(View.GONE);
	 	}	
        if(myorderlist!=null&&myorderlist.size()>1){        
	        PdrAdapter pdradapter = new PdrAdapter(
	        		MyOrderActivity.this,R.layout.activity_myorderlistview,myorderlist);
		 	ListView listview = (ListView)findViewById(R.id.myorderlistview);
		 	listview.setAdapter(pdradapter);	
		 	listview.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position,
						long id) {
					if(position!=0){
						Ppdr_dailyrecycle pdr =  myorderlist.get(position);
						List<Ppdr_dailyrecycle> list1=new ArrayList<Ppdr_dailyrecycle>();
						list1.add(pdr);
						Gson gson=new Gson();
						String jsonstring=gson.toJson(list1);
						Intent intentDetail = new Intent(getApplicationContext(), MyOrderDetailActivity.class);        
						intentDetail.putExtra("jsonstring", jsonstring);
						intentDetail.putExtra("type", "0");
				    	startActivity(intentDetail);
						Log.d("MyOrderActivity", "getDailyrecycle_user_mobile:"+pdr.getDailyrecycle_user_mobile()+",getDailyrecycle_name:"+pdr.getDailyrecycle_name()+",id:"+id);
					}
				}	 		
			});   
        }
	 	
        if(myorderlistdetail!=null&&myorderlistdetail.size()>1){     
		 	PdrAdapterDetail pdradapterdetail = new PdrAdapterDetail(
	        		MyOrderActivity.this,R.layout.activity_deliveryorderlistview,myorderlistdetail);	 		
		 	listviewdatail.setAdapter(pdradapterdetail);	
		 	listviewdatail.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position,
						long id) {
					if(position!=0){
						Ppdr_dailyrecycle pdr =  myorderlistdetail.get(position);
						List<Ppdr_dailyrecycle> list1=new ArrayList<Ppdr_dailyrecycle>();
						list1.add(pdr);
						Gson gson=new Gson();
						String jsonstring=gson.toJson(list1);
						Intent intentDetail = new Intent(getApplicationContext(), MyOrderDetailActivity.class);        
						intentDetail.putExtra("jsonstring", jsonstring);
						intentDetail.putExtra("type", "1");
				    	startActivity(intentDetail);
						Log.d("MyOrderActivity", "getDailyrecycle_user_mobile:"+pdr.getDailyrecycle_user_mobile()+",getDailyrecycle_id:"+pdr.getDailyrecycle_id()+",id:"+id);
					}
				}	 		
			});  
        }
        linefanhui = (LinearLayout)findViewById(R.id.linefanhui);
        linefanhui.setOnClickListener(new OnClickListener() {			
 			@Override
 			public void onClick(View arg0) {				
 				finish();
 			}
 		});
 	   fanhui  = (ImageView)findViewById(R.id.fanhui);
 	   fanhui.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View arg0) {				
				finish();
			}
		});
	}
	public void init(String result){
		JsonUtil jsonUtil=new JsonUtil();
		List<Ppdr_dailyrecycle> list1=(List<Ppdr_dailyrecycle>) jsonUtil.StringFromJsonRecycle(result);
		Log.d("MyOrderActivity", "MyOrderActivity=list1="+list1.size());
		if(list1!=null&&list1.size()>0){
			for (int i = 0; i < list1.size(); i++) {
				if(i==0){
					Ppdr_dailyrecycle pdr = new Ppdr_dailyrecycle();
					pdr.setDailyrecycle_user_mobile("手机");
					pdr.setDailyrecycle_type("类型");
					pdr.setDailyrecycle_date("时间                 ");
					myorderlist.add(pdr);
					myorderlistdetail.add(pdr);
				}
				Ppdr_dailyrecycle pdreach = list1.get(i);
				if(i==1){
					Log.d("MyOrderActivity", "MyOrderActivity=pdreach.getUser_type()="+pdreach.getUser_type());
					if(pdreach.getUser_type()!=null&&(!pdreach.getUser_type().equals(""))){
						puser.setUser_type(pdreach.getUser_type());
					}
				}
				if(pdreach.getDailyrecycle_user_mobile().equals(puser.getUser_mobile())){
					myorderlist.add(pdreach);
				}		
				if(!(pdreach.getDailyrecycle_user_mobile().equals(puser.getUser_mobile()))&&pdreach.getDailyrecycle_recyclingmanphone().equals(puser.getUser_mobile())){
					myorderlistdetail.add(pdreach);
				}
			}
		}
		Log.d("MyOrderActivity", "MyOrderActivity=myorderlist="+myorderlist.size());
	}
	class PdrAdapter extends ArrayAdapter<Ppdr_dailyrecycle>{
		private int resourceId;
		public PdrAdapter(Context context,int textViewResourceId,List<Ppdr_dailyrecycle> object){
			super(context, textViewResourceId,object);
			resourceId = textViewResourceId;
		}
		@Override
		public View getView(int position,View convertView,ViewGroup parent){
			Ppdr_dailyrecycle pdr = getItem(position);
			View view;
			if(convertView==null){
				view = LayoutInflater.from(getContext()).inflate(resourceId, null);
			}else{
				view = convertView;
			}		
			TextView shoujiordertext = (TextView)view.findViewById(R.id.shoujiordertext);
			shoujiordertext.setText(pdr.getDailyrecycle_user_mobile().trim());
			TextView leixingordertext = (TextView)view.findViewById(R.id.leixingordertext);
			if(pdr.getDailyrecycle_type()!=null&&!pdr.getDailyrecycle_type().trim().equals("")){
				leixingordertext.setText(pdr.getDailyrecycle_type().trim());
			}else{
				leixingordertext.setText("");
			}
			TextView shijianordertext = (TextView)view.findViewById(R.id.shijianordertext);
			if(pdr.getDailyrecycle_date()!=null&&!pdr.getDailyrecycle_date().trim().equals("")){
				shijianordertext.setText(pdr.getDailyrecycle_date().trim());
			}else{
				shijianordertext.setText("");
			}
			
			if(position==0){
				shoujiordertext.setGravity(Gravity.CENTER);
				leixingordertext.setGravity(Gravity.CENTER);
				shijianordertext.setGravity(Gravity.CENTER);
			}
//			Log.d("MyOrderActivity", "=pdr.getDailyrecycle_user_mobile()="+pdr.getDailyrecycle_user_mobile());
//			Log.d("MyOrderActivity", "=pdr.getDailyrecycle_type()="+pdr.getDailyrecycle_type());
//			Log.d("MyOrderActivity", "=pdr.getDailyrecycle_date()="+pdr.getDailyrecycle_date());
			return view;				
		}
	}
	class PdrAdapterDetail extends ArrayAdapter<Ppdr_dailyrecycle>{
		private int resourceId;
		public PdrAdapterDetail(Context context,int textViewResourceId,List<Ppdr_dailyrecycle> object){
			super(context, textViewResourceId,object);
			resourceId = textViewResourceId;
		}
		@Override
		public View getView(int position,View convertView,ViewGroup parent){
			Ppdr_dailyrecycle pdr = getItem(position);
			View view;
			if(convertView==null){
				view = LayoutInflater.from(getContext()).inflate(resourceId, null);
			}else{
				view = convertView;
			}		
			TextView shoujiordertextdetail = (TextView)view.findViewById(R.id.shoujiordertextdetail);
			shoujiordertextdetail.setText(pdr.getDailyrecycle_user_mobile().trim());
			TextView leixingordertextdetail = (TextView)view.findViewById(R.id.leixingordertextdetail);
			if(pdr.getDailyrecycle_type()!=null&&!pdr.getDailyrecycle_type().trim().equals("")){
				leixingordertextdetail.setText(pdr.getDailyrecycle_type().trim());
			}else{
				leixingordertextdetail.setText("");
			}
			TextView shijianordertextdetail = (TextView)view.findViewById(R.id.shijianordertextdetail);
			if(pdr.getDailyrecycle_date()!=null&&!pdr.getDailyrecycle_date().trim().equals("")){
				shijianordertextdetail.setText(pdr.getDailyrecycle_date().trim());
			}else{
				shijianordertextdetail.setText("");
			}
			if(position==0){
				shoujiordertextdetail.setGravity(Gravity.CENTER);
				leixingordertextdetail.setGravity(Gravity.CENTER);
				shijianordertextdetail.setGravity(Gravity.CENTER);
			}
			return view;				
		}
	}
}

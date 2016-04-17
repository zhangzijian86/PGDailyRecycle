package com.pg.pg.main;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.pg.pg.R;
import com.pg.pg.bean.Pgdr_price;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class PriceActivity extends Activity  implements OnClickListener{
	
	private Button priceyuyuebt;
	private ImageView pricefanhui;
	private ImageView priceimage;
	private TextView pricetitle;
	private LinearLayout linefanhui;
	private List<Pgdr_price> pricelist = new ArrayList<Pgdr_price>();	
	private String type;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);  
        setContentView(R.layout.activity_price);  
        
        priceyuyuebt = (Button)findViewById(R.id.priceyuyuebt);
        priceyuyuebt.setOnClickListener(this);
        
        pricefanhui  = (ImageView)findViewById(R.id.pricefanhui);
        pricefanhui.setOnClickListener(this);
        
        linefanhui = (LinearLayout)findViewById(R.id.linefanhui);
        linefanhui.setOnClickListener(this);
        
        priceimage = (ImageView)findViewById(R.id.priceimage);
        
        
        
        pricetitle = (TextView)findViewById(R.id.pricetitle);
        
        Intent intent = getIntent(); 
        String result = intent.getStringExtra("result");
        type = intent.getStringExtra("type");
        init(result);
        if(pricelist!=null&&pricelist.size()>1){        
	        PdrAdapter pdradapter = new PdrAdapter(
	        		PriceActivity.this,R.layout.activity_mypricelistview,pricelist);
		 	ListView listview = (ListView)findViewById(R.id.pricelistview);
		 	listview.setAdapter(pdradapter);	 
         }       
        
        if(type.equals("shouji")){
        	priceimage.setImageDrawable(getResources().getDrawable(R.drawable.shouji_hong));
        	pricetitle.setText("手机回收");
        }else if(type.equals("jiuyifu")){
        	priceimage.setImageDrawable(getResources().getDrawable(R.drawable.jiuyifu_hong));
        	pricetitle.setText("衣服回收");
         }
        else if(type.equals("suliaoping")){
        	priceimage.setImageDrawable(getResources().getDrawable(R.drawable.suliaoping_hong));
        	pricetitle.setText("塑料瓶回收");
         }
        else if(type.equals("yilaguan")){
        	priceimage.setImageDrawable(getResources().getDrawable(R.drawable.yilaguan_hong));
        	pricetitle.setText("易拉罐回收");
         }
        else if(type.equals("zhi")){
        	priceimage.setImageDrawable(getResources().getDrawable(R.drawable.zhixiang_hong));
        	pricetitle.setText("纸箱回收");
         }
        else if(type.equals("dianzi")){
        	priceimage.setImageDrawable(getResources().getDrawable(R.drawable.dianzi_h));
        	pricetitle.setText("电子设备回收");
         }
        else if(type.equals("jiadian")){
        	priceimage.setImageDrawable(getResources().getDrawable(R.drawable.jiujiadian_hong));
        	pricetitle.setText("家电回收");
         }
        else if(type.equals("qita")){
        	priceimage.setImageDrawable(getResources().getDrawable(R.drawable.gengduo_hong));
        	pricetitle.setText("其他回收");
         }
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.priceyuyuebt:
			Intent intent = new Intent(getApplicationContext(), OrderActivity.class);
			intent.putExtra("type", type);
			startActivity(intent);
			break;
		case R.id.pricefanhui:
			finish();
			break;
		case R.id.linefanhui:
			finish();
			break;
		default:
			break;
		}
	}
	
	public void init(String result){
		JsonUtil jsonUtil=new JsonUtil();
		List<Pgdr_price> list1=(List<Pgdr_price>) jsonUtil.StringFromJsonPrice(result);
		Log.d("MyOrderActivity", "MyOrderActivity=list1="+list1.size());
		if(list1!=null&&list1.size()>0){
			for (int i = 0; i < list1.size(); i++) {
//				if(i==0){
//					Pgdr_price pdp = new Pgdr_price();
//					pdp.setPrice_name("名称");
//					pdp.setPrice_price("价格");
//					pricelist.add(pdp);
//				}else{
					Pgdr_price pdpeach = list1.get(i);
					pricelist.add(pdpeach);
//				}
			}
		}
		Log.d("PriceActivity", "=PriceActivity=pricelist="+pricelist.size());
	}
	
	class PdrAdapter extends ArrayAdapter<Pgdr_price>{
		private int resourceId;
		public PdrAdapter(Context context,int textViewResourceId,List<Pgdr_price> object){
			super(context, textViewResourceId,object);
			resourceId = textViewResourceId;
		}
		@Override
		public View getView(int position,View convertView,ViewGroup parent){
			Pgdr_price pdp = getItem(position);
			View view;
			if(convertView==null){
				view = LayoutInflater.from(getContext()).inflate(resourceId, null);
			}else{
				view = convertView;
			}		
			TextView pricemingchengtext = (TextView)view.findViewById(R.id.pricemingchengtext);
			ImageView priceListviewimage = (ImageView)view.findViewById(R.id.priceListviewimage);
			if(type.equals("jiuyifu")){
	        	priceListviewimage.setImageDrawable(getResources().getDrawable(R.drawable.jiuyifu_lv));        	
	         }
	        else if(type.equals("suliaoping")){
	        	priceListviewimage.setImageDrawable(getResources().getDrawable(R.drawable.suliaoping_lv));        
	         }
	        else if(type.equals("yilaguan")){
	        	priceListviewimage.setImageDrawable(getResources().getDrawable(R.drawable.yilaguan_lv));        
	         }
	        else if(type.equals("zhi")){
	        	priceListviewimage.setImageDrawable(getResources().getDrawable(R.drawable.zhixiang_lv));        
	         }
	        else if(type.equals("dianzi")){
	        	priceListviewimage.setImageDrawable(getResources().getDrawable(R.drawable.dianzi_lv));        
	         }
	        else if(type.equals("jiadian")){
	        	priceListviewimage.setImageDrawable(getResources().getDrawable(R.drawable.jiujiadian_lv));        
	         }
			if(pdp.getPrice_name()!=null){
				pricemingchengtext.setText(pdp.getPrice_name().trim());
			}else{
				pricemingchengtext.setText("");
			}
			TextView pricepricetext = (TextView)view.findViewById(R.id.pricepricetext);
			if(pdp.getPrice_price()!=null){
				pricepricetext.setText(pdp.getPrice_price().trim());
			}else{
				pricepricetext.setText("");
			}
			
			pricemingchengtext.setGravity(Gravity.CENTER);
			pricepricetext.setGravity(Gravity.CENTER);

			return view;				
		}
	}
}

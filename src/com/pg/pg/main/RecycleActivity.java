package com.pg.pg.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

import com.pg.pg.R;
import com.pg.pg.bean.Pgdr_userApp;
import com.pg.pg.login.RegisterActivity;
import com.pg.pg.tools.ExampleUtil;
import com.pg.pg.tools.LoadingProgressDialog;
import com.pg.pg.tools.Operaton;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;

public class RecycleActivity extends Activity{
	
	private ViewPager viewPager; // android-support-v4中的滑动组件
	private List<ImageView> imageViews; // 滑动的图片集合

	private String[] titles; // 图片标题
	private int[] imageResId; // 图片ID
	private List<View> dots; // 图片标题正文的那些点

	private Button  yijianyuyue;
	
	private TextView tv_title;
	private int currentItem = 0; // 当前图片的索引号
	
	private Pgdr_userApp puser;	
	
	private RelativeLayout jiuyifuR;
	private RelativeLayout suliaopingR;
	private RelativeLayout yilaguanR;
	private RelativeLayout zhiR;
	private RelativeLayout dianziR;
	private RelativeLayout jiadianR;
	
	private String typeflag; // 当前图片的索引号
	
	private LoadingProgressDialog dialog;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_recycle);      
               
       jiuyifuR = (RelativeLayout) findViewById(R.id.jiuyifuR); 
       jiuyifuR.setOnClickListener(imageViewlistener);
       suliaopingR = (RelativeLayout) findViewById(R.id.suliaopingR); 
       suliaopingR.setOnClickListener(imageViewlistener);
       yilaguanR = (RelativeLayout) findViewById(R.id.yilaguanR); 
       yilaguanR.setOnClickListener(imageViewlistener);
       zhiR = (RelativeLayout) findViewById(R.id.zhiR); 
       zhiR.setOnClickListener(imageViewlistener);
       dianziR = (RelativeLayout) findViewById(R.id.dianziR); 
       dianziR.setOnClickListener(imageViewlistener);
       jiadianR = (RelativeLayout) findViewById(R.id.jiadianR); 
       jiadianR.setOnClickListener(imageViewlistener);
       
//       image_qita = (ImageView) findViewById(R.id.qita);
//       image_qita.setOnClickListener(imageViewlistener);

		imageResId = new int[] { R.drawable.a};
		titles = new String[imageResId.length];
		titles[0] = "";
/*		titles[1] = "22222222";
		titles[2] = "33333333";
		titles[3] = "44444444";
		titles[4] = "55555555";*/

		imageViews = new ArrayList<ImageView>();

		// 初始化图片资源
		for (int i = 0; i < imageResId.length; i++) {
			ImageView imageView = new ImageView(this);
			imageView.setImageResource(imageResId[i]);
			imageView.setScaleType(ScaleType.CENTER_CROP);
			imageViews.add(imageView);
		}

		
		dots = new ArrayList<View>();
		dots.add(findViewById(R.id.v_dot0));
/*		dots.add(findViewById(R.id.v_dot1));
		dots.add(findViewById(R.id.v_dot2));
		dots.add(findViewById(R.id.v_dot3));
		dots.add(findViewById(R.id.v_dot4));*/

		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setText(titles[0]);//

		viewPager = (ViewPager) findViewById(R.id.vp);
		viewPager.setAdapter(new MyAdapter());// 设置填充ViewPager页面的适配器
		// 设置一个监听器，当ViewPager中的页面改变时调用
		viewPager.setOnPageChangeListener(new MyPageChangeListener());
		//初始化用户数据
		puser = (Pgdr_userApp) getApplication();
		
		yijianyuyue  = (Button) findViewById(R.id.yijianyuyue);
		yijianyuyue.setOnClickListener(imageViewlistener);
		
		dialog=new LoadingProgressDialog(this,"正在加载...");		
	}
	
	private static final String TAG = "JPush";
	private static final int MSG_SET_ALIAS = 1001;
	private static final int MSG_SET_TAGS = 1002;
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
            case MSG_SET_ALIAS:
                Log.d(TAG, "Set alias in handler.");
                JPushInterface.setAliasAndTags(getApplicationContext(), (String) msg.obj, null, mAliasCallback);
                break;
                
            case MSG_SET_TAGS:
                Log.d(TAG, "Set tags in handler.");
                //JPushInterface.setAliasAndTags(getApplicationContext(), null, (Set<String>) msg.obj, mTagsCallback);
                break;
                
            default:
                Log.i(TAG, "Unhandled msg - " + msg.what);
            }
        }
    };
    
	private final TagAliasCallback mAliasCallback = new TagAliasCallback() {

        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs ;
            switch (code) {
            case 0:
                logs = "Set tag and alias success";
                Log.i(TAG, logs);
                break;
                
            case 6002:
                logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                Log.i(TAG, logs);
                if (ExampleUtil.isConnected(getApplicationContext())) {
                	mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                } else {
                	Log.i(TAG, "No network");
                }
                break;
            
            default:
                logs = "Failed with errorCode = " + code;
                Log.e(TAG, logs);
            }
            
            //ExampleUtil.showToast(logs, getApplicationContext());
        }
	    
	};
	
	private String getValue(String name){
		SharedPreferences sp=getSharedPreferences("paramater", Context.MODE_PRIVATE);
		//若没有数据，返回默认值""
		String value=sp.getString(name, "");
		return value;
	}
	
	OnClickListener   imageViewlistener = new OnClickListener() {
		@Override  
		public void onClick(View view) {  
			 int buttonid = view.getId();
			 switch (buttonid) {
/*			    case R.id.shouji:
			    	doSomething("shouji");
			    	Log.d("=com.pg.pg.main.RecycleActivity=", "==imageViewlistener==shouji====");
			    	break;*/
			    case R.id.jiuyifuR:
			    	doSomething("jiuyifu");
			    	Log.d("=com.pg.pg.main.RecycleActivity=", "==imageViewlistener==jiuyifu====");
			    	break;
			    case R.id.suliaopingR:
			    	doSomething("suliaoping");
			    	Log.d("=com.pg.pg.main.RecycleActivity=", "==imageViewlistener==suliaoping====");
			    	break;
			    case R.id.yilaguanR:
			    	doSomething("yilaguan");
			    	Log.d("=com.pg.pg.main.RecycleActivity=", "==imageViewlistener==yilaguan====");
			    	break;
			    case R.id.zhiR:
			    	doSomething("zhi");
			    	Log.d("=com.pg.pg.main.RecycleActivity=", "==imageViewlistener==zhi====");
			    	break;
			    case R.id.dianziR:
			    	doSomething("dianzi");
			    	Log.d("=com.pg.pg.main.RecycleActivity=", "==imageViewlistener==dianzi====");
			    	break;
			    case R.id.jiadianR:
			    	doSomething("jiadian");
			    	Log.d("=com.pg.pg.main.RecycleActivity=", "==imageViewlistener==jiadian====");
			    	break;
/*			    case R.id.qita:
			    	doSomething("qita");
			    	Log.d("=com.pg.pg.main.RecycleActivity=", "==imageViewlistener==qita====");
			    	break;*/
			    case R.id.yijianyuyue:
					if(puser.getUser_mobile().equals("")){
						 Intent intent = new Intent(getApplicationContext(),RegisterActivity.class);
						 intent.putExtra("type", "yijianyuyue");
						 startActivity(intent); 
					}else{
						Intent intent = new Intent(getApplicationContext(), AkeyAppointment.class);
						startActivity(intent);
					}
			    	Log.d("=com.pg.pg.main.RecycleActivity=", "==imageViewlistener==yijianyuyue====");
			    	break;			    	
			    default:
			       break;
			 }
		}   
	};
	
	private void doSomething(String type){
		if(puser.getUser_mobile().equals("")){
			 Intent intent = new Intent(getApplicationContext(),RegisterActivity.class);
			 startActivity(intent); 
		}else{
			typeflag = type;
			new MyOrderRegisterYanZhengMaAsyncTask().execute(new String[]{type});			
		}
	}
	
	// An ExecutorService that can schedule commands to run after a given delay,
	// or to execute periodically.
	private ScheduledExecutorService scheduledExecutorService;
	
	// 切换当前显示的图片
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			viewPager.setCurrentItem(currentItem);// 切换当前显示的图片
		};
	};
	
	@Override
	protected void onStart() {
		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		// 当Activity显示出来后，每两秒钟切换一次图片显示
		scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 1, 4, TimeUnit.SECONDS);
		super.onStart();
	}

	@Override
	protected void onStop() {
		// 当Activity不可见的时候停止切换
		scheduledExecutorService.shutdown();
		super.onStop();
	}
	
	/**
	 * 换行切换任务
	 * 
	 * @author Administrator
	 * 
	 */
	private class ScrollTask implements Runnable {

		public void run() {
			synchronized (viewPager) {
				System.out.println("currentItem: " + currentItem);
				currentItem = (currentItem + 1) % imageViews.size();
				handler.obtainMessage().sendToTarget(); // 通过Handler切换图片
			}
		}

	}

	/**
	 * 当ViewPager中页面的状态发生改变时调用
	 * 
	 * @author Administrator
	 * 
	 */
	private class MyPageChangeListener implements OnPageChangeListener {
		private int oldPosition = 0;

		/**
		 * This method will be invoked when a new page becomes selected.
		 * position: Position index of the new selected page.
		 */
		public void onPageSelected(int position) {
			currentItem = position;
			tv_title.setText(titles[position]);
//			dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
//			dots.get(position).setBackgroundResource(R.drawable.dot_focused);
			oldPosition = position;
		}

		public void onPageScrollStateChanged(int arg0) {

		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}
	}

	/**
	 * 填充ViewPager页面的适配器
	 * 
	 * @author Administrator
	 * 
	 */
	private class MyAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return imageResId.length;
		}

		@Override
		public Object instantiateItem(View arg0, int arg1) {
			((ViewPager) arg0).addView(imageViews.get(arg1));
			return imageViews.get(arg1);
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView((View) arg2);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {

		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {

		}

		@Override
		public void finishUpdate(View arg0) {

		}
	}
	
	/**
	 * dis：AsyncTask参数类型：
	 * 第一个参数标书传入到异步任务中并进行操作，通常是网络的路径
	 * 第二个参数表示进度的刻度
	 * 第三个参数表示返回的结果类型
	 * */
	private class MyOrderRegisterYanZhengMaAsyncTask extends AsyncTask<String, String, String>{
		//任务执行之前的操作
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			dialog.show();//显示dialog，数据正在处理....
		}
		//完成耗时操作
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			try{
				Operaton operaton=new Operaton();
 				String result=operaton.getPriceByType("GetPrice", params[0]);
 				return result;
			}catch(Exception e){
				e.printStackTrace();
				return "false";
			}
		}
		
		@Override
		protected void onProgressUpdate(String... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
			
		}
		
		//数据处理完毕后更新UI操作
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result!=null){
				if("false".equals(result)){
					Toast.makeText(getApplicationContext(), "数据加载失败，请重新请求！", Toast.LENGTH_SHORT).show();
				}else{
					Intent intent = new Intent(getApplicationContext(), PriceActivity.class);
					intent.putExtra("result", result);
					intent.putExtra("type", typeflag);
					startActivity(intent);
				}
			}else{
				Toast.makeText(getApplicationContext(), "数据加载失败，请重新请求！", Toast.LENGTH_SHORT).show();
			}
			dialog.dismiss();//dialog关闭，数据处理完毕
		}
	}

}

package com.pg.pg.main;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.pg.pg.R;

import android.app.Activity;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;

public class RecycleActivity extends Activity{
	
	private ViewPager viewPager; // android-support-v4中的滑动组件
	private List<ImageView> imageViews; // 滑动的图片集合

	private String[] titles; // 图片标题
	private int[] imageResId; // 图片ID
	private List<View> dots; // 图片标题正文的那些点

	private TextView tv_title;
	private int currentItem = 0; // 当前图片的索引号
	
	private ImageView image_shouji;
	private ImageView image_jiuyifu;
	private ImageView image_suliaoping;
	private ImageView image_yilaguan;
	private ImageView image_zhi;
	private ImageView image_dianzi;
	private ImageView image_jiadian;
	private ImageView image_qita;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_recycle);      
        
       image_shouji = (ImageView) findViewById(R.id.shouji);
       image_shouji.setOnClickListener(imageViewlistener);
       image_jiuyifu = (ImageView) findViewById(R.id.jiuyifu);
       image_jiuyifu.setOnClickListener(imageViewlistener);
       image_suliaoping = (ImageView) findViewById(R.id.suliaoping);
       image_suliaoping.setOnClickListener(imageViewlistener);
       image_yilaguan = (ImageView) findViewById(R.id.yilaguan);
       image_yilaguan.setOnClickListener(imageViewlistener);
       image_zhi = (ImageView) findViewById(R.id.zhi);
       image_zhi.setOnClickListener(imageViewlistener);
       image_dianzi = (ImageView) findViewById(R.id.dianzi);
       image_dianzi.setOnClickListener(imageViewlistener);
       image_jiadian = (ImageView) findViewById(R.id.jiadian);
       image_jiadian.setOnClickListener(imageViewlistener);
       image_qita = (ImageView) findViewById(R.id.qita);
       image_qita.setOnClickListener(imageViewlistener);

		imageResId = new int[] { R.drawable.a, R.drawable.f, R.drawable.g, R.drawable.f, R.drawable.g };
		titles = new String[imageResId.length];
		titles[0] = "11111111";
		titles[1] = "22222222";
		titles[2] = "33333333";
		titles[3] = "44444444";
		titles[4] = "55555555";

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
		dots.add(findViewById(R.id.v_dot1));
		dots.add(findViewById(R.id.v_dot2));
		dots.add(findViewById(R.id.v_dot3));
		dots.add(findViewById(R.id.v_dot4));

		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_title.setText(titles[0]);//

		viewPager = (ViewPager) findViewById(R.id.vp);
		viewPager.setAdapter(new MyAdapter());// 设置填充ViewPager页面的适配器
		// 设置一个监听器，当ViewPager中的页面改变时调用
		viewPager.setOnPageChangeListener(new MyPageChangeListener());
	}
	
	OnClickListener   imageViewlistener = new OnClickListener() {
		@Override  
		public void onClick(View view) {  
			 int buttonid = view.getId();
			 switch (buttonid) {
			    case R.id.shouji:
			    	Log.d("=com.pg.pg.main.RecycleActivity=", "==imageViewlistener==shouji====");
			    	break;
			    case R.id.jiuyifu:
			    	Log.d("=com.pg.pg.main.RecycleActivity=", "==imageViewlistener==jiuyifu====");
			    	break;
			    case R.id.suliaoping:
			    	Log.d("=com.pg.pg.main.RecycleActivity=", "==imageViewlistener==suliaoping====");
			    	break;
			    case R.id.yilaguan:
			    	Log.d("=com.pg.pg.main.RecycleActivity=", "==imageViewlistener==yilaguan====");
			    	break;
			    case R.id.zhi:
			    	Log.d("=com.pg.pg.main.RecycleActivity=", "==imageViewlistener==zhi====");
			    	break;
			    case R.id.dianzi:
			    	Log.d("=com.pg.pg.main.RecycleActivity=", "==imageViewlistener==dianzi====");
			    	break;
			    case R.id.jiadian:
			    	Log.d("=com.pg.pg.main.RecycleActivity=", "==imageViewlistener==jiadian====");
			    	break;
			    case R.id.qita:
			    	Log.d("=com.pg.pg.main.RecycleActivity=", "==imageViewlistener==qita====");
			    	break;
			    default:
			       break;
			 }
		}   
	};
	
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
			dots.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
			dots.get(position).setBackgroundResource(R.drawable.dot_focused);
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

}

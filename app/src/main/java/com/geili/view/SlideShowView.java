package com.geili.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

import com.geili.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;



public class SlideShowView extends FrameLayout {
	
	private ImageLoader imageLoader = ImageLoader.getInstance();
	
	//轮播图图片数量
    private final static int IMAGE_COUNT = 3;
    
    //自动轮播的时间间隔
    private final static int TIME_INTERVAL = 3;
    
    //自动轮播启用开关
    private final static boolean isAutoPlay = true; 
    
    //自定义轮播图的资源
    private int[] imagesResIds;
    
    //放轮播图片的ImageView 的list
    private List<ImageView> imageViewsList;
    
    //放圆点的View的list
    private List<View> dotViewsList;
    
    private ViewPager viewPager;
    private int currentItem  = 0;
    
    //定时任务
    private ScheduledExecutorService scheduledExecutorService;
    
    private Context context;
    
    //Handler
    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            viewPager.setCurrentItem(currentItem);
        }
        
    };
    
    public SlideShowView(Context context) {
        this(context,null);
        // TODO Auto-generated constructor stub
    }
    public SlideShowView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        // TODO Auto-generated constructor stub
    }
    public SlideShowView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

		//initImageLoader(context);
        initData();					//initData() initUI(context)顺序不要交换
		initUI(context);
        if(isAutoPlay){
            startPlay();
        }
        
    }
    
    /**
     * 开始轮播图切换
     */
    private void startPlay(){
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(new SlideShowTask(), 1, 3, TimeUnit.SECONDS);
    }
    
    /**
     * 停止轮播图切换
     */
    private void stopPlay(){
        scheduledExecutorService.shutdown();
    }
    
    private void initData(){
    	imagesResIds = new int[]{
    			R.drawable.pic1,
    			R.drawable.pic2,
    			R.drawable.pic3
    	};
    	
        imageViewsList = new ArrayList<ImageView>();
        dotViewsList = new ArrayList<View>();

        //new GetListTask().execute("");
    }
    
    private void initUI(Context context){
    	if(imagesResIds == null || imagesResIds.length == 0)
    		return;
    	
        LayoutInflater.from(context).inflate(R.layout.layout_slideshow, this, true);
        
        LinearLayout dotLayout = (LinearLayout)findViewById(R.id.dotLayout);
        dotLayout.removeAllViews();
        
        for (int i = 0; i < imagesResIds.length; i++) {
        	ImageView view =  new ImageView(context);
        	//view.setTag(imageUrls[i]);
        	view.setImageResource(imagesResIds[i]);
        	//if(i==0)	//给一个默认图
        	//	view.setBackgroundResource(R.drawable.pic2);
        	view.setScaleType(ScaleType.FIT_XY);
        	imageViewsList.add(view);
        	
        	ImageView dotView =  new ImageView(context);
        	LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20,20);
        	params.leftMargin = 4;
			params.rightMargin = 4;
			dotLayout.addView(dotView, params);
        	dotViewsList.add(dotView);
		}
        
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setFocusable(true);
        
        viewPager.setAdapter(new MyPagerAdapter());
        viewPager.setOnPageChangeListener(new MyPageChangeListener());
    }
    
    
    private class MyPagerAdapter  extends PagerAdapter{

        @Override
        public void destroyItem(View container, int position, Object object) {
            //((ViewPag.er)container).removeView((View)object);
            ((ViewPager)container).removeView(imageViewsList.get(position));
        }

        @Override
        public Object instantiateItem(View container, int position) {
        	//ImageView imageView = imageViewsList.get(position);

			//imageLoader.displayImage(imageView.getTag() + "", imageView);
        	
            ((ViewPager)container).addView(imageViewsList.get(position));
            return imageViewsList.get(position);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return imageViewsList.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            // TODO Auto-generated method stub
            return arg0 == arg1;
        }
        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
            // TODO Auto-generated method stub

        }

        @Override
        public Parcelable saveState() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public void startUpdate(View arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void finishUpdate(View arg0) {
            // TODO Auto-generated method stub
            
        }
        
    }
    
    /**
     * ViewPager的监听器
     * 当ViewPager中页面的状态发生改变时调用
     * 
     */
    private class MyPageChangeListener implements OnPageChangeListener{

        boolean isAutoPlay = false;

        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub
            switch (arg0) {
            case 1:
                isAutoPlay = false;
                break;
            case 2:
                isAutoPlay = true;
                break;
            case 0:
                
                if (viewPager.getCurrentItem() == viewPager.getAdapter().getCount() - 1 && !isAutoPlay) {
                    viewPager.setCurrentItem(0);
                }
                
                else if (viewPager.getCurrentItem() == 0 && !isAutoPlay) {
                    viewPager.setCurrentItem(viewPager.getAdapter().getCount() - 1);
                }
                break;
        }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            
            
        }

        @Override
        public void onPageSelected(int pos) {
            
            
            currentItem = pos;
            for(int i=0;i < dotViewsList.size();i++){
                if(i == pos){
                    ((View)dotViewsList.get(pos)).setBackgroundResource(R.drawable.dot_focus);
                }else {
                    ((View)dotViewsList.get(i)).setBackgroundResource(R.drawable.dot_blur);
                }
            }
        }
        
    }
    
    /**
     *执行轮播图切换任务
     *
     */
    private class SlideShowTask implements Runnable{

        @Override
        public void run() {
            // TODO Auto-generated method stub
            synchronized (viewPager) {
                currentItem = (currentItem+1)%imageViewsList.size();
                handler.obtainMessage().sendToTarget();
            }
        }
        
    }
    
    /**
     * 销毁ImageView资源，回收内存
     * 
     */
    private void destoryBitmaps() {

        for (int i = 0; i < IMAGE_COUNT; i++) {
            ImageView imageView = imageViewsList.get(i);
            Drawable drawable = imageView.getDrawable();
            if (drawable != null) {
                
                drawable.setCallback(null);
            }
        }
    }
 

	/*class GetListTask extends AsyncTask<String, Integer, Boolean> {

		@Override
		protected Boolean doInBackground(String... params) {
			try {
				
				imageUrls = new String[]{
						"http://image.zcool.com.cn/56/35/1303967876491.jpg",
						"http://image.zcool.com.cn/59/54/m_1303967870670.jpg",
						"http://image.zcool.com.cn/47/19/1280115949992.jpg",
				};
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			if (result) {
		        initUI(context);
			}
		}
	}*/
	
	/*
	public static void initImageLoader(Context context) {
		// This configuration tuning is custom. You can tune every option, you
		// may tune some of them,
		// or you can create default configuration by
		// ImageLoaderConfiguration.createDefault(this);
		// method.
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context).threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory().discCacheFileNameGenerator(new Md5FileNameGenerator()).tasksProcessingOrder(QueueProcessingType.LIFO).writeDebugLogs() // Remove
																																																																								// for
																																																																								// release
																																																																								// app
				.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
	}*/
}
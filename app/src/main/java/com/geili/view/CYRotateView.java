package com.geili.view;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Scroller;
@SuppressLint("DrawAllocation")
public class CYRotateView extends ViewGroup{
	public interface CYRotateViewListener{
		void getRorateCurrentView(int item);
	}
		// 无事件的状态
		private static final int TOUCH_STATE_REST = 0;
		// 处于拖动的状态
		private static final int TOUCH_STATE_SCROLLING = 1;
		public static final int RORATE_HORIZONTAL = 2;
		public static final int RORATE_VERTICAL = 3;
		
		// 用于滑动的类
		private Scroller mScroller;
		private int mTouchState = TOUCH_STATE_REST;
		private int mTouchSlop;
		// 用来跟踪触摸速度的类
		private VelocityTracker mVelocityTracker;
		// 滑动的速度
		private static final int SNAP_VELOCITY = 600;
		// 当前的屏幕视图
		private int mCurScreen;
		 private int mPreScreen;
		// 默认的显示视图
		private int mDefaultScreen = 0;
		// 用来处理立体效果的类
		private Camera mCamera;
		private Matrix mMatrix;
		// 旋转的角度
		private float angle = 90;
		private Context context;
		private int rotateDirecation;
		private boolean isNeedCirculate = true;
		private int min ;
		private int max ;
		private CYRotateViewListener listener;
		
		
    

	@SuppressLint("FloatMath")
	public CYRotateView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}
	public CYRotateView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		mScroller = new Scroller(context);

		mCurScreen = mDefaultScreen;
		mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();

		mCamera = new Camera();
		mMatrix = new Matrix();
		
		rotateDirecation = RORATE_HORIZONTAL;
	}
	public void setRotateViewListener(CYRotateViewListener listener){
		this.listener = listener;
		
	}
	public void setRorateDirecation(int direcation){
		this.rotateDirecation = direcation;
	}
	
	public void setIsNeedCirculate(boolean need){
		this.isNeedCirculate = need;
		
	}
	public void setRoateAngle(float angle){
		this.angle = angle;
	}
	public void rorateTo(int index ){
		if (mScroller.isFinished()) //如果返回true，表示动画还没有结束
			snapToScreen(index);
		else 
			snapToDestination();
	}
	public void rorateToNext(){
	if (mScroller.isFinished()) //如果返回true，表示动画还没有结束
		snapToScreen(mCurScreen + 1);
	else 
		snapToDestination();
	}
	public void rorateToPre(){
		if (mScroller.isFinished()) //如果返回true，表示动画还没有结束
		snapToScreen(mCurScreen - 1);
		else 
			snapToDestination();
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		// 创建测量参数
		int cellWidthSpec = MeasureSpec.makeMeasureSpec(widthMeasureSpec,
				MeasureSpec.UNSPECIFIED);
		int cellHeightSpec = MeasureSpec.makeMeasureSpec(heightMeasureSpec,
				MeasureSpec.UNSPECIFIED);
		// 记录ViewGroup中Child的总个数
		int count = getChildCount();
		// 设置子空间Child的宽高
		for (int i = 0; i < count; i++) {
			View childView = getChildAt(i);
			/*
			 * This is called to find out how big a view should be. The parent
			 * supplies constraint information in the width and height
			 * parameters. The actual mesurement work of a view is performed in
			 * onMeasure(int, int), called by this method. Therefore, only
			 * onMeasure(int, int) can and must be overriden by subclasses.
			 */
			childView.measure(cellWidthSpec, cellHeightSpec);
		}

		// 设置容器控件所占区域大小
		// 注意setMeasuredDimension和resolveSize的用法
		setMeasuredDimension(
				resolveSize(widthMeasureSpec * count, widthMeasureSpec),
				resolveSize(heightMeasureSpec * count, heightMeasureSpec));
		// setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
		// 不需要调用父类的方法
		// super.onMeasure(widthMeasureSpec, heightMeasureSpec);

	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		if (changed) {
			int childLeft = 0;
			int childTop = 0;
			int childCount = getChildCount();
			if(isNeedCirculate)
				setHeadAndFoot();
			childCount = getChildCount();
			for (int i = 0; i < childCount; i++) {
				final View childView = getChildAt(i);
		        //setLayoutParams(childView);
				if (childView.getVisibility() != View.GONE) {
					//childView.measure(r-l, b-t);
					final int childWidth = childView.getMeasuredWidth();
					 int childHeight = childView.getMeasuredHeight();
					//childHeight = getHeight();
					if(rotateDirecation == RORATE_HORIZONTAL){
							childView.layout(childLeft, 0, childLeft+childWidth,childHeight);
						
						childLeft += childWidth;
					}else if(rotateDirecation == RORATE_VERTICAL){
						childView.layout(0, childTop, childWidth,childTop + childHeight);
						childTop += childHeight;
					}
					
						}
			}
			if(isNeedCirculate){
				min = 1;
				max = getChildCount() - 2;
				if(rotateDirecation == RORATE_HORIZONTAL){
					scrollBy(getWidth(), 0);
				}else if(rotateDirecation == RORATE_VERTICAL){
					scrollBy(0, getHeight());
				}
			}else{
				min = 0;
				max = getChildCount() - 1;
			}
			
		}
	}
	private void setHeadAndFoot() {
        ImageView img0 = new ImageView(context);
        ImageView img1 = new ImageView(context);
        View view0 = getChildAt(0);
        View view1 = getChildAt(getChildCount() - 1);
        setLayoutParams(view0);
        setLayoutParams(view1);
        img0.setImageBitmap(convertViewToBitmap(view0));
        img1.setImageBitmap(convertViewToBitmap(view1));
        addView(img1, 0);
        addView(img0);
        
}
	private  Bitmap convertViewToBitmap(View v){
		v.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
        v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
        v.buildDrawingCache();
	        Bitmap bitmap = v.getDrawingCache();
	return bitmap;
	}
	
	private void setLayoutParams(View view) {
		LayoutParams layoutParams = view.getLayoutParams();
		layoutParams.height = getMeasuredHeight();
		layoutParams.width = getMeasuredWidth();
		view.setLayoutParams(layoutParams);
		
	}
	private void snapToScreen(int whichScreen) {
		whichScreen =  ( whichScreen >=  getChildCount() - 1 ) ? getChildCount() - 1 : whichScreen ;
		switch (rotateDirecation) {
		case RORATE_HORIZONTAL:
			if(getScrollX() != whichScreen * getWidth()){
				mCurScreen = whichScreen;
				final int delta = (int) (whichScreen * getWidth() - getScrollX());
				mScroller.startScroll(getScrollX(), 0, delta, 0,Math.abs(delta) * 2);
				if(isNeedCirculate){
						if(mCurScreen == 0){
							mScroller.startScroll(getWidth() * (getChildCount() - 2) - delta, 0, delta, 0,Math.abs(delta) * 2);
							mCurScreen = getChildCount() - 2;
						}
						if(mCurScreen == getChildCount() - 1){
							mScroller.startScroll(getWidth() - delta, 0, delta, 0,Math.abs(delta) * 2);
							mCurScreen = 1;
						}
				}
				
				invalidate(); // 重新布局
			}
			break;
		case RORATE_VERTICAL:
			if(getScrollY() != whichScreen * getHeight()){
				mCurScreen = whichScreen;
				final int delta = (int) (whichScreen * getHeight() - getScrollY());
				mScroller.startScroll(0,getScrollY(), 0, delta,Math.abs(delta) * 2);
				if(isNeedCirculate){
					if(mCurScreen == 0){
						mScroller.startScroll(0,getHeight() * (getChildCount() - 2) - delta,0, delta,Math.abs(delta) * 2);
						mCurScreen = getChildCount() - 2;
					}
					if(mCurScreen == getChildCount() - 1){
						mScroller.startScroll(0,getHeight() - delta, 0,delta,Math.abs(delta) * 2);
						mCurScreen = 1;
					}
			}
				invalidate(); // 重新布局
			}
			break;

		}
		if (this.mPreScreen != this.mCurScreen && listener != null) {
	        this.mPreScreen = this.mCurScreen;
	        int item = isNeedCirculate ? this.mCurScreen - 1  : this.mCurScreen ;
	        this.listener.getRorateCurrentView(item);
	      }
		
	}
	@Override
	public void computeScroll() {
		if (mScroller.computeScrollOffset()) {//如果返回true，表示动画还没有结束
			scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
			postInvalidate();
		}
	}
	/**
	 * 根据目前的位置滚动到下一个视图位置.
	 */
	private void snapToDestination() {
		int destScreen = 0;
		if(rotateDirecation == RORATE_HORIZONTAL){
			final int screenWidth = getWidth();
			// 根据View的宽度以及滑动的值来判断是哪个View
			destScreen = (getScrollX() + screenWidth / 2) / screenWidth;
		}else if(rotateDirecation == RORATE_VERTICAL){
			final int screenHeight = getHeight();
			// 根据View的宽度以及滑动的值来判断是哪个View
			destScreen = (getScrollY() + screenHeight / 2) / screenHeight;
		}
		snapToScreen(destScreen);
		
	}
	
	private float mLastMotionY;
	private float mLastMotionX;
	/*
	 * 当进行View滑动时，会导致当前的View无效，该函数的作用是对View进行重新绘制 调用drawScreen函数
	 */
	protected void dispatchDraw(Canvas canvas) {
		final long drawingTime = getDrawingTime();
		final int count = getChildCount();
		for (int i = 0; i < count; i++) {
			drawScreen(canvas, i, drawingTime);
		}
	}
	
	/*
	 * 立体效果的实现函数 ,screen为哪一个子View
	 */
	private void drawScreen(Canvas canvas, int screen, long drawingTime) {
		if(rotateDirecation == RORATE_HORIZONTAL){
			// 得到当前子Vie  w的宽度
			final int width = getWidth();
			final int scrollHeight = screen * width;
			int scrollX = this.getScrollX();
			int faceIndex = screen;
			if (scrollHeight > scrollX + width || scrollHeight + width < scrollX) {
				return;
			}
			final View child = getChildAt(faceIndex);
			final float currentDegree = scrollX * (angle / getMeasuredWidth());
			final float faceDegree = currentDegree - faceIndex * angle;
			if (faceDegree > 90 || faceDegree < -90) {
				return;
			}
			 float centerX = (scrollHeight < scrollX) ? scrollHeight + width
					: scrollHeight;
			final float centerY = getHeight() / 2;
			final Camera camera = mCamera;
			final Matrix matrix = mMatrix;
			canvas.save();
			camera.save();
			camera.rotateY( -faceDegree);
			camera.getMatrix(matrix);
			camera.restore();
			matrix.preTranslate(-centerX, -centerY);
			matrix.postTranslate(centerX, centerY);
			canvas.concat(matrix);
			drawChild(canvas, child, drawingTime);
			canvas.restore();
		}else if(rotateDirecation == RORATE_VERTICAL){
			// 得到当前子Vie  w的宽度
			final int height = getHeight();
			final int scrollHeight = screen * height;
			int scrollY = this.getScrollY();
			int faceIndex = screen;
			if (scrollHeight > scrollY + height || scrollHeight + height < scrollY) {
				return;
			}
			final View child = getChildAt(faceIndex);
			final float currentDegree = scrollY * (angle / getMeasuredHeight());
			final float faceDegree = currentDegree - faceIndex * angle;
			if (faceDegree > 90 || faceDegree < -90) {
				return;
			}
			 float centerY = (scrollHeight < scrollY) ? scrollHeight + height
					: scrollHeight;
			final float centerX = getWidth() / 2;
			final Camera camera = mCamera;
			final Matrix matrix = mMatrix;
			canvas.save();
			camera.save();
			camera.rotateX(faceDegree);
			camera.getMatrix(matrix);
			camera.restore();
			matrix.preTranslate(-centerX, -centerY);
			matrix.postTranslate(centerX, centerY);
			canvas.concat(matrix);
			drawChild(canvas, child, drawingTime);
			canvas.restore();
		}
		
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (mVelocityTracker == null) {
			// 使用obtain方法得到VelocityTracker的一个对象
			mVelocityTracker = VelocityTracker.obtain();
		}
		// 将当前的触摸事件传递给VelocityTracker对象
		mVelocityTracker.addMovement(event);
		// 得到触摸事件的类型
		final int action = event.getAction();
		final float y = event.getY();
		final float x = event.getX();

		switch (action) {
		case MotionEvent.ACTION_DOWN:
			if (!mScroller.isFinished()) {
				mScroller.abortAnimation();
			}
			mLastMotionX = x;
			mLastMotionY = y;
			break;
		case MotionEvent.ACTION_MOVE:
			int deltaY = (int) (mLastMotionY - y);
			int deltaX = (int) (mLastMotionX -x);
			mLastMotionY = y;
			mLastMotionX = x;
			if(rotateDirecation == RORATE_HORIZONTAL)
			scrollBy(deltaX, 0);
			else if(rotateDirecation == RORATE_VERTICAL)
			scrollBy(0, deltaY);
			break;

		case MotionEvent.ACTION_UP:
			//if (mTouchState == TOUCH_STATE_SCROLLING) {
			final VelocityTracker velocityTracker = mVelocityTracker;
			// 计算当前的速度
			velocityTracker.computeCurrentVelocity(1000);
			// 获得当前的速度
			int velocityY = (int) velocityTracker.getYVelocity();
			int velocityX = (int) velocityTracker.getXVelocity();
			if ((velocityY > SNAP_VELOCITY  && rotateDirecation == RORATE_VERTICAL
			|| velocityX > SNAP_VELOCITY && rotateDirecation == RORATE_HORIZONTAL) && mCurScreen > min) {
				// Fling enough to move left
				snapToScreen(mCurScreen - 1);
			} else if ( (velocityY < -SNAP_VELOCITY && rotateDirecation == RORATE_VERTICAL || velocityX < -SNAP_VELOCITY && rotateDirecation == RORATE_HORIZONTAL) && mCurScreen < max) {
				// Fling enough to move right
				snapToScreen(mCurScreen + 1);
			} else {
				snapToDestination();
			}

			if (mVelocityTracker != null) {
				mVelocityTracker.recycle();
				mVelocityTracker = null;
			}
			// }
			mTouchState = TOUCH_STATE_REST;
			break;
		case MotionEvent.ACTION_CANCEL:
			mTouchState = TOUCH_STATE_REST;
			break;
		}

		return true;
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub

		final int action = ev.getAction();
		if ((action == MotionEvent.ACTION_MOVE)
				&& (mTouchState != TOUCH_STATE_REST)) {
			return true;
		}

		final float x = ev.getX();

		switch (action) {
		case MotionEvent.ACTION_DOWN:
			mLastMotionX = x;
			mTouchState = mScroller.isFinished() ? TOUCH_STATE_REST : TOUCH_STATE_SCROLLING;
			break;
		case MotionEvent.ACTION_MOVE:
			final int xDiff = (int) Math.abs(mLastMotionX - x);
			if (xDiff > mTouchSlop) {
				mTouchState = TOUCH_STATE_SCROLLING;
			}
			break;
		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_UP:
			mTouchState = TOUCH_STATE_REST;
			break;
		}
		return mTouchState != TOUCH_STATE_REST;
	}
	

}

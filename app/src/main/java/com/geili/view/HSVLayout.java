package com.geili.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.geili.R;
import com.geili.adapter.HSVAdapter;
import com.geili.adapter.ImageAdapter;
import com.geili.util.ContantValues;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import uk.co.senab.photoview.PhotoViewAttacher;

public class HSVLayout extends LinearLayout {

	private HSVAdapter adapter;
	private Context context;

	public HSVLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}

	public void setAdapter(final String[]list,  HSVAdapter adapter) {
		this.adapter = adapter;
		for (int i = 0; i < adapter.getCount(); i++) {
			final Map<String, Object> map = adapter.getItem(i);
			map.put("index", i);
			final View view = adapter.getView(i, null, null);
			view.setPadding(10, 10, 10, 10);

			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					350, LayoutParams.MATCH_PARENT);

			final int pos = i;
			view.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					//Toast.makeText(context, "点击了" + map.get("index"), Toast.LENGTH_SHORT).show();
					setViewPagerAndZoom(view, pos, list);
				}
			});
			this.setOrientation(HORIZONTAL);
			this.addView(view, params);
		}
	}

	public void setViewPagerAndZoom(View v ,int position, String[]list) {
		//得到要放大展示的视图界面
		ViewPager expandedView = (ViewPager)((Activity)context).findViewById(R.id.detail_view);
		//最外层的容器，用来计算
		View containerView = (FrameLayout)((Activity)context).findViewById(R.id.img_container);
		//实现放大缩小类，传入当前的容器和要放大展示的对象
		ZoomTutorial mZoomTutorial = new ZoomTutorial(containerView, expandedView);

		ImageAdapter adapter = new ImageAdapter(context, list, mZoomTutorial);
		expandedView.setAdapter(adapter);
		expandedView.setCurrentItem(position);

		// 通过传入Id来从小图片扩展到大图，开始执行动画
		mZoomTutorial.zoomImageFromThumb(v);
		mZoomTutorial.setOnZoomListener(new ZoomTutorial.OnZoomListener() {

			@Override
			public void onThumbed() {
				System.out.println("现在是-------------------> 小图状态");
			}

			@Override
			public void onExpanded() {
				System.out.println("现在是-------------------> 大图状态");
			}
		});
	}

}

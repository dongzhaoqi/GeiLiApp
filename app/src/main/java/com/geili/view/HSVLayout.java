package com.geili.view;

import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.geili.adapter.HSVAdapter;
import com.geili.util.ContantValues;

public class HSVLayout extends LinearLayout {

	private HSVAdapter adapter;
	private Context context;

	public HSVLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}

	public void setAdapter(HSVAdapter adapter) {
		this.adapter = adapter;
		for (int i = 0; i < adapter.getCount(); i++) {
			final Map<String, Object> map = adapter.getItem(i);
			View view = adapter.getView(i, null, null);
			view.setPadding(10, 0, 10, 0);

			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);

			view.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					/*Toast.makeText(context, "点击了" + map.get("index"),
							Toast.LENGTH_SHORT).show();*/
					Intent intent = new Intent();
					intent.setAction(ContantValues.UPDATE_IMAGE_ACTION);
					intent.putExtra("index", (Integer)map.get("index"));
					context.sendBroadcast(intent);
					
				}
			});
			this.setOrientation(HORIZONTAL);
			this.addView(view, params);
		}
	}
}

package com.geili.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;

import com.geili.view.ZoomTutorial;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Dong on 4/23/2016.
 */
public class ImageAdapter extends PagerAdapter {

    private String[] sDrawables;
    private Context mContext;
    private ZoomTutorial mZoomTutorial;

    public ImageAdapter( Context context ,String[] imgUrls,ZoomTutorial zoomTutorial) {
        this.sDrawables = imgUrls;
        this.mContext = context;
        this.mZoomTutorial = zoomTutorial;
    }

    @Override
    public int getCount() {
        return sDrawables.length;
    }

    @Override
    public View instantiateItem(ViewGroup container, final int position) {

        final ImageView imageView = new ImageView(mContext);
        Picasso.with(mContext).load(sDrawables[position]).into(imageView);
        container.addView(imageView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

        imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                mZoomTutorial.closeZoomAnim(position);
            }
        });
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


}
